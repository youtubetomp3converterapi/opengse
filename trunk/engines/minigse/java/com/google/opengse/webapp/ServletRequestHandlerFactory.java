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
import com.google.opengse.handlers.PermanentlyUnavailableHandler;
import com.google.opengse.handlers.TemporarilyUnavailableHandler;

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
  private static FilterChain permanentlyUnavailableHandler = new PermanentlyUnavailableHandler();
  private static FilterChain temporarilyUnavailableHandler = new TemporarilyUnavailableHandler();

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

/*
A servlet may throw either a ServletException or an UnavailableException during
the service of a request. A ServletException signals that some error occurred
during the processing of the request and that the container should take appropriate
measures to clean up the request.
An UnavailableException signals that the servlet is unable to handle requests
either temporarily or permanently.
If a permanent unavailability is indicated by the UnavailableException, the
servlet container must remove the servlet from service, call its destroy method,
and release the servlet instance. Any requests refused by the container by that
cause must be returned with a SC_NOT_FOUND (404) response.
If temporary unavailability is indicated by the UnavailableException, the
container may choose to not route any requests through the servlet during the time
period of the temporary unavailability. Any requests refused by the container during
this period must be returned with a SC_SERVICE_UNAVAILABLE (503) response
status along with a Retry-After header indicating when the unavailability will
terminate.
The container may choose to ignore the distinction between a permanent and
temporary unavailability and treat all UnavailableExceptions as permanent,
thereby removing a servlet that throws any UnavailableException from service.
   */
  public FilterChain getRequestHandler() {
    if (!initialized) {
      try {
        initialize();
      } catch (Throwable e) {
        /* ignored */
      }
    }
    if (!initialized) {
// TODO: figure out when to return temporarilyUnavailableHandler      
      return permanentlyUnavailableHandler;
    }
    return new ServletHandler(errorManager,  servlet, servletDef);
  }
}
