// Copyright 2008 Google Inc. All Rights Reserved.
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

package com.google.opengse.webapp.listeners;

import java.util.List;
import java.util.ArrayList;

import javax.servlet.ServletRequestListener;
import javax.servlet.ServletRequestEvent;

/**
 * A list of ServletRequestListeners
 *
 * @author Mike Jennings
 */
public class ServletRequestListenerList implements ServletRequestListener {

  private final List<ServletRequestListener> listeners;

  public ServletRequestListenerList() {
    listeners = new ArrayList<ServletRequestListener>();
  }

  public void add(ServletRequestListener listener) {
    listeners.add(listener);
  }

  public void requestDestroyed(ServletRequestEvent sre) {
    for (ServletRequestListener listener : listeners) {
      listener.requestDestroyed(sre);
    }
  }

  public void requestInitialized(ServletRequestEvent sre) {
    for (ServletRequestListener listener : listeners) {
      listener.requestInitialized(sre);
    }
  }
}
