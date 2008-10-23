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

package com.google.opengse;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Date;

/**
 * Utility methods for manipulating http headers.
 *
 * @author Mike Jennings
 */
public final class HeaderUtil {
  private HeaderUtil() { /* Utility class: do not instantiate */ }

  /**
   * Per thread pre-initialized date formatters
   */
  private static final ThreadLocal<SimpleDateFormat[]> DATE_FORMATS =
    new ThreadLocal<SimpleDateFormat[]>() {
      @Override
      protected synchronized SimpleDateFormat[] initialValue() {
        SimpleDateFormat[] fmts =
          new SimpleDateFormat[] {
            // The first two formats are the same because we use fmts[0] for
            // parsing and fmts[1] for formatting. We uses separate objects for
            // these tasks because parsing a date actually modifies the
            // timezone that the formatting object uses for formatting.
            new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US),
            new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US),
            new SimpleDateFormat("EEEEEE, dd-MMM-yy HH:mm:ss zzz", Locale.US),
            new SimpleDateFormat("EEE MMMM d HH:mm:ss yyyy", Locale.US)
              };
        fmts[0].setTimeZone(TimeZone.getTimeZone("GMT"));
        fmts[1].setTimeZone(TimeZone.getTimeZone("GMT"));
        fmts[2].setTimeZone(TimeZone.getTimeZone("GMT"));
        fmts[3].setTimeZone(TimeZone.getTimeZone("GMT"));
        return fmts;
      }
    };

  public static int toIntHeader(String value) {
    if (value == null) {
      return -1;
    }
    return Integer.parseInt(value);
  }

  public static String toDateHeader(long value) {
    final SimpleDateFormat[] formats = DATE_FORMATS.get();
    return formats[0].format(new Date(value));
  }

  public static long toDateHeaderLong(String value) {
    if (value == null) {
      return -1L;
    }

    // Note that formats[0] and formats[1] are identical, but we are only using
    // formats[0] for formatting dates so that the timezone we set on the
    // SimpleDateFormat object does not get changed by parsing a date.
    for (SimpleDateFormat format : DATE_FORMATS.get()) {
      try {
        final Date date = format.parse(value);
        return date.getTime();
      } catch (ParseException e) { /* ignored */ }
    }
    throw new IllegalArgumentException(value);
  }
}
