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
 * Used for testing the Filter order.
 *
 * @author Wenbo Zhu
 */
public class FilterOrder1Filter implements Filter {

  public void doFilter( ServletRequest request, ServletResponse response,
                        FilterChain chain ) throws IOException, ServletException {
    response.getWriter().print("FilterOrder1Filter-");
    chain.doFilter(request, response);
  }

  public void destroy() {
  }

  public void init( FilterConfig filterConfig ) {
  }
}
