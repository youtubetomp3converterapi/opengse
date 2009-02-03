package com.google.opengse.session;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import java.security.SecureRandom;
import java.util.Map;
import java.util.HashMap;

/**
 * Author: Mike Jennings
 * Date: Feb 3, 2009
 * Time: 9:15:36 AM
 */
public class HttpSessionsImpl implements HttpSessions {
  private SecureRandom random = new SecureRandom();
  private Map<String, HttpSessionImpl> id_to_session;

  public HttpSessionsImpl() {
    id_to_session = new HashMap<String, HttpSessionImpl>();
  }

  private synchronized String nextId() {
    String id = "S" + random.nextInt();
    while (id_to_session.containsKey(id)) {
      id = "S" + random.nextInt();
    }
    return id;
  }

  public synchronized HttpSession createSession(HttpServletRequest request) {
    String id = nextId();
    HttpSessionImpl session = new HttpSessionImpl(this, id, System.currentTimeMillis());
    id_to_session.put(id, session);
    return session;
  }

  public synchronized HttpSession lookupSession(String requestedSessionId) {
    HttpSessionImpl session = id_to_session.get(requestedSessionId);
    if (session != null) {
      session.sessionIsOld();
      session.setLastAccessedTime(System.currentTimeMillis());
    }
    return session;
  }

  public synchronized HttpSession removeAndInvalidateSession(String sessionId) {
    HttpSessionImpl session = id_to_session.remove(sessionId);
    if (session != null && !session.isInvalidated) {
      session.invalidate();
    }
    return session;
  }

}
