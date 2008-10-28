// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.opengse.testlet.ServletRequest;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;

/**
 * @author Wenbo Zhu
 */
public class MyServletRequestAttributeListener implements ServletRequestAttributeListener {

  public void attributeAdded(ServletRequestAttributeEvent event) {
    if (! event.getName().equals("message")){
      event.getServletRequest().setAttribute("message",
          "Hello from MyServletRequestAttributeListener: attributed added");
    }
  }

  public void attributeRemoved(ServletRequestAttributeEvent event) {
    if (! event.getName().equals("message")){
      event.getServletRequest().setAttribute("message",
          "Hello from MyServletRequestAttributeListener: attributed removed");
    }
  }

  public void attributeReplaced(ServletRequestAttributeEvent event) {
    if (! event.getName().equals("message")){
      event.getServletRequest().setAttribute("message",
          "Hello from MyServletRequestAttributeListener: attributed replaced");
    }
  }
}
