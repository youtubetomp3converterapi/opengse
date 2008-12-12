// Copyright 2002 Google Inc.
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
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.google.opengse.httputil.AcceptHeader;
import com.google.opengse.httputil.ContentType;
import com.google.opengse.httputil.HttpUtil;
import com.google.opengse.httputil.Range;
import com.google.opengse.iobuffer.ConsumeCallback;
import com.google.opengse.iobuffer.IOBuffer;
import com.google.opengse.iobuffer.IOBufferOutputStream;
import com.google.opengse.iobuffer.IOBufferWriter;
import com.google.opengse.util.string.Base64;
import com.google.opengse.util.string.StringUtil;
import com.google.opengse.HeaderUtil;
import com.google.opengse.HttpResponse;
import com.google.opengse.GSEConstants;
import com.google.opengse.ServletEngineConfiguration;

/**
 * Implements the <code>HttpServletResponseSubset</code> interface. Some servlet
 * methods are unimplemented. These are usually in the more esoteric areas of
 * the servlet specification. These methods currently throw an
 * <code>Error</code> if invoked.
 * <p/>
 * Range requests (RFC 2616, section 14.35) are implemented with two caveats:
 * One, the response must manually set a content length before any data is
 * flushed; Two, the Range request, if for multiple ranges, must specify
 * monotonically increasing, non-overlapping ranges. Both caveats arise as a
 * response to potential denial-of-service attacks, as support for either
 * unspecified content lengths or arbitrary range specifications would allow
 * range specifications requiring the entire response to be buffered by GSE
 * before writing to the client. Servers are allowed to ignore Range requests
 * entirely, so these caveats do not affect the correctness of the server.
 * Further, the intended purpose of support for this feature is to enable
 * continuation of downloads, which should not require multiple ranges, much
 * less overlapping or unordered ranges.
 *
 * @author Peter Mattis
 * @author Spencer Kimball
 */
