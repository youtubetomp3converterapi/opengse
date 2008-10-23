// Copyright 2008 Google Inc. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.opengse.webapp;

import com.google.opengse.wrappers.HttpSessionWrapper;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.ServletContext;

/**
 * @author Mike Jennings
 */
final class WebAppSessionWrapper extends HttpSessionWrapper {

  private final ServletContext context;
  private final HttpSessionListener sessionListener;
  private final HttpSessionAttributeListener sessionAttributeListener;
  private boolean isInvalidated_ = false;

  public WebAppSessionWrapper(HttpSession delegate, ServletContext context,
      HttpSessionListener sessionListener,
      HttpSessionAttributeListener sessionAttributeListener) {
    super(delegate);
    this.context = context;
    this.sessionListener = sessionListener;
    this.sessionAttributeListener = sessionAttributeListener;
    fireSessionCreated();
  }

  /**
   * Checks if the session has been invalidated. If no, a warning is logged.
   */
  private void maybeThrowIllegalStateException() {
    if (isInvalidated_) {
      throw new IllegalStateException("illegal access to invalidated session");
    }
  }


  private void fireSessionCreated() {
    if (sessionListener != null) {
      sessionListener.sessionCreated(new HttpSessionEvent(this));
    }
  }

  private void fireSessionDestroyed() {
    if (sessionListener != null) {
      sessionListener.sessionDestroyed(new HttpSessionEvent(this));
    }
  }


  @Override
  public ServletContext getServletContext() {
    return context;
  }

  @Override
  public void invalidate() {
    maybeThrowIllegalStateException();  // no re-entry, according to 2.3 spec
    isInvalidated_ = true;
    // accroding to 2.4 spec, fire this event BEFORE calling super.invalidate()
    fireSessionDestroyed();
    super.invalidate();
  }

  @Override
  public boolean isNew() {
    maybeThrowIllegalStateException();
    return super.isNew();
  }

  @Override
  public Object getAttribute(String name) {
    maybeThrowIllegalStateException();
    return super.getAttribute(name);
  }

  @Override
  public Enumeration<?> getAttributeNames() {
    maybeThrowIllegalStateException();
    return super.getAttributeNames();
  }

  @Override
  public void setAttribute(String name, Object value) {
    maybeThrowIllegalStateException();
    final Object oldValue = super.getAttribute(name);
    super.setAttribute(name, value);
    if (oldValue == null) {
      fireAttributeAdded(name, value);
    } else {
      fireAttributeReplaced(name, oldValue);
    }
  }

  @Override
  public void removeAttribute(String name) {
    maybeThrowIllegalStateException();
    final Object oldValue = super.getAttribute(name);
    super.removeAttribute(name);
    fireAttributeRemoved(name, oldValue);
  }

  private void fireAttributeAdded(String name, Object value) {
    if (sessionAttributeListener != null) {
      sessionAttributeListener.attributeAdded(
          new HttpSessionBindingEvent(this, name, value));
    }
  }

  private void fireAttributeReplaced(String name, Object value) {
    if (sessionAttributeListener != null) {
      sessionAttributeListener.attributeReplaced(
          new HttpSessionBindingEvent(this, name, value));
    }
  }

  private void fireAttributeRemoved(String name, Object value) {
    if (sessionAttributeListener != null) {
      sessionAttributeListener.attributeRemoved(
          new HttpSessionBindingEvent(this, name, value));
    }
  }

}
