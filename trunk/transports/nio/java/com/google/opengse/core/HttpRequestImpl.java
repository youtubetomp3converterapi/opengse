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

import com.google.opengse.ConnectionInformation;
import com.google.opengse.GSEConstants;
import com.google.opengse.HttpRequest;
import com.google.opengse.RequestUtils;
import com.google.opengse.ServletEngineConfiguration;
import com.google.opengse.httputil.AcceptHeader;
import com.google.opengse.httputil.ChunkHeader;
import com.google.opengse.httputil.ContentType;
import com.google.opengse.httputil.FormUrlDecoder;
import com.google.opengse.httputil.Locales;
import com.google.opengse.httputil.ParamMap;
import com.google.opengse.httputil.Range;
import com.google.opengse.iobuffer.IOBuffer;
import com.google.opengse.iobuffer.IOBufferInputStream;
import com.google.opengse.iobuffer.IOBufferReader;
import com.google.opengse.util.IteratorEnumeration;
import com.google.opengse.util.string.StringUtil;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;

/**
 * Implements the <code>HttpServletRequestSubset</code> interface.
 *
 * @author Peter Mattis
 * @author Spencer Kimball
 */
final class HttpRequestImpl implements HttpRequest, ConnectionInformation {

  private static final Logger LOGGER
      = Logger.getLogger(HttpRequestImpl.class.getName());

  private static final int READLINE_LIMIT = 8192;

  private static final Locale DEFAULT_LOCALE = Locale.getDefault();

  // lazily initialized set of GET parameters
  private ParameterSet queryStringParams = null;

  // container for http method and version
  private RequestContext requestContext = new RequestContext();

  // Tuning parameters for secure session ids.
  // @TODO(pkr, 1/2007) consider adding methods to make these settable
  // from application level.

  // A simple regex that validates host name is in roughly canonical format.
  private static final Pattern CANONICAL_HOST_PATTERN = Pattern.compile(
      "^([a-zA-Z0-9][-_a-zA-Z0-9]*)(\\.[a-zA-Z0-9][-_a-zA-Z0-9]*)*\\.?$");

  /**
   * The connection that is servicing this request
   */
  protected final HttpConnection conn_;
  protected final HttpServer server_;


  /**
   * The content type structure. Used in HttpMultipartRequest subclass to get
   * the content boundary parameter
   */
  protected ContentType ctype_ = null;

  /**
   * Private variables
   */
  private ParamMap parsed_params_ = null;
  private String char_encoding_ = null;
  private String content_type_ = null;
  private ArrayList<Cookie> cookies_ = null;
  private List<Locale> locales_ = null;
  private List<AcceptHeader> codings_ = null;
  private List<AcceptHeader> charsets_ = null;
  private ChunkHeader chunk_header_ = null;
  private IOBuffer post_body_ = null;
  private IOBufferInputStream input_stream_ = null;
  private BufferedReader input_reader_ = null;
  private final ServletEngineConfiguration config;

  /**
   * Constructor for normal usage.
   *
   * NOTE: this constructor is no longer public. If your code has "new
   * HttpRequest(null)" please change it to use
   * "new com.google.opengse.test.TestHttpRequest()" instead.
   */
  protected HttpRequestImpl(HttpConnection conn) {
    this.conn_ = conn;
    this.server_ = conn.server_;
    config = conn.getConfiguration();
  }


  /**
   * Parse an HTTP request.
   */
  boolean parse(final ByteArrayOutputStream baos, final IOBuffer buf)
      throws IOException {
    return RequestContextFactory.parse(requestContext, baos, buf);
  }

  void setURI(String uri) {
    requestContext.setURI(uri);
    this.parsed_params_ = null;  // Query string may have changed.
  }

  URI getURI() {
    return requestContext.getURI();
  }

  int _getMajorVersion() {
    return requestContext.getMajorVersion();
  }

  int _getMinorVersion() {
    return requestContext.getMinorVersion();
  }

  /**
   * Returns the parsed "Range" header as a
   * {@link com.google.opengse.httputil.Range} object, or <code>null</code> if
   * no "Range" header exists. This method is not part of the
   * HttpServletRequest interface. Servlets that intend to use
   * the Range header directly must cast their request object first.
   *
   * @return the parsed range specifcation if a "Range" header was supplied with
   *         the request; else <code>null</code>.
   */
  Range _getRange() {
    final String range = getHeader("Range");
    if (range == null) {
      return null;
    } else {
      return Range.parse(range);
    }
  }

