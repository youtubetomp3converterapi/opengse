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

package com.google.opengse.configuration.impl;

import com.google.opengse.configuration.WebAppServlet;
import com.google.opengse.configuration.WebAppIcon;
import com.google.opengse.configuration.WebAppInitParam;
import com.google.opengse.configuration.WebAppRunAs;
import com.google.opengse.configuration.WebAppSecurityRoleRef;

import java.util.List;
import java.util.ArrayList;

/**
 * A mutable implementation of WebAppServlet.
*
* @author Mike Jennings
*/
public final class MutableWebAppServlet extends HasDescription
    implements WebAppServlet {
  private WebAppIcon icon;
  private String servletName;
  private String displayName;
  private String servletClass;
  private String jspFile;
  private final List<WebAppInitParam> initParams;
  private Integer loadOnStartup;
  private WebAppRunAs runAs;
  private final List<WebAppSecurityRoleRef> roleRefs;

  private MutableWebAppServlet() {
    super();
    roleRefs = new ArrayList<WebAppSecurityRoleRef>();
    initParams = new ArrayList<WebAppInitParam>();
  }

  public static MutableWebAppServlet create() {
    return new MutableWebAppServlet();
  }

  public WebAppIcon getIcon() {
    return icon;
  }

  public void setIcon(final WebAppIcon icon) {
    this.icon = icon;
  }

  public String getServletName() {
    return servletName;
  }

  public void setServletName(final String servletName) {
    this.servletName = servletName;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(final String displayName) {
    this.displayName = displayName;
  }

  public String getServletClass() {
    return servletClass;
  }

  public void setServletClass(final String servletClass) {
    this.servletClass = (servletClass == null) ? null : servletClass.trim();
  }

  public String getJspFile() {
    return jspFile;
  }

  public void setJspFile(final String jspFile) {
    this.jspFile = jspFile;
  }

  public WebAppInitParam[] getInitParams() {
    return initParams.toArray(new WebAppInitParam[initParams.size()]);
  }

  public void addInitParam(final WebAppInitParam param) {
    initParams.add(param);
  }

  /**
   * The load-on-startup element indicates that this servlet should be
   * loaded (instantiated and have its init() called) on the startup
   * of the web application. The optional contents of
   * these element must be an integer indicating the order in which
   * the servlet should be loaded. If the value is a negative integer,
   * or the element is not present, the container is free to load the
   * servlet whenever it chooses. If the value is a positive integer
   * or 0, the container must load and initialize the servlet as the
   * application is deployed. The container must guarantee that
   * servlets marked with lower integers are loaded before servlets
   * marked with higher integers. The container may choose the order
   * of loading of servlets with the same load-on-start-up value.
   *
   * @return the load-on-startup value
   */
  public Integer getLoadOnStartup() {
    return loadOnStartup;
  }

  public void setLoadOnStartup(final String loadOnStartup) {
    this.loadOnStartup = Integer.valueOf(loadOnStartup);
  }

  public WebAppRunAs getRunAs() {
    return runAs;
  }

  public void setRunAs(final WebAppRunAs runAs) {
    this.runAs = runAs;
  }

  public WebAppSecurityRoleRef[] getSecurityRoleRefs() {
    return roleRefs.toArray(new WebAppSecurityRoleRef[roleRefs.size()]);
  }

  public void addSecurityRoleRef(final WebAppSecurityRoleRef roleref) {
    roleRefs.add(roleref);
  }
}