final class HttpResponseImpl
    extends MimeHeaders implements HttpResponse, ConsumeCallback {

  private static final String HTTPS = "https";

  private static final String HTTP = "http";

  private static final Logger LOGGER =
      Logger.getLogger(HttpResponseImpl.class.getName());

  private static final Locale DEFAULT_LOCALE = Locale.getDefault();

  private static final int NUM_BOUNDARY_BYTES = 24;

  protected HttpConnection conn;
  private final HttpRequestImpl req;
  private int status = 0;
  private int major = 0;
  private int minor = 9;
  private int contentLength = 0;
  private int discardedBytes = 0;
  private boolean committed = false;
  private boolean finished = false;
  private boolean canChunkEncode = false;
  private boolean closeConnection = false;
  private boolean propagateOutputErrors;
  private String reason = null;
  private Range range = null;
  private String rangeBoundary = null;
  private int rangeIndex = 0;
  private int rangeContentLength = -1;
  private int rangeCurrentPos = 0;
  private String rangeContentType = null;
  private final String defaultCharset;
  private String specified_charset = null;
  private Locale locale = DEFAULT_LOCALE;
  private IOBuffer output_buf = null;
  private IOBufferOutputStream output_stream = null;
  private PrintWriter output_writer = null;
  private DeflaterOutputStream deflater_output_stream = null;
  private IOBuffer deflater_output_buf = null;
  private boolean compress_response = false;
  private boolean compress_response_set = false;
  private String content_encoding = null;
  private boolean head_request = false;
  private String session_cookie_domain = null;

  private HttpNonblockingTransferTask transfer_task_;
  private static final SecureRandom SECURE_RANDOM = new SecureRandom();
  private static final AtomicBoolean HAVE_SHOWN_COOKIE_VERSION_WARNING
      = new AtomicBoolean();
  private final ServletEngineConfiguration config;


  public HttpResponseImpl(
      HttpConnection conn, HttpRequestImpl req) {
    config = conn.getConfiguration();
    if (isUnsupportedCharset(config.defaultResponseCharacterEncoding())) {
      throw new IllegalStateException(config.defaultResponseCharacterEncoding()
          + " is an unsupported encoding.");
    }
    this.conn = conn;
    this.req = req;
    this.head_request = req != null
        && GSEConstants.HEAD.equals(req.getMethod());

    if (req == null) {
      this.defaultCharset = config.defaultResponseCharacterEncoding();
    } else {
      this.defaultCharset = getDefaultCharset(req._getCharsets());
    }
    propagateOutputErrors = config.propagateOutputErrors();
  }

  public int getStatus() {
    return status;
  }

  public void setVersion(int majorVersion, int minorVersion) {
    this.major = majorVersion;
    this.minor = minorVersion;
  }

  public void setReason(String reason) {
    // Remove all instances of the header delimiter (CR or LF)
    //
    // Uncareful code may directly use client specified data as the value of
    // the HTTP response line's reason. A malicious client can take advantage
    // of this by placing a header delimiter in the data and appending
    // arbitrary headers to it. This escaping prevents such header injection.
    this.reason = StringUtil.collapse(reason, "\r\n", "");
  }

  public String getReason() {
    return reason;
  }

  /**
   * Sets whether or not to compress the response. This value
   * overrides the server-wide setting configured via
   * {@link com.google.opengse.core.HttpServer#setCompressResponses(boolean)}.
   *
   * @param compress
   */
  public void setCompressResponse(boolean compress) {
    if (isCommitted()) {
      throw new IllegalStateException(
          "response committed; cannot set content encoding");
    }
    this.compress_response = compress;
    this.compress_response_set = true;
  }

  /**
   * Can we perform keep-alive on this response?
   */
  public boolean canKeepAlive() {
    // We only perform keep-alive on HTTP/1.0 and HTTP/1.1 responses
    if (major != 1) {
      return false;
    }
    if (closeConnection) {
      return false;
    }
    // We don't perform keep-alive on error responses
    if (status >= 400 && (conn == null || conn.server_.getCloseOnErrors())) {
      return false;
    }

    // if the application said close, respect that
    if ("close".equals(getHeader("Connection"))) {
      return false;
    }

    // Need a valid content length or chunked encoding. The one exception is
    // status codes that don't require any content.
    if (getHeader("Content-Length") != null || isChunkEncoded()
        || !statusAllowsContent(status)) {
      return true;
    }
    return false;
  }

  /**
   * Is this a a keep-alive response. It is only valid to call this
   * method after <code>finish</code> has been called.
   */
  public boolean isKeepAlive() {
    if (major != 1) {
      return false;
    }
    if (closeConnection) {
      return false;
    }

    if (minor == 0) {
      // HTTP/1.0 defaults to closing connections
      if (!headerHasValue("Connection", "keep-alive")) {
        return false;
      }
    } else if (minor == 1) {
      // HTTP/1.1 defaults to keeping connections alive
      if (headerHasValue("Connection", "close")) {
        return false;
      }
    }

    // Check for a mismatch between manually set content length and actual
    // number of bytes being sent.
    if (getContentLength() != contentLength && getContentLength() != -1) {
      if (!head_request) {
        LOGGER.warning("manually set content length header, "
            + getContentLength() + ", does not match actual content length "
            + contentLength);
      }
    }

    // We are keep-alive if the content-length header must match the returned
    // content length, or we must have used the "chunked" transfer
    // encoding. The one exception is status codes that don't require any
    // content.
    if (getContentLength() == contentLength || isChunkEncoded()
        || !statusAllowsContent(status)) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public void print(PrintWriter writer) {
    writer.print("HTTP/" + major + "." + minor + " " + status + " " + reason
        + "\r\n");
    if (major >= 1) {
      super.print(writer);
    } else {
      writer.print("\r\n");
    }
  }

  @Override
  public void writeIOBuffer(IOBuffer buf) throws IOException {
    buf
        .writeBytes(("HTTP/" + major + "." + minor + " " + status + " "
            + reason + "\r\n").getBytes());
    if (major >= 1) {
      super.writeIOBuffer(buf);
    } else {
      buf.writeBytes("\r\n".getBytes());
    }
  }

  /**
   * This method should be called only if we want to make sure that
   * {@link #finish} becomes a no-op. This is the case when writing
   * to {@link HttpConnection} is done directly instead of going through
   * the servlet API.
   * <strong>Caution:</strong> This method has been put in place for a
   * special proxy server and should not be called by normal
   * GSE applications.
   */
  public void setFinished() {
    finished = true;
  }

  /**
   * Specify that errors encountered while sending output to the client should
   * propagate to servlet. Such errors are typically encountered when the
   * client connection is closed mid-stream. Errors are propagated via an
   * unchecked IllegalStateException.
   *
   * @param propagate
   */
  public void setPropagateOutputErrors(boolean propagate) {
    propagateOutputErrors = propagate;
  }

  /**
   * Returns true if the response is set to propagate errors on output to the
   * servlet.
   *
   * @return value of propagate_output_errors.
   */
  public boolean getPropagateOutputErrors() {
    return propagateOutputErrors;
  }

  /**
   * Completes an HTTP response by setting and prepending response headers
   * to the output buffer and flushing it.
   *
   * @param keepAlive is <code>true</code> to allow keep alive connections.
   *                  This value is ignored if the client or response does not
   *                  support keep-alive.
   */
  public void finish(boolean keepAlive) {
    // finished can already be true if the servlet closed its writer or
    // output stream; if this is true, there's no further work to be done
    if (finished == true) {
      return;
    }

    // set the output buffer consume callback to null to avoid consume on flush
    getOutputBuffer();
    output_buf.setConsumeCallback(null);

    try {
      // pre commit if necessary
      if (isCommitted() == false) {
        preCommit(true);
      }

      // flush and encode data
      output_buf.flush();
      encodeData(output_buf, true);

      // prepend response headers if they haven't already been committed
      if (!isCommitted()) {
        // prepend response headers to response body
        IOBuffer headers = prepareHeaders(keepAlive, true);
        output_buf.prepend(headers);
      }

      LOGGER.log(Level.FINEST, "request finished ("
          + output_buf.availableBytes() + " bytes)", output_buf);
    } catch (IOException e) {
      /* ignored */
    }
    // no consume callback, no IOException
  }

  // ServletResponse methods

  public String getCharacterEncoding() {
    // WARNING: Do NOT call this function if the return value is used to set
    //          the encoding scheme of an output buffer to write the response.
    //          Use getInternalCharacterEncoding() instead.
    String encoding = defaultCharset;
    if (specified_charset != null) {
      encoding = specified_charset;
    }
    return encoding;
  }

  public Locale getLocale() {
    return locale;
  }

  public IOBuffer getOutputBuffer() {
    if (output_buf == null) {
      output_buf = getConnectionOutputBuffer();

      // Since the IOBuffer returned here will be used for real output,
      // we should use the encoding returned by getInternalCharacterEncoding(),
      // NOT the one returned by getCharacterEncoding.
      output_buf.setCharacterEncoding(getInternalCharacterEncoding());
      output_buf.setConsumeCallback(this);
      // set default buffer size from the HttpServer object
      output_buf.setSizeLimit(conn.server_.getResponseBufferSize());
    }
    return output_buf;
  }

  // Provided for subclasses used in unittests
  protected IOBuffer getConnectionOutputBuffer() {
    return conn.getOutputBuffer();
  }

  public ServletOutputStream getOutputStream() {
    if (output_writer != null) {
      throw new IllegalStateException("getWriter() called previously");
    }
    if (transfer_task_ != null) {
      throw new IllegalStateException("sendStream() called previously");
    }
    if (output_stream == null) {
      output_stream = new IOBufferOutputStream(getOutputBuffer());
    }
    return output_stream;
  }

  public PrintWriter getWriter() {
    if (output_stream != null) {
      throw new IllegalStateException("getOutputStream() called previously");
    }
    if (transfer_task_ != null) {
      throw new IllegalStateException("sendStream() called previously");
    }
    if (output_writer == null) {
      output_writer = new PrintWriter(new IOBufferWriter(getOutputBuffer()));
    }
    return output_writer;
  }

  public boolean isCommitted() {
    return committed;
  }

  public void reset() {
    status = 0;
    clearHeaders();
    resetBuffer();
  }

  public void resetBuffer() {
    if (isCommitted()) {
      throw new IllegalStateException(
          "response committed; cannot reset buffer");
    }
    if (output_buf != null) {
      output_buf.clear();
    }
    output_writer = null;
    output_stream = null;
    output_buf = null;
  }

  public void setContentLength(int len) {
    setIntHeader("Content-Length", len);
  }

  public int getContentLength() {
    return getIntHeader("Content-Length");
  }

  /**
   * The actual content length, not just the one set in the
   * response headers. This value will be exactly the size of
   * the data sent to the client, assuming there were no errors.
   * If this response is for a HEAD request, the actual content
   * length will be 0. This value is used for statistics and
   * request logging.
   */
  public int getActualContentLength() {
    int bytes = contentLength - discardedBytes;
    return (head_request || bytes < 0) ? 0 : bytes;
  }

  /**
   * Sets the character encoding. Note the the default behavior is to use
   * the most preferred charset specified in the request's Accept-Charset
   * header, so you should rarely need to set this.
   * <p/>
   * This method will also ensure that the specified encoding is acceptable
   * according to the Accept-Charset header of the request. If it is not,
   * a warning will be logged and no change will take effect.
   * <p/>
   * If the specified encoding differs from that of an existing Content-Type,
   * the Content-Type will be updated to reflect the new encoding, as
   * specified in the servlet JavaDoc.
   * <p/>
   * If {@code null} or the empty string is specified, the charset will be
   * removed from the Content-Type header, and the default charset will be used
   * to encode any text response.
   * <p/>
   * NOTE: this method should be called before a call to {@link #getWriter()}.
   */
  public void setCharacterEncoding(String encoding) {
    if (StringUtil.isEmpty(encoding)) {
      specified_charset = null;
    } else if (req != null && !req._acceptsCharset(encoding)) {
      LOGGER.warning("Charset " + encoding + " is not accepted. "
          + "Setting the charset to " + getCharacterEncoding());

      // Although the specified encoding is not supported, the client does
      // want to include the charset attribute in the Content-Type, so we
      // set specified_charset (to a supported encoding) to reflect this.
      specified_charset = getCharacterEncoding();
    } else {
      specified_charset = encoding;
    }

    // If the specified charset is not supported use the default
    if (isUnsupportedCharset(specified_charset)) {
      specified_charset = defaultCharset;
    }

    // We need to modify the Content-Type header as a result.
    String contentTypeString = getContentType();
    if (contentTypeString != null) {
      ContentType ctype = ContentType.parse(contentTypeString);
      if (specified_charset == null) {
        ctype.removeParameter("charset");
      } else {
        ctype.setParameter("charset", specified_charset);
      }
      setHeader("Content-Type", ctype.toString());
    }
  }

  /**
   * Returns the Content-Type header.
   */
  public String getContentType() {
    return getHeader("Content-Type");
  }

  /**
   * Sets the Content-Type header. The character encoding used for the
   * response body is extracted from the content type header. If no
   * character encoding is specified, or the content type isn't
   * parsable, the character encoding will remain as {@code default_charset}
   * by default , but
   * possibly changed via a call to setCharacterEncoding().
   * <p/>
   * If the specified character encoding is not supported according to the
   * request's Accept-Charset header, the charset will be modified to one
   * that is supported.
   * <p/>
   * NOTE: this method should be called before a call to
   * {@link #getWriter()}.
   * NOTE: this method overrides a previous call to
   * {@link #setCharacterEncoding(String)}.
   */
  public void setContentType(String type) {
    ContentType ctype = ContentType.parse(type);
    String charset = ctype.getParameter("charset", null);
    if (charset != null) {
      // Reset the charset to whatever is filtered by the Accept-Charset hdr
      setCharacterEncoding(charset);
      ctype.setParameter("charset", getCharacterEncoding());
    }
    setHeader("Content-Type", ctype.toString());
  }

  public void setLocale(Locale locale) {
    if (locale == null) {
      throw new IllegalArgumentException("setLocale.localenull");
    }
    this.locale = locale;

    String language = locale.getLanguage();
    if ((language != null) && (language.length() > 0)) {
      StringBuffer value = new StringBuffer(language);
      String country = locale.getCountry();
      if ((country != null) && (country.length() > 0)) {
        value.append('-');
        value.append(country);
      }
      setHeader("Content-Language", value.toString());
    }
  }

  // HttpServletResponse methods
  //  (some methods are implemented in MimeHeaders)
  @Override
  public void addHeader(String name, String value) {
    if (isCommitted() == true) {
      LOGGER.warning("header \"" + name + "\" will be ignored; "
          + "response already committed");
      return;
    }

    // 2.3 compliance
    if (isRedirected()) {
      LOGGER.warning("header \"" + name + "\" will be ignored; "
          + "response already redirected");
      return;
    }

    super.addHeader(name, value);
  }

  private boolean isRedirected() {
    return status == HttpServletResponse.SC_MOVED_TEMPORARILY;
  }

  public void sendError(int sc) {
    sendError(sc, null);
  }

  public void sendError(int sc, String msg) {
    // if the response is already committed, except
    if (isCommitted()) {
      throw new IllegalStateException("response committed; cannot send error");
    }
    setStatus(sc, msg);
    setCharacterEncoding(getCharacterEncoding());
    resetBuffer();
  }

  
  public void setStatus(int sc) {
    setStatus(sc, null);
  }

  public void setStatus(int sc, String sm) {
    this.status = sc;
    if (sm == null) {
      this.reason = findReason(sc);
    } else {
      setReason(sm);
    }
  }

  /**
   * Flushes the output buffer, but does not invoke consume so the
   * client won't immediately see the output. This method is used to
   * make the response agnostic about whether it is using a writer or
   * an output stream. Such functionality is necessary to support
   * includes via the request dispatcher; the included servlet may
   * desire an output stream when the original servlet used a writer,
   * and vice versa. This is not necessary for forwards since the
   * response is reset before the forwarded servlet is invoked.<p/>
   * <p/>
   * This method should be used with caution, since it makes it more
   * likely that a writer and an output stream coexist simultaneously,
   * writing to the same output buffer.
   */
  protected void flushOutput() throws IOException {
    if (output_buf != null) {
      output_buf.flush();
      output_writer = null;
      output_stream = null;
      transfer_task_ = null;
    }
  }

  /**
   * Flushes any data written to the output buffer to the client
   * connection.
   *
   * @throws java.io.IOException is thrown on a handleConsume failure
   */
  public void flushBuffer() throws IOException {
    if (output_writer != null) {
      output_writer.flush();
    } else if (output_stream != null) {
      output_stream.flush();
    }
  }

  public int getBufferSize() {
    return getOutputBuffer().getSizeLimit();
  }

  public void setBufferSize(int size) {
    if (isCommitted()) {
      throw new IllegalStateException(
          "response committed; cannot set buffer size");
    }
    if (!output_buf.isEmpty()) {
      throw new IllegalStateException(
          "response written; cannot set buffer size");
    }
    getOutputBuffer().setSizeLimit(size);
  }

  // Utility functions

  /**
   * Consumes the available read data from the provided iobuffer
   * and sends it through the client connection.
   * This method implements the ConsumeCallback interface, to
   * allow a buffer size limit on the response output. When the
   * data written to the output_buf IOBuffer exceeds the buffer size
   * limit, a call to this method is triggered.
   *
   * @param iobuffer is the IOBuffer with the data. This is always
   *                 the same as <code>output_buf</code>
   * @param done     <code>true</code> if this is the last data to consume
   * @throws java.io.IOException is thrown on failure sending data
   */
  public void handleConsume(IOBuffer buffer, boolean done) throws IOException {
    if (buffer != output_buf) {
      throw new IllegalArgumentException();
    }
    // HTTP encode output, adding headers if needed
    prepareOutputBuffer(done);

    // Send the encoded buffer and wait for write to complete.
    sendBuffer(done);
  }

  /**
   * Encodes the contents of the output buffer and initiates a non-blocking
   * write via the HttpConnection. If true is returned then a write was
   * initiated. The caller is responsible for avoiding overlapping writes.
   *
   * @param done true if this is the final write for this response.
   * @return true if a write was initiated, false if there was nothing to write.
   * @throws java.io.IOException
   */
  public boolean flushAsync(boolean done) throws IOException {
    assert (conn.isNonBlocking());

    // HTTP encode output, adding headers if needed
    prepareOutputBuffer(done);

    // This will be our return value, false unless we start a write.
    boolean writeInitiated = false;

    // Send the encoded buffer. The connection is non-blocking, so remember if a
    // write was initiated.
    writeInitiated = sendBuffer(done);
    return writeInitiated;
  }

  /**
   * Invoke HttpConnection.write if needed.
   *
   * @param done true if this is the last of the content to send.
   * @return true if a write was initiated
   */
  private boolean sendBuffer(boolean done) {
    // send data
    boolean writeInitiated = false;
    if (output_buf.availableBytes() > 0) {
      LOGGER.log(Level.FINEST, "sending encoded data ("
          + output_buf.availableBytes() + " bytes)", output_buf);
      // write all available data to connection
      try {
        conn.write(done);
        writeInitiated = true;
      } catch (IOException e) {
        LOGGER.log(Level.FINE, "unable to send data ("
            + output_buf.availableBytes()
            + " bytes) to client; the connection was probably"
            + " closed by the client");
        // Discard remaining data. The client connection is closed, but we want
        // the servlet to continue processing regardless in order to avoid
        // spurious exception reports. Therefore, we discard the bytes that
        // were intended for the client to make it appear to the serlvet as
        // though the bytes were sent.
        discardedBytes += output_buf.availableBytes();
        output_buf.discard(output_buf.availableBytes());

        // If we're configured to propagate output errors, throw an unchecked
        // IllegalStateException with the cause initialized to e.
        if (propagateOutputErrors) {
          throw new IllegalStateException(e);
        }
      }
    }
    return writeInitiated;
  }

  /**
   * Encode the content in the output buffer as appropriate for this HTTP
   * request, adding headers and trailers as needed.
   *
   * @param done true if there is nothing more to add to the response body.
   * @throws java.io.IOException
   */
  private void prepareOutputBuffer(boolean done) throws IOException {
    if (isCommitted() == false) {
      // a member variable is computed for efficiency
      canChunkEncode = canChunkEncode();
      preCommit(done);
    }

    // encode the data (this must be done after preCommit)
    encodeData(output_buf, done);

    // If the headers haven't been written yet, prepare them and prepend to the
    // output_buf.
    if (isCommitted() == false) {
      IOBuffer headers = prepareHeaders(true, done);
      headers.flush();
      output_buf.prepend(headers);
    }
    // remember if we've finalized the encoding here so that it's
    // not done twice when HttpConnection.runServlet calls
    // HttpResponseImpl.finish
    if (done == true) {
      setFinished();
    }
  }

  /**
   * Encodes the data in the provided buffer based on the content
   * codings appropriate for this HTTP response.
   * <p/>
   * The supplied buffer is modified in place.
   *
   * @param iobuffer the IOBuffer with the data to encode
   * @param done     <code>true</code> if this is the last data to encode
   */
  private void encodeData(IOBuffer iobuffer, boolean done) {
    // for a status that disallows content, clear iobuffer and log warning
    if (!statusAllowsContent(status) && iobuffer.availableBytes() > 0) {
      LOGGER.log(Level.WARNING, "cannot write content to a response with "
          + "HTTP status " + status + "; skipping...", iobuffer);
      iobuffer.discard(iobuffer.availableBytes());
      return;
    }

    // if gzip content coding is enabled, compress iobuffer
    if ("gzip".equals(content_encoding) == true) {
      gzipEncodeData(iobuffer, done);
    } else if ("deflate".equals(content_encoding) == true) {
      deflateEncodeData(iobuffer, done);
    }

    // if the response is partial content, verify no compression or chunking
    // and encode data according to RFC 2616, section 19.2.
    if (isPartialContent()) {
      try {
        if (canChunkEncode != false) {
          throw new IllegalStateException();
        }
        if (content_encoding != null) {
          throw new IllegalStateException();
        }
        partialContentEncodeData(iobuffer, done);
      } catch (IllegalStateException e) {
        LOGGER.log(Level.WARNING,
            "unable to complete partial content request; done=" + done
                + ", range-content-length=" + rangeContentLength
                + ", range-current-pos=" + rangeCurrentPos + ", range="
                + range, e);
        sendErrorAndCloseConnection(
            HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
        iobuffer.discard(iobuffer.availableBytes());
        return;
      }
    }

    // if the request is already committed (the headers have been written)
    // and chunked encoding is possible, encode iobuffer as chunked.
    if ((isCommitted() == true || done == false) && canChunkEncode == true) {
      chunkEncodeData(iobuffer, done);
    }

    // TODO(pmattis): SRV.5.5 of the servlet spec (version 2.3) says
    // that the servlet is considered to have satisfied the request if
    // the amount of content specified in a call to setContentLength()
    // is written to the response. It doesn't say way to do with any
    // additional content, but the implication is to not send
    // it. Currently we do. It would be nice to figure out if this is
    // terribly bad or not.

    // add in -or- set the new content length as appropriate
    contentLength += iobuffer.availableBytes();

    // if this is a head request, clear iobuffer so no data is sent
    // this is done late because all headers must match the equivalent
    // GET request exactly.
    if (head_request == true) {
      iobuffer.discard(iobuffer.availableBytes());
    }
  }

  /**
   * Sets the response status to error code "ec" and sets the "Connection"
   * header to "close", if the response has not been committed. If the response
   * has been committed, the connection will still be closed when
   * HttpConnection.finishRequest() is invoked.
   */
  private void sendErrorAndCloseConnection(int ec) {
    if (!isCommitted()) {
      setStatus(ec);
      setCharacterEncoding(getCharacterEncoding());
      setHeader("Connection", "close");
    }
    closeConnection = true;
  }

  /**
   * Gzips iobuffer data.
   */
  private void gzipEncodeData(IOBuffer iobuffer, boolean done) {
    try {
      if (deflater_output_buf == null) {
        deflater_output_buf = new IOBuffer();
        deflater_output_stream = new ThriftyGZIPOutputStream(
            new IOBufferOutputStream(deflater_output_buf));
      }
      compressEncodeData(iobuffer, done);
    } catch (IOException e) {
      LOGGER.warning("unable to create deflater");
    }
  }

  /**
   * Helper class that immediately frees deflater resource on close
   */
  private class ThriftyGZIPOutputStream extends GZIPOutputStream {
    public ThriftyGZIPOutputStream(OutputStream os) throws IOException {
      super(os);
    }

    @Override
    public void finish() throws IOException {
      super.finish();
      def.end();
    }

    @Override
    public void close() throws IOException {
      super.finish();
      def.end();
    }
  }

  /**
   * Deflates iobuffer data.
   */
  private void deflateEncodeData(IOBuffer iobuffer, boolean done) {
    if (deflater_output_buf == null) {
      deflater_output_buf = new IOBuffer();
      deflater_output_stream = new ThriftyDeflaterOutputStream(
          new IOBufferOutputStream(deflater_output_buf));
    }
    compressEncodeData(iobuffer, done);
  }

  /**
   * Helper class that immediately frees deflater resource on close
   */
  private class ThriftyDeflaterOutputStream extends DeflaterOutputStream {
    public ThriftyDeflaterOutputStream(OutputStream os) {
      super(os);
    }

    @Override
    public void finish() throws IOException {
      super.finish();
      def.end();
    }

    @Override
    public void close() throws IOException {
      super.finish();
      def.end();
    }
  }

  /**
   * Compresses iobuffer data in place using either 'gzip' or
   * 'deflate'. The name 'compress' does not imply the LZW format
   * UNIX compress algorithm. It's just a generic term to describe
   * either deflate or gzip
   *
   * @param iobuffer the IOBuffer with the data to encode
   * @param done     <code>true</code> if this is the last data to encode
   */
  private void compressEncodeData(IOBuffer iobuffer, boolean done) {
    try {
      // compress iobuffer by writing read buffer contents to deflater stream
      ByteBuffer byteBuffer;
      while ((byteBuffer = iobuffer.getReadBuffer()) != null) {
        byte[] buf = byteBuffer.array();
        int o = byteBuffer.arrayOffset();
        int s = byteBuffer.position();
        int e = byteBuffer.limit();
        deflater_output_stream.write(buf, o + s, (e - s));
        byteBuffer.position(e);
        iobuffer.releaseReadBuffer();
      }

      // if this is the last bit of data, finish all deflating by closing
      if (done == true) {
        deflater_output_stream.close();
      } else {
        deflater_output_stream.flush();
      }

      // transfer the compressed data back by prepending it to the iobuffer
      if (deflater_output_buf.isEmpty() == false) {
        iobuffer.prepend(deflater_output_buf);
        // clear deflater_output_buf of contents
        deflater_output_buf.clear();
      }
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, "error encoding buffer", e);
    }
  }

  /**
   * Encodes data as a partial content response. If there is a single range,
   * the data before and after the range is skipped. If there are multiple
   * ranges, then the data for each range is encoded as multipart/byteranges.
   *
   * @param iobuffer the IOBuffer with the data to encode
   * @param done     <code>true</code> if this is the last data to encode
   */
  private void partialContentEncodeData(IOBuffer iobuffer, boolean done) {
    try {
      int len = iobuffer.availableBytes();
      IOBuffer newIOBuffer = new IOBuffer();

      while (len > 0) {
        // If we're already past the last range, simply skip data.
        if (rangeIndex == range.getNumRanges()) {
          iobuffer.skipBytes(len);
          rangeCurrentPos += len;
          len = 0;
          continue;
        }
        Range.Pair p = range.getRange(rangeIndex, rangeContentLength);
        // If necessary, skip to the start of the current range.
        int skip = p.getStart() - rangeCurrentPos;
        if (skip > 0) {
          int skipped = (int) iobuffer.skipBytes(skip);
          rangeCurrentPos += skipped;
          len -= skipped;
          // If there is no data left, continue.
          if (len == 0) {
            continue;
          }
        }
        // The byte range is inclusive, so we must transfer 1 + getEnd().
        int transfer = Math.min((p.getEnd() - rangeCurrentPos + 1), len);
        if (transfer < 0) {
          throw new IllegalStateException();
        }

        if (transfer > 0) {
          // If we're at the start of a multipart byterange, output boundary.
          if (range.getNumRanges() > 1 && rangeCurrentPos == p.getStart()) {
            // It's ok for additional CRLFs to preceed first boundary.
            newIOBuffer.writeBytes("\r\n--".getBytes());
            newIOBuffer.writeBytes(rangeBoundary.getBytes());
            if (rangeContentType != null) {
              newIOBuffer.writeBytes("\r\n".getBytes());
              newIOBuffer.writeBytes("Content-Type: ".getBytes());
              newIOBuffer.writeBytes(rangeContentType.getBytes());
            }
            newIOBuffer.writeBytes("\r\n".getBytes());
            newIOBuffer.writeBytes("Content-Range: ".getBytes());
            newIOBuffer.writeBytes(("bytes " + p.getStart() + "-" + p.getEnd()
                + "/" + rangeContentLength).getBytes());
            newIOBuffer.writeBytes("\r\n\r\n".getBytes());
          }
          newIOBuffer.transfer(iobuffer, transfer);
          rangeCurrentPos += transfer;
          len -= transfer;
        }
        // Advance to the next range if we completed the current one.
        if (rangeCurrentPos == (p.getEnd() + 1)) {
          rangeIndex += 1;
        }
      }

      if (done && !head_request) {
        // Sanity checks
        if (rangeIndex < range.getNumRanges()) {
          Range.Pair p = range.getRange(rangeIndex, rangeContentLength);
          if (p.getStart() != rangeContentLength) {
            throw new IllegalStateException();
          }
        } else {
          if (rangeIndex != range.getNumRanges()) {
            throw new IllegalStateException();
          }
        }
        // If we encoded as multipart/byteranges, output final boundary.
        if (range.getNumRanges() > 1) {
          newIOBuffer.writeBytes("\r\n--".getBytes());
          newIOBuffer.writeBytes(rangeBoundary.getBytes());
          newIOBuffer.writeBytes("--\r\n".getBytes());
        }
      }

      // Prepend new iobuffer
      if (iobuffer.availableBytes() != 0) {
        throw new IllegalStateException();
      }
      if (newIOBuffer.isEmpty() == false) {
        iobuffer.prepend(newIOBuffer);
      }
    } catch (IOException e) {
      // should never reach this statement. IOException in IOBuffers only are
      // thrown from handling consume callbacks. new_iobuf doesn't have a
      // consume callback and iobuffer.prepend() will never trigger one.
      LOGGER.log(Level.SEVERE, "unexpected IOException chunk coding buffer",
          iobuffer);
      throw new AssertionError(e);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Chunk encodes iobuffer data in place.
   *
   * @param iobuffer the IOBuffer with the data to encode
   * @param done     <code>true</code> if this is the last data to encode
   */
  private void chunkEncodeData(IOBuffer iobuffer, boolean done) {
    try {
      int len = iobuffer.availableBytes();
      IOBuffer tmpBuffer = new IOBuffer();

      if (len > 0) {
        String stringLength = Integer.toHexString(len).toUpperCase();

        tmpBuffer.writeBytes(stringLength.getBytes());
        tmpBuffer.writeBytes("\r\n".getBytes());
        tmpBuffer.transfer(iobuffer, len);
        tmpBuffer.writeBytes("\r\n".getBytes());

        // should have transferred all available bytes
        if (iobuffer.availableBytes() != 0) {
          throw new IllegalStateException();
        }
      }

      // if this is the last chunk in the response, append
      // 1*("0") CRLF CRLF, as per specification
      if (done) {
        tmpBuffer.writeBytes("0\r\n\r\n".getBytes());
      }

      // prepend new iobuffer
      if (!tmpBuffer.isEmpty()) {
        iobuffer.prepend(tmpBuffer);
      }
    } catch (IOException e) {
      // should never reach this statement. IOException in IOBuffers only are
      // thrown from handling consume callbacks. new_iobuf doesn't have a
      // consume callback and iobuffer.prepend() will never trigger one.
      LOGGER.log(Level.SEVERE, "unexpected IOException chunk coding buffer",
          iobuffer);
      throw new AssertionError(e);
    }
  }

  /**
   * Returns whether we can compress for the client based on the
   * user-agent, the accept encoding, and the content-type?
   * <p/>
   * For more details on how this algorithm was arrived at,
   * consult io/httpserverconnection.cc#CanCompressForClient
   *
   * @param coding the compression coding used (e.g. gzip)
   * @return <code>true</code> if we can gzip/deflate the response.
   */
  protected boolean canCompressForClient(String coding) {
    // if status disallows content, no compression
    if (statusAllowsContent(status) == false) {
      return false;
    }

    // do not compress partial content responses
    if (isPartialContent() == true) {
      return false;
    }

    String userAgent = req.getHeader("User-Agent");
    String type = getHeader("Content-Type");
    if (userAgent == null || type == null) {
      return false;
    }

    // extract the actual type from the content type header
    ContentType ctype = ContentType.parse(type);
    type = ctype.getType("text/html");

    // check if client accepts the encoding
    if (!req._acceptsEncoding(coding)) {
      return false;
    }

    // check for clients which handle compression properly
    if ((!userAgent.contains("Mozilla/") || userAgent.contains("Mozilla/4.0"))
        && userAgent.contains(" MSIE ")
        && !userAgent.contains("Opera")
        && !userAgent.contains(" Gecko/")
        && !userAgent.contains(" Safari/")
        && !userAgent.contains("gzip")) {
      return false;
    }

    // Don't compress css/javascript for anything but browsers we
    // trust - currently IE, Opera, Mozilla, and safari.  This list
    // should be kept in sync with C++, net/httpserverconnection.cc
    if (("text/css".equals(type)
          || "text/javascript".equals(type)
          || "application/x-javascript".equals(type)
          || "application/json".equals(type))
        && !userAgent.contains(" MSIE ")
        && !userAgent.contains("Opera")
        && !userAgent.contains(" Gecko/")
        && !userAgent.contains(" Safari/")) {
      return false;
    }

    // othewise, compress all text/ content types and
    // several application types that we allow to be compressed.
    return type.startsWith("text/")
        || (type.startsWith("application/") && (type.endsWith("/x-javascript")
        || type.endsWith("+xml")
        || type.endsWith("/csv")
        || type.endsWith("/json")));
  }

  private static final Comparator<AcceptHeader> ACCEPT_HEADER_COMPARATOR
  = new Comparator<AcceptHeader>() {
    public int compare(AcceptHeader ah1, AcceptHeader ah2) {
      if (ah1 == ah2) {
        return 0;
      }
      if (ah1 == null) {
        return 1;
      }
      if (ah2 == null) {
        return -1;
      }
      double q1 = ah1.getQuality();
      double q2 = ah2.getQuality();
      if (q2 < q1) {
        return -1;
      }
      if (q2 > q1) {
        return 1;
      }
      return 0;
    }
  };

  /**
   * Returns the best compression encoding for the client based
   * on what's supported, what'll work with the user agent and
   * what the client prefers.
   *
   * @return the best compression encoding for this client or
   *         <code>null</code> if none.
   */
  protected String getCompressEncodingForClient() {
    AcceptHeader[] encodings = {
          req._getAcceptEncoding("gzip"),
          req._getAcceptEncoding("deflate")
        };

    // sort in descending order of quality
    Arrays.sort(encodings, ACCEPT_HEADER_COMPARATOR);

    for (AcceptHeader encoding : encodings) {
      if (encoding != null && canCompressForClient(encoding.getType())) {
        return encoding.getType();
      }
    }
    return null;
  }

  /**
   * Can we perform chunked encoding on this response?
   */
  private boolean canChunkEncode() {
    // if status disallows content, no chunking
    if (statusAllowsContent(status) == false) {
      return false;
    }
    // if the content length has been manually set, don't chunk
    if (getContentLength() != -1) {
      return false;
    }
    // if less than HTTP/1.1 response, accept-encoding
    // chunked must be present
    AcceptHeader ae = req._getAcceptEncoding("chunked");
    if (major < 1 || (major == 1 && minor < 1)) {
      // if chunked encoding has been specified and is accepted, yes
      return (ae != null && ae.getQuality() > 0.0);
    } else {
      // check if chunked encoding is explicitly declined
      return !(ae != null && ae.getQuality() == 0.0);
    }
  }

  /**
   * Are we returning chunk encoded data?
   */
  private boolean isChunkEncoded() {
    return "chunked".equals(getHeader("Transfer-Encoding"));
  }

  /**
   * Is this a valid range request? This requires that the response code be 200
   * (SC_OK), the content length has been manually set, and a valid Range
   * request was provided by the client. Valid range requests have a
   * syntactically correct range specification that contains only monotonically
   * increasing, non-overlapping ranges.
   * <p/>
   * This method initializes the range member variable on success. The
   * caller is responsible for changing the status to SC_PARTIAL_CONTENT as
   * appropriate.
   *
   * @return <code>true</code> if the request is a valid range request and the
   *         response will be partial content, if satisfiable.
   */
  private boolean isRangeRequest() {
    if (status != HttpServletResponse.SC_OK || req == null) {
      return false;
    }
    if ((range = req._getRange()) == null) {
      return false;
    }
    rangeContentLength = getContentLength();
    rangeContentType = getContentType();
    if (rangeContentLength == -1) {
      return false;
    }
    if (range.getNumRanges() > 1) {
      rangeBoundary = generateRangeBoundary();
    }
    if (range != null && range.isValid(rangeContentLength)) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Returns a randomly generated string for use as a range boundary in the
   * case of a range request for multiple ranges.
   *
   * @return random boundary string suitable for use as a separator between
   *         multipart/byteranges data.
   */
  private static synchronized String generateRangeBoundary() {
    byte[] randomBytes = new byte[NUM_BOUNDARY_BYTES];
    SECURE_RANDOM.nextBytes(randomBytes);
    return Base64.encodeWebSafe(randomBytes, false);
  }

  /**
   * Is this a partial content response?
   */
  private boolean isPartialContent() {
    return status == HttpServletResponse.SC_PARTIAL_CONTENT
        || status == HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE;
  }

  /**
   * Called before committing the response to the client. This method
   * is called in finish() and handleConsume(). Code that is
   * appropriate here includes anything that must be done both before
   * prepareHeaders() and encodeData() are called.
   *
   * @param done <code>true</code> if the response is finished; no more
   *             output will be written
   */
  private void preCommit(boolean done) {
    // things to do if the response is done
    if (done) {
      // if the status is not OK and nothing has been written to
      // the output buffer, output HTML describing the error condition.
      // Note that an unset status is considered "OK" here.
      if (status != 0
          && status != HttpServletResponse.SC_OK
          && output_buf.isEmpty()) {
        try {
          // if done is true, no consume callback is possible so no IOException
          outputDefaultError();
        } catch (IOException e) {
          /* ignored */
        }
      }
    }
    // If no status code has been specified, then default to 200 (SC_OK)
    if (status == 0) {
      setStatus(HttpServletResponse.SC_OK);
    }

    // Check if this is a satisfiable range request. If so, set status to
    // partial content.
    if (isRangeRequest()) {
      if (range.isSatisfiable(rangeContentLength)) {
        setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
      } else {
        setStatus(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
      }
    }

    // if the compression policy is not set, use the server's default
    if (!compress_response_set && conn != null) {
      compress_response = conn.server_.getCompressResponses();
    }

    // only compress for client if output buffer is not empty (this will
    // disable compression for requests which flush before output is written)
    if (compress_response && !output_buf.isEmpty()) {
      // figure out the preferred compression ordering
      content_encoding = getCompressEncodingForClient();
      if (content_encoding != null) {
        setHeader("Content-Encoding", content_encoding);
        // we have to remove any manually specified content length
        // header as there is no way the user could know what the
        // content length would be after the compression is performed
        removeHeader("Content-Length");
      }
    }

    // if the response is not done, add chunked transfer-encoding
    if (done == false && canChunkEncode == true) {
      setHeader("Transfer-Encoding", "chunked");
    }
  }

  /**
   * Adds appropriate "Content-Length", "Connection" and "Keep-Alive" headers.
   * In the case of a partial content response, appropriate "Content-Range"
   * or "Content-Type" headers are added.
   *
   * @param keepAlive is {@code true} to allow keep alive connections.
   *                  This value is ignored if the client or response does not
   *                  support keep-alive.
   * @param done      is {@code true} to indicate that no other data will
   *                  be written to the output buffer. If {@code false}, then
   *                  the response will be streamed without benefit of a
   *                  content-length header. This could either be streaming
   *                  with a connection close or using chunked transfer coding.
   *
   * @return the HTTP status and headers as an IOBuffer
   */
  private IOBuffer prepareHeaders(boolean keepAlive, boolean done) {
    // Add the Date header
    setDateHeader("Date", System.currentTimeMillis());

    // Some network caches handle cache control headers incorrectly, so
    // we can't send things out with cache-control: public and a set-cookie
    // header.  If we do, people may end up seeing other people's data.
    // - seidl
    stripDangerousHeaders();

    if (!statusAllowsContent(status)) {
      // Responses that do not allow content must not have
      // Content-Type or Content-Length headers.
      removeHeader("Content-Type");
      removeHeader("Content-Length");

    } else {
      // Appropriately set default headers for responses that have content

      // Set the caching policy, unless the servlet set one explicitly
      if (conn != null && !containsHeader("Cache-control")) {
        setHeader("Cache-control", conn.server_.getDefaultCachePolicy());
      }

      // If all data is written, the content length is known; set it.
      if (done == true) {
        int manualContentLength = getContentLength();
        setContentLength(contentLength);

        if (manualContentLength != -1
            && manualContentLength != contentLength) {
          if (head_request && contentLength == 0) {
            // By default, HEAD requests write no data and manually set the
            // Content-Length header, in which case the manual content length is
            // authoritative.
            setContentLength(manualContentLength);
          } else {
            LOGGER.warning("content length mismatch: manually set "
                + manualContentLength + " does not match actual "
                + contentLength);
          }
        }
      } else {
        // The Content-Length header might have been set manually. We
        // don't touch it asssuming that the user knows what they are
        // doing. But we do verify that the manually specified content
        // length was the real content length in isKeepAlive().
      }

      // If this is a partial content response, add appropriate headers.
      if (isPartialContent()) {
        if (range == null) {
          throw new IllegalStateException();
        }
        if (rangeContentLength == -1) {
          throw new IllegalStateException();
        }
        if (!range.isSatisfiable(rangeContentLength)) {
          // An unsatisfiable range generates no actual data,
          // but the client is informed of the actual entity size.
          setHeader("Content-Range", "bytes */" + rangeContentLength);
          setContentLength(0);
        } else if (range.getNumRanges() == 1) {
          Range.Pair p = range.getRange(0, rangeContentLength);
          setHeader("Content-Range", ("bytes " + p.getStart() + "-"
              + p.getEnd() + "/" + rangeContentLength));
          // Now, reset the content length to the actual number of bytes in the
          // range. The byte positions are inclusive, so add one for content
          // length.
          setContentLength(p.getEnd() - p.getStart() + 1);
        } else {
          if (range.getNumRanges() <= 1) {
            throw new IllegalStateException();
          }
          // Remove the content length header as content lengths are provided
          // for each byterange multipart.
          removeHeader("Content-Length");
          setContentType("multipart/byteranges; boundary=" + rangeBoundary);
        }
      }

      // Add the character encoding to the content type header if it is not
      // already specified and the character encoding set by the servlet
      // differs from the assumed encoding.
      String contentTypeString = getHeader("Content-Type");
      if (contentTypeString != null) {
        //NOTE: only set charset parameter for "text/..." content types
        //Otherwise some applications which expect an "application/..."
        //content type will become confused.
        ContentType ctype = ContentType.parse(contentTypeString);
        if (ctype.getParameter("charset", null) == null
            && specified_charset != null) {
          setHeader("Content-Type",
              contentTypeString + "; charset=" + specified_charset);
        }
      }
    }

    if (req != null) {
      // Add keepalive headers...or not
      if (keepAlive && req._canKeepAlive() && canKeepAlive()) {
        setHeader("Connection", "keep-alive");
        setHeader("Keep-Alive",
            "timeout=" + (conn.server_.getKeepaliveTimeout() / 1000));
      } else {
        setHeader("Connection", "close");
      }
    }

    // Mark the request committed
    committed = true;

    // write response header
    IOBuffer headers = new IOBuffer();
    try {
      // An IOBuffer without a consume callback (headers)
      // cannot throw an IOException; ignore the possibility
      writeIOBuffer(headers);
    } catch (IOException e) {
      /* ignored */
    }

    return headers;
  }


  /**
   * Strip any dangerous header combinations before we send the response
   * out on the wire.  This first came up because some network caches were
   * caching content with cache-control: public and a set cookie, and
   * sending the cookie out to people who weren't the original requester.
   * This lead to a massive potential data leak (users trivially seeing other
   * user's files).  Other dangerous header combinations can also be stripped
   * in here if they are discovered, and before our other tools can be changed
   * to detect and warn about them.
   */
  private void stripDangerousHeaders() {

    /* Remove a public cache control header if we're setting a cookie */
    String cookieHeader = getHeader("Set-Cookie");
    if (cookieHeader != null && cookieHeader.length() > 0) {
      String cacheControl = getHeader("Cache-Control");
      if (cacheControl != null
          && cacheControl.toLowerCase().contains("public")) {
        String newCacheControlPolicy = "private";
        String base = "cache-control: public header " + "to "
            + newCacheControlPolicy + " because of a set-cookie header."
            + (req == null ? " " : "[" + req.getRequestURL().toString() + "] ")
            + new Throwable();
        if (config.fixCookieCacheHeaders()) {
          if (conn != null && conn.server_ != null) {
            newCacheControlPolicy = conn.server_.getDefaultCachePolicy();
          }
          setHeader("Cache-Control", newCacheControlPolicy);
          LOGGER.log(Level.INFO, "Changing " + base);
        } else {
          LOGGER.log(Level.WARNING, "NOT changing dangerous " + base
              + "; consider --fix_cookie_cache_headers");
        }
      }
    }
  }


  /**
   * Get the actual character encoding that will be used for output.
   * This may differ from the encoding returned by
   * {@link #getCharacterEncoding}
   * <p/>
   * NOTE: Use this instead of <code>getCharacterEncoding</code>
   * to set the encoding scheme of an output buffer.
   */
  private String getInternalCharacterEncoding() {
    String defaultEncoding = getCharacterEncoding();

    return defaultEncoding;
  }

  /**
   * Returns an appropriate domain for the session cookie.
   * If no domain was set in the HttpServer, this method
   * will return null. Otherwise, the HttpServer domain is
   * examined to determine an appropriate value as follows:
   * <p/>
   * <li>If the server domain does not end with a '.', then
   * the server domain is returned verbatim.
   * <p/>
   * <li>If the server domain ends with a '.', and the HTTP/1.1
   * Host header is available, the substring of the
   * Host header starting at the server domain is returned.
   * <p/>
   * For example, if the server domain is set to '.google.'
   * <pre>
   * HTTP/1.1 Host      Session cookie domain
   * www.google.co.uk   .google.co.uk
   * www.google.ca      .google.ca
   * ads.google.com     .google.com
   * </pre>
   * <p/>
   * If the request isn't HTTP/1.1 or greater, and the server
   * domain ends with '.', then null is returned.
   *
   * @return an appropriate cookie domain
   */
  private String getSessionCookieDomain() {
    if (session_cookie_domain != null) {
      return session_cookie_domain;
    }
    String serverDomain = conn.server_.getSessionCookieDomain();
    return getResolvedCookieDomain(serverDomain);
  }

  /**
   * The equivalent of getSessionCookieDomain() for secure cookies
   *
   * @see #getSessionCookieDomain()
   */
  private String getSecureSessionCookieDomain() {
    String serverDomain = conn.server_.getSecureSessionCookieDomain();
    return getResolvedCookieDomain(serverDomain);
  }

  /**
   * Resolve the given cookie domain with respect to the requesting host name
   * according to rules described in {@link
   * com.google.opengse.httputil.HttpUtil#resolveCookieDomain(String, String)}
   *
   * @return an appropriate cookie domain (or null if cookieDomain is null)
   * @see
   * com.google.opengse.httputil.HttpUtil#resolveCookieDomain(String, String)
   */
  protected String getResolvedCookieDomain(String cookieDomain) {
    if (cookieDomain == null) {
      return null;
    }

    return HttpUtil.resolveCookieDomain(cookieDomain, req.getHeader("Host"));
  }

  /**
   * Sets the domain to be used for session cookies.  This should normally
   * be configured globally with the --session_cookie_domain flag,
   * but this method is provided for applications that need to serve on
   * multiple domains and the getSessionCookieDomainForHost logic is
   * inadequate.
   */
  public void setSessionCookieDomain(String sessionCookieDomain) {
    this.session_cookie_domain = sessionCookieDomain;
  }

  /**
   * Outputs a default error message in HTML
   */
  public void outputDefaultError() throws IOException {
    // Only output an HTML error page on unrecoverable HTTP errors
    // (4xx-5xx); also output redirect page for 3xx
    if (statusAllowsContent(status) && (status >= 300)) {
      // must set charset to avoid any possible XSS in IE.
      setContentType("text/html; charset=UTF-8");

      StringBuilder buf = new StringBuilder();
      buf.append("<HTML>\n");
      buf.append("<HEAD>\n");
      buf.append("<TITLE>");
      buf.append(StringUtil.htmlEscape(reason));
      buf.append("</TITLE>\n");
      buf.append("</HEAD>\n");
      buf.append("<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">\n");
      buf.append("<H1>");
      buf.append(StringUtil.htmlEscape(reason));
      buf.append("</H1>\n");
      String location = getHeader("Location");
      if ((status < 400) && (location != null)) { // 3xx
        // a security check to avoid redirection to malicious urls
        // for eg: "data:", "javascript:", etc.,
        if (isMalformedLocation(location)) {
          buf.append("The document has moved to "
              + StringUtil.htmlEscape(location));
        } else {
          buf.append("The document has moved <A HREF=\"");
          buf.append(StringUtil.htmlEscape(location));
          buf.append("\">here</A>.\n");
        }
      } else { // 4xx - 5xx
        buf.append("<H2>Error ");
        buf.append(status);
        buf.append("</H2>\n");
      }
      buf.append("</BODY>\n");
      buf.append("</HTML>\n");
      // Ensure that output_buf is initialized.
      getOutputBuffer();
      // clear the output buf to clean out any existing char_write_buf
      output_buf.clear();
      // write the default output
      output_buf.writeBytes(buf.toString().getBytes("ASCII"));
    }
  }

  /**
   * A url is malformed for redirection if it is a non-http(s) url
   *
   * @return true if a non-http(s) url is encountered,
   *         false if it is a relative url or an absolute http(s) url
   */
  static boolean isMalformedLocation(String location) {
    URI uri;
    try {
      uri = new URI(location);
    } catch (URISyntaxException e) {
      LOGGER.log(Level.SEVERE, "Redirection URL not well formed", e);
      return true;
    }
    if (uri.isAbsolute() && !uri.getScheme().equalsIgnoreCase(HTTP)
        && !uri.getScheme().equalsIgnoreCase(HTTPS)) {
      return true;
    }
    return false;
  }

  /**
   * Returns whether the specified status is compatible with
   * a non-empty content body
   */
  private static final boolean statusAllowsContent(int status) {
    return status != HttpServletResponse.SC_NOT_MODIFIED
        && status != HttpServletResponse.SC_NO_CONTENT
        && status != HttpServletResponse.SC_CONTINUE
        && status != HttpServletResponse.SC_SWITCHING_PROTOCOLS;
  }

  /**
   * Finds a suitable HTTP reason phrase given an HTTP status code.
   */
  private static final String findReason(int status) {
    switch (status) {
      case HttpServletResponse.SC_CONTINUE:
        return "Continue";
      case HttpServletResponse.SC_SWITCHING_PROTOCOLS:
        return "Switching Protocols";
      case HttpServletResponse.SC_OK:
        return "OK";
      case HttpServletResponse.SC_CREATED:
        return "Created";
      case HttpServletResponse.SC_ACCEPTED:
        return "Accepted";
      case HttpServletResponse.SC_NON_AUTHORITATIVE_INFORMATION:
        return "Non-Authoritative Information";
      case HttpServletResponse.SC_NO_CONTENT:
        return "No Content";
      case HttpServletResponse.SC_RESET_CONTENT:
        return "Reset Content";
      case HttpServletResponse.SC_PARTIAL_CONTENT:
        return "Partial Content";
      case HttpServletResponse.SC_MULTIPLE_CHOICES:
        return "Multiple Choices";
      case HttpServletResponse.SC_MOVED_PERMANENTLY:
        return "Moved Permanently";
      case HttpServletResponse.SC_MOVED_TEMPORARILY:
        return "Moved Temporarily";
      case HttpServletResponse.SC_SEE_OTHER:
        return "See Other";
      case HttpServletResponse.SC_NOT_MODIFIED:
        return "Not Modified";
      case HttpServletResponse.SC_USE_PROXY:
        return "Use Proxy";
      case HttpServletResponse.SC_BAD_REQUEST:
        return "Bad Request";
      case HttpServletResponse.SC_UNAUTHORIZED:
        return "Unauthorized";
      case HttpServletResponse.SC_PAYMENT_REQUIRED:
        return "Payment Required";
      case HttpServletResponse.SC_FORBIDDEN:
        return "Forbidden";
      case HttpServletResponse.SC_NOT_FOUND:
        return "Not Found";
      case HttpServletResponse.SC_METHOD_NOT_ALLOWED:
        return "Method Not Allowed";
      case HttpServletResponse.SC_NOT_ACCEPTABLE:
        return "Not Acceptable";
      case HttpServletResponse.SC_PROXY_AUTHENTICATION_REQUIRED:
        return "Proxy Authentication Required";
      case HttpServletResponse.SC_REQUEST_TIMEOUT:
        return "Request Timeout";
      case HttpServletResponse.SC_CONFLICT:
        return "Conflict";
      case HttpServletResponse.SC_GONE:
        return "Gone";
      case HttpServletResponse.SC_PRECONDITION_FAILED:
        return "Precondition Failed";
      case HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE:
        return "Request Entity Too Large";
      case HttpServletResponse.SC_REQUEST_URI_TOO_LONG:
        return "Request-URI Too Long";
      case HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE:
        return "Unsupported Media Type";
      case HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE:
        return "Requested Range Not Satisfiable";
      case HttpServletResponse.SC_INTERNAL_SERVER_ERROR:
        return "Internal Server Error";
      case HttpServletResponse.SC_NOT_IMPLEMENTED:
        return "Not Implemented";
      case HttpServletResponse.SC_BAD_GATEWAY:
        return "Bad Gateway";
      case HttpServletResponse.SC_SERVICE_UNAVAILABLE:
        return "Service Unavailable";
      case HttpServletResponse.SC_GATEWAY_TIMEOUT:
        return "Gateway Timeout";
      case HttpServletResponse.SC_HTTP_VERSION_NOT_SUPPORTED:
        return "HTTP Version Not Supported";
        // Defined in rfc2518 (section 10)
      case 102:
        return "Processing";
      case 207:
        return "Multi-Status";
      case 422:
        return "Unprocessable Entity";
      case 423:
        return "Locked";
      case 424:
        return "Failed Dependency";
      case 507:
        return "Insufficient Storage";
      default:
        return "unknown";
    }
  }

  /**
   * Protected method used only for testing to set the
   * output buffer so that a NetConnection is not necessary
   * to use the HttpResponse object.
   */
  protected void setOutputBuffer(IOBuffer buf) {
    this.output_buf = buf;
    this.output_buf.setConsumeCallback(this);
  }

  /**
   * Returns a charset supported by the client, according to the
   * Accept-Charset header of the request. If none of the requested charsets
   * is supported by Java, we default to the default encoding.
   *
   * <p/>
   * The algorithm looks among all of the charsets and chooses the earliest
   * listed charset that is not a subset of any other one.
   * <p/>
   * The "quality" field is mostly ignored (except if the quality is 0),
   * since it's not possible to compare two non-equivalent charsets based on
   * their quality.
   */
  String getDefaultCharset(List<AcceptHeader> preferredCharsets) {
    // "best" charset we've seen so far (subsumes any previous best)
    Charset bestCharset = null;

    for (AcceptHeader preferredCharset : preferredCharsets) {

      if (preferredCharset.getQuality() > 0) {
        String charset = preferredCharset.getType();
        if ("*".equals(charset)) {
          // See how the default encoding fairs among the others
          charset = config.defaultResponseCharacterEncoding();
        }
        boolean charsetSupported = false;
        try {
          charsetSupported = Charset.isSupported(charset);
        } catch (IllegalCharsetNameException icne) {
          LOGGER.log(Level.INFO, "Ignoring illegal charset \"{0}\"", charset);
        }
        if (charsetSupported) {
          Charset candidate = Charset.forName(charset);

          // First charset we've seen, or ...
          if (bestCharset == null ||

              // this candidate is a superset of the best.
              (candidate.contains(bestCharset) && !bestCharset
                  .contains(candidate))) {

            bestCharset = candidate;
          }
        }
      }
    }
    if (bestCharset != null) {
      String charset = bestCharset.name();
      if (isUnsupportedCharset(charset)) {
        return config.defaultResponseCharacterEncoding();
      }
      return charset;
    }
    return config.defaultResponseCharacterEncoding();
  }

  /**
   * Check to see if the given charset is unsupported.
   * Only null values and ISO-2022-CN is considered unsupported.
   * ISO-2022-CN is not fully supported by java.nio, decoding is supported
   * but encoding is not.
   * TODO(cmendis): Remove once java supports ISO-2022-CN encoding
   * <p/>
   *
   * @param charset the name of the charset to check
   * @return true if the charset is null or ISO-2022-CN else false
   */
  private static boolean isUnsupportedCharset(String charset) {
    if ("ISO-2022-CN".equalsIgnoreCase(charset)) {
      return true;
    }
    return false;
  }

  /**
   * Send the contents from a ReadableByteChannel to the HttpConnection without
   * blocking on writes. The caller must return after invoking this method and
   * can perform no more modifications to the HttpResponse.
   *
   * @param channel ReadableByteChannel from which to send data.
   * @param cb      the callback invoked when transfer completes or fails
   */
  public void sendStream(ReadableByteChannel channel,
      HttpNonblockingTransferTask.TransferCompleteCallback cb) {
    if (output_writer != null) {
      throw new IllegalStateException("getWriter() called previously");
    }
    if (output_stream != null) {
      throw new IllegalStateException("getOutputStream() called previously");
    }

    transfer_task_ = new HttpNonblockingTransferTask(this, conn, channel, cb);
    transfer_task_.startTransfer();
  }
}