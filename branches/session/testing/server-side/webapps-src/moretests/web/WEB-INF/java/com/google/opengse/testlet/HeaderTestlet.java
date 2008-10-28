package com.google.opengse.testlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet which dumps request headers in a deterministic way
 *
 * @author jennings
 *         Date: Jun 19, 2008
 */
public class HeaderTestlet extends HttpServlet {
  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/plain");
    PrintWriter out = response.getWriter();
    TestHelper.printHeaderStuff(request, out);
  }

}
