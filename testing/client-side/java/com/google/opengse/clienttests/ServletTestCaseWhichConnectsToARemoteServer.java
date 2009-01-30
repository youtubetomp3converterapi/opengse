// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.opengse.clienttests;

import java.io.StringWriter;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.fail;
import org.junit.BeforeClass;


/**
 * Base class for test cases that run against a web server with test wars already deployed
 * and running.
 *
 * To connect to a host other than localhost, define the "host" system property.
 * To connect to a port other than 8080, define the "port" system property.
 * @author Mike Jennings
 */
public class ServletTestCaseWhichConnectsToARemoteServer {
  private final StringWriter sw = new StringWriter();
  private final PrintWriter out = new PrintWriter(sw);
  private Properties props;
  private static int port;
  private static String host;
  private static AtomicInteger users = new AtomicInteger(0);

  @BeforeClass
  public static void getHostAndPort() throws Exception {
    synchronized (users) {
      if (users.get() > 0) {
        return; // already running
      }
      // unfortunately, we need to hardcode the port to be 8080 since the
      // "golden files" we compare against have port information in them
      String sport = System.getProperty("port", "8080");
      port = Integer.parseInt(sport);
      host = System.getProperty("host", "localhost");
    }
  }

  protected HttpAssertion createAssertion() {
    sw.getBuffer().setLength(0);
    if (props == null) {
      props = new Properties();
      props.setProperty("user.dir", System.getProperty("user.dir"));
      props.setProperty("host", host);
      props.setProperty("port", Integer.toString(port));
    }
    HttpAssertion httpAssertion = new HttpAssertion(props, out);
    httpAssertion.setHost("${host}");
    httpAssertion.setPort("${port}");
    return httpAssertion;
  }

  protected void httpFail() {
    fail(sw.toString());
  }

  protected HttpRequestAsserter createGetAssertion() {
    if (props == null) {
      props = new Properties();
      props.setProperty("user.dir", System.getProperty("user.dir"));
      props.setProperty("host", host);
      props.setProperty("port", Integer.toString(port));
    }

    HttpRequestAsserter asserter = new HttpRequestAsserter(props);
    return asserter;
  }

}
