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

import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Author: Mike Jennings
 * Date: Feb 6, 2009
 * Time: 11:31:34 AM
 */
class ErrorCodeResponseWrapper extends HttpServletResponseWrapper {
  private ErrorPageManager errorHandler;
  private final HttpServletRequest request;
  private final String servletName;
  private boolean errorSent;

  ErrorCodeResponseWrapper(ErrorPageManager errorHandler
      ,HttpServletRequest request, HttpServletResponse response,
      String servletName) {
    super(response);
    this.errorHandler = errorHandler;
    this.request = request;
    this.servletName = servletName;
    errorSent = false;
  }


  @Override
  public void sendError(int sc) throws IOException {
    try {
      errorSent = true;
      errorHandler.handleError(sc, servletName, request, this);
    } catch (ServletException e) {
      throw new IOException("ServletException: " + e.getMessage());
    }
  }

  @Override
  public void sendError(int sc, String msg) throws IOException {
    try {
      errorSent = true;
      errorHandler.handleError(sc, msg, servletName, request, this);
    } catch (ServletException e) {
      throw new IOException("ServletException: " + e.getMessage());
    }
  }

  @Override
  public void addHeader(String name, String value) {
    if (!errorSent) {
      super.addHeader(name, value);
    }
  }

  @Override
  public void addDateHeader(String name, long date) {
    if (!errorSent) {
      super.addDateHeader(name, date);
    }
  }

  @Override
  public void addIntHeader(String name, int value) {
    if (!errorSent) {
      super.addIntHeader(name, value);
    }
  }
}

