// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.opengse.examples.noxml;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;

/**
 * Demonstrates a basic ServletContextListener
 *
 * @author jennings@google.com (Mike Jennings)
 */
public class MyServletContextListener implements ServletContextListener {

  public void contextInitialized(ServletContextEvent sce) {
    sce.getServletContext().setAttribute("message", "Hello from MyServletContextListener");
  }

  public void contextDestroyed(ServletContextEvent sce) {

  }
}
