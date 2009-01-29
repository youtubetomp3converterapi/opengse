// Copyright 2008 Google Inc. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.opengse.webapp;

import com.google.opengse.configuration.WebAppServlet;

import javax.servlet.FilterChain;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Adapts a servlet to the RequestHandler interface.
 * @author Mike Jennings
 */
class ServletHandler implements FilterChain {

  /**
   * Defined for supporting "jsp-file" element of a servlet definition.  If
   * present on a request, "JspServlet" will override the value returned by
   * {@code request.getServletPath()} to select the JSP page to be executed.
   */
  private static final String JSP_FILE = System.getProperty(
      "com.google.opengse.webapp.jsp_file",
      "org.apache.catalina.jsp_file");

  private final Servlet servlet;
  private final ErrorPageManager errorManager;
  private final WebAppServlet servletDef;

  ServletHandler(ErrorPageManager errorManager,
      Servlet servlet, WebAppServlet servletDef) {
    this.errorManager = errorManager;
    this.servletDef = servletDef;
    if (servlet == null) {
      throw new NullPointerException("null servlet!");
    }
    this.servlet = servlet;
  }

  public void doFilter(final ServletRequest req, final ServletResponse resp)
      throws IOException, ServletException {
    if (getJspFile() != null) {
      req.setAttribute(JSP_FILE, getJspFile());
    }
    ServletResponse wrappedResponse =
        errorManager.wrapResponse(req, resp, getName());
    try {
      servlet.service(req, wrappedResponse);
    } catch (IOException e) {
      errorManager.handleException(e, getName(), req, wrappedResponse);
    } catch (ServletException e) {
      errorManager.handleException(e, getName(), req, wrappedResponse);
    } catch (RuntimeException e) {
      errorManager.handleException(e, getName(), req, wrappedResponse);
    }
  }

  private String getName() {
    return (servletDef == null) ? null : servletDef.getServletName();
  }

  private String getJspFile() {
    return (servletDef == null) ? null : servletDef.getJspFile();
  }
}
