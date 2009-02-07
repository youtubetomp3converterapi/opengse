// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.opengse.testlet.Error;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Any uncaught exception from  SessionAttributeChangeListener should result in
 * a 500 status.
 *
 * @author Wenbo Zhu
 */
public class UnhandledSessionExceptionTestlet extends HttpServlet {

  @Override
  protected void service(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/plain");
    PrintWriter out = response.getWriter();
    String id = request.getParameter("id");
    HttpSession session = request.getSession(false);
    if (session != null) {
      session.invalidate();
    }
    session = request.getSession( true );
    // SessionAttributeChangeListener throws IllegalArgumentException if it sees an attribute called
    // "noMoreAttribute"
    try {
      session.setAttribute("id", id);             // may throw exception
      out.println("FAILED");
    } catch(Throwable t) {
      out.println("PASSED");
      throw new IllegalArgumentException("Force an error code of 500");
    }
    session.setMaxInactiveInterval(1);
  }
}
