// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.opengse.examples.internalgse;

import com.google.opengse.jndi.JNDIMain;
import com.google.opengse.webapp.WebAppCollection;
import com.google.opengse.util.PropertiesUtil;
import com.google.gse.open.OpenGSE;
import com.google.gse.GoogleServletEngineFactory;

import java.util.Properties;

/**
 * Example of how to use the internal GSE to deploy webapps
 *
 * @author Mike Jennings
 */
public class Main {

  public static void main(String[] args) throws Exception {
    // get our configuration
    Properties props = PropertiesUtil.getPropertiesFromCommandLine(args);
    // add our configuration to JNDI
    JNDIMain.initialize(props);

    // use the properties to create one or more webapps from one or more files or directories
    WebAppCollection webapps = com.google.opengse.webapp.war.Main.getWebAppCollection(props);
    if (webapps == null) {
      return;
    }
    // set our corp-gse factory
    props.setProperty("opengse.factoryclass", GoogleServletEngineFactory.class.getName());
    // remove props that don't correspond to GSE flags
    props.remove("basedir");
    props.remove("com.google.opengse.port");
    props.remove("cwd");
    props.remove("javax.servlet.context.tempdir");
    props.remove("props");
    props.remove("watchdogdir");
    props.remove("webapps");
    OpenGSE.ServletEngine engine = OpenGSE.createServletEngine(props, webapps);
    webapps.startAll();
    engine.dispatchRequests();
  }


}

