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
import com.google.opengse.configuration.impl.MutableWebAppConfiguration;
import com.google.opengse.configuration.impl.MutableWebAppContextParam;
import com.google.opengse.configuration.impl.MutableWebAppFilter;
import com.google.opengse.configuration.impl.MutableWebAppInitParam;
import com.google.opengse.configuration.impl.MutableWebAppListener;
import com.google.opengse.configuration.impl.MutableWebAppServlet;
import com.google.opengse.configuration.impl.MutableWebAppServletMapping;
import com.google.opengse.configuration.impl.MutableWebappFilterMapping;
import com.google.opengse.util.PropertiesUtil;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * @author jennings
 *         Date: May 22, 2008
 */
public class WebAppConfigurationBuilder {
  private final MutableWebAppConfiguration config;

  public WebAppConfigurationBuilder() {
    config = MutableWebAppConfiguration.create();
  }

  public WebAppConfiguration getConfiguration() {
    return config;
  }

  public WebAppConfigurationBuilder addContextParam(String key, String value) {
    MutableWebAppContextParam cparam = MutableWebAppContextParam.create();
    cparam.setParamName(key);
    cparam.setParamValue(value);
    config.addContextParam(cparam);
    return this;
  }

  /*
   * TODO:  add filter with servlet name; multiple pattern/servlet mappings;
   * dispatchers
   */
  public WebAppConfigurationBuilder addFilter(
      Class<? extends Filter> filterClass, String pattern) {
    return addFilter(filterClass, pattern, new Properties());
  }

  public WebAppConfigurationBuilder addFilter(
      Class<? extends Filter> filterClass, String pattern,
      String key1, String value1) {
    Properties initParams = new Properties();
    initParams.put(key1, value1);
    return addFilter(filterClass, pattern, initParams);
  }


  public WebAppConfigurationBuilder addFilter(
      Class<? extends Filter> filterClass, String pattern,
      String key1, String value1,
      String key2, String value2) {
    Properties initParams = new Properties();
    initParams.put(key1, value1);
    initParams.put(key2, value2);
    return addFilter(filterClass, pattern, initParams);
  }


  public WebAppConfigurationBuilder addFilter(
      Class<? extends Filter> filterClass,
      String pattern,
      Properties initParams) {
    MutableWebAppFilter filter = MutableWebAppFilter.create();
    filter.setFilterName(filterClass.getName());
    filter.setFilterClass(filterClass.getName());
    MutableWebAppInitParam initParam;
    for (Object okey : initParams.keySet()) {
      String key = okey.toString();
      String value = initParams.getProperty(key);
      initParam = MutableWebAppInitParam.create();
      initParam.setParamName(key);
      initParam.setParamValue(value);
      filter.addInitParam(initParam);
    }
    config.addFilter(filter);
    MutableWebappFilterMapping filterMapping
        = MutableWebappFilterMapping.create();
    filterMapping.setFilterName(filterClass.getName());
    filterMapping.setUrlPattern(pattern);
    config.addFilterMapping(filterMapping);
    return this;
  }

  public WebAppConfigurationBuilder addServlet(
      Class<? extends Servlet> servletClass, String pattern) {
    return addServlet(servletClass.getName(), servletClass, pattern);
  }

  public WebAppConfigurationBuilder addServlet(
      String servletName,
      Class<? extends Servlet> servletClass,
      String pattern) {
    return addServlet(servletName, servletClass, pattern, new Properties());
  }

  public WebAppConfigurationBuilder addServlet(
      Class<? extends Servlet> servletClass, String pattern,
      String key1, String value1) {
    return addServlet(
        servletClass.getName(), servletClass, pattern, key1, value1);
  }

  public WebAppConfigurationBuilder addServlet(
      String servletName, Class<? extends Servlet> servletClass, String pattern,
      String key1, String value1) {
    Properties initParams = new Properties();
    initParams.put(key1, value1);
    return addServlet(servletName, servletClass, pattern, initParams);
  }

  public WebAppConfigurationBuilder unsafe_addServlet(
      String servletName, String whatClaimsToBeAServletClass, String pattern,
      String key1, String value1) {
    Properties initParams = new Properties();
    initParams.put(key1, value1);
    return unsafe_addServlet(servletName, whatClaimsToBeAServletClass, pattern, initParams);
  }

  public WebAppConfigurationBuilder addServlet(
      Class<? extends Servlet> servletClass, String pattern,
      String key1, String value1,
      String key2, String value2) {
    return addServlet(servletClass.getName(), servletClass, pattern,
        key1, value1,
        key2, value2);
  }

  public WebAppConfigurationBuilder addServlet(
      String servletName, Class<? extends Servlet> servletClass, String pattern,
      String key1, String value1,
      String key2, String value2) {
    Properties initParams = new Properties();
    initParams.put(key1, value1);
    initParams.put(key2, value2);
    return addServlet(servletName, servletClass, pattern, initParams);
  }


  public WebAppConfigurationBuilder addServlet(
      Class<? extends Servlet> servletClass, String pattern,
      Properties initParams) {
    return addServlet(
        servletClass.getName(), servletClass, pattern, initParams);
  }

  public WebAppConfigurationBuilder addServlet(
      String servletName, Class<? extends Servlet> servletClass,
      String pattern, Properties initParams) {
    MutableWebAppServlet servlet = MutableWebAppServlet.create();
    servlet.setServletName(servletName);
    servlet.setServletClass(servletClass.getName());
    MutableWebAppInitParam initParam;
    for (Object okey : initParams.keySet()) {
      String key = okey.toString();
      String value = initParams.getProperty(key);
      initParam = MutableWebAppInitParam.create();
      initParam.setParamName(key);
      initParam.setParamValue(value);
      servlet.addInitParam(initParam);
    }
    config.addServlet(servlet);
    MutableWebAppServletMapping servletMapping
        = MutableWebAppServletMapping.create();
    servletMapping.setServletName(servletName);
    servletMapping.setUrlPattern(pattern);
    config.addServletMapping(servletMapping);
    return this;
  }

  public WebAppConfigurationBuilder unsafe_addServlet(
      String servletName, String whatClaimsToBeAServletClass,
      String pattern, Properties initParams) {
    MutableWebAppServlet servlet = MutableWebAppServlet.create();
    servlet.setServletName(servletName);
    servlet.setServletClass(whatClaimsToBeAServletClass);
    MutableWebAppInitParam initParam;
    for (Object okey : initParams.keySet()) {
      String key = okey.toString();
      String value = initParams.getProperty(key);
      initParam = MutableWebAppInitParam.create();
      initParam.setParamName(key);
      initParam.setParamValue(value);
      servlet.addInitParam(initParam);
    }
    config.addServlet(servlet);
    MutableWebAppServletMapping servletMapping
        = MutableWebAppServletMapping.create();
    servletMapping.setServletName(servletName);
    servletMapping.setUrlPattern(pattern);
    config.addServletMapping(servletMapping);
    return this;
  }


  public WebAppConfigurationBuilder addServletContextListener(
      Class<? extends ServletContextListener> listenerClass) {
    MutableWebAppListener listener = MutableWebAppListener.create();
    listener.setListenerClass(listenerClass.getName());
    config.addListener(listener);
    return this;
  }


}
