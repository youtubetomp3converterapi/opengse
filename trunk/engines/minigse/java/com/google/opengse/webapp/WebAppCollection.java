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

import javax.servlet.ServletException;
import javax.servlet.FilterChain;

/**
 * A collection of WebApp objects.
 */
public interface WebAppCollection extends FilterChain {

  /**
   * Get the servlet container context for this collection.
   *
   * @return
   */
  ServletContainerContext getContainerContext();

  /**
   * Get the named attribute from this collection.
   * @param name
   * @return
   */
  Object getAttribute(String name);

  /**
   * Add a web application to the collection of web applications. Note: If
   * another webapp has been added with the same URI prefix, this method will
   * throw an exception.
   */
  void addWebApp(WebApp webapp) throws WebAppConfigurationException;

  /**
   * Returns the webapp with the given uri prefix.
   *
   * @param uriPrefix the prefix to check.
   * @return the webapp with the given uri prefix, or null if there isn't one
   */
  WebApp getWebApp(String uriPrefix);

 /**
   * Starts all of the registered webapps.
   */
  void startAll() throws ServletException;

  /**
   * Stops all of the webapps which have been started.
   */
  void stopAll();
}
