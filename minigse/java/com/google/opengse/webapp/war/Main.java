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

package com.google.opengse.webapp.war;

import com.google.opengse.ServletEngine;
import com.google.opengse.ServletEngineFactory;
import com.google.opengse.ServletEngineConfigurationImpl;
import com.google.opengse.configuration.WebAppConfigurationException;
import com.google.opengse.configuration.webxml.WebXmlDump;
import com.google.opengse.jndi.JNDIMain;
import com.google.opengse.util.PropertiesUtil;
import com.google.opengse.webapp.WebAppCollection;
import com.google.opengse.webapp.WebAppCollectionFactory;
import com.google.opengse.webapp.GlobalConfigurationFactory;
import com.google.opengse.webapp.WebAppConfigurationBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Main entry point for war deployment.
 *
 * @author Mike Jennings
 */
public class Main {
  private Main() { /* Entry point: do not instantiate */ }

  private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
  private static final String WAR_EXTENSION = ".war";
  private static final String PORT_PROPERTY = "port";
  private static final String DEFAULT_PORT = "8080";
  private static boolean jndiInitialized = false;

  public static void main(String[] args) throws Exception {
    // get our configuration
    Properties props = PropertiesUtil.getPropertiesFromCommandLine(args);
    if (props.getProperty("showversion") != null) {
      showVersion();
      return;
    }

    if (props.getProperty("create") != null) {
      SkeletonMaker.createWebApp(props);
      return;
    }
    // Let us run this more than once (e.g. from unit tests)
    if (!jndiInitialized) {
      // add our configuration to JNDI
      JNDIMain.initialize(props);
      jndiInitialized = true;
    }
    WebAppCollection webapps = getWebAppCollection(props);
    if (webapps == null) {
      return;
    }
    ServletEngineFactory engineFactory
        = JNDIMain.lookup(ServletEngineFactory.class);
    ServletEngine engine = engineFactory.createServletEngine(
                webapps, new ServletEngineConfigurationImpl(props));
    webapps.startAll();
    engine.run();
  }



  private static void showVersion() throws IOException {
    Properties props = GlobalConfigurationFactory.getVersionInformation(Thread.currentThread().getContextClassLoader());
    if (props == null) {
      LOGGER.info("No version information available.");
    }
    String version = props.getProperty("version");
    if (version == null) {
      LOGGER.severe("No version information found!");
    } else {
      LOGGER.info("version " + version);
    }
  }

  public static WebAppCollection getWebAppCollection(Properties props) throws
      ClassNotFoundException, IOException, WebAppConfigurationException,
      InstantiationException, IllegalAccessException {
    File webappsDir = PropertiesUtil.getFile(props, "webapps");
    File webapp = PropertiesUtil.getFile(props, "webapp");

    if (webappsDir == null && webapp == null) {
      System.err.println("No webapp or webapps property found.");
      System.err.println("Please use --webapp=/path/to/file.war ");
      System.err.println("or --webapp=/path/to/webapp ");
      System.err.println("or --webapps=/path/to/wars or --props=/path/to/file.properties");
      System.err.println("or --create to create a skeleton web application");
      return null;
    }

    if (webapp != null && webappsDir != null) {
      LOGGER.severe("Found both webapp=" + webapp
          + " and webapps=" + webappsDir + " properties set");
      return null;
    }

    String sport = PropertiesUtil.getAliasedProperty(props,
        PORT_PROPERTY, null);
    if (sport == null) {
      sport = PropertiesUtil.getAliasedProperty(props,
        ServletEngineConfigurationImpl.KEY_PORT, DEFAULT_PORT);
    }
    int port = Integer.parseInt(sport);
    props.setProperty(ServletEngineConfigurationImpl.KEY_PORT,
            Integer.toString(port));
    return getWebApps(props);
  }


  private static WebAppCollection getWebApps(Properties props)
      throws IOException, ClassNotFoundException, WebAppConfigurationException,
      IllegalAccessException, InstantiationException {
    File webapp = PropertiesUtil.getFile(props, "webapp");
    File webapps = PropertiesUtil.getFile(props, "webapps");
    if (webapp != null) {
      return getWebApp(props);
    }

    // must be a directory of webapps
    File[] warsToDeploy;
    warsToDeploy = getWarsInDirectory(webapps);
    if (warsToDeploy == null || warsToDeploy.length == 0) {
      LOGGER.severe("No war files found in " + webapps);
      return null;
    }
    return WarDeployer.deploy(props, warsToDeploy);
  }


  // get a webapp as a WebAppCollection containing a single webapp
  private static WebAppCollection getWebApp(Properties props)
      throws IOException, ClassNotFoundException, WebAppConfigurationException,
      IllegalAccessException, InstantiationException {
    File webapp = PropertiesUtil.getFile(props, "webapp");
    if (webapp.isFile()) {
      // if it's a file, assume it's a .war file
      return WarDeployer.deploy(props, webapp);
    }
    /*
     * webapp must be a directory, get the context for it
     * (default is directory name)
    */
    String context = PropertiesUtil.getAliasedProperty(
            props, "context", webapp.getName());
    LOGGER.info("using context=" + context);
    return WebAppCollectionFactory.create(props, context, webapp);
  }


  private static File[] getWarsInDirectory(File dir) {
    File[] files = dir.listFiles();
    if (files == null) {
      return new File[0];
    }
    List<File> fileList = new ArrayList<File>(files.length);
    for (File f : files) {
      if (f.isFile() && f.getName().endsWith(WAR_EXTENSION)) {
        fileList.add(f);
      }
    }
    files = new File[fileList.size()];
    fileList.toArray(files);
    return files;
  }

}
