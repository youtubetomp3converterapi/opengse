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

import com.google.opengse.io.StreamUtils;
import com.google.opengse.webapp.WebAppCollection;
import com.google.opengse.webapp.WebAppConfigurationBuilder;
import com.google.opengse.webapp.WebAppCollectionFactory;
import com.google.opengse.jndi.JNDIMain;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Mike Jennings
 */
public class BasicServletTest {

  private static final String EXPECTED_MESSAGE = BasicServlet.DEFAULT_MESSAGE;

  private static ServletEngine engine;

  @BeforeClass
  public static void startEngine() throws Exception {
    WebAppConfigurationBuilder configBuilder = new WebAppConfigurationBuilder();

    configBuilder.addServlet(BasicServlet.class, "/bar/*");

    Properties props = new Properties();
    props.setProperty("context", "foo");
    props.setProperty("contextdir", "/tmp/opengse");
    props.setProperty("javax.servlet.context.tempdir", "/tmp/opengse");

    WebAppCollection webapps
        = WebAppCollectionFactory.createWebAppCollectionWithOneContext(props, configBuilder.getConfiguration());

    webapps.startAll();
    ServletEngineConfiguration config
        = ServletEngineConfigurationImpl.create(-1, 5);
    ServletEngineFactory engineFactory
        = JNDIMain.lookup(ServletEngineFactory.class);

    engine = engineFactory.createServletEngine(webapps, config);
    Thread engineThread = new Thread(engine, "engine");
    engineThread.setDaemon(true);
    engineThread.start();
    engine.awaitInitialization(10000);
  }

  @AfterClass
  public static void stopEngine() throws IOException {
    assertTrue(engine.quit(20000));
  }

  @Test
  public void testRequest() throws Exception {
    URL url = new URL("http://localhost:" + engine.getPort() + "/foo/bar/baz");
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    assertEquals(200, conn.getResponseCode());
    assertEquals("GET", conn.getRequestMethod());
    String actual = StreamUtils.toString(conn.getInputStream());
    assertEquals(EXPECTED_MESSAGE, actual);
  }

}