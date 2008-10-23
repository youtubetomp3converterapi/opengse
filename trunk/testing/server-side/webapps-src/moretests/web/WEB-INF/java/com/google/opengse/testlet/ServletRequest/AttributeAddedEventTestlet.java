// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.opengse.testlet.ServletRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Wenbo Zhu
 */
public class AttributeAddedEventTestlet extends HttpServlet {

  @Override
  protected void service(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/plain");
    PrintWriter out = response.getWriter();
    request.setAttribute("foo", "bar");
    String message = (String) request.getAttribute("message");
    if (message == null) {
      out.println("FAILED");
    } else {
      out.println("PASSED");
      out.println(message);
    }
  }
}

