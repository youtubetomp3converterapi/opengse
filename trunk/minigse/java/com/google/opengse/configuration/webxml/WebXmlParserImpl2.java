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

package com.google.opengse.configuration.webxml;

import com.google.opengse.configuration.WebAppConfiguration;
import com.google.opengse.configuration.WebAppContextParam;
import com.google.opengse.configuration.WebAppErrorPage;
import com.google.opengse.configuration.WebAppFilter;
import com.google.opengse.configuration.WebAppFilterMapping;
import com.google.opengse.configuration.WebAppInitParam;
import com.google.opengse.configuration.WebAppListener;
import com.google.opengse.configuration.WebAppMimeMapping;
import com.google.opengse.configuration.WebAppServlet;
import com.google.opengse.configuration.WebAppServletMapping;
import com.google.opengse.configuration.WebAppSessionConfig;
import com.google.opengse.configuration.WebAppTagLib;
import com.google.opengse.configuration.WebAppWelcomeFileList;
import com.google.opengse.configuration.WebAppSecurityConstraint;
import com.google.opengse.configuration.WebAppWebResourceCollection;
import com.google.opengse.configuration.WebAppAuthConstraint;
import com.google.opengse.configuration.WebAppUserDataConstraint;
import com.google.opengse.configuration.WebAppSecurityRole;
import com.google.opengse.configuration.WebAppSecurityRoleRef;
import com.google.opengse.configuration.impl.MutableWebAppConfiguration;
import com.google.opengse.configuration.impl.MutableWebAppContextParam;
import com.google.opengse.configuration.impl.MutableWebAppErrorPage;
import com.google.opengse.configuration.impl.MutableWebAppFilter;
import com.google.opengse.configuration.impl.MutableWebAppInitParam;
import com.google.opengse.configuration.impl.MutableWebAppListener;
import com.google.opengse.configuration.impl.MutableWebAppMimeMapping;
import com.google.opengse.configuration.impl.MutableWebAppServlet;
import com.google.opengse.configuration.impl.MutableWebAppServletMapping;
import com.google.opengse.configuration.impl.MutableWebAppSessionConfig;
import com.google.opengse.configuration.impl.MutableWebAppTagLib;
import com.google.opengse.configuration.impl.MutableWebAppWelcomeFileList;
import com.google.opengse.configuration.impl.MutableWebappFilterMapping;
import com.google.opengse.configuration.impl.MutableWebAppWebResourceCollection;
import com.google.opengse.configuration.impl.MutableWebAppSecurityConstraint;
import com.google.opengse.configuration.impl.MutableWebAppAuthConstraint;
import com.google.opengse.configuration.impl.MutableWebAppUserDataConstraint;
import com.google.opengse.configuration.impl.MutableWebAppSecurityRole;
import com.google.opengse.configuration.impl.MutableWebappSecurityRoleRef;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

/**
 * An implementation of WebXmlFileParser that uses no third-party xml parsing.
 *
 * @author Mike Jennings
 */
public class WebXmlParserImpl2 implements WebXmlParser {

  public WebXmlParserImpl2() {
    try {
      initialize();
    } catch (NoSuchMethodException e) {
      // this should never happen
      throw new RuntimeException(e);
    }
  }

  public WebAppConfiguration parse(Reader webxml)
      throws IOException, SAXException {
    try {
      Document xml = XMLUtil.readerToDocument(webxml);
      return parse(xml);
    } catch (ParserConfigurationException e) {
      throw new SAXException(e);
    }
  }

  private static SimpleObjectCreator webAppCreator;


  private WebAppConfiguration parse(Document xml)
      throws IOException, SAXException {
    Node webappNode = XMLUtil.findNamedElementNode(xml, "web-app");
    if (webappNode == null) {
      throw new IOException("No web-app element found!");
    }
    return parseWebApp(webappNode);
  }

