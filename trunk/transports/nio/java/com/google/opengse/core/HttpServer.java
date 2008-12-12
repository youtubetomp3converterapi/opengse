// Copyright 2002-2006 Google Inc.
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

package com.google.opengse.core;


import com.google.opengse.ServletEngineConfiguration;
import com.google.opengse.HttpRequest;
import com.google.opengse.HttpRequestHandler;
import com.google.opengse.util.DispatchQueue;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * A class which listens on a set of ports for incoming Http
 * connections, creates a <code>HttpConnection</code> to handle a
 * particular connection, dispatches requests to an appropriate
 * <code>HttpServlet</code> using a <code>HttpDispatcher</code> and
 * helps run the servlet on a separate thread using a
 * <code>DispatchQueue<code>. The <code>HttpServer</code> does very
 * little itself and instead acts as a delegator and central
 * dispatcher.
 *
 * @see com.google.opengse.core.HttpConnection
 * @see DispatchQueue
 * @author Peter Mattis
 */
final class HttpServer  implements AcceptCallback, QuitCallback {
  private static final Logger LOGGER =
    Logger.getLogger(HttpServer.class.getName());

  /**
   * Default settings
   */
  protected static final String  SESSION_PARAM_NAME = "gsessionid";
  protected static final String  SESSION_COOKIE_NAME = "S";
  private   static final int     DEFAULT_MAX_POSTSIZE = 1024 * 1024;
  private   static final int     DEFAULT_KEEPALIVE_TIMEOUT = 15 * 1000;
  private   static final int     DEFAULT_STARTUP_TIMEOUT = 30 * 1000;
  private   static final int     DEFAULT_READ_TIMEOUT = 10 * 1000;
  private   static final int     DEFAULT_WRITE_TIMEOUT = 10 * 1000;
  private   static final int     DEFAULT_BUFFER_SIZE = 16 * 1024;
  private   static final boolean DEFAULT_COMPRESS_RESPONSES = false;
  private   static final boolean DEFAULT_CLOSE_ON_ERRORS = false;
  public    static final int     NO_FLUSH_INTERVAL = 0;
  private   static final String  DEFAULT_SECURE_SESSION_COOKIE_PATH = "/";

  private final HttpRequestHandler dispatcher;
  private static final String ALLOWED_SERVERTYPE_CHARACTERS =
      "[a-zA-Z0-9_\\-]+";

  /**
   * A factory for creating multipart request objects. This pattern makes it
   * possible for application code to provide a customized mulitpart request
   * implementation.
   */
  public interface MultipartRequestFactory {
    HttpServletRequest create(HttpConnection conn);
  }

  private final NetSelector selector_;
  private final DispatchQueue queue_;
  private boolean is_exiting_ = false;

  private String errorPath_ = null;

  private String name_ = null;
  private String user_ip_header_ = null;
  private String secure_header_ = null;
  private String session_cookie_domain_ = null;
  private int session_cookie_maxage_ = -1;
  private String default_cache_policy_ = "private";
  private int maxpostsize_ = DEFAULT_MAX_POSTSIZE;
  private int keepalive_timeout_ = DEFAULT_KEEPALIVE_TIMEOUT;
  private int startup_timeout_ = DEFAULT_STARTUP_TIMEOUT;
  private int read_timeout_ = DEFAULT_READ_TIMEOUT;
  private int write_timeout_ = DEFAULT_WRITE_TIMEOUT;
  private int response_buffer_size_ = DEFAULT_BUFFER_SIZE;
  private boolean compress_responses_ = DEFAULT_COMPRESS_RESPONSES;
  private boolean health_logging_ = true;
  private boolean close_on_errors_ = DEFAULT_CLOSE_ON_ERRORS;
  private String tempdir_ = null;
  private String servertype_ = null;
  private boolean suppress_enforcing_secure_session_cookies_ = false;
  private String secure_session_cookie_domain_ = null;
  private String secure_session_cookie_path_ =
      DEFAULT_SECURE_SESSION_COOKIE_PATH;
  private HttpRequestHandler badRequestHandler_ = null;
  private final ServletEngineConfiguration config;

  /**
   * Constructs an HttpServer instance using the supplied parameters.
   *
   * @param selector
   * @param queue
   */
  HttpServer(NetSelector selector,
             DispatchQueue queue,
             HttpRequestHandler dispatcher,
             ServletEngineConfiguration config) {
    this.dispatcher = dispatcher;
    this.config = config;
    if (selector == null) {
      throw new NullPointerException();
    }
    this.selector_ = selector;
    this.queue_ = queue;
    setServerType(config.getServerType());
  }

