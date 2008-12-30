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
import com.google.opengse.configuration.webxml.WebAppConfigurationFactory;

import java.io.File;
import java.io.IOException;
import java.net.URLClassLoader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;

/**
 * A factory for WebApp objects.
 *
 * @author Mike Jennings
 */
public class WebAppFactory {

  private WebAppFactory() {
  }

  /**
   * Creates a WebApp given a contextBase (directory which has a WEB-INF
   * directory) and a URI prefix (for example "/foo").
   *
   * Note lib or classes directory may or may not exist
   */
  public static WebApp createWebApp(File contextBase, String uriPrefix,
      ServletContainerContext containerContext)
      throws WebAppConfigurationException, ClassNotFoundException,
      InstantiationException, IllegalAccessException {
    WebAppConfiguration config = WebAppConfigurationFactory
        .getConfiguration(contextBase);
    File webinfdir = new File(contextBase, "WEB-INF");
    File classesdir = new File(webinfdir, "classes");
    File libdir = new File(webinfdir, "lib");
    List<URL> urlList = new ArrayList<URL>();
    try {
      if (classesdir.exists()) {
        urlList.add(classesdir.toURL());
      }
      if (libdir.exists()) {
        File[] jars = libdir.listFiles();
        if (jars != null) {
          for (File jar : jars) {
            if (jar.isFile() && jar.getName().endsWith(".jar")) {
              urlList.add(jar.toURL());
            }
          }
        }
      }
    } catch (MalformedURLException e) {
      throw new WebAppConfigurationException(e);
    }

      URL[] urls = urlList.toArray(new URL[0]);
      URLClassLoader urlcl = new URLClassLoader(urls, WebAppFactory.class.getClassLoader());
      return WebAppImpl.create(uriPrefix, contextBase,
          urlcl, config, containerContext);
  }

  /**
   * Creates a WebApp without a WEB-INF/web.xml
   *
   * @param uriPrefix
   * @param contextBase
   * @param config
   * @param containerContext
   * @return
   * @throws IllegalAccessException
   * @throws WebAppConfigurationException
   * @throws InstantiationException
   * @throws ClassNotFoundException
   */
  public static WebApp createWebApp(String uriPrefix, File contextBase,
      WebAppConfiguration config,
      ServletContainerContext containerContext)
  throws IllegalAccessException, WebAppConfigurationException,
      InstantiationException, ClassNotFoundException {
    return WebAppImpl.create(uriPrefix, contextBase, config, containerContext);
  }

  /**
   * Creates a WebApp from a RequestHandler and a uriPrefix.
   */
  public static WebApp createWebApp(FilterChain handler, String uriPrefix) {
    return new SimpleWebApp(handler, uriPrefix);
  }

  /**
   * Checks that a directory exists and is a directory.
   *
   * @param dir the directory to check
   * @throws WebAppConfigurationException if the directory does not exist or is
   *                                      not a directory
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

  private static class SimpleWebApp implements WebApp {
    private final FilterChain handler;
    private final String uriPrefix;
    private boolean started;

    private SimpleWebApp(FilterChain handler, String uriPrefix) {
      this.handler = handler;
      this.uriPrefix = uriPrefix;
    }

    public String getUriPrefix() {
      return uriPrefix;
    }

    public void start() throws ServletException {
      started = true;
    }

    public boolean isStarted() {
      return started;
    }

    public void stop() {
      started = false;
    }

    /**
     * Get the context of this web application.
     */
    public ServletContext getContext() {
      return null;
    }

    public Object getAttribute(String name) {
      return null;
    }

    public File getContextBase() {
      throw new UnsupportedOperationException();
    }

    public HttpSessionAttributeListener getHttpSessionAttributeListener() {
      return null;
    }

    public HttpSessionListener getHttpSessionListener() {
      return null;
    }


    public void doFilter(ServletRequest request,
        ServletResponse response) throws IOException, ServletException {
      if (!started) {
        throw new IllegalStateException("WebApp has not been started");
      }
      handler.doFilter(request, response);
    }
  }

}
