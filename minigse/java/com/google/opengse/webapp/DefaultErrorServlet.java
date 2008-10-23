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

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author jennings
 *         Date: Jun 12, 2008
 */
public class DefaultErrorServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private static final String EXCEPTION_ATTRIBUTE
      = "javax.servlet.error.exception";

  @Override
  protected void service(
      final HttpServletRequest req, final HttpServletResponse resp)
      throws ServletException, IOException {
    final Throwable t = (Throwable) req.getAttribute(EXCEPTION_ATTRIBUTE);
    if (t != null) {
      this.getServletContext().log("Got exception", t);
    }
    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
  }
}
