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

import com.google.opengse.httputil.ContentType;
import com.google.opengse.httputil.Cookies;
import com.google.opengse.HeaderUtil;
import com.google.opengse.util.EmptyEnumeration;

import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;

/**
 * A request that is prefixed with a given URI prefix.
 *
 * @author Mike Jennings
 */
final class WebAppRequestWrapper extends HttpServletRequestWrapper {

  public static final String SERVLET_PATH_INCLUDE_ATTRIBUTE
      = "javax.servlet.include.servlet_path";
  private static final String SERVLET_CONTEXT_ATTRIBUTE
      = ServletContext.class.getName();

  private final String uriPrefix;
  private final int uriPrefixLength;
  private final WebApp webapp;
  private HttpSession session;
  private WebAppSessionWrapper wrappedSession;
  private final String requestURI;
  private final String servletPath;
  private ContentType ctype_;
  private String content_type_;
  private String char_encoding_;
  private List<Cookie> cookies_;

  WebAppRequestWrapper(HttpServletRequest request, WebApp webapp) {
    super(request);
    this.webapp = webapp;
    uriPrefix = webapp.getUriPrefix();
    uriPrefixLength = uriPrefix.length();
    requestURI = request.getRequestURI();
    // compute the servlet path from the request URI
    servletPath = requestURI.substring(uriPrefixLength);
  }

  @Override
  public RequestDispatcher getRequestDispatcher(String path) {
    return webapp.getContext().getRequestDispatcher(path);
  }

  @Override
  public String getServletPath() {
    return servletPath;
  }


  @Override
  public String getContextPath() {
    return uriPrefix;
  }


  @Override
  public String getContentType() {
    String hdr = getHeader("Content-Type");
    if (ctype_ == null) {
      ctype_ = ContentType.parse(hdr);
      content_type_ = ctype_.getType(null);
      // use the encoding specified in the content type only if not already set
      if (char_encoding_ == null) {
        char_encoding_ = ctype_.getParameter(
            "charset", null);
      }
    }
    return content_type_;
  }

  @Override
  public String getCharacterEncoding() {
    // WARNING: Do NOT call this function if the return value is used
    //          to set the encoding scheme of an i/o buffer or query string
    //          from the incoming request for decoding purpose.
    //          Use getInternalCharacterEncoding() instead.
    getContentType();
    return char_encoding_;
  }

  @Override
  public String getPathInfo() {
    return null;
  }

  @Override
  public Object getAttribute(String name) {
    if (SERVLET_CONTEXT_ATTRIBUTE.equals(name)) {
      return webapp.getContext();
    }
    return super.getAttribute(name);
  }

  @Override
  public String getAuthType() {
    return null;
  }

  @Override
  public String getRemoteUser() {
    return null;
  }

  private boolean sessionIsDifferent(HttpSession possiblyNewSession) {
    if (session == null) {
      /*
       * return true if new object is non-null,
       * false if null like existing session
       */
      return (possiblyNewSession != null);
    }
    if (possiblyNewSession == null) {
      return true; // new object is null, old object was non-null
    }
    return false; // might want to check session ids here...
  }

  @Override
  public HttpSession getSession(boolean create) {
    HttpSession possiblyNewSession = super.getSession(create);
    if (sessionIsDifferent(possiblyNewSession)) {
      session = possiblyNewSession;
      wrappedSession = new WebAppSessionWrapper(
          session,
          webapp.getContext(),
          webapp.getHttpSessionListener(),
          webapp.getHttpSessionAttributeListener());
    }
    return wrappedSession;
  }

  @Override
  public int getContentLength() {
    return getIntHeader("Content-Length");
  }


  @Override
  public long getDateHeader(String name) {
    return HeaderUtil.toDateHeaderLong(getHeader(name));
  }

  @Override
  public int getIntHeader(String name) {
    return HeaderUtil.toIntHeader(getHeader(name));
  }

  @Override
  public Enumeration<String> getHeaders(String name) {
    @SuppressWarnings("unchecked")
    Enumeration<String> headers = super.getHeaders(name);
    if (headers == null) {
      // return an empty enumeration if wrapped request gave us a null
      return EmptyEnumeration.please();
    }
    return new WebAppHeadersWrapper(headers);
  }

  @Override
  public HttpSession getSession() {
    return getSession(true);
  }

class WebAppHeadersWrapper implements Enumeration<String> {
  private final Enumeration<String> delegate;
  private int current, last;
  private String[] elements;

  WebAppHeadersWrapper(Enumeration<String> delegate) {
    this.delegate = delegate;
    current = last = 0;
  }

  public boolean hasMoreElements() {
    if (current < last) {
      return true;
    }
    return delegate.hasMoreElements();
  }

  public String nextElement() {
    if (current < last) {
      return elements[current++];
    }
    String element = delegate.nextElement();
    if (element == null) {
      return null;
    }
    StringTokenizer st = new StringTokenizer(element, ", ");
    last = st.countTokens();
    if (last == 0) {
      return null;
    }
    if (elements == null || elements.length < last) {
      elements = new String[last];
    }
    for (current = 0; current < last; ++current) {
      elements[current] = st.nextToken();
    }
    current = 0;
    return nextElement();
  }
}
}