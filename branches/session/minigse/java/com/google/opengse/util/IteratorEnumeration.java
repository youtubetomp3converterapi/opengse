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

package com.google.opengse.util;

import java.util.Enumeration;
import java.util.Iterator;

/**
 *
 * Adapts an Iterator to an Enumeration. Essentially a copy of
 * the anonymous inner class in com.google.common.collect.Iterators
 * but we choose to use this class rather than pull in the
 * Google collections library.
 *
 * @author jennings
 *         Date: Jul 13, 2008
 */
public class IteratorEnumeration<T> implements Enumeration<T> {
  private final Iterator<T> iterator;

  public IteratorEnumeration(Iterator<T> iterator) {
    if (iterator == null) {
      throw new IllegalArgumentException("iterator cannot be null");
    }
    this.iterator = iterator;
  }

  public boolean hasMoreElements() {
    return iterator.hasNext();
  }

  public T nextElement() {
    return iterator.next();
  }

}
