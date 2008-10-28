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
package com.google.opengse.session.impl2;

import com.google.opengse.session.SessionProvider;

import java.io.Serializable;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

/**
 * @author jennings
 *         Date: Sep 19, 2008
 */
public class SessionProviderImpl implements SessionProvider {
  private Map<String, Map<String, Serializable> > idToSessionObjects;

  public SessionProviderImpl() {
    idToSessionObjects
        = Collections.synchronizedMap(
               new HashMap<String, Map<String, Serializable> >());
  }

  public Map<String, Serializable> getSessionObjects(String id) throws IOException {
    return idToSessionObjects.get(id);
  }

  public boolean isSessionValid(String id) {
    return idToSessionObjects.containsKey(id);
  }

  public void saveSessionObjects(String id, Map<String, Serializable> sessionObjects) throws IOException {
    idToSessionObjects.put(id, sessionObjects);
  }

  public void removeSession(String id) throws IOException {
    idToSessionObjects.remove(id);
  }
}
