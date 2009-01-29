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

import com.google.opengse.configuration.WebAppServletMapping;

/**
 * A mutable implementation of WebAppServletMapping.
*
* @author Mike Jennings
*/
public final class MutableWebAppServletMapping implements WebAppServletMapping {
  private String servletName;
  private String urlPattern;

  private MutableWebAppServletMapping() {
  }

  public static MutableWebAppServletMapping create() {
    return new MutableWebAppServletMapping();
  }

  public String getServletName() {
    return servletName;
  }

  public void setServletName(String servletName) {
    this.servletName = servletName;
  }

  public String getUrlPattern() {
    return urlPattern;
  }

  public void setUrlPattern(String urlPattern) {
    this.urlPattern = urlPattern;
  }
}
