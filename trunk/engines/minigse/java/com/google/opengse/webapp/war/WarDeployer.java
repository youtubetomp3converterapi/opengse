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

import com.google.opengse.configuration.WebAppConfigurationException;
import com.google.opengse.util.PropertiesUtil;
import com.google.opengse.webapp.ServletContainerContext;
import com.google.opengse.webapp.WebApp;
import com.google.opengse.webapp.WebAppCollectionFactory;
import com.google.opengse.webapp.WebAppFactory;
import com.google.opengse.webapp.WebAppCollection;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * This class knows how to deploy war files
 *
 * @author Mike Jennings
 */
public final class WarDeployer {
  private WarDeployer() { /* Utility class: do not instantiate */ }

  private static final Logger LOGGER =
      Logger.getLogger(WarDeployer.class.getName());

  private static final String ROOT = "ROOT";
  private static final String DEPLOYDIR = "deploydir";
  private static final String WAR_EXTENSION = ".war";

  /**
   * Deploys 1 or more war files.
   *
   * @param props        properties that may be used by in the deployment
   *                     process.
   * @param warsToDeploy An array of war files.
   */
  public static WebAppCollection deploy(Properties props, File... warsToDeploy)
      throws ClassNotFoundException, IOException, WebAppConfigurationException,
          InstantiationException, IllegalAccessException {
    File rootWar = null;
    List<File> nonRootWars = new ArrayList<File>();
    /*
     * TODO:  What should happen if there's more than one ROOT.war in the
     * same directory?
     */
    for (File war : warsToDeploy) {
      if (war.getName().equals("ROOT.war")) {
        rootWar = war;
      } else {
        nonRootWars.add(war);
      }
    }
    WebAppCollection webAppCollection;
    if (rootWar == null) {
      webAppCollection = WebAppCollectionFactory.create(props);
    } else {
      webAppCollection = deployRootWar(props, rootWar);
    }
    ServletContainerContext containerContext
        = webAppCollection.getContainerContext();
    List<WebApp> webapps = new ArrayList<WebApp>();
    for (File nonRootWar : nonRootWars) {
      webapps.add(deployWar(props, nonRootWar, containerContext));
    }
    for (WebApp webapp : webapps) {
      webAppCollection.addWebApp(webapp);
    }
    return webAppCollection;
  }

  private static WebApp deployWar(Properties props, File warFile,
      ServletContainerContext containerContext)
      throws IOException, ClassNotFoundException, WebAppConfigurationException,
      IllegalAccessException, InstantiationException {
    LOGGER.info("Deploying " + warFile);
    String warname = warFile.getName();
    int dot = warname.indexOf(WAR_EXTENSION);
    File deploydir = PropertiesUtil.getFile(props, DEPLOYDIR);
    if (deploydir == null) {
      deploydir = warFile.getParentFile();
    }
    String contextname = warname.substring(0, dot);
    File contextdir = new File(deploydir, contextname);
    if (WarUtil.warIsDifferentFromExploded(warFile, contextdir)) {
      WarUtil.explodeWarFile(warFile, contextdir);
    }
    String uriPrefix;
    if (ROOT.equals(contextname)) {
      uriPrefix = "";
    } else {
      uriPrefix = "/" + contextname;
    }
    return WebAppFactory.createWebApp(contextdir, uriPrefix, containerContext);
  }

  private static WebAppCollection deployRootWar(Properties props, File warFile)
      throws IOException, ClassNotFoundException, WebAppConfigurationException,
      IllegalAccessException, InstantiationException {
    LOGGER.info("Deploying " + warFile);
    String warname = warFile.getName();
    int dot = warname.indexOf(WAR_EXTENSION);
    File deploydir = PropertiesUtil.getFile(props, DEPLOYDIR);
    if (deploydir == null) {
      deploydir = warFile.getParentFile();
    }
    String contextname = warname.substring(0, dot);
    File contextdir = new File(deploydir, contextname);
    if (WarUtil.warIsDifferentFromExploded(warFile, contextdir)) {
      WarUtil.explodeWarFile(warFile, contextdir);
    }
    return WebAppCollectionFactory.createWithRootWebapp(props, contextdir);
  }

}