  /**
   * Returns the host header value if it exists. If not, the default server name
   * that the server was configured with is used. The format of this value is
   * host:port, though the :port suffix is only reported for non-standard
   * ports.<p>
   *
   * This method reports the host that the client browser is accessing, which is
   * not always the actual DNS name of the GSE server. In production, this is
   * rarely true. The client helpfully reports the server it is accessing via
   * the HTTP/1.1 Host header. For HTTP/1.0 clients, the default for the server
   * is used as the fallback.
   *
   * @return the host:port pair (the :port is optional)
   */
  private String getHost() {
    String host = getHeader("Host");
    if (host == null) {
      // use the configured server name if host is not present
      host = getNameFromServer();
    }
    return host;
  }

  protected String getNameFromServer() {
    return (server_ == null) ? null : server_.getName();
  }


  /**
   * Can we perform keep-alive on this request?
   */
  boolean _canKeepAlive() {
    // We only perform keep-alive on HTTP/1.0 and HTTP/1.1 requests
    if (requestContext.getMajorVersion() != 1) {
      return false;
    }

    if (requestContext.getMethod() == GSEConstants.POST ||
        requestContext.getMethod() == GSEConstants.PUT) {
      // We need a valid content length for POST & PUT keep-alives
      if (getHeader("Content-Length") == null) {
        return false;
      }
    } else if ((requestContext.getMethod() != GSEConstants.OPTIONS)
        && (requestContext.getMethod() != GSEConstants.GET)
        && (requestContext.getMethod() != GSEConstants.HEAD)
        && (requestContext.getMethod() != GSEConstants.DELETE)
        && (requestContext.getMethod() != GSEConstants.TRACE)
        && (requestContext.getMethod() != GSEConstants.CONNECT)) {
      // Why the redundant test above?
      return false;
    }

    if (requestContext.getMinorVersion() == 0) {
      // HTTP/1.0 defaults to closing connections
      return requestContext.getHeaders().headerHasValue("Connection",
          "keep-alive");
    } else if (requestContext.getMinorVersion() == 1) {
      // HTTP/1.1 defaults to keeping connections alive
      return !requestContext.getHeaders().headerHasValue("Connection", "close");
    }
    return false;
  }

  void print(PrintWriter writer) {
    writer.print(requestContext.getMethod() + " ");
    if (requestContext.getURI() != null) {
      writer.print(requestContext.getURI().toASCIIString());
    } else {
      writer.print("<null>");
    }
    if (requestContext.getMajorVersion() >= 1) {
      writer.print(" HTTP/" + requestContext.getMajorVersion() + "."
          + requestContext.getMinorVersion() + "\r\n");
      requestContext.getHeaders().print(writer);
    } else {
      writer.print("\r\n\r\n");
    }
    if (!config.dontPrintPostBody()) {
      writer.print(getPostBody());
    }
  }

  void writeIOBuffer(IOBuffer buf) throws IOException {
    // write the method and headers
    buf.writeBytes((requestContext.getMethod() + " ").getBytes());
    if (requestContext.getURI() != null) {
      buf.writeBytes(requestContext.getURI().toASCIIString().getBytes());
    } else {
      buf.writeBytes("<null>".getBytes());
    }
    if (requestContext.getMajorVersion() >= 1) {
      buf.writeBytes((" HTTP/" + requestContext.getMajorVersion() + "."
          + requestContext.getMinorVersion() + "\r\n").getBytes());
      requestContext.getHeaders().writeIOBuffer(buf);     // write headers
    } else {
      buf.writeBytes("\r\n\r\n".getBytes());
    }

    // write the contents of the post body (if applicable) without
    // consuming the post body
    if (post_body_ != null) {
      post_body_.flush();
      buf.append(post_body_);
    }
  }

  private String getCharacterEncoding() {
    // WARNING: Do NOT call this function if the return value is used
    //          to set the encoding scheme of an i/o buffer or query string
    //          from the incoming request for decoding purpose.
    //          Use getInternalCharacterEncoding() instead.
    getContentType();
    return char_encoding_;
  }

