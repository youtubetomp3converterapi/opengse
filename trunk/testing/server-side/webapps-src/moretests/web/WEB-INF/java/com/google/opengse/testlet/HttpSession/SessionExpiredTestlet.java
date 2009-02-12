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

package com.google.opengse.testlet.HttpSession;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author jennings
 *         Date: Nov 2, 2008
 */
public class SessionExpiredTestlet extends HttpServlet {
  private static final int ONE_SECOND = 1;
  private static final long ONE_AND_A_HALF_SECONDS_OF_MILLIS = 1500;

  @Override
  protected void service(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/plain");
    PrintWriter out = response.getWriter();
    HttpSession session = request.getSession(true);
    session.setMaxInactiveInterval(ONE_SECOND);
    session.setAttribute("foo", "bar");
    try {
      Thread.sleep(ONE_AND_A_HALF_SECONDS_OF_MILLIS);
    } catch (InterruptedException e) {
      throw new ServletException(e);
    }
    try {
      session.getAttribute("foo");
      out.println("FAILED - session should be expired");
    } catch(IllegalStateException ise) {
      out.println("PASSED");
    }
  }

}