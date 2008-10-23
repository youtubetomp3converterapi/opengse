// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.opengse.testlet.WebApp;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * Access resource file from under WEB_INF.
 *
 * @author Wenbo Zhu
 */
public class WebInfResourceTestlet extends HttpServlet {

  @Override
  protected void service(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/plain");
    ServletContext servletContext = getServletContext();
    InputStream resource = servletContext.getResourceAsStream("/WEB-INF/testdata/resource.txt");
    if (resource != null) {
      response.getWriter().println("PASSED");
    } else {
      response.getWriter().println("FAILED: can't open resource from WEB-INF.");
    }
  }
}