  private String getContentType() {
    String hdr = getHeader("Content-Type");
    if (ctype_ == null) {
      ctype_ = ContentType.parse(hdr);
      content_type_ = ctype_.getType(null);
      // use the encoding specified in the content type only if not already set
      if (char_encoding_ == null) {
        char_encoding_ = ctype_.getParameter(
            "charset", null);
      }
    }
    return content_type_;
  }

  /**
   * Returns a copy of the i/o buffer containing the post body.
   */
  private IOBuffer getPostBody() {
    if (post_body_ != null) {
      return new IOBuffer(post_body_);
    } else {
      return new IOBuffer();
    }
  }

  /**
   * Sets the post body i/o buffer to the provided string
   */
  void setPostBody(final String body)
      throws CharacterCodingException, IOException {
    _setPostBody(new IOBuffer(body, "US-ASCII"), body.length());
  }

  /**
   * Sets the post body i/o buffer to a copy of the provided i/o buffer. Any
   * parameters in the given post body will be parsed the next time parameters
   * are requested from this object.
   */
  void _setPostBody(final IOBuffer buf, final int length) throws IOException {
    post_body_ = new IOBuffer();
    buf.flush();
    post_body_.transfer(buf, length);
    post_body_.flush();
    parsed_params_ = null;  // Reparse the parameters when needed.
  }

  /**
   * Parses (or continues parsing) a chunked transfer-encoded request body
   * according to RFC 2616, section 3.6.1. This method should be called
   * repeatedly, as new data is available in buf, until it returns
   * <code>true</code>.
   *
   * @param line_buf a buffer to hold the contents of a chunk header between
   *                 calls to this method, for cases where the available bytes
   *                 in buf are not sufficient to hold the entire line.
   * @param buf      iobuffer containing bytes to parse.
   * @return <code>true</code> if the parsing is complete.
   * @throws java.io.IOException in case of badly formed input.
   */
  boolean _parseChunkedBody(
      final ByteArrayOutputStream line_buf, final IOBuffer buf)
      throws IOException {

    while (true) {
      // If a chunk header has been parsed, attempt to read chunk data.
      if (chunk_header_ != null) {
        final int chunkSize = chunk_header_.getChunkSize();
        // We check that we have sufficient bytes in the input buffer to
        // satisfy the size reported with the chunk header plus the trailing
        // CRLF.
        if (buf.availableBytes() >= chunkSize + 2) {
          // As long as this isn't the last chunk (0 length), transfer bytes.
          if (chunkSize > 0) {
            if (post_body_ == null) {
              post_body_ = new IOBuffer();
            }
            post_body_.transfer(buf, chunkSize);
          }
          // Pull off trailing CRLF.
          if (!buf.readLine(line_buf, READLINE_LIMIT)) {
            throw new IOException("chunk-data not succeeded by CRLF");
          } else if (line_buf.size() > 2) {
            throw new IOException("extra characters found in data chunk: " +
                line_buf.toString());
          }
          chunk_header_ = null;
          // We're done if this was the last chunk (chunk_size == 0).
          if (chunkSize == 0) {
            // Set the content length header so request forwarding will work.
            requestContext.getHeaders().removeHeader("Transfer-Encoding");
            requestContext.getHeaders().setHeader("Content-Length",
                    Integer.toString(post_body_.availableBytes()));

            post_body_.flush();
            return true;
          }
        } else {
          return false;
        }
      } else if (buf.readLine(line_buf, READLINE_LIMIT)) {
        // We've managed to read the next chunk header; parse it.
        chunk_header_ =
            ChunkHeader.parse(line_buf.toString("ISO-8859-1").trim());
        line_buf.reset();
      } else {
        return false;
      }
    }
  }

  /**
   * Test whether or not the body of this request is currently gzipped. If so,
   * then the request may be unzipped by calling
   * {@link #_decodeGzippedRequest()}, after which this method will return
   * false.
   */
  boolean _isGzippedRequest() {
    return GSEConstants.CONTENT_ENCODING_GZIP.equals(
        this.getHeader("Content-Encoding"));
  }

