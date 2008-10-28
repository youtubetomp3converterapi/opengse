// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.opengse.testlet.ServletContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * This tests the order of listener invocations.
 *
 * What's really important is to ensure a reversed order for destroy().
 * TODO: need verify the code; and figure out how to client-drive such a test.
 *
 * This applies to all listeners.
 *
 * @author Wenbo Zhu
 */
public class ServletContextListener1 implements ServletContextListener {

  public void contextInitialized (ServletContextEvent sce) {
    String order = (String) sce.getServletContext().getAttribute("order");
    if (order == null) {
      order = "";
    }
    order += "ServletContextListener1";
    sce.getServletContext().setAttribute("order", order);
  }

  public void contextDestroyed (ServletContextEvent sce) {
  }
}
