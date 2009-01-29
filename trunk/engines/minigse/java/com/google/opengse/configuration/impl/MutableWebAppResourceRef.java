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

import com.google.opengse.configuration.WebAppResourceRef;

/**
 * A mutable implementation of WebAppResourceRef.
*
* @author Mike Jennings
*/
public final class MutableWebAppResourceRef extends HasDescription implements
    WebAppResourceRef {
  private String resRefName;
  private String resType;
  private String resAuth;
  private String resSharingScope;

  private MutableWebAppResourceRef() {
  }

  public static MutableWebAppResourceRef create() {
    return new MutableWebAppResourceRef();
  }

  public String getResRefName() {
    return resRefName;
  }

  public void setResRefName(String resRefName) {
    this.resRefName = resRefName;
  }

  public String getResType() {
    return resType;
  }

  public void setResType(String resType) {
    this.resType = resType;
  }

  public String getResAuth() {
    return resAuth;
  }

  public void setResAuth(String resAuth) {
    this.resAuth = resAuth;
  }

  public String getResSharingScope() {
    return resSharingScope;
  }

  public void setResSharingScope(String resSharingScope) {
    this.resSharingScope = resSharingScope;
  }
}
