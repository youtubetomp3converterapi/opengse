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

package com.google.opengse.webapp;

import com.google.opengse.RequestUtils;

import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletOutputStream;

import java.io.IOException;
import java.net.URI;

/**
 * @author jennings
 *         Date: Jun 10, 2008
 */
public class WebAppResponseWrapper extends HttpServletResponseWrapper {
  private final WebApp webapp;
  private final HttpServletRequest request;
  private boolean errorSent;
  private boolean redirectSent;
  private WebAppServletOutputStreamWrapper outputStream;
  protected static final String SESSION_PARAM_NAME = "gsessionid";

  /**
   * Constructs a response adaptor wrapping the given response.
   *
   * @throws IllegalArgumentException if the response is null
   */
  public WebAppResponseWrapper(
      HttpServletResponse response, WebApp webapp, HttpServletRequest request) {
    super(response);
    this.webapp = webapp;
    this.request = request;
    errorSent = false;
    redirectSent = false;
    outputStream = null;
  }

  @Override
  public void sendError(int sc, String msg) throws IOException {
    errorSent = true;
    super.sendError(sc, msg);
  }


  @Override
  public void sendError(int sc) throws IOException {
    errorSent = true;
    super.sendError(sc);
  }

  private boolean canAddHeaders() {
    return (!errorSent && !redirectSent);
  }

  @Override
  public void addHeader(String name, String value) {
    if (canAddHeaders()) {
      super.addHeader(name, value);
    }
  }

  @Override
  public void addDateHeader(String name, long date) {
    if (canAddHeaders()) {
      super.addDateHeader(name, date);
    }
  }

  @Override
  public void addIntHeader(String name, int value) {
    if (canAddHeaders()) {
      super.addIntHeader(name, value);
    }
  }


  @Override
  public ServletOutputStream getOutputStream() throws IOException {
    if (outputStream == null) {
      outputStream = new WebAppServletOutputStreamWrapper(
          super.getOutputStream());
    }
    return outputStream;
  }

  @Override
  public void setBufferSize(int size) {
    if (isCommitted()) {
      throw new IllegalStateException(
          "response committed; cannot set buffer size");
    }
    if (outputStream != null && outputStream.isContentWritten()) {
      throw new IllegalStateException(
          "response written; cannot set buffer size");
    }
    super.setBufferSize(size);
  }

  @Override
  public String encodeRedirectUrl(String url) {
    return encodeRedirectURL(url);
  }

  @Override
  public String encodeRedirectURL(String url) {
    return encodeURL(url);
  }

  @Override
  public String encodeUrl(String url) {
    return encodeURL(url);
  }


  @Override
  public String encodeURL(String url) {
    // only encode url if a session is present &
    // requested session is not from a cookie
    HttpSession session = request.getSession(false);
    if (session != null && request.isRequestedSessionIdFromCookie() == false) {
      String paramName = SESSION_PARAM_NAME;
      // check if the session param name is already in url
      int index = url.indexOf(paramName + "=");
      if (index != -1) {
        // replace existing session id
        int lastIndex = url.indexOf("&", index);
        if (lastIndex == -1) {
          lastIndex = url.length();
        }
        url = url.substring(0, index) + paramName + "=" + session.getId()
            + url.substring(lastIndex, url.length());
      } else {
        // add session id
        url = url + (url.indexOf("?") == -1 ? "?" : "&") + paramName + "="
            + session.getId();
      }
    }

    return url;
  }


  @Override
  public void sendRedirect(String location) throws IOException {
    // if the response is already committed, except
    if (isCommitted()) {
      throw new IllegalStateException(
          "response committed; cannot send redirect");
    }

    URI newUri = RequestUtils.getURI(request).resolve(location);
    setStatus(SC_MOVED_TEMPORARILY);
    setCharacterEncoding(getCharacterEncoding());
    if (!newUri.isAbsolute()) {
      setHeader("Location", RequestUtils.getRequestURLPrefix(request)
          .append(newUri.toASCIIString()).toString());
    } else {
      setHeader("Location", newUri.toASCIIString());
    }
    redirectSent = true;
  }
}
