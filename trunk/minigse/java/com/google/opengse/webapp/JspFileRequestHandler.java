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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Adapts a servlet to the RequestHandler interface.
 *
 * This approach uses the forward mechanism, and by-passes the required servlet
 * init parameters.
 *
 * @author Mike Jennings
 * @deprecated
 */
@Deprecated
class JspFileRequestHandler implements FilterChain {
  private final String jspFile;
  private final ErrorPageManager errorManager;
  private final WebAppServlet servletDef;

  JspFileRequestHandler(
      final ErrorPageManager errorManager,
      final WebAppServlet servletDef,
      final String jspFile) {
    this.errorManager = errorManager;
    this.servletDef = servletDef;
    this.jspFile = jspFile;
  }

  public void doFilter(final ServletRequest req, final ServletResponse resp)
      throws IOException, ServletException {
    final String servletName
        = (servletDef == null) ? null : servletDef.getServletName();
    final ServletResponse wrappedResponse
        = errorManager.wrapResponse(req, resp, servletName);
    try {
      // wrap the response so errors can be handled
      // get the dispatcher for the jsp file
      RequestDispatcher jspDispatcher = req.getRequestDispatcher(jspFile);
      if (jspDispatcher == null) {
        throw new ServletException("No dispatcher found for '" + jspFile + "'");
      }
      jspDispatcher.forward(req, wrappedResponse);
    } catch (IOException e) {
      errorManager.handleException(e, servletName, req, wrappedResponse);
    } catch (ServletException e) {
      errorManager.handleException(e, servletName, req, wrappedResponse);
    } catch (RuntimeException e) {
      errorManager.handleException(e, servletName, req, wrappedResponse);
    }
  }
}