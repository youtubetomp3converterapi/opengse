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

import com.google.opengse.configuration.WebAppLoginConfig;
import com.google.opengse.configuration.WebAppFormLoginConfig;

/**
 * A mutable implementation of WebAppLoginConfig.
*
* @author Mike Jennings
*/
public final class MutableWebAppLoginConfig implements WebAppLoginConfig {
  private String authMethod, realmName;
  private WebAppFormLoginConfig webAppFormloginConfig;

  private MutableWebAppLoginConfig() {
  }

  public static MutableWebAppLoginConfig create() {
    return new MutableWebAppLoginConfig();
  }

  public String getAuthMethod() {
    return authMethod;
  }

  public void setAuthMethod(String authMethod) {
    this.authMethod = authMethod;
  }

  public String getRealmName() {
    return realmName;
  }

  public void setRealmName(String realmName) {
    this.realmName = realmName;
  }

  public WebAppFormLoginConfig getFormLoginConfig() {
    return webAppFormloginConfig;
  }

  public void setFormLoginConfig(final WebAppFormLoginConfig loginConfig) {
    this.webAppFormloginConfig = loginConfig;
  }
}
