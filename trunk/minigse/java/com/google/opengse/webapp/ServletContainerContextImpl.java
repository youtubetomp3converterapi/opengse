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

package com.google.opengse.webapp;

import com.google.opengse.configuration.WebAppConfiguration;
import com.google.opengse.configuration.WebAppConfigurationException;
import com.google.opengse.configuration.WebAppContextParam;
import com.google.opengse.configuration.WebAppMimeMapping;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

/**
 * An implementation of ServletContainerContext.
 *
 * @author Mike Jennings
 */
final class ServletContainerContextImpl implements ServletContainerContext {
  private final WebAppCollection webapps;
  private final WebAppConfiguration globalConfig;
  private final Map<String, String> contextParams;
  private final Map<String, String> extensionToMimeType;
  private int majorVersion;
  private int minorVersion;
  private static final String OPENGSE_MAJORVERSION = "opengse.majorversion";
  private static final String OPENGSE_MINORVERSION = "opengse.minorversion";

  ServletContainerContextImpl(WebAppCollection webapps)
      throws IOException, WebAppConfigurationException {
    this.webapps = webapps;
    globalConfig = GlobalConfigurationFactory
        .getGlobalConfiguration(this.getClass().getClassLoader());
    contextParams = new HashMap<String, String>();
    extensionToMimeType = new HashMap<String, String>();
    parseConfig();
  }

  private void parseConfig() throws WebAppConfigurationException {
    for (WebAppContextParam contextParam : globalConfig.getContextParams()) {
      contextParams
          .put(contextParam.getParamName(), contextParam.getParamValue());
    }
    String value = contextParams.get(OPENGSE_MAJORVERSION);
    if (value == null) {
      throw new WebAppConfigurationException(
          "No context-param named '" + OPENGSE_MAJORVERSION + "'");
    }
    majorVersion = Integer.parseInt(value);
    value = contextParams.get(OPENGSE_MINORVERSION);
    if (value == null) {
      throw new WebAppConfigurationException(
          "No context-param named '" + OPENGSE_MINORVERSION + "'");
    }
    minorVersion = Integer.parseInt(value);
    for (WebAppMimeMapping mapping : globalConfig.getMimeMappings()) {
      extensionToMimeType.put(mapping.getExtension(), mapping.getMimeType());
    }
  }

  public int getMajorVersion() {
    return majorVersion;
  }

  public int getMinorVersion() {
    return minorVersion;
  }

  public String getMimeType(String file) {
    // Default to null => unknown MIME type.
    String mimeType = null;

    // Try to derive the MIME type from the file extension (e.g. ".png").
    int dotPosition = file.lastIndexOf('.');

    /*
     *  Only accept files with extensions
     *  (for example, ".png" isn't allowed, but "1.png" is).
     */
    if (dotPosition > 0) {
      String extension = file.substring(dotPosition + 1).toLowerCase();
      mimeType = extensionToMimeType.get(extension);
    }
    return mimeType;
  }

  /**
   * @{inheritDoc}
   */
  public ServletContext getContext(String path) {
    String uriPrefix = path;
    /*
     * We want paths like "/servlet-tests/ServletConfigGetServletContextTest"
     * to be matched up with the webapp with the uri prefix "/servlet-tests"
     */
    if (path.startsWith("/")) {
      int secondSlashLocation = path.indexOf('/', 1);
      if (secondSlashLocation > 1) {
        uriPrefix = path.substring(0, secondSlashLocation);
      }
    } else {
      // paths that don't start with a '/' get mapped to the root context
       uriPrefix = "";
    }

    WebApp webapp = webapps.getWebApp(uriPrefix);
    return (webapp == null) ? null : webapp.getContext();
  }

  public Object getAttribute(String name) {
    return webapps.getAttribute(name);
  }

  public String getServerInfo() {
    return "opengse/" + getMajorVersion() + "." + getMinorVersion();
  }
}