  ServletEngineConfiguration getConfiguration() {
    return config;
  }

  /**
   * Accessor function to retrieve the <code>NetSelector</code>.
   *
   * @see HttpConnection
   */
  public NetSelector getSelector() {
    return selector_;
  }

  /**
   * Accessor function to retrieve the <code>DispatchQueue</code>.
   *
   * @see HttpConnection
   */
  public DispatchQueue getQueue() {
    return queue_;
  }

  void enqueue(Runnable r) {
    queue_.addRunnable(r);
  }

  HttpRequestImpl createRequestSubsetImpl(HttpConnection conn) {
    return new HttpRequestImpl(conn);
  }

  /**
   * Returns the path of the servlet that will be forwarded to when an
   * {@link Exception} is thrown by the servicing servlet, or
   * <code>null</code>
   *
   * @see HttpServletRequest#getRequestDispatcher(String path)
   */
  public String getErrorPath() {
    return errorPath_;
  }
  public void setErrorPath(String errorPath) {
    this.errorPath_ = errorPath;
  }

  /**
   * Accessor function to retrieve the servers name.
   */
  public synchronized String getName() {
    // lazily get the default name in order to allow time for it
    // to be set correctly via a call to {@link #setName(String)}.
    if (name_ == null) {
      name_ = getDefaultName();
      LOGGER.warning("using DNS lookup for server hostname");
    }
    return name_;
  }
  public void setName(String name) {
    this.name_ = name;
  }

  /**
   * Returns the header to examine to determine the user's remote
   * IP address. This parameter is necessary to determine whether or not
   * to derive the remote IP address from a request header or from the
   * socket endpoint.
   *
   * @return the header to examine for remote IP, or null for TCP
   *         socket endpoint address.
   */
  public String getUserIPHeader() {
    return user_ip_header_;
  }
  public void setUserIPHeader(String userIpHeader) {
    this.user_ip_header_ = userIpHeader;
  }

  /**
   * Returns the header to examine to determine whether the
   * request underwent secure transport, such as an SSL
   * proxy.
   *
   * @return the header name
   */
  public String getSecureHeader() {
    return secure_header_;
  }
  public void setSecureHeader(String secureHeader) {
    this.secure_header_ = secureHeader;
  }

  /**
   * Returns the domain to be used when setting a session cookie.
   * This will normally be ".google.com" so that the cookie
   * will be shared by all google services.
   */
  public String getSessionCookieDomain() {
    return session_cookie_domain_;
  }
  public void setSessionCookieDomain(String session_cookie_domain) {
    this.session_cookie_domain_ = session_cookie_domain;
  }


  /**
   * Use with caution, and if you turn it on, please be sure to have a
   * plan for turning it off.
   *
   * If set to true - do not actually kill sessions when a request
   * comes in that should have a secure session cookie, but doesn't.
   *
   * Instead, just log the issue.  This is intended to be used by
   * applications that want to soft-launch their implementation of
   * secure session ids, and provide additional testing before
   * impacting users.
   *
   * By definition, this has no effect unless
   * getUsingSecureSessionCookies() is also true.
   */
  protected void setSuppressEnforcingSecureSessionCookies(boolean suppress){
     suppress_enforcing_secure_session_cookies_ = suppress;
  }

  /**
   * Whether we are suppressing enforcement of secure ids, even if
   * they are enabled.  (For tests or launches.)
   */
  protected boolean getSuppressEnforcingSecureSessionCookies(){
    return suppress_enforcing_secure_session_cookies_;
  }

  /**
   * Returns the domain to be used when setting a secure session cookie.
   * This is often more specific, e.g., "adwords.google.com", since
   * the secure cookie is used only by one application.
   */
  public String getSecureSessionCookieDomain() {
    return secure_session_cookie_domain_;
  }
  protected void setSecureSessionCookieDomain(String cookieDomain) {
    this.secure_session_cookie_domain_ = cookieDomain;
  }

  /**
   * Returns the path for secure session cookies, e.g., '/adsense/'.
   * This further restricts the distribution of the cookie, especially
   * if the domain is ".google.com".
   */
  public String getSecureSessionCookiePath() {
    return secure_session_cookie_path_;
  }

