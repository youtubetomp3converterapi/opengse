// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.opengse.testlet.Error;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Wenbo Zhu
 */
public class UnhandledSessionExceptionTestlet extends HttpServlet {

  @Override
  protected void service(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/plain");
    String id = request.getParameter("id");
    HttpSession session = request.getSession( true );
    session.setAttribute("id", id);             // may throw exception
    session.setMaxInactiveInterval(1);
  }
}
