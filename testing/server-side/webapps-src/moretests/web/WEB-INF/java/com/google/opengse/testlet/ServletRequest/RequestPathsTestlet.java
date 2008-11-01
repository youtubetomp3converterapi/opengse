// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.opengse.testlet.ServletRequest;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * A Test for {@link javax.servlet.http.HttpServletRequest#getServletPath()}
 * and {@link javax.servlet.http.HttpServletRequest#getPathInfo()}.
 *
 * This augments the 2.3 watchdog test suite, which doesn't cover any wildcard
 * servlet mapping.
 *
 * @author Wenbo Zhu
 */
public class RequestPathsTestlet extends HttpServlet {

  public void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/plain");
    response.getWriter().println("servletPath="
        + request.getServletPath());
    response.getWriter().println("pathInfo="
        + request.getPathInfo());
  }
}
