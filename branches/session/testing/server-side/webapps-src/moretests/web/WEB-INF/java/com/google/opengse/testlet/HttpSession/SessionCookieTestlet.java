package com.google.opengse.testlet.HttpSession;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * A servlet which stores a key/value pair in the session
 * @author jennings
 *         Date: Jul 6, 2008
 */
public class SessionCookieTestlet extends HttpServlet {
  @Override
  protected void service(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/plain");
    HttpSession session = request.getSession(true);
    session.setAttribute("foo", "bar");
    String shouldBeBar = (String)session.getAttribute("foo");
    if ("bar".equals(shouldBeBar))
      response.getWriter().println("PASSED");
    else
      response.getWriter().println("PASSED");
  }

}
