// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.opengse.testlet.Security;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * In web.xml, security role may be specified with the servlet, i.e. the role
 * required to access the servlet, via the "security-role-ref" element, which
 * either links to a specific role with a different name, or if unspecified,
 * a role with the same name.
 *
 * @author Wenbo Zhu
 */
public class ServletSecurityRoleTestlet extends HttpServlet {

  @Override
  protected void service(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/plain");
    String role = request.getParameter("target-role");
    response.getWriter().println(role + "=" + request.isUserInRole(role));
    // <role-name>role_name</role-name>  = the local role name
    // <role-link>external_role</role-link> = the global role name
    // and the API should always return "local role name".
  }
}

