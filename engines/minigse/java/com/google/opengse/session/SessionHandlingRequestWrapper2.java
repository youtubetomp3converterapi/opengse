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

package com.google.opengse.session;

import javax.servlet.http.*;
import javax.servlet.ServletResponse;
import java.util.logging.Logger;

/**
 * @author jennings
 *         Date: Jul 6, 2008
 */
public final class SessionHandlingRequestWrapper2 extends HttpServletRequestWrapper {
  private static final Logger LOG =
      Logger.getLogger(SessionHandlingRequestWrapper2.class.getName());
  protected static final String SESSION_COOKIE_NAME = "gsessionid";
  protected static final String SESSION_PARAM_NAME = "gsessionid";

  private HttpSession session;
  private boolean is_session_id_from_cookie;
  private boolean is_session_id_from_url;
  private String requested_session_id;
  private HttpServletResponse response;
  private HttpSessions httpSessions;
  private boolean sessionAlreadyExtractedFromRequest;


  /**
   * Constructs a request object wrapping the given request.
   *
   * @throws IllegalArgumentException if the request is null
   */
  public SessionHandlingRequestWrapper2(HttpServletRequest request,
                                        HttpSessions httpSessions,
                                        ServletResponse resp) {
    super(request);
    this.httpSessions = httpSessions;
    if (resp instanceof HttpServletResponse) {
      response = (HttpServletResponse) resp;
    } else {
      response = null;
    }
    sessionAlreadyExtractedFromRequest = false;
  }


  private void maybeGetSessionFromCookieOrRequestParameter() {
    if (sessionAlreadyExtractedFromRequest) {
      return;
    }
    Cookie[] cookies = getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(SESSION_COOKIE_NAME)) {
          requested_session_id = cookie.getValue();
          is_session_id_from_cookie = true;
          break;
        }
      }
    }
    if (requested_session_id == null) {
      requested_session_id = getParameter(SESSION_PARAM_NAME);
      if (requested_session_id != null) {
        is_session_id_from_url = true;
      }
    }
    session = httpSessions.lookupSession(requested_session_id);
    sessionAlreadyExtractedFromRequest = true;
  }

  @Override
  public HttpSession getSession(boolean create) {
    maybeGetSessionFromCookieOrRequestParameter();
    if (session != null && isInvalidated(session)) {
      session = null;
    }
    if (create && session == null) {
      
      session = httpSessions.createSession(this);
      if (session != null && response != null) {
        response.addCookie(createSessionCookie(session.getId()));
      }
    }
    return session;
  }


  private static Cookie createSessionCookie(String id) {
    if (id == null) {
      throw new NullPointerException("null id for session cookie");
    }
    Cookie cookie = new Cookie(SESSION_COOKIE_NAME, id);
    cookie.setPath("/");
    // Set the session cookie to expire on browser exit
    cookie.setMaxAge(-1);
    cookie.setVersion(1); // use RFC 2109 cookies!
    return cookie;
  }


  @Override
  public boolean isRequestedSessionIdFromCookie() {
    maybeGetSessionFromCookieOrRequestParameter();
    return is_session_id_from_cookie;
  }

  @Override
  public boolean isRequestedSessionIdFromURL() {
    maybeGetSessionFromCookieOrRequestParameter();
    return is_session_id_from_url;
  }

  @Override
  public boolean isRequestedSessionIdValid() {
    maybeGetSessionFromCookieOrRequestParameter();
    return (requested_session_id != null && session != null);
  }

  @Override
  public String getRequestedSessionId() {
    maybeGetSessionFromCookieOrRequestParameter();
    return requested_session_id;
  }

  @Override
  public HttpSession getSession() {
    return getSession(true);
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

}