// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.opengse.examples.noxml;

import com.google.opengse.jndi.JNDIMain;
import com.google.opengse.webapp.WebAppCollection;
import com.google.opengse.webapp.WebAppConfigurationBuilder;
import com.google.opengse.webapp.WebAppCollectionFactory;
import com.google.opengse.configuration.WebAppConfiguration;
import com.google.opengse.configuration.webxml.WebXmlDump;
import com.google.opengse.util.PropertiesUtil;
import com.google.gse.GoogleServletEngineFactory;
import com.google.gse.open.OpenGSE;

import java.util.Properties;
import java.io.PrintWriter;

/**
 * An example of how to configure the internal GSE without using web.xml
 *
 * Just pass the following command line to this class:
 * 
 *  --context=contextpath --contextdir=/tmp --javax.servlet.context.tempdir=/tmp --port=8080
 *
 * and then view http://localhost:8080/contextpath/bar and you should see
 * "Hello from MyServletContextListener"
 *
 * @author jennings@google.com (Mike Jennings)
 */
public class Main {
  public static void main(String[] args) throws Exception {
    // get our configuration
    Properties props = PropertiesUtil.getPropertiesFromCommandLine(args);
    // verify we have the minimum config
    requireProperties(props, "context", "contextdir", "port");
    // add our configuration to JNDI
    JNDIMain.initialize(props);

    WebAppConfigurationBuilder configBuilder = new WebAppConfigurationBuilder();
    // register our servlet to response to the "/bar" pattern
    configBuilder.addServlet(BasicServlet.class, "/bar");
    // register our ServletContextListener
    configBuilder.addServletContextListener(MyServletContextListener.class);

    // Dump out a web.xml just because we can :)
    WebAppConfiguration config = configBuilder.getConfiguration();
    PrintWriter out = new PrintWriter(System.out);
    out.println("I hate writing web.xml, luckily, code can do it for me...");
    WebXmlDump.dump(config, out);
    out.flush();

    // create our web app collection (which just contains one webapp)
    WebAppCollection webapps
        = WebAppCollectionFactory.createWebAppCollectionWithOneContext(props,
            config);


    // set our corp-gse factory
    props.setProperty("opengse.factoryclass", GoogleServletEngineFactory.class.getName());
    // remove props that don't correspond to GSE flags
    makeFlagsHappy(props);
    // pass our webapp collection to our engine-creator
    OpenGSE.ServletEngine engine = OpenGSE.createServletEngine(props, webapps);
    webapps.startAll();
    engine.dispatchRequests();
  }

  private static void makeFlagsHappy(Properties props) {
    props.remove("context");
    props.remove("contextdir");
    props.remove("cwd");
    props.remove("javax.servlet.context.tempdir");
  }

  private static void requireProperties(Properties props, String... keys) throws Exception {
    for (String key : keys) {
      String value = props.getProperty(key);
      if (value == null) {
        throw new Exception("No --"+key+" specified");
      }
    }
  }

}
