// Copyright 2007 Google Inc. All Rights Reserved.
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

package com.google.opengse.httputil;

import com.google.opengse.util.string.StringUtil;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.FilterConfig;
import javax.servlet.ServletConfig;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Some util methods for HTTP in servlets.
 *
 * @author Jeremy Chau
 */
public final class HttpUtil {

  /**
   * Dump the request's HTTP headers into a string, good for logging.
   * Likely to be called from initRequest().
   */
  public static String dumpHeadersToString(HttpServletRequest req) {
    StringBuffer sb = new StringBuffer();
    Enumeration<?> names = req.getHeaderNames();
    while (names.hasMoreElements()) {
      String name = names.nextElement().toString();
      Enumeration<?> values = req.getHeaders(name);
      while (values.hasMoreElements())
        sb.append(name + ": " + values.nextElement() + "\n");
    }

    return sb.toString();
  }

  /**
   * Extract a parameter value from a request.
   *
   * @param request the HttpServletRequest to parse from.
   * @param name the name of the parameter to look for
   * @param defaultValue default value to return.
   * @return String parameter value, or default if not found.
   */
  public static String getDefault (final HttpServletRequest request,
      final String name, final String defaultValue) {
    final String val = request.getParameter(name);
    return null == val ? defaultValue : val;
  }

  /**
   * Returns the value of the parameter as an int, or a default value if the
   * value is unparseable.
   *
   * @param request the HttpServletRequest to parse from.
   * @param name the name of the parameter to look for
   * @param defaultValue default value to return.
   * @return int value from the request parameter, or a default value.
   */
  public static int getIntDefault (final HttpServletRequest request,
     final String name, final int defaultValue) {
    final String val = request.getParameter(name);
    if (null == val) {
      return defaultValue;
    }
    try {
      return Integer.parseInt(val);
    } catch (final NumberFormatException exc) {
      return defaultValue;
    }
  }

  /**
   * Returns the value of the parameter as a long, or a default value if the
   * value is unparseable.
   *
   * @param request the HttpServletRequest to parse from.
   * @param name the name of the parameter to look for
   * @param defaultValue default value to return.
   * @return long value from the request parameter, or a default value.
   */
  public static long getLongDefault (final HttpServletRequest request,
      final String name, final long defaultValue) {
    final String val = request.getParameter(name);
    if (null == val) {
      return defaultValue;
    }
    try {
      return Long.parseLong(val);
    } catch (final NumberFormatException exc) {
      return defaultValue;
    }
  }

  /** Return all headers for this connection in a map
   * Duplicates HttpHeaders.listHeaders()
   **/
  public static Map<String, String[]> getHeaderFields(HttpURLConnection con) {
    int i = 1; // spastic getHeaderFieldKey() uses a 1-based index
    Map<String, String[]> map = new HashMap<String, String[]>();
    String key;
    while (null != (key = con.getHeaderFieldKey(i))) {
      // Add normalized header to the map
      map.put(key.toLowerCase(), new String[] {con.getHeaderField(i)});
      i++;
    }
    return map;
  }

  /**
   * If the specified request was a forwarded or included request, unwrap it
   * and return the original request.
   */
  public static HttpServletRequest unwrap(HttpServletRequest req) {
    if (req instanceof ServletRequestWrapper) {
      ServletRequestWrapper wrapper = (ServletRequestWrapper) req;
      req = (HttpServletRequest) wrapper.getRequest(); // unwrap
    }
    return req;
  }

  /**
   * Returns the request URI of the specified request, including the
   * query string if any. If this request is a forwarded (wrapped)
   * request, the request is first unwrapped. This method is most
   * commonly useful when using request dispatchers to forward from
   * one servlet to another.
   *
   * This method differs from the following <code>getForwardingURL</code>
   * in that only the URI is returned, not the entire URL.
   */
  public static String getForwardingURI(HttpServletRequest req) {
    /*
     * Extract the full request URI from the original request. The
     * query string, if present, is appended to the request URI.
     */
    req = unwrap(req);
    String uri = req.getRequestURI();
    String query = req.getQueryString();
    return (query == null) ? uri : (uri + "?" + query);
  }

