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

import com.google.opengse.session.impl.SessionCookie;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Cookie;

/**
 * @author Mike Jennings
 *         Date: Jun 6, 2008
 */
public interface SessionCache {
  /**
   * requested session post mortem enum
   */
  public enum RequestedSessionStatus {
    NO_SESSION("NoSession"),
    VALID("Valid"),
    INVALID("Invalid"),
    EXPIRED("Expired"),
    BAD_SESSION_ID("BadSessionId"),
    BAD_SECURE_ID("BadSecureId"),
    WRONG_BACKEND("WrongBackend");

    /**
     * @return the string ID of this object (as passed in to the constructor).
     */
    @Override
    public String toString() { return id_; }

    private RequestedSessionStatus(String s) { id_ = s; }

    private final String id_;  // the string value of this object

    private static final long serialVersionUID = 1L;
  }

  /**
   *
   * Given {@code cookies}, finds the
   * {@link SessionCookie} that
   * corresponds to the cookie with the correct name (correct in the sense
   * that it is the designated session cookie for the specified
   * HttpServer. The {@link SessionCookie},
   * cookie session id, and a boolean indicating whether or not it was found
   * are provided as methods on the session cookie.
   *
   * @param cookies
   * @param serverType
   */  
  public SessionCookie readSessionFromCookies(
      Cookie[] cookies, String serverType);


  /**
   * Creates a new session and stores it in the cache
   *
   * @param request
   * @return
   */
  public HttpSession createSession(HttpServletRequest request);

   /**
   * Invalidates an existing session by removing from the cache.
   *
   * @param sessionId
   *          the session to invalidate
   */
  public void invalidateSession(String sessionId);

  /**
   * Performs a post-mortem on an invalid session ID. An invalid session ID
   * means that the session ID is otherwise decryptable & decodable, and has a
   * matching server ID (IP address). However, there is no corresponding session
   * in the cache. This means one of three things depending on the following
   * conditions:
   *
   * <li>the session was created before the last server restart: true if gse
   * has never seen the session ID before.
   * <li>the session ID is recognized, but
   *
   * @return the status of the requested session
   */
  public SessionCache.RequestedSessionStatus invalidSessionPostMortem(
      HttpServletRequest req, String sessionId);

  /**
   * Generates a session id
   * @return
   */
  public String generateId();

 /**
   * Returns a session by id if one exists.
   * @return {@link javax.servlet.http.HttpSession} object by id.
   */
  public HttpSession getSession(String id);


  /**
   * Sets the extended server ID. This should normally be set by the constructor
   * to the server's port.
   */
  public void setServerIdExt(int serverIdExt) throws IllegalStateException;


}
