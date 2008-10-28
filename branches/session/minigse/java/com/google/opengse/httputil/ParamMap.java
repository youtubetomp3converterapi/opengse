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

package com.google.opengse.httputil;

import java.util.Map;
import java.util.HashMap;

/**
 * Utility data structure for maintaining URL/CGI params.
 *
 * @author Harry Heymann
 */
public final class ParamMap {
  private final Map<String, String[]> map = new HashMap<String, String[]>();

  /**
   * Get list of values associated with name
   */
  public String[] get(final String name) {
    return map.get(name);
  }

  /**
   * Currently used only by unit tests.
   */
  public boolean containsKey(final String name) {
    return map.containsKey(name);
  }

  /**
   * Add a new value for name
   */
  public void append(final String name, final String value) {
    String[] values = map.get(name);
    if (values == null) {
      values = new String[1];
    } else {
      // the next 3 lines of code shouldn't be called that often
      // typically parameters are mapped to a single string
      String[] newvalues = new String[values.length + 1];
      System.arraycopy(values, 0, newvalues, 0, values.length);
      values = newvalues;
    }
    map.put(name, values);
    values[values.length - 1] = value;
  }

  /**
   * Set values for name
   */
  public void put(final String name, final String[] values) {
    map.put(name, values);
  }

  /**
   * Returns a Map containing (String name) -> (String[] values) mappings.
   * Runs in O(number of mappings).
   */
  public Map<String, String[]> toMap() {
    return map;
  }
}
