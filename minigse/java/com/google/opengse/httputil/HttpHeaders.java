// Copyright 2007 Google Inc. All Rights Reserved.
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

package com.google.opengse.httputil;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Encapsulate the headers for an HTTP request or response. Use of the
 * <code>processRequest</code> or <code>processResponse</code> methods
 * will allow the caller to parse the first line and headers of a request or
 * response, respectively.
 *
 * @author Ed Karrels
 */
public class HttpHeaders {

  /**
   * Reset the settings for this object.
   */
  public void clear() {
    header_values_.clear();
    first_line_ = uri_ = null;
    version_ = null;
    protocol_ = null;
    response_code_ = null;
  }

  public boolean isEmpty() {
    return first_line_ == null && header_values_.isEmpty();
  }

  /**
   * Process input, one line at a time.
   *
   * @param line A line from a request or response.  May be the first line,
   *        or a header.
   * @param isRequest whether this is input from a request (as opposed to
   *        a response).
   * @return true when all headers have been read.
   */
  public boolean processLine(String line, boolean isRequest) {
    // this is the first line
    if (first_line_ == null) {
      // According to the RFCs, blank lines before the first line
      // should be accepted.
      if (line.length() == 0) {
        return false;
      }
      first_line_ = line;
      boolean success;
      if (isRequest) {
        success = processRequestFirstLine(line);
      } else {
        success = processResponseFirstLine(line);
      }
      if (!success) {
        return true;
      }
    } else if (line.length() == 0) {
      // blank line marks the end of the headers
      return true;
    } else {
      setHeaderFromLine(line, true);
    }
    return false;
  }

  /**
   * Process a request, one line at a time.
   *
   * @param line Request line to process.  May be first line or header line.
   * @return  true when all the headers have been read.
   */
  public boolean processRequestLine(String line) {
    return processLine(line, true);
  }

  /**
   * Process a response, one line at a time.
   *
   * @param line Response line to process.  May be first line or header line.
   * @return  true when all the headers have been read.
   */
  public boolean processResponseLine(String line) {
    return processLine(line, false);
  }

  /**
   * Process one line of input in the header, assuming input is
   * coming from a request.  Maintained for backwards compatibility.
   *
   * Use of this method directly is denigrated.  Please use
   * <code>processRequest</code> or <code>processResponse</code> accordingly.
   */
  public boolean processHeaderLine(String line) {
    return processRequestLine(line);
  }

  /**
   * Process the request header line.
   * Return true if it's parsed successfully.
   * It will look like "GET /foobar?hey HTTP/1.0"
   */
  private boolean processRequestFirstLine(String line) {

    // the protocol should be the first word on the line
    if (line.startsWith("GET ")) {
      setProtocol(Protocol.GET);
    } else if (line.startsWith("POST ")) {
      setProtocol(Protocol.POST);
    } else if (line.startsWith("HEAD ")) {
      setProtocol(Protocol.HEAD);
    } else {
      return false;
    }

    // get the URI, the second word on the line
    int len = line.length();
    int uriStart = line.indexOf(' ');
    if (uriStart == -1) {
      return false;
    }
    while (uriStart < len && line.charAt(uriStart) == ' ') {
      uriStart++;
    }
    if (uriStart == len) {
      return false;
    }
    int uriEnd = line.indexOf(' ', uriStart + 1);
    if (uriEnd == -1) {
      uriEnd = len;
    }
    uri_ = line.substring(uriStart, uriEnd);

    // get the version number
    int versionStart = uriEnd;
    while (versionStart < len && line.charAt(versionStart) == ' ')
      versionStart++;
    if (versionStart == len) {
      // no verion string, must be 0.9
      setHttpVersion(Version.HTTP_09);
    } else if (line.regionMatches(versionStart, "HTTP/1.0", 0, 8)) {
      setHttpVersion(Version.HTTP_10);
    } else if (line.regionMatches(versionStart, "HTTP/1.1", 0, 8)) {
      setHttpVersion(Version.HTTP_11);
    } else {
      // invalid version
      return false;
    }

    return true;
  }

