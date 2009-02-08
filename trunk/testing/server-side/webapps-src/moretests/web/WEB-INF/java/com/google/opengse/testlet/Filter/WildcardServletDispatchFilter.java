// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.opengse.testlet.Filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

/**
 * This filter is matched to any servlet via the wildcard web.xml mapping rule.
 *
 * @author Wenbo Zhu
 */
public class WildcardServletDispatchFilter implements Filter {

  private static final String SERVLETPATH2_PATHINFO = "/servletpath2/pathinfo";

  public void doFilter( ServletRequest request, ServletResponse response,
                        FilterChain chain ) throws IOException, ServletException {
    request.setAttribute("wildcardDispatchFilter", "Hello from wildcardDispatchFilter");
    if (request instanceof HttpServletRequest) {
      HttpServletRequest httpRequest = (HttpServletRequest) request;
      String servletPath = httpRequest.getServletPath();
      String pathInfo = httpRequest.getPathInfo();
      String path = servletPath + pathInfo;
      if (path.indexOf(SERVLETPATH2_PATHINFO) != -1 || path.startsWith(SERVLETPATH2_PATHINFO)) {
        response.setContentType("text/plain");
        response.getWriter().println("WildcardServletDispatchFilter.servletPath=" + servletPath);
        response.getWriter().println("WildcardServletDispatchFilter.pathInfo=" + pathInfo);
        return; // don't pass things along the chain
      }
    }
    chain.doFilter(request, response);
  }

  public void destroy() {
  }

  public void init( FilterConfig filterConfig ) {
  }
}
