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

package com.google.opengse.configuration.webxml;

import java.util.*;

/**
 * @author jennings
 *         Date: Oct 12, 2008
 */
class SetList<E> {
  private Map<String, E> key_to_thing;
  private List<String> key_order;

  SetList() {
    key_to_thing = new HashMap<String, E>();
    key_order = new ArrayList<String>();
  }

  void add(String key, E object) {
    if (key_to_thing.get(key) == null) {
      // if this is the first time we see the key, add it to
      // the key_order
      key_order.add(key);
    }
    key_to_thing.put(key, object);
  }

  int size() {
    return key_order.size();
  }

  E[] toArray(E[] array) {
    int i = 0;
    for (String key : key_order) {
      array[i++] = key_to_thing.get(key);
    }
    return array;
  }
}
