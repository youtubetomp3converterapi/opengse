package com.google.opengse.examples.guice;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Author: Mike Jennings
 * Date: Aug 22, 2009
 * Time: 8:50:42 AM
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