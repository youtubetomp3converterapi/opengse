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

import com.google.opengse.configuration.WebAppConfigurationException;
import com.google.opengse.util.PropertiesUtil;
import com.google.opengse.session.SessionCache;
import com.google.opengse.session.SessionCacheFactory;
import com.google.opengse.session.HttpSessions;
import com.google.opengse.session.SessionHandlingRequestWrapper2;
import com.google.opengse.jndi.JNDIMain;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.naming.NamingException;

/**
 * A collection of WebApp objects that acts like a WebApp.
 *
 * @author Mike Jennings
 */
final class WebAppCollectionImpl implements WebAppCollection {

  private final Map<String, WebApp> uriPrefixToWebApp;
  private final Map<String, WebApp> uriPrefixToStartedWebApp;
  private static final String ROOT_CONTEXT_URI_PREFIX = "";
  private static final String CONTEXTKEY_TMP_DIR
      = "javax.servlet.context.tempdir";
  private File tmpdir;
  private final ServletContainerContext containerContext;
  private final Properties props;
  private SessionCache session_cache_;
  private HttpSessions httpSessions;
  private static final String CONTEXTKEY_SESSIONCACHE
      = SessionCache.class.getName();
  private final boolean sessionsEnabled;

  private WebAppCollectionImpl(Properties props)
      throws IOException, WebAppConfigurationException {
    this.props = props;
    this.containerContext = new ServletContainerContextImpl(this);
    uriPrefixToWebApp = new HashMap<String, WebApp>();
    uriPrefixToStartedWebApp = new HashMap<String, WebApp>();
    checkMandatoryPropertiesExist();
    sessionsEnabled = !PropertiesUtil.getBoolean(
        props, "disable-sessions", false);
  }

  private void checkMandatoryPropertiesExist()
      throws WebAppConfigurationException, IOException {
    if (props.getProperty(CONTEXTKEY_TMP_DIR) == null) {
      props.setProperty(CONTEXTKEY_TMP_DIR, System.getProperty("java.io.tmpdir"));
    }
    tmpdir = PropertiesUtil.getFile(props, CONTEXTKEY_TMP_DIR);
    checkTempDirProperty();
  }

  private void checkTempDirProperty() throws WebAppConfigurationException {
    if (tmpdir == null) {
      throw new WebAppConfigurationException(
          "No '" + CONTEXTKEY_TMP_DIR + "' property defined");
    }
    if (tmpdir.isFile()) {
      throw new WebAppConfigurationException(
          "'" + CONTEXTKEY_TMP_DIR + "' property points to a file");
    }
    if (!tmpdir.exists()) {
      tmpdir.mkdirs();
    }
    if (!tmpdir.exists()) {
      throw new WebAppConfigurationException(
          "Directory ${" + CONTEXTKEY_TMP_DIR + "}=" + tmpdir
              + " does not exist");
    }
  }

  public ServletContainerContext getContainerContext() {
    return containerContext;
  }

  private synchronized HttpSessions getHttpSessions() {
    if (httpSessions == null) {
      try {
        httpSessions = JNDIMain.lookup(HttpSessions.class);
      } catch (NamingException e) {
        throw new RuntimeException(e);
      }
    }
    return httpSessions;
  }

  private synchronized SessionCache getSessionCache() {
    // create on demand
    if (session_cache_ == null) {
      SessionCacheFactory factory;
      try {
        // look up our SessionCacheFactory via JNDI
        // (this allows end users to plug in their own session stuff)
        factory = JNDIMain.lookup(SessionCacheFactory.class);
      } catch (NamingException e) {
        throw new RuntimeException(e);
      }

      session_cache_ = factory.createSessionCache(0);
//      if (session_id_key_ != null) {
//        session_cache_.setEncryptionKey(session_id_key_);
//      }
    }
    return session_cache_;
  }

  public Object getAttribute(String name) {
    if (CONTEXTKEY_TMP_DIR.equals(name)) {
      return tmpdir;
    }
    if (CONTEXTKEY_SESSIONCACHE.equals(name)) {
      return getSessionCache();
    }
    return null;
  }

  /**
   * Creates a WebApplications object given a root webapp.
   *
   * @param rootWebApp The root webapp
   * @return a new WebApplications object.
   */
  static WebAppCollection create(Properties props, WebApp rootWebApp)
      throws WebAppConfigurationException, IOException {
    if (!ROOT_CONTEXT_URI_PREFIX.equals(rootWebApp.getUriPrefix())) {
      throw new WebAppConfigurationException("Not a root WebApp");
    }
    WebAppCollectionImpl webApps = new WebAppCollectionImpl(props);
    webApps.addWebApp(rootWebApp);
    return webApps;
  }