  /**
   * Returns the request URL of the specified request, including the
   * query string if any. If this request is a forwarded (wrapped)
   * request, the request is first unwrapped. This method is most
   * commonly useful when using request dispatchers to forward from
   * one servlet to another.
   *
   * This method differs from the preceeding <code>getForwardingURI</code>
   * in that the entire URL is returned, not just the URI.
   */
  public static String getForwardingURL(HttpServletRequest req) {
    /*
     * Extract the full request URI from the original request. The
     * query string, if present, is appended to the request URI.
     */
    req = unwrap(req);
    String url = req.getRequestURL().toString();
    String query = req.getQueryString();
    return (query == null) ? url : (url + "?" + query);
  }

  /**
   * Returns an unmodifiable map from <code>String</code> name to
   * <code>String</code> value for every init parameter in the
   * specified <code>FilterConfig</code>.
   */
  public static Map<String, String> getInitParameters(FilterConfig config) {
    Enumeration<?> e = config.getInitParameterNames();
    if (!e.hasMoreElements()) {
      return Collections.emptyMap();
    }
    Map<String, String> map = new HashMap<String, String>();
    while (e.hasMoreElements()) {
      String name = e.nextElement().toString();
      map.put(name, config.getInitParameter(name));
    }
    return Collections.unmodifiableMap(map);
  }

  /**
   * Returns an unmodifiable map from <code>String</code> name to
   * <code>String</code> value for every init parameter in the
   * specified <code>ServletConfig</code>.
   */
  public static Map<String, String> getInitParameters(ServletConfig config) {
    Enumeration<?> e = config.getInitParameterNames();
    if (!e.hasMoreElements()) {
      return Collections.emptyMap();
    }
    Map<String, String> map = new HashMap<String, String>();
    while (e.hasMoreElements()) {
      String name = e.nextElement().toString();
      map.put(name, config.getInitParameter(name));
    }
    return Collections.unmodifiableMap(map);
  }

  /**
   * Returns an unmodifiable map from <code>String</code> name to
   * {@link Cookie}. If the request has no cookies, this method
   * returns an empty map.
   */
  public static Map<String, Cookie> getCookies(HttpServletRequest req) {
    Cookie[] cookies = req.getCookies();
    if ((cookies == null) || (cookies.length == 0)) {
      return Collections.emptyMap();
    }
    Map<String, Cookie> map = new HashMap<String, Cookie>(cookies.length);
    for (int i = 0; i < cookies.length; i++) {
      Cookie cookie = cookies[i];
      map.put(cookie.getName(), cookie);
    }
    return Collections.unmodifiableMap(map);
  }

  /**
   * Does the reverse of javax.servlet.http.HttpUtils.parseQueryString()
   *
   * @param args        A hashtable mapping keys to arrays of values, such
   *                    as that produced with parseQueryString
   * @param encoding    Build string using this encoding
   * @return String     A single CGI string like "q=test&deb=i..."
   */
  public static String constructQueryString(Map<String, String[]> args,
                                            String encoding)
      throws UnsupportedEncodingException {
    // URL-escape all args (parseQueryString unescapes them)
    Map<String, String[]> safeArgs = new HashMap<String, String[]>();
    for (Map.Entry<String, String[]> e : args.entrySet()) {
      String values[] = e.getValue();
      String safeValues[] = new String[values.length];
      for (int j = 0; j < values.length; j++) {
        safeValues[j] = FastURLEncoder.encode(values[j], encoding);
      }
      safeArgs.put(e.getKey(), safeValues);
    }

    // return CGI string
    return StringUtil.arrayMap2String(safeArgs, "=", "&");
  }

