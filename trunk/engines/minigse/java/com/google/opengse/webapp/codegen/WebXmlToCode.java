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

package com.google.opengse.webapp.codegen;

import com.google.opengse.util.PropertiesUtil;
import com.google.opengse.configuration.webxml.WebAppConfigurationFactory;
import com.google.opengse.configuration.*;
import com.google.opengse.webapp.WebAppConfigurationBuilder;

import java.util.Properties;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.io.File;
import java.io.PrintWriter;

/**
 * Generates code for configuring a web app programatically given a
 * WEB-INF/web.xml file.
 *
 * @author Mike Jennings
 */
public final class WebXmlToCode {
  private WebXmlToCode() { throw new AssertionError(); }

  private static final Logger LOGGER
      = Logger.getLogger(WebXmlToCode.class.getName());

  public static void main(String[] args) throws Exception {
    // get our configuration
    Properties props
        = PropertiesUtil.getPropertiesFromCommandLine(args);
    // verify we have the minimum config
    requireProperties(props, "contextdir", "codedir", "classname");
    File contextdir = PropertiesUtil.getFile(props, "contextdir");
    File webinf = new File(contextdir, "WEB-INF");
    if (!webinf.exists() || !webinf.isDirectory()) {
      LOGGER.severe("'" + webinf + "' does not appear to be a directory");
      return;
    }
    File webxml = new File(webinf, "web.xml");
    if (!webxml.exists() || !webxml.isFile()) {
      LOGGER.severe("'" + webxml + "' does not appear to be a file");
      return;
    }
    File codedir = PropertiesUtil.getFile(props, "codedir");
    if (!codedir.exists() || !codedir.isDirectory()) {
      LOGGER.severe("'" + codedir + "' does not appear to be a directory");
      return;
    }
    String classname = props.getProperty("classname");
    if (classname.contains(" ")
        || classname.contains("/")
        || classname.contains("\\")
        || classname.contains("$")) {
      LOGGER.severe("'" + classname + "' is not a valid classname");
      return;
    }
    int lastdot = classname.lastIndexOf('.');
    if (lastdot == -1) {
      LOGGER.severe("Classname '" + classname
          + "' implies the default package, which is unsupported.");
      return;
    }
    String pkg = classname.substring(0, lastdot);
    classname = classname.substring(lastdot + 1);
    File pkgdir = new File(codedir, pkg.replace('.', File.separatorChar));
    if (!pkgdir.exists() || !pkgdir.isDirectory()) {
      LOGGER.severe("'" + pkgdir + "' does not appear to be a directory");
      return;
    }
    if (Character.isLowerCase(classname.charAt(0))) {
      LOGGER.severe(classname + " does not start with an uppercase character");
      return;
    }

    WebAppConfiguration config
        = WebAppConfigurationFactory.getConfiguration(contextdir);
    // now we can write the code here
    File javaFile = new File(pkgdir, classname + ".java");
    PrintWriter out = new PrintWriter(javaFile);
    ClassDefinition classdef = new ClassDefinition(pkg, classname);
    classdef.addImport(WebAppConfigurationBuilder.class);
    classdef.addImport(WebAppConfiguration.class);
    classdef.addImport(Properties.class);
    MethodDefinition method = new MethodDefinition("getConfiguration");
    classdef.addMethod(method);
    writeCodeFromWebXML(config, method);
    classdef.write(out);
    out.close();
  }

  private static void writeCodeFromWebXML(
      WebAppConfiguration config, MethodDefinition method)
      throws WebAppConfigurationException {
    method.setStatic(true);
    method.setReturnType(WebAppConfiguration.class);
    method.addLine(
        "WebAppConfigurationBuilder cb = new WebAppConfigurationBuilder();");
    writeContextParamsCode(config, method);
    writeServletCode(config, method);
    method.addLine("return cb.getConfiguration();");
  }

