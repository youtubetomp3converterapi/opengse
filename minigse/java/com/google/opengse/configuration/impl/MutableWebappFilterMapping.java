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

import com.google.opengse.configuration.WebAppFilterMapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A mutable implementation of WebAppFilterMapping.
 *
 * This class is not thread-safe and the collection values are returned
 * directly by reference too.
*
* @author Mike Jennings
*/
public final class MutableWebappFilterMapping implements WebAppFilterMapping {
  private final static String[] EMPTY_STRING_ARRAY = new String[0];
  private String filterName;
  private List<String> urlPatterns;
  private List<String> servletNames;
  private List<String> dispatchers;

  private MutableWebappFilterMapping() {
    urlPatterns = new ArrayList<String>();
    servletNames = new ArrayList<String>();
    dispatchers = new ArrayList<String>();
  }

  public static MutableWebappFilterMapping create() {
    return new MutableWebappFilterMapping();
  }

  public String getFilterName() {
    return filterName;
  }

  public String[] getUrlPatterns() {
    return toArray(urlPatterns);
  }

  public String[] getServletNames() {
    return toArray(servletNames);
  }

  public void setServletName(String servletName) {
    servletNames.add(servletName);
  }

  public String[] getDispatchers() {
    return toArray(dispatchers);
  }

  // convenience method for turning a list of strings into an array of strings
  private static String[] toArray(List<String> list) {
    if (list == null || list.isEmpty()) {
      return EMPTY_STRING_ARRAY;
    }
    String[] array = new String[list.size()];
    list.toArray(array);
    return array;
  }

// The following methods are discovered by reflection. Do not remove!  
  public void setDispatcher(String dispatcher) {
    dispatchers.add(dispatcher);
  }

  public void setUrlPattern(String urlPattern) {
    urlPatterns.add(urlPattern);
  }

  public void setFilterName(String filterName) {
    this.filterName = filterName;
  }


}
