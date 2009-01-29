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

package com.google.opengse;

import com.google.opengse.util.PropertiesUtil;

import java.util.Properties;

/**
 * @author jennings
 */
public class ServletEngineConfigurationImpl
    implements ServletEngineConfiguration {
  public static final String KEY_PORT = "com.google.opengse.port";
  public static final String KEY_THREADS = "threads";

  private int port;
  private int maxThreads;

  public ServletEngineConfigurationImpl(Properties props) {
    final int noneSpecified = Integer.MAX_VALUE;
    port = PropertiesUtil.getInteger(props, KEY_PORT, noneSpecified);
    if (port == noneSpecified) {
      throw new IllegalArgumentException(
          "No " + KEY_PORT + " property specified");
    }
    maxThreads = PropertiesUtil.getInteger(props, KEY_THREADS, 5);
  }

  public static ServletEngineConfiguration create(int port, int maxThreads) {
    Properties props = new Properties();
    props.setProperty(KEY_PORT, Integer.toString(port));
    props.setProperty(KEY_THREADS, Integer.toString(maxThreads));
    return new ServletEngineConfigurationImpl(props);
  }

  public int getPort() {
    return port;
  }

  public int getMaxThreads() {
    return maxThreads;
  }

  public String getServerType() {
    return "foo";
  }

  public boolean exitOnError() {
    return false;
  }

  public boolean allowChunkedRequestBodies() {
    return true;
  }

  public boolean allowGzippedRequestBodies() {
    return false;
  }

  public boolean dontPrintPostBody() {
    return false;
  }

  public String defaultResponseCharacterEncoding() {
    return "ISO-8859-1";
  }

  public boolean propagateOutputErrors() {
    return false;
  }

  public boolean fixCookieCacheHeaders() {
    return true;
  }
}
