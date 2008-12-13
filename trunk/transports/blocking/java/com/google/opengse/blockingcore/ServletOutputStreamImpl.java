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
  private Map<String, String> ucase_headername_to_headername;
  private Map<String, List<String>> ucase_headers;

  private PrintWriter out;
  private PrintWriter printWriter;
  private int statusCode = 200;
  private String statusMessage = "OK";

  ServletOutputStreamImpl(OutputStream realStream, int precommitSize) {
    this.realStream = realStream;
    this.precommitSize = precommitSize;
    ucase_headername_to_headername = new HashMap<String, String>();
    ucase_headers = new HashMap<String, List<String>>();
    bufferStream = new ByteArrayOutputStream();
    currentStream = bufferStream;
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
    if (printWriter != null) {
      printWriter.flush();
    }
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
    ucase_headers.clear();
    ucase_headername_to_headername.clear();
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
    for (String ukey : ucase_headers.keySet()) {
      List<String> values = ucase_headers.get(ukey);
      // get the original rendering of the header "content-type" versus "Content-Type" etc.
      String key = ucase_headername_to_headername.get(ukey);
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
    // "HTTP/1.1 200 OK" for example
    out.print("HTTP/1.1 " + statusCode + " " + statusMessage + "\r\n");
  }

  /**
   * Do a case-insensitive lookup of a given header name
   * @param key
   * @return
   */
  synchronized List<String> getHeaderValues(String key) {
    String ukey = key.toUpperCase();
    List<String> values = ucase_headers.get(ukey);
    if (values == null) {
      values = new ArrayList<String>();
      ucase_headername_to_headername.put(ukey, key);
      ucase_headers.put(ukey, values);
    }
    return values;
  }

  synchronized boolean containsHeader(String name) {
    return ucase_headers.containsKey(name.toUpperCase());
  }

  synchronized void setHeader(String key, String value) {
    if (value == null) {
      removeHeader(key);
      return;
    }
    List<String> values = getHeaderValues(key);
    values.clear();
    values.add(value);
  }

  void removeHeader(String key) {
    String ukey = key.toUpperCase();
    ucase_headername_to_headername.remove(ukey);
    ucase_headers.remove(ukey);
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

  PrintWriter getWriter() throws IOException {
    if (printWriter == null) {
      printWriter = new PrintWriter(this);
    }
    return printWriter;
  }

}
