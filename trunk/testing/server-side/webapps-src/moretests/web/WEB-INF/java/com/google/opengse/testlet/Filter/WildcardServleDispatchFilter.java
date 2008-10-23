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
 * This filter is matched to any servlet via the wildcard web.xml mapping rule.
 *
 * @author Wenbo Zhu
 */
public class WildcardServleDispatchFilter implements Filter {

  public void doFilter( ServletRequest request, ServletResponse response,
                        FilterChain chain ) throws IOException, ServletException {
    request.setAttribute("wildcardDispatchFilter", "Hello from wildcardDispatchFilter");
    chain.doFilter(request, response);
  }

  public void destroy() {
  }

  public void init( FilterConfig filterConfig ) {
  }
}
