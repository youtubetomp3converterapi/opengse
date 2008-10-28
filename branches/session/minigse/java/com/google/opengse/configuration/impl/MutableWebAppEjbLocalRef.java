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

import com.google.opengse.configuration.WebAppEjbLocalRef;

/**
 * A mutable implementation of WebAppEjbLocalRef.
*
* @author Mike Jennings
*/
public final class MutableWebAppEjbLocalRef extends HasDescription
    implements WebAppEjbLocalRef {
  private String ejbLink;
  private String ejbRefName;
  private String ejbRefType;
  private String localHome;
  private String local;

  private MutableWebAppEjbLocalRef() {
    super();
  }

  public static MutableWebAppEjbLocalRef create() {
    return new MutableWebAppEjbLocalRef();
  }

  public String getEjbRefName() {
    return ejbRefName;
  }

  public void setEjbRefName(final String ejbRefName) {
    this.ejbRefName = ejbRefName;
  }

  public String getEjbRefType() {
    return ejbRefType;
  }

  public void setEjbRefType(final String ejbRefType) {
    this.ejbRefType = ejbRefType;
  }

  public String getLocalHome() {
    return localHome;
  }

  public void setLocalHome(final String localHome) {
    this.localHome = localHome;
  }

  public String getLocal() {
    return local;
  }

  public void setLocal(final String local) {
    this.local = local;
  }

  public String getEjbLink() {
    return ejbLink;
  }

  public void setEjbLink(final String ejbLink) {
    this.ejbLink = ejbLink;
  }
}
