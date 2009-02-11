package com.google.opengse.session;

import com.google.opengse.util.IteratorEnumeration;

import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.util.Enumeration;
import java.util.Map;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

/**
 * Author: Mike Jennings
 * Date: Feb 3, 2009
 * Time: 9:18:05 AM
 */
final class HttpSessionImpl extends AbstractHttpSession {
  private HttpSessionsImpl parent;
  private String id;
  private long creationTime;
  private TimeKey lastAccessedTime;
  boolean isInvalidated;
  private int maxInactiveInterval;
  private boolean sessionIsNew;
  private Map<String, Object> sessionObjects;

  HttpSessionImpl(HttpSessionsImpl parent, String id, int maxInactiveInterval) {
    this.parent = parent;
    this.id = id;
    this.maxInactiveInterval = maxInactiveInterval;
    creationTime = System.currentTimeMillis();
    this.lastAccessedTime = new TimeKey(creationTime, id);
    this.sessionIsNew = true;
    sessionObjects = Collections.synchronizedMap(new HashMap<String, Object>());
  }

  /**
   *
   * Invalidates this session then unbinds any objects bound
   * to it.
   *
   * @exception IllegalStateException	if this method is called on an
   *					already invalidated session
   *
   */
  public void invalidate() {
    maybeThrowIllegalStateException();
    parent.removeAndInvalidateSession(id);
  }

  void doInvalidation() {
    if (isInvalidated) {
      return;
    }
    isInvalidated = true;
    // TODO: check session objects in case they need to be notified of invalidation
  }
  
  /**
   * Checks if the session has been invalidated.
   */
  private void maybeThrowIllegalStateException() {
    if (isInvalidated) {
      throw new IllegalStateException("illegal access to invalidated session");
    }
  }



  /**
   *
   * Returns the time when this session was created, measured
   * in milliseconds since midnight January 1, 1970 GMT.
   *
   * @return				a <code>long</code> specifying
   * 					when this session was created,
   *					expressed in
   *					milliseconds since 1/1/1970 GMT
   *
   * @exception IllegalStateException	if this method is called on an
   *					invalidated session
   *
   */
  public long getCreationTime() {
    maybeThrowIllegalStateException();
    return creationTime;
  }


  /**
   *
   * Returns a string containing the unique identifier assigned
   * to this session. The identifier is assigned
   * by the servlet container and is implementation dependent.
   *
   * @return				a string specifying the identifier
   *					assigned to this session
   *
   * @exception IllegalStateException	if this method is called on an
   *					invalidated session
   *
   */
  public String getId() {
    maybeThrowIllegalStateException();
    return id;
  }

  /**
   *
   * Returns the last time the client sent a request associated with
   * this session, as the number of milliseconds since midnight
   * January 1, 1970 GMT, and marked by the time the container received the request.
   *
   * <p>Actions that your application takes, such as getting or setting
   * a value associated with the session, do not affect the access
   * time.
   *
   * @return				a <code>long</code>
   *					representing the last time
   *					the client sent a request associated
   *					with this session, expressed in
   *					milliseconds since 1/1/1970 GMT
   *
   * @exception IllegalStateException	if this method is called on an
   *					invalidated session
   *
   */
  public long getLastAccessedTime() {
    maybeThrowIllegalStateException();
    return lastAccessedTime.time;
  }

  TimeKey getLastAccessedTimeKey() {
    return lastAccessedTime;
  }

   /**
    * Returns the maximum time interval, in seconds, that
    * the servlet container will keep this session open between
    * client accesses. After this interval, the servlet container
    * will invalidate the session.  The maximum time interval can be set
    * with the <code>setMaxInactiveInterval</code> method.
    * A negative time indicates the session should never timeout.
    *
    *
    * @return		an integer specifying the number of
    *			seconds this session remains open
    *			between client requests
    *
    * @see		#setMaxInactiveInterval
    *
    *
    */
   public int getMaxInactiveInterval() {
    return maxInactiveInterval;
  }

  /**
   *
   * Specifies the time, in seconds, between client requests before the
   * servlet container will invalidate this session.  A negative time
   * indicates the session should never timeout.
   *
   * @param interval		An integer specifying the number
   * 				of seconds
   *
   */
  public void setMaxInactiveInterval(int interval) {
    this.maxInactiveInterval = interval;
  }
  
