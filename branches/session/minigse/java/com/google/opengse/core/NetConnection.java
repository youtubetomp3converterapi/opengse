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

import com.google.opengse.iobuffer.IOBuffer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A <code>NetConnection</code> object encapsulates a single network
 * connection. The connection provides methods for reading and writing
 * data.
 *
 * @author Peter Mattis
 */
public class NetConnection
    implements NetSelectionCallback, NetTimerCallback {
  private static final Logger LOGGER =
    Logger.getLogger("com.google.opengse.NetConnection");

  // ThreadLocal temporary direct byte buffers for avoiding a bug in
  // jdk1.4.2 where SocketChannel.read() calls fail due to being
  // unable to allocate a direct byte buffer internally for reading
  // into.
  private static ThreadLocal<ByteBuffer> tmpBuffers =
    new ThreadLocal<ByteBuffer>() {
      @Override
      protected synchronized ByteBuffer initialValue() {
        return ByteBuffer.allocateDirect(16 * 1024);
      }
    };

  private NetConnectionCallback cb_;
  private NetSelector selector_;
  private SocketChannel channel_;
  private SelectionKey key_ = null;
  private Object timer_ = null;
  private IOBuffer input_buf_ = new IOBuffer();
  private IOBuffer output_buf_ = new IOBuffer();
  private long start_;
  private long bytes_read_ = 0;
  private long bytes_written_ = 0;
  private Exception cause_ = null;
  private Listener listener_ = null;

  /**
   * The Listener allows a client to listen to data passed between
   * the client and server.
   */
  public static interface Listener {
    /** called when data is read from the connection */
    void readData(ByteBuffer data);

    /** called when data is written out to the connection */
    void writeData(ByteBuffer data);

    /** called when the connection is closed */
    void close();
  }

  public NetConnection(NetConnectionCallback cb, NetSelector selector,
                       SocketChannel channel) {
    super();
    this.cb_ = cb;
    this.selector_ = selector;
    this.channel_ = channel;
    this.start_ = System.currentTimeMillis();
    selector_.addConnection(this);
    LOGGER.log(Level.FINE, "new connection", this);
  }

  public long getStart() {
    return start_;
  }
  public boolean isDebugPort() {
    return selector_.isDebugPort(channel_.socket().getLocalPort());
  }
  public NetSelector getSelector() {
    return selector_;
  }
  public void setCallback(NetConnectionCallback cb) {
    this.cb_ = cb;
  }

  /**
   * Sets a listener on the connection.
   * Currently this only supports one listener. Calling this with two
   * different listeners will throw an assertion error.
   * TODO: allow arbitrary number of listeners if necessary.
   */
  public void setListener(Listener listener) {
    if (this.listener_ != null && listener != null) {
      throw new IllegalArgumentException("May register only one listener");
    }
    this.listener_ = listener;
  }
  public SocketChannel getChannel() {
    return channel_;
  }
  public Socket getSocket() {
    return channel_.socket();
  }
  public SelectionKey getSelectionKey() {
    return key_;
  }
  public boolean getSecure() {
    return selector_.isSecure(channel_);
  }
  public IOBuffer getInputBuffer() {
    return input_buf_;
  }
  public IOBuffer getOutputBuffer() {
    return output_buf_;
  }
  public void setOutputBuffer(IOBuffer buf) {
    if (buf == null) { throw new NullPointerException(); }
    output_buf_ = buf;
  }
  public Exception getCauseException() {
    return cause_;
  }
  public boolean isOpen() {
    return channel_.isOpen();
  }

  public void close() {
    LOGGER.fine("closing connection " + this +
                 ", wrote " + bytes_written_ + " bytes");

    selector_.removeConnection(this);

    stopTimer();

    try {
      channel_.close();
    } catch (IOException e) {
      cause_ = e;
      LOGGER.log(Level.WARNING, "caught an exception", e);
    }

    if (listener_ != null) {
      listener_.close();
    }

    if (cb_ != null) {
      cb_.handleClose(this);
      cb_ = null;
    }
  }

  public void wakeup() {
    key_.selector().wakeup();
  }

  public void startTimer(long timeout) {
    stopTimer();
    timer_ = selector_.schedule(this, timeout);
  }

  public void stopTimer() {
    if (timer_ != null) {
      selector_.cancel(timer_);
      timer_ = null;
    }
  }

  private void interestOps(int ops) throws IOException {
    if (selector_.isOpen()) {
      if (ops == 0) {
        /* NOTE: Calling interestOps(0) is disallowed because doing so
         * can lead to a cpu-chewing loop in NetSelector.run() if the
         * connection is closed. Specifically, if the client closes a
         * connection for which we have interestOps(0) set,
         * java.nio.channels.Selector.select() will immediately return
         * 0 upon being called, yet not reveal the reason it didn't
         * block. This behavior continues until the closed connection
         * is removed from the select poll array. Unfortunately there
         * is not a good way to determine if the connection is closed
         * by simply looking at the keys we are selecting on. The only
         * way to determine the connection is closed is to try reading
         * or writing to it.
         */
        throw new IllegalArgumentException("ops == 0 is invalid");
      }

      try {
        if (key_ == null) {
          key_ = selector_.register(channel_, ops, this);
        } else {
          selector_.interestOps(key_, ops);
        }
      } catch (IOException e) {
        LOGGER.log(Level.FINEST, "IO exception on interestOps", e);
        close();
        cause_ = e;
      }
    }
  }

  public void interestRead() throws IOException {
    interestOps(SelectionKey.OP_READ);
  }

  public void interestReadWrite() throws IOException {
    interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
  }

  public void interestConnect() throws IOException {
    interestOps(SelectionKey.OP_CONNECT);
  }

  public void handleEvent(SelectionKey key) {
    if (this.key_ != key) {
      throw new IllegalArgumentException();
    }

    if (cb_ == null) {
      close();
      return;
    }

    try {
      if (key.isConnectable()) {
        cb_.handleConnect(this);
      }
      if (key.isReadable()) {
        cb_.handleRead(this);
      }
      if (key.isValid() && key.isWritable()) {
        cb_.handleWrite(this);
      }
    } catch (IOException e) {
      LOGGER.log(Level.FINEST, "caught an IO exception", e);
      close();
      cause_ = e;
    } catch (Exception e) {
      LOGGER.log(Level.WARNING, "caught an exception", e);
      close();
      cause_ = e;
    }
  }

  public void handleTimerFired() {
    // A timeout fired. Terminate the connection.
    LOGGER.info("I/O timeout; connection " + this + " terminated");
    timer_ = null;
    close();
    cause_ = new IOException("I/O timeout; connection terminated");
  }

  public int doRead() throws IOException {
    // This is more complicated than it should be. IOBuffers use
    // non-direct byte buffers so that we can get access to the
    // underlying array using ByteBuffer.array(). But it looks like
    // SocketChannel.read() internally always needs to read into a
    // direct byte buffer and in jdk1.4.2 it has a bug and is
    // sometimes unable to allocate the direct byte buffer. So we ease
    // it's burden and keep a pool of temporary buffers which we read
    // into and then copy from the temporary buffer into the actual
    // write buffer for the IOBuffer. Since our temporary buffers are
    // thread local and we never delete them or allocate a huge number
    // of them, we should be safe from memory leaks. See
    // http://developer.java.sun.com/developer/bugParade/bugs/4884817.html
    // for more information on the jdk1.4.2 bug.

    ByteBuffer tmpBuf = tmpBuffers.get();
    ByteBuffer buf = input_buf_.getWriteBuffer();

    tmpBuf.clear();
    if (tmpBuf.limit() > buf.remaining()) {
      tmpBuf.limit(buf.remaining());
    }

    int count = channel_.read(tmpBuf);
    if (count > 0) {
      bytes_read_ += count;
      tmpBuf.flip();

      if (listener_ != null) {
        listener_.readData(tmpBuf.asReadOnlyBuffer());
      }

      buf.put(tmpBuf);
    }

    return count;
  }

  public int doWrite() throws IOException {
    ByteBuffer tmpBuffer = tmpBuffers.get();
    int count = 0;

    for (;;) {
      ByteBuffer buf = output_buf_.getReadBuffer();
      if (buf == null || !buf.hasRemaining()) {
        break;
      }

      /* This is ugly. As described above in doRead() we need to use a
       * temporary direct byte buffer. But we have the added
       * complication that we might not be able to write out all the
       * data and therefore have to "back up".
       */
      tmpBuffer.clear();

      int ntodo = buf.remaining();
      if (ntodo > tmpBuffer.limit()) {
        ntodo = tmpBuffer.limit();
      }

      /* Save away the original position and limit because a) we're
       * going to change the limit to account for how much we can copy
       * into the temporary buffer and b) we're going to change the
       * position in doing the copy. We're going to use these saved
       * values to correctly adjust the position based on how much we
       * write out to the socket.
       */
      int origPos = buf.position();
      int origLimit = buf.limit();
      buf.limit(origPos + ntodo);
      tmpBuffer.put(buf);
      tmpBuffer.flip();

      /* Make a read-only copy of buffer for the listener, if necessary,
       * before writing out to the channel.
       */
      ByteBuffer listenerCopy = null;
      if (listener_ != null) {
        listenerCopy = tmpBuffer.asReadOnlyBuffer();
      }

      int nbytes = channel_.write(tmpBuffer);
      int ndone = nbytes > 0 ? nbytes : 0;

      if (listener_ != null) {
        listenerCopy.limit(listenerCopy.position() + ndone);
        listener_.writeData(listenerCopy);
      }

      // Reset the original limit the buffer had
      buf.limit(origLimit);
      // Set the position based on how much we actually wrote out to
      // the socket.
      buf.position(origPos + ndone);

      // Release the read buffer if there is no more data in it.
      output_buf_.releaseReadBuffer();

      if (nbytes < ntodo) {
        break;
      }
      count += nbytes;
    }

    bytes_written_ += count;
    return count;
  }

  @Override
  public String toString() {
    if (null == channel_) {
      return super.toString();
    }
    Socket s = channel_.socket();
    if (null == s) {
      return super.toString();
    }
    InetAddress addr = s.getInetAddress();
    String remote = (addr == null) ? "not connected" : addr.toString();

    return remote + ":" + s.getLocalPort() + ":" + s.getPort();
  }

  /**
   * Returns an HTML formatted string containing information about
   * the connection. The HTML is formatted as a single row with the
   * following information: connection time (ms), bytes read, bytes
   * written, and (if the callback is not null) the result of the
   * callback's getHtmlStatus() method.
   *
   * @return an HTML table row of status information
   */
  public String getHtmlStatus() {
    StringBuffer buf = new StringBuffer();
    buf.append("<tr bgcolor=#fff5ee>");
    buf.append("<td align=right valign=top>");
    buf.append((System.currentTimeMillis() - start_));
    buf.append("</td>");
    buf.append("<td align=right valign=top>");
    buf.append(getSecure());
    buf.append("</td>");
    buf.append("<td align=right valign=top>");
    buf.append(bytes_read_);
    buf.append("</td>");
    buf.append("<td align=right valign=top>");
    buf.append(bytes_written_);
    buf.append("</td>");
    buf.append("<td valign=top>");
    if (cb_ != null) {
      buf.append(cb_.getHtmlStatus(this));
    } else {
      buf.append("connection detached");
    }
    buf.append("</td>");
    buf.append("</tr>");
    return buf.toString();
  }
}