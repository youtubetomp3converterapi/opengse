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

package com.google.opengse.wrappers;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;

/**
 * Forwarding implementation of {@link HttpSession} that passes all calls
 * directly through to a delegate instance.
 *
 * @author Mike Jennings
 */
public class HttpSessionWrapper implements HttpSession {
  private final transient HttpSession delegate;

  public HttpSessionWrapper(final HttpSession delegate) {
    this.delegate = delegate;
  }

  protected HttpSession getDelegate() {
    return delegate;
  }

  public long getCreationTime() {
    return delegate.getCreationTime();
  }

  public String getId() {
    return delegate.getId();
  }

  public long getLastAccessedTime() {
    return delegate.getLastAccessedTime();
  }

  public ServletContext getServletContext() {
    return delegate.getServletContext();
  }

  public void setMaxInactiveInterval(final int interval) {
    delegate.setMaxInactiveInterval(interval);
  }

  public int getMaxInactiveInterval() {
    return delegate.getMaxInactiveInterval();
  }

  @Deprecated
  public javax.servlet.http.HttpSessionContext getSessionContext() {
    return delegate.getSessionContext();
  }

  public Object getAttribute(final String name) {
    return delegate.getAttribute(name);
  }

  @Deprecated
  public Object getValue(final String name) {
    return delegate.getValue(name);
  }

  public Enumeration<?> getAttributeNames() {
    return delegate.getAttributeNames();
  }

  @Deprecated
  public String[] getValueNames() {
    return delegate.getValueNames();
  }

  public void setAttribute(final String name, final Object value) {
    delegate.setAttribute(name, value);
  }

  @Deprecated
  public void putValue(final String name, final Object value) {
    delegate.putValue(name, value);
  }

  public void removeAttribute(final String name) {
    delegate.removeAttribute(name);
  }

  @Deprecated
  public void removeValue(final String name) {
    delegate.removeValue(name);
  }

  public void invalidate() {
    delegate.invalidate();
  }

  public boolean isNew() {
    return delegate.isNew();
  }

  @Override
  public String toString() {
    return delegate.toString();
  }
}
