package com.google.opengse.session;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

/**
 * A collection of HttpSession objects
 * 
 * Author: Mike Jennings
 * Date: Feb 3, 2009
 * Time: 9:09:47 AM
 */
public interface HttpSessions {
    HttpSession createSession(HttpServletRequest request);
    HttpSession lookupSession(String requestedSessionId);
    HttpSession removeAndInvalidateSession(String sessionId);
}
