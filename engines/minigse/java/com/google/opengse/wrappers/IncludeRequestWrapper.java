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

import com.google.opengse.httputil.ParamMap;
import com.google.opengse.httputil.FormUrlDecoder;

import java.util.Map;
import java.util.HashMap;
import java.util.Enumeration;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import java.net.URI;

import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletRequest;

/**
 * Wrapper implementation of {@link javax.servlet.http.HttpServletRequest} used
 * for includes. The path elements remain unchanged. However, parameters
 * still need to be merged between the old request and the new
 * request, with the new request taking priority. In addition, a
 * number of request attributes must be specified.
 *
 * <p>Note: this class is required to extend {@link
 * javax.servlet.http.HttpServletRequestWrapper} per the servlet spec.
 *
 * @author Mike Bostock
 * @author Mike Jennings
 */
public final class IncludeRequestWrapper extends HttpServletRequestWrapper {
  private static final String PREFIX = "javax.servlet.include.";
  private static final String REQUEST_URI = PREFIX + "request_uri";
  private static final String CONTEXT_PATH = PREFIX + "context_path";
  private static final String SERVLET_PATH = PREFIX + "servlet_path";
  private static final String PATH_INFO = PREFIX + "path_info";
  private static final String QUERY_STRING = PREFIX + "query_string";
  private final URI includeUri;
  private final String queryString;
  private final String servletPath;
  private final ParamMap queryParams;

  private final Map<String, String> attributes_ =
      new HashMap<String, String>();

  /**
   * Constructs a request object wrapping the given request.
   *
   * @throws IllegalArgumentException if the request is null
   */
  private IncludeRequestWrapper(
      final HttpServletRequest request, final URI includeUri) {
    super(request);
    this.includeUri = includeUri;
    final String contextPath = request.getContextPath();
    queryString = includeUri.getQuery();
    servletPath = includeUri.getPath().substring(contextPath.length());
    final String requestUri = includeUri.getPath();
    queryParams = new ParamMap();
    FormUrlDecoder.parse(
        queryString, queryParams, request.getCharacterEncoding());

    attributes_.put(REQUEST_URI, requestUri);
    attributes_.put(CONTEXT_PATH, contextPath);
    attributes_.put(SERVLET_PATH, servletPath);
    attributes_.put(PATH_INFO, request.getPathInfo());
    attributes_.put(QUERY_STRING, queryString);
  }

  public static HttpServletRequest create(
      final HttpServletRequest request, final URI includeUri) {
    return new IncludeRequestWrapper(request, includeUri);
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


  @Override
  public String getRequestURI() {
    return includeUri.getPath();
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



  /**
   * Expose this to package; gets rid of warning.
   */
  String getIncludePath() {
    return includeUri.getPath();
  }
}