  /**
   * Process the response header line.
   * Return true if it's parsed successfully.
   * It will look like "HTTP/1.1 200 OK"
   */
  private boolean processResponseFirstLine(String line) {
    if (line.startsWith("HTTP/1.0")) {
      setHttpVersion(Version.HTTP_10);
    } else if (line.startsWith("HTTP/1.1")) {
      setHttpVersion(Version.HTTP_11);
    } else {
      return false;
    }
    String statusString = line.substring(9, 12);
    int code;
    try {
      code = Integer.valueOf(statusString);
    } catch (NumberFormatException e) {
      return false;
    }
    ResponseCode c = getResponseCode(code);
    if (c == null) {
      return false;
    }
    setResponseCode(c);
    return true;
  }

  /**
   * Parse a header line with "key: value".
   */
  private void setHeaderFromLine(String line, boolean overwrite) {
    int keyEnd = line.indexOf(':');

    if (keyEnd == -1) {
      // invalid header line
      return;
    }

    int valueStart = keyEnd + 1;

    // skip spaces before the colon
    while (keyEnd > 0 &&
           Character.isWhitespace(line.charAt(keyEnd - 1)))
      keyEnd--;
    String key = line.substring(0, keyEnd);

    // skip spaces after the colon
    int len = line.length();
    while (valueStart < len &&
           Character.isWhitespace(line.charAt(valueStart)))
      valueStart++;
    String value = line.substring(valueStart);

    setHeader(key, value, overwrite);
  }


  public String getUri() {
    return uri_;
  }

  /**
   * Return null if this header hasn't been set.
   */
  public String getHeader(String key) {
    return header_values_.get(key);
  }

  /**
   * Set a header value.  Return the previous value.
   * If 'overwrite' is false, only set the value if it
   * hasn't been set already.
   */
  public String setHeader(String key, String value, boolean overwrite) {
    key = normalizeHeader(key);

    String oldValue = getHeader(key);
    if (!overwrite && header_values_.containsKey(key)) {
      return oldValue;
    }

    header_values_.put(key, value);
    return oldValue;
  }

  /**
   * Removes a header value. Returns true if it existed
   */
  public boolean removeHeader(String key) {
    if (!header_values_.containsKey(key)) {
      return false;
    }
    header_values_.remove(key);
    return true;
  }

  /**
   * Return a set of Map.Entry objects representing all
   * the header key-value pairs.
   */
  public Iterator<Map.Entry<String, String>> listHeaders() {
    return header_values_.entrySet().iterator();
  }

  Version version_;
  public Version getHttpVersion() {
    return version_;
  }
  public void setHttpVersion(Version version) {
    version_ = version;
  }

  Protocol protocol_;
  public Protocol getProtocol() {
    return protocol_;
  }
  public void setProtocol(Protocol protocol) {
    protocol_ = protocol;
  }

  ResponseCode response_code_;
  public ResponseCode getResponseCode() {
    return response_code_;
  }
  public void setResponseCode(ResponseCode rc) {
    response_code_ = rc;
  }

  /**
   * Return -1 if the content length is unset or invalid.
   */
  public int getContentLength() {
    String contentLength = getHeader(CONTENT_LENGTH);
    if (contentLength == null) {
      return -1;
    }
    try {
      int len = Integer.parseInt(contentLength);
      return len;
    } catch (NumberFormatException e) {
      return -1;
    }
  }

  /**
   * Return true if Keep-Alive was requested.
   */
  public boolean isKeepAlive() {
    String val = getHeader(CONNECTION);

    // just check if the first letter of the "Connection" header
    // is not 'k', as in 'Keep-Alive'.
    return (val != null
        && val.length() > 0
        && (val.charAt(0) == 'k' || val.charAt(0) == 'K'));
  }


  /**
   * Typesafe enum class
   */
  public static class Version {
    private final String version;

    private Version(String version) {
      this.version = version;
    }

    @Override
    public String toString() {
      return version;
    }

    public static final Version HTTP_ERROR = new Version("");
    public static final Version HTTP_OTHER = new Version("H");
    public static final Version HTTP_09 = new Version("");
    public static final Version HTTP_10 = new Version("HTTP/1.0");
    public static final Version HTTP_11 = new Version("HTTP/1.1");
  }