  protected void setSecureSessionCookiePath(String cookiePath) {
    this.secure_session_cookie_path_ = cookiePath;
  }

  /**
   * Returns the maximum age of session cookies.
   * Should normally be -1; only change for testing.
   */
  public int getSessionCookieMaxAge() {
    return session_cookie_maxage_;
  }
  public void setSessionCookieMaxAge(int cookieMaxAge) {
    session_cookie_maxage_ = cookieMaxAge;
  }

  public String getDefaultCachePolicy() {
    return this.default_cache_policy_;
  }

  public void setDefaultCachePolicy(String policy) {
    this.default_cache_policy_ = policy;
  }


  /**
   * Returns the size of the HTTP response buffer.
   */
  public int getResponseBufferSize() {
    return response_buffer_size_;
  }
  public void setResponseBufferSize(int bufferSize) {
    this.response_buffer_size_ = bufferSize;
  }

  /**
   * Returns true if HTTP responses are compressed by default
   */
  public boolean getCompressResponses() {
    return compress_responses_;
  }
  public void setCompressResponses(boolean compressResponses) {
    this.compress_responses_ = compressResponses;
  }

  /**
   * Accessor function to retrieve the maximum post size. Used by
   * {@link HttpConnection} to refuse requests with excessive POST
   * data.
   */
  public int getMaxPostSize() {
    return maxpostsize_;
  }
  public void setMaxPostSize(int maxPostSize) {
    this.maxpostsize_ = maxPostSize;
  }

  /**
   * Accessor function to retrieve the keepalive timeout. Used by
   * {@link HttpConnection} to close connections marked keep-alive
   * that are left open by the client.
   */
  public int getKeepaliveTimeout() {
    if (is_exiting_) {
      return 0;
    } else {
      return keepalive_timeout_;
    }
  }
  public void setKeepaliveTimeout(int keepAliveTimeout) {
    this.keepalive_timeout_ = keepAliveTimeout;
  }

  /**
   * Accessor function to retrieve the startup timeout. Used by
   * {@link HttpConnection} to close pending connections before they are
   * fully initialized if the client is too unresponsive.
   */
  public int getStartupTimeout() {
    return startup_timeout_;
  }
  public void setStartupTimeout(int startupTimeout) {
    this.startup_timeout_ = startupTimeout;
  }

  /**
   * Accessor function to retrieve the read timeout. Used by {@link
   * HttpConnection} to close connections while waiting to read data from an
   * unresponsive client.
   */
  public int getReadTimeout() {
    return read_timeout_;
  }
  public void setReadTimeout(int readTimeout) {
    this.read_timeout_ = readTimeout;
  }

  /**
   * Accessor function to retrieve the write timeout. Used by {@link
   * HttpConnection} to close connections while waiting to write data to an
   * unresponsive client.
   */
  public int getWriteTimeout() {
    return write_timeout_;
  }
  public void setWriteTimeout(int writeTimeout) {
    this.write_timeout_ = writeTimeout;
  }

    /**
   * Accessor function for whether to log health check requests. Used by
   * {@link HttpConnection} to skip potentially high volume logging
   * for health checks.
   */
  public boolean getHealthCheckLogging() {
    return health_logging_;
  }
  public void setHealthCheckLogging(boolean healthCheckLogging) {
    this.health_logging_ = healthCheckLogging;
  }

  /**
   * Accessor function for whether to close connections when the HTTP return
   * code is >= 400. This should be set to true unless GSE is acting behind
   * a proxy.
   */
  public boolean getCloseOnErrors() {
    return close_on_errors_;
  }
  public void setCloseOnErrors(boolean closeOnErrors) {
    this.close_on_errors_ = closeOnErrors;
  }

  /**
   * Get the server type using the supplied http request. If no selector has
   * been set, uses the default server type.
   *
   * @param req The request for selector to find a server type.
   * @return The server type for the request.
   */
  public String getServerType(HttpRequest req) {
    return "default";
  }

  /**
   * Returns the default server type, without the benefit of the http request.
   * Use {@link #getServerType(com.google.opengse.HttpRequest)) instead.
   */
  @Deprecated
  public String getServerType() {
    return getServerType(null);
  }

  /**
   * Set the default server type for the server.
   *
   * @param serverType The server type for the server.
   */
  private void setServerType(String serverType) {
    if (!serverType.matches(ALLOWED_SERVERTYPE_CHARACTERS)) {
      throw new IllegalArgumentException(
          "serverType (" + serverType + ") must contain only "
          + ALLOWED_SERVERTYPE_CHARACTERS);
    }
    this.servertype_ = serverType;
  }


