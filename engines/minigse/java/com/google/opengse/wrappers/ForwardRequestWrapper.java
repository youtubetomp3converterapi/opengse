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

package com.google.opengse.wrappers;

import com.google.opengse.httputil.FormUrlDecoder;
import com.google.opengse.httputil.ParamMap;

import java.net.URI;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Wrapper implementation of {@link javax.servlet.http.HttpServletRequest} that
 * translates the request to the forwarded path. The main
 * responsibility of this class is to reflect the path elements of
 * the new translated request, and to aggregate the parameters and
 * query string together from both the old and new request.
 *
 * Per the spec: "For a
 * RequestDispatcher obtained via getRequestDispatcher(), the ServletRequest
 * object has its path elements and parameters adjusted to match the path of
 * the target resource."
 *
 * "The parameters specified in the query string used to create the
 * RequestDispatcher take precedence over other parameters of the
 * same name passed to the included servlet."
 *
 * "The request dispatching mechanism is responsible for aggregating
 * query string parameters when forwarding or including requests."
 *
 * Note: this class is required to extend {@link
 * javax.servlet.http.HttpServletRequestWrapper} per the servlet spec.
 *
 *
 * @author Mike Jennings
 */
public final class ForwardRequestWrapper
    extends HttpServletRequestWrapper {
  private static final String PREFIX = "javax.servlet.forward.";
  private static final String REQUEST_URI = PREFIX + "request_uri";
  private static final String CONTEXT_PATH = PREFIX + "context_path";
  private static final String SERVLET_PATH = PREFIX + "servlet_path";
  private static final String PATH_INFO = PREFIX + "path_info";
  private static final String QUERY_STRING = PREFIX + "query_string";


  private final Map<String, String> attributes_ =
      new HashMap<String, String>();

  private final URI forwardedPath;
  private final String queryString;
  private final String servletPath;
  private final ParamMap queryParams;

  /**
   * @param request the original request, which asked to be
   * forwarded, and which was passed into the {@link
   * javax.servlet.RequestDispatcher#forward(
   * javax.servlet.ServletRequest, javax.servlet.ServletResponse)}
   * @param forwardedPath the new path
   * path
   */
  private ForwardRequestWrapper(
      final HttpServletRequest request, final URI forwardedPath) {
    super(request);
    this.forwardedPath = forwardedPath;

    /*
     * From the Servlet 2.4 specification:
     *
     * Except for servlets obtained by using the getNamedDispatcher method,
     * a servlet that has been invoked by another servlet using the forward
     * method of RequestDispatcher has access to the path of the original
     * request.
     *
     * The following request attributes must be set:
     *   javax.servlet.forward.request_uri
     *   javax.servlet.forward.context_path
     *   javax.servlet.forward.servlet_path
     *   javax.servlet.forward.path_info
     *   javax.servlet.forward.query_string
     *
     * The values of these attributes must be equal to the return values of
     * the HttpServletRequest methods getRequestURI, getContextPath,
     * getServletPath, getPathInfo, getQueryString respectively, invoked on
     * the request object passed to the first servlet object in the call chain
     * that received the request from the client.
     *
     * These attributes are accessible from the forwarded servlet via the
     * getAttribute method on the request object. Note that these attributes
     * must always reflect the information in the original request even under
     * the situation that multiple forwards and subsequent includes are
     * called.
     */
    final String originalRequestURI = request.getRequestURI();
    queryString = forwardedPath.getRawQuery();
    final String tmpServletPath = forwardedPath.getPath();
    final String contextPath = request.getContextPath();
    servletPath = tmpServletPath.substring(contextPath.length());
    queryParams = new ParamMap();
    FormUrlDecoder.parse(
        queryString, queryParams, request.getCharacterEncoding());
    putAttribute(REQUEST_URI, originalRequestURI);
    putAttribute(CONTEXT_PATH, request.getContextPath());
    putAttribute(SERVLET_PATH, request.getServletPath());
    putAttribute(PATH_INFO, request.getPathInfo());
    putAttribute(QUERY_STRING, request.getQueryString());
  }

  public static HttpServletRequest create(
      final HttpServletRequest request, final URI forwardedPath) {
    return new ForwardRequestWrapper(request, forwardedPath);
  }

  private void putAttribute(final String attributeName, final String newValue) {
    final String value = (String) getAttribute(attributeName);

    if (value == null) {
      attributes_.put(attributeName, newValue);
    }
  }

  @Override
  public String getPathInfo() {
    return null;
  }

  @Override
  public String getRequestURI() {
    if (forwardedPath == null) {
      return super.getRequestURI();
    } else {
      return forwardedPath.getPath();
    }
  }

  @Override
  public String getServletPath() {
    return servletPath;
  }

  @Override
  public String getQueryString() {
    return queryString;
  }

  @Override
  public String[] getParameterValues(final String name) {
    return queryParams.get(name);
  }

  @Override
  public String getParameter(final String name) {
    final String[] values = getParameterValues(name);
    return (values == null) ? null : values[0];
  }



  @Override
  public Object getAttribute(final String name) {
    if (attributes_.containsKey(name)) {
      return attributes_.get(name);
    }
    return super.getAttribute(name);
  }

  @Override
  public Enumeration<String> getAttributeNames() {
    final Set<String> set = new HashSet<String>(attributes_.keySet());

    final Enumeration<?> e = super.getAttributeNames();
    while (e.hasMoreElements()) {
      set.add(e.nextElement().toString());
    }
    return Collections.enumeration(set);
  }

  @Override
  public void removeAttribute(final String name) {
    attributes_.remove(name);
    super.removeAttribute(name);
  }

  @Override
  public void setAttribute(final String name, final Object o) {
    attributes_.remove(name);
    super.setAttribute(name, o);
  }

  /**
   * Expose this to the rest of the package; not sure if it's needed,
   * but it removed the warning.
   */
  URI getForwardedPath() {
    return forwardedPath;
  }
}
