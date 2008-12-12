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

import javax.servlet.ServletInputStream;
import java.util.*;
import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.io.BufferedReader;
/**
 * @author jennings
 *         Date: Jul 22, 2008
 */
final class HttpRequestImpl implements HttpRequest {
  private final ServletInputStreamImpl inputStream;
  private HttpRequestHeaders headers;

  HttpRequestImpl(ServletInputStreamImpl inputStream) throws IOException {
    this.inputStream = inputStream;
    parseHeaders();
  }

  private void parseHeaders() throws IOException {
    headers = new HttpRequestHeaders(inputStream.getReader());
  }

  public String getHeader(String name) {
    return headers.getHeader(name);
  }

  public Enumeration<String> getHeaders(String name) {
    return headers.getHeaders(name);
  }

  public Enumeration<String> getHeaderNames() {
    return headers.getHeaderNames();
  }

  public String getMethod() {
    return headers.getRequestType().getType();
  }

  public String getPathTranslated() {
    return null;
  }

  public String getQueryString() {
    return null;
  }

  public String getRequestURI() {
    return headers.getRequestType().getRequestData();
  }

  public StringBuffer getRequestURL() {
    return null;
  }

  public void setCharacterEncoding(String env)
      throws UnsupportedEncodingException {
  }

  public ServletInputStream getInputStream() throws IOException {
    return inputStream;
  }

  public Map<String, String[]> getParameterMap() {
    return new HashMap<String, String[]>();
  }

  public Locale getLocale() {
    return null;
  }

  public Enumeration<Locale> getLocales() {
    return null;
  }

  public String getProtocol() {
    return null;
  }

  public String getScheme() {
    return null;
  }

  public BufferedReader getReader() throws IOException {
    return null;
  }

  public ConnectionInformation getConnectionInformation() {
    return null;
  }
}
