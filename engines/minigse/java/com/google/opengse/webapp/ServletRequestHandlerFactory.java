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
import com.google.opengse.handlers.UnavailableHandler;

import javax.servlet.FilterChain;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * Contains bookkeeping for servlets
 *
 * @author Mike Jennings
 */
class ServletRequestHandlerFactory
    extends ServletOrJspFileRequestHandlerFactory {
  Servlet servlet;
  boolean initialized;
  ServletContext context;
  Throwable initException;
  private static FilterChain unavailableHandler = new UnavailableHandler();

  ServletRequestHandlerFactory(ErrorPageManager errorManager,
      WebAppServlet servletDef, Servlet servlet) {
    super(errorManager, servletDef);
    this.servlet = servlet;
    initialized = false;
    context = null;
    initException = null;
  }

  private void initialize()
      throws ServletException {
    servlet.init(ServletManager.createServletConfig(context, servletDef));
    initialized = true;
  }

  public void initialize(ServletContext servletContext) {
    this.context = servletContext;
    try {
      initialize();
    } catch (Throwable e) {
      initException = e;
    }
  }

  public FilterChain getRequestHandler() {
    if (!initialized) {
      try {
        initialize();
      } catch (Throwable e) {
        /* ignored */
      }
    }
    if (!initialized) {
      return unavailableHandler;
    }
    return new ServletHandler(errorManager,  servlet, servletDef);
  }
}
