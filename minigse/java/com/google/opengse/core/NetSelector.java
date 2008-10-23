// Copyright 2002-2006 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.opengse.core;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The <code>NetSelector</code> class provides a fairly thin wrapper
 * over the <code>java.nio.channels.Selector</code>
 * class. Specifically, a callback based system is created on top of
 * the NIO select system. Additionally, support for timers is provided
 * via the <code>java.util.Timer</code> class.
 *
 * @author Peter Mattis
 */
public class NetSelector {
  private static final Logger logger_ =
    Logger.getLogger("com.google.opengse.NetSelector");

  private static final int DEFAULT_MAX_REQUESTS = 250;
  private static final int DEFAULT_MAX_NULL_LOOPS = 100000;
  private static final String ADDRESS_ALREADY_IN_USE = "Address already in use";

  private final Selector selector_;
  private final SortedSet<NetTimer> timers_;
  private final List<ServerSocketChannel> server_channels_;
  private final Map<SelectionKey, Integer> key_ops_;
  private final Set<NetConnection> connections_;
  private final Set<Integer> secure_ports_;
  private int first_port_;
  private NetSelectionCallback callback_;
  private long next_timer_id_ = 0;
  private boolean accepting_ = true;
  private boolean quit_ = false;
  private int max_requests_ = DEFAULT_MAX_REQUESTS;
  private int max_null_loops_ = DEFAULT_MAX_NULL_LOOPS;
  private boolean log_status_ = false;
  private boolean debug_port_enabled_ = false;
  private int debug_port_;

  /**
   * This variable identifies the "network" thread. That is, the
   * thread which is responsible for handling the select() loop.
   * This variable is used to discriminate between callers in order
   * to avoid blocking calls to SelectionKey.interestOps. This
   * is necessary because worker threads which stream data to the
   * client need to apprise the selector that data is ready to
   * be streamed without blocking on a selection key, which the
   * network thread can already be indefinitely blocked on.
   */
  private Thread network_thread_ = null;

  /**
   * Class constructor.
   */
  public NetSelector() throws IOException {
    this(DEFAULT_MAX_REQUESTS);
  }

  public NetSelector(int max_requests) throws IOException {
    this.selector_ = Selector.open();
    this.max_requests_ = max_requests;
    this.timers_ = Collections.synchronizedSortedSet(new TreeSet<NetTimer>());
    this.key_ops_ = Collections.synchronizedMap(
        new HashMap<SelectionKey, Integer>());
    // do NOT change the following line to Lists.newArrayList()
    this.server_channels_ = new ArrayList<ServerSocketChannel>();
    this.connections_ = Collections.synchronizedSet(
        new HashSet<NetConnection>());
    // do NOT change the following line to Sets.newHashSet()
    this.secure_ports_ = new HashSet<Integer>();
    this.first_port_ = 0;
  }

  /**
   * Return <code>true</code> if the current thread is the
   * network thread.
   * @return <code>true</code> if operating as I/O thread
   */
  public boolean isNetworkThread() {
    return network_thread_ == null || Thread.currentThread() == network_thread_;
  }

  /**
   * Sets the maximum number of requests to entertain concurrently
   * before ignoring incoming requests.
   *
   * @param max_requests is the maximum request queue length
   */
  public void setMaxRequests(int max_requests) {
    this.max_requests_ = max_requests;
  }

  /**
   * Sets the maximum number of null loops in a row that are allowed
   * to occur before we think an error occurred and exit.
   *
   * @param max_null_loops the maximum number of null loops
   */
  public void setMaxNullLoops(int max_null_loops) {
    this.max_null_loops_ = max_null_loops;
  }

  /**
   * Enables the specified port as the debug port, an alternate
   * means of making requests to the server.
   *
   * @param debug_port the port to listen on
   */
  public void enableDebugPort(int debug_port) {
    if (debug_port_enabled_) {
      return;
    } else {
      this.debug_port_ = debug_port;
      selector_.wakeup();
    }
  }

  /**
   * Returns whether the specified port is the debug port.
   * This is used to determine whether an incoming request
   * should be run using the dispatch queue or specially,
   * via a dedicated thread.
   *
   * @param port
   * @return <code>true</code> if the specified port is the
   *         debug port
   */
  public boolean isDebugPort(int port) {
    return (port == debug_port_);
  }

