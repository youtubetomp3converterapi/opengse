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

import com.google.opengse.configuration.webxml.WebXmlParserImpl2Test;

import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * @author Mike Jennings
 */
public class AllTests extends TestSuite {
  /** Suite of all tests; add your tests here. */
  public static Test suite() throws Exception {
    TestSuite suite = new TestSuite(AllTests.class.getName());
    suite.addTestSuite(WebXmlParserImpl2Test.class);
    suite.addTest(AllClassesHaveATestTest.suite());
    return suite;
  }
}
