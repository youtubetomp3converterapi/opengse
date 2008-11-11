// Copyright 2007 Google Inc.
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
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

/**
 * Contains logic for managing a non-blocking transfer from a file to an
 * HttpConnection without needing to tie up a dedicated thread while blocking on
 * socket write operations.
 *
 * @author Mathew Mills
 *
 */
public class HttpNonblockingTransferTask
    implements HttpConnection.Reattachable {

  /**
   * This interface works a little like Runnable, only it takes an Exception as
   * a parameter.
   *
   */
  public interface TransferCompleteCallback {

    /**
     * Implement this interface to register clean-up or logging code to be
     * invoked after the transfer is complete. If the transfer completes
     * successfully the callback is called with null. If the transfer is aborted
     * then an exception object is passed to the callback. Exceptions passed to
     * this callback are unlikely to be logged anywhere else, so implementations
     * of this interface should log the exception.
     *
     * @param e an Exception if the transfer failed, null if successful
     */
    void handleTransferComplete(Exception e);
  }

  /**
   * MINIMUM_BYTES_PER_READ is the smallest size to read from file (channel)
   * between network writes. 16kB is approximately the minimum Linux read-ahead
   * size, which should reduce the number of times we block on disk access but
   * still keeps the bytes-per-write acceptable.
   */
  private static final int MINIMUM_BYTES_PER_READ = 4096 * 4;

  /**
   *  IOBuffer from the HttpConnection (from its NetConnection)
   */
  private IOBuffer outputBuffer_;

  /**
   * The connection to which we stream the content.
   */
  private HttpConnection conn_;

  /**
   * The NIO channel from which we read the content to transmit.
   */
  private ReadableByteChannel channel_;

  /**
   * How many bytes we attempt to read from the file at a time.
   */
  private int bytesPerRead_;

  /**
   * The callback to invoke when transfer is complete, successful or not.
   */
  private TransferCompleteCallback finishCallback_;

  /**
   * The response object through which we send the stream.
   */
  private HttpResponseImpl response_;

  /**
   * doneReading_ == true if all contents of channel_ have been read and
   * buffered to output.
   */
  private boolean doneReading_ = false;

  /**
   * transferComlpeted_ is true when all reads and writes are done.
   */
  private boolean transferCompleted_ = false;

  /**
   * @param response The response to complete.
   * @param connection connection to which to send the content
   * @param channel file from which to read the content
   * @param cb callback to be invoked when transfer is complete
   */
  public HttpNonblockingTransferTask(HttpResponseImpl response,
      HttpConnection connection, ReadableByteChannel channel,
      TransferCompleteCallback cb) {
    conn_ = connection;
    channel_ = channel;
    response_ = response;
    outputBuffer_ = response.getOutputBuffer();
    finishCallback_ = cb;

    bytesPerRead_ = Math.max(outputBuffer_.getSizeLimit(),
        MINIMUM_BYTES_PER_READ);

    /*
     * We don't want to accidentally trigger an IOBuffer call to consume, so we
     * set the consumeCallback to null. We will use
     * HttpResponseImpl::flushAsync instead of
     * HttpResponseImpl::handleConsume to prepare the output buffer
     * for sending.
     */
    outputBuffer_.setConsumeCallback(null);
    setupCallback();
  }

  /**
   * A constructed HttpNonblockingTransferTask is ready to go after
   * construction. We just need to tell it to start its work with this method.
   * Callers should only return after calling this method.
   */
  public void startTransfer() {
    try {
      sendMoreOfFile();
    } catch (IOException e) {
      // From the time this function is invoked, all transfer-related
      // exceptions must be routed to the finishCallback_, not thrown.
      completeTransfer(e);
    }
  }

  /**
   * This routine makes progress in sending the contents of the channel_ to the
   * conn_ and completes the cleanup work after.
   *
   * @throws java.io.IOException
   */
  private void sendMoreOfFile() throws IOException {
    // TODO(mathewm): explore mmap'ing a direct buffer instead

    // Synchronized against the workerThreadCallback created in setupCallback().
    synchronized (this) {
      while (!doneReading_) {
        // Read from the channel and stuff into output buffer.
        doneReading_ = readFromChannel();

        // Ask the HttpRequest to encode any existing data and initiate a write.
        // We can only call flushAsync with doneReading_ == true _once_ because
        // some encodings have non-idempotent flush code (e.g. the
        // Range-handling code)
        boolean writeInitiated = response_.flushAsync(doneReading_);
        if (writeInitiated) {
          // The flushAsync() call initiated a write in the IO-thread, so we
          // need to detach and return back to the runServlet() or
          // reattachThread() that resulted in our invocation.
          conn_.detachThread();
          return;
        } else if (!doneReading_) {
          // We are not done reading, yet writes have stopped working so
          // something must be wrong with the connection.
          // As with synchronous transfers, we keep reading and writing and
          // pretend nothing is wrong.
          continue;
        }
      }
      // We are done reading and have made the final flushAsync call.
      completeTransfer(null);
    }
  }

  /**
   * Read up to bytesPerRead_ bytes from the channel_ and stuff it into the
   * outputBuffer_.
   *
   * @return true if the end of input is reached, false otherwise.
   * @throws java.io.IOException
   */
  private boolean readFromChannel() throws IOException {
    boolean done = false;
    ByteBuffer fileInputBuffer = ByteBuffer.allocate(bytesPerRead_);

    // Read until the end of the channel or until we fill the buffer.
    int bytesRead = 0;
    while (bytesRead < bytesPerRead_) {
      int bytesThisRead = channel_.read(fileInputBuffer);
      if (bytesThisRead == -1) {
        // We reached the end of the input channel.
        done = true;
        break;
      } else {
        // We have more to send with the response, so add it to the IOBuffer
        done = false;
        bytesRead += bytesThisRead;
        outputBuffer_.appendBuffer(fileInputBuffer);
      }
    }

    // Flush to make the IOBuffer recognize the new data.
    outputBuffer_.flush();

    return done;
  }

  /**
   * Invoke the user-supplied callback when we are done with the transfer,
   * passing in any exception that might have aborted the transfer.
   *
   * @param e An exception that aborted the transfer, or null if successful.
   */
  private void completeTransfer(Exception e) {
    // Indicate that any further callbacks should do no work.
    transferCompleted_ = true;

    // Restore the HttpConnection to standard blocking behavior.
    conn_.setNonblocking(null);
    /*
     * Restore the HttpResponseImpl's IOBuffer callback, though it
     * is not needed as the response is complete.
     */
    outputBuffer_.setConsumeCallback(response_);
    // Call any registered clean-up/logging callback
    if (finishCallback_ != null) {
      finishCallback_.handleTransferComplete(e);
    }
  }

  /**
   * Implements the Reattachable interface. Continues the process of reading
   * from the file and buffering to the HttpConnection. Usually, this thread is
   * detached upon returning. When we return while still attached it indicates
   * the response is complete.
   */
  public void reattach() throws IOException {
    sendMoreOfFile();
  }

  /**
   * We need a short callback chain to register with the HttpConnection and
   * arrange to invoke our own reattach method in a worker thread.
   */
  private void setupCallback() {
    // We need to run our reattach within a worker thread, so we need this
    // Runnable to feed to the DispatchQueue.
    final Runnable workerThreadCallback = new Runnable() {
      public void run() {
        // Synchronized to prevent overlapping calls to reattachThread.
        // reattachThread should also not overlap with runServlet, but those
        // functions are already synchronized as needed.
        // Also, synchronized against sendMoreOfFile() in order to prevent
        // calling reattachThread() for a servelet that has already completely
        // finished the transfer. The latter case only happens when the
        // connection is closed abnormally.
        synchronized (HttpNonblockingTransferTask.this) {
          try {
            // If transferCompleted_ == true, then we are a vestigial callback,
            // probably in response to an aborted connection.  Do no work.
            if (!transferCompleted_) {
              conn_.reattachThread(HttpNonblockingTransferTask.this);
            }
          } catch (IOException e) {
            completeTransfer(e);
          }
        }
      }
    };

    // Next, we need another Runnable, to be called in the IO-thread, which
    // enqueues the workerThreadCallback.
    final Runnable ioThreadCallback = new Runnable() {
      Runnable workerThreadCallback_ = workerThreadCallback;
      HttpServer server = conn_.server_;
      public void run() {
        server.enqueue(workerThreadCallback_);
      }
    };

    // Register the callback, making the connection non-blocking (for writes).
    conn_.setNonblocking(ioThreadCallback);
  }
}