// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.opengse.examples.noxml;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

/**
 * A basic servlet which sends a "hello world"
 *
 * @author jennings@google.com (Mike Jennings)
 */
public class BasicServlet extends HttpServlet {
  public BasicServlet() {
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
      ServletException, IOException {
    response.setContentType("text/plain");
    PrintWriter out = response.getWriter();
    String message = (String) getServletContext().getAttribute("message");
    if (message == null) {
      out.println("No message attribute found :(");
    } else {
      out.println(message);
    }
    out.flush();
  }
}
