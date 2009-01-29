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

import com.google.opengse.configuration.WebAppResourceEnvRef;

/**
 * A mutable implementation of WebAppResourceEnvRef.
*
* @author Mike Jennings
*/
public final class MutableWebAppResourceEnvRef extends HasDescription
    implements WebAppResourceEnvRef {
  private String envRefName;
  private String envRefType;
  private String envRefAuth;

  private MutableWebAppResourceEnvRef() {
    super();
  }

  public static MutableWebAppResourceEnvRef create() {
    return new MutableWebAppResourceEnvRef();
  }

  public String getEnvRefName() {
    return envRefName;
  }

  public void setEnvRefName(final String envRefName) {
    this.envRefName = envRefName;
  }

  public String getEnvRefType() {
    return envRefType;
  }

  public void setEnvRefType(final String envRefType) {
    this.envRefType = envRefType;
  }

  public String getEnvRefAuth() {
    return envRefAuth;
  }

  public void setEnvRefAuth(final String envRefAuth) {
    this.envRefAuth = envRefAuth;
  }

}
