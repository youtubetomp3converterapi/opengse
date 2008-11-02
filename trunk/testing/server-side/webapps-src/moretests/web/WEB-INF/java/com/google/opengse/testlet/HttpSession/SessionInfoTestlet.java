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
public class SessionInfoTestlet extends HttpServlet {
  @Override
  protected void service(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/plain");
    PrintWriter out = response.getWriter();
    String requestedId = request.getRequestedSessionId();
    boolean isRequestedIdFromCookie = request.isRequestedSessionIdFromCookie();
    boolean isRequestedIdFromUrl = request.isRequestedSessionIdFromURL();
    boolean isRequestedIdValid = request.isRequestedSessionIdValid();
    out.println("requestedId=" + requestedId);
    out.println("isRequestedIdFromCookie=" + isRequestedIdFromCookie);
    out.println("isRequestedIdFromUrl=" + isRequestedIdFromUrl);
    out.println("isRequestedIdValid=" + isRequestedIdValid);
    HttpSession session = request.getSession(true);
    out.println("after session creation: requestedId=" + requestedId);
    out.println("after session creation: isRequestedIdFromCookie=" + isRequestedIdFromCookie);
    out.println("after session creation: isRequestedIdFromUrl=" + isRequestedIdFromUrl);
    out.println("after session creation: isRequestedIdValid=" + isRequestedIdValid);
  }

}
