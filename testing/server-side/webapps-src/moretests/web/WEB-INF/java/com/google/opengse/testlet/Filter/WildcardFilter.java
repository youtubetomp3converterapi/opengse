// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.opengse.testlet.Filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * This filter is matched to any servlet via the wildcard web.xml mapping rule.
 *
 * @author Wenbo Zhu
 */
public class WildcardFilter implements Filter {

  private static final String SERVLETPATH3 = "/servletpath3/";

  public void doFilter( ServletRequest request, ServletResponse response,
                        FilterChain chain ) throws IOException, ServletException {
    if (request instanceof HttpServletRequest) {
      doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    } else {
      chain.doFilter(request, response);
    }
  }

  private static boolean hasServletPath3(String s) {
    return (s != null && s.indexOf(SERVLETPATH3) > -1);
  }

  private void doFilter( HttpServletRequest request, HttpServletResponse response,
                        FilterChain chain ) throws IOException, ServletException {
    String servletPath = request.getServletPath();
    String pathInfo = request.getPathInfo();
    if (hasServletPath3(servletPath) || hasServletPath3(pathInfo)) {
      response.setContentType("text/plain");
      response.getWriter().println("WildcardFilter.servletPath=" + servletPath);
      response.getWriter().println("WildcardFilter.pathInfo=" + pathInfo);
    } else {
      // not interested in this request, so pass it down the chain
      chain.doFilter(request, response);
    }
  }

  public void destroy() {
  }

  public void init( FilterConfig filterConfig ) {
  }
}