  /**
   * Close the underlying selector, causing the thread in
   * {@link #runForever()} to exit.
   *
   * @param wait <code>true</code> to signal the select loop to continue
   *        processing until all keys in the selector are removed.
   */
  public void quit(boolean wait) throws IOException {
    quit_ = true;
    // if network_thread_ is null, that means run() was never called
    // we should just close the selector.
    if (network_thread_ == null) {
      close();
    } else {
      if (wait) {
        selector_.wakeup();
      } else {
        // interrupt network thread to avoid deadlock on the
        // selector's monitor with select() vs. close()
        network_thread_.interrupt();
        close();
      }
    }
  }

  /**
   * Close the selector and all registered channels. Closing the channels
   * manually became necessary with JDK1.5.
   */
  private void close() {
    // Close all registered connections.
    // We need to make our own copy to avoid concurrent modification exceptions.
    NetConnection[] empty = {};
    for (NetConnection connection : connections_.toArray(empty)) {
      connection.close();
    }

    // Close the selector itself.
    try {
      selector_.close();
    } catch (IOException ignored) {
      // We don't mind if it was already closed.
    }
  }

  public boolean isOpen() {
    return selector_.isOpen();
  }

  /**
   * Returns the first port value registered via a call to listen()
   */
  public int getPort() {
    return first_port_;
  }

  /**
   * Close all of the server channel(s). This has the effect of
   * dropping queued connect requests and refusing all further
   * requests.
   *
   * @exception java.io.IOException is thrown on an error closing the server
   *            socket channel(s).
   */
  public void drain() throws IOException {
    for (ServerSocketChannel ssc : server_channels_) {
      // we don't want to close the debug port yet, it may be useful
      ServerSocket socket = ssc.socket();
      if (socket != null && !isDebugPort(socket.getLocalPort())) {
        ssc.close();
      }
    }
  }

  /**
   * Creates a socket which will listen on <code>port</code> for new
   * connections. Whenever a new connection is made to the port,
   * <code>callback.handleEvent</code> will be called.
   *
   * @param port port to bind to, -1 for GSE to pick a free port for you
   * @param callback
   * @param secure
   */
  public void listen(int port, NetSelectionCallback callback, boolean secure)
    throws IOException {
    ServerSocketChannel ssc = ServerSocketChannel.open();
    ssc.configureBlocking(false);
    ssc.socket().setReuseAddress(true);
    port = verboseBind(ssc, port);
    ssc.register(selector_, SelectionKey.OP_ACCEPT, callback);
    server_channels_.add(ssc);
    if (secure) {
      secure_ports_.add(new Integer(port));
    }
    if (first_port_ == 0) {
      first_port_ = port;
      callback_ = callback;
    }
    logger_.info("listening on " + port +
                 (secure ? " (secure)" : ""));
  }

  private int verboseBind(ServerSocketChannel ssc, int port)
      throws IOException {
    try {
      ServerSocket socket = ssc.socket();
      if (port == -1) {
        // Bind to server-chosen port.
        socket.bind(null);
        return socket.getLocalPort();
      } else {
        // Bind to user-chosen port.
        ssc.socket().bind(new InetSocketAddress(port), 1024);
        return port;
      }
    } catch (SocketException e) {
      /*
       * SocketException does not tell you what port was already in use,
       * so this is an attempt to give a little more helpful info.
       */
      if (e.getMessage().equals(ADDRESS_ALREADY_IN_USE)) {
        SocketException newException =
            new SocketException(ADDRESS_ALREADY_IN_USE
            + " (port " + port + ")");
        newException.setStackTrace(e.getStackTrace());
        throw newException;
      }
      throw e;
    }
  }

  /**
   * Creates a socket which will listen on an unused port between
   * <code>port_lo</code> and <code>port_hi</code>.
   *
   * @param lowPortInclusive
   * @param highPortExclusive
   * @param callback
   * @param secure
   */
  public int listen(int lowPortInclusive, int highPortExclusive,
                    NetSelectionCallback callback,
                    boolean secure) {
    Random r = new Random(); //TODO: fix. see Still More Java Puzzlers. 13.

    for (;;) {
      int port = lowPortInclusive
          + r.nextInt(highPortExclusive - lowPortInclusive);

      try {
        listen(port, callback, secure);
        return port;
      } catch (IOException e) {
      }
    }
  }

