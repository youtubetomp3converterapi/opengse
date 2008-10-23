// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.opengse.testlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;

/**
 * A servlet which forwards requests to "/foo"
 *
 * @author Mike Jennings
 */
public class ForwardToFooTestlet extends HttpServlet {

  @Override
  protected void service(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    String forwardTo = "/foo";
    RequestDispatcher dispatcher = request.getRequestDispatcher(forwardTo);
    if (dispatcher == null) {
      sendError(response, "No dispatcher found for '" + forwardTo + "'");
      return;
    }
    dispatcher.forward(request, response);
  }

  private void sendError(HttpServletResponse response, String msg) throws ServletException, IOException {
    response.setContentType("text/plain");
    PrintWriter out = response.getWriter();
    out.println(msg);
  }
}