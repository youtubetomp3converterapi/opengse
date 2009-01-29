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

import java.io.File;

import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;

/**
 * Represents a web application.
 *
 * @author Mike Jennings
 */
public interface WebApp extends FilterChain {

  /**
   * Get the URI prefix for this web application.
   *
   * @return
   */
  String getUriPrefix();

  /**
   * Starts this web application.
   *
   * @throws ServletException
   */
  void start() throws ServletException;

  /**
   * Returns true if this web application has been started.
   *
   * @return
   */
  boolean isStarted();

  /**
   * Stops this web application.
   */
  void stop();

  /**
   * Returns a <code>ServletContext</code> object that corresponds to a
   * specified URL on the server.
   *
   * <p>This method allows servlets to gain access to the context for various
   * parts of the server, and as needed obtain
   * {@link javax.servlet.RequestDispatcher}
   * objects from the context. The given path must be begin with "/", is
   * interpreted relative to the server's document root and is matched against
   * the context roots of other web applications hosted on this container.
   *
   * <p>In a security conscious environment, the servlet container may return
   * <code>null</code> for a given URL.
   *
   * @return
   * the <code>ServletContext</code> object that corresponds to the
   * named URL, or null if either none exists or the container wishes to
   * restrict this access.
   * @see javax.servlet.RequestDispatcher
   */
  ServletContext getContext();

  /**
   * Get the attribute associated with this web application.
   * @param name
   * @return
   */
  Object getAttribute(String name);

  /**
   * Gets the context base of this webapp
   * ie. the directory where this webapp has been deployed.
   * @return
   */
  File getContextBase();

  /**
   * Get the HttpSessionAttributeListener for this webapp
   * @return
   */
  HttpSessionAttributeListener getHttpSessionAttributeListener();

  /**
   * Get the HttpSessionListener for this webapp
   * @return
   */
  HttpSessionListener getHttpSessionListener();
}