  /**
   * When content-encoding indicates the request is gzipped, then decode the
   * post-body.  The post_body_ IOBuffer is modified in place.
   *
   * We also strip the Content-Encoding:gzip header from the request and adjust
   * the size of the content, to prevent confusion in the servlet and make it
   * possible to forward this connection without recompression.
   */
  void _decodeGzippedRequest() throws IOException {
    final String contentEncoding = getHeader("Content-Encoding");
    if (!GSEConstants.CONTENT_ENCODING_GZIP.equals(contentEncoding)) {
      LOGGER.log(Level.WARNING, "cannot unzip body when content-encoding " +
          "is not 'gzip'; Content-Encoding: " +
          (contentEncoding == null ? "null" : contentEncoding));
      return;
    }

    // wrap post body in an inflator stream
    post_body_.flush();
    InputStream inflator_input_stream =
        new GZIPInputStream(new IOBufferInputStream(post_body_), 1024);

    // decompress by writing to a new IO buffer, update body
    IOBuffer inflatedBodyBuffer = new IOBuffer();
    final byte[] byteBuffer = new byte[1024];
    int numBytes;
    while (-1 != (numBytes = inflator_input_stream.read(byteBuffer))) {
      inflatedBodyBuffer.writeBytes(byteBuffer, 0, numBytes);
    }
    inflatedBodyBuffer.flush();

    int inflated_body_length = inflatedBodyBuffer.totalBytes();
    _setPostBody(inflatedBodyBuffer, inflated_body_length);

    // Remove content encoding, set new length, so request forwarding will work
    requestContext.getHeaders().removeHeader("Content-Encoding");
    requestContext.getHeaders().setIntHeader("Content-Length",
        inflated_body_length);
  }

  /**
   * Gives raw byte access to post body. Unlike getReader(), the byte stream is
   * not decoded into characters.
   *
   * @return input stream of the post body in the request.
   * @throws IllegalStateException if {@link #getReader()} has been previously
   *                               called.
   */
  public ServletInputStream getInputStream() {
    if (input_reader_ != null) {
      throw new IllegalStateException("getReader() called previously");
    }
    if (input_stream_ == null) {
      input_stream_ = new IOBufferInputStream(getPostBody());
    }
    return input_stream_;
  }

  /**
   * Called after getInputStream(), to allow body of request to be re-read by
   * throwing away the existing input_stream_. One use case is to modify the
   * bytes in post body using a new InputStream from getInputStream(). Then
   * replace post body with modified body using setPostBody(). But in order to
   * re-parse the new post body, we must call resetInputStream().
   */
  void resetInputStream() {
    input_stream_ = null;
  }

  public Locale getLocale() {
    Enumeration<Locale> e = getLocales();
    if (e.hasMoreElements()) {
      return e.nextElement();
    }
    return DEFAULT_LOCALE;
  }

  public Enumeration<Locale> getLocales() {
    if (locales_ == null) {
      locales_ = Locales.parse(getHeaders("Accept-Language"));
      if (locales_.isEmpty()) {
        locales_.add(DEFAULT_LOCALE);
      }
    }
    // utilize the server's locales hook if available
    return new IteratorEnumeration<Locale>(locales_.iterator());
  }

  /**
   * Return a sorted list of {@link com.google.opengse.httputil.AcceptHeader}
   * values from the "Accept-Encoding" HTTP request header. If no such header is
   * present, 'identity;q=1' is assumed.
   *
   * @return a list of codings, as specified by the AcceptHeader HTTP header
   */
  List<AcceptHeader> getCodings() {
    if (codings_ == null) {
      codings_ = AcceptHeader.parse(getHeaders("Accept-Encoding"));
      if (codings_.isEmpty()) {
        // create a new list, as the list returned via AcceptHeader is
        // fixed size and cannot be added to.
        codings_ = new ArrayList<AcceptHeader>(1);
        codings_.add(new AcceptHeader("identity", 1.0));
      }
    }
    return codings_;
  }

  /**
   * Return a sorted list of {@link com.google.opengse.httputil.AcceptHeader}
   * values from the "Accept-Charset" HTTP request header. If no such header is
   * present, "*;q=1" * is assumed.
   *
   * @return a list of charsets, as specified by the AcceptCharset HTTP header.
   */
  List<AcceptHeader> _getCharsets() {
    if (charsets_ == null) {
      charsets_ = AcceptHeader.parse(getHeaders("Accept-Charset"));
      if (charsets_.isEmpty()) {
        // create a new list, as the list returned via AcceptHeader is
        // fixed size and cannot be added to.
        charsets_ = new ArrayList<AcceptHeader>(1);
        charsets_.add(new AcceptHeader("*", 1.0));
      }
    }
    return charsets_;
  }

