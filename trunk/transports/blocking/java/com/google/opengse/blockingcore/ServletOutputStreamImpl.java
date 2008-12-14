// Copyright 2008 Google Inc. All Rights Reserved.
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
package com.google.opengse.blockingcore;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author jennings
 *         Date: Jul 23, 2008
 */
class ServletOutputStreamImpl extends ServletOutputStream {
  private OutputStream realStream;
  private ByteArrayOutputStream uncommittedOutputStream;
  private OutputStream currentStream;
  private int precommitSize;
  private HttpHeaders headers;

  private PrintWriter printWriter;
  private int statusCode = 200;
  private String statusMessage = "OK";

  ServletOutputStreamImpl(OutputStream realStream, int precommitSize) {
    this.realStream = realStream;
    this.precommitSize = precommitSize;
    headers = new HttpHeaders();
    uncommittedOutputStream = new ByteArrayOutputStream();
    currentStream = uncommittedOutputStream;
  }

  HttpHeaders getHeaders() {
    return headers;
  }

  void setStatus(int statusCode, String statusMessage) {
    this.statusCode = statusCode;
    this.statusMessage = statusMessage;
  }

  void setStatus(int statusCode) {
    this.statusCode = statusCode;
    this.statusMessage = getMessageForStatusCode(statusCode);
  }

  String getMessageForStatusCode(int sc) {
    return "OK";
  }

  boolean isCommitted() {
    return (uncommittedOutputStream == null); // null buffer implies committed
  }

  synchronized void commit() throws IOException {
    if (isCommitted()) {
      return;
    }
    PrintWriter out = new PrintWriter(realStream);
    writeStatusLine(out);
    writeHeaders(out);
    out.flush();
    writeUncommittedBuffer();
    uncommittedOutputStream = null;
    currentStream = realStream;
  }

  void flushPrintWriterIfItWasCreated() {
    if (printWriter != null) {
      printWriter.flush();
    }
  }

  @Override
  public void flush() throws IOException {
    commit();
    super.flush();
  }

  /**
   * Clears the content of the underlying buffer in the response without
   * clearing headers or status code. If the
   * response has been committed, this method throws an
   * <code>IllegalStateException</code>.
   *
   * @see 		#setBufferSize
   * @see 		#getBufferSize
   * @see 		#isCommitted
   * @see 		#reset
   *
   * @since 2.3
   */
  synchronized void resetBuffer() {
    if (isCommitted()) {
      throw new IllegalStateException("Cannot reset buffer, already committed");
    }
    uncommittedOutputStream.reset();
  }

  /**
   * Clears any data that exists in the buffer as well as the status code and
   * headers.  If the response has been committed, this method throws an
   * <code>IllegalStateException</code>.
   *
   * @exception IllegalStateException  if the response has already been
   *                                   committed
   *
   * @see 		#setBufferSize
   * @see 		#getBufferSize
   * @see 		#commit
   * @see 		#isCommitted
   */
  synchronized void reset() {
    if (isCommitted()) {
      throw new IllegalStateException("Cannot reset, already committed");
    }
    uncommittedOutputStream.reset();
    headers.clear();
  }


  synchronized void setBufferSize(int size) {
    precommitSize = size;
  }

  /**
   * Returns the actual buffer size used for the response.  If no buffering
   * is used, this method returns 0.
   *
   * @return	 	the actual buffer size used
   *
   * @see 		#setBufferSize
   * @see 		#commit
   * @see 		#isCommitted
   * @see 		#reset
   */
  synchronized int getBufferSize() {
    return precommitSize;
  }


  /**
   *
   * Determines if we need to commit the stream before we write
   * the given number of bytes.
   * 
   * @param bytesToWrite
   * @return
   */
  private boolean needToCommit(int bytesToWrite) {
    if (isCommitted()) {
      return false;
    }
    return ((uncommittedOutputStream.size() + bytesToWrite) > precommitSize);
  }

  private void writeUncommittedBuffer() throws IOException {
    realStream.write(uncommittedOutputStream.toByteArray());
  }

  private void writeHeaders(PrintWriter out) {
    headers.writeHeaders(out);
  }

  private void writeStatusLine(PrintWriter out) {
    // "HTTP/1.1 200 OK" for example
    out.print("HTTP/1.1 " + statusCode + " " + statusMessage + "\r\n");
  }


  synchronized public void write(int b) throws IOException {
    if (needToCommit(1)) {
      commit();
    }
    currentStream.write(b);
  }

  synchronized public void write(byte[] buf, int offset, int length) throws IOException {
    if (needToCommit(length)) {
      commit();
    }
    currentStream.write(buf, offset, length);
  }

  PrintWriter getWriter() throws IOException {
    if (printWriter == null) {
      printWriter = new PrintWriter(this);
    }
    return printWriter;
  }

}
