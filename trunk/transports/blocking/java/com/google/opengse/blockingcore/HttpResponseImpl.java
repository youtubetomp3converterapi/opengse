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

import com.google.opengse.HttpResponse;
import com.google.opengse.HeaderUtil;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.List;

/**
 * @author jennings
 *         Date: Jul 22, 2008
 */
final class HttpResponseImpl implements HttpResponse {
  private HttpRequestImpl request;
  private ServletOutputStreamImpl outputStream;
  private boolean outputStream_called;
  private PrintWriter writer;
  private HttpHeaders headers;
  private Locale locale;

  HttpResponseImpl(HttpRequestImpl request, ServletOutputStreamImpl outputStream) {
    this.request = request;
    this.outputStream = outputStream;
    headers = outputStream.getHeaders();
    outputStream_called = false;
    writer = null;
  }

  public boolean containsHeader(String name) {
    return headers.containsHeader(name);
  }

  public void sendError(int sc, String msg) throws IOException {
    // if the response is already committed, except
    if (isCommitted()) {
      throw new IllegalStateException("response committed; cannot send error");
    }
    outputStream.setStatus(sc);
    outputStream.resetBuffer();
    setContentType("text/html");
    outputStream.println(getErrorHtml(sc, msg));
    outputStream.commit();
  }

  private String getErrorHtml(int statusCode, String msg) {
    return "Error:" + msg;
  }

  public void sendError(int sc) throws IOException {
    sendError(sc, getErrorMessageForStatusCode(sc));
  }

  private String getErrorMessageForStatusCode(int sc) {
    return "error";
  }

  public void setDateHeader(String name, long value) {
    setHeader(name, HeaderUtil.toDateHeader(value));
  }

  public void addDateHeader(String name, long value) {
    addHeader(name, HeaderUtil.toDateHeader(value));
  }

  public void setHeader(String name, String value) {
    headers.setHeader(name, value);
  }

  public void addHeader(String name, String value) {
    headers.addHeader(name, value);
  }

  public void setIntHeader(String name, int value) {
    setHeader(name, Integer.toString(value));
  }

  public void addIntHeader(String name, int value) {
    addHeader(name, Integer.toString(value));
  }

  public void setStatus(int sc) {
    outputStream.setStatus(sc);
  }

  public void setStatus(int sc, String sm) {
    outputStream.setStatus(sc, sm);
  }

  public String getCharacterEncoding() {
    return "ISO-8859-1";
  }

  public String getContentType() {
    List<String> values = headers.getHeaderValues("Content-Type");
    return (values != null && !values.isEmpty()) ? values.iterator().next() : null;
  }

  public ServletOutputStream getOutputStream() throws IOException {
    if (writer != null) {
      throw new IllegalStateException("getWriter() already called");
    }
    outputStream_called = true;
    return outputStream;
  }

  public PrintWriter getWriter() throws IOException {
    if (outputStream_called) {
      throw new IllegalStateException("getOutputStream() already called");
    }
    writer = outputStream.getWriter();
    return writer;
  }

  public void setCharacterEncoding(String charset) {
    setHeader("Content-Type", charset);    
  }

  public void setContentLength(int len) {
    setIntHeader("Content-Length", len);    
  }

  public void setContentType(String type) {
    setHeader("Content-Type", type);
  }

  public void setBufferSize(int size) {
    outputStream.setBufferSize(size);
  }

  public int getBufferSize() {
    return outputStream.getBufferSize();
  }

  public void flushBuffer() throws IOException {
    outputStream.flushPrintWriterIfItWasCreated();
    outputStream.commit();
  }

  public void resetBuffer() {
    outputStream.resetBuffer();
  }

  public boolean isCommitted() {
    return outputStream.isCommitted();
  }

  public void reset() {
    outputStream.reset();
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

  public Locale getLocale() {
    return locale;
  }
}
