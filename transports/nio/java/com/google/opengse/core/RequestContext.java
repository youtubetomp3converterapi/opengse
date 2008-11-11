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

package com.google.opengse.core;

import com.google.opengse.io.LineReader;
import com.google.opengse.iobuffer.IOBuffer;
import com.google.opengse.iobuffer.IOBufferLineReader;
import com.google.opengse.parser.Parser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An object which represents the context in which an HttpRequest runs.
 *
 * @author Mike Jennings
 */
class RequestContext {
  private static final int DEFAULT_MAJOR_VERSION = 0;
  private static final int DEFAULT_MINOR_VERSION = 9;
  private static final int READLINE_LIMIT = 8192;

  private static final Logger LOGGER =
      Logger.getLogger(RequestContext.class.getName());

  private String requestMethod;
  private URI requestUri;
  private int httpMajorVersion;
  private int httpMinorVersion;
  private final MimeHeaders headers = new MimeHeaders();
  private boolean parsing_http_ = true;

  public RequestContext() {
    this(DEFAULT_MAJOR_VERSION, DEFAULT_MINOR_VERSION);
  }

  private RequestContext(int httpMajorVersion, int httpMinorVersion) {
    this.httpMajorVersion = httpMajorVersion;
    this.httpMinorVersion = httpMinorVersion;
  }

  void setMethod(String method) {
    if (method == null) {
      requestMethod = null;
      return;
    }
    if (ParserFactory.isStandardMethod(method)) {
      requestMethod = method.intern();
    } else {
      String upper = method.toUpperCase();
      if (!upper.equals(method)) {
        LOGGER.log(Level.WARNING,
            "Received incorrect-cased HTTP method " + method);
      }
      requestMethod = ParserFactory.isStandardMethod(upper)
          ? upper.intern() : method.intern();
    }
  }

  public String getMethod() {
    return requestMethod;
  }

  void setURI(String uri) {
    if (uri == null) {
      this.requestUri = null;
      return;
    }

    try {
      /* Hack: when Firefox extensions checks for update,
       * it sends an URL with unecnoded bracees '{' and '}', e.g.
       *
       * /firefox/extensions/safebrowsing/update?guid=safebrowsing@google.com&
       * version=1.0&application={ec8030f7-c20a-464f-9b0e-13a3a9e97384}&
       * appversion=1.5.0.1&dist=google
       *
       * This will cause URISyntaxException thrown and 400 Bad requset
       * returned. Then firefox extensions can never be updated anymore.
       *
       * To get around this, we patch up the URI by encoding the braces first.
       */
      String patchedUri = uri.replaceAll("\\{", "%7B").replaceAll("\\}", "%7D");
      this.requestUri = new URI(patchedUri);
    } catch (URISyntaxException e) {
      LOGGER.info(e.getMessage());
      this.requestUri = null;
    }
  }

  URI getURI() {
    return requestUri;
  }

  void setMajorVersion(int major) {
    this.httpMajorVersion = major;
  }

  int getMajorVersion() {
    return httpMajorVersion;
  }

  void setMinorVersion(int minor) {
    this.httpMinorVersion = minor;
  }

  int getMinorVersion() {
    return httpMinorVersion;
  }

  MimeHeaders getHeaders() {
    return headers;
  }

  /**
   * Parse an HTTP request.
   */
  /**
   * Parse an HTTP request.
   *
   * @param parser the thing that knows how to poke values into a
   * RequestContext
   * @param baos stupid, redundant, reused buffer
   * @param buf the source of the bytes
   * @return
   * @throws IOException
   */
  boolean parse(Parser<RequestContext> parser, ByteArrayOutputStream baos,
      IOBuffer buf)
      throws IOException {
    if (parsing_http_) {
      LineReader reader = new IOBufferLineReader(buf, baos, READLINE_LIMIT);
      String line;
      while ((line = reader.readLine()) != null) {
        parser.parse(line, this);
        if (requestMethod == null) {
          // if no method was specified on the request line, check to
          // see if the line is entirely whitespace, in which case we
          // just ignore it.
          for (int i = 0; i < line.length(); i++) {
            if (!Character.isWhitespace(line.charAt(i))) {
              throw new IOException("no method specified: " + line +
                  ", buf: " + buf);
            }
          }
          continue;
        }
        if (requestUri == null) {
          // this could be caused by some dangling header line(s);
          // instead of throwing IOException, we ignore the line and continue;
          LOGGER.warning("Unable to handle request line: " + line);
          requestMethod = null;
          continue;

          // TODO: we can't really convert any exceptions thrown from this class
          // to a valid "HttpConnection#badRequest", due to the "null" request
          // context.
          // Note: unless we are certain it's malformed http message, any
          // resulting "badRequest" will screw up the existing response.
        }
        parsing_http_ = false;
        break;
      }
      if (parsing_http_) {
        return false;
      }
    }
    return headers.parse(baos, buf);
  }


}
