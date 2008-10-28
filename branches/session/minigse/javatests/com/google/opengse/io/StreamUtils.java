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

package com.google.opengse.io;

import java.io.InputStream;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * A collection of utility methods for streams (for testing only?).
 *
 * @author Mike Jennings
 */
public final class StreamUtils {
  private StreamUtils() { /* Utility class: do not instantiate */ }

  /**
   * Convert an InputStream to a string, but does not close the InputStream.
   * 
   */
  public static String toString(InputStream istr) throws IOException {
    Reader reader = new InputStreamReader(istr);
    char[] buf = new char[512];
    int n;
    StringBuilder sb = new StringBuilder();
    while ((n = reader.read(buf)) == buf.length) {
      sb.append(buf, 0, n);
    }
    if (n > 0) {
      sb.append(buf, 0, n);
    }
    return sb.toString();
  }

  /**
   * Convert an InputStream to a string, then close the InputStream.
   * 
   * @param istr
   * @return
   * @throws IOException
   */
  public static String toStringAndClose(InputStream istr) throws IOException {
    try {
      return toString(istr);
    } finally {
      istr.close();
    }
  }

}
