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

import com.google.opengse.util.IteratorEnumeration;

import java.util.Map;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;

/**
 * Exposes a string-to-string map as both a
 * <code>ServletConfig</code> and a <code>FilterConfig</code>
 *
 * @author Mike Jennings
 */
final class ServletOrFilterConfig implements ServletConfig, FilterConfig {

  private final Map<String, String> configMap;
  private final String name;
  private final ServletContext servletContext;

  /**
   * Models the configuration name/value pairs used by both servlets and
   * filters
   *
   * @param servletContext the servlet context
   * @param name           the name of either the servlet or the filter
   * @param configMap      the name/value pairs that the servlet or filter will
   *                       use for its configuration
   */
  ServletOrFilterConfig(ServletContext servletContext, String name,
      Map<String, String> configMap) {
    this.configMap = configMap;
    this.name = name;
    this.servletContext = servletContext;
  }

  /**
   * Returns the name of this servlet instance. The name may be provided via
   * server administration, assigned in the web application deployment
   * descriptor, or for an unregistered (and thus unnamed) servlet instance it
   * will be the servlet's class name.
   *
   * @return the name of the servlet instance
   */
  public String getServletName() {
    return name;
  }

  /**
   * Returns the filter-name of this filter as defined in the deployment
   * descriptor.
   */
  public String getFilterName() {
    return name;
  }

  /**
   * Returns a reference to the {@link javax.servlet.ServletContext} in which
   * the caller is executing.
   *
   * @return a {@link javax.servlet.ServletContext} object, used by the caller
   * to interact with its servlet container
   * @see javax.servlet.ServletContext
   */
  public ServletContext getServletContext() {
    return servletContext;
  }

  /**
   * Returns a <code>String</code> containing the value of the named
   * initialization parameter, or <code>null</code> if the parameter does not
   * exist.
   *
   * @param parameterName a <code>String</code> specifying the name of the
   *                      initialization parameter
   *
   * @return a <code>String</code> containing the value of the initialization
   * parameter
   */
  public String getInitParameter(String parameterName) {
    return configMap.get(parameterName);
  }


  /**
   * Returns the names of the servlet's initialization parameters as an
   * <code>Enumeration</code> of <code>String</code> objects, or an empty
   * <code>Enumeration</code> if the servlet has no initialization parameters.
   *
   * @return an <code>Enumeration</code> of <code>String</code> objects
   * containing the names of the servlet's initialization parameters
   */
  public Enumeration<String> getInitParameterNames() {
    // do NOT replace the following line with Iterators.asEnumeration
    return new IteratorEnumeration<String>(configMap.keySet().iterator());
  }
}
