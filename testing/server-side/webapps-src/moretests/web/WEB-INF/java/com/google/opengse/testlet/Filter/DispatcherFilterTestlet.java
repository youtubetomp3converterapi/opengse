// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.opengse.testlet.Filter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The target of the dispatcher, or the direct client-request target. 
 *
 * When filter mapping doesn't come with "dispatcher", it is only applied to
 * "client" requests. Dispatcher.forward or Dispatcher.include should invoke
 * a filter only if "dispatcher" is explicitly specified.
 *
 * @author Wenbo Zhu
 */
public class DispatcherFilterTestlet extends HttpServlet {

  @Override
  protected void service(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/plain");  // in case a direct invocation
    PrintWriter out = response.getWriter();
    if (request.getAttribute("requestFilter") != null ) {
      out.println(request.getAttribute("requestFilter"));
    }
    if (request.getAttribute("includeFilter") != null ) {
      out.println(request.getAttribute("includeFilter"));
    }

    if (request.getAttribute("forwardFilter") != null ) {
      out.println(request.getAttribute("forwardFilter"));
    }
    
    if (request.getAttribute("errorFilter") != null ) {
      out.println(request.getAttribute("errorFilter"));
    }

    if (request.getAttribute("mixedMatchingFilter") != null ) {
      out.println(request.getAttribute("mixedMatchingFilter"));
    }

    if (request.getAttribute("wildcardDispatchFilter") != null ) {
      out.println(request.getAttribute("wildcardDispatchFilter"));
    }
  }
}