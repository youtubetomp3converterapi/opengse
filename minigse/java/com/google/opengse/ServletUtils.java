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

package com.google.opengse;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * A collection of utility methods for javax.servlet objects.
 *
 * @author Mike Jennings
 */
public final class ServletUtils {
  private ServletUtils() { /* Utility class: do not instantiate */ }

  /**
   * Wraps the requestHandler in one or more filters and returns the wrapped
   * request handler.
   */
  public static FilterChain createChain(FilterChain requestHandler,
      Filter... filters) {
    List<Filter> filterList = Arrays.asList(filters);
    Collections.reverse(filterList);
    FilterChain current = requestHandler;
    for (Filter filter : filterList) {
      current = new LinkedFilterChain(filter, current);
    }
    return current;
  }

  /**
   * Convert the supplied servlet into a FilterChain
   */
  public static FilterChain asFilterChain(HttpServlet servlet) {
    return new ServletAsChain(servlet);
  }

  private static class ServletAsChain implements FilterChain {
    private final HttpServlet servlet;

    private ServletAsChain(HttpServlet servlet) {
      this.servlet = servlet;
    }

    public void doFilter(ServletRequest request,
        ServletResponse response) throws IOException, ServletException {
      servlet.service(request, response);
    }
  }


  /**
   * implementation of {@link FilterChain} which wraps a {@link Filter}
   * and has a reference to the next {@link FilterChain}
   */
  private static class LinkedFilterChain implements FilterChain {
    FilterChain next;
    private final Filter filter;

    LinkedFilterChain(Filter filter) {
      this.filter = filter;
    }

    LinkedFilterChain(Filter filter, FilterChain next) {
      this.filter = filter;
      this.next = next;
    }

    /**
     * Causes the next filter in the chain to be invoked, or if the calling
     * filter is the last filter in the chain, causes the resource at the end of
     * the chain to be invoked.
     *
     * @param request  the request to pass along the chain.
     * @param response the response to pass along the chain.
     * @since 2.3
     */
    public void doFilter(ServletRequest request, ServletResponse response)
        throws IOException, ServletException {
      filter.doFilter(request, response, next);
    }
  }
}
