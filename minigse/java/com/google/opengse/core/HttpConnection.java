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

import com.google.opengse.GSEConstants;
import com.google.opengse.HeaderUtil;
import com.google.opengse.HttpRequestHandler;
import com.google.opengse.HttpRequest;
import com.google.opengse.RequestUtils;
import com.google.opengse.ServletEngineConfiguration;
import com.google.opengse.iobuffer.IOBuffer;
import com.google.opengse.util.string.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An {@link HttpConnection} object encapsulates a single http connection.
 * The connection reads a request, dispatches it to a servlet and writes a
 * response. Keep-alive is supported and timeouts are provided for the various
 * stages of handling a request.
 *
 * An {@link HttpConnection} uses a {@link NetConnection} internally. This
 * composition pattern allows one immediately useful benefit: the underlying
 * {@link NetConnection} can be detached from the {@link HttpConnection}
 * (using {@link #detachNetConnection}). A future benefit is the ability to
 * replace the {@link NetConnection} with another class which obeys the
 * same interface.
 *
 * @author Peter Mattis
 */
final class HttpConnection
    implements NetConnectionCallback, Runnable {

  /**
   * The possible states of the connection.
   */
  private enum Status {
    IO_READ,
    IO_WRITE,
    QUEUED,
    PROCESSING,
    CLOSED
  };

  private static final Logger LOGGER =
      Logger.getLogger(HttpConnection.class.getName());

  /**
   * Exported variable counting chunked requests that have been seen.
   */
  public static int chunkedRequests = 0;

  /**
   * Exported variable counting chunked requests that were dechunked ok.
   */
  public static int chunkedRequestsCompleted = 0;

  /**
   * Exported variable counting gzipped requests that have been seen.
   */
  public static int gzippedRequests = 0;

  /**
   * Exported variable counting gzipped requests that have been decoded.
   */
  public static int gzippedRequestsCompleted = 0;

  /**
   * The HttpServer object that owns this connection. This variable is protected
   * to allow access within the package to avoid the raft of accessors that
   * would be required to access server-level information from requests and
   * responses.
   */
  protected HttpServer server_;

  /**
   * The underlying NetConnection object this connection uses to communicate
   * with the client.
   */
  private NetConnection conn_;

  /**
   * The servlet which is handling the current request. The HttpConnection has
   * no support for pipelining and executing multiple requests in parallel.
   */
  private HttpRequestHandler dispatcher;

  /**
   * This is the default (state-less) dispatcher from constructor. We keep a
   * reference here for processing multiple requests over the same connection,
   * while some requests may be handled by alternative "bad request handler"
   * dispatcher.
   */
  private final HttpRequestHandler webappDispatcher;

  /**
   * The current request. A new HttpRequest structure is allocated when we start
   * reading a new request.
   */
  private HttpRequestImpl req_;

  /**
   * The current response. A new HttpResponse structure is allocated when we
   * start responding to a request.
   */
  private HttpResponseImpl resp_;

  /**
   * A temporary buffer used for parsing incoming requests.
   */
  private final ByteArrayOutputStream line_buf_ = new ByteArrayOutputStream();

  /**
   * The current stage of processing requests.
   */
  private Status status_ = Status.IO_READ;

  /**
   * The number of requests processed by this connection.
   */
  private int num_reqs_ = 0;

  /**
   * The number of bytes read over the course of the current HTTP request.
   */
  private long num_request_bytes_ = 0;

  /**
   * The thread which is processing the current request. Only valid (non-null)
   * if status_ == Status.PROCESSING.
   */
  private Thread processing_thread_ = null;

  /**
   * The time (in milliseconds since the epoch) when we started processing the
   * current request. Only valid if status_ == Status.PROCESSING.
   */
  private long processing_start_ = 0;

  /**
   * A monitor used to synchronize communication between the I/O thread and a
   * worker thread blocking on a call to write().
   */
  private final Object wait_lock_ = new Object();

  /**
   * Indicates that the connection was closed by the client.
   */
  private boolean is_closed_ = false;

  /**
   * Indicates that an outstanding write has not finished sending via the I/O
   * thread.
   */
  private boolean write_in_flight_ = false;

  /**
   * Indicates that the thread was detached and that the caller is responsible
   * for calling reattachThread when they are ready to respond to the request.
   */
  private boolean thread_detached_ = false;

  /**
   * A boolean used to indicate if the call to runServlet is done. This is used
   * so that we only reattach threads after the initial call is finished.
   */
  private boolean run_servlet_finished_ = false;

  /**
   * Used as an event to signal that runServlet has finished and threads waiting
   * to reattach can proceed.
   */
  private final Object run_servlet_finished_lock_ = new Object();

  /**
   * Callback to invoke when an in-flight write completes. This is used by
   * asynchronous writers that do not want to block on the wait_lock_. Setting
   * this to a non-null value also makes calls to write non-blocking.
   */
  private Runnable flushNotifyCallback_ = null;

  private final ServletEngineConfiguration config;


  /**
   * Constructor for an {@link HttpConnection}. Creates a
   * {@link NetConnection} from the passed in {@link SocketChannel}
   * and indicates our interesting in reading from it.
   */
  HttpConnection(
      final HttpServer server,
      final SocketChannel channel,
      final HttpRequestHandler dispatcher) throws IOException {
    this.conn_ = new NetConnection(this, server.getSelector(), channel);
    this.server_ = server;
    this.dispatcher = dispatcher;
    this.webappDispatcher = this.dispatcher;
    conn_.startTimer(server.getStartupTimeout());
    conn_.interestRead();
    config = server.getConfiguration();
  }

  ServletEngineConfiguration getConfiguration() {
    return config;
  }

  /**
   * Detaches the internal {@link NetConnection} from this
   * {@link HttpConnection}. This is useful if you want to have more
   * explicit control over what gets sent back over the {@link NetConnection}
   * or want to interact with the {@link NetConnection} using asynchronous
   * IO.
   */
  public NetConnection detachNetConnection() {
    // Synchronized so that we don't have a race with getHtmlStatus()
    // and dereference a NULL pointer.
    synchronized (this) {
      NetConnection c = conn_;
      conn_ = null;
      c.setCallback(null);
      return conn_;
    }
  }

  public NetConnection getNetConnection() {
    return conn_;
  }

  public Socket getSocket() {
    if (conn_ == null) {
      return null;
    }
    return conn_.getSocket();
  }

  public boolean getSecure() {
    if (conn_ == null) {
      return false;
    }
    return conn_.getSecure();
  }

  public IOBuffer getInputBuffer() {
    return conn_.getInputBuffer();
  }

  public IOBuffer getOutputBuffer() {
    return conn_.getOutputBuffer();
  }

  public void setOutputBuffer(IOBuffer buf) {
    conn_.setOutputBuffer(buf);
  }

  public long getNumRequestBytes() {
    return num_request_bytes_;
  }

  /**
   * Used by {@link HttpConnection#reattachThread} to complete the asynchronous
   * request. Basically Runnable but with the ability to throw IOException.
   */
  public interface Reattachable {

    /**
     * Called by reattachThread to complete the request.
     */
    void reattach() throws IOException;
  }

  /**
   * Detaches the thread from this {@link HttpConnection}.
   *
   * <p>This is useful if you want to process the request using asynchronous
   * I/O, but still be able to use the {@link HttpServletRequest} and
   * {@link HttpResponseImpl} objects to process the request. It is only valid
   * to call this method during {@link javax.servlet.http.HttpServlet#service}
   * or one of its specialized methods (doGet, doPost, etc.,) or from inside
   * {@link #reattachThread(Reattachable)}. You can then return from that
   * method call and later respond to the request asynchronously.
   *
   * <p>After you call this method, you must later call {@link #reattachThread}
   * to complete the request. If {@link #reattachThread} is never called, some
   * resources will never be cleaned up.
   *
   * <p>If {@link javax.servlet.http.HttpServlet#service} throws an exception
   * after a call to this method, the detach is cancelled and the behavior is
   * the same as if this method was never called.
   */
  public void detachThread() {
    synchronized (this) {
      if (processing_thread_ == null) {
        throw new IllegalStateException("Called detachThread outside of " +
            "runServlet or reattachThread");
      }
      if (processing_thread_ != Thread.currentThread()) {
        throw new IllegalStateException("Called detachThread on the wrong " +
            "thread.");
      }
      thread_detached_ = true;
    }
  }

  /**
   * Reattaches the thread to this {@link HttpConnection}. This is used to
   * complete the request asynchronously after calling {@link #detachThread}.
   * This will throw an {@link java.io.IOException} if the request cannot be
   * completed.
   *
   * @param reattachable A callback that is called to finish processing the
   *                     request. This function should write the response back
   *                     to the HttpResponse object and then return. It's
   *                     basically the same behavior you would do if you were
   *                     synchronously sending the response during the {@link
   *                     javax.servlet.http.HttpServlet} call.
   *
   *                     If the Reattachable handler throws any exception, any
   *                     detach will be cancelled, the servlet logic will finish
   *                     the response, and an IOException will be thrown from
   *                     reattachThread back to the caller.
   */
  public void reattachThread(Reattachable reattachable) throws IOException {

    // this prevents another thread from calling reattachThread when
    // runServlet hasn't finished. The problematic scenario is that the servlet
    // detaches inside of RunServlet on thread 1 and then schedules a reattach
    // on thread 2. There's no way for the caller or thread 2 to know that
    // thread 1 has exited the RunServlet call. This lock ensures that it has.
    synchronized (run_servlet_finished_lock_) {
      if (!run_servlet_finished_) {
        try {
          LOGGER.log(Level.FINE, "Waiting for runServlet to finish");
          run_servlet_finished_lock_.wait();
        } catch (InterruptedException e) {
          // This will be dealt with below
        }
      }
    }

    synchronized (this) {
      if (!run_servlet_finished_) {
        LOGGER.info("RunServlet never completed");
        throw new IOException("RunServlet never completed");
      }
      if (thread_detached_) {
        thread_detached_ = false;
        processing_thread_ = Thread.currentThread();
      } else {
        throw new IOException("Attempting thread reattach when thread was " +
            "not detached");
      }
    }

    Throwable throwableCaught = null;
    try {
      try {
        try {
          reattachable.reattach();
        } catch (Exception e) {
          // if the caller throws an exception, the detach is cancelled
          synchronized (this) {
            thread_detached_ = false;
          }

//          e = handleException(e);
          if (e != null) {
            throw e; // rethrow; exception not handled
          }
        } finally {
          // If the servlet closes the output stream or output writer, the
          // final flush is sent asynchronously. We wait here if necessary
          // for the I/O thread to complete the write.
          waitForWriteInFlightToComplete();

          synchronized (this) {
            if (thread_detached_) {
              processing_thread_ = null;
            }
          }
        }
      } catch (RuntimeException e) {
        LOGGER.log(Level.WARNING, "caught a runtime exception: " + req_, e);
        throwableCaught = e;
      } catch (Error e) {
        LOGGER.log(Level.SEVERE, "caught an error: " + req_, e);
        throwableCaught = e;
      } catch (Throwable t) {
        LOGGER.log(Level.SEVERE, "caught a throwable: " + req_, t);
        throwableCaught = t;
      } finally {
        if (!thread_detached_) {
          finishRunServlet1();
        }
      }
      if (!thread_detached_) {
        finishRunServlet2();
      }
    } finally {
      if (!thread_detached_) {
        finishRunServlet3();
      }
    }

    if (throwableCaught != null) {
      // if there was a failure, we want to throw an Exception here because it
      // makes it easier for application code to handle these exception in its
      // call to reattachThread instead of in its Reattachable handler.
      IOException e = new IOException(throwableCaught.getMessage());
      e.initCause(throwableCaught);
      throw e;
    }
  }

  /**
   * Returns whether the servlet thread is currently detached via a call to
   * detachThread.
   */
  public synchronized boolean isThreadDetached() {
    return thread_detached_;
  }

  /**
   * The implementation of the {@link Runnable#run()} interface used
   * for worker threads to run servlets.
   */
  public void run() {
    if (dispatcher == null) {
      throw new IllegalStateException();
    }

    try {
      status_ = Status.PROCESSING;
      runServlet();
    } catch (Error e) {
      if (config.exitOnError()) {
        exitApplication(e);
      }
      LOGGER.log(Level.SEVERE, "caught an error", e);
      conn_.close();
    } catch (Throwable t) {
      LOGGER.log(Level.SEVERE, "caught a throwable", t);
      conn_.close();
    }
  }

  /**
   * Calls the {@link javax.servlet.Servlet#service} method of the servlet_ and returns the
   * result of a call to writeResponse. This method is called from the Thread
   * {@link #run} method if the servlet is non-blocking and from the {@link
   * #processRequest} method if the servlet is blocking.
   *
   * Attempt to fetch the status servlet and register the execution statistics
   * for the servlet.
   */
  private void runServlet() {
    run_servlet_finished_ = false;
    if (thread_detached_) {
      throw new IllegalStateException();
    }

    // This is a complicated try/catch/finally setup, but it has
    // a very specific goal: No matter what happens with the
    // servlet execution, logging, and stat collecting, the
    // connection must either be re-enabled for writing or closed.
    try {
      try {
        // Set processing status variables. We need to synchronize
        // this access since it may be happening on a servlet thread
        // (as opposed to the NetSelector thread) in which case the
        // StatusServlet might be accessing the variables from the
        // NetSelector thread.
        synchronized (this) {
          processing_thread_ = Thread.currentThread();
          processing_start_ = System.currentTimeMillis();
        }

        if (dispatcher != null) {
          // keep StaticFileServlet requests grouped as one URI

          LOGGER.log(Level.FINE, "servicing request via " +
              dispatcher.getClass().getName() + " servlet");
          LOGGER.log(Level.FINE,  req_.getRequestURI());

            dispatcher.handleRequest(req_, resp_);
            synchronized (this) {
              thread_detached_ = false;
            }
            // If the servlet closes the output stream or output writer, the
            // final flush is sent asynchronously. We wait here if necessary
            // for the I/O thread to complete the write.
            waitForWriteInFlightToComplete();

            // the caller gets the opportunity to detach the thread, in which
            // case we record that fact and unwind from these nested try blocks
            // without completing the request
            synchronized (this) {
              if (thread_detached_) {
                processing_thread_ = null;
              }
            }
        }
      } catch (Exception e) {
        LOGGER.log(Level.WARNING, "caught an exception: " + req_, e);
      } catch (Error e) {
        if (config.exitOnError()) {
          exitApplication(e);
        } else {
          LOGGER.log(Level.SEVERE, "caught an error: " + req_, e);
        }
      } catch (Throwable t) {
        // People should never extend directly from Throwable,
        // but we log this anyway.
        LOGGER.log(Level.SEVERE, "caught a throwable: " + req_, t);
      } finally {
        if (!thread_detached_) {
          finishRunServlet1();
        }
      }
      if (!thread_detached_) {
        finishRunServlet2();
      }
    } finally {

      if (!thread_detached_) {
        finishRunServlet3();
      }

      synchronized (run_servlet_finished_lock_) {
        run_servlet_finished_ = true;
        run_servlet_finished_lock_.notifyAll();
      }
    }
  }

  /**
   * Causes the currently running application to exit.
   */
  private void exitApplication(Error e) {
    try {
      LOGGER.log(Level.SEVERE, "shutting down", e);
      System.exit(-1);
    } finally {
      // Just in case logging or exiting throws another exception.
      Runtime.getRuntime().halt(-1);
    }
  }

  /**
   * Waits until a write in flight finishes. This blocks the thread until the
   * write is complete on the network thread.
   */
  private void waitForWriteInFlightToComplete() {
    synchronized (wait_lock_) {
      if (!isNonBlocking() && write_in_flight_ && !is_closed_) {
        try {
          wait_lock_.wait();
        } catch (InterruptedException e) {
          LOGGER.log(
              Level.WARNING, "synchronous write interrupted", e);
        }
      }
    }
  }

  /**
   * Called in the IO-thread to Wake the worker thread that is waiting for a
   * write to complete. If a flushNotifyCallback was registered, we also invoke
   * it here.
   */
  private void notifyWriteInFlightComplete() {
    synchronized (wait_lock_) {
      wait_lock_.notify();
    }
    if (flushNotifyCallback_ != null) {
      flushNotifyCallback_.run();
    }
  }

  /**
   * Register a Runnable to be run when the NetConnection finishes writing the
   * contents of the its IOBuffer to the socket or the socket is closed. The
   * callback will be invoked in the IO-thread, so the callback cannot block or
   * be computationally expensive.
   *
   * Registering a callback also makes {@link write} non-blocking, so the
   * caller is responsible for ensuring that {@link write} will not be
   * called again until after the registered callback has been invoked.
   *
   * @param cb the callback to run in the IO-thread when the IOBuffer has been
   *           written to the socket or an error occurred. Use null to disable
   *           the callback and restore normal blocking behavior.
   */
  public void setNonblocking(Runnable cb) {
    synchronized (wait_lock_) {
      flushNotifyCallback_ = cb;
    }
  }

  /**
   * Tests for the current blocking/non-blocking behavior of
   * {@link #write}.
   *
   * @return true if calls will not wait for a write to complete. false if calls
   *         will wait for a write to complete.
   */
  public boolean isNonBlocking() {
    synchronized (wait_lock_) {
      return flushNotifyCallback_ != null;
    }
  }

  /**
   * Bad name. Basically undoes the first part of the try-catch-finally logic in
   * runServlet. Can be used synchronously or asynchronously.
   *
   * This code was refactored out of runSerlvet when implementing detachThread,
   * in such a way as to allow sharing of the code without affecting existing
   * behavior (thus the obscure naming).
   */
  private void finishRunServlet1() {
    // Clear processing status variables. See comments above for
    // why this needs to be synchronized.
    synchronized (this) {
      status_ = Status.IO_WRITE;
      processing_thread_ = null;
    }
  }

  /**
   * Bad name. Basically finishes the second piece of logic in runServlet. Can
   * be used synchronously or asynchronously
   *
   * This code was refactored out of runSerlvet when implementing detachThread,
   * in such a way as to allow sharing of the code without affecting existing
   * behavior (thus the obscure naming).
   */
  private void finishRunServlet2() {
  // finish the response as the response status and length need to be finalized
    if (conn_ != null) {
      resp_.finish(server_.getKeepaliveTimeout() > 0);
    }
  }

  /**
   * Bad name. Basically finishes the third piece of logic in runServlet. Can be
   * used synchronously or asynchronously.
   *
   * This code was refactored out of runSerlvet when implementing detachThread,
   * in such a way as to allow sharing of the code without affecting existing
   * behavior (thus the obscure naming).
   */
  private void finishRunServlet3() {
    try {
      if (conn_ != null && conn_.isOpen()) {
        conn_.startTimer(server_.getWriteTimeout());
        conn_.interestReadWrite();
      }
    } catch (IOException e) {
      LOGGER.log(Level.WARNING, "unable to write response", e);
      conn_.close();
    }
  }



  /**
   * Process a request. The appropriate servlet is retrieved based on the
   * incoming request and it is run either by placing it on the dispatch queue
   * or running to completion immediately if no servlet has been specified
   * (servlet_ == null). The request must be fully read before calling this
   * function. If the request contains a content- length header, the request
   * body will have been read.
   */
  private void processRequest() throws IOException {
    conn_.stopTimer();
    conn_.interestRead();

    // We set status_ to Status.PROCESSING here so that if another
    // request arrives before the thread processing our request is run
    // we won't attempt to process it.
    status_ = Status.PROCESSING;

    dispatcher = webappDispatcher;

    // if the servlet is null, runServlet will clean up the connection
    if (dispatcher == null) {
      runServlet();
    } else if (conn_.isDebugPort()) {
      // if this request is on the debug port, create a thread to run it
      LOGGER.log(Level.FINE, "processing debug request in dedicated thread");
      Thread t = new Thread() {
        @Override
        public void run() {
          HttpConnection.this.run();
        }
      };
      t.start();
    } else {
      LOGGER.log(Level.FINE, "enqueueing request");
      status_ = Status.QUEUED;
      server_.enqueue(this);
    }
  }

  /**
   * Verifies the headers of a request.
   *
   * @return {@link true} iff the request verifies successfully.
   */
  private boolean verifyRequest() throws IOException {
    int status = HttpServletResponse.SC_OK;

    // check if the request content length exceeds limit set in server
    if (getContentLength(req_) > server_.getMaxPostSize()) {
      LOGGER.warning("request content length: " +
          getContentLength(req_) + " exceeds " +
          "server limit of " +
          server_.getMaxPostSize());
      status = HttpServletResponse.SC_BAD_REQUEST;
    }

    // Check if the URI host matches the reported host header. If there is a
    // mismatch, fail in order to avoid XSS attacks.
    if (req_.getHeader("Host") != null
        && RequestUtils.getURI(req_).getHost() != null &&
        req_.getHeader("Host") != RequestUtils.getURI(req_).getHost()) {
      LOGGER.warning("request has a Host header and URI host mismatch: " +
          req_.getHeader("Host") + " vs. " +
          RequestUtils.getURI(req_).getHost());
      status = HttpServletResponse.SC_BAD_REQUEST;
    }

    // Only identity and gzip content encodings are supported.  Gzipped
    // requests are allowed only if enabled via the flag
    // --allow-gzipped-request-bodies.
    String content_encoding = req_.getHeader("Content-Encoding");
    if (content_encoding != null) {
      if (!content_encoding.equals(GSEConstants.CONTENT_ENCODING_IDENTITY) &&
          !content_encoding.equals(GSEConstants.CONTENT_ENCODING_GZIP)) {
        LOGGER.warning("request has unsupported content encoding: " +
            content_encoding);
        status = HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE;
      }

      if (content_encoding.equals(GSEConstants.CONTENT_ENCODING_GZIP)) {
        ++gzippedRequests;
        if (!config.allowGzippedRequestBodies()) {
          LOGGER.warning("gzipped request rejected; not allowed by server");
          status = HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE;
        }
      }
    }

    // only identity and chunked transfer encoding are supported. Chunked
    // request is allowed only if server is enabled for it
    String transfer_encoding = req_.getHeader("Transfer-Encoding");
    if (transfer_encoding != null) {
      if (!transfer_encoding.equals(GSEConstants.TRANSFER_ENCODING_IDENTITY) &&
          !transfer_encoding.equals(GSEConstants.TRANSFER_ENCODING_CHUNKED)) {
        LOGGER.warning("request has unsupported transfer encoding: " +
            transfer_encoding);
        status = HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE;
      }

      if (transfer_encoding.equals(GSEConstants.TRANSFER_ENCODING_CHUNKED)) {
        ++chunkedRequests;
        if (!config.allowChunkedRequestBodies()) {
          LOGGER.warning("chunked request rejected; not allowed by server");
          status = HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE;
        }
      }
    }

    // content-range for requests is unimplemented
    String content_range = req_.getHeader("Content-Range");
    if (content_range != null) {
      LOGGER.warning("partial entity-body requests via Content-Range are " +
          "not supported by this server: " + content_range);
      status = HttpServletResponse.SC_NOT_IMPLEMENTED;
    }

    if (status != HttpServletResponse.SC_OK) {
      badRequest(status);
      return false;
    }

    return true;
  }

  /**
   * Return an error message to the user that a bad request was received.
   * Usually caused by malformed HTTP.
   */
  private void badRequest(int error) throws IOException {
    if (resp_ == null) {
      resp_ = new HttpResponseImpl(this, req_);
      if (req_ != null) {
        resp_.setVersion(req_._getMajorVersion(),
            req_._getMinorVersion());
      }
    }
    resp_.setHeader("Connection", "close");

    // If the request has no valid URI, send a direct response with the error
    // code and message. Otherwise, schedule the bad request handler on the
    // worker queue.
    if (server_.getBadRequestHandler() == null
        || RequestUtils.getURI(req_) == null) {
      resp_.sendError(error);
      resp_.finish(false);
      status_ = Status.IO_WRITE;
      conn_.startTimer(server_.getWriteTimeout());
      conn_.interestReadWrite();
      LOGGER.log(Level.FINE, "error response", resp_);
    } else {
      resp_.setStatus(error);
      status_ = Status.PROCESSING;
      dispatcher = server_.getBadRequestHandler();
      LOGGER.log(Level.FINE,
          "enqueueing bad request on HttpServer.badRequestHandler");
      status_ = Status.QUEUED;
      server_.enqueue(this);
    }
  }

  /**
   * Finish a request. The response has been fully written to the client when
   * this function is called. If the response was a keep alive response and keep
   * alive is enabled, we try to read another request. Otherwise we close the
   * connection.
   */
  protected void finishRequest() throws IOException {
    if (resp_ != null &&
        resp_.isKeepAlive() == true &&
        server_.getKeepaliveTimeout() > 0) {
      thread_detached_ = false;
      dispatcher = null;
      req_ = null;
      resp_ = null;
      conn_.getOutputBuffer().clear();
      conn_.startTimer(server_.getKeepaliveTimeout());
      conn_.interestRead();
      status_ = Status.IO_READ;

      // If pipelined requests were sent, the next request might
      // already be in our input buffer.
      if (conn_.getInputBuffer().isEmpty() == false) {
        readRequest();
      }
    } else {
      conn_.close();
    }
  }

  /**
   * Signals the I/O thread to write the available contents of the NetConnection
   * to the network. If done is false, the calling thread blocks until the I/O
   * thread has completed. Otherwise, we schedule the bytes for writing and
   * return immediately.
   *
   * If the connection isNonBlocking, write will not block. Non-blocking calls
   * to write should only be made from HttpResponse.flushAsync.
   *
   * @param done {@link true} if this data concludes the response
   */
  public void write(boolean done) throws IOException {
    synchronized (wait_lock_) {
      if (is_closed_) {
        throw new IOException("connection closed");
      }
      if (write_in_flight_) {
        throw new IllegalStateException(
            "write() cannot be called while data is still in flight");
      }
      conn_.startTimer(server_.getWriteTimeout());
      conn_.interestReadWrite();
      write_in_flight_ = true;
      // if done, return immediately
      if (done) {
        return;
      }
      // otherwise, wait for the I/O thread to complete the write
      waitForWriteInFlightToComplete();
    }

    // error processing
    Exception cause = conn_.getCauseException();
    if (cause instanceof IOException) {
      // if there was an exception during I/O of type IOException, throw it
      throw (IOException) cause;
    } else if (cause != null) {
      // if some other exception, log exception and throw new IOException
      LOGGER.log(Level.WARNING, "synchronous write failed", cause);
      throw new IOException("failure in synchronous write: "
          + cause.getMessage());
    } else if (!conn_.isOpen()) {
      // if the connection is closed, throw new IOException
      throw new IOException("network connection terminated; cannot write");
    }
  }

  /**
   * Reads data from the underlying {@link NetConnection}. This function
   * properly resets the READ timeout if progress was made, notices when the
   * client closes the connection. The actual work of parsing the request is
   * delegated to the {@link #readRequest} function which is also called when
   * we're handling keep alive and need to read a pipelined request which is
   * already in the NetConnection's input buffer.
   */
  public void handleRead(NetConnection conn) throws IOException {
    try {
      boolean reset_timer = false;

      while (true) {
        int nbytes = conn_.doRead();
        if (nbytes == 0) {
          if (reset_timer) {
            conn_.startTimer(server_.getReadTimeout());
          }
          return;
        }
        if (nbytes == -1) {
          // connection was closed
          conn_.close();
          return;
        }
        reset_timer = true;

        if (readRequest()) {
          return;
        }
      }
    } catch (OutOfMemoryError e) {
      // If there are a lot of large simultaneous POSTS, then it is possible
      // to have all available memory consumed by gse. It would be nice
      // to give gse some kind of request buffer memory limit to prevent
      // this, but that seems difficult. This is the next best thing, which
      // is to catch OutOfMemoryError and close the connection (even
      // though this may not be the guilty connection, it's dead anyway,
      // and this allows us to free some memory)
      conn_.close();

      // Log after closing, just in case we don't even have enough memory
      // for this.
      LOGGER.log(Level.SEVERE,
          "Memory exhausted during read. Closing connection.", e);
    }
  }

  /**
   * If the request was received, the HttpHeaders have been fully read (resp_ !=
   * null), and there is a Content-Length header, the additional work of reading
   * the request body is dispatched to readRequestBody().
   */
  boolean readRequest() throws IOException {
    if (status_ != Status.IO_READ) {
      // We might have been called to read more data when we're
      // processing a request. This can occur if the client sends a
      // pipelined request in a separate packet. The buffered data
      // will eventually be processed when we're done with the current
      // request. See the readRequest() call in finishRequest().
      return false;
    }

    if (resp_ == null) {
      if (req_ == null) {
        req_ = server_.createRequestSubsetImpl(this);
        num_request_bytes_ = 0;
        num_reqs_ += 1;
        line_buf_.reset();
      }

      // still reading the request header
      try {
        // flush the data so it's available to parse
        conn_.getInputBuffer().flush();
        int avail = conn_.getInputBuffer().availableBytes();
        try {
          if (!req_.parse(line_buf_, conn_.getInputBuffer())) {
            return false;
          }
        } finally {
          // Add in the number of bytes that were consumed during parsing.
          num_request_bytes_ +=
              (avail - conn_.getInputBuffer().availableBytes());
        }
      } catch (IOException ioe) {
        // error parsing request headers
        LOGGER.warning(ioe.getMessage());
        badRequest(HttpServletResponse.SC_BAD_REQUEST);
        return true;
      }

      resp_ = new HttpResponseImpl(this, req_);
      resp_.setVersion(req_._getMajorVersion(),
          req_._getMinorVersion());
      line_buf_.reset();

      // check request content length against limit, content encoding
      // against supported media types, etc. If the verification
      // fails, we're done (return true)
      if (verifyRequest() == false) {
        return true;
      }

      // check to see if we should read the request body
      if (req_.getMethod() != GSEConstants.POST &&
          req_.getMethod() != GSEConstants.PUT &&
          getContentLength(req_) == -1 &&
          !isChunkedRequest(req_)) {
        LOGGER.log(Level.FINE, "request parsed and ready to process");
        LOGGER.log(Level.FINEST, "request", req_);
        processRequest();
        return true;
      }
    }

    // reading the request body
    return readRequestBody();
  }

  private boolean isChunkedRequest(HttpServletRequest request) {
    return GSEConstants.TRANSFER_ENCODING_CHUNKED.equals(
        request.getHeader("Transfer-Encoding"));
  }

  private boolean isChunkedRequest(HttpRequest request) {
    return GSEConstants.TRANSFER_ENCODING_CHUNKED.equals(
        request.getHeader("Transfer-Encoding"));
  }

  /**
   * Reads the request body. This function should only be called if we are
   * handling a POST request, or a request. If we reach the end of the post
   * body, or encounter an error in parsing a multipart request, we return true
   * to the handleRead() function to indicate that we should stop reading from
   * the {@link NetConnection}
   */
  private boolean readRequestBody() throws IOException {
    // Flush the data so it's available to parse.
    conn_.getInputBuffer().flush();
    int avail = conn_.getInputBuffer().availableBytes();

    // handle multipart parsing
    if (isChunkedRequest(req_) && config.allowChunkedRequestBodies()) {
      // Verify that the content length has not been specified.
      if (getContentLength(req_) != -1) {
        LOGGER.log(Level.WARNING, req_.getMethod() +
            " chunked request has an extraneous content length " +
            "header; ignoring...", req_);
      }
      try {
        if (req_._parseChunkedBody(line_buf_, conn_.getInputBuffer())) {
          num_request_bytes_ +=
              (avail - conn_.getInputBuffer().availableBytes());
          ++chunkedRequestsCompleted;
          processRequest();
          return true;
        } else {
          num_request_bytes_ +=
              (avail - conn_.getInputBuffer().availableBytes());
        }
      } catch (IOException ioe) {
        // error parsing multipart request
        LOGGER.warning(ioe.getMessage());
        badRequest(HttpServletResponse.SC_BAD_REQUEST);
        return true;
      }
    } else {
      // Verify that the content length is valid, log warning if not.
      if (getContentLength(req_) == -1) {
        LOGGER.log(Level.WARNING, req_.getMethod() +
            " request does not have a valid content length", req_);
        processRequest();
        return true;
      }
      if (conn_.getInputBuffer().totalBytes() >= getContentLength(req_)) {
        // copy the indicated number of bytes to the request body
        req_._setPostBody(conn_.getInputBuffer(), getContentLength(req_));
        num_request_bytes_ += getContentLength(req_);
        LOGGER.log(Level.FINE, "request body parsed and ready to process");
        LOGGER.log(Level.FINEST, "request", req_);
        processRequest();
        return true;
      }
      // else, there are not sufficient number of bytes in the input buffer.
      // return false to keep reading intput connection
    }

    return false;
  }

  private static int getContentLength(HttpRequest req) {
    return getIntHeader(req, "Content-Length");
  }

  private static int getIntHeader(HttpRequest req, String name) {
      return HeaderUtil.toIntHeader(req.getHeader(name));
  }

  public void handleConnect(NetConnection conn) throws IOException {
    LOGGER.warning("HttpConnection should not receive connection events");
  }

  /**
   * Writes more of the response to the underlying {@link NetConnection}.
   * The bulk of the work is done by {@link NetConnection#doWrite} which writes
   * data from the output buffer that the response was written in to. If we have
   * no more data to write, the request is finished. Otherwise, we reset the
   * WRITE timeout if we made some progress.
   */
  public void handleWrite(NetConnection conn) throws IOException {
    int nbytes = conn_.doWrite();
    if (conn_.getOutputBuffer().availableBytes() == 0) {
      // if we're processing the request still, it means data is just being
      // flushed to the network and the worker thread should be notified
      boolean stillProcessing;
      synchronized (this) {
        stillProcessing = processing_thread_ != null || thread_detached_;
      }
      if (stillProcessing) {
        synchronized (wait_lock_) {
          conn_.stopTimer();
          conn_.interestRead();
          write_in_flight_ = false;
          notifyWriteInFlightComplete();
        }
      } else {
        // otherwise, finish the request
        finishRequest();
      }
    } else {
      // if any bytes were written, restart timeout
      if (nbytes > 0) {
        conn.startTimer(server_.getWriteTimeout());
      }
    }
  }

  public void handleClose(NetConnection conn) {
    status_ = Status.CLOSED;

    synchronized (wait_lock_) {
      is_closed_ = true;
      notifyWriteInFlightComplete();
    }
  }

  /**
   * Return an HTML-formatted string containing all relevant details on HTTP
   * connection.
   *
   * @return HTML status string
   */
  public String getHtmlStatus(NetConnection conn) {
    StringBuilder buf = new StringBuilder();

    // We need to synchronize access to various parts of the
    // connection here for two reasons: 1) to prevent conn_ from being
    // set to null by a call to detachNetConnection() and 2) to
    // prevent processing_thread_ and processing_start_ from changing
    // unexpectedly by a different thread running runServlet().
    synchronized (this) {
      buf.append("HTTP Connection from IP ");
      try {
        buf.append(req_.getRemoteAddr());
      } catch (Exception e) {
        buf.append(getSocket().getInetAddress().getHostAddress());
      }
      buf.append(" on port ");
      buf.append(getSocket().getPort());
      if (num_reqs_ > 1) {
        buf.append("<br>Keepalive request #");
        buf.append(num_reqs_);
      } else if (num_reqs_ == 1) {
        buf.append("<br>First request");
      } else {
        buf.append("<br>Waiting for request");
      }
      buf.append("<ul>");

      buf.append("<li>");
      buf.append("Status: ");

      switch (status_) {
        case IO_READ:
          buf.append("Reading request");
          break;
        case IO_WRITE:
          buf.append("Writing response");
          break;
        case QUEUED:
          buf.append("Queued for dispatch");
          break;
        case PROCESSING:
          buf.append("Processing servlet '");
          buf.append((dispatcher == null) ? "n/a" :
              dispatcher.getClass().getSimpleName());
          buf.append("' for ");
          buf.append((System.currentTimeMillis() - processing_start_));
          buf.append(" ms, dispatched to thread ");
          buf.append(processing_thread_);
          break;
        case CLOSED:
          buf.append("Closed");
          break;
        default:
          throw new AssertionError("Unknown status: " + status_);
      }
      buf.append("</li>");
    }

    // Only the NetSelector thread changes req_ in ways that affect
    // req_.toString(), so it's alright to do this work outside of the
    // synchronized block.
    if (req_ != null) {
      buf.append("<li><pre>");
      buf.append(StringUtil.htmlEscape(req_.toString()));
      buf.append("</pre></li>");
    }

    buf.append("</ul>");

    return buf.toString();
  }


  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    // We need to synchronize access to various parts of the
    // connection here for two reasons: 1) to prevent conn_ from being
    // set to null by a call to detachNetConnection() and 2) to
    // prevent processing_thread_ and processing_start_ from changing
    // unexpectedly by a different thread running runServlet().
    synchronized (this) {
      buf.append("HTTP Connection from IP ");
      try {
        buf.append(req_.getRemoteAddr());
      } catch (Exception e) {
        buf.append(getSocket().getInetAddress().getHostAddress());
      }
      buf.append(" on port ");
      buf.append(getSocket().getPort());
      if (num_reqs_ > 1) {
        buf.append("\nKeepalive request #");
        buf.append(num_reqs_);
      } else if (num_reqs_ == 1) {
        buf.append("\nFirst request");
      } else {
        buf.append("\nWaiting for request");
      }
      buf.append("\n");

      buf.append("* ");
      buf.append("Status: ");

      switch (status_) {
        case IO_READ:
          buf.append("Reading request");
          break;
        case IO_WRITE:
          buf.append("Writing response");
          break;
        case QUEUED:
          buf.append("Queued for dispatch");
          break;
        case PROCESSING:
          buf.append("Processing servlet '");
          buf.append((dispatcher == null) ? "n/a" :
              dispatcher.getClass().getSimpleName());
          buf.append("' for ");
          buf.append((System.currentTimeMillis() - processing_start_));
          buf.append(" ms, dispatched to thread ");
          buf.append(processing_thread_);
          break;
        case CLOSED:
          buf.append("Closed");
          break;
        default:
          throw new AssertionError("Unknown status: " + status_);
      }
      buf.append("\n");
    }

    // Only the NetSelector thread changes req_ in ways that affect
    // req_.toString(), so it's alright to do this work outside of the
    // synchronized block.
    if (req_ != null) {
      buf.append("\n");
      buf.append(req_.toString());
      buf.append("\n");
    }

    return buf.toString();

  }
}