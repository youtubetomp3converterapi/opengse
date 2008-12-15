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

  /**
   * Sends an error response to the client using the specified
   * status.  The server defaults to creating the
   * response to look like an HTML-formatted server error page
   * containing the specified message, setting the content type
   * to "text/html", leaving cookies and other headers unmodified.
   *
   * If an error-page declaration has been made for the web application
   * corresponding to the status code passed in, it will be served back in
   * preference to the suggested msg parameter.
   *
   * <p>If the response has already been committed, this method throws
   * an IllegalStateException.
   * After using this method, the response should be considered
   * to be committed and should not be written to.
   *
   * @param	sc	the error status code
   * @param	msg	the descriptive message
   * @exception	IOException	If an input or output exception occurs
   * @exception	IllegalStateException	If the response was committed
   */
  void sendError(int sc, String msg) throws IOException;

  void sendError(int sc) throws IOException;

  void setDateHeader(String name, long date);

  void addDateHeader(String name, long date);

  void setHeader(String name, String value);

  void addHeader(String name, String value);

  void setIntHeader(String name, int value);

  void addIntHeader(String name, int value);

  /**
   * Sets the status code for this response.  This method is used to
   * set the return status code when there is no error (for example,
   * for the status codes SC_OK or SC_MOVED_TEMPORARILY).  If there
   * is an error, and the caller wishes to invoke an error-page defined
   * in the web application, the <code>sendError</code> method should be used
   * instead.
   * <p> The container clears the buffer and sets the Location header, preserving
   * cookies and other headers.
   *
   * @param	sc	the status code
   *
   * @see #sendError
   */
  void setStatus(int sc);

  /**
   * @deprecated As of version 2.1, due to ambiguous meaning of the
   * message parameter. To set a status code
   * use <code>setStatus(int)</code>, to send an error with a description
   * use <code>sendError(int, String)</code>.
   *
   * Sets the status code and message for this response.
   *
   * @param	sc	the status code
   * @param	sm	the status message
   */
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

  /**
   * Sets the locale of the response, if the response has not been
   * committed yet. It also sets the response's character encoding
   * appropriately for the locale, if the character encoding has not
   * been explicitly set using {@link #setContentType} or
   * {@link #setCharacterEncoding}, <code>getWriter</code> hasn't
   * been called yet, and the response hasn't been committed yet.
   * If the deployment descriptor contains a
   * <code>locale-encoding-mapping-list</code> element, and that
   * element provides a mapping for the given locale, that mapping
   * is used. Otherwise, the mapping from locale to character
   * encoding is container dependent.
   * <p>This method may be called repeatedly to change locale and
   * character encoding. The method has no effect if called after the
   * response has been committed. It does not set the response's
   * character encoding if it is called after {@link #setContentType}
   * has been called with a charset specification, after
   * {@link #setCharacterEncoding} has been called, after
   * <code>getWriter</code> has been called, or after the response
   * has been committed.
   * <p>Containers must communicate the locale and the character encoding
   * used for the servlet response's writer to the client if the protocol
   * provides a way for doing so. In the case of HTTP, the locale is
   * communicated via the <code>Content-Language</code> header,
   * the character encoding as part of the <code>Content-Type</code>
   * header for text media types. Note that the character encoding
   * cannot be communicated via HTTP headers if the servlet does not
   * specify a content type; however, it is still used to encode text
   * written via the servlet response's writer.
   *
   * @param loc  the locale of the response
   *
   * @see 		#getLocale
   * @see 		#setContentType
   * @see 		#setCharacterEncoding
   */
  void setLocale(Locale loc);

  Locale getLocale();

}
