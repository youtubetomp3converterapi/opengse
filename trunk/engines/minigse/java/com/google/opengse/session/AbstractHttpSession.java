package com.google.opengse.session;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import javax.servlet.ServletContext;

/**
 * Basic base class for implementing HttpSession. Does the right thing with deprecated methods.
 *
 * Author: Mike Jennings
 * Date: Feb 3, 2009
 * Time: 9:18:05 AM
 */
public abstract class AbstractHttpSession implements HttpSession {

  /**
   * Returns the servlet context. For GSE, this returns null.
   * This method was added in version 2.3 of the servlet API
   *
   * @return
   */
  public ServletContext getServletContext() {
    return null;
  }



  /**
   *
   * @deprecated 	As of Version 2.1, this method is
   *			deprecated and has no replacement.
   *			It will be removed in a future
   *			version of the Java Servlet API.
   *
   */
  public HttpSessionContext getSessionContext() {
    return null;
  }


  /**
   *
   * @deprecated 	As of Version 2.2, this method is
   * 			replaced by {@link #getAttribute}.
   *
   * @param name		a string specifying the name of the object
   *
   * @return			the object with the specified name
   *
   * @exception IllegalStateException	if this method is called on an
   *					invalidated session
   *
   */
  public Object getValue(String name) {
    return getAttribute(name);
  }


  /**
   *
   * @deprecated 	As of Version 2.2, this method is
   * 			replaced by {@link #setAttribute}
   *
   * @param name			the name to which the object is bound;
   *					cannot be null
   *
   * @param value			the object to be bound; cannot be null
   *
   * @exception IllegalStateException	if this method is called on an
   *					invalidated session
   *
   */
  public void putValue(String name, Object value) {
    setAttribute(name, value);
  }


  /**
   *
   * @deprecated 	As of Version 2.2, this method is
   * 			replaced by {@link #removeAttribute}
   *
   * @param name				the name of the object to
   *						remove from this session
   *
   * @exception IllegalStateException	if this method is called on an
   *					invalidated session
   */
  public void removeValue(String name) {
    removeAttribute(name);
  }


}