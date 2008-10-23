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

package com.google.opengse;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * An interface which is a subset of HttpServletResponse.
 *
 * @author jennings
 *         Date: Jul 6, 2008
 */
public interface HttpResponse {

  boolean containsHeader(String name);

  void sendError(int sc, String msg) throws IOException;

  void sendError(int sc) throws IOException;

  void setDateHeader(String name, long date);

  void addDateHeader(String name, long date);

  void setHeader(String name, String value);

  void addHeader(String name, String value);

  void setIntHeader(String name, int value);

  void addIntHeader(String name, int value);

  void setStatus(int sc);

  void setStatus(int sc, String sm);

  String getCharacterEncoding();

  String getContentType();

  ServletOutputStream getOutputStream() throws IOException;

  PrintWriter getWriter() throws IOException;

  void setCharacterEncoding(String charset);

  void setContentLength(int len);

  void setContentType(String type);

  /**
   * Sets the preferred buffer size for the body of the response.
   * The servlet container will use a buffer at least as large as
   * the size requested.  The actual buffer size used can be found
   * using <code>getBufferSize</code>.
   *
   * <p>A larger buffer allows more content to be written before anything is
   * actually sent, thus providing the servlet with more time to set
   * appropriate status codes and headers.  A smaller buffer decreases
   * server memory load and allows the client to start receiving data more
   * quickly.
   *
   * <p>This method must be called before any response body content is
   * written; if content has been written or the response object has
   * been committed, this method throws an
   * <code>IllegalStateException</code>.
   *
   * @param size 	the preferred buffer size
   *
   * @exception  IllegalStateException  	if this method is called after
   *						content has been written
   *
   * @see 		#getBufferSize
   * @see 		#flushBuffer
   * @see 		#isCommitted
   * @see 		#reset
   *
   */
  void setBufferSize(int size);

  /**
   * Returns the actual buffer size used for the response.  If no buffering
   * is used, this method returns 0.
   *
   * @return	 	the actual buffer size used
   *
   * @see 		#setBufferSize
   * @see 		#flushBuffer
   * @see 		#isCommitted
   * @see 		#reset
   */
  int getBufferSize();

  /**
   * Forces any content in the buffer to be written to the client.  A call
   * to this method automatically commits the response, meaning the status
   * code and headers will be written.
   *
   * @see 		#setBufferSize
   * @see 		#getBufferSize
   * @see 		#isCommitted
   * @see 		#reset
   */
  void flushBuffer() throws IOException;

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
  void resetBuffer();

  /**
   * Returns a boolean indicating if the response has been
   * committed.  A committed response has already had its status
   * code and headers written.
   *
   * @return		a boolean indicating if the response has been
   *  		committed
   *
   * @see 		#setBufferSize
   * @see 		#getBufferSize
   * @see 		#flushBuffer
   * @see 		#reset
   */
  boolean isCommitted();

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
   * @see 		#flushBuffer
   * @see 		#isCommitted
   */
  void reset();

  void setLocale(Locale loc);

  Locale getLocale();

}