  /**
   * Typesafe enum class
   */
  public static class Protocol {

    private final String protocol;

    private Protocol(String protocol) {
      this.protocol = protocol;
    }

    @Override
    public String toString() {
      return protocol;
    }

    public static final Protocol GET = new Protocol("GET");
    public static final Protocol POST = new Protocol("POST");
    public static final Protocol HEAD = new Protocol("HEAD");
  }

  /**
   * Given a HTTP status code, return the corresponding ResponseCode object,
   * or null if none is found.
   */
  public static ResponseCode getResponseCode(int code) {
    return CODE_TO_RESPONSE_CODE_MAP.get(code);
  }

  /**
   * Typesafe enum class
   */
  public enum ResponseCode {
    UNDEFINED(0, ""),
    FIRST_CODE(100, ""),

    // Informational
    CONTINUE(100, "Continue"),
    SWITCHING(101, "Switching Protocols"),

    // Success
    REQUEST_OK(200, "OK"),
    CREATED(201, "Created"),
    ACCEPTED(202, "Accepted"),
    PROVISIONAL(203, "Non-Authoritative Information"),
    NO_CONTENT(204, "No Content"),
    RESET_CONTENT(205, "Reset Content"),
    PART_CONTENT(206, "Partial Content"),
    IN_PROGRESS(280, "In Progress"),

    // Redirect
    MULTIPLE(300, "Multiple Choices"),
    MOVED_PERM(301, "Moved Permanently"),
    MOVED_TEMP(302, "Found"),
    SEE_OTHER(303, "See Other"),
    NOT_MODIFIED(304, "Not Modified"),
    USE_PROXY(305, "Use Proxy"),
    TEMP_REDIRECT(307, "More or less like 302"),


    // Client Error
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    PAYMENT(402, "Payment Required"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NA(405, "Method Not Allowed"),
    NONE_ACC(406, "Not Acceptable"),
    PROXY(407, "Proxy Authentication Required"),
    REQUEST_TO(408, "Request Time-out"),
    CONFLICT(409, "Conflict"),
    GONE(410, "Gone"),
    LEN_REQUIRED(411, "Length Required"),
    PRECOND_FAILED(412, "Precondition Failed"),
    ENTITY_TOO_BIG(413, "Request Entity Too Large"),
    URI_TOO_BIG(414, "Request-URI Too Large"),
    UNKNOWN_MEDIA(415, "Unsupported Media Type"),
    BAD_RANGE(416, "Requested range not satisfiable"),
    BAD_EXPECTATION(417, "Expectation Failed"),
    BAD_ID(420, "Id Not Found"),

    // Server Error
    ERROR(500, "Internal Server Error"),
    NOT_IMP(501, "Not Implemented"),
    BAD_GATEWAY(502, "Bad Gateway"),
    SERVICE_UNAV(503, "Service Unavailable"),
    GATEWAY_TO(504, "Gateway Time-out"),
    BAD_VERSION(505, "HTTP Version not supported"),

    LAST_CODE(505, "");

    private int code;
    private String desc;

    private ResponseCode(int code, String desc) {
      this.code = code;
      this.desc = desc;
    }

    public int getCode() {
      return code;
    }

    @Override
    public String toString() {
      return desc;
    }
  }

  private static final Map<Integer, ResponseCode> CODE_TO_RESPONSE_CODE_MAP
      = new HashMap<Integer, ResponseCode>();

  static {
    for (ResponseCode responseCode : ResponseCode.values()) {
      CODE_TO_RESPONSE_CODE_MAP.put(responseCode.code, responseCode);
    }
  }

  private static final Map<String, String> LOWER_HEADER_MAP
      = new HashMap<String, String>();

  private static String addHeader(String name) {
    LOWER_HEADER_MAP.put(name.toLowerCase(), name);
    return name;
  }