  /**
   * Returns the named {@link com.google.opengse.httputil.AcceptHeader} value.
   * If no Accept-Encoding header was present in the request, only
   * coding=="identity" will be available.
   *
   * @param coding the coding to return
   * @return the coding or <code>null</code> if not specified
   */
  AcceptHeader _getAcceptEncoding(String coding) {
    List<AcceptHeader> codings = getCodings();
    for (AcceptHeader ae : codings) {
      if (ae.getType().equals(coding)) {
        return ae;
      }
    }
    return null;
  }

  /**
   * Determines whether the specified charset is acceptable.<p>
   * http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.2
   *
   * @param charset the charset to test
   * @return {@code true} if the client will accept the specified charset;
   *         {@code false} otherwise.
   */
  boolean _acceptsCharset(String charset) {
    return acceptsType(charset, _getCharsets());
  }

  /**
   * Determines whether the specified coding is acceptable.<p>
   * http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.3
   *
   * @param coding the coding to test
   * @return <code>true</code> if the client will accept the specified coding;
   *         <code>false</code> otherwise.
   */
  boolean _acceptsEncoding(String coding) {
    return acceptsType(coding, getCodings());
  }

  private static boolean acceptsType(String type,
      List<AcceptHeader> acceptHeaders) {
    double star_q = 0.0;

    for (AcceptHeader ae : acceptHeaders) {
      if (ae.getType().equalsIgnoreCase(type)) {
        if (ae.getQuality() > 0.0) {
          return true;
        } else {
          return false;
        }
      } else if (ae.getType().equals("*")) {
        star_q = ae.getQuality();
      }
    }

    return star_q > 0.0;
  }

  // Changes to the returned map won't affect the underlying parameters
  // for this request.
  public Map<String, String[]> getParameterMap() {
    initParsedParameters();
    Map<String, String[]> copy = parsed_params_.toMap();
    return copy;
  }

  private void initParsedParameters() {
    if (parsed_params_ == null) {
      parsed_params_ = parseParameters(getInternalCharacterEncoding());
    }
  }

  /**
   * Parses the parameters associated with the request using the given {@code
   * encoding}.
   */
  protected ParamMap parseParameters(String encoding) {
    ParamMap map = new ParamMap();
    FormUrlDecoder.parse(getQueryString(), map, encoding);

    if (post_body_ != null) {
      IOBuffer params = getPostBody();
      if ("application/x-www-form-urlencoded".equals(getContentType())) {
        // the character encoding on the post body is guaranteed to be US-ASCII
        params.setCharacterEncoding("US-ASCII");

        StringBuilder sb = new StringBuilder();
        char[] char_buf = new char[1024];
        int res;

        while ((res = params.read(char_buf)) > 0) {
          sb.append(char_buf, 0, res);
        }

        FormUrlDecoder.parse(sb.toString(), map, encoding);
      }
    }
    return map;
  }

  public String getProtocol() {
    return "HTTP/" + requestContext.getMajorVersion() + "."
        + requestContext.getMinorVersion();
  }

  public BufferedReader getReader() {
    if (input_stream_ != null) {
      throw new IllegalStateException("getInputStream() called previously");
    }
    if (input_reader_ == null) {
      IOBuffer body = getPostBody();
      body.setCharacterEncoding(getInternalCharacterEncoding());
      input_reader_ = new BufferedReader(new IOBufferReader(body));
    }
    return input_reader_;
  }

  public ConnectionInformation getConnectionInformation() {
    return this;
  }

  public int getLocalPort() {
    Socket socket = conn_.getSocket();
    // can socket ever be null?
    return socket.getLocalPort();
  }

  public String getLocalAddr() {
    Socket socket = conn_.getSocket();
    // can socket ever be null?
    InetAddress inet = socket.getLocalAddress();
    return inet.getHostAddress();
  }

