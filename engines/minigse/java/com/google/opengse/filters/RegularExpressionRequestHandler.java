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

package com.google.opengse.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.net.URI;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Request handler which dispatches to other request handlers based on regular
 * expressions.
 *
 * @author Mike Jennings
 * @author Michael Guntsch
 */
public class RegularExpressionRequestHandler implements FilterChain {
  private static final String SERVLET_CONTEXT_ATTRIBUTE
      = ServletContext.class.getName();

  private static final String MATCH_EVERYTHING_STRING = ".*";
  static final Pattern MATCH_EVERYTHING_PATTERN
      = Pattern.compile(MATCH_EVERYTHING_STRING);

  private final SortedSet<Entry> entries;

  private RegularExpressionRequestHandler(SortedSet<Entry> entries) {
    this.entries = entries;
  }

  /**
   * Creates a RegularExpressionRequestHandler given a default request handler.
   */
  public static RegularExpressionRequestHandler create(
      FilterChain defaultHandler) {
    return create(defaultHandler, new TreeSet<Entry>());
  }

  static RegularExpressionRequestHandler create(FilterChain defaultHandler,
      SortedSet<Entry> entries) {
    RegularExpressionRequestHandler handler
        = new RegularExpressionRequestHandler(entries);
    handler.setHandler(MATCH_EVERYTHING_PATTERN, defaultHandler);
    return handler;
  }

  /**
   * Creates a mapping from a regular expression to a filter.
   *
   * <p>Note that if a regular expression has capturing groups then when it
   * matches the request's path info
   * (see {@link javax.servlet.http.HttpServletRequest#getPathInfo()})
   * will be set to the value of the match's first capturing group.
   */
  boolean setHandler(Pattern pattern, FilterChain handler) {
    return entries.add(new Entry(pattern, handler));
  }

  /**
   * Convenience method which calls
   * <code>setHandler(Pattern.compile(regexp), handler)</code>.
   */
  public boolean setHandler(String regexp, FilterChain handler) {
    return setHandler(Pattern.compile(regexp), handler);
  }

  /**
   * Replace the default handler for this
   * @param handler
   */
  public void replaceDefaultHandler(FilterChain handler) {
    Entry entry = new Entry(MATCH_EVERYTHING_PATTERN, handler);
    entries.remove(entry);
    entries.add(entry);
  }

  /**
   * Gets the handler for a given servlet path.
   * @param requestURI the URI of the request
   * @return
   */
  public FilterChain getHandler(URI requestURI) {
    String path = requestURI.getPath();
    for (Entry entry : entries) {
      Matcher matcher = entry.getPattern().matcher(path);
      if (matcher.matches()) {
        return entry.getHandler();
      }
    }
    throw new IllegalStateException("Found no matching pattern for " + path);
  }

  /**
   * TODO: this won't scale for a large web.xml.
   *
   * {@inheritDoc}
   */
  public void doFilter(ServletRequest req,
      ServletResponse response) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    // TODO(jennings): get a requestURI from the request and use getHandler() above
    String path = request.getServletPath();
    for (Entry entry : entries) {
      HttpServletRequest wrappedRequest = matchingRequest(path, request, entry);
      if (wrappedRequest != null) {
        entry.getHandler().doFilter(wrappedRequest, response);
        return;
      }
    }
    throw new IllegalStateException("Found no matching pattern for " + path);
  }

  /**
   * Returns null if path does not match entry, a (possibly) wrapped request
   * if it does.
   */
  private static HttpServletRequest matchingRequest(
      String path, HttpServletRequest request, Entry entry) {
    Pattern pattern = entry.getPattern();
    Matcher matcher = pattern.matcher(path);
    if (!matcher.matches()) {
      if (path.endsWith("/")) {
        return null;
      } else {
        return matchingRequest(path + "/", request, entry);
      }
    }
    if (matcher.groupCount() > 0) {
      return new PathInfoRequest(request, matcher.group(1));
    }
    String regex = pattern.toString();
    if (regex.endsWith(MATCH_EVERYTHING_STRING)) {
      int end = regex.length() - MATCH_EVERYTHING_STRING.length() - 1;
      if (end <= 0) {
        // for global wildcard match, servletPath="" and pathInfo=path
        return new PathInfoRequest(request, "", path);
      }
      String prefix = regex.substring(0, end);
      String pathInfo = path.substring(prefix.length());
      if (pathInfo.equals("/")) {
        return request;
      }
      return new PathInfoRequest(request, prefix, pathInfo);
    }
    return request;
  }

  static class PathInfoRequest extends HttpServletRequestWrapper {
    private final String servletPath;
    private final String pathInfo;

    PathInfoRequest(HttpServletRequest request, String servletPath, String pathInfo) {
      super(request);
      this.servletPath = servletPath;
      this.pathInfo = pathInfo;
    }

    PathInfoRequest(HttpServletRequest request, String pathInfo) {
      this(request, null, pathInfo);
    }

    @Override
    public String getPathTranslated() {
      ServletContext context
          = (ServletContext) getAttribute(SERVLET_CONTEXT_ATTRIBUTE);
      if (context == null) {
        return null;
      }
      if (pathInfo == null) {
        return null;
      }
      return context.getRealPath(pathInfo);
    }

    @Override
    public String getServletPath() {
      return (servletPath == null) ? super.getServletPath() : servletPath;
    }

    @Override
    public String getPathInfo() {
      return pathInfo;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof PathInfoRequest) {
        return ((PathInfoRequest) obj).pathInfo.equals(pathInfo);
      }
      return false;
    }

    @Override
    public int hashCode() {
      throw new UnsupportedOperationException(
          "I was never designed to be in a container!");
    }
  }

  static class Entry implements Comparable<Entry> {

    private final Pattern pattern;
    private final FilterChain handler;

    Entry(Pattern pattern, FilterChain handler) {
      this.pattern = pattern;
      this.handler = handler;
    }

    private int size() {
      return (MATCH_EVERYTHING_STRING.equals(pattern.toString()))
          ? -1 : pattern.toString().length();
    }

    /**
     * This sorts in reverse order, i.e. if this Entry's size is smaller, put it
     * behind the other Entry.
     * {@inheritDoc}
     */
    public int compareTo(Entry other) {
      if (this.equals(other)) {
        return 0;
      }
      return (this.size() > other.size()) ? -1 : +1;
    }

    @Override
    public boolean equals(Object obj) {
      try {
        Entry other = (Entry) obj;
        return this.pattern.toString().equals(other.pattern.toString());
      } catch (ClassCastException e) {
        return false;
      }
    }

    @Override
    public int hashCode() {
      return pattern.toString().hashCode();
    }

    public Pattern getPattern() {
      return pattern;
    }

    public FilterChain getHandler() {
      return handler;
    }
  }
}