  /**
   *
   * Returns the object bound with the specified name in this session, or
   * <code>null</code> if no object is bound under the name.
   *
   * @param name		a string specifying the name of the object
   *
   * @return			the object with the specified name
   *
   * @exception IllegalStateException	if this method is called on an
   *					invalidated session
   *
   */
  public Object getAttribute(String name) {
    maybeThrowIllegalStateException();
    return sessionObjects.get(name);
  }

  /**
   *
   * Returns an <code>Enumeration</code> of <code>String</code> objects
   * containing the names of all the objects bound to this session.
   *
   * @return			an <code>Enumeration</code> of
   *				<code>String</code> objects specifying the
   *				names of all the objects bound to
   *				this session
   *
   * @exception IllegalStateException	if this method is called on an
   *					invalidated session
   *
   */
  public Enumeration<String> getAttributeNames() {
    maybeThrowIllegalStateException();
    return new IteratorEnumeration<String>(sessionObjects.keySet().iterator());
  }

  /**
   *
   * @deprecated 	As of Version 2.2, this method is
   * 			replaced by {@link #getAttributeNames}
   *
   * @return				an array of <code>String</code>
   *					objects specifying the
   *					names of all the objects bound to
   *					this session
   *
   * @exception IllegalStateException	if this method is called on an
   *					invalidated session
   *
   */
  public String[] getValueNames() {
    maybeThrowIllegalStateException();
    Set<String> keySet = sessionObjects.keySet();
    return keySet.toArray(new String[keySet.size()]);
  }

  /**
   * Binds an object to this session, using the name specified.
   * If an object of the same name is already bound to the session,
   * the object is replaced.
   *
   * <p>After this method executes, and if the new object
   * implements <code>HttpSessionBindingListener</code>,
   * the container calls
   * <code>HttpSessionBindingListener.valueBound</code>. The container then
   * notifies any <code>HttpSessionAttributeListener</code>s in the web
   * application.

   * <p>If an object was already bound to this session of this name
   * that implements <code>HttpSessionBindingListener</code>, its
   * <code>HttpSessionBindingListener.valueUnbound</code> method is called.
   *
   * <p>If the value passed in is null, this has the same effect as calling
   * <code>removeAttribute()<code>.
   *
   *
   * @param name			the name to which the object is bound;
   *					cannot be null
   *
   * @param value			the object to be bound
   *
   * @exception IllegalStateException	if this method is called on an
   *					invalidated session
   *
   */
  public void setAttribute(String name, Object value) {
    maybeThrowIllegalStateException();
    sessionObjects.put(name, value);
    // notify binding listeners
    if (value instanceof HttpSessionBindingListener) {
      HttpSessionBindingEvent event = new HttpSessionBindingEvent(this, name, value);
      ((HttpSessionBindingListener) value).valueBound(event);
    }
  }

  /**
   *
   * Removes the object bound with the specified name from
   * this session. If the session does not have an object
   * bound with the specified name, this method does nothing.
   *
   * <p>After this method executes, and if the object
   * implements <code>HttpSessionBindingListener</code>,
   * the container calls
   * <code>HttpSessionBindingListener.valueUnbound</code>. The container
   * then notifies any <code>HttpSessionAttributeListener</code>s in the web
   * application.
   *
   *
   *
   * @param name				the name of the object to
   *						remove from this session
   *
   * @exception IllegalStateException	if this method is called on an
   *					invalidated session
   */
  public void removeAttribute(String name) {
    maybeThrowIllegalStateException();
    Object value = sessionObjects.remove(name);
    if (value instanceof HttpSessionBindingListener) {
      HttpSessionBindingEvent event = new HttpSessionBindingEvent(this, name, value);
      ((HttpSessionBindingListener) value).valueUnbound(event);
    }

  }


  /**
   *
   * Returns <code>true</code> if the client does not yet know about the
   * session or if the client chooses not to join the session.  For
   * example, if the server used only cookie-based sessions, and
   * the client had disabled the use of cookies, then a session would
   * be new on each request.
   *
   * @return 				<code>true</code> if the
   *					server has created a session,
   *					but the client has not yet joined
   *
   * @exception IllegalStateException	if this method is called on an
   *					already invalidated session
   *
   */
  public boolean isNew() {
    maybeThrowIllegalStateException();
    return sessionIsNew;
  }

  void sessionIsOld() {
    sessionIsNew = false;
  }

}
