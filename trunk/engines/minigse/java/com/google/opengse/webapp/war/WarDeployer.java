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
    File rootWarOrDirectory = null;
    List<File> nonRootWars = new ArrayList<File>();
    for (File war : warsToDeploy) {
      String warname = war.getName();
      if (warname.equals("ROOT.war") || warname.equals("ROOT")) {
        rootWarOrDirectory = war;
      } else {
        nonRootWars.add(war);
      }
    }
    WebAppCollection webAppCollection;
    if (rootWarOrDirectory == null) {
      webAppCollection = WebAppCollectionFactory.create(props);
    } else {
      webAppCollection = deployRootWebapp(props, rootWarOrDirectory);
    }
    ServletContainerContext containerContext = webAppCollection.getContainerContext();
    List<WebApp> webapps = new ArrayList<WebApp>();
    for (File nonRootWar : nonRootWars) {
      webapps.add(deployWar(props, nonRootWar, containerContext));
    }
    for (WebApp webapp : webapps) {
      webAppCollection.addWebApp(webapp);
    }
    return webAppCollection;
  }


  public static WebAppCollection deploy(Properties props, WebappDeployInfo... warsOrDirectoriesToDeploy)
      throws ClassNotFoundException, IOException, WebAppConfigurationException,
          InstantiationException, IllegalAccessException {
    WebappDeployInfo rootWebappInfo = null;
    List<WebappDeployInfo> nonRootWarsOrDirectories = new ArrayList<WebappDeployInfo>();
    for (WebappDeployInfo warOrDirectory : warsOrDirectoriesToDeploy) {
      if (warOrDirectory.isRoot()) {
        rootWebappInfo = warOrDirectory;
      } else {
        nonRootWarsOrDirectories.add(warOrDirectory);
      }
    }
    WebAppCollection webAppCollection;
    if (rootWebappInfo == null) {
      webAppCollection = WebAppCollectionFactory.create(props);
    } else {
      webAppCollection = deployRootWebapp(props, rootWebappInfo);
    }
    ServletContainerContext containerContext = webAppCollection.getContainerContext();
    List<WebApp> webapps = new ArrayList<WebApp>();
    for (WebappDeployInfo nonRootWarOrDirectory : nonRootWarsOrDirectories) {
      webapps.add(deployWebapp(containerContext, nonRootWarOrDirectory));
    }
    for (WebApp webapp : webapps) {
      webAppCollection.addWebApp(webapp);
    }
    return webAppCollection;
  }

  private static WebApp deployWar(Properties props, File warFile
                                    , ServletContainerContext containerContext)
      throws IOException, ClassNotFoundException, WebAppConfigurationException,
              IllegalAccessException, InstantiationException {
    LOGGER.info("Deploying " + warFile);
    String warname = warFile.getName();
    int dot = warname.indexOf(WAR_EXTENSION);
    String contextname = warname.substring(0, dot);
    LOGGER.info("Deploying " + warFile);
    File deployDir = PropertiesUtil.getFile(props, DEPLOYDIR);
    WebappDeployInfo webappInfo = new WebappDeployInfo(contextname, warFile, deployDir);
    return deployWebapp(containerContext, webappInfo);
  }

  /**
   * Deploys a war file
   * 
   * @param containerContext the container context to use
   * @param webappInfo the war file plus other information (context name etc.)
   * @return a WebApp representing the war file
   * @throws IOException
   * @throws ClassNotFoundException
   * @throws WebAppConfigurationException
   * @throws IllegalAccessException
   * @throws InstantiationException
   */
  public static WebApp deployWebapp(ServletContainerContext containerContext, WebappDeployInfo webappInfo)
      throws IOException, ClassNotFoundException, WebAppConfigurationException,
      IllegalAccessException, InstantiationException {
    File warFileOrDir = webappInfo.getWarFileOrDirectory();
    if (warFileOrDir.isFile()) {
      LOGGER.info("Deploying " + warFileOrDir);
      if (WarUtil.warIsDifferentFromExploded(warFileOrDir, webappInfo.getContextDirectory())) {
        WarUtil.explodeWarFile(warFileOrDir, webappInfo.getContextDirectory());
      }
    }
    return WebAppFactory.createWebApp(webappInfo.getContextDirectory(), webappInfo.getURIPrefix()
        ,containerContext);
  }

  /**
   * Turns the given warFileOrDirectory into a WebAppCollection with the given warFileOrDirectory
   * as the root webapp for the WebAppCollection
   * 
   * @param props
   * @param warFileOrDirectory
   * @return
   * @throws IOException
   * @throws ClassNotFoundException
   * @throws WebAppConfigurationException
   * @throws IllegalAccessException
   * @throws InstantiationException
   */
  private static WebAppCollection deployRootWebapp(Properties props, File warFileOrDirectory)
      throws IOException, ClassNotFoundException, WebAppConfigurationException,
      IllegalAccessException, InstantiationException {
    if (warFileOrDirectory.isDirectory()) {
      return WebAppCollectionFactory.createWithRootWebapp(props,warFileOrDirectory);
    }
    LOGGER.info("Deploying " + warFileOrDirectory);
    String warname = warFileOrDirectory.getName();
    int dot = warname.indexOf(WAR_EXTENSION);
    File deploydir = PropertiesUtil.getFile(props, DEPLOYDIR);
    if (deploydir == null) {
      deploydir = warFileOrDirectory.getParentFile();
    }
    String contextname = warname.substring(0, dot);
    File contextdir = new File(deploydir, contextname);
    if (WarUtil.warIsDifferentFromExploded(warFileOrDirectory, contextdir)) {
      WarUtil.explodeWarFile(warFileOrDirectory, contextdir);
    }
    return WebAppCollectionFactory.createWithRootWebapp(props, contextdir);
  }


  public static File getDeployDir(Properties props) throws IOException {
    return PropertiesUtil.getFile(props, DEPLOYDIR);
  }

  private static WebAppCollection deployRootWebapp(Properties props, WebappDeployInfo webappInfo)
      throws IOException, ClassNotFoundException, WebAppConfigurationException,
      IllegalAccessException, InstantiationException {
    File warFileOrDirectory = webappInfo.getWarFileOrDirectory();
    if (warFileOrDirectory.isDirectory()) {
      return WebAppCollectionFactory.createWithRootWebapp(props, warFileOrDirectory);
    }
    LOGGER.info("Deploying " + warFileOrDirectory);
    File contextdir = webappInfo.getContextDirectory();
    if (WarUtil.warIsDifferentFromExploded(warFileOrDirectory, contextdir)) {
      WarUtil.explodeWarFile(warFileOrDirectory, contextdir);
    }
    return WebAppCollectionFactory.createWithRootWebapp(props, contextdir);
  }

}