  /**
   * Creates a socket which will listen on <code>port</code> for new
   * connections. Whenever a new connection is made to the port,
   * <code>callback.handleNewConnection</code> will be called.
   *
   * @param port
   * @param callback
   * @param secure
   */
  public void listen(int port, AcceptCallback callback, boolean secure)
    throws IOException {
    listen(port, new AcceptServer(callback), secure);
  }

  /**
   * Creates a socket which will listen on an unused port between
   * <code>port_lo</code> and <code>port_hi</code>.
   *
   * @param lowPortInclusive
   * @param highPortExclusive
   * @param callback
   * @param secure
   */
  public int listen(int lowPortInclusive, int highPortExclusive,
                    AcceptCallback callback,
                    boolean secure) {
    return listen(
        lowPortInclusive, highPortExclusive,
        new AcceptServer(callback), secure);
  }

  /**
   * Passthrough function which calls <code>channel.register</code>
   * with the provide <code>selector</code> class as the first
   * argument. The <code>callback</code> argument is set as the
   * attachment object on the underlying <code>SelectionKey</code> and
   * will be activated whenever any of the specified <code>ops</code>
   * occur on <code>channel</code>.
   *
   * This method should only be called by the network thread.
   *
   * @param channel
   * @param ops
   * @param callback
   */
  public SelectionKey register(SocketChannel channel, int ops,
                               NetSelectionCallback callback)
    throws IOException {
    if (!isNetworkThread()) {
      throw new IllegalStateException();
    }
    return channel.register(selector_, ops, callback);
  }

  /**
   * Schedules a timer to fire in a specified number of
   * milliseconds. When the timer fires,
   * <code>callback.handleTimerFired</code> will be called. The timer
   * can be cancelled by calling <code>NetSelector.cancel</code> and
   * passing in the returned <code>Object</code> handle.
   *
   * @param callback
   * @param delay
   */
  public Object schedule(NetTimerCallback callback, long delay) {
    NetTimer t = new NetTimer(callback, delay + System.currentTimeMillis(),
                              getNextTimerId());
    timers_.add(t);
    return t;
  }

  private synchronized long getNextTimerId() {
    return next_timer_id_++;
  }

  /**
   * Accepts a new socket connection from a system outside of GSE. Some bytes
   * may have been read from the given socket already. Using this method, a
   * caller can read some bytes from the socket and decide that it cannot
   * process the request and pass the socket to GSE. We will process the bytes
   * already read by the caller first before processing bytes from the given
   * socket.
   *
   * <p>Any thread can call this method.
   *
   * @param channel the new socket channel. Cannot be null.
   * @param bytesAlreadyRead bytes that have previously been read from channel
   * already. The ownership of this array is passed to GSE, so the caller must
   * not modify the array after calling this method. Cannot be null. Can be an
   * empty array.
   * @return true if the socket is successfully accepted by the callee.
   * Otherwise, return false, in which case the caller must continue to process
   * the socket.
   */
  public boolean acceptNewConnectionWithBytesRead(final SocketChannel channel,
      final byte[] bytesAlreadyRead) {
    if (channel == null) {
      throw new NullPointerException("channel cannot be null");
    }
    if (bytesAlreadyRead == null) {
      throw new NullPointerException("bytesAlreadyRead cannot be null");
    }
    if (!(callback_ instanceof AcceptServer)) {
      return false;
    }
    schedule(new NetTimerCallback() {
        public void handleTimerFired() {
          ((AcceptServer) callback_).handleNewConnectionWithBytesRead(
              channel, bytesAlreadyRead);
        }
      }, 0);
    selector_.wakeup();
    return true;
  }

