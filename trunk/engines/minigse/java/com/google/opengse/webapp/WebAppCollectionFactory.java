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

import com.google.opengse.configuration.WebAppConfigurationException;
import com.google.opengse.configuration.WebAppConfiguration;
import com.google.opengse.util.PropertiesUtil;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletResponse;

/**
 * A factory for WebAppCollection objects.
 *
 * @author Mike Jennings
 */
public final class WebAppCollectionFactory {

  private static final String ROOT = "ROOT";

  private WebAppCollectionFactory() {
  }

  public static WebAppCollection create(Properties props, WebApp rootWebApp)
      throws WebAppConfigurationException, IOException {
    return WebAppCollectionImpl.create(props, rootWebApp);
  }

  public static WebAppCollection create(Properties props)
      throws IOException, WebAppConfigurationException {
    WebApp rootWebapp = WebAppFactory.createWebApp(new NotFoundHandler(), "");
    return create(props, rootWebapp);
  }

  public static WebAppCollection createWithRootWebapp(Properties props,
      File contextdir)
      throws
      ClassNotFoundException, IOException, WebAppConfigurationException,
      InstantiationException, IllegalAccessException {
    // to solve the chicken&egg problem, we create a ServletContainerContext
    // that will later delegate to a real implementation
    ServletContainerContextWrapper containerContext
        = new ServletContainerContextWrapper();
    WebApp rootWebapp = WebAppFactory
        .createWebApp(contextdir, "", containerContext);
    WebAppCollection webAppCollection = create(props, rootWebapp);
    // now that we've created a WebAppCollection, we can use its container
    // context as the delegate for the one used to create the root webapp
    containerContext.setDelegate(webAppCollection.getContainerContext());
    return webAppCollection;
  }


  public static WebAppCollection createWithRootWebapp(Properties props,
      File contextdir, WebAppConfiguration config)
      throws
      ClassNotFoundException, IOException, WebAppConfigurationException,
      InstantiationException, IllegalAccessException {
    // to solve the chicken&egg problem, we create a ServletContainerContext
    // that will later delegate to a real implementation
    ServletContainerContextWrapper containerContext
        = new ServletContainerContextWrapper();
    
    WebApp rootWebapp = WebAppFactory.createWebApp(
            "", contextdir, config, containerContext);


    WebAppCollection webAppCollection = create(props, rootWebapp);
    // now that we've created a WebAppCollection, we can use its container
    // context as the delegate for the one used to create the root webapp
    containerContext.setDelegate(webAppCollection.getContainerContext());
    return webAppCollection;
  }


  /**
   * Creates a web application collection with a single webapp.
   *
   * @param props
   * @param contextname
   * @param contextdir
   * @return
   * @throws ClassNotFoundException
   * @throws IOException
   * @throws WebAppConfigurationException
   * @throws InstantiationException
   * @throws IllegalAccessException
   */
  public static WebAppCollection create(Properties props, String contextname,
      File contextdir)
      throws
      ClassNotFoundException, IOException, WebAppConfigurationException,
      InstantiationException, IllegalAccessException {
    if (ROOT.equals(contextname)) {
      return createWithRootWebapp(props, contextdir);
    }
    if (contextname.indexOf('/') >= 0) {
      throw new WebAppConfigurationException(
              "Found / in contextname '" + contextname + "'");
    }
    String uriPrefix = "/" + contextname;
    WebAppCollection webAppCollection = WebAppCollectionFactory.create(props);
    ServletContainerContext containerContext
        = webAppCollection.getContainerContext();
    WebApp webapp = WebAppFactory
        .createWebApp(contextdir, uriPrefix, containerContext);
    webAppCollection.addWebApp(webapp);
    return webAppCollection;
  }

  /**
   * Creates a WebAppCollection containing only one context.
   * 
   * @param props
   * @param config
   * @return
   * @throws IllegalAccessException
   * @throws WebAppConfigurationException
   * @throws IOException
   * @throws InstantiationException
   * @throws ClassNotFoundException
   */
  public static WebAppCollection createWebAppCollectionWithOneContext(Properties props,
      WebAppConfiguration config)
      throws IllegalAccessException, WebAppConfigurationException,
      IOException, InstantiationException, ClassNotFoundException {
    String context = PropertiesUtil.getAliasedProperty(props, "context", null);
    if (context == null) {
      throw new WebAppConfigurationException(
          "No property named 'context' found");
    }
    File contextdir = PropertiesUtil.getFile(props, "contextdir");
    if (contextdir == null) {
      throw new WebAppConfigurationException(
          "No property named 'contextdir' found");
    }
    if (!contextdir.isDirectory()) {
      throw new WebAppConfigurationException(
          "'" + contextdir + "' is not a directory");
    }
    return create(props, context, contextdir, config);
  }

  /**
   * Convenience method to create a WebAppCollection from a single context.
   *
   * @param contextdir the directory of the context
   * @param context For root context, use "ROOT". Does not start with "/".
   * 
   * @return
   * @throws WebAppConfigurationException
   * @throws IllegalAccessException
   * @throws IOException
   * @throws ClassNotFoundException
   * @throws InstantiationException
   */
  public static WebAppCollection createWebAppCollectionWithOneContext(
      final File contextdir, final String context, WebAppConfiguration config)
      throws WebAppConfigurationException, IllegalAccessException, IOException,
      ClassNotFoundException, InstantiationException {
    Properties props = new Properties();
    props.setProperty("context", context);
    props.setProperty("contextdir", contextdir.toString());
    return WebAppCollectionFactory.createWebAppCollectionWithOneContext(props, config);
  }



  /**
   * Creates a WebAppCollection with a single webapp using the supplied
   * configuration object instead of parsing a WEB-INF/web.xml
   *
   * @param props
   * @param contextname
   * @param contextdir
   * @param config
   * @return
   * @throws ClassNotFoundException
   * @throws IOException
   * @throws WebAppConfigurationException
   * @throws InstantiationException
   * @throws IllegalAccessException
   */
  public static WebAppCollection create(Properties props, String contextname,
      File contextdir, WebAppConfiguration config)
      throws
      ClassNotFoundException, IOException, WebAppConfigurationException,
      InstantiationException, IllegalAccessException {
    if (ROOT.equals(contextname)) {
      return createWithRootWebapp(props, contextdir, config);
    }
    if (contextname.indexOf('/') >= 0) {
      throw new WebAppConfigurationException(
              "Found / in contextname '" + contextname + "'");
    }
    if (contextname.equals("")) {
      throw new WebAppConfigurationException(
              "Invalid context name. Use \"ROOT\" for the root context");
    }
    String uriPrefix = "/" + contextname;
    WebAppCollection webAppCollection = WebAppCollectionFactory.create(props);
    ServletContainerContext containerContext
        = webAppCollection.getContainerContext();
    WebApp webapp = WebAppFactory.createWebApp(
            uriPrefix, contextdir, config, containerContext);
    webAppCollection.addWebApp(webapp);
    return webAppCollection;
  }


  private static class NotFoundHandler implements FilterChain {
    public void doFilter(ServletRequest request,
        ServletResponse resp) throws IOException, ServletException {
      HttpServletResponse response = (HttpServletResponse) resp;
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
  }

}