  private static void initialize() throws NoSuchMethodException {
    if (webAppCreator != null) {
      return;
    }
    webAppCreator = new WebAppConfigurationCreator();
    webAppCreator.add("display-name",
        new SetStringViaMethod(
            MutableWebAppConfiguration.class, "setDisplayName"));
    webAppCreator.add("description",
        new SetStringViaMethod(
            MutableWebAppConfiguration.class, "setDescription"));
    webAppCreator.add("context-param", new ContextParamParser());
    webAppCreator.add("listener", new WebappListener());
    webAppCreator.add("servlet", new WebappServlet());
    webAppCreator.add("servlet-mapping", new WebappServletMapping());
    webAppCreator.add("filter-mapping", new WebappFilterMapping());
    webAppCreator.add("welcome-file-list", new WebappWelcomeFile());
    webAppCreator.add("error-page", new ErrorPage());
    webAppCreator.add("mime-mapping", new WebappMimeMapping());
    webAppCreator.add("taglib", new TaglibParser());
    webAppCreator.add("filter", new WebappFilter());
    webAppCreator.add("session-config", new SessionConfigParser());
    webAppCreator.add("security-constraint", new SecurityConstraintParser());
    webAppCreator.add("security-role", new SecurityRoleParser());
  }

  private WebAppConfiguration parseWebApp(Node webappNode) throws SAXException {
    return (WebAppConfiguration) webAppCreator.create(webappNode);
  }



  private static class WebAppConfigurationCreator extends SimpleObjectCreator {
    WebAppConfigurationCreator() throws NoSuchMethodException {
      super(MutableWebAppConfiguration.class);
    }
  }

  private static interface NodeParser {
    void parse(Object context, Node node) throws SAXException;
  }

  private static class SimpleNodeParser implements NodeParser {
    private final Method method;
    private final ObjectCreator creator;

    private SimpleNodeParser(
        Class<?> clazz, String methodName, ObjectCreator creator)
        throws NoSuchMethodException {
      this.creator = creator;
      method = clazz.getMethod(methodName, creator.getCreatedClass());
    }

    public void parse(Object context, Node node) throws SAXException {
      Object arg = creator.create(node);
      try {
        if (arg instanceof String) {
          arg = ((String) arg).trim().intern();
        }
        method.invoke(context, arg);
      } catch (IllegalAccessException e) {
        throw new SAXException(e);
      } catch (InvocationTargetException e) {
        throw new SAXException(e);
      }
    }
  }


  private static class SetStringViaMethod extends SimpleNodeParser {
    private SetStringViaMethod(Class<?> clazz, String methodName)
        throws NoSuchMethodException {
      super(clazz, methodName, new StringCreator());
    }
  }

  private static class WebappServletMapping extends SimpleNodeParser {
    private WebappServletMapping() throws NoSuchMethodException {
      super(MutableWebAppConfiguration.class,
            "addServletMapping",
            new WebAppServletMappingCreator());
    }
  }

  private static class WebappFilterMapping extends SimpleNodeParser {
    private WebappFilterMapping() throws NoSuchMethodException {
      super(MutableWebAppConfiguration.class,
            "addFilterMapping",
            new WebAppFilterMappingCreator());
    }
  }

  private static class WebappWelcomeFile extends SimpleNodeParser {
    private WebappWelcomeFile() throws NoSuchMethodException {
      super(MutableWebAppConfiguration.class,
            "setWelcomeFileList",
            new WebAppWelcomeFileListCreator());
    }
  }

  private static class ErrorPage extends SimpleNodeParser {
    private ErrorPage() throws NoSuchMethodException {
      super(MutableWebAppConfiguration.class,
            "addErrorPage",
            new ErrorPageCreator());
    }
  }

  private static class WebappMimeMapping extends SimpleNodeParser {
    private WebappMimeMapping() throws NoSuchMethodException {
      super(MutableWebAppConfiguration.class,
          "addMimeMapping",
          new WebAppMimeMappingCreator());
    }
  }

