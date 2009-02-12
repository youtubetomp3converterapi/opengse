// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.opengse.testlet.Error;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSession;

/**
 * Throws an exception out of the the app code stack.
 *
 * This is not yet used - as our TCs are all client-driven.
 *
 * The spec says: "crash" the web app - 500 code only.
 *
 * @author Wenbo Zhu
 */
public class SessionTimeoutListener implements HttpSessionListener {

  public void sessionCreated ( HttpSessionEvent se ) {
  }

  public void sessionDestroyed ( HttpSessionEvent se ) {
    HttpSession session = se.getSession();
    if (session == null) {
      throw new NullPointerException("null session!");
    }
    String id = (String) session.getAttribute("id");
    if (id != null && id.equals("timedoutSession")) {
      // timed-out
      throw new IllegalArgumentException("what the hell");
    }
  }
}
