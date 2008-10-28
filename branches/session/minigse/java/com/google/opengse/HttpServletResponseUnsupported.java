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

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * @author jennings
 *         Date: Jul 6, 2008
 */
public class HttpServletResponseUnsupported implements HttpServletResponse {

  public void addCookie(Cookie cookie) {
    throw new UnsupportedOperationException();
  }

  public boolean containsHeader(String name) {
    throw new UnsupportedOperationException();
  }

  public String encodeURL(String url) {
    throw new UnsupportedOperationException();
  }

  public String encodeRedirectURL(String url) {
    throw new UnsupportedOperationException();
  }

  public String encodeUrl(String url) {
    throw new UnsupportedOperationException();
  }

  public String encodeRedirectUrl(String url) {
    throw new UnsupportedOperationException();
  }

  public void sendError(int sc, String msg) throws IOException {
    throw new UnsupportedOperationException();
  }

  public void sendError(int sc) throws IOException {
    throw new UnsupportedOperationException();
  }

  public void sendRedirect(String location) throws IOException {
    throw new UnsupportedOperationException();
  }

  public void setDateHeader(String name, long date) {
    throw new UnsupportedOperationException();
  }

  public void addDateHeader(String name, long date) {
    throw new UnsupportedOperationException();
  }

  public void setHeader(String name, String value) {
    throw new UnsupportedOperationException();
  }

  public void addHeader(String name, String value) {
    throw new UnsupportedOperationException();
  }

  public void setIntHeader(String name, int value) {
    throw new UnsupportedOperationException();
  }

  public void addIntHeader(String name, int value) {
    throw new UnsupportedOperationException();
  }

  public void setStatus(int sc) {
    throw new UnsupportedOperationException();
  }

  public void setStatus(int sc, String sm) {
    throw new UnsupportedOperationException();
  }

  public String getCharacterEncoding() {
    throw new UnsupportedOperationException();
  }

  public String getContentType() {
    throw new UnsupportedOperationException();
  }

  public ServletOutputStream getOutputStream() throws IOException {
    throw new UnsupportedOperationException();
  }

  public PrintWriter getWriter() throws IOException {
    throw new UnsupportedOperationException();
  }

  public void setCharacterEncoding(String charset) {
    throw new UnsupportedOperationException();
  }

  public void setContentLength(int len) {
    throw new UnsupportedOperationException();
  }

  public void setContentType(String type) {
    throw new UnsupportedOperationException();
  }

  public void setBufferSize(int size) {
    throw new UnsupportedOperationException();
  }

  public int getBufferSize() {
    throw new UnsupportedOperationException();
  }

  public void flushBuffer() throws IOException {
    throw new UnsupportedOperationException();
  }

  public void resetBuffer() {
    throw new UnsupportedOperationException();
  }

  public boolean isCommitted() {
    throw new UnsupportedOperationException();
  }

  public void reset() {
    throw new UnsupportedOperationException();
  }

  public void setLocale(Locale loc) {
    throw new UnsupportedOperationException();
  }

  public Locale getLocale() {
    throw new UnsupportedOperationException();
  }
}
