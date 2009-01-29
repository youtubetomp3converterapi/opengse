// Copyright 2002 Google Inc.
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

package com.google.opengse.session.impl;

import com.google.opengse.GSEConstants;
import com.google.opengse.util.IteratorEnumeration;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

/**
 * ServletSession: an implementation of the HttpSession interface.
 * Used to store information about a client "session" across multiple
 * HTTP requests.
 *
 * @see javax.servlet.http.HttpSession
 * @author Spencer Kimball
 */
final class HttpSessionImpl implements HttpSession, Serializable {
  private static final Logger LOGGER =
    Logger.getLogger("com.google.opengse.HttpSessionImpl");

  /**
   * A reference to the cache that holds this session
   * (used for invalidating only)
   *
   * It's a key to ServletSessionCache.getCache rather than a simple
   * reference because we need to be serializable.  Note that whenever
   * we deserialize this we must update the cacheId_.
   */
  Integer cacheId_;

  /**
   * The session data hash map
   */
  private HashMap<String, Object> sessionData_;

  /**
   * The creation time (in millis since epoch)
   */
  private long creationTime_;

  /**
   * The last accessed time (in millis since epoch)
   */
  private long lastAccessedTime_;

  /**
   * Number of times accessed
   */
  private int accessCount_;

  /**
   * The per-session timeout setting. Can be set using
   * the {@link #setMaxInactiveInterval(int)}
   */
  private long sessionTimeout_;

  /**
   * The session identifier (randomly generated)
   */
  private String sessionId_;

  /**
   * Flag to indicate whether the session is new or fetched
   * from the cache.
   */
  private boolean isNew_;

  /**
   * Flag to indicate the session has been invalidated.
   */
  private boolean isInvalidated_;

  /**
   * IP address of requesting client. This is used to warn
   * of possible session hijacking attempts.
   */
  private String clientAddr_;

  /**
   * Class constructor. Starts an empty session with a generated
   * session id.
   *
   * @param sessionTimeout the default session timeout setting
   * @param sessionId is the session id
   */
  protected HttpSessionImpl(ServletSessionCache cache,
                           long sessionTimeout,
                           String sessionId) {
    this.cacheId_ = cache.getId();
    this.sessionData_ = new HashMap<String, Object>();
    this.creationTime_ = System.currentTimeMillis();
    this.lastAccessedTime_ = creationTime_;
    this.accessCount_ = 0;
    this.sessionTimeout_ = sessionTimeout;
    this.sessionId_ = sessionId;
    this.isNew_ = true;
    this.isInvalidated_ = false;
    this.clientAddr_ = null;
  }

  /**
   * Checks if the session has been invalidated. If no, a warning is logged.
   */
  private void checkValid() {
    if (isInvalidated_) {
      LOGGER.warning("illegal access to invalidated session");
    }
  }

  /**
   * Checks if the session has been invalidated. If no, a warning is logged.
   */
  private void maybeThrowIllegalStateException() {
    if (isInvalidated_) {
      throw new IllegalStateException("illegal access to invalidated session");
    }
  }

  /**
   * Records that the session has been accessed. This updates
   * the last accessed time and the <code>isNew_</code> flag.
   */
  protected void access() {
    lastAccessedTime_ = System.currentTimeMillis();
    accessCount_ += 1;
    isNew_ = false;
  }

  /**
   * Sends the current state of this object to the cache.
   * Does nothing for local caches, but must be called after
   * any change to the session state for remote caches.
   */
  private void updateCache() {
    ServletSessionCache.getCache(cacheId_).updateSession(this);
  }


  /**
   * HttpSession API
   * ---------------
   */

  /**
   * Returns the time when this session was created, measured
   * in milliseconds since midnight January 1, 1970 GMT.
   */
  public long getCreationTime() {
    // throw an IllegalStateException if this session has been invalidated
    maybeThrowIllegalStateException();
    return creationTime_;
  }

  /**
   * Returns a string containing the unique identifier assigned
   * to this session.
   */
  public String getId() {
    return sessionId_;
  }

  /**
   * Returns the last time the client sent a request associated
   * with this session, as the number of milliseconds since
   * midnight January 1, 1970 GMT.
   */
  public long getLastAccessedTime() {
    return lastAccessedTime_;
  }

  /**
   * Returns the maximum time interval, in seconds, that the
   * servlet container will keep this session open between client
   * accesses.
   */
  public int getMaxInactiveInterval() {
    return (int) (sessionTimeout_ / 1000);
  }

  /**
   * Specifies the time, in seconds, between client requests
   * before the servlet container will invalidate this session.
   */
  public void setMaxInactiveInterval(int interval) {
    sessionTimeout_ = interval * 1000L;
    updateCache();
  }

