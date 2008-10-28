// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.opengse.testlet.ServletResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * It is recommended that containers use the X-Powered-By HTTP header to
 * publish its implementation information. The field value should consist of
 * one or more implementation types, such as "Servlet/2.4". Optionally, the
 *  supplementary information of the container and the underlying Java platform
 * can be added after the implementation type within parentheses.
 *
 * The container should be configurable to suppress this header.
 * Here is an example of this header.
 *    X-Powered-By: Servlet/2.4 JSP/2.0 (Tomcat/5.0 JRE/1.4.1)
 *
 * @author Wenbo Zhu
 */
public class XPoweredByHeaderTestlet extends HttpServlet {

  @Override
  protected void service(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/plain");
    // we should also be able to check if the header is turned on or not
    if (response.containsHeader("X-Powered-By")) {
      response.getWriter().println("PASSED");
    } else {
      response.getWriter().println("FAIL - no header found.");
    }
  }
}

