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

package com.google.opengse.util.string;

/**
 * Interface for character escaping, used to allow a strategy pattern
 * style of escaping to be used.  We're relying somewhat on the jvm to
 * optimize calls to the escaper at runtime and hopefully inline them,
 * reducing the overhead of having to call out to a different object.
 *
 * @author Sven Mawson
 */
public interface CharEscaper {

  /**
   * Escape a character, returns the char[] resulting from escaping that
   * character or null if no escaping is needed.  Note that returning an
   * empty array is the equivalent of stripping that character out of the
   * input stream.
   *
   * The returned array remains under the control of the escaper, and MUST
   * not be modified by the caller.
   *
   * @param c the character we are escaping.
   * @return a char[] escaped version or null if no escaping was needed.  This
   *     must not be modified by the caller.
   */
  public char[] escape(char c);
}
