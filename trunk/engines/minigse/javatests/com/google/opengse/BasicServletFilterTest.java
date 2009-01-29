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

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Basic filter functions.
 *
 * @author Wenbo Zhu
 */
public class BasicServletFilterTest {

  private static final String FILTER_MESSAGE = "You over there!";
  private static final String EXPECTED_MESSAGE =
    FILTER_MESSAGE + " - " + BasicServlet.DEFAULT_MESSAGE;

  private static ServletEngine engine;

  @BeforeClass
  public static void startEngine() throws Exception {
    WebAppConfigurationBuilder configBuilder = new WebAppConfigurationBuilder();

    configBuilder.addServlet(BasicServlet.class, "/bar1/wildcard");
    configBuilder.addFilter(BasicFilter.class, "/bar1/*");

    configBuilder.addServlet(BasicServlet.class, "/bar2/exact");
    configBuilder.addFilter(BasicFilter.class, "/bar2/exact");

    configBuilder.addServlet(BasicServlet.class, "/bar3/wildcard/*");
    configBuilder.addFilter(BasicFilter.class, "/bar3/*");

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
  public void testWildcardMatch() throws Exception {
    doRequest("/foo/bar1/wildcard");
  }

  @Test
  public void testWildcardSubpathMatch() throws Exception {
    doRequest("/foo/bar3/wildcard/sub1/sub2");
  }

  @Test
  public void testExactMatch() throws Exception {
    doRequest("/foo/bar2/exact");
  }

  private void doRequest(String path) throws Exception {
    URL url = new URL("http://localhost:" + engine.getPort() + path);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    assertEquals(200, conn.getResponseCode());
    assertEquals("GET", conn.getRequestMethod());
    String actual = StreamUtils.toString(conn.getInputStream());
    assertEquals(EXPECTED_MESSAGE, actual);
  }

  /** Filter that outputs a short message to the response's writer. */
  public static class BasicFilter implements Filter {
    public void doFilter(final ServletRequest request,
        final ServletResponse response, FilterChain chain) throws
        IOException, ServletException {
      response.getWriter().append(FILTER_MESSAGE).append(" - ");
      chain.doFilter(request, response);
    }
    public void init(final FilterConfig filterConfig) { /* nothing */ }
    public void destroy() { /* nothing */ }
  }
}

