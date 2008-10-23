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

/**
 * Implementation of an empty Enumeration.
 *
 * @author jennings
 *         Date: Jul 13, 2008
 */
public class EmptyEnumeration<T> implements Enumeration<T> {

  private static final EmptyEnumeration<Object> EMPTY_ENUM
      = new EmptyEnumeration<Object>();

  public boolean hasMoreElements() {
    return false;
  }

  public T nextElement() {
    return null;
  }

  /** Returns the empty {@code Enumeration}. */
  // Casting to any type is safe since there are no actual elements.
  @SuppressWarnings("unchecked")
  public static <T> Enumeration<T> please() {
    return (Enumeration<T>) EMPTY_ENUM;
  }

}
