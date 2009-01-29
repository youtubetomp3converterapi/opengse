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

import com.google.opengse.configuration.WebAppSecurityConstraint;
import com.google.opengse.configuration.WebAppWebResourceCollection;
import com.google.opengse.configuration.WebAppAuthConstraint;
import com.google.opengse.configuration.WebAppUserDataConstraint;

import java.util.List;
import java.util.ArrayList;

/**
 * A mutable implementation of WebAppSecurityConstraint.
*
* @author Mike Jennings
*/
public final class MutableWebAppSecurityConstraint
    implements WebAppSecurityConstraint {

  private String displayName;
  private final List<WebAppWebResourceCollection> wrcollections;
  private WebAppAuthConstraint authConstraint;
  private WebAppUserDataConstraint userDataConstraint;

  /*
   *  private constructor to force usage of create method. This
   * gives us the flexibility to return any subclass of
   * MutableWebAppSecurityConstraint in future if we need to.
   */
  private MutableWebAppSecurityConstraint() {
    wrcollections = new ArrayList<WebAppWebResourceCollection>();
  }

  public static MutableWebAppSecurityConstraint create() {
    return new MutableWebAppSecurityConstraint();
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public WebAppWebResourceCollection[] getWebResourceCollections() {
    WebAppWebResourceCollection[] empty = {};
    return wrcollections.toArray(empty);
  }

  public void addWebResourceCollection(WebAppWebResourceCollection wrc) {
    wrcollections.add(wrc);
  }

  public WebAppAuthConstraint getAuthConstraint() {
    return authConstraint;
  }

  public void setAuthConstraint(WebAppAuthConstraint authConstraint) {
    this.authConstraint = authConstraint;
  }

  public WebAppUserDataConstraint getUserDataConstraint() {
    return userDataConstraint;
  }

  public void setUserDataConstraint(WebAppUserDataConstraint userDataConstraint) {
    this.userDataConstraint = userDataConstraint;
  }
}
