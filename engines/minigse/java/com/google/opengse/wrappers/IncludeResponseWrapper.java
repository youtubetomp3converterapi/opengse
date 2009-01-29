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

package com.google.opengse.wrappers;

import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

/**
 * Wrapper implementation of {@link javax.servlet.http.HttpServletResponse}
 * used for includes. Any changes to the headers and status code are ignored.
 *
 * Note: this class is required to extend {@link
 * javax.servlet.http.HttpServletResponseWrapper} per the servlet spec.
 *
 * @author Mike Bostock
 * @author Mike Jennings
 */
public final class IncludeResponseWrapper
  extends HttpServletResponseWrapper {

  private IncludeResponseWrapper(final HttpServletResponse res) {
    super(res);
  }

  public static HttpServletResponse create(final HttpServletResponse response) {
    return new IncludeResponseWrapper(response);
  }

  @Override
  public void addCookie(final Cookie cookie) {
  }

  @Override
  public void addDateHeader(final String name, final long date) {
  }

  @Override
  public void addHeader(final String name, final String value) {
  }

  @Override
  public void addIntHeader(final String name, final int value) {
  }

  @Override
  public void sendError(final int sc) {
  }

  @Override
  public void sendError(final int sc, final String msg) {
  }

  @Override
  public void sendRedirect(final String location) {
  }

  @Override
  public void setDateHeader(final String name, final long date) {
  }

  @Override
  public void setHeader(final String name, final String value) {
  }

  @Override
  public void setIntHeader(final String name, final int value) {
  }

  @Override
  public void setStatus(final int sc) {
  }

  @Override
  public void setStatus(final int sc, final String sm) {
  }

  @Override
  public void setContentType(final String type) {
  }
}
