// Copyright 2002-2005 Google Inc.
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

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.*;

import com.google.opengse.session.SessionCache;
import com.google.opengse.session.SessionConfiguration;
import com.google.opengse.*;

/**
 * ServletSessionCache: caches ServletSession objects, the GSE implementation of
 * the {@link javax.servlet.http.HttpSession} interface. Each
 * {@link com.google.opengse.HttpRequest} object contains its own independent
 * session cache. Session data is stored in a synchronized (thread-safe)
 * in-memory cache.
 *
 * @see javax.servlet.http.HttpSession
 * @author Spencer Kimball
 * @author Ben Darnell
 */
public class ServletSessionCache implements
    CacheListener<String, HttpSessionImpl>, SessionCache {
  protected static final String  SESSION_COOKIE_NAME = "S";  
  private static final Logger LOGGER = Logger
      .getLogger(ServletSessionCache.class.getName());

  /**
   * The default timeout value for session data. (in ms)
   */
  protected static final long DEFAULT_SESSION_TIMEOUT = 30 * 60 * 1000;

  /**
   * The default maximum session count in the cache (if unconfigured)
   */
  protected static final int DEFAULT_MAX_SESSIONS = 5000;

  /**
   * Purge entries in the timer every 5 minutes to release references to
   * cancelled entries.
   */
  private static final long TIMER_PURGE_INTERVAL = 5 * 60 * 1000;


  /**
   * A map of all ServletSessionCaches instantiated in this JVM.
   */
  // do NOT change the following line to Maps.newHashMap()
  private static Map<Integer, ServletSessionCache> caches_ = new HashMap<Integer, ServletSessionCache>();

  /**
   * A counter used to generate IDs for caches
   */
  private static AtomicInteger nextCacheId = new AtomicInteger(0);

  /**
   * The object used to create encrypted session keys
   */
  protected SessionIdGenerator sessionId_ = new SessionIdGenerator();

  /**
   * Returns the cache with the given ID.
   */
  protected static synchronized ServletSessionCache getCache(Integer id) {
    return caches_.get(id);
  }

  /**
   * Adds the given cache to the map and assigns it an ID
   */
  protected static synchronized Integer registerCache(
      ServletSessionCache cache) {
    Integer id = nextCacheId.incrementAndGet();
    caches_.put(id, cache);
    return id;
  }

  /**
   * The actual cache used to store sessions.
   */
  protected ThreadSafeCache<String, HttpSessionImpl> sessionCache_;

  /**
   * Class to hold timeouts and session IDs. This is used to reap expired
   * sessions using the {@code sessionTimer_} timer.
   */
  private class SessionTimeout extends TimerTask {
    public String id_;
    public long delay_;
    public int timeout_;

    public SessionTimeout(HttpSessionImpl ss) {
      this.id_ = ss.getId();
      this.timeout_ = ss.getMaxInactiveInterval();
      // negative delays are invalid
      this.delay_ = Math.max(
          (ss.getLastAccessedTime() + (1000 * timeout_) - System
              .currentTimeMillis()), 0);
    }

    @Override public void run() {
      try {
        LOGGER.info("session " + id_ + " expired after " + timeout_
            + " seconds");
        accounting_.update(id_, SESSION_EXPIRED);
        // remove timeout and invalidate cache
        sessionTimer_.remove(this);
        sessionCache_.invalidate(id_);
      } catch (Throwable t) {
        // catch Throwable to prevent the possibility of the Timer
        // being unintentionally canceled.
        LOGGER.log(Level.SEVERE, "SessionTimeout", t);
      }
    }
  }

  /**
   * Class to wrap a timer for expiring sessions and a map for fetching
   * SessionTimeout objects by session ID. This class does not extend Timer in
   * order to properly implement the {@code clear()} method, which cancels the
   * timer and starts a new one.
   * <p>
   *
   */
  protected class SessionTimer {
    Timer timer_;
    Map<String, SessionTimeout> timeoutMap_;
    long lastPurgeTime_ = System.currentTimeMillis();

    public SessionTimer() {
      timeoutMap_ = new HashMap<String, SessionTimeout>();
      resetTimer();
    }

    public synchronized void add(HttpSessionImpl ss) {
      SessionTimeout st = new SessionTimeout(ss);
      timeoutMap_.put(st.id_, st);
      try {
        timer_.schedule(st, st.delay_);
      } catch (IllegalStateException e) {
        resetTimer();
      }
    }

    public synchronized void remove(HttpSessionImpl ss) {
      SessionTimeout st = timeoutMap_.get(ss.getId());
      if (st != null) {
        remove(st);
      }
    }

    public synchronized void update(HttpSessionImpl ss) {
      remove(ss);
      add(ss);
    }

    public synchronized void clear() {
      timer_.cancel();
      timer_ = new Timer(ServletSessionCache.class.getName(), true);
      timeoutMap_.clear();
    }

    public synchronized void remove(SessionTimeout st) {
      st.cancel();
      timeoutMap_.remove(st.id_);

      // purge timer entries to free up memory
      long now = System.currentTimeMillis();
      if (now - lastPurgeTime_ > TIMER_PURGE_INTERVAL) {
        timer_.purge();
        lastPurgeTime_ = now;
      }
    }

    protected synchronized int size() {
      return timeoutMap_.size();
    }

    /**
     * Creates a new timer and reschedules all extant session timeouts. This
     * method is called at startup and in the event that the timer thread exits.
     * This will reset the timeouts for all sessions still in the cache,
     * regardless of when they were originally supposed to expire.
     */
    private synchronized void resetTimer() {
      if (timer_ != null) {
        timer_.cancel();
      }
      timer_ = new Timer(ServletSessionCache.class.getName(), true);
      for (SessionTimeout st : timeoutMap_.values()) {
        timer_.schedule(st, st.delay_);
      }
    }
  }

  /**
   * A timer for SessionTimeout objects, which implement TimerTask
   */
  protected SessionTimer sessionTimer_;

  /**
   * The session timeout setting.
   */
  protected long sessionTimeout_;

  /*
   * Cache with key='session ID' and data='status, last accessed time'. This is
   * a potentially temporary construct, used to keep track of sessions, no
   * matter whether they are currently in the session cache or have been
   * evicted/invalidated. It's used as a means to diagnose the cause of a
   * requested session being invalid. Was it evicted? Invalidated? Never seen?
   * It also keeps track of when the session was evicted or invalidated. There
   * is a maximum size for the number of sessions tracked. This is always set to
   * a multiple of the max number of sessions active at any point in time. This
   * allows flexibility with different server session requirements.
   */

  /** Track 5 times the peak # of sessions */
  private static final int SESSION_ACCOUNTING_MULTIPLE = 5;

  /** status fields for sessions */
  protected static final int SESSION_ACTIVE = 1;
  protected static final int SESSION_EXPIRED = 2;
  protected static final int SESSION_EVICTED = 3;
  protected static final int SESSION_INVALIDATED = 4;
  protected static final int SESSION_NOT_FOUND = 5;

  /** only used within SessionAccounting * */
  private class SessionStatus {
    public int status_;
    public long lastAccess_;

    public SessionStatus(int status) {
      this.status_ = status;
      this.lastAccess_ = System.currentTimeMillis();
    }
  }

  /** package access for unit tests */
  class SessionAccounting extends LinkedHashMap<String, SessionStatus> {
    private static final long serialVersionUID = 2L;
    private int maxActive_ = 1;
    private long lastKnownAccess_ = 0L;

    @Override protected boolean removeEldestEntry(
        Map.Entry<String, SessionStatus> eldest) {
      if (size() > (maxActive_ * SESSION_ACCOUNTING_MULTIPLE)) {
        SessionStatus value = eldest.getValue();
        if (value != null) {
          lastKnownAccess_ = value.lastAccess_;
        }
        return true;
      } else {
        return false;
      }
    }

    /** public interface */
    public synchronized void update(String id, int status) {
      // keep high-water mark for peak sessions
      maxActive_ = Math.max(maxActive_, ServletSessionCache.this
          .getSessionCount());
      SessionStatus ss = get(id);
      if (ss == null) {
        if (status != SESSION_ACTIVE) {
          LOGGER.warning("updating non-existent session with non-active "
              + "status: " + status);
        }
        ss = new SessionStatus(status);
        put(id, ss);
      } else if (ss.status_ == SESSION_ACTIVE) {
        // update last access if still active
        if (status == SESSION_ACTIVE) {
          ss.lastAccess_ = System.currentTimeMillis();
          remove(id); // remove
          put(id, ss); // re-insert to change insert ordering
        }
        ss.status_ = status;
      }
    }

    public synchronized int getStatus(String id) {
      SessionStatus ss = get(id);
      return ss == null ? SESSION_NOT_FOUND : ss.status_;
    }

    public synchronized long getLastAccess(String id) {
      SessionStatus ss = get(id);
      return ss == null ? 0 : ss.lastAccess_;
    }

    public synchronized long getLastKnownAccess() {
      return lastKnownAccess_;
    }
  }

  /**
   * Session accounting object for this cache
   */
  protected SessionAccounting accounting_ = new SessionAccounting();

  /**
   * An integer uniquely identifying the server that owns this session cache.
   */
  protected int serverId_;
  protected int serverIdExt_;

  /**
   * An integer uniquely identifying this cache within this JVM.
   */
  protected Integer cacheId_;

  /**
   * True if any sessions have been created in this cache
   */
  protected boolean nonEmpty_;

  /**
   * The list of session attribute listeners
   */
  protected List<HttpSessionAttributeListener> sessionAttributeListeners_;

  /**
   * The list of session listeners
   */
  protected List<HttpSessionListener> sessionListeners_;

  /**
   * The set of active session IDs
   */
  protected Set<String> activeSessionSet_;

  private SessionConfiguration config;

  /**
   * Returns an integer uniquely identifying this cache within this JVM.
   */
  Integer getId() {
    return cacheId_;
  }


  /**
   * Creates a cache and adds it to the map so it can be accessed via getCache()
   *
   * @param config
   */
  public ServletSessionCache(SessionConfiguration config) {
    this.config = config;
    this.serverId_ = config.getServerId();
    this.serverIdExt_ = config.getServerIdExt();
    this.cacheId_ = registerCache(this);
    this.nonEmpty_ = false;
    this.activeSessionSet_ = Collections.synchronizedSet(new HashSet<String>());

    // Create a sorted set for reaping expired sessions
    sessionTimer_ = new SessionTimer();

    // Create a thread-safe session cache; multiple request threads WILL
    // be accessing it concurrently.
    sessionCache_ = new ThreadSafeCache<String, HttpSessionImpl>(
        config.getMaxSessions());

    // setup notification of cache events (for session listeners)
    sessionCache_.registerListener(this);
    sessionTimeout_ = config.getSessionTimeoutMillis();
  }

  /**
   * Returns the number of sessions in the cache
   *
   * @return the session count
   */
  public int getSessionCount() {
    return activeSessionSet_.size();
  }

  /**
   * Sets the global session timeout value.
   *
   * @param timeout
   *          is the timeout in milliseconds for all new sessions
   */
  public void setSessionTimeout(long timeout) {
    sessionTimeout_ = timeout;
  }

  /**
   * Fetches a session from the cache.
   *
   * @param sessionId
   *          looks up a session based on the provided session ID
   * @return the session or {@code null} if not found
   */
  public synchronized HttpSession getSession(String sessionId) {
    if (sessionId == null) {
      return null;
    }

    // get the session from the cache, if available
    HttpSessionImpl ss = sessionCache_.get(sessionId);
    if (ss == null) {
      return null;
    }

    // Update the cacheId. The cacheId is used to refer back to a cache
    // so that a session can be invalidated or updated without a handle
    // to the owner cache.
    ss.cacheId_ = getId();

    // update access timestamp
    ss.access();
    accounting_.update(sessionId, SESSION_ACTIVE);
    updateSession(ss);

    return ss;
  }

  /**
   * Creates a new session and stores it in the cache
   *
   * @return the new session
   */
  public HttpSession createSession(HttpServletRequest request) {
    String sessionId = generateId();
    HttpSessionImpl ss = new HttpSessionImpl(this, sessionTimeout_, sessionId);
    sessionCache_.put(ss.getId(), ss);
    sessionTimer_.add(ss);
    notifySessionCreated(ss);
    activeSessionSet_.add(ss.getId());
    accounting_.update(ss.getId(), SESSION_ACTIVE);
    LOGGER.log(Level.FINER, "created new session " + sessionId);
    return ss;
  }

  /**
   * Invalidates an existing session by removing from the cache.
   *
   * @param sessionId
   *          the session to invalidate
   */
  public void invalidateSession(String sessionId) {
    HttpSessionImpl ss = sessionCache_.get(sessionId);
    if (ss != null) {
      if (LOGGER.isLoggable(Level.INFO)) {
        LOGGER.log(Level.INFO, "session " + sessionId
            + " invalidated by application code", new Throwable());
      }
      sessionTimer_.remove(ss);
      sessionCache_.invalidate(sessionId);
    }
  }

  /**
   * Ensures that changes to the ServletSession are reflected in the cache
   *
   * @param ss
   *          the changed ServletSession (must have been created by this cache's
   *          createSession())
   */
  public void updateSession(HttpSessionImpl ss) {
    // update session timeout
    sessionTimer_.update(ss);
  }

  /**
   * Sets the server ID. This should normally be set by the constructor to the
   * machine's IP address; this method is provided only for testing.
   */
  public void setServerId(int serverId) throws IllegalStateException {
    if (nonEmpty_) {
      throw new IllegalStateException(
          "Can't change serverId of non-empty cache");
    }
    serverId_ = serverId;
  }

  /**
   * Sets the extended server ID. This should normally be set by the constructor
   * to the server's port.
   */
  public void setServerIdExt(int serverIdExt) throws IllegalStateException {
    if (nonEmpty_) {
      throw new IllegalStateException(
          "Can't change serverIdExt of non-empty cache");
    }
    serverIdExt_ = serverIdExt;
  }

  /**
   * Clears the session cache. For use with unit testing only. NOTE: Will throw
   * an UnsupportedOperationException if called on a remote cache.
   */
  protected void clearSessions() {
    sessionCache_.clear();
    sessionTimer_.clear();
  }

  /**
   * Generates a random session ID and concatenates this with the serverId. This
   * produces a 64 bit long value which is encrypted using the specified
   * encryption algorithm to yield another 64 bit value. This is in turn Base64
   * encoded and the resulting 11 character string checked against the session
   * cache for existing entries to avoid collisions. Random session IDs are
   * generated until no collision occurs.
   *
   * The encoded key is as follows: WebSafeBase64(TEAencrypt(serverId << 32 |
   * randId))
   *
   * @return generated session ID
   */
  public synchronized String generateId() {
    nonEmpty_ = true;
    String encodedId;
    for (;;) {
      encodedId = sessionId_.generateId();

      // make sure the new ID is not already a session in the cache and has
      // no accounting history whatsoever
      if (sessionCache_.get(encodedId) == null
          && accounting_.getLastAccess(encodedId) == 0L) {
        return encodedId;
      }
    }
  }

  /**
   * Performs a post-mortem on an invalid session ID.
   * This *could* do something more fancy. Right now it just logs a message.
   *
   * @return the status of the requested session
   */
  public SessionCache.RequestedSessionStatus invalidSessionPostMortem(
      HttpServletRequest req, String sessionId) {
    LOGGER.log(Level.WARNING, "Invalid session ID received: '" + sessionId + "'");
    return SessionCache.RequestedSessionStatus.BAD_SESSION_ID;
  }

  private String createServerIdString(int serverId, int serverIdExt) {
    return ((serverId >> 24) & 0xff) + "." + ((serverId >> 16) & 0xff) + "."
        + ((serverId >> 8) & 0xff) + "." + ((serverId >> 0) & 0xff) + ":"
        + serverIdExt;
  }

  /*****************************************************************************
   * The CacheListener interface *
   ****************************************************************************/

  /**
   * Notify session listeners that an entry has been invalidated. The session is
   * queried from the cache using the session ID. This callback is always
   * invoked before the entry is actually removed from the cache, so this is
   * valid.
   *
   * @param key
   *          session ID
   */
  public void entryInvalidated(String key) {
    LOGGER.info("session " + key + " invalidated");
    activeSessionSet_.remove(key);
    accounting_.update(key, SESSION_INVALIDATED);
    HttpSessionImpl ss = sessionCache_.get(key);
    if (ss != null) {
      notifySessionDestroyed(ss);
      ss.clearAttributes();
    }
  }

  /**
   * Notify session listeners that a session has been evicted--that is, timed
   * out. The session can sometimes be null, in which case ignore the
   * notification. A null value for {@code data} is supplied for certain types
   * of caches, such as the soft cache. Remote caches never call this method.
   *
   * @param key
   *          session ID
   * @param ss
   *          servlet session or null, if not supported
   */
  public void entryEvicted(String key, HttpSessionImpl ss) {
    LOGGER.info("session " + key + " evicted");
    activeSessionSet_.remove(key);
    accounting_.update(key, SESSION_EVICTED);
    if (ss != null) {
      notifySessionDestroyed(ss);
      ss.clearAttributes();
    }
  }

  /**
   * Registers the specified listener to be notified of session
   * activation/passivation events. Note: currently, sessions are not
   * activated/passivated with GSE, so registered listeners will not receive
   * event notifications under any circumstances.
   *
   * @param listener
   */
  public void registerSessionActivationListener(
      HttpSessionActivationListener listener) {
    // does nothing
  }

  /**
   * Registers the specified listener to be notified of session attribute
   * events: any attribute on the session that is added, removed or replaced is
   * signaled to the listener.
   *
   * @param listener
   */
  public void registerSessionAttributeListener(
      HttpSessionAttributeListener listener) {
    if (sessionAttributeListeners_ == null) {
      sessionAttributeListeners_ = new LinkedList<HttpSessionAttributeListener>();
    }
    sessionAttributeListeners_.add(listener);
  }

  /**
   * Registers the specified listener to be notified of session events: that is,
   * sessions being created or destroyed.
   *
   * @param listener
   */
  public void registerSessionListener(HttpSessionListener listener) {
    if (sessionListeners_ == null) {
      sessionListeners_ = new LinkedList<HttpSessionListener>();
    }
    sessionListeners_.add(listener);
  }

  /**
   * Notifies all session listeners that a session has been created.
   *
   * @param ss
   *          the session
   */
  protected void notifySessionCreated(HttpSessionImpl ss) {
    if (sessionListeners_ == null) {
      return;
    }
    for (HttpSessionListener listener : sessionListeners_) {
      listener.sessionCreated(new HttpSessionEvent(ss));
    }
  }

  /**
   * Notifies all session listeners that a session has been destroyed.
   *
   * @param ss
   *          the session
   */
  protected void notifySessionDestroyed(HttpSessionImpl ss) {
    if (sessionListeners_ == null) {
      return;
    }
    for (HttpSessionListener listener : sessionListeners_) {
      listener.sessionDestroyed(new HttpSessionEvent(ss));
    }
  }

  /**
   * Notifies all attribute listeners that an attribute has been added
   *
   * @param ss
   *          the session
   * @param name
   *          the name of the attribute
   * @param value
   *          the value of the attribute
   */
  protected void notifySessionAttributeAdded(HttpSessionImpl ss, String name,
      Object value) {
    if (sessionAttributeListeners_ == null) {
      return;
    }
    for (HttpSessionAttributeListener listener : sessionAttributeListeners_) {
      listener.attributeAdded(new HttpSessionBindingEvent(ss, name, value));
    }
  }

  /**
   * Notifies all attribute listeners that an attribute has been replaced
   *
   * @param ss
   *          the session
   * @param name
   *          the name of the replaced attribute
   * @param value
   *          the old value of the replaced attribute
   */
  protected void notifySessionAttributeReplaced(HttpSessionImpl ss, String name,
      Object value) {
    if (sessionAttributeListeners_ == null) {
      return;
    }
    for (HttpSessionAttributeListener listener : sessionAttributeListeners_) {
      listener.attributeReplaced(new HttpSessionBindingEvent(ss, name, value));
    }
  }

  /**
   * Notifies attribute listeners that an attributed has been removed
   *
   * @param ss
   *          the session
   * @param name
   *          the name of the removed attribute
   */
  protected void notifySessionAttributeRemoved(
      HttpSessionImpl ss, String name) {
    if (sessionAttributeListeners_ == null) {
      return;
    }
    for (HttpSessionAttributeListener listener : sessionAttributeListeners_) {
      listener.attributeRemoved(new HttpSessionBindingEvent(ss, name));
    }
  }

  /**
   *
   * Given {@code cookies}, finds the
   * {@link SessionCookie} that
   * corresponds to the cookie with the correct name (correct in the sense
   * that it is the designated session cookie for the specified
   * HttpServer. The {@link SessionCookie},
   * cookie session id, and a boolean indicating whether or not it was found
   * are provided as methods on this class as well.
   *
   * @param cookies
   * @param serverType
   */
  public SessionCookie readSessionFromCookies(
      Cookie[] cookies, String serverType) {
    SessionCookie session_cookie_ = null;

    if (cookies != null) {
      HttpSession cookieSession;
      // Get regular session cookie first
      for (Cookie cookie : cookies) {
        if (SESSION_COOKIE_NAME.equals(cookie.getName())) {
          session_cookie_ = SessionCookieParser.parse(cookie.getValue());
          // only use this cookie if it has a matching server type
          if (session_cookie_.getId(serverType) != null) {
            session_cookie_.is_session_id_from_cookie_ = true;
            session_cookie_.requested_session_id_ =
                session_cookie_.getId(serverType);
            // if this session is in the cache, don't check any more cookies
            cookieSession = getSession(session_cookie_.requested_session_id_);
            if (cookieSession != null) {
              break;
            }
          }
        }
      }
    }
    return session_cookie_;
  }

}