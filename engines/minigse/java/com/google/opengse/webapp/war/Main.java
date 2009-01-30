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
import com.google.opengse.jndi.JNDIMain;
import com.google.opengse.util.PropertiesUtil;
import com.google.opengse.webapp.WebAppCollection;
import com.google.opengse.webapp.WebAppCollectionFactory;
import com.google.opengse.webapp.GlobalConfigurationFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import java.net.URLClassLoader;
import java.net.URL;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Main entry point for war deployment.
 *
 * @author Mike Jennings
 */
public class Main {
  private static final String WEBAPP_DOT = "webapp.";

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
    String classpath = PropertiesUtil.getAliasedProperty(props, "classpath", null);
    if (classpath != null) {
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      if (cl != null && cl instanceof URLClassLoader) {
        URLClassLoader urlcl = (URLClassLoader) cl;
        urlcl = new URLClassLoader(classpathToUrls(classpath), urlcl);
        Thread.currentThread().setContextClassLoader(urlcl);
      }
    }
    ServletEngineFactory engineFactory
        = JNDIMain.lookup(ServletEngineFactory.class);
    ServletEngine engine = engineFactory.createServletEngine(
                webapps, new ServletEngineConfigurationImpl(props));
    webapps.startAll();
    engine.run();
  }


  /**
   * Converts a classpath to an array of URLs
   * 
   * @param classpath
   * @return
   * @throws IOException
   */
  private static URL[] classpathToUrls(String classpath) throws IOException {
    classpath = classpath.replace('/', File.separatorChar);
    StringTokenizer st = new StringTokenizer(classpath, File.pathSeparator);
    URL[] urls = new URL[st.countTokens()];
    int i = 0;
    while (st.hasMoreTokens()) {
      File f = new File(st.nextToken());
      f = f.getCanonicalFile();
      if (!f.exists()) {
        throw new FileNotFoundException(f.toString());
      }
      urls[i++] = f.toURL(); 
    }
    return urls;
  }

  private static void showVersion() throws IOException {
    Properties props = GlobalConfigurationFactory.getVersionInformation(Thread.currentThread().getContextClassLoader());
    if (props == null) {
      System.err.println("No version information available.");
      return;
    }
    String version = props.getProperty("version");
    if (version == null) {
      System.err.println("No version information found!");
    } else {
      System.out.println("version " + version);
    }
  }

  /**
   * Get all the properties that look like webapp.foo=... webapp.bar=... etc.
   * @param props
   * @return
   */
  private static List<String> getWebAppKeys(Properties props) {
    List<String> webappKeys = new ArrayList<String>();
    for (Object okey : props.keySet()) {
      String key = okey.toString();
      if (key.startsWith(WEBAPP_DOT)) {
        String context = key.substring(WEBAPP_DOT.length());
        if (contextIsValid(context)) {
          webappKeys.add(key);
        } else {
          System.err.println("'" + context + "' is not a valid context");
        }
      }
    }
    return webappKeys;
  }

  private static boolean contextIsValid(String context) {
    if (context.indexOf(".") > -1 || context.indexOf('/') > -1) {
      return false;
    }
    if (context.trim().length() == 0) {
      return false;
    }
    return true;
  }

  public static WebAppCollection getWebAppCollection(Properties props) throws
      ClassNotFoundException, IOException, WebAppConfigurationException,
      InstantiationException, IllegalAccessException {
    File webappsDir = PropertiesUtil.getFile(props, "webapps");
    File webapp = PropertiesUtil.getFile(props, "webapp");
    List<String> webappKeys = getWebAppKeys(props);

    if (webappsDir == null && webapp == null && webappKeys.isEmpty()) {
      System.err.println("No webapp or webapps property found.");
      System.err.println("Please use --webapp=/path/to/file.war ");
      System.err.println("or --webapp=/path/to/webapp ");
      System.err.println("or --webapp.somecontext=/path/to/webapp ");
      System.err.println("or --webapps=/path/to/wars or --props=/path/to/file.properties");
      System.err.println("or --create to create a skeleton web application");
      return null;
    }

    if (webapp != null && webappsDir != null) {
      System.err.println("Found both webapp=" + webapp
          + " and webapps=" + webappsDir + " properties set");
      return null;
    }

    if (!webappKeys.isEmpty() && webappsDir != null) {
      String webappKey = webappKeys.iterator().next();
      File webappValue = PropertiesUtil.getFile(props, webappKey);
      System.err.println("Found both " + webappKey + "=" + webappValue
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

    if (webapp != null) {
      return getWebApp(props);
    }

    if (webappsDir != null) {
      return getWebApps(props, webappsDir);
    }
    return getWebApps(props, webappKeys);
  }


  private static WebAppCollection getWebApps(Properties props, List<String> webappKeys)
      throws IOException, ClassNotFoundException, WebAppConfigurationException,
      IllegalAccessException, InstantiationException {

    File deployDir = WarDeployer.getDeployDir(props);
    // must be a directory of webapps
    WebappDeployInfo[] warsToDeploy = new WebappDeployInfo[webappKeys.size()];
    int i = 0;
    for (String webappKey : webappKeys) {
      String context = webappKey.substring(WEBAPP_DOT.length());
      File warOrDirectory = PropertiesUtil.getFile(props, webappKey);
      warsToDeploy[i++] = new WebappDeployInfo(context, warOrDirectory, deployDir); 
    }
    if (warsToDeploy == null || warsToDeploy.length == 0) {
      System.err.println("No webapps found!");
      return null;
    }
    return WarDeployer.deploy(props, warsToDeploy);
  }




  private static WebAppCollection getWebApps(Properties props, File webappsDir)
      throws IOException, ClassNotFoundException, WebAppConfigurationException,
      IllegalAccessException, InstantiationException {

    // must be a directory of webapps
    File[] warsToDeploy;
    warsToDeploy = getWarsInDirectory(webappsDir);
    if (warsToDeploy == null || warsToDeploy.length == 0) {
      System.err.println("No war files found in " + webappsDir);
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
