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

import com.google.opengse.configuration.WebAppFilter;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * Bookkeeping for filters.
 *
 * Note that multiple URL patterns may trigger the same filter.
 *
 * @author Mike Jennings
 */
class FilterInfo {
  private final WebAppFilter filterDef;
  private final Filter filter;
  boolean initialized;
  private ServletContext context;
  @SuppressWarnings("unused")  
  private Throwable initException;

  FilterInfo(WebAppFilter filterDef, Filter filter) {
    this.filterDef = filterDef;
    this.filter = filter;
  }

  Filter getFilter() {
    return filter;
  }

  void initialize(final ServletContext servletContext) {
    this.context = servletContext;
    try {
      initialize();
    } catch (ServletException e) {
      initException = e;
    }
  }

  void initialize()
      throws ServletException {
    filter.init(ServletManager.createFilterConfig(context, filterDef));
    initialized = true;
  }
}