  /**
   * Sets the provided selection key's interest ops to be updated
   * by the network thread. This method can be safely called from
   * any thread, including the network thread and will not block.
   * It adds the key and specified ops to a map and invokes
   * the selector's wakeup() method. The network thread, in the
   * main select loop, processes the contents of this map.
   * This is necessary to avoid blocking calls to interestOps().
   *
   * If the calling thread is the network thread, the selection
   * key's interest ops are updated immediately instead of being
   * added to the key_ops_ map.
   *
   * @param key the selection key
   * @param ops the desired operation bitmask
   * @exception java.io.IOException is thrown if the key is not valid
   */
  public void interestOps(SelectionKey key, int ops) throws IOException {
    if (isNetworkThread()) {
      key_ops_.remove(key);
      if (!key.isValid()) {
        throw new IOException("selection key invalid; cannot set interest ops");
      } else {
        key.interestOps(ops);
      }
    } else {
      key_ops_.put(key, Integer.valueOf(ops));
      selector_.wakeup();
    }
  }

  /**
   * Cancel an existing timer object as returned from
   * <code>NetSelector.schedule</code>. Multiple cancels of a timer
   * work fine.
   *
   * @param timer
   */
  public void cancel(Object timer) {
    NetTimer t = (NetTimer) timer;
    timers_.remove(t);
    t.callback_ = null;
  }

  /**
   * Returns <code>true</code> if the specified channel was opened on
   * a port designated as secure by a call to one of this class' listen
   * methods.
   *
   * @return <code>true</code> if the channel is over a port designated
   *         as secure.
   */
  public boolean isSecure(SocketChannel channel) {
    int port = channel.socket().getLocalPort();
    return secure_ports_.contains(Integer.valueOf(port));
  }

  /**
   * Stops accepting incoming connections until {@link #startAccepting()}
   * is called. Loops through the server socket channels and unselects
   * OP_ACCEPT interest.
   */
  public void stopAccepting() {
    logger_.warning("maximum request limit (" + max_requests_ + ") " +
                    "exceeded; new connections will block");
    accepting_ = false;
    for (ServerSocketChannel ssc : server_channels_) {
      SelectionKey key = ssc.keyFor(selector_);
      key.interestOps(0);
    }
  }

  /**
   * Continues accepting connections, by selecting OP_ACCEPT
   * interest on the selection key.
   */
  public void startAccepting() {
    logger_.warning("request queue leveled; re-accepting new connections");
    accepting_ = true;
    for (ServerSocketChannel ssc : server_channels_) {
      SelectionKey key = ssc.keyFor(selector_);
      key.interestOps(SelectionKey.OP_ACCEPT);
    }
  }

  public boolean isAccepting() {
    return accepting_;
  }

  /**
   * Add the connection object to a set of connections
   */
  public void addConnection(NetConnection nc) {
    connections_.add(nc);
  }

  /**
   * Remove the connection object from the set of connections
   */
  public void removeConnection(NetConnection nc) {
    connections_.remove(nc);
  }

  /**
   * Return the list of connection objects
   */
  public Set<NetConnection> getConnections() {
    return connections_;
  }

  /**
   * Return the number of connections
   */
  public int getNumConns() {
    return connections_.size();
  }

  /**
   * Return the maximum connections
   */
  public int getMaxConns() {
    return max_requests_;
  }

  /**
   * Since {@link #toString()} can only be called by the I/O
   * thread (the select loop locks on the selector, so it cannot
   * be queried for status), call this method to request status
   * for the net selector be logged.
   */
  public void logStatus() {
    log_status_ = true;
    selector_.wakeup();
  }

  @Override
  public String toString() {
    if (!isNetworkThread() || null == selector_ || !selector_.isOpen()) {
      return super.toString();
    }
    return toDebugString();
  }

  /**
   * Returns the status of the net selector as a string.
   * This method should only be called by the I/O thread.
   */
  private String toDebugString() {
    if (!isNetworkThread()) {
      throw new IllegalStateException();
    }
    StringBuilder buf = new StringBuilder();
    buf.append(selector_.keys().size()).append(" selection keys:\n");
    for (SelectionKey key : selector_.keys()) {
      int ready = 0x0;
      int interest = 0x0;
      if (key.isValid()) {
        ready = key.readyOps();
        interest = key.interestOps();
      }
      Object attachment = key.attachment();
      if (attachment instanceof NetConnection) {
        NetConnection conn = (NetConnection) attachment;
        buf.append("  key for connection ").append(conn).
            append(" is valid: ").append(key.isValid()).
            append(", ready to write: ").
            append((ready & SelectionKey.OP_WRITE) != 0).
            append(":").
            append((interest & SelectionKey.OP_WRITE) != 0).
            append(", ready to read: ").
            append((ready & SelectionKey.OP_READ) != 0).
            append(":").
            append((interest & SelectionKey.OP_READ) != 0).
            append(", ready to connect: ").
            append((ready & SelectionKey.OP_CONNECT) != 0).
            append(":").
            append((interest & SelectionKey.OP_CONNECT) != 0).
            append("\n");
      } else {
        buf.append("  key for server socket").
            append(" is valid: ").append(key.isValid()).
            append(", ready to accept: ").
            append((ready & SelectionKey.OP_ACCEPT) != 0).
            append(":").
            append((interest & SelectionKey.OP_ACCEPT) != 0).
            append("\n");
      }
    }

    return buf.toString();
  }

