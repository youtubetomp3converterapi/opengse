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

import com.google.opengse.configuration.WebAppErrorPage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Mike Jennings
 */
public class ErrorPageManager {
  private static final String STATUS_CODE_ATTRIBUTE
      = "javax.servlet.error.status_code";
  private static final String EXCEPTION_TYPE_ATTRIBUTE
      = "javax.servlet.error.exception_type";
  private static final String MESSAGE_ATTRIBUTE
      = "javax.servlet.error.message";
  private static final String EXCEPTION_ATTRIBUTE
      = "javax.servlet.error.exception";
  private static final String REQUEST_URI_ATTRIBUTE
      = "javax.servlet.error.request_uri";
  private static final String SERVLET_NAME_ATTRIBUTE
      = "javax.servlet.error.servlet_name";

  private final List<WebAppErrorPage> errorPages;
  private String defaultErrorPage;

  public ErrorPageManager() {
    // do NOT change the following line to Lists.newArrayList()
    errorPages = new ArrayList<WebAppErrorPage>();
  }

  public synchronized void addErrorPage(WebAppErrorPage errorPage) {
    if (errorPage.getExceptionType() != null
        || errorPage.getErrorCode() != null) {
      errorPages.add(errorPage);
    }
    String etype = errorPage.getExceptionType();
    if (etype != null && etype.equals("java.lang.Exception")
        && errorPage.getLocation() != null) {
      defaultErrorPage = errorPage.getLocation();
    }
  }

  private synchronized String getUriForException(Throwable t) {
    String className = t.getClass().getName();
    for (WebAppErrorPage errorPage : errorPages) {
      String etype = errorPage.getExceptionType();
      if (etype != null && etype.equals(className)) {
        return errorPage.getLocation();
      }
    }
    return defaultErrorPage;
  }

  private synchronized String getUriForErrorCode(int code) {
    for (WebAppErrorPage errorPage : errorPages) {
      Integer ec = errorPage.getErrorCode();
      if (ec != null && ec.intValue() == code) {
        return errorPage.getLocation();
      }
    }
    return null;
  }


  public synchronized void handleException(
      Throwable error, String servletName, ServletRequest request,
      ServletResponse response) throws IOException, ServletException {
    String errorUrl = getUriForException(error);
    if (errorUrl == null) {
      if (error instanceof IOException) {
        throw (IOException) error;
      }
      if (error instanceof ServletException) {
        throw (ServletException) error;
      }
      throw new ServletException(error);
    } else {
      RequestDispatcher errorDispatcher
          = request.getRequestDispatcher(errorUrl);
      if (errorDispatcher == null) {
        throw new ServletException(
            "No dispatcher found for '" + errorUrl + "'");
      }
      request.setAttribute(STATUS_CODE_ATTRIBUTE, "500");
      request.setAttribute(EXCEPTION_TYPE_ATTRIBUTE,
          "class " + error.getClass().getName());
      request.setAttribute(MESSAGE_ATTRIBUTE, error.getMessage());
      request.setAttribute(EXCEPTION_ATTRIBUTE, error);
      request.setAttribute(REQUEST_URI_ATTRIBUTE, getRequestUri(request));
      request.setAttribute(SERVLET_NAME_ATTRIBUTE, servletName);
      dispatchErrorRequest(errorDispatcher, request, response);
    }
  }

  synchronized void handleError(
      int errorCode, String errorMessage, String servletName,
      HttpServletRequest request, HttpServletResponseWrapper response)
    throws IOException, ServletException {
    HttpServletResponse originalResponse =
        (HttpServletResponse) response.getResponse();
    originalResponse.setStatus(errorCode);     
    String uri = getUriForErrorCode(errorCode);
    if (uri == null) {
      if (errorMessage == null) {
        originalResponse.sendError(errorCode);
      } else {
        originalResponse.sendError(errorCode, errorMessage);
      }
      return;
    }
    RequestDispatcher errorDispatcher = request.getRequestDispatcher(uri);
    if (errorDispatcher == null) {
      if (errorMessage == null) {
        originalResponse.sendError(errorCode);
      } else {
        originalResponse.sendError(errorCode, errorMessage);
      }
      return;
    }
    request.setAttribute(STATUS_CODE_ATTRIBUTE, Integer.toString(errorCode));
    request.setAttribute(MESSAGE_ATTRIBUTE, errorMessage);
    request.setAttribute(REQUEST_URI_ATTRIBUTE, getRequestUri(request));
    request.setAttribute(SERVLET_NAME_ATTRIBUTE, servletName);
    dispatchErrorRequest(errorDispatcher, request, response);
  }

  synchronized void handleError(
      int errorCode, String servletName, HttpServletRequest request,
      HttpServletResponseWrapper response) throws IOException, ServletException {
    handleError(errorCode, null, servletName, request, response);
  }

  /**
   * @see RequestDispatcherImpl#ERROR_DISPATCHER_FORWARD_ATTRIBUTE
   */
  private void dispatchErrorRequest(
        RequestDispatcher errorDispatcher,
        ServletRequest request,
        ServletResponse response)
      throws IOException, ServletException {
    request.setAttribute(
        RequestDispatcherImpl.ERROR_DISPATCHER_FORWARD_ATTRIBUTE, "");
    errorDispatcher.forward(request, response);
  }

  public ServletResponse wrapResponse(
      ServletRequest request, ServletResponse response, String servletName) {
    if ((response instanceof HttpServletResponse)
        && (request instanceof HttpServletRequest)) {
      return wrapResponse((HttpServletRequest) request,
          (HttpServletResponse) response, servletName);
    } else {
      return response;
    }
  }


  private HttpServletResponse wrapResponse(
      HttpServletRequest request, HttpServletResponse response,
      String servletName) {
    return new ErrorCodeResponseWrapper(this, request, response, servletName);
  }


  private static String getRequestUri(ServletRequest req) {
    if (req instanceof HttpServletRequest) {
      HttpServletRequest request = (HttpServletRequest) req;
      return request.getRequestURI();
    } else {
      return null;
    }
  }
}
