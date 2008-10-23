// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.opengse.testlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

/**
 * A servlet that dumps all URI related information
 *
 * @author Mike Jennings
 */
public class URITestlet extends HttpServlet {

  @Override
  protected void service(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/plain");
    PrintWriter out = response.getWriter();
    TestHelper.printUriStuff(request, out);
    TestHelper.printAttributeStuff(request, out);
  }
}
