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

import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestAttributeEvent;
import java.util.List;
import java.util.ArrayList;

/**
 * @author jennings
 *         Date: Jun 22, 2008
 */
public class ServletRequestAttributeListenerList
    implements ServletRequestAttributeListener {

  private final List<ServletRequestAttributeListener> listeners;

  public ServletRequestAttributeListenerList() {
    listeners = new ArrayList<ServletRequestAttributeListener>();
  }

  public void add(ServletRequestAttributeListener listener) {
    listeners.add(listener);
  }

  public boolean isEmpty() {
    return listeners.isEmpty();
  }

  public void attributeAdded(ServletRequestAttributeEvent srae) {
    for (ServletRequestAttributeListener listener : listeners) {
      listener.attributeAdded(srae);
    }
  }

  public void attributeRemoved(ServletRequestAttributeEvent srae) {
    for (ServletRequestAttributeListener listener : listeners) {
      listener.attributeRemoved(srae);
    }
  }

  public void attributeReplaced(ServletRequestAttributeEvent srae) {
    for (ServletRequestAttributeListener listener : listeners) {
      listener.attributeReplaced(srae);
    }
  }
}