  private static class TaglibParser extends SimpleNodeParser {
    private TaglibParser() throws NoSuchMethodException {
      super(MutableWebAppConfiguration.class,
          "addTagLib",
          new WebAppTaglibCreator());
    }
  }


  private static class WebappListener extends SimpleNodeParser {
    private WebappListener() throws NoSuchMethodException {
      super(MutableWebAppConfiguration.class,
            "addListener",
            new WebAppListenerCreator());
    }
  }

  private static class SessionConfigParser extends SimpleNodeParser {
    private SessionConfigParser() throws NoSuchMethodException {
      super(MutableWebAppConfiguration.class,
            "setSessionConfig",
            new SessionConfigCreator());
    }
  }

  private static class WebappServlet implements NodeParser {
    private final ObjectCreator creator;

    private WebappServlet() throws NoSuchMethodException {
      creator = new WebAppServletCreator();
    }

    public void parse(Object context, Node webappSubnode) throws SAXException {
      MutableWebAppConfiguration webapp = (MutableWebAppConfiguration) context;
      WebAppServlet servlet = (WebAppServlet) creator.create(webappSubnode);
      if (servlet != null) {
        webapp.addServlet(servlet);
      }
    }
  }



  private static class WebappFilter implements NodeParser {
    private final ObjectCreator creator;

    private WebappFilter() throws NoSuchMethodException {
      creator = new WebAppFilterCreator();
    }

    public void parse(Object context, Node webappSubnode) throws SAXException {
      MutableWebAppConfiguration webapp = (MutableWebAppConfiguration) context;
      WebAppFilter filter = (WebAppFilter) creator.create(webappSubnode);
      if (filter != null) {
        webapp.addFilter(filter);
      }
    }
  }


  private static class ContextParamParser implements NodeParser {
    private final ObjectCreator creator;

    ContextParamParser() throws NoSuchMethodException {
      creator = new WebAppContextParamCreator();
    }

    public void parse(Object context, Node webappSubnode) throws SAXException {
      MutableWebAppConfiguration webapp = (MutableWebAppConfiguration) context;
      WebAppContextParam contextParam
          = (WebAppContextParam) creator.create(webappSubnode);
      if (contextParam != null) {
        webapp.addContextParam(contextParam);
      }
    }
  }

  private static class SecurityConstraintParser implements NodeParser {
    private final ObjectCreator creator;

    SecurityConstraintParser() throws NoSuchMethodException {
      creator = new SecurityConstraintCreator();
    }

    public void parse(Object context, Node webappSubnode) throws SAXException {
      MutableWebAppConfiguration webapp = (MutableWebAppConfiguration) context;
      WebAppSecurityConstraint securityConstraint
          = (WebAppSecurityConstraint) creator.create(webappSubnode);
      if (securityConstraint != null) {
        webapp.addSecurityConstraint(securityConstraint);
      }
    }
  }


  private static class SecurityConstraintCreator extends SimpleObjectCreator {
    private SecurityConstraintCreator() throws NoSuchMethodException {
      super(MutableWebAppSecurityConstraint.class);
      add("web-resource-collection", new WebResourceCollectionParser());
      add("auth-constraint", new AuthConstraintParser());
      add("user-data-constraint", new UserDataConstraintParser());
    }
  }


  private static class SecurityRoleParser implements NodeParser {
    private final ObjectCreator creator;

    SecurityRoleParser() throws NoSuchMethodException {
      creator = new SecurityRoleCreator();
    }

    public void parse(Object context, Node webappSubnode) throws SAXException {
      MutableWebAppConfiguration webapp = (MutableWebAppConfiguration) context;
      WebAppSecurityRole securityRole
          = (WebAppSecurityRole) creator.create(webappSubnode);
      if (securityRole != null) {
        webapp.addSecurityRole(securityRole);
      }
    }
  }