  private static void writeServletCode(
      WebAppConfiguration config, MethodDefinition method)
      throws WebAppConfigurationException {
    // do NOT change the following line to Maps.newTreeMap()
    Map<String, WebAppServlet> servletNameToDef
        = new TreeMap<String, WebAppServlet>();
    for (WebAppServlet servletDef : config.getServlets()) {
      servletNameToDef.put(servletDef.getServletName(), servletDef);
    }
    // do NOT change the following to Maps.newTreeMap()
    Map<String, WebAppServletMapping> servletNameToMapping
        = new TreeMap<String, WebAppServletMapping>();
    for (WebAppServletMapping servletMapping : config.getServletMappings()) {
      servletNameToMapping.put(servletMapping.getServletName(), servletMapping);
    }
    for (String servletName : servletNameToMapping.keySet()) {
      WebAppServlet servletDef = servletNameToDef.get(servletName);
      if (servletDef == null) {
        throw new WebAppConfigurationException(
            "Cannot find servlet '" + servletName + "'");
      }
      if (servletDef.getServletClass() == null) {
        String jspFile = servletDef.getJspFile();
        if (jspFile == null) {
          throw new WebAppConfigurationException(
              "Bad servlet definition '" + servletName + "'");
        }
        method.addLine(
            "// need to handle jsp-file '" + jspFile
            + "' for servlet-name " + servletName);
      } else {
        String classname = servletDef.getServletClass().trim();
        WebAppServletMapping mapping = servletNameToMapping.get(servletName);
        String urlpattern = mapping.getUrlPattern().trim();
        WebAppInitParam[] initParams = servletDef.getInitParams();
        if (initParams.length == 0) {
          method.addLine(
              "cb.addServlet(\""
                  + servletName + "\", "
                  + classname + ".class, \""
                  + urlpattern + "\");");
        } else if (initParams.length == 1) {
          String key1 = initParams[0].getParamName();
          String value1 = initParams[0].getParamValue();
          method.addLine(
              "cb.addServlet(\""
                  + servletName + "\", "
                  + classname + ".class, \""
                  + urlpattern + "\",\""
                  + key1 + "\",\""
                  + value1 + "\");");
        } else if (initParams.length == 2) {
          String key1 = initParams[0].getParamName();
          String value1 = initParams[0].getParamValue();
          String key2 = initParams[1].getParamName();
          String value2 = initParams[1].getParamValue();
          method.addLine(
              "cb.addServlet(\""
                  + servletName + "\", "
                  + classname + ".class, \""
                  + urlpattern + "\",\""
                  + key1 + "\",\""
                  + value1 + "\",\""
                  + key2 + "\",\""
                  + value2 + "\");");
        } else {
          method.addLine("  {");
          method.addLine("    Properties initParams = new Properties();");
          for (WebAppInitParam initParam : initParams) {
            method.addLine(
                "    initParams.setProperty(\"" + initParam.getParamName()
                    + "\", \"" + initParam.getParamValue() + "\");");
          }
          method.addLine(
              "    cb.addServlet(\""
                  + servletName + "\", "
                  + classname + ".class, \""
                  + urlpattern + "\", initParams);");
          method.addLine("  }");
        }
      }
    }
  }

  private static void writeContextParamsCode(
      WebAppConfiguration config, MethodDefinition method) {
    WebAppContextParam[] contextParams = config.getContextParams();
    int i;
    int last = contextParams.length - 1;
    for (i = 0; i < contextParams.length; ++i) {
      addContextParam(
          contextParams[i],
          method,
          (i == 0) ? "cb" : "",
          (i == last) ? ";" : "");
    }
  }

  private static void addContextParam(WebAppContextParam contextParam,
                                      MethodDefinition method,
                                      String prefix, String suffix) {
    method.addLine(prefix + ".addContextParam(\""
          + contextParam.getParamName() + "\","
          + "\"" + contextParam.getParamValue() + "\")"
          + suffix);
  }

  private static void requireProperties(
      Properties props, String... keys) throws Exception {
    for (String key : keys) {
      String value = props.getProperty(key);
      if (value == null) {
        throw new IllegalArgumentException("No property value for " + key);
      }
    }
  }

}
