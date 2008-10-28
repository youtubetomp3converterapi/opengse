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

import com.google.opengse.filters.RegularExpressionRequestHandler;
import com.google.opengse.filters.RegularExpressionRequestHandlerDispatcher;
import com.google.opengse.wrappers.ForwardRequestWrapper;
import com.google.opengse.wrappers.ForwardResponseWrapper;
import com.google.opengse.wrappers.IncludeRequestWrapper;
import com.google.opengse.wrappers.IncludeResponseWrapper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * An implementation of RequestDispatcher.
 *
 * @author Mike Jennings
 */
final class RequestDispatcherImpl implements RequestDispatcher {

  /**
   * This attribute is introduced to identify any "error-handling" oriented
   * forward. For such a forward, only filters that come with
   * "<dispatcher>ERROR</dispatcher>" will be applied. This is a transient
   * attribute, and should only be read once and cleared thereafter. Currently
   * this is only used by {@link ErrorPageManager}. Using request attributes
   * for passing invocation context isn't the best approach per se, and we may
   * consider using ThreadLocal to manage this type of internal contexts.
   */
  static final String ERROR_DISPATCHER_FORWARD_ATTRIBUTE =
      "error.dispatcher.forward";

  private String path;
  private final RegularExpressionRequestHandlerDispatcher handlerDispatcher;

  private RequestDispatcherImpl(String path,
      RegularExpressionRequestHandlerDispatcher handlerDispatcher) {
    this.path = path;
    this.handlerDispatcher = handlerDispatcher;
  }

  static RequestDispatcher create(String path,
      RegularExpressionRequestHandlerDispatcher handlerDispatcher) {
    return new RequestDispatcherImpl(path, handlerDispatcher);
  }

  public void forward(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    forward((HttpServletRequest) request, (HttpServletResponse) response);
  }

  void forward(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    if (response.isCommitted()) {
      throw new IllegalStateException("Response already committed");
    }
    URI requestUri;
    try {
      requestUri = solveURI(request);
    } catch (URISyntaxException e) {
      throw new ServletException(e);
    }
    request = ForwardRequestWrapper.create(request, requestUri);
    response = ForwardResponseWrapper.create(response);
    RegularExpressionRequestHandler handler;
    try {
      if (request.getAttribute(ERROR_DISPATCHER_FORWARD_ATTRIBUTE) != null) {
        handler = handlerDispatcher.getErrorHandler();
        request.removeAttribute(ERROR_DISPATCHER_FORWARD_ATTRIBUTE);
      } else {
        handler = handlerDispatcher.getForwardHandler();
      }
      handler.getHandler(new URI(path)).doFilter(request, response);
    } catch (URISyntaxException ex) {
      throw new ServletException("Incorrect dispatcher path: " + path);
    }
    // now we close the output stream. For some reason this is what
    // makes testImplicitImportServlet pass
    try {
      response.getWriter().close();
    } catch (IllegalStateException ignored) {
      response.getOutputStream().close();
    }
  }

  /**
   * Solve any relative dispatch path, and create the URI for the dispatched
   * request.
   */
  private URI solveURI(HttpServletRequest request) throws URISyntaxException {
    if (!path.startsWith("/")) {
      String servletPath = request.getServletPath();
      String parentPath = servletPath.substring(
              0, servletPath.lastIndexOf("/") + 1); // this is always safe

      // reset the path to being absolute to contextPath
      path = parentPath + path;
    }
    String contextPath = request.getContextPath();
    return new URI(contextPath + path);
  }

  public void include(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    include((HttpServletRequest) request, (HttpServletResponse) response);
  }

  public void include(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    URI requestUri;
    try {
      requestUri = solveURI(request);
    } catch (URISyntaxException e) {
      throw new ServletException(e);
    }

    request = IncludeRequestWrapper.create(request, requestUri);
    response = IncludeResponseWrapper.create(response);
    try {
      handlerDispatcher.getIncludeHandler().getHandler(
          new URI(path)).doFilter(request, response);
    } catch (URISyntaxException ex) {
      throw new ServletException("Incorrect dispatcher path: " + path);
    }
  }
}