  // HTTP Header Names
  public static final String ACCEPT = addHeader("Accept");
  public static final String ACCEPT_CHARSET = addHeader("Accept-Charset");
  public static final String ACCEPT_ENCODING = addHeader("Accept-Encoding");
  public static final String ACCEPT_LANGUAGE = addHeader("Accept-Language");
  public static final String ACCEPT_RANGES = addHeader("Accept-Ranges");
  public static final String AGE = addHeader("Age");
  public static final String ALLOW = addHeader("Allow");
  public static final String AUTHORIZATION = addHeader("Authorization");
  public static final String CACHE_CONTROL = addHeader("Cache-Control");
  public static final String CONNECTION = addHeader("Connection");
  public static final String CONTENT_DISPOSITION = addHeader("Content-Disposition");
  public static final String CONTENT_LANGUAGE = addHeader("Content-Language");
  public static final String CONTENT_LENGTH = addHeader("Content-Length");
  public static final String CONTENT_LOCATION = addHeader("Content-Location");
  public static final String CONTENT_TYPE = addHeader("Content-Type");
  public static final String CONTENT_ENCODING = addHeader("Content-Encoding");
  public static final String DATE = addHeader("Date");
  public static final String EXPIRES = addHeader("Expires");
  public static final String FROM = addHeader("From");
  public static final String HOST = addHeader("Host");
  public static final String IF_MODIFIED_SINCE = addHeader("If-Modified-Since");
  public static final String LAST_MODIFIED = addHeader("Last-Modified");
  public static final String LOCATION = addHeader("Location");
  public static final String REFERER = addHeader("Referer");
  public static final String SERVER = addHeader("Server");
  public static final String TRANSFER_ENCODING = addHeader("Transfer-Encoding");
  public static final String TRANSFER_ENCODING_ABBRV = addHeader("TE");
  public static final String USER_AGENT = addHeader("User-Agent");
  public static final String VIA = addHeader("Via");
  public static final String X_FORWARDED_FOR = addHeader("X-Forwarded-For");

  /**
   * Return a capitalization-normalized version of this header key.
   * If this is not a known header, return it unchanged.
   */
  public static String normalizeHeader(String header) {
    String norm = LOWER_HEADER_MAP.get(header.toLowerCase());
    if (norm == null) {
      return header;
    }
    return norm;
  }

  @Override
  public String toString() {
    StringBuffer buf = new StringBuffer();
    if (first_line_ != null) {
      buf.append(first_line_);
      buf.append('\n');
    }
    if (uri_ != null) {
      buf.append(uri_);
      buf.append('\n');
    }
    Iterator<Map.Entry<String, String>> it = listHeaders();
    while (it.hasNext()) {
      Map.Entry<String, String> entry = it.next();
      buf.append(entry.getKey());
      buf.append(": ");
      buf.append(entry.getValue());
      buf.append('\n');
    }
    return buf.toString();
  }

  /**
   * Outputs HTTP headers (including the first response line) to <tt>out</tt>
   * using the data contained in this instance. If any data for the first
   * response line is unavailable (HTTP version and response code), some
   * defaults will be used. An example output looks like this:
   * <pre>
   *   HTTP/1.1 200 OK
   *   Content-Length: 3580
   *   Content-Type: text/html; charset=ISO-8859-1
   * </pre>
   *
   * @param out where HTTP headers are dumped to
   */
  public void outputToWriter(Writer out) throws IOException {
    Version version = (version_ != null) ? version_ : Version.HTTP_10;
    ResponseCode responseCode =
        (response_code_ != null) ? response_code_ : ResponseCode.REQUEST_OK;

    // Write the first line
    out.write(version + " " + responseCode.getCode() + " " + responseCode +
        "\r\n");

    // Write all the other headers
    for (Iterator<Map.Entry<String, String>> i = listHeaders(); i.hasNext();) {
      Map.Entry<String, String> entry = i.next();
      out.write(entry.getKey() + ": " + entry.getValue() + "\r\n");
    }
  }

  /**
   * Outputs HTTP headers (including the first response line) to <tt>out</tt>
   * using the data contained in this instance and append a newline at the end.
   *
   * @param out where HTTP headers are dumped to
   * @see #outputToWriter(Writer)
   */
  public void outputToWriterWithNewLine(Writer out) throws IOException {
    outputToWriter(out);
    out.write("\r\n");
  }

  protected String first_line_;
  private final HashMap<String, String> header_values_ =
      new LinkedHashMap<String, String>();
  protected String uri_;
}