  /**
   * Runs the event loop. The event loop processes timers and network
   * events. The loop might exit if a runtime exception is thrown, or
   * if an IOException is thrown from <code>Selector.select()</code>.
   *
   * @see NetSelector#runForever
   */
  public void run() throws IOException {
    // Set the identity of the "network" thread (for use with interestOps)
    network_thread_ = Thread.currentThread();

    int null_loop_count = 0;

    while (true) {
      // Selector must be open
      if (!selector_.isOpen()) {
        throw new ClosedSelectorException();
      }

      // Handle any keys which need updated interest ops
      int async_ops_count = 0;
      int async_ops_valid = 0;
      synchronized (key_ops_) {
        for (SelectionKey key : key_ops_.keySet()) {
          async_ops_count += 1;

          if (key.isValid()) {
            int ops = key_ops_.get(key).intValue();
            key.interestOps(ops);
            async_ops_valid += 1;
          }
        }
        key_ops_.clear();
      }

      // If a debug port is specified, but not enabled, enable it
      if (debug_port_ != 0 && !debug_port_enabled_) {
        listen(debug_port_, callback_, false);
        debug_port_enabled_ = true;
      }
      // If a log status request was made, satisfy it
      if (log_status_) {
        log_status_ = false;
        logger_.log(Level.INFO, "net selector status", this);
      }

      /* Handle any pending network events. We handle existing network
       * events before selecting for additional events so that
       * runForever() works properly in the face of a runtime
       * exception being thrown from within this code.
       */
      int sel_keys_count = 0;
      int sel_keys_valid = 0;
      int sel_keys_ready = 0;
      for (Iterator<SelectionKey> i = selector_.selectedKeys().iterator();
           i.hasNext(); ) {
        SelectionKey key = i.next();
        i.remove();
        sel_keys_count += 1;

        if (key.isValid()) {
          NetSelectionCallback cb =
            (NetSelectionCallback) key.attachment();

          sel_keys_valid += 1;
          if (key.readyOps() != 0) {
            sel_keys_ready += 1;
          }

          cb.handleEvent(key);
        }
      }

      // Determine whether to stop accepting connections (max capacity)
      // or to start re-accepting connections
      if (accepting_ && getNumConns() >= max_requests_) {
        stopAccepting();
      } else if (!accepting_ && getNumConns() < (max_requests_ / 2)) {
        startAccepting();
      }

      // Handle any timers that have expired, and determine when the
      // next timer will fire.
      long delay = 0;
      long now = System.currentTimeMillis();
      int timers_fired = 0;

      // Scoped to ensure the removedTimers isn't used outside this block
      {
        // do NOT change the following line to Lists.newLinkedList()
        LinkedList<NetTimer> removedTimers = new LinkedList<NetTimer>();
        synchronized (timers_) {
          for (Iterator<NetTimer> i = timers_.iterator(); i.hasNext(); ) {
            final NetTimer t = i.next();
            if (now < t.timeout_) {
              delay = t.timeout_ - now;
              break;
            }
            i.remove();
            removedTimers.offer(t);
            timers_fired += 1;
          }
        }

        /*
         * Originally used:
         * t.callback_.handleTimerFired(); within the loop.
         * This meant that the callback ran in the main thread while
         * the timers_ was locked. Under some extreme situations this
         * could result in a deadlock.
         *
         * This is now changed where each of the timers is removed from the
         * timers_ and then the callback fired outside the synchronized
         * block. This will avoid the deadlock.
         */
        for (NetTimer t : removedTimers) {
          t.callback_.handleTimerFired();
        }
      }

      // if there are no timers and no keys, exit the loop
      if (timers_.isEmpty() && selector_.keys().isEmpty()) {
        break;
      }

      // Don't let the select go on indefinitely in the case of a quit
      if (quit_) {
        delay = 10;
      }

      // TODO(pmattis): We can remove the null loop tests sometime in
      // the future when we're confident the null loop bug is gone.
      int work_count = async_ops_count + sel_keys_count + timers_fired;
      if (work_count == 0) {
        null_loop_count += 1;
        if (null_loop_count >= max_null_loops_) {
          /* If we have more than max_null_loops_ (default 100) we'll
           * log a fatal error and exit. This is both a workaround for
           * a NetSelector infinite loop bug and an attempt to get
           * some useful debugging information when it happens.
           */
          logger_.log(Level.SEVERE, "Looped " + null_loop_count +
              " times without doing any work!!!\n" +
              "This is a NetSelector bug. I'm intentionally exiting the " +
              "process instead of letting this looping continue forever. " +
              "Please use the --maxnullloops flag if you want to adjust " +
              "how many such loops can occur before exiting.\n"
              + this);
          throw new Error("Null loop exception");
        }
      } else {
        null_loop_count = 0;
      }

      if (delay == 0) {
        /* Due to a bug in some Linux kernels, Selector.select(0) will not
         * block if the JVM is using the epoll implementation of Selector
         * and the machine has been up for more than 248 days. A large timeout
         * value is used instead of zero to protect against this bug.
         *
         * The epoll implementation of Selector is supported in JDK 1.6. Epoll
         * is used automatically on 2.6 kernels or can be forced on 2.4
         * kernels with the command line flag
         * -Djava.nio.channels.spi.SelectorProvider=
         *                                   sun.nio.ch.EPollSelectorProvider.
         *
         * If Selector.select(0) is called, then the kernel eventpoll code
         * incorrectly uses 248 days as the time to return from the
         * call (248 days = 1 << 31 jiffies).  If the machine has been up
         * for more than 248 days, then the call returns immediately.
         */
        delay = 1000L * 60 * 60 * 24; // one day
      }

      // Wait for the next timer to fire, or a network event to occur.
      // If you need the logger output below, use these 3 lines below and
      // comment out the single "select" call below.
      //now = System.currentTimeMillis();
      //int count = selector_.select(delay);
      //long elapsed = System.currentTimeMillis() - now;

      selector_.select(delay);

      // Check if the network thread has been interrupted; if so, clear
      if (Thread.interrupted()) {
        logger_.log(Level.WARNING, "I/O thread interrupted", this);
      }

      /* Computing this string and passing it through the logging system
       * is a hotspot on the performance of some servers.  Leaving it here
       * for ease of restoration if we ever need it in the future.
       *
       * Debug for select loop:
       */

//      logger_.log(Level.FINE, "select loop" +
//          ": count=" + count +
//          ", open=" + selector_.isOpen() +
//          ", async_ops=" + key_ops_.size() +
//          " (" + async_ops_count + "," + async_ops_valid +
//          "), sel_keys=" + selector_.selectedKeys().size() +
//          " (" + sel_keys_count + "," + sel_keys_valid + ","
//          + sel_keys_ready +
//          "), conns=" + getNumConns() +
//          ", timers=" + timers_.size() + " (" + timers_fired +
//          "), delay=" + delay +
//          ", elapsed=" + elapsed +
//          ", work=" + work_count);
    }
  }

  /**
   * Runs the event loop and catches any thrown runtime exception and
   * logs it.
   * This routine will exit if the selector is closed during
   * its operation.
   */
  public void runForever() {
    // set the I/O thread's priority to max
    Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
    // loop
    while (!(quit_ && selector_.keys().isEmpty())) {
      try {
        run();
      } catch (ClosedSelectorException cse) {
        // time to exit...all keys with sockets are already closed
        break;
      } catch (Exception e) {
        logger_.log(Level.SEVERE, "select loop exception", e);
      }
    }
    if (quit_ && selector_.isOpen()) {
      close();
    }
  }
}