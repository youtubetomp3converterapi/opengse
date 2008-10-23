// Copyright 2003 Google Inc. All Rights Reserved.
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

package com.google.opengse.configuration;

/**
 * @author Mike Jennings
 */
public interface WebAppServlet {
  WebAppIcon getIcon();
  String getServletName();
  String getDisplayName();
  String getDescription();
  String getServletClass();
  String getJspFile();
  WebAppInitParam[] getInitParams();

  /**
   * The load-on-startup element indicates that this servlet should be loaded
   * (instantiated and have its init() called) on the startup of the web
   * application. The optional contents of these element must be an integer
   * indicating the order in which the servlet should be loaded. If the value is
   * a negative integer, or the element is not present, the container is free to
   * load the servlet whenever it chooses. If the value is a positive integer or
   * 0, the container must load and initialize the servlet as the application is
   * deployed. The container must guarantee that servlets marked with lower
   * integers are loaded before servlets marked with higher integers. The
   * container may choose the order of loading of servlets with the same
   * load-on-start-up value.
   *
   * @return the load-on-startup value
   */
  Integer getLoadOnStartup();

  WebAppRunAs getRunAs();
  WebAppSecurityRoleRef[] getSecurityRoleRefs();
}
