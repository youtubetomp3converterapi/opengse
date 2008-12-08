// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.opengse.webapp;

import com.google.opengse.configuration.WebAppConfiguration;
import com.google.opengse.configuration.WebAppConfigurationException;
import com.google.opengse.configuration.webxml.WebXmlParser;
import com.google.opengse.configuration.webxml.WebXmlParserImpl2;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Provides global servlet engine configuration.
 *
 * @author Mike Jennings
 */
public class GlobalConfigurationFactory {

  private static final String GSE_INF_WEB_XML = "GSE-INF/web.xml";
  private static final String GSE_INF_VERSION_PROPERTIES = "GSE-INF/version.properties";

  private GlobalConfigurationFactory() {
  }

  private static String globalConfiguration = GSE_INF_WEB_XML;

  /**
   * Override the default global configuration web.xml with an application
   * provided one.
   *
   * @param webXml A new web.xml that serves as the global configuration.
   */
  public static void setGlobalConfiguration(String webXml) {
    globalConfiguration = webXml;
  }

  /**
   * Get the version information "baked in" to this jar, or null if running out
   * of an IDE.
   * 
   * @param classLoader
   * @return
   * @throws IOException
   */
  public static Properties getVersionInformation(ClassLoader classLoader) throws IOException {
    Enumeration<URL> webxmls = classLoader.getResources(GSE_INF_VERSION_PROPERTIES);
    URL url = null;
    while (webxmls.hasMoreElements()) {
      if (url != null) {
        throw new IOException(
            "More than one " + GSE_INF_VERSION_PROPERTIES + " found in the classpath");
      }
      url = webxmls.nextElement();
    }
    if (url == null) {
      return null;
    }
    InputStream istr = url.openStream();
    try {
      Properties props = new Properties();
      props.load(istr);
      return props;
    } finally {
      if (istr != null) {
        istr.close();
      }
    }
  }

  /**
   * Gets the global configuration stored as a resource in /GSE-INF/web.xml
   *
   * @param classLoader the classloader to use
   * @return the global configuration
   */
  public static WebAppConfiguration getGlobalConfiguration(
      ClassLoader classLoader)
      throws IOException, WebAppConfigurationException {
    Enumeration<URL> webxmls = classLoader.getResources(globalConfiguration);
    URL webxmlUrl = null;
    while (webxmls.hasMoreElements()) {
      if (webxmlUrl != null) {
        throw new WebAppConfigurationException(
            "More than one " + globalConfiguration + " found in the classpath");
      }
      webxmlUrl = webxmls.nextElement();
    }
//System.out.println("Classloaders: " + getClassloaderChain(classLoader));
    if (webxmlUrl == null) {
      String me = GlobalConfigurationFactory.class.getName().replace('.', '/') + ".class";
      InputStream istr = classLoader.getResourceAsStream(me);
      if (istr == null) {
        throw new RuntimeException("Cannot load " + me + " something is seriously wrong with the classloader(s): " + getClassloaderChain(classLoader));
      }
      throw new WebAppConfigurationException(
          "Could not find global configuration resource '" + globalConfiguration
              + "'");
    }
    Reader reader = new InputStreamReader(webxmlUrl.openStream());
    try {
      WebXmlParser parser = new WebXmlParserImpl2();
      return parser.parse(reader);
    } catch (SAXException e) {
      throw new WebAppConfigurationException(e);
    } finally {
      reader.close();
    }
  }

  private static String getClassloaderChain(ClassLoader classLoader) {
    StringWriter sw = new StringWriter();
    PrintWriter out = new PrintWriter(sw);
    while (classLoader != null) {
      out.println(classLoader.getClass().getName());
      classLoader = classLoader.getParent();
    }
    out.flush();
    return sw.toString();
  }
}
