// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.opengse.testlet.Filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author Wenbo Zhu
 */
public class ServletForwardFilter implements Filter {

  public void doFilter( ServletRequest request, ServletResponse response,
                        FilterChain chain ) throws IOException, ServletException {
    request.setAttribute("forwardFilter", "Hello from ServletForwardFilter request");
    chain.doFilter(request, response);
    response.getWriter().println("Hello from ServletForwardFilter response");
  }

  public void destroy() {
  }

  public void init( FilterConfig filterConfig ) {
  }
}