  /**
   * Invalidates this session and unbinds any objects bound
   * to it.
   */
  public void invalidate() {
    maybeThrowIllegalStateException();  // no re-entry, accroding to 2.3 spec
    isInvalidated_ = true;
    ServletSessionCache.getCache(cacheId_).invalidateSession(sessionId_);
  }

  /**
   * Returns true if the client does not yet know about the
   * session or if the client chooses not to join the session.
   */
  public boolean isNew() {
    maybeThrowIllegalStateException();      // be strict for 2.3 compliance
    // checkValid();
    return isNew_;
  }

  /**
   * Returns the object bound with the specified name in this
   * session, or null if no object is bound under the name.
   */
  public Object getAttribute(String name) {
    maybeThrowIllegalStateException();
    // if they're asking for the magic "client address" key, don't go
    // to the hash table
    if (GSEConstants.SESSIONKEY_CLIENTADDRESS.equals(name)) {
      return clientAddr_;
    }
    checkValid();
    return sessionData_.get(name);
  }

  /**
   * Returns an Enumeration of String objects containing the
   * names of all the objects bound to this session.
   */
  public Enumeration<String> getAttributeNames() {
    maybeThrowIllegalStateException();
    // do NOT replace the following line with Iterators.asEnumeration
    return new IteratorEnumeration<String>(sessionData_.keySet().iterator());
  }

  /**
   * Binds an object to this session, using the name specified.
   */
  public void setAttribute(String name, Object value) {
    maybeThrowIllegalStateException();
    // if they're setting the magic "client address" key, don't go
    // to the hash table
    if (GSEConstants.SESSIONKEY_CLIENTADDRESS.equals(name)) {
      clientAddr_ = (value == null) ? null : value.toString();
      return;
    }
    checkValid();
    Object oldValue = getAttribute(name);
    sessionData_.put(name, value);
    updateCache();
    // notify binding listeners
    if (value instanceof HttpSessionBindingListener) {
      ((HttpSessionBindingListener) value).valueBound(
        new HttpSessionBindingEvent(this, name, value));
    }
    // notify attribute listeners
    if (oldValue == null) {
      ServletSessionCache.getCache(cacheId_).
        notifySessionAttributeAdded(this, name, value);
    } else {
      ServletSessionCache.getCache(cacheId_).
        notifySessionAttributeReplaced(this, name, oldValue);
    }
  }

  /**
   * Removes the object bound with the specified name from
   * this session.
   */
  public void removeAttribute(String name) {
    maybeThrowIllegalStateException();
    checkValid();
    Object value = getAttribute(name);
    // notify binding listeners
    if (value != null) {
      sessionData_.remove(name);
      updateCache();
      if (value instanceof HttpSessionBindingListener) {
        ((HttpSessionBindingListener) value).valueUnbound(
          new HttpSessionBindingEvent(this, name));
      }
    }
    // notify attribute listeners
    ServletSessionCache.getCache(cacheId_).
      notifySessionAttributeRemoved(this, name);
  }

  /**
   * Clears the attribute map, potentially calling the valueUnbound
   * methods of any attributes which implement the
   * {@link javax.servlet.http.HttpSessionBindingListener} interface.
   */
  protected void clearAttributes() {
    checkValid();
    // call value unbound where appropriate...
    for (Map.Entry<String, Object> entry : sessionData_.entrySet()) {
      Object value = entry.getValue();
      if (value instanceof HttpSessionBindingListener) {
        String name = entry.getKey();
        HttpSessionBindingListener hsbl = (HttpSessionBindingListener) value;
        hsbl.valueUnbound(new HttpSessionBindingEvent(this, name));
      }
    }
    sessionData_.clear();
  }

  /**
   * Returns the servlet context. For GSE, this returns null.
   * This method was added in version 2.3 of the servlet API
   */
  public ServletContext getServletContext() {
    return null;
  }

  /**
   * Unsupported.
   * @deprecated Operation deprecated as of version 2.2
   */
  @Deprecated
  public Object getValue(String name) {
    return getAttribute(name);
  }

  /**
   * Unsupported.
   * @deprecated Operation deprecated as of version 2.2
   */
  @Deprecated
  public String[] getValueNames() {
    checkValid();
    String[] empty = {};
    return sessionData_.values().toArray(empty);
  }

  /**
   * Unsupported.
   * @deprecated Operation deprecated as of version 2.2
   */
  @Deprecated
  public void putValue(String name, Object value) {
    setAttribute(name, value);
    updateCache();
  }

  /**
   * Unsupported.
   * @deprecated Operation deprecated as of version 2.2
   */
  @Deprecated
  public void removeValue(String name) {
    removeAttribute(name);
    updateCache();
  }

  /**
   * Unsupported.
   * @deprecated Operation deprecated as of version 2.1
   */
  @Deprecated
  public javax.servlet.http.HttpSessionContext getSessionContext() {
    throw new UnsupportedOperationException();
  }

  private static final long serialVersionUID = 1L;
}