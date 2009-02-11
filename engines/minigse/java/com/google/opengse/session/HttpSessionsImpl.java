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
  private Map<TimeKey, HttpSessionImpl> time_to_session;
  private static final int SECONDS_PER_MINUTE = 60;
  // give sessions a default timeout of 30 minutes
  private static final int SESSION_TIMEOUT_SECONDS = SECONDS_PER_MINUTE * 30;

  public HttpSessionsImpl() {
    id_to_session = new HashMap<String, HttpSessionImpl>();
    time_to_session = new HashMap<TimeKey, HttpSessionImpl>();
    // now to spin off a background thread to invalidate old sessions
    new SessionReaper();
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
    HttpSessionImpl session = new HttpSessionImpl(this, id, SESSION_TIMEOUT_SECONDS);
    id_to_session.put(id, session);
    synchronized(time_to_session) {
      time_to_session.put(session.getLastAccessedTimeKey(), session);
      time_to_session.notify(); // just in case another thread is waiting on it
    }
    return session;
  }

  public synchronized HttpSession lookupSession(String requestedSessionId) {
    HttpSessionImpl session = id_to_session.get(requestedSessionId);
    if (session != null) {
      session.sessionIsOld();
      synchronized(time_to_session) {
        TimeKey lastAccessed = session.getLastAccessedTimeKey();
        time_to_session.remove(lastAccessed);
        lastAccessed.update();
        time_to_session.put(lastAccessed, session);
      }
    }
    return session;
  }

  public synchronized HttpSession removeAndInvalidateSession(String sessionId) {
    HttpSessionImpl session = id_to_session.remove(sessionId);
    if (session != null) {
      synchronized(time_to_session) {
        time_to_session.remove(session.getLastAccessedTimeKey());
      }
      if (!session.isInvalidated) {
        session.doInvalidation();
      }
    }
    return session;
  }

  HttpSessionImpl getOldestSession() throws InterruptedException {
    synchronized(time_to_session) {
      if (time_to_session.isEmpty()) {
        time_to_session.wait();
      }
      TimeKey oldestKey = time_to_session.keySet().iterator().next();
      return time_to_session.get(oldestKey);
    }
  }

  private void invalidateOldSessions() {
    HttpSessionImpl session;
    while(true) {
      try {
        Thread.sleep(500); // check for old sessions every 1/2 second
        session = getOldestSession();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        break;
      }
      long maxInactiveIntervalSeconds = session.getMaxInactiveInterval();
      if (maxInactiveIntervalSeconds < 0) {
        // force lookup for "forever" sessions
        lookupSession(session.getId());
        continue;
      }
      long elapsedMillis = System.currentTimeMillis() - session.getLastAccessedTimeKey().time;
      if (elapsedMillis > maxInactiveIntervalSeconds * 1000) {
        session.invalidate();
      }
    }
  }

  private class SessionReaper extends Thread {
    private SessionReaper() {
      super("SessionReaper");
      this.setDaemon(true);
      start();
    }

    @Override
    public void run() {
      invalidateOldSessions();
    }
  }

}
