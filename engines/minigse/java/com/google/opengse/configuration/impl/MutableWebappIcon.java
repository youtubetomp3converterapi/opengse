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

import com.google.opengse.configuration.WebAppIcon;

/**
 * A mutable implementation of WebAppIcon.
*
* @author Mike Jennings
*/
public final class MutableWebappIcon implements WebAppIcon {
  private String largeIcon, smallIcon;

  private MutableWebappIcon() {
  }

  public static MutableWebappIcon create() {
    return new MutableWebappIcon();
  }

  public String getLargeIcon() {
    return largeIcon;
  }

  public void setLargeIcon(String largeIcon) {
    this.largeIcon = largeIcon;
  }

  public String getSmallIcon() {
    return smallIcon;
  }

  public void setSmallIcon(String smallIcon) {
    this.smallIcon = smallIcon;
  }
}