  private static class SecurityRoleCreator extends SimpleObjectCreator {
    private SecurityRoleCreator() throws NoSuchMethodException {
      super(MutableWebAppSecurityRole.class);
      setStringViaMethod("role-name", "setRoleName");
    }
  }


  private static class WebResourceCollectionParser implements NodeParser {
    private final ObjectCreator creator;

    WebResourceCollectionParser() throws NoSuchMethodException {
      creator = new WebResourceCollectionCreator();
    }

    public void parse(Object context, Node webappSubnode) throws SAXException {
      MutableWebAppSecurityConstraint constraint = (MutableWebAppSecurityConstraint) context;
      WebAppWebResourceCollection webResourceCollection
          = (WebAppWebResourceCollection) creator.create(webappSubnode);
      if (webResourceCollection != null) {
        constraint.addWebResourceCollection(webResourceCollection);
      }
    }
  }


  private static class WebResourceCollectionCreator extends SimpleObjectCreator {
    private WebResourceCollectionCreator() throws NoSuchMethodException {
      super(MutableWebAppWebResourceCollection.class);
      setStringViaMethod("web-resource-name", "setWebResourceName");
      setStringViaMethod("url-pattern", "addUrlPattern");
      setStringViaMethod("http-method", "addHttpMethod");
    }
  }


  private static class UserDataConstraintParser implements NodeParser {
    private final ObjectCreator creator;

    UserDataConstraintParser() throws NoSuchMethodException {
      creator = new UserDataConstraintCreator();
    }

    public void parse(Object context, Node webappSubnode) throws SAXException {
      MutableWebAppSecurityConstraint constraint = (MutableWebAppSecurityConstraint) context;
      WebAppUserDataConstraint userConstraint
          = (WebAppUserDataConstraint) creator.create(webappSubnode);
      if (userConstraint != null) {
        constraint.setUserDataConstraint(userConstraint);
      }
    }
  }


  private static class UserDataConstraintCreator extends SimpleObjectCreator {
    private UserDataConstraintCreator() throws NoSuchMethodException {
      super(MutableWebAppUserDataConstraint.class);
      setStringViaMethod("transport-guarantee", "setTransportGuarantee");
    }
  }




  private static class AuthConstraintParser implements NodeParser {
    private final ObjectCreator creator;

    AuthConstraintParser() throws NoSuchMethodException {
      creator = new AuthConstraintCreator();
    }

    public void parse(Object context, Node webappSubnode) throws SAXException {
      MutableWebAppSecurityConstraint constraint = (MutableWebAppSecurityConstraint) context;
      WebAppAuthConstraint authConstraint
          = (WebAppAuthConstraint) creator.create(webappSubnode);
      if (authConstraint != null) {
        constraint.setAuthConstraint(authConstraint);
      }
    }
  }


  private static class AuthConstraintCreator extends SimpleObjectCreator {
    private AuthConstraintCreator() throws NoSuchMethodException {
      super(MutableWebAppAuthConstraint.class);
      setStringViaMethod("role-name", "addRoleName");
    }
  }


  private static class WebAppServletMappingCreator extends SimpleObjectCreator {
    WebAppServletMappingCreator() throws NoSuchMethodException {
      super(MutableWebAppServletMapping.class, WebAppServletMapping.class);
      add("servlet-name",
          new SetStringViaMethod(
              MutableWebAppServletMapping.class, "setServletName"));
      add("url-pattern",
          new SetStringViaMethod(
              MutableWebAppServletMapping.class, "setUrlPattern"));
    }
  }

  private static class WebAppFilterMappingCreator extends SimpleObjectCreator {
    WebAppFilterMappingCreator() throws NoSuchMethodException {
      super(MutableWebappFilterMapping.class, WebAppFilterMapping.class);
      add("filter-name",
          new SetStringViaMethod(
              MutableWebappFilterMapping.class, "setFilterName"));
      add("url-pattern",
          new SetStringViaMethod(
              MutableWebappFilterMapping.class, "setUrlPattern"));
      add("dispatcher",
          new SetStringViaMethod(
              MutableWebappFilterMapping.class, "setDispatcher"));
      add("servlet-name",
          new SetStringViaMethod(
              MutableWebappFilterMapping.class, "setServletName"));
    }
  }

