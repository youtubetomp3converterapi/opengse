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

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Unit test for {@link HeaderUtil} methods.
 *
 * @author Mick Killianey
 */
public class HeaderUtilTest extends UtilityClassTestCase {

  @Override protected Class<?> getClassUnderTest() {
    return HeaderUtil.class;
  }

  @Test
  public void testRoundtrip() {
    long[] values = {
        System.currentTimeMillis(),
        0x123456789ABCDEFL,
        Long.MAX_VALUE
    };

    for (long value : values) {
      String longToString = HeaderUtil.toDateHeader(value);
      long stringToLong = HeaderUtil.toDateHeaderLong(longToString);
      String longToString2 = HeaderUtil.toDateHeader(value);
      assertEquals("Value of " + value, longToString, longToString2);

      // This next test does the 1000L to truncate to the nearest second
      assertEquals(longToString,
          (value / 1000L) * 1000L,
          (stringToLong / 1000L) * 1000L);
    }
  }

  @Ignore @Test
  public void testToIntHeader() {
    fail("Not yet implemented");
  }

  @Ignore @Test
  public void testToDateHeader() {
    fail("Not yet implemented");
  }

  @Ignore @Test
  public void testToDateHeaderLong() {
    fail("Not yet implemented");
  }

}