  public String getLocalName() {
    Socket socket = conn_.getSocket();
    // can socket ever be null?
    InetAddress inet = socket.getLocalAddress();
    return inet.getHostName();
  }

  public int getRemotePort() {
    Socket socket = conn_.getSocket();
    // can socket ever be null?
    return socket.getPort();
  }

  protected String getUserIPHeader() {
    return (server_ == null) ? null : server_.getUserIPHeader();
  }

  /**
   * Returns a string representation of the remote host address as a
   * dot-separated IP address. If GSE was instructed to use a request header to
   * divine the user's IP address (useful when behind a proxy), this method will
   * inspect the appropriate header(s) and report the supplied value. This
   * feature must be specifically enabled, as it can be a big security problem
   * if the proxy does not correctly set the header (because the remote client
   * can set it arbitrarily).
   *
   * @return the string representation of the remote IP address or null if it
   *         isn't available
   */
  public String getRemoteAddr() {
    // use an optional header to derive user IP. This is necessary when behind
    // a proxy. In these cases, the getHostAddress() method will incorrectly
    // return the proxy's IP instead of the user's.
    if (getUserIPHeader() != null) {
      Enumeration<String> userIpHeaders = getHeaders(getUserIPHeader());
      String lastUserIp = null;
      String firstDebugIp = null;
      // search through the available user ip headers and report the last
      // header value which IS NOT a debug IP, or the first header value
      // which IS a debug IP.
      while (userIpHeaders != null && userIpHeaders.hasMoreElements()) {
        String ip = userIpHeaders.nextElement();
        if (!StringUtil.isEmpty(ip)) {
          lastUserIp = ip;
        } else {
          LOGGER.warning("encountered empty user IP header");
        }
      }
      if (lastUserIp != null) {
        return lastUserIp;
      } else if (firstDebugIp != null) {
        return firstDebugIp;
      }
      // otherwise, fall through and report connected IP
    }
    return getSocketRemoteAddr();
  }

  /**
   * Returns thre remote address the socket is connected to.
   *
   * @return the string representation of the remote IP address the socket is
   *         connected to or null if socket is not connected or available.
   */
  String getSocketRemoteAddr() {
    Socket socket = conn_.getSocket();
    if (socket != null && socket.isConnected()) {
      InetAddress remoteInetAddr = socket.getInetAddress();
      if (remoteInetAddr != null) {
        return remoteInetAddr.getHostAddress();
      }
    }
    return null;
  }

  public String getRemoteHost() {
    String ip = getRemoteAddr();
    if (ip == null) {
      return null;
    } else {
      try {
        return InetAddress.getByName(ip).getHostName();
      } catch (UnknownHostException e) {
        LOGGER.info("hostname lookup failed for: " + ip);
        return null;
      }
    }
  }

  public String getScheme() {
    String scheme = requestContext.getURI().getScheme();
    if (scheme == null) {
      if (isSecure()) {
        scheme = "https";
      } else {
        scheme = "http";
      }
    }
    return scheme;
  }

  public String getServerName() {
    String host = getHost();
    int index = host.indexOf(':');
    if (index != -1) {
      host = host.substring(0, index);
    }
    Matcher m = CANONICAL_HOST_PATTERN.matcher(host);
    if (!m.find()) {
      LOGGER.warning("bad host header format: " + host);
      return null;
    }
    return host;
  }

  public int getServerPort() {
    String host = getHost();
    int index = host.indexOf(':');
    if (index != -1) {
      try {
        return Integer.parseInt(host.substring(index + 1));
      } catch (NumberFormatException ignored) {
        LOGGER.warning("bad host header format: " + host);
      }
    }
    // no port specified in the host header or through the default
    // host set in the HttpServer. In this case, we assume the client
    // browser is connecting to the default ports (80 for http:// and
    // 443 for https://). This assumption will be true if there is
    // a host header without a :port suffix. It's a just a 'good'
    // guess if there is no host header and we're relying on the default
    // HttpServer host (server_.getname()).
    return isSecure() ? 443 : 80;
  }