  private static class WebAppWelcomeFileListCreator
      extends SimpleObjectCreator {
    WebAppWelcomeFileListCreator() throws NoSuchMethodException {
      super(MutableWebAppWelcomeFileList.class, WebAppWelcomeFileList.class);
      add("welcome-file",
          new SetStringViaMethod(
              MutableWebAppWelcomeFileList.class, "addWelcomeFile"));
    }
  }

  private static class ErrorPageCreator extends SimpleObjectCreator {
    ErrorPageCreator() throws NoSuchMethodException {
      super(MutableWebAppErrorPage.class, WebAppErrorPage.class);
      setStringViaMethod("error-code", "setErrorCode");
      setStringViaMethod("location", "setLocation");
      setStringViaMethod("exception-type", "setExceptionType");
    }
  }

  private static class WebAppFilterCreator extends SimpleObjectCreator {
    WebAppFilterCreator() throws NoSuchMethodException {
      super(MutableWebAppFilter.class);
      setStringViaMethod("filter-name", "setFilterName");
      setStringViaMethod("filter-class", "setFilterClass");
      setStringViaMethod("display-name", "setDisplayName");
      setStringViaMethod("description", "setDescription");
      add("init-param", new InitParamParser(MutableWebAppFilter.class));
    }
  }

  private static class WebAppServletCreator extends SimpleObjectCreator {
    WebAppServletCreator() throws NoSuchMethodException {
      super(MutableWebAppServlet.class);
      setStringViaMethod("servlet-name", "setServletName");
      setStringViaMethod("servlet-class", "setServletClass");
      setStringViaMethod("load-on-startup", "setLoadOnStartup");
      setStringViaMethod("display-name", "setDisplayName");
      setStringViaMethod("jsp-file", "setJspFile");
      setStringViaMethod("description", "setDescription");
      add("init-param", new InitParamParser(MutableWebAppServlet.class));
      add("security-role-ref", new SecurityRoleRefParser());
    }
  }



  private static class SecurityRoleRefParser implements NodeParser {
    private final ObjectCreator creator;

    SecurityRoleRefParser() throws NoSuchMethodException {
      creator = new SecurityRoleRefCreator();
    }

    public void parse(Object context, Node webappSubnode) throws SAXException {
      MutableWebAppServlet servlet = (MutableWebAppServlet) context;
      WebAppSecurityRoleRef securityRoleRef
          = (WebAppSecurityRoleRef) creator.create(webappSubnode);
      if (securityRoleRef != null) {
        servlet.addSecurityRoleRef(securityRoleRef);
      }
    }
  }


  private static class SecurityRoleRefCreator extends SimpleObjectCreator {
    private SecurityRoleRefCreator() throws NoSuchMethodException {
      super(MutableWebappSecurityRoleRef.class);
      setStringViaMethod("role-name", "setRoleName");
      setStringViaMethod("role-link", "setRoleLink");
    }
  }



  private static class InitParamParser extends SimpleNodeParser {
    private InitParamParser(Class<?> mutableParentClass)
        throws NoSuchMethodException {
      super(mutableParentClass,
            "addInitParam",
            new WebAppInitParamCreator());
    }
  }


  private static class WebAppInitParamCreator extends SimpleObjectCreator {
    WebAppInitParamCreator() throws NoSuchMethodException {
      super(MutableWebAppInitParam.class, WebAppInitParam.class);
      setStringViaMethod("param-name", "setParamName");
      setStringViaMethod("param-value", "setParamValue");
    }
  }

