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

import com.google.opengse.GSEConstants;
import com.google.opengse.session.SessionCache;
import com.google.opengse.session.impl.SessionCookie;
import com.google.opengse.httputil.Cookies;

import javax.servlet.http.*;
import javax.servlet.ServletResponse;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * @author jennings
 *         Date: Jul 6, 2008
 */
class SessionHandlingRequestWrapper extends HttpServletRequestWrapper {
  private static final Logger LOGGER =
      Logger.getLogger(SessionHandlingRequestWrapper.class.getName());
  protected static final String SESSION_COOKIE_NAME = "S";
  protected static final String SESSION_PARAM_NAME = "gsessionid";

  private final SessionCache sessionCache;
  private HttpSession session_;
  private boolean session_initialized_;
  private boolean is_session_id_from_cookie_;
  private boolean is_session_id_from_url_;
  private String requested_session_id_;
  private SessionCookie session_cookie_;
  private ArrayList<Cookie> cookies_;
  private final ServletResponse resp;


  /**
   * Constructs a request object wrapping the given request.
   *
   * @throws IllegalArgumentException if the request is null
   */
  public SessionHandlingRequestWrapper(HttpServletRequest request,
                                       SessionCache sessionCache,
                                       ServletResponse resp) {
    super(request);
    this.sessionCache = sessionCache;
    this.resp = resp;
  }



  @Override
  public HttpSession getSession(boolean create) {
    initSession();
    // if session is invalidated, set to null
    if (session_ != null && isInvalidated(session_)) {
      session_ = null;
    }
    // create session if it doesn't exist (if create is true)
    if (session_ == null && create) {
      session_ = sessionCache.createSession(this);
      Cookie cookie = getSessionCookie();
      if (cookie != null) {
        if (resp instanceof HttpServletResponse) {
          ((HttpServletResponse) resp).addCookie(cookie);
        }
      }

      // if we're creating a new session, clear the bools specifying whether
      // the requested session id came from a cookie or url param. They no
      // longer apply.
      is_session_id_from_cookie_ = false;
      is_session_id_from_url_ = false;
    }

    return session_;
  }


  private Cookie getSessionCookie() {
    if (session_cookie_ == null) {
      session_cookie_ = new SessionCookie();
    }
    session_cookie_.setId("default", session_.getId());
    Cookie cookie = new Cookie(SESSION_COOKIE_NAME,
        session_cookie_.toString());
    cookie.setPath("/");
    // Set the session cookie to expire on browser exit
    cookie.setMaxAge(-1);
    cookie.setVersion(1); // use RFC 2109 cookies!
    return cookie;
  }


  @Override
  public boolean isRequestedSessionIdFromCookie() {
    initSession();
    return is_session_id_from_cookie_;
  }

  @Override
  public boolean isRequestedSessionIdFromURL() {
    initSession();
    return is_session_id_from_url_;
  }

  @Override
  public boolean isRequestedSessionIdValid() {
    return (sessionCache.getSession(getRequestedSessionId()) != null);
  }

  @Override
  public String getRequestedSessionId() {
    initSession();
    return requested_session_id_;
  }

  @Override
  public HttpSession getSession() {
    return getSession(true);
  }

  /**
   * Initializes the request's session. The requested session ID is first
   * determined from the HTTP cookie values or URL query string. This method is
   * invoked by any request methods dealing with the session. Otherwise, it is
   * called when the HTTP response is finalized.
   */
  private void initSession() {
    if (session_initialized_) {
      // always refetch the session, as it can become invalid
      if (session_ != null) {
        session_ = sessionCache.getSession(session_.getId());
      }
      return;
    }
    session_initialized_ = true;

    initSessionId();
    incrementStatistics();
    updateClientAddress();
  }


  /**
   * If session is not null, update client address, warning of possible session
   * hijacking if client address has changed between requests
   */
  private void updateClientAddress() {
    if (session_ == null) {
      return;
    }

    String oldAddress = getClientAddress(session_);
    String newAddress = getRemoteAddr();

    /*
     * if the client ip address has changed between requests, it is
     * possible that session hijacking is underway.
     */
    if (oldAddress != null && !oldAddress.equals(newAddress)) {
      LOGGER.warning("client IP address for session " + session_.getId() +
          " changed mid-session from " +
          oldAddress + " to " + newAddress + ". This could " +
          "be an indication of session hijacking activity, " +
          "but is mostly likely a shift in ISP proxy use.");
    }

    // finally, set the most recent client address
    setClientAddress(session_, newAddress);
  }


  private void incrementStatistics() {
    if (requested_session_id_ != null) {
      // fetch the session by requested id
      session_ = sessionCache.getSession(requested_session_id_);
      // increment statistics as appropriate
      if (session_ == null) {
        // check if the requested server id matches the server address
         sessionCache.invalidSessionPostMortem(this, requested_session_id_);
      }
    }
  }

  @Override
  public Cookie[] getCookies() {
    if (cookies_ == null) {
      @SuppressWarnings("unchecked")
      Enumeration<String> enumeration = getHeaders("Cookie");
      cookies_ = Cookies.parse(enumeration);
      if (cookies_ == null) {
        return null;
      }
    }
    return cookies_.toArray(new Cookie[0]);
  }


  /**
   * If URI is set, initialize session ID
   */
  private void initSessionId() {
    // can't get session id if URI has not been set
    if (getRequestURI() == null) {
      return;
    }

    // check cookies
    Cookie[] cookies = getCookies();

    session_cookie_ = sessionCache.readSessionFromCookies(
        cookies, "default");

    requested_session_id_ = (session_cookie_ == null) ? null : session_cookie_.getRequestedSessionId();
    is_session_id_from_cookie_ = (session_cookie_ == null) ? false : session_cookie_.isSessionIdFromCookie();

    // check query string (url params)
    if (requested_session_id_ == null) {
      requested_session_id_ =
          getParameter(SESSION_PARAM_NAME);
      if (requested_session_id_ != null) {
        is_session_id_from_url_ = true;
      }
    }
  }


  private static boolean isInvalidated(HttpSession session) {
    try {
      // getCreationTime() will throw an exception if the session is invalid
      session.getCreationTime();
      return false;
    } catch (IllegalStateException invalidSession) {
      return true;
    }
  }


  private static String getClientAddress(HttpSession session) {
    return (String) session.getAttribute(GSEConstants.SESSIONKEY_CLIENTADDRESS);
  }

  private static void setClientAddress(
      HttpSession session, String clientAddress) {
    session.setAttribute(GSEConstants.SESSIONKEY_CLIENTADDRESS, clientAddress);
  }

}
