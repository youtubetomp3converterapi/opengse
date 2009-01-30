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

import java.io.File;

/**
 * Container for information about a war file or exploded war directory
 *
 * Author: Mike Jennings
 * Date: Jan 30, 2009
 * Time: 8:44:11 AM
 */
public final class WebappDeployInfo {
  private static final String WAR_EXTENSION = ".war";
  private static final String ROOT = "ROOT";
  private String contextName;
  private File warFileOrDirectory;
  private File deployDirectory;
  private File contextDirectory;

  public WebappDeployInfo(String contextName, File warFileOrDirectory, File deployDirectory) {
    if (contextName == null) {
      String warname = warFileOrDirectory.getName();
      int dot = warname.indexOf(WAR_EXTENSION);
      if (dot == -1) {
        dot = warname.length();
      }
      this.contextName = warname.substring(0, dot);
    } else {
      this.contextName = contextName;
    }
    this.warFileOrDirectory = warFileOrDirectory;
    if (warFileOrDirectory.isDirectory()) {
      // must be an already-deployed webapp
      // it doesn't make sense to have a deployment directory for an already-deployed webapp
      // so we set it to null
      this.deployDirectory = null;
      // for an already-deployed webapp, the context directory is simply the supplied directory
      this.contextDirectory = warFileOrDirectory;
    } else {
      // must be a war file
      if (deployDirectory == null) {
        // use the directory the war file is in as the deployment directory
        this.deployDirectory = warFileOrDirectory.getParentFile();
      } else {
        this.deployDirectory = deployDirectory;
      }
      // for a war file, the context directory is a subdirectory of the deploy directory
      this.contextDirectory = new File(this.deployDirectory, this.contextName);
    }
  }

  public WebappDeployInfo(String contextName, File warFileOrDirectory) {
    this(contextName, warFileOrDirectory, null);
  }

  public WebappDeployInfo(File warFileOrDirectory) {
    this(null, warFileOrDirectory);
  }

  public boolean isWarFile() {
    return warFileOrDirectory.isFile();
  }

  public String getURIPrefix() {
    if (isRoot()) {
      return "";
    } else {
      return "/" + contextName;
    }
  }

  public boolean isRoot() {
    return (ROOT.equals(contextName));
  }

  public File getContextDirectory() {
    return contextDirectory;
  }

  public String getContextName() {
    return contextName;
  }

  public File getWarFileOrDirectory() {
    return warFileOrDirectory;
  }

  public File getDeployDirectory() {
    return deployDirectory;
  }

}