  /**
   * Overrides the name of the character encoding used in the body of this
   * request. If parameters have already been parsed, they will be reparsed with
   * the new encoding when needed.
   */
  public void setCharacterEncoding(String env)
      throws UnsupportedEncodingException {
    try {
      Charset.forName(env);
    } catch (IllegalArgumentException unsupported) {
      throw new UnsupportedEncodingException(env);
    }

    if (!env.equalsIgnoreCase(char_encoding_)) {
      if (input_reader_ != null) {
        LOGGER.warning("Resetting character encoding from " + char_encoding_ +
            " to " + env + "will not affect the contents of the " +
            "post body reader already fetched via getReader(), " +
            "which has been initialized using " + char_encoding_);
      }
      if (parsed_params_ != null) {
        LOGGER.info("Parameters (originally encoded with " +
            char_encoding_ + ") will be reparsed with " + env);
        parsed_params_ = null;
      }
    }
    char_encoding_ = env;
  }

  public String getMethod() {
    return requestContext.getMethod();
  }

  public String getPathTranslated() {
    return null;
  }

  public String getQueryString() {
    return requestContext.getURI().getRawQuery();
  }

  public String getRequestURI() {
    // According to the javadoc 1.4 api, the web container does not decode
    // this string, so we return the raw path.
    final URI uri = requestContext.getURI();
    return (uri == null) ? null : uri.getRawPath();
  }

  public StringBuffer getRequestURL() {
    final StringBuffer buf = getRequestURLPrefix();

    // append the raw path
    buf.append(requestContext.getURI().getRawPath());
    return buf;
  }

  StringBuffer getRequestURLPrefix() {
    StringBuffer buf = new StringBuffer();

    // add in the scheme
    String scheme = getScheme();
    buf.append(scheme);
    buf.append("://");

    // add in the authority (username, password, host, port)
    URI requestUri = RequestUtils.getURI(this);
    String authority = requestUri.getRawAuthority();
    if (authority != null) {
      buf.append(authority);
    } else {
      // if the URI has no authority info, construct from the host header
      buf.append(getHost());
    }

    return buf;
  }


  /**
   * Returns whether or not the request was received via a secure channel. This
   * value is determined by the incoming port or through an optional header, if
   * present.
   */
  public boolean isSecure() {
    if (server_.getSecureHeader() != null) {
      String secure = getHeader(server_.getSecureHeader());
      if (secure != null) {
        return ("true".equals(secure) || "yes".equals(secure));
      }
    }
    return conn_.getSecure();
  }

  /**
   * Returns this request's {@link HttpServer}. This is used by the {@link
   * HttpResponseImpl} class.
   */
  HttpServer getServer() {
    return server_;
  }


  /**
   * Get the actual character encoding that will be used to parse input. This
   * may differ from the encoding returned by {@link #getCharacterEncoding}
   *
   *
   * NOTE: Use this instead of <code>getCharacterEncoding</code> to set the
   * encoding scheme of an i/o buffer or query string from the incoming request,
   * for decoding.
   */
  private String getInternalCharacterEncoding() {
    String defaultEncoding = getCharacterEncoding();
    return defaultEncoding;
  }

  /**
   * ParameterSet of only the query string.
   */
  private class QueryStringParameterSet implements ParameterSet {

    private final ParamMap paramMap;

    private QueryStringParameterSet() {
      String queryString = getQueryString();
      paramMap = (queryString == null) ? new ParamMap() :
          FormUrlDecoder.parse(queryString, null,
              getInternalCharacterEncoding());
    }

    public Iterator<String> getParameterNames() {
      return paramMap.toMap().keySet().iterator();
    }

    public String getParameter(String name) {
      String[] vals = getParameterValues(name);
      if (vals == null) {
        return null;
      }
      return vals[0];
    }

    public String[] getParameterValues(String name) {
      return paramMap.get(name);
    }
  }

  /**
   * Returns a ParameterSet consisting of the parameters in the query string.
   */
  ParameterSet getQueryParameterSet() {
    if (queryStringParams == null) {
      queryStringParams = new QueryStringParameterSet();
    }
    return queryStringParams;
  }

  public String getHeader(String name) {
    return requestContext.getHeaders().getHeader(name);
  }

  public Enumeration<String> getHeaders(String name) {
    return requestContext.getHeaders().getHeaders(name);
  }

  public Enumeration<String> getHeaderNames() {
    return requestContext.getHeaders().getHeaderNames();
  }

  public int getIntHeader(String name) {
    return requestContext.getHeaders().getIntHeader(name);
  }
}
