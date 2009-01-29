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

// Copyright 2007 Google Inc. All Rights Reserved.

package com.google.opengse.configuration.webxml;

import com.google.opengse.configuration.WebAppConfiguration;
import com.google.opengse.io.FileUtil;

import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Mike Jennings
 */
public abstract class WebXmlTestCase extends TestCase {

  private String testDataResource;
  protected WebXmlParser parser;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    String testdataPackage = getClass().getPackage().getName() + ".testdata";
    testDataResource = testdataPackage.replace('.', '/');
    parser = new WebXmlParserImpl2();
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  private InputStream getWebXmlResource(String name) {
    return Thread.currentThread().getContextClassLoader().getResourceAsStream(
        testDataResource + "/" + name);
  }

  /**
   * Get the resource in the testdata directory as an array of bytes
   * @param name
   * @return
   * @throws java.io.IOException
   */
  protected byte[] getResourceBytes(String name) throws IOException {
    InputStream stream = getWebXmlResource(name);
    assertNotNull("Can't find " + name, stream);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {
      FileUtil.copy(stream, baos);
    } finally {
      stream.close();
    }
    return baos.toByteArray();
  }

  protected String getResourceAsString(String name) throws IOException {
    return new String(getResourceBytes(name));
  }

}