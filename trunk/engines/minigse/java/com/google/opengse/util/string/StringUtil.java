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

import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import java.io.StringWriter;


/**
 * String utilities used by the servlet engine.
 *
 * @author Mike Jennings
 */
public final class StringUtil {
  private StringUtil() { throw new AssertionError(); }

  // unicode 3000 is the double-byte space character in UTF-8
  // unicode 00A0 is the non-breaking space character (&nbsp;)
  // unicode 2007 is the figure space character (&#8199;)
  // unicode 202F is the narrow non-breaking space character (&#8239;)
  public static final String WHITE_SPACES = " \r\n\t\u3000\u00A0\u2007\u202F";


  /**
   * Helper function for making null strings safe for comparisons, etc.
   *
   * @return (s == null) ? "" : s;
   */
  public static String makeSafe(String s) {
    return (s == null) ? "" : s;
  }

  /**
   * Helper function for null and empty string testing.
   *
   * @return true iff s == null or s.equals("");
   */
  public static boolean isEmpty(String s) {
    return (s == null) || "".equals(s);
  }


  /**
   * Escapes special characters from a string so it can safely be included in an
   * HTML document in either element content or attribute values. Does
   * <em>not</em> alter non-ASCII characters or control characters.
   */
  public static String htmlEscape(String s) {
    return CharEscapers.escape(s, CharEscapers.HTML_ESCAPE);
  }

  /**
   * Replaces any string of matched characters with the supplied string.<p>
   *
   * This is a more general version of collapseWhitespace.
   *
   * <pre>
   *   E.g. collapse("hello     world", " ", "::")
   *   will return the following string: "hello::world"
   * </pre>
   *
   * @param str the string you want to munge
   * @param chars all of the characters to be considered for munge
   * @param replacement the replacement string
   * @return munged and replaced string.
   */
  public static String collapse(String str, String chars, String replacement) {
    if (str == null) {
      return null;
    }

    StringBuilder newStr = new StringBuilder();

    boolean prevCharMatched = false;
    char c;
    for (int i = 0; i < str.length(); i++) {
      c = str.charAt(i);
      if (chars.indexOf(c) != -1) {
        // this character is matched
        if (prevCharMatched) {
          // apparently a string of matched chars, so don't append anything
          // to the string
          continue;
        }
        prevCharMatched = true;
        newStr.append(replacement);
      } else {
        prevCharMatched = false;
        newStr.append(c);
      }
    }

    return newStr.toString();
  }

  /**
   * Serializes a map
   *
   * @param map A map of String keys to arrays of String values
   * @param keyValueDelim Delimiter between keys and values
   * @param entryDelim Delimiter between entries
   *
   * @return String A string containing a serialized representation of the
   *         contents of the map.
   *
   * e.g. arrayMap2String({"foo":["bar","bar2"],"foo1":["bar1"]}, "=", "&")
   * returns "foo=bar&foo=bar2&foo1=bar1"
   */
  public static String arrayMap2String(Map<String, String[]> map,
                                       String keyValueDelim,
                                       String entryDelim) {
    Set<Map.Entry<String, String[]>> entrySet = map.entrySet();
    Iterator<Map.Entry<String, String[]>> itor = entrySet.iterator();
    StringWriter sw = new StringWriter();
    while (itor.hasNext()) {
      Map.Entry<String, String[]> entry = itor.next();
      String key = entry.getKey();
      String[] values = entry.getValue();
      for (int i = 0; i < values.length; i++) {
        sw.write(key + keyValueDelim + values[i]);
        if (i < values.length - 1) {
          sw.write(entryDelim);
        }
      }
      if (itor.hasNext()) {
        sw.write(entryDelim);
      }
    }
    return sw.toString();
  }

  /**
   * Like String.indexOf() except that it will look for any of the
   * characters in 'chars' (similar to C's strpbrk)
   */
  public static int indexOfChars(CharSequence str, CharSequence chars) {
    return indexOfChars(str, chars, 0);
  }

  /**
   * Return the first index in the string of any of the specified characters.
   * @param str The string to look in
   * @param chars The list of characters to look for. The first occurrence
   *        of any of these characters is a match.
   * @param fromIndex Start looking at this index in the input string.
   *        Must be within bounds for the input string length.
   * @return The index of the match
   */
  public static int indexOfChars(
      CharSequence str, CharSequence chars, int fromIndex) {
    final int len = str.length();

    for (int pos = fromIndex; pos < len; pos++) {
      for (int chrpos = 0; chrpos < chars.length(); chrpos++) {
        if (chars.charAt(chrpos) == str.charAt(pos)) {
          return pos;
        }
      }
    }

    return -1;
  }
}
