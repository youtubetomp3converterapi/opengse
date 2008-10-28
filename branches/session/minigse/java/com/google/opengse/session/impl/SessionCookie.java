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

package com.google.opengse.session.impl;

import java.util.Map;
import java.util.HashMap;

/**
 * Represents a session cookie.
 * The session cookie typically has the following
 * form:
 * <servertype>=<id>[:<servertype>=<id>[:...]]
 *
 * @author Spencer Kimball
 */
public class SessionCookie {
  boolean is_session_id_from_cookie_;
  String requested_session_id_;
  String  serverType_;
  final Map<String, String> serverTypes_ = new HashMap<String, String>();

  public boolean isSessionIdFromCookie() {
    return is_session_id_from_cookie_;
  }

  public String getRequestedSessionId() {
    return requested_session_id_;
  }
  

  String getId(String servertype) {
    return serverTypes_.get(servertype);
  }

  /**
   * This method is used to change the contents of the session
   * cookie after it has been parsed. Then the {@code
   * toString()} method can be invoked to output a new cookie
   * value.
   */
  public void setId(String servertype, String id) {
    // only update the array if id is different
    String idForServer = serverTypes_.get(servertype);
    if (!id.equals(idForServer)) {
      serverTypes_.put(servertype, id);
    }
  }

  /**
   * This method returns a string representation of the session
   * cookie that in turn is parsable by this class.
   */
  @Override
  public String toString() {
    StringBuffer buf = new StringBuffer();

    for (String serverType : serverTypes_.keySet()) {
      String id = serverTypes_.get(serverType);
      if (buf.length() != 0) {
        buf.append(":");
      }
      buf.append(serverType);
      buf.append("=");
      buf.append(id);
    }

    return buf.toString();
  }
}

