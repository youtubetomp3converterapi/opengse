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

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * @author jennings
 *         Date: Jul 22, 2008
 */
final class HttpResponseImpl implements HttpResponse {
  private HttpRequestImpl request;
  private ServletOutputStreamImpl outputStream;

  HttpResponseImpl(HttpRequestImpl request, ServletOutputStreamImpl outputStream) {
    this.request = request;
    this.outputStream = outputStream;
  }

  public boolean containsHeader(String name) {
    return outputStream.containsHeader(name);
  }

  public void sendError(int sc, String msg) throws IOException {
  }

  public void sendError(int sc) throws IOException {
  }

  public void setDateHeader(String name, long date) {
  }

  public void addDateHeader(String name, long date) {
  }

  public void setHeader(String name, String value) {
    outputStream.setHeader(name, value);
  }

  public void addHeader(String name, String value) {
    outputStream.addHeader(name, value);
  }

  public void setIntHeader(String name, int value) {
  }

  public void addIntHeader(String name, int value) {
  }

  public void setStatus(int sc) {
  }

  public void setStatus(int sc, String sm) {
  }

  public String getCharacterEncoding() {
    return null;
  }

  public String getContentType() {
    return null;
  }

  public ServletOutputStream getOutputStream() throws IOException {
    return outputStream;
  }

  public PrintWriter getWriter() throws IOException {
    return outputStream.getWriter();
  }

  public void setCharacterEncoding(String charset) {
  }

  public void setContentLength(int len) {
  }

  public void setContentType(String type) {
  }

  public void setBufferSize(int size) {
    outputStream.setBufferSize(size);
  }

  public int getBufferSize() {
    return outputStream.getBufferSize();
  }

  public void flushBuffer() throws IOException {
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

  public void setLocale(Locale loc) {
  }

  public Locale getLocale() {
    return null;
  }
}