  /**
   * Add a web application to the collection of web applications. Note: If
   * another webapp has been added with the same URI prefix, this method will
   * throw an exception.
   */
  public void addWebApp(WebApp webapp) throws WebAppConfigurationException {
    String uriPrefix = webapp.getUriPrefix();
    if (getWebApp(uriPrefix) != null) {
      throw new WebAppConfigurationException(
          "A WebApp with URI prefix '" + uriPrefix + "' already exists");
    }
    uriPrefixToWebApp.put(webapp.getUriPrefix(), webapp);
  }

  /**
   * Returns true if this collection contains the webapp with the given uri
   * prefix.
   *
   * @param uriPrefix the prefix to check.
   */
  public WebApp getWebApp(String uriPrefix) {
    return uriPrefixToWebApp.get(uriPrefix);
  }

  /**
   * Starts all of the registered webapps.
   */
  public void startAll() throws ServletException {
    if (getWebApp(ROOT_CONTEXT_URI_PREFIX) == null) {
      throw new ServletException("No root webapp defined!");
    }
    stopAll();
    Collection<WebApp> webapps = uriPrefixToWebApp.values();
    if (webapps.isEmpty()) {
      throw new ServletException("No web applications registered!");
    }
    for (WebApp webApp : uriPrefixToWebApp.values()) {
      try {
        webApp.start();
        uriPrefixToStartedWebApp.put(webApp.getUriPrefix(), webApp);
      } catch (ServletException ex) {
        // stop all of the other webapps if one has trouble starting.
        stopAll();
        throw ex;
      }
    }
  }

  /**
   * Stops all of the webapps which have been started.
   */
  public void stopAll() {
    for (WebApp startedWebApp : uriPrefixToStartedWebApp.values()) {
      startedWebApp.stop();
    }
    uriPrefixToStartedWebApp.clear();
  }

  /**
   * Returns a <code>ServletContext</code> object that corresponds to a
   * specified URL on the server.
   *
   * <p>This method allows servlets to gain access to the context for various
   * parts of the server, and as needed obtain
   * {@link javax.servlet.RequestDispatcher} objects from the context. The
   * given path must be begin with "/", is interpreted relative to the server's
   * document root and is matched against the context roots of other web
   * applications hosted on this container.
   *
   * <p>In a security conscious environment, the servlet container may return
   * {@code null} for a given URL.
   *
   * @param uripath a <code>String</code> specifying the context path of
   *        another web application in the container.
   * @return the <code>ServletContext</code> object that corresponds to the
   *         named URL, or null if either none exists or the container wishes to
   *         restrict this access.
   * @see javax.servlet.RequestDispatcher
   */
  ServletContext getContext(String uripath) {
    WebApp webapp = uriPrefixToWebApp.get(uripath);
    return (webapp == null) ? null : webapp.getContext();
  }

  /**
   * {@inheritDoc}
   */
  public void doFilter(ServletRequest req,
      ServletResponse response) throws IOException, ServletException {
    if (response instanceof HttpServletResponse) {
      HttpServletResponse hsr = (HttpServletResponse) response;
      hsr.setHeader("X-Powered-By", "Servlet/2.5 (OpenGSE/0.9)");
    }
    HttpServletRequest request = (HttpServletRequest) req;
    String requestURI = request.getRequestURI();
    // The root context always matches if nothing else matches
    String matchedPrefix = ROOT_CONTEXT_URI_PREFIX;
    for (String uriPrefix : uriPrefixToStartedWebApp.keySet()) {
      // if the uri prefix matches our request uri and is longer than
      // the previous match, than this is a better match
      if (requestURI.startsWith(uriPrefix) &&
          uriPrefix.length() > matchedPrefix.length()) {
        // the new matched prefix is longer and thus, more specific than the
        // previous one.
        matchedPrefix = uriPrefix;
      }
    }
    WebApp webapp = uriPrefixToStartedWebApp.get(matchedPrefix);
    if (webapp == null) {
      throw new ServletException(
          "No webapps started which can handle '" + requestURI + "'");
    }
    if (sessionsEnabled) {
//      request = new SessionHandlingRequestWrapper(
//          request, getSessionCache(), response);
      request = new SessionHandlingRequestWrapper2(
          request, getHttpSessions(), response);
    }
    request = new WebAppRequestWrapper(request, webapp);
    response = new WebAppResponseWrapper(
        (HttpServletResponse) response, webapp, request);
    webapp.doFilter(request, response);
  }


}
