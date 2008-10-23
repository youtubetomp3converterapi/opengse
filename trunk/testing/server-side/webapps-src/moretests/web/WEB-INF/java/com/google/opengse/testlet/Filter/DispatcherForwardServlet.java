// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.opengse.testlet.Filter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The upstream servlet for DispatcherFilterTestlet.
 *
 * @author Wenbo Zhu
 */
public class DispatcherForwardServlet extends HttpServlet {

  @Override
  protected void service(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/plain");
    String path = request.getParameter("forwardTarget");
    RequestDispatcher rd = getServletContext().getRequestDispatcher(path);
    rd.forward( request, response );
  }
}


