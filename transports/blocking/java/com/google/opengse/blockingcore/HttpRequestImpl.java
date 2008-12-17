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

import com.google.opengse.HttpRequest;
import com.google.opengse.ConnectionInformation;
import com.google.opengse.RequestUtils;
import com.google.opengse.httputil.Locales;
import com.google.opengse.util.IteratorEnumeration;

import javax.servlet.ServletInputStream;
import java.util.*;
import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

/**
 * @author jennings
 *         Date: Jul 22, 2008
 */
final class HttpRequestImpl implements HttpRequest {
  private final ServletInputStreamImpl inputStream;
  private boolean getInputStream_called;
  private BufferedReader inputStreamReader;
  private HttpHeaders headers;
  private static final Locale DEFAULT_LOCALE = Locale.getDefault();
  private static final String ACCEPT_LANGUAGE_HEADER = "Accept-Language";
  private RequestMetaData requestMetaData;
  private RequestURI requestURI;
  private Charset characterEncoding;

  HttpRequestImpl(RequestMetaData requestMetaData, ServletInputStreamImpl inputStream) throws IOException {
    this.requestMetaData = requestMetaData;
    headers = requestMetaData.getHeaders();
    this.inputStream = inputStream;
    getInputStream_called = false;
    inputStreamReader = null;

    requestURI = new RequestURI(requestMetaData.getType().getRequestData());
  }

  public String getHeader(String name) {
    if (headers.containsHeader(name)) {
      List<String> values = headers.getHeaderValues(name);
      if (values.isEmpty()) {
        return null;
      }
      return values.iterator().next();
    }
    return null;
  }

  public Enumeration<String> getHeaders(String name) {
    return new IteratorEnumeration<String>(headers.getHeaderValues(name).iterator());
  }

  public Enumeration<String> getHeaderNames() {
    return new IteratorEnumeration<String>(headers.getHeaderNames().iterator());
  }

  public String getMethod() {
    return requestMetaData.getType().getType();
  }

  public String getPathTranslated() {
    return null;
  }

  public String getQueryString() {
    return requestURI.getRawQuery();
  }

  public String getRequestURI() {
    return requestURI.toString();
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
      ConnectionInformation conn = getConnectionInformation();
      // if the URI has no authority info, construct from the local address
      buf.append(conn.getLocalName());
      int port = conn.getLocalPort();
      if (port != 80) {
        buf.append(':').append(port);
      }
    }

    return buf;
  }
  
  public StringBuffer getRequestURL() {
    StringBuffer buf = getRequestURLPrefix();
    // append the raw path
    buf.append(requestURI.getRawPath());
    return buf;
  }

  public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
    try {
      characterEncoding = Charset.forName(env);
    } catch(UnsupportedCharsetException ucse) {
      throw new UnsupportedEncodingException(env);
    }
  }

  public ServletInputStream getInputStream() throws IOException {
    if (inputStreamReader != null) {
      throw new IllegalStateException("getReader() was already called");
    }
    getInputStream_called = true;
    return inputStream;
  }

  public BufferedReader getReader() throws IOException {
    if (getInputStream_called) {
      throw new IllegalStateException("getInputStream() was already called");
    }
    if (inputStreamReader == null) {
      inputStreamReader = new BufferedReader(new InputStreamReader(inputStream));
    }
    return inputStreamReader;
  }
  
  public Map<String, String[]> getParameterMap() {
    return requestURI.getParameterMap();
  }

  public Locale getLocale() {
    if (!headers.containsHeader(ACCEPT_LANGUAGE_HEADER)) {
      return DEFAULT_LOCALE;
    }
    return getLocales().nextElement();
  }

  public Enumeration<Locale> getLocales() {
    List<Locale> locales = getLocalesFromHeaders();
    return new IteratorEnumeration<Locale>(locales.iterator());
  }

  private List<Locale> getLocalesFromHeaders() {
    List<String> localeStrings = headers.getHeaderValues(ACCEPT_LANGUAGE_HEADER);
    List<Locale> locales = Locales.parse(localeStrings);
    if (locales.isEmpty()) {
      locales.add(DEFAULT_LOCALE);
    }
    return locales;    
  }

  public String getProtocol() {
//    return "HTTP/" + requestContext.getMajorVersion() + "."
//        + requestContext.getMinorVersion();
    return "HTTP/1.0";
  }

  public String getScheme() {
    return "http";
  }


  public ConnectionInformation getConnectionInformation() {
    return requestMetaData;
  }
}
