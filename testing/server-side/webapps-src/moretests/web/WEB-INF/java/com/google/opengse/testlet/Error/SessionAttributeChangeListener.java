// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.opengse.testlet.Error;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * Throw an exception for servlets (app) to handle (but not).
 *
 * @author Wenbo Zhu
 */
public class SessionAttributeChangeListener implements HttpSessionAttributeListener {

  public void attributeAdded ( HttpSessionBindingEvent se ) {
    String id = se.getName();  
    if (id.equals("id")) {
      if (se.getValue().equals("noMoreAttribute")) {
        throw new IllegalArgumentException("what the hell");
      }
    }
  }

  public void attributeRemoved ( HttpSessionBindingEvent se ) {

  }

  public void attributeReplaced ( HttpSessionBindingEvent se ) {

  }
}
