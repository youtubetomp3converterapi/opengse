// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.opengse.testlet.ServletRequest;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

/**
 * An example ServletRequestListener
 *
 * @author Mike Jennings
 */
public class MyServletRequestListener implements ServletRequestListener {

  public void requestDestroyed(ServletRequestEvent event) {
    event.getServletRequest().setAttribute("requestDestroyedEvent", "Hello from MyServletRequestListener: request destroyed");
  }

  public synchronized void requestInitialized(ServletRequestEvent event) {
    event.getServletRequest().setAttribute("requestIntializedEvent", "Hello from MyServletRequestListener: request initialized");
  }
}
