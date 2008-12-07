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
  private ByteArrayOutputStream bufferStream;
  private OutputStream currentStream;
  private int precommitSize;
  private Map<String, List<String>> respHeaders;

  private PrintWriter out;
  private PrintWriter printWriter;
  private String statusLine;

  ServletOutputStreamImpl(OutputStream realStream, int precommitSize) {
    this.realStream = realStream;
    this.precommitSize = precommitSize;
    this.respHeaders = new HashMap<String, List<String>>();
    bufferStream = new ByteArrayOutputStream();
    currentStream = bufferStream;
    statusLine = "HTTP/1.1 200 OK";
  }

  boolean isCommitted() {
    return (bufferStream == null); // null buffer implies committed
  }

  synchronized void commit() throws IOException {
    if (isCommitted()) {
      return;
    }
    out = new PrintWriter(realStream);
    writeStatusLine();
    writeHeaders();
    out.flush();
    writeUncommittedBuffer();
    bufferStream = null;
    currentStream = realStream;
    printWriter.flush();
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
    bufferStream.reset();
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
    bufferStream.reset();
    respHeaders.clear();
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
    return ((bufferStream.size() + bytesToWrite) > precommitSize);    
  }

  private void writeUncommittedBuffer() throws IOException {
    realStream.write(bufferStream.toByteArray());
  }

  private void writeHeaders() {
    for (String key : respHeaders.keySet()) {
      List<String> values = respHeaders.get(key);
      // we know that values cannot be empty
      Iterator<String> iter = values.iterator();
      out.print(key + ": " + iter.next());
      while (iter.hasNext()) {
        out.print(", " + iter.next());
      }
      out.print("\r\n");
    }
    // now we signal the end of headers by printing a blank line
    out.print("\r\n");
  }

  private void writeStatusLine() {
    out.print(statusLine + "\r\n");
  }

  synchronized List<String> getHeaderValues(String key) {
    List<String> values = respHeaders.get(key);
    if (values == null) {
      values = new ArrayList<String>();
      respHeaders.put(key, values);
    }
    return values;
  }

  synchronized boolean containsHeader(String name) {
    return respHeaders.containsKey(name);
  }

  synchronized void setHeader(String key, String value) {
    if (value == null) {
      respHeaders.remove(key);
      return;
    }
    List<String> values = getHeaderValues(key);
    values.clear();
    values.add(value);
  }

  synchronized void addHeader(String key, String value) {
    List<String> values = getHeaderValues(key);
    values.add(value);
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

  String getStatusLine() {
    return statusLine;
  }

  synchronized void setStatusLine(String statusLine) {
    this.statusLine = statusLine;
  }

  PrintWriter getWriter() throws IOException {
    if (printWriter == null) {
      printWriter = new PrintWriter(this);
    }
    return printWriter;
  }

}
