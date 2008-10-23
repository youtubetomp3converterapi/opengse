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

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Unit test for {@link RequestUtils} utility methods.
 *
 * @author Mick Killianey
 */
public class RequestUtilsTest extends UtilityClassTestCase {

  @Override
  protected Class<?> getClassUnderTest() {
    return RequestUtils.class;
  }

  @Ignore @Test
  public void testGetURIHttpServletRequest() {
    fail("Not yet implemented");
  }

  @Ignore @Test
  public void testGetURIHttpRequest() {
    fail("Not yet implemented");
  }

  @Ignore @Test
  public void testGetRequestURLPrefix() {
    fail("Not yet implemented");
  }
}
