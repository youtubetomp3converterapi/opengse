// Copyright 2007 Google Inc. All Rights Reserved.
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

package com.google.opengse.configuration.webxml;

import com.google.opengse.configuration.WebAppConfiguration;
import com.google.opengse.configuration.WebAppConfigurationException;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * A factory for creating WebAppConfiguration objects from a standard webapp
 * directory layout.
 *
 * @author Mike Jennings
 */
public final class WebAppConfigurationFactory {

  private WebAppConfigurationFactory() {
  }

  /**
   * Given a directory with a standard webapp layout (ie, there is a subdirectory
   * called WEB-INF and a file inside that subdirectory called web.xml)
   * create a WebAppConfiguration object.
   *
   * @param dir
   * @return
   * @throws WebAppConfigurationException if the webapp's configuration is unknown or incorrect.
   */
  public static WebAppConfiguration getConfiguration(File dir) throws
      WebAppConfigurationException {
    checkDirectory(dir);
    File webinfdir = new File(dir, "WEB-INF");
    checkDirectory(webinfdir);
    File webxml = new File(webinfdir, "web.xml");
    if (!webxml.exists()) {
      throw new WebAppConfigurationException(
          "File '" + webxml + "' does not exist");
    }
    WebXmlParser parser = new WebXmlParserImpl2();
    FileReader reader;
    try {
      reader = new FileReader(webxml);
    } catch (FileNotFoundException e) {
      throw new WebAppConfigurationException(e);
    }
    // now we have a non-null reader.
    // pass it to the parser.
    try {
      return parser.parse(reader);
    } catch (IOException e) {
      throw new WebAppConfigurationException(e);
    } catch (SAXException e) {
      throw new WebAppConfigurationException(e);
    } finally {
      // always close the reader
      try {
        reader.close();
      } catch (IOException e) {
        throw new WebAppConfigurationException(e);
      }
    }
  }

  /**
   * Checks that a directory exists and is a directory.
   *
   * @param dir the directory to check
   * @throws WebAppConfigurationException
   * if the directory does not exist or is not a directory
   */
  private static void checkDirectory(File dir)
      throws WebAppConfigurationException {
    if (!dir.exists()) {
      throw new WebAppConfigurationException(
          "Directory '" + dir + "' does not exist");
    }
    if (!dir.isDirectory()) {
      throw new WebAppConfigurationException(
          "'" + dir + "' is not a directory");
    }
  }
}
