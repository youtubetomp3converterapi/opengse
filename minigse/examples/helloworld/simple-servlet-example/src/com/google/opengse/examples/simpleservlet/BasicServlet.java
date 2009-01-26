// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.opengse.examples.simpleservlet;

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
    PrintWriter out = response.getWriter();
    out.print("Hello world!");
    out.flush();
  }
}
