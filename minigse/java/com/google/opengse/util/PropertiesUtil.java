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

package com.google.opengse.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * Utility methods for reading properties files.
 * This class has no dependencies beyond core java classes.
 *
 * @author Mike Jennings
 */
public final class PropertiesUtil {
  private PropertiesUtil() { throw new AssertionError(); }

  // if value is less characters than "${x}" then no translation is needed
  private static final int SMALLEST_POSSIBLE_ALIASED_STRING = 4;
  private static final String VAR_START = "${";
  private static final String VAR_END = "}";
  private static final String ARG_PREFIX = "--";

  /**
   * Read a properties file without throwing any exceptions.
   *
   * @param f the properties file to read
   * @return null if any exceptions were thrown
   */
  public static Properties safeReadProperties(File f) {
    try {
      return readProperties(f);
    } catch (IOException e) {
      return null;
    }
  }

  /**
   * Reads a properties file.
   *
   * @param f the properties file
   * @return a Properties object containing the parse properties
   */
  public static Properties readProperties(File f) throws IOException {
    Properties props = new Properties();
    FileInputStream fis = null;
    try {
      fis = new FileInputStream(f);
      props.load(fis);
    } finally {
      if (fis != null) {
        fis.close();
      }
    }
    return props;
  }

  /**
   * Writes properties to a file.
   *
   * @param f the file to write to
   */
  public static void writeProperties(File f, Properties props)
      throws IOException {
    FileOutputStream fos = null;
    try {
      fos = new FileOutputStream(f);
      props.store(fos, "");
    } finally {
      if (fos != null) {
        fos.close();
      }
    }
  }

  /**
   * Converts command-line switches of the form --key1=value1 --key2=value2 into
   * a Properties object. A Switch of the form --props=/path/to/some/file.properties
   * gets special treatment; the properties file is loaded and added to the set
   * of command-line switches (command-line taking precedence over properties file)
   * and a new property called "basedir" is added which is the directory containing
   * the properties file.
   *
   * @param args
   * @return
   * @throws IOException
   */
  public static Properties getPropertiesFromCommandLine(String[] args)
      throws IOException {
    Properties props = toProperties(args);
    // now to add our current working directory as a property
    props.setProperty("cwd", System.getProperty("user.dir"));
    File externalPropsFile = PropertiesUtil.getFile(props, "props");
    if (externalPropsFile != null) {
      Properties externalProps = PropertiesUtil
          .readProperties(externalPropsFile);
      // add the property "basedir" just like ant does
      externalProps
          .setProperty("basedir", externalPropsFile.getParentFile().toString());
      // command-line properties override external properties file
      externalProps.putAll(props);
      props = externalProps;
    }
    return props;
  }


  /**
   * Converts command-line switches of the form --key1=value1 --key2=value2 into
   * a Properties object.
   *
   * @param args the arguments to parse
   * @return the parsed properties
   */
  public static Properties toProperties(String[] args) {
    Properties props = new Properties();
    if (args != null) {
      for (String arg : args) {
        if (arg.startsWith(ARG_PREFIX)) {
          arg = arg.substring(ARG_PREFIX.length());
          int eq = arg.indexOf('=');
          if (eq > 0) {
            String key = arg.substring(0, eq);
            String value = arg.substring(eq + 1);
            props.setProperty(key, value);
          } else {
            String key = arg;
            String value = "true";
            props.setProperty(key, value);
          }
        }
      }
    }
    return props;
  }

  /**
   * Get a canonical file property using aliasing.
   *
   * @param props the properties object to use
   * @param name the (possibly aliased) property name to use
   * @return null if the property was not found
   * @throws IOException if there is a problem getting the canonical form
   * of the file.
   */
  public static File getFile(Properties props, String name)
      throws IOException {
    String fileAsString = getAliasedProperty(props, name, null);
    if (fileAsString == null) {
      return null;
    }
    if (fileAsString.indexOf("${") >= 0 && fileAsString.indexOf("}") >= 0) {
      throw new IOException("Cannot resolve '" + fileAsString + "'");
    }
    File file = new File(fileAsString);
    return file.getCanonicalFile();
  }

  /**
   * Get an integer property using aliasing
   * @param props the properties object to use
   * @param name the (possibly aliased) property name to use
   * @param defaultValue the default value to use if a value wasn't specified
   */
  public static int getInteger(
      Properties props, String name, int defaultValue) {
    String value = getAliasedProperty(
        props, name, Integer.toString(defaultValue));
    return Integer.parseInt(value);
  }


  public static boolean getBoolean(
      Properties props, String name, boolean defaultValue) {
    String value = getAliasedProperty(
        props, name, Boolean.toString(defaultValue)).toLowerCase();
    return (value.equals("true") || value.equals("1") || value.equals("yes"));
  }

  /**
   * Retrieves an "aliased" property from a Properties object. For example, a
   * Properties object containing: host=localhost url=http://${host}/index.html
   *
   * would return "http://localhost/index.html" from a call to
   * getAliasedProperty(props, "url", null)
   */
  public static String getAliasedProperty(Properties props, String key,
      String def) {
    return getAliasedProperty(props, key, def, new HashSet<String>());
  }

  private static String getAliasedProperty(Properties props, String key,
      String def, Set<String> keysToIgnore) {
    String value = props.getProperty(key);
    if (value == null) {
      return def;
    }
    keysToIgnore.add(key);
    String translatedValue = translateString(value, props, keysToIgnore);
    keysToIgnore.remove(key);
    return translatedValue;
  }


  private static int findMatchingBrace(String value, int fromIndex) {
    if (value.charAt(fromIndex) != '{') {
      // this should never happen. This method is only called when a '{' char is found
      throw new RuntimeException("char at fromIndex is not {");
    }
    int balance = 0;
    char ch;
    for (int i = fromIndex; i < value.length(); ++i) {
      ch = value.charAt(i);
      if (ch == '{') {
        --balance;
      } else if (ch == '}') {
        ++balance;
      }
      if (balance == 0) {
        return i;
      }
    }
    return -1;
  }

  private static String translateString(String value, Properties props,
      Set<String> keysToIgnore) {
    int n = value.length();
    if (n < SMALLEST_POSSIBLE_ALIASED_STRING) {
      // if value is less characters than "${x}" then no translation is needed
      return value;
    }
    int startVarIndex = value.indexOf(VAR_START);
    if (startVarIndex == -1) {
      return value; // nothing to translate
    }
    int endVarIndex = findMatchingBrace(value, startVarIndex + 1);
    if (endVarIndex == -1) {
      return value; // nothing to translate
    }

    StringBuffer sb = new StringBuffer();
    sb.append(value.substring(0, startVarIndex));
    String aliasedKey = value
        .substring(startVarIndex + VAR_START.length(), endVarIndex);
    String aliasedKeyDefaultValue = VAR_START + aliasedKey + VAR_END;
    if (keysToIgnore.contains(aliasedKey)) {
      sb.append(aliasedKeyDefaultValue);
    } else {
      sb.append(getAliasedProperty(props, aliasedKey, aliasedKeyDefaultValue,
          keysToIgnore));
    }
    sb.append(
        translateString(value.substring(endVarIndex + 1), props, keysToIgnore));
    return sb.toString();
  }


}