  /**
   *  Get the name of the secure cookie, if one is being used.  Secure
   *  cookies are specific to the server type.
   */
  protected String getSecureSessionCookieName(String serverType) {
    return SESSION_COOKIE_NAME + '_' + serverType;
  }

  /**
   * Accessor function to retrieve the directory which the server
   * components should use to store temporary files.
   */
  public String getTempdir() {
    return tempdir_;
  }
  public void setTempdir(String tempdir) {
    this.tempdir_ = tempdir;
    // attempt to create a file in the directory to test validity
    try {
      File test;
      if (tempdir == null) {
        test = File.createTempFile("gse_test", null);
      } else {
        test = File.createTempFile("gse_test", null, new File(tempdir));
      }
      test.delete();
    } catch (IOException ioe) {
      LOGGER.severe("unable to use '" + tempdir + "' " +
                     "as temp file directory: " + ioe.getMessage());
      this.tempdir_ = null;
    }
  }

  /**
   * Receives a new socket and constructs an <code>HttpConnection</code> to
   * handle http processing on it. Some bytes may have been read from the given
   * socket already.
   *
   * @param channel the new socket channel
   * @param bytesAlreadyRead the bytes already read from channel
   */
  public void handleNewConnection(SocketChannel channel,
      byte[] bytesAlreadyRead) throws IOException {
    HttpConnection conn = new HttpConnection(this, channel, dispatcher);
    if (bytesAlreadyRead.length > 0) {
      // Pretends that some bytes have just been read from the socket into conn
      conn.getInputBuffer().writeBytes(bytesAlreadyRead);
      conn.readRequest();
    }
  }

  /**
   * Prepare server for shutdown.
   */
  public void drain() {
    is_exiting_ = true;
  }

  /**
   * Stops accepting connections on the selector, blocks until there are no more
   * pending requests and the dispatch queue is empty. In the case that quit is
   * invoked from a worker thread, we allow for 1 request to still be in
   * progress.
   *
   * @param timeout is the timeout value in milliseconds for this operation.
   * @return <code>true</code> if all dispatch threads gracefully exited;
   *         <code>false</code> otherwise.
   */
  public boolean quit(long timeout) throws IOException {
    long end = System.currentTimeMillis() + timeout;

    // disallow Keep-Alive connections
    drain();
    // stop accepting new connections
    selector_.drain();

    // Compute remaining timeout and wait for all requests to complete, except
    // maybe this request.
    timeout = Math.max(end - System.currentTimeMillis(), 0);
//    requestTracker_.waitUntilIdle(timeout);

    // Compute remaining timeout again and sweep up the worker threads.
    timeout = Math.max(end - System.currentTimeMillis(), 0);
    boolean success = queue_.quit(timeout);

    // Close the selector and all open connections.
    selector_.quit(success);
    return success;
  }

  /**
   * Returns the local host name or "unknown". If the port
   * that the selector is listening on is not 80 or 443, the
   * port number is included in host:port format.
   */
  private String getDefaultName() {
    try {
      // get the localhost
      String host = InetAddress.getLocalHost().getHostName();
      // get the port the selector is listening on
      int port = getSelector().getPort();

      // use the port if NOT 80 or 443
      if (port == 80 || port == 443) {
        return host;
      } else {
        return host + ":" + port;
      }
    } catch (UnknownHostException e) {
      return "unknown";
    }
  }


  /**
   * Sets a servlet used to handle bad requests which would normally return
   * a non-200 error code to the user. For example, content lengths for a post
   * body exceeding the maximum post size, or an unsupported content encoding.
   * The HttpServletRequest provided will contain all headers, but may not
   * include a post body. The HttpServletResponse provided will have a
   * "Connection: close" header and the status set to an error code. Do not
   * modify the "Connection" header, but feel free to reset the status code to
   * 200 if appropriate.
   *
   * @param badRequestHandler servlet to handle bad requests, or null for none.
   */
  public void setBadRequestHandler(HttpRequestHandler badRequestHandler) {
    badRequestHandler_ = badRequestHandler;
  }

  /**
   * @return the bad request handler if set, or null otherwise.
   */
  public HttpRequestHandler getBadRequestHandler() {
    return badRequestHandler_;
  }

}