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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Mike Jennings
 */
public class WebXmlParserImpl2Test extends WebXmlTestCase {

  public void testParse() throws Exception {
    String testFile = "test1.web.xml";
    byte[] inputBytes = getResourceBytes(testFile);
    InputStream webxmlStream = new ByteArrayInputStream(inputBytes);
    WebAppConfiguration config = parser
        .parse(new InputStreamReader(webxmlStream));
    StringWriter stringWriter = new StringWriter();
    PrintWriter out = new PrintWriter(stringWriter);
    WebXmlDump.dump(config, out);
    out.close();
    String generatedXML = stringWriter.getBuffer().toString();
    String originalXML = new String(inputBytes);
    assertEquals(originalXML, generatedXML);
  }
}