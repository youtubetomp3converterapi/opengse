// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.opengse.examples.simpleservlet;

import com.google.opengse.webapp.WebAppConfigurationBuilder;
import com.google.opengse.webapp.WebAppCollection;
import com.google.opengse.webapp.WebAppCollectionFactory;
import com.google.opengse.webapp.WebAppFactory;
import com.google.opengse.ServletEngine;
import com.google.opengse.ServletEngineConfiguration;
import com.google.opengse.ServletEngineConfigurationImpl;
import com.google.opengse.core.ServletEngineImpl;

import java.util.Properties;
import java.io.File;

import javax.servlet.FilterChain;

/**
 * An example of how to use OpenGSE with a single servlet.
 *
 * Usage: invoke this main method and then try loading http://localhost:8080/foo/bar
 *
 * @author jennings@google.com (Mike Jennings)
 */
public class Main {
  private static final int PORT = 8080;
  private static final int MAX_THREADS = 5;

  public static void main(String[] args) throws Exception {
    // This object will create our webapp's configuration for us
    WebAppConfigurationBuilder configBuilder = new WebAppConfigurationBuilder();
    // register our servlet to respond to the "/bar" pattern
    configBuilder.addServlet(BasicServlet.class, "/bar");
    // we need to be able to return a directory
    // when javax.servlet.ServletContext.getRealPath("/") is called
    // hence the need for a context directory
    File contextDir = new File(System.getProperty("java.io.tmpdir"));
    // We want "/foo" to be returned by 
    // javax.servlet.http.HttpServletRequest.getContextPath()
    String context = "foo";
    // create a collection of webapps that just contains a single webapp
    WebAppCollection webapps
      = WebAppCollectionFactory.createWebAppCollectionWithOneContext(contextDir,
        context, configBuilder.getConfiguration());
    // start all of the webapps
    webapps.startAll();
    // create servlet engine configuration object
    ServletEngineConfiguration config
        = ServletEngineConfigurationImpl.create(PORT, MAX_THREADS);
    // the collection of webapps is our "request sink" (just done to illustrate)
    FilterChain requestSink = webapps;
    // create an engine using the enging configuration and send all requests
    // to our request sink
    ServletEngine engine = ServletEngineImpl.create(requestSink, config);
    // run it
    engine.run();
  }

}
