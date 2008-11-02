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

import com.google.opengse.httputil.CookieUtil;

import javax.servlet.http.Cookie;
import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * @author jennings
 *         Date: Jul 6, 2008
 */
public class HttpServletResponseAdapter
    extends HttpServletResponseUnsupported {

  private final HttpResponse delegate;
//  private static final AtomicBoolean HAVE_SHOWN_COOKIE_VERSION_WARNING
//      = new AtomicBoolean();

  public HttpServletResponseAdapter(HttpResponse delegate) {
    this.delegate = delegate;
  }

  @Override
  public void addCookie(Cookie cookie) {
    String cookieAsString = CookieUtil.toString(cookie);
    delegate.addHeader("Set-Cookie", cookieAsString);
  }

  @Override
  public boolean containsHeader(String name) {
    return delegate.containsHeader(name);
  }

  @Override
  public void sendError(int sc, String msg) throws IOException {
    delegate.sendError(sc, msg);
  }

  @Override
  public void sendError(int sc) throws IOException {
    delegate.sendError(sc);
  }

  @Override
  public void setDateHeader(String name, long date) {
    delegate.setDateHeader(name, date);
  }

  @Override
  public void addDateHeader(String name, long date) {
    delegate.addDateHeader(name, date);
  }

  @Override
  public void setHeader(String name, String value) {
    delegate.setHeader(name, value);
  }

  @Override
  public void addHeader(String name, String value) {
    delegate.addHeader(name, value);
  }

  @Override
  public void setIntHeader(String name, int value) {
    delegate.setIntHeader(name, value);
  }

  @Override
  public void addIntHeader(String name, int value) {
    delegate.addIntHeader(name, value);
  }

  @Override
  public void setStatus(int sc) {
    delegate.setStatus(sc);
  }

  @Override
  public void setStatus(int sc, String sm) {
    delegate.setStatus(sc, sm);
  }

  @Override
  public String getCharacterEncoding() {
    return delegate.getCharacterEncoding();
  }

  @Override
  public String getContentType() {
    return delegate.getContentType();
  }

  @Override
  public ServletOutputStream getOutputStream() throws IOException {
    return delegate.getOutputStream();
  }

  @Override
  public PrintWriter getWriter() throws IOException {
    return delegate.getWriter();
  }

  @Override
  public void setCharacterEncoding(String charset) {
    delegate.setCharacterEncoding(charset);
  }

  @Override
  public void setContentLength(int len) {
    delegate.setContentLength(len);
  }

  @Override
  public void setContentType(String type) {
    delegate.setContentType(type);
  }

  @Override
  public void setBufferSize(int size) {
    delegate.setBufferSize(size);
  }

  @Override
  public int getBufferSize() {
    return delegate.getBufferSize();
  }

  @Override
  public void flushBuffer() throws IOException {
    delegate.flushBuffer();
  }

  @Override
  public void resetBuffer() {
    delegate.resetBuffer();
  }

  @Override
  public boolean isCommitted() {
    return delegate.isCommitted();
  }

  @Override
  public void reset() {
    delegate.reset();
  }

  @Override
  public void setLocale(Locale loc) {
    delegate.setLocale(loc);
  }

  @Override
  public Locale getLocale() {
    return delegate.getLocale();
  }
}