  /**
   * Shortcut for constructQueryString(args, "UTF-8").
   * @see #constructQueryString(Map, String)
   */
  public static String constructQueryString(Map<String, String[]> args) {
    try {
      return constructQueryString(args, "UTF-8");
    } catch (UnsupportedEncodingException ue) {
      // This cannot happen with UTF-8
      throw new AssertionError("Could not construct query in UTF-8");
    }
  }

  /**
   * Given a request, reconstruct the first line as sent by the client.
   * This looks something like:
   *  GET /images?q=johnson+street&svnum=10&hl=en&ie=UTF8&oe=UTF8&sa=N HTTP/1.1
   *
   * @param req The request object. Must not be null
   * @return The first line of the request
   */
  public static String reconstructRequestFirstLine(HttpServletRequest req) {
    if (req == null) {
      throw new IllegalArgumentException();
    }
    return constructRequestFirstLine(req.getMethod(),
                                     req.getRequestURI(),
                                     req.getQueryString(),
                                     req.getProtocol());
  }

  /**
   * Constructs the first line of an HTTP(S) request.
   *
   * @param method HTTP method. See {@link HttpServletRequest#getMethod()}.
   * @param path URL path. See {@link HttpServletRequest#getRequestURI()}. Note
   * that this must be the raw (still escaped) path.
   * @param queryString URL query string, or null. See {@link
   * HttpServletRequest#getQueryString()}.
   * @param protocol request protocol. See {@link
   * HttpServletRequest#getProtocol()}.
   * @return The first line of the request.
   */
  public static String constructRequestFirstLine(
      String method, String path, String queryString, String protocol) {
    StringBuffer b = new StringBuffer();
    b.append(method);
    b.append(" ");
    b.append(path);
    if (queryString != null) {
      b.append("?");
      b.append(queryString);
    }
    b.append(" ");
    b.append(protocol);
    return b.toString();
  }

  /**
   * Resolves the provided cookie domain argument based on the supplied host
   * argument. This makes it possible for applications to define the cookie
   * domain in form of ".example." and have it expanded acoording to the Host
   * header of the HTTP request at run time.
   * <p>
   * If the cookie domain does not end with a ".", then the cookie domain is
   * returned verbatim, unless the host is non-{@code null} and conflicts with
   * the cookie domain, in which case {@code null} is returned.
   * <p>
   * Otherwise, if the cookie domain ends with a ".", the substring of the host
   * starting at the cookie domain is returned, unless the host {@code null} or
   * the host conflicts with the cookie domain, in which case {@code null} is
   * returned.
   * <p>
   * For example, if the cookie domain is set to ".example.":
   * <pre>
   * HTTP/1.1 Host      Session cookie domain
   * www.example.co.uk   .example.co.uk
   * www.example.ca      .example.ca
   * foo.example.com     .example.com
   * bar.example.com    .example.com
   * </pre>

   * @param cookieDomain the domain for which the HTTP cookie is valid. If the
   * cookie domain ends with a ".", the substring of the host starting at the
   * cookie domain used instead. Otherwise, the cookie domain is used verbatim.
   * Must not be null.
   * @param host the HTTP/1.1 Host header value.
   * @return the resolved cookie domain, which may be {@code null}
   * @throws NullPointerException if {@code cookieDomain} is {@code null}
   */
  public static String resolveCookieDomain(String cookieDomain,
      String host) {

    if (cookieDomain.endsWith(".")) {
      if (host == null) {
        return null;
      }

      // Take the suffix from the user-supplied Host header
      int index = host.indexOf(cookieDomain);
      if (index == -1) {
        return null;
      } else {
        int colonIndex = host.indexOf(':', index);
        if (colonIndex == -1) {
          return host.substring(index);
        } else {
          return host.substring(index, colonIndex);
        }
      }
    } else {
      if (host != null && !host.endsWith(cookieDomain)) {
        // don't return a domain restriction that conflicts with the
        // host the browser is asking for
        return null;
      } else {
        return cookieDomain;
      }
    }
  }

  private HttpUtil() {}
}

