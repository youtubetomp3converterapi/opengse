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

package com.google.opengse.session;

import java.io.Serializable;
import java.io.IOException;
import java.util.Map;

/**
 * @author jennings
 *         Date: Sep 19, 2008
 */
public interface SessionProvider {
  /**
   * Retrieve the session objects corresponding to the given session id
   * @param id
   * @return null if no session objects were found which correspond to the
   * supplied id
   * @throws IOException if the provider couldn't fetch the session objects from
   * wherever they were stored.
   */
  Map<String, Serializable> getSessionObjects(String id) throws IOException;

  /**
   * Returns true if the given session id is valid.
   * @param id the session id
   * @return
   */
  boolean isSessionValid(String id);


  /**
   * Save the given session objects with the corresponding id.
   * @param id
   * @param sessionObjects
   * @throws IOException if the provider could not store the session objects
   */
  void saveSessionObjects(String id, Map<String, Serializable> sessionObjects)
      throws IOException;

  /**
   * Removes the given session from whereever it is stored by the provider.
   * @param id
   * @throws IOException
   */
  void removeSession(String id) throws IOException;

}
