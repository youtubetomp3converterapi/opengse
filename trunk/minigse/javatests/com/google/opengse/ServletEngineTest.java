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

package com.google.opengse;

import com.google.opengse.io.StreamUtils;
import com.google.opengse.testing.FilterChains;
import com.google.opengse.jndi.JNDIMain;

import junit.framework.JUnit4TestAdapter;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URL;
import java.net.HttpURLConnection;

/**
 * @author Mike Jennings
 */
public class ServletEngineTest {

  private static ServletEngine engine;
  private static Thread engineThread;
  private static final int PORT = -1; // Server-assigned.
  private static final String EXPECTED_MESSAGE = "Hey man!";

  /** Make this JUnit4 test runnable in JUnit3. */
  public static junit.framework.Test suite() {
    return new JUnit4TestAdapter(ServletEngineTest.class);
  }

  @BeforeClass
  public static void setUp() throws Exception {
    ServletEngineConfiguration config =
        ServletEngineConfigurationImpl.create(PORT, 5);
    ServletEngineFactory engineFactory
        = JNDIMain.lookup(ServletEngineFactory.class);

    engine = engineFactory.createServletEngine(FilterChains.withPlainMessage(EXPECTED_MESSAGE), config);
    engineThread = new Thread(engine, "engine");
    engineThread.setDaemon(true);
    engineThread.start();
    engine.awaitInitialization(10000);
    System.out.println("gse initialized");
  }

  @AfterClass
  public static void tearDown() throws Exception {
    if (!engine.quit(20000)) {
      throw new Exception("Timeout waiting for engine to quit");
    }
  }

  @Test
  public void testRequest() throws Exception {
    URL url = new URL("http://localhost:" + engine.getPort() + "/foo");
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    assertEquals(200, conn.getResponseCode());
    assertEquals("GET", conn.getRequestMethod());
    String actual = StreamUtils.toString(conn.getInputStream());
    assertEquals(EXPECTED_MESSAGE, actual);
  }
}
