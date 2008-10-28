// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.opengse.testlet.WebApp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Using the absolution path (to the context) to load a static file, e.g. HTML.
 *
 * @author Wenbo Zhu
 */
public class StaticFileIncludeTestlet extends HttpServlet {

  @Override
  protected void service(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/plain");
    RequestDispatcher rd = getServletContext().getRequestDispatcher("/doc/jsp-include.txt");
    rd.include(request, response);
  }
}


