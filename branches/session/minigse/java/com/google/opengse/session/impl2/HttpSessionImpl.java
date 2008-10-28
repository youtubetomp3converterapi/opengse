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

package com.google.opengse.session.impl2;

import com.google.opengse.util.IteratorEnumeration;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import javax.servlet.ServletContext;
import java.util.Enumeration;
import java.util.Map;
import java.io.Serializable;

/**
 * @author jennings
 *         Date: Sep 19, 2008
 */
final class HttpSessionImpl implements HttpSession {
  private static final int DEFAULT_SESSION_TIMEOUT_SECONDS = 30 * 60;
  private String id;
  private int secondsOfInactivityBeforeTimeout;
  private Map<String, Serializable> objectMap;
  private long creationTime;
  private long lastAccessedTime;
  private boolean isNew;

  HttpSessionImpl(String id, boolean isNew, Map<String, Serializable> objectMap) {
    this.id = id;
    this.secondsOfInactivityBeforeTimeout = DEFAULT_SESSION_TIMEOUT_SECONDS;
    this.objectMap = objectMap;
    this.isNew = isNew;
    creationTime = System.currentTimeMillis();
  }

  private void updateLastAccessedTime() {
    lastAccessedTime = System.currentTimeMillis();
  }

  private boolean isInvalidated() {
    return false;
  }

  private void maybeThrowIllegalStateException() {
    if (isInvalidated()) {
      throw new IllegalStateException("illegal access to invalidated session");
    }
  }

  public long getCreationTime() {
    maybeThrowIllegalStateException();
    return creationTime;
  }

  public String getId() {
    maybeThrowIllegalStateException();
    return id;
  }

  public long getLastAccessedTime() {
    maybeThrowIllegalStateException();
    return lastAccessedTime;
  }

  public ServletContext getServletContext() {
    return null;
  }

  public void setMaxInactiveInterval(int interval) {
    secondsOfInactivityBeforeTimeout = interval;
  }

  public int getMaxInactiveInterval() {
    return secondsOfInactivityBeforeTimeout;
  }

  public HttpSessionContext getSessionContext() {
    return null;
  }

  public Object getAttribute(String name) {
    maybeThrowIllegalStateException();
    updateLastAccessedTime();
    return objectMap.get(name);
  }

  public void setAttribute(String name, Object value) {
    maybeThrowIllegalStateException();
    updateLastAccessedTime();
    // we want to force people to use serializable objects. That way their
    // sessions can be distributed.
    Serializable svalue = (Serializable) value;
    objectMap.put(name, svalue);
  }
  

  public Object getValue(String name) {
    return getAttribute(name);
  }

  public Enumeration getAttributeNames() {
    maybeThrowIllegalStateException();
    updateLastAccessedTime();
    return new IteratorEnumeration(objectMap.keySet().iterator());
  }

  public String[] getValueNames() {
    maybeThrowIllegalStateException();
    updateLastAccessedTime();
    if (objectMap.isEmpty()) {
      return new String[0];
    }
    String[] valueNames = new String[objectMap.size()];
    objectMap.keySet().toArray(valueNames);
    return valueNames;
  }


  public void putValue(String name, Object value) {
    setAttribute(name, value);
  }

  public void removeAttribute(String name) {
    maybeThrowIllegalStateException();
    updateLastAccessedTime();
    objectMap.remove(name);
  }

  public void removeValue(String name) {
    removeAttribute(name);
  }

  public boolean isNew() {
    maybeThrowIllegalStateException();
    updateLastAccessedTime();
    return isNew;
  }
  
  public void invalidate() {
    maybeThrowIllegalStateException();
  }

}
