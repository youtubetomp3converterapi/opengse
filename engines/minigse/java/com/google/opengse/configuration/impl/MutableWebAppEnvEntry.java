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

import com.google.opengse.configuration.WebAppEnvEntry;

/**
 * A mutable implementation of WebAppEnvEntry.
*
* @author Mike Jennings
*/
public final class MutableWebAppEnvEntry extends HasDescription
    implements WebAppEnvEntry {
  private String envEntryName;
  private String envEntryValue;
  private String envEntryType;

  private MutableWebAppEnvEntry() {
    super();
  }

  public static MutableWebAppEnvEntry create() {
    return new MutableWebAppEnvEntry();
  }

  public String getEnvEntryName() {
    return envEntryName;
  }

  public void setEnvEntryName(final String envEntryName) {
    this.envEntryName = envEntryName;
  }

  public String getEnvEntryValue() {
    return envEntryValue;
  }

  public void setEnvEntryValue(final String envEntryValue) {
    this.envEntryValue = envEntryValue;
  }

  public String getEnvEntryType() {
    return envEntryType;
  }

  public void setEnvEntryType(final String envEntryType) {
    this.envEntryType = envEntryType;
  }
}
