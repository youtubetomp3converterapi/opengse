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

import java.util.Arrays;
import java.util.Iterator;
import java.util.Enumeration;

import org.junit.Assert;
import org.junit.Test;

/**
 * A test for {@link IteratorEnumeration}.
 *
 * @author jennings
 */
public class IteratorEnumerationTest {

  @Test
  public void testEnumeration() throws Exception {
    Iterator<String> iter = Arrays.asList("foo", "bar").iterator();
    Enumeration<String> en = new IteratorEnumeration<String>(iter);
    Assert.assertTrue(en.hasMoreElements());
    Assert.assertEquals("foo", en.nextElement());
    Assert.assertTrue(en.hasMoreElements());
    Assert.assertEquals("bar", en.nextElement());
    Assert.assertFalse(en.hasMoreElements());
  }
}
