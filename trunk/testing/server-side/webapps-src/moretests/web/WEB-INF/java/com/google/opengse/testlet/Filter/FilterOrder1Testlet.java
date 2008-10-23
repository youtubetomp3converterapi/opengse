// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.opengse.testlet.Filter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet No.1 for testing the filter order.
 *
 * @author Wenbo Zhu
 */
public class FilterOrder1Testlet extends HttpServlet {

  @Override
  protected void service(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/plain");
    PrintWriter out = response.getWriter();
    out.print("FilterOrder1Testlet");
  }
}