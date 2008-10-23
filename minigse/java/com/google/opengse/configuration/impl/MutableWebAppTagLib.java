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

import com.google.opengse.configuration.WebAppTagLib;

/**
 * A mutable implementation of WebAppTagLib.
*
* @author Mike Jennings
*/
public final class MutableWebAppTagLib implements WebAppTagLib {
  private String taglibUri;
  private String taglibLocation;

  private MutableWebAppTagLib() {
  }

  public static MutableWebAppTagLib create() {
    return new MutableWebAppTagLib();
  }

  public String getTaglibUri() {
    return taglibUri;
  }

  public void setTaglibUri(String taglibUri) {
    this.taglibUri = taglibUri;
  }

  public String getTaglibLocation() {
    return taglibLocation;
  }

  public void setTaglibLocation(String taglibLocation) {
    this.taglibLocation = taglibLocation;
  }
}
