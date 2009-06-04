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

import javax.net.ssl.SSLServerSocketFactory;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Set;
import java.util.TreeSet;
import java.util.Iterator;

/**
 * @author jennings
 */
public class ServletEngineConfigurationImpl
    implements ServletEngineConfiguration {
  public static final String KEY_PORT = "com.google.opengse.port";
  public static final String KEY_SECURE = "secure";
  public static final String KEY_THREADS = "threads";
  public static final String KEY_ENABLED_CIPHER_SUITES = "secure.enabled.cipher.suites";

  private int port;
  private int maxThreads;
  private boolean secure;
  private String[] enabledCipherSuites;
  private static final String JAVAX_NET_SSL_KEYSTORE = "javax.net.ssl.keyStore";
  private static final String JAVAX_NET_SSL_KEYSTORE_PASSWORD = "javax.net.ssl.keyStorePassword";

  public ServletEngineConfigurationImpl(Properties props) {
    final int noneSpecified = Integer.MAX_VALUE;
    port = PropertiesUtil.getInteger(props, KEY_PORT, noneSpecified);
    if (port == noneSpecified) {
      throw new IllegalArgumentException(
          "No " + KEY_PORT + " property specified");
    }
    maxThreads = PropertiesUtil.getInteger(props, KEY_THREADS, 5);
    maybeSetupSSL(props);
  }

  private void maybeSetupSSL(Properties props) {
    secure = PropertiesUtil.getBoolean(props, KEY_SECURE, false);
    if (!secure) {
      return;
    }

    Set<String> supportedSuites = getSupportedCipherSuites();

    String commaSuites = PropertiesUtil.getAliasedProperty(props, KEY_ENABLED_CIPHER_SUITES, null);
    if (commaSuites == null) {
      String commaSupportedSuites = toCommaDelimeteredString(supportedSuites);
      throw new IllegalArgumentException("Cannot have " + KEY_SECURE
          + "=true without specifying " + KEY_ENABLED_CIPHER_SUITES
          + " something like: "
          + KEY_ENABLED_CIPHER_SUITES + "=" + commaSupportedSuites + " would work");
    }
    StringTokenizer st = new StringTokenizer(commaSuites, ",");
    String[] suites = new String[st.countTokens()];
    int i = 0;
    boolean allAnonymous = true;
    while (st.hasMoreTokens()) {
      suites[i] = st.nextToken();
      if (!supportedSuites.contains(suites[i])) {
        throw new IllegalArgumentException("Cipher suite '" + suites[i] + "' is not supported");
      }
      if (suites[i].indexOf("_anon_") == -1) {
        allAnonymous = false;
      }
      ++i;
    }
    // now we check for mandatory system properties
    if (!allAnonymous) {
      // we only need a keystore if there are any non-anonymous cipher suites enabled
      String keystore = System.getProperty(JAVAX_NET_SSL_KEYSTORE);
      if (keystore == null) {
        throw new IllegalArgumentException("No " + JAVAX_NET_SSL_KEYSTORE
            + " system property specified."
            + " This is needed unless only anonymous suites like 'SSL_DH_anon_WITH_RC4_128_MD5' are used");
      }
      String keystorepass = System.getProperty(JAVAX_NET_SSL_KEYSTORE_PASSWORD);
      if (keystorepass == null) {
        throw new IllegalArgumentException("No " + JAVAX_NET_SSL_KEYSTORE_PASSWORD + " system property specified");
      }
    }

    enabledCipherSuites = suites;
  }

  public String[] getEnabledCipherSuites() {
    return enabledCipherSuites;
  }

  private static Set<String> getSupportedCipherSuites() {
    SSLServerSocketFactory factory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
    String[] supportedSuites = factory.getSupportedCipherSuites();
    Set<String> suites = new TreeSet<String>();
    for (String suite : supportedSuites) {
      suites.add(suite);
    }
    return suites;
  }

  private static String toCommaDelimeteredString(Set<String> set) {
    if (set.isEmpty()) {
      return "";
    }
    Iterator<String> iter = set.iterator();
    StringBuffer sb = new StringBuffer(iter.next());
    while (iter.hasNext()) {
      sb.append(',').append(iter.next());
    }
    return sb.toString();
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

  public boolean isSecure() {
    return secure;
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
