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

package com.google.opengse;

import com.google.opengse.util.IteratorEnumeration;
import com.google.opengse.httputil.Cookies;

import java.util.*;
import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.io.BufferedReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;

/**
 * A class which adapts HttpRequest to HttpServletRequest
 *
 * @author Mike Jennings
 */
public class HttpServletRequestAdapter
    extends HttpServletRequestUnsupported {

  private static ConnectionInformation nullConnectInfo
      = new NullConnectionInformation();
  private final HttpRequest delegate;
  private final ConnectionInformation connectInfo;
  private Map<String, Object> attributes_ = null;
  private ArrayList<Cookie> cookies_;
  

  public HttpServletRequestAdapter(final HttpRequest delegate) {
    super();
    this.delegate = delegate;
    final ConnectionInformation ci = delegate.getConnectionInformation();
    if (ci == null) {
      connectInfo = nullConnectInfo;
    } else {
      connectInfo = ci;
    }
  }

  @Override
  public Cookie[] getCookies() {
    if (cookies_ == null) {
      @SuppressWarnings("unchecked")
      Enumeration<String> enumeration = getHeaders("Cookie");
      cookies_ = Cookies.parse(enumeration);
      if (cookies_ == null) {
        return null;
      }
    }
    return cookies_.toArray(new Cookie[0]);
  }

  @Override
  public String getHeader(final String name) {
    return delegate.getHeader(name);
  }

  @Override
  public Enumeration<String> getHeaders(final String name) {
    return delegate.getHeaders(name);
  }

  @Override
  public Enumeration<String> getHeaderNames() {
    return delegate.getHeaderNames();
  }

  @Override
  public String getMethod() {
    return delegate.getMethod();
  }

  @Override
  public String getPathTranslated() {
    return delegate.getPathTranslated();
  }

  @Override
  public String getQueryString() {
    return delegate.getQueryString();
  }

  @Override
  public String getRequestURI() {
    return delegate.getRequestURI();
  }

  @Override
  public StringBuffer getRequestURL() {
    return delegate.getRequestURL();
  }

  private Map<String, Object> getAttributes() {
    if (attributes_ == null) {
      attributes_ = new HashMap<String, Object>();
    }
    return attributes_;
  }


  @Override
  public Object getAttribute(final String name) {
    return getAttributes().get(name);
  }

  @Override
  public Enumeration<String> getAttributeNames() {
    return new IteratorEnumeration<String>(getAttributes().keySet().iterator());
  }

  @Override
  public void setCharacterEncoding(final String env)
      throws UnsupportedEncodingException {
    delegate.setCharacterEncoding(env);
  }

  @Override
  public ServletInputStream getInputStream() throws IOException {
    return delegate.getInputStream();
  }

  @Override
  public String getParameter(final String name) {
    final String[] vals = delegate.getParameterMap().get(name);
    if (vals == null) {
      return null;
    }
    return vals[0];
  }

  @Override
  public Enumeration<String> getParameterNames() {
    return new IteratorEnumeration<String>(
        delegate.getParameterMap().keySet().iterator());
  }

  @Override
  public String[] getParameterValues(final String name) {
    return delegate.getParameterMap().get(name);
  }

  @Override
  public Map<String, String[]> getParameterMap() {
    return delegate.getParameterMap();
  }

  @Override
  public String getProtocol() {
    return delegate.getProtocol();
  }

  @Override
  public String getScheme() {
    return delegate.getScheme();
  }

  @Override
  public String getServerName() {
    return connectInfo.getServerName();
  }

  @Override
  public int getServerPort() {
    return connectInfo.getServerPort();
  }

  @Override
  public BufferedReader getReader() throws IOException {
    return delegate.getReader();
  }

  @Override
  public String getRemoteAddr() {
    return connectInfo.getRemoteAddr();
  }

  @Override
  public String getRemoteHost() {
    return connectInfo.getRemoteHost();
  }

  @Override
  public void setAttribute(final String name, final Object value) {
    final Map<String, Object> attributes = getAttributes();
    if (value == null) {
      attributes.remove(name);
    } else {
      attributes.put(name, value);
    }
  }

  @Override
  public void removeAttribute(final String name) {
    getAttributes().remove(name);
  }

  @Override
  public Locale getLocale() {
    return delegate.getLocale();
  }

  @Override
  public Enumeration<Locale> getLocales() {
    return delegate.getLocales();
  }

  @Override
  public boolean isSecure() {
    return connectInfo.isSecure();
  }

  @Override
  public int getRemotePort() {
    return connectInfo.getRemotePort();
  }

  @Override
  public String getLocalName() {
    return connectInfo.getLocalName();
  }

  @Override
  public String getLocalAddr() {
    return connectInfo.getLocalAddr();
  }

  @Override
  public int getLocalPort() {
    return connectInfo.getLocalPort();
  }

  private static class NullConnectionInformation
      implements ConnectionInformation {

    public String getServerName() {
      return null;
    }

    public int getServerPort() {
      return -1;
    }

    public String getRemoteAddr() {
      return null;
    }

    public String getRemoteHost() {
      return null;
    }

    public boolean isSecure() {
      return false;
    }

    public int getRemotePort() {
      return 0;
    }

    public String getLocalName() {
      return null;
    }

    public String getLocalAddr() {
      return null;
    }

    public int getLocalPort() {
      return 0;
    }
  }
}
