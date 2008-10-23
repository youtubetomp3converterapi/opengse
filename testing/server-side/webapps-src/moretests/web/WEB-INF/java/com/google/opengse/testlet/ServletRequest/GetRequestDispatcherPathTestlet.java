// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.opengse.testlet.ServletRequest;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Use a relative path to get a dispatcher relative to the current request path.
 * @author Wenbo Zhu
 */
public class GetRequestDispatcherPathTestlet extends HttpServlet {

  @Override
  protected void service(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/plain");
    ServletContext servletContext = getServletContext();
    request.setAttribute("dispatch", "1");
    RequestDispatcher dispatcher1 = servletContext.getRequestDispatcher("/subcontextpath/DispatchTargetServlet");
    dispatcher1.include(request, response);

    request.setAttribute("dispatch", "2");
    RequestDispatcher dispatcher2 = request.getRequestDispatcher("DispatchTargetServlet");  // == /subcontextpath/DispatchTargetServlet
    dispatcher2.include(request, response);
  }
}

