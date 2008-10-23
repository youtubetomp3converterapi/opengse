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

package com.google.opengse.configuration.webxml;

import junit.framework.TestCase;

import java.io.*;

import com.google.opengse.configuration.WebAppConfiguration;
import org.xml.sax.SAXException;

/**
 * @author jennings
 *         Date: Oct 12, 2008
 */
public class WebAppConfigurationCombinerTest extends WebXmlTestCase {

  private String toString(WebAppConfiguration config) {
    StringWriter stringWriter = new StringWriter();
    PrintWriter out = new PrintWriter(stringWriter);
    WebXmlDump.dump(config, out);
    out.close();
    return stringWriter.getBuffer().toString();
  }

  private WebAppConfiguration getConfigFromResource(String resourceName) throws IOException, SAXException {
    byte[] inputBytes1 = getResourceBytes(resourceName);
    InputStream webxmlStream1 = new ByteArrayInputStream(inputBytes1);
    return parser
        .parse(new InputStreamReader(webxmlStream1));

  }

  public void testCombine() throws Exception {
    WebAppConfiguration config1 = getConfigFromResource("test1.web.xml");
    WebAppConfiguration config2 = getConfigFromResource("test2.web.xml");

    WebAppConfiguration combinedConfig = WebAppConfigurationCombiner.combine(config1, config2);

    String combinedWebXml = toString(combinedConfig);
    String expectedCombinedWebXml = getResourceAsString("test1_and_test2_combined.xml");
    assertEquals("Did not get expected web.xml", expectedCombinedWebXml, combinedWebXml);
  }

}
