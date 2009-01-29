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

import com.google.opengse.configuration.WebAppListener;

/**
 * A mutable implementation of WebAppListener.
*
* @author Mike Jennings
*/
public final class MutableWebAppListener implements WebAppListener {
  private String listenerClass;

  private MutableWebAppListener() {
  }

  public static MutableWebAppListener create() {
    return new MutableWebAppListener();
  }

  public String getListenerClass() {
    return listenerClass;
  }
  public void setListenerClass(String listenerClass) {
    this.listenerClass = (listenerClass == null) ? null : listenerClass.trim();
  }
}