  private static class WebAppMimeMappingCreator extends SimpleObjectCreator {
    WebAppMimeMappingCreator() throws NoSuchMethodException {
      super(MutableWebAppMimeMapping.class, WebAppMimeMapping.class);
      setStringViaMethod("mime-type", "setMimeType");
      setStringViaMethod("extension", "setExtension");
    }
  }

  private static class WebAppTaglibCreator extends SimpleObjectCreator {
    WebAppTaglibCreator() throws NoSuchMethodException {
      super(MutableWebAppTagLib.class, WebAppTagLib.class);
      setStringViaMethod("taglib-uri", "setTaglibUri");
      setStringViaMethod("taglib-location", "setTaglibLocation");
    }
  }

  private static interface ObjectCreator {
    Object create(Node node) throws SAXException;
    Class<?> getCreatedClass();
  }

  private static class StringCreator implements ObjectCreator {
    public Object create(Node node) throws SAXException {
      return XMLUtil.getChildTextNodes(node);
    }

    public Class<?> getCreatedClass() {
      return String.class;
    }
  }

  private static class SimpleObjectCreator implements ObjectCreator {
    private final Map<String, NodeParser> subParsers;
    private final Method createMethod;
    private final Class<?> mutableClass;
    private final Class<?> createdClass;

    SimpleObjectCreator(Class<?> clazz) throws NoSuchMethodException {
      this(clazz, clazz);
    }

    SimpleObjectCreator(Class<?> mutableClass, Class<?> createdClass)
        throws NoSuchMethodException {
      this.mutableClass = mutableClass;
      this.createdClass = createdClass;
      this.subParsers = new HashMap<String, NodeParser>();
      createMethod = mutableClass.getMethod("create");
    }

    void setStringViaMethod(String xmlTag, String methodName)
        throws NoSuchMethodException {
      add(xmlTag,
          new SetStringViaMethod(mutableClass, methodName));

    }

    public Class<?> getCreatedClass() {
      return createdClass;
    }

    public void add(String name, NodeParser parser) {
      subParsers.put(name, parser);
    }

    public Object create(Node node) throws SAXException {
      Object thisObject;
      try {
        thisObject = createMethod.invoke(null);
      } catch (IllegalAccessException e) {
        throw new SAXException(e);
      } catch (InvocationTargetException e) {
        throw new SAXException(e);
      }
      Node[] elnodes = XMLUtil.getChildNodes(node, Node.ELEMENT_NODE);
      for (Node childNode : elnodes) {
        NodeParser nodeParser = subParsers.get(childNode.getNodeName());
        if (nodeParser == null) {
          throw new SAXException(
              "Don't know how to process <" + childNode.getNodeName()
              + "> using " + mutableClass.getName());
        }
        nodeParser.parse(thisObject, childNode);
      }
      return thisObject;
    }
  }


  private static class WebAppListenerCreator extends SimpleObjectCreator {
    private WebAppListenerCreator() throws NoSuchMethodException {
      super(MutableWebAppListener.class, WebAppListener.class);
      this.add("listener-class",
          new SetStringViaMethod(
              MutableWebAppListener.class, "setListenerClass"));
    }
  }

  private static class SessionConfigCreator extends SimpleObjectCreator {
    private SessionConfigCreator() throws NoSuchMethodException {
      super(MutableWebAppSessionConfig.class, WebAppSessionConfig.class);
      setStringViaMethod("session-timeout", "setSessionTimeout");
    }
  }


  private static class WebAppContextParamCreator extends SimpleObjectCreator {
    private WebAppContextParamCreator() throws NoSuchMethodException {
      super(MutableWebAppContextParam.class);
      this.add("param-name",
          new SetStringViaMethod(
              MutableWebAppContextParam.class, "setParamName"));
      this.add("param-value",
          new SetStringViaMethod(
              MutableWebAppContextParam.class, "setParamValue"));
      this.add("description",
          new SetStringViaMethod(
              MutableWebAppContextParam.class, "setDescription"));
    }
  }







}