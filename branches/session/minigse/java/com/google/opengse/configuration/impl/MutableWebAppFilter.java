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

import com.google.opengse.configuration.WebAppFilter;
import com.google.opengse.configuration.WebAppIcon;
import com.google.opengse.configuration.WebAppInitParam;

import java.util.ArrayList;
import java.util.List;

/**
 * A mutable version of WebAppFilter.
 *
 * @author Mike Jennings
 */
public final class MutableWebAppFilter extends HasDescription
    implements WebAppFilter {

  private WebAppIcon icon;
  private String filterName;
  private String displayName;
  private String filterClass;
  private String servletName;
  private final List<WebAppInitParam> initParams;

  private MutableWebAppFilter() {
    super();
    initParams = new ArrayList<WebAppInitParam>();
  }

  public static MutableWebAppFilter create() {
    return new MutableWebAppFilter();
  }

  public WebAppIcon getIcon() {
    return icon;
  }

  public void setIcon(final WebAppIcon icon) {
    this.icon = icon;
  }

  public void setFilterName(final String filterName) {
    this.filterName = filterName;
  }

  public String getFilterName() {
    return filterName;
  }

  public void setDisplayName(final String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setFilterClass(final String filterClass) {
    this.filterClass = filterClass;
  }

  public String getFilterClass() {
    return filterClass;
  }

  public void setServletName(final String servletName) {
    this.servletName = servletName;
  }

  public String getServletName() {
    return servletName;
  }

  public WebAppInitParam[] getInitParams() {
    return initParams.toArray(new WebAppInitParam[initParams.size()]);
  }

  public void addInitParam(final WebAppInitParam param) {
    initParams.add(param);
  }
}
