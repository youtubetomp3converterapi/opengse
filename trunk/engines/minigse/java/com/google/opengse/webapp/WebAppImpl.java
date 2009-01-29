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

import com.google.opengse.classloader.JasperHack;
import com.google.opengse.configuration.WebAppConfiguration;
import com.google.opengse.configuration.WebAppConfigurationException;
import com.google.opengse.configuration.WebAppContextParam;
import com.google.opengse.configuration.WebAppErrorPage;
import com.google.opengse.configuration.WebAppFilter;
import com.google.opengse.configuration.WebAppFilterMapping;
import com.google.opengse.configuration.WebAppListener;
import com.google.opengse.configuration.WebAppServlet;
import com.google.opengse.configuration.WebAppServletMapping;
import com.google.opengse.configuration.webxml.WebAppConfigurationCombiner;
import com.google.opengse.filters.RegularExpressionRequestHandler;
import com.google.opengse.filters.RegularExpressionRequestHandlerDispatcher;
import com.google.opengse.handlers.NotFoundHandler;
import com.google.opengse.webapp.listeners.HttpSessionAttributeListenerList;
import com.google.opengse.webapp.listeners.HttpSessionListenerList;
import com.google.opengse.webapp.listeners.ServletContextAttributeListenerList;
import com.google.opengse.webapp.listeners.ServletContextListenerList;
import com.google.opengse.webapp.listeners.ServletRequestAttributeListenerList;
import com.google.opengse.webapp.listeners.ServletRequestListenerList;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;

/**
 * An implementation of WebApp.
 *
 * @author Mike Jennings
 */
final class WebAppImpl implements WebApp {
  private static final Logger LOGGER = Logger
      .getLogger(WebAppImpl.class.getName());

  private final String uriPrefix;
  private final File contextbase;
  private final ClassLoader classLoader;
  private final WebAppConfiguration config;
  private final ServletContext context;
  private final RegularExpressionRequestHandlerDispatcher regexHandler;
  private final ServletManager manager;
  private LoggerFactory loggerFactory;
  private final Hashtable<String, String> contextParams;
  private final ServletContextAttributeListenerList scaListeners;
  private final ServletContextListenerList scListeners;
  private final ServletRequestListenerList srListeners;
  private final ServletRequestAttributeListenerList sraListeners;
  private final HttpSessionAttributeListenerList sessionAttributeListeners;
  private final HttpSessionListenerList sessionListeners;
  private static final String JASPER_CLASSPATH_ATTRIBUTE =
      "org.apache.catalina.jsp_classpath";

  private WebAppImpl(String uriPrefix, File contextbase,
                     ClassLoader classLoader, WebAppConfiguration wac,
                     ServletContainerContext containerContext)
      throws WebAppConfigurationException, ClassNotFoundException,
      InstantiationException, IllegalAccessException {
    this.uriPrefix = uriPrefix;
    this.contextbase = contextbase;
    URLClassLoader jasperFriendlyClassLoader =
        makeTheClassLoaderThatJasperExpects(classLoader);
    this.classLoader = jasperFriendlyClassLoader;
    this.config = wac;
    regexHandler =
        RegularExpressionRequestHandlerDispatcher.create(new NotFoundHandler());

    context =
        ServletContextImpl
            .create(containerContext, new WebAppContextImpl(this));
    manager = new ServletManager();
    contextParams = new Hashtable<String, String>();
    scaListeners = new ServletContextAttributeListenerList();
    scListeners = new ServletContextListenerList();
    srListeners = new ServletRequestListenerList();
    sessionAttributeListeners = new HttpSessionAttributeListenerList();
    sessionListeners = new HttpSessionListenerList();
    sraListeners = new ServletRequestAttributeListenerList();
    loadListeners();
    initContextParams();
    loadServletClasses();
    loadFilterClasses();
    loadErrorPages();
    try {
      putTheClasspathSomewhereJasperCanFindIt(context, jasperFriendlyClassLoader);
    } catch (RuntimeException ex) {
      // TODO: we need make the jasper support optional and more robust
    }
  }

  private static File findTheToolsJarThatJasperNeeds() {
    File javahome = new File(System.getProperty("java.home"));
    File javalibdir = new File(javahome, "lib");
    File toolsjar = new File(javalibdir, "tools.jar");
    if (!toolsjar.exists()) {
      javalibdir = new File(javahome.getParentFile(), "lib");
      toolsjar = new File(javalibdir, "tools.jar");
      if (toolsjar.exists()) {
        return toolsjar;
      }
    }
    return null; // couldn't detect a tools.jar
  }

  /**
   * Jasper looks for the magic context attribute called
   * "org.apache.catalina.jsp_classpath" so that it can rummage through the jar
   * files, so we need to put it there (sigh).
   *
   * @param context
   * @param classLoader
   */
  private static void putTheClasspathSomewhereJasperCanFindIt(
      ServletContext context, URLClassLoader classLoader) {
    File toolsjar = findTheToolsJarThatJasperNeeds();
    // for now we'll just put the location of tools.jar in the magic context
    // property for Jasper
    if (toolsjar != null) {
      context.setAttribute(JASPER_CLASSPATH_ATTRIBUTE, toolsjar.toString());
    }
  }

  /**
   * In JspRuntimeContext there is the following line:
   * org.apache.jasper.compiler.parentClassLoader = (URLClassLoader)
   * Thread.currentThread().getContextClassLoader();
   * <p/>
   * Which means that if you want to use Jasper, you need to ensure that the
   * context class loader is a subclass of URLClassLoader.
   *
   * @param classLoader
   * @throws WebAppConfigurationException
   */
  private static URLClassLoader makeTheClassLoaderThatJasperExpects(
      ClassLoader classLoader) throws WebAppConfigurationException {
    if (classLoader instanceof URLClassLoader) {
      return (URLClassLoader) classLoader;
    }
    if (classLoader instanceof JasperHack) {
      JasperHack ugly = (JasperHack) classLoader;
      URL[] classLoaderUrls = null;
      try {
        classLoaderUrls = ugly.getUrls();
      } catch (IOException ex) {
        throw new WebAppConfigurationException(ex);
      }
      URLClassLoader whatJasperExpects =
          new URLClassLoader(classLoaderUrls, classLoader);
      // uncomment the following line if Jasper has trouble finding TLDs etc.
      // (ie. if jstl does not work)
      // System.setProperty("java.class.path",
      // getClassPathFromClassLoader(whatJasperExpects));
      return whatJasperExpects;
    }
    throw new WebAppConfigurationException("I can't figure out"
        + " how to turn that class loader into something that Jasper needs");
  }


  ServletContextAttributeListener getGlobalServletContextAttributeListener() {
    return scaListeners;
  }

  private void loadListeners() throws ClassNotFoundException,
      IllegalAccessException, InstantiationException,
      WebAppConfigurationException {
    loadListeners(config);
  }

  private void loadListeners(WebAppConfiguration wac)
      throws ClassNotFoundException, IllegalAccessException,
      InstantiationException, WebAppConfigurationException {
    for (WebAppListener listener : wac.getListeners()) {
      String className = listener.getListenerClass();
      Class<?> clazz = classLoader.loadClass(className);
      Object obj = clazz.newInstance();
      boolean used = false;
      if (obj instanceof ServletContextAttributeListener) {
        scaListeners.add((ServletContextAttributeListener) obj);
        used = true;
      }
      if (obj instanceof ServletContextListener) {
        scListeners.add((ServletContextListener) obj);
        used = true;
      }
      if (obj instanceof ServletRequestListener) {
        srListeners.add((ServletRequestListener) obj);
        used = true;
      }
      if (obj instanceof ServletRequestAttributeListener) {
        sraListeners.add((ServletRequestAttributeListener) obj);
        used = true;
      }
      if (obj instanceof HttpSessionAttributeListener) {
        sessionAttributeListeners.add((HttpSessionAttributeListener) obj);
        used = true;
      }
      if (obj instanceof HttpSessionListener) {
        sessionListeners.add((HttpSessionListener) obj);
        used = true;
      }
      if (!used) {
        throw new WebAppConfigurationException("Don't know what to do with '"
            + className + "'");
      }
    }
  }

  private void initContextParams() {
    // populate parameters
    for (WebAppContextParam param : config.getContextParams()) {
      contextParams.put(param.getParamName(), param.getParamValue());
    }
    // fire life-cycle listeners
    scListeners.contextInitialized(new ServletContextEvent(context));
    // fire attribute listeners
    for (WebAppContextParam param : config.getContextParams()) {
      ServletContextAttributeEvent event;
      event =
          new ServletContextAttributeEvent(context, param.getParamName(), param
              .getParamValue());
      scaListeners.attributeAdded(event);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void start() throws ServletException {
    // set the context classloader before initializing the servlets
    Thread.currentThread().setContextClassLoader(classLoader);
    // try to initialize the servlets
    manager.initializeServlets(context);
    // try to initialize the filters
    manager.initializeFilters(context);
    try {
      // process the servlet and filter mappings
      processServletAndFilterMappings();
      // combine the servlet and filter mappings
      manager.combineFilterAndServletMappings(regexHandler);
    } catch (WebAppConfigurationException e) {
      throw new ServletException(e);
    }
  }


  private void loadServletClasses() throws WebAppConfigurationException,
      ClassNotFoundException, IllegalAccessException, InstantiationException {
    loadServletClasses(config);
  }

  private void loadServletClasses(WebAppConfiguration wac)
      throws WebAppConfigurationException,
      IllegalAccessException, InstantiationException {
    for (WebAppServlet servletDef : wac.getServlets()) {
      if (servletDef.getServletName() == null) {
        throw new WebAppConfigurationException("Invalid servlet definition");
      }
      String servletClassName = servletDef.getServletClass();
      String jspFileName = servletDef.getJspFile();
      if (servletClassName == null && jspFileName == null) {
        throw new WebAppConfigurationException(
            "No servet-class or jsp-file for servlet '"
                + servletDef.getServletName() + "'");
      }
      if (servletClassName != null && jspFileName != null) {
        throw new WebAppConfigurationException("servlet '"
            + servletDef.getServletName()
            + "' has both servlet-class and jsp-file elements defined");
      }
      Object servletObject;
      // for jsp-file element, we use the generic JSP servlet class
      if (servletClassName == null) {
        servletClassName = getJspServletClassName(servletDef.getJspFile());
      }
      Class<?> servletClass = null;
      try {
        if (servletClassName != null) {
          servletClass = classLoader.loadClass(servletClassName);
        }
      } catch (ClassNotFoundException e) {
        LOGGER.log(Level.SEVERE, "Cannot find " + servletClassName);
      }
      if (servletClass != null) {
        servletObject = servletClass.newInstance();
        if (!(servletObject instanceof Servlet)) {
          throw new WebAppConfigurationException("'" + servletClassName
              + "' is not a Servlet");
        }
        manager.addServlet(servletDef, (Servlet) servletObject);
      }
    }
  }

  /**
   * Ideally, we should check the globalconfig to find out the generic *.jsp
   * mapping rule and the JspServlet implementation class. In doing so, we will
   * unnecessarily complicated the whole bootstrap, including both API and
   * performance.
   * <p/>
   * This is to say: we don't pretend we can support anything other than Jasper.
   */
  private String getJspServletClassName(String jspFile) {
    return "org.apache.jasper.servlet.JspServlet";
  }

  private void loadErrorPages() {
    loadErrorPages(config);
  }

  private void loadErrorPages(WebAppConfiguration wac) {
    for (WebAppErrorPage errorPage : wac.getErrorPages()) {
      manager.addErrorPage(errorPage);
    }
  }

  private void loadFilterClasses() throws ClassNotFoundException,
      WebAppConfigurationException, InstantiationException,
      IllegalAccessException {
    loadFilterClasses(config);
  }

  private void loadFilterClasses(WebAppConfiguration wac)
      throws WebAppConfigurationException, ClassNotFoundException,
      IllegalAccessException, InstantiationException {
    for (WebAppFilter filterDef : wac.getFilters()) {
      if (filterDef.getFilterName() == null) {
        throw new WebAppConfigurationException("Invalid filter definition");
      }
      String filterClassName = filterDef.getFilterClass();
      if (filterClassName == null) {
        throw new WebAppConfigurationException(
            "No filter-class element defined");
      }
      Class<?> filterClass = classLoader.loadClass(filterClassName);
      Object filterObject = filterClass.newInstance();
      if (!(filterObject instanceof Filter)) {
        throw new WebAppConfigurationException("'" + filterClassName
            + "' is not a Filter");
      }
      manager.addFilter(filterDef, (Filter) filterObject);
    }
  }

  private void processServletAndFilterMappings()
      throws WebAppConfigurationException {
    processServletAndFilterMappings(config);
  }

  private void processServletAndFilterMappings(WebAppConfiguration wac)
      throws WebAppConfigurationException {
    processServletMappings(wac);
    processFilterMappings(wac);
  }


  private void processServletMappings(WebAppConfiguration wac)
      throws WebAppConfigurationException {
    for (WebAppServletMapping mapping : wac.getServletMappings()) {
      manager.addServletMapping(mapping);
    }
  }

  private void processFilterMappings(WebAppConfiguration wac)
      throws WebAppConfigurationException {
    for (WebAppFilterMapping mapping : wac.getFilterMappings()) {
      manager.addFilterMapping(mapping);
    }
  }


  /**
   * Create a WebApp given a URI prefix, a classloader and a configuration
   * object.
   */
  public static WebApp create(String uriPrefix, File contextbase,
                              ClassLoader classLoader, WebAppConfiguration localConfig,
                              ServletContainerContext containerContext)
      throws WebAppConfigurationException, ClassNotFoundException,
      IllegalAccessException, InstantiationException {
    WebAppConfiguration globalConfig;
    try {
      globalConfig =
          GlobalConfigurationFactory.getGlobalConfiguration(classLoader);
    } catch (IOException e) {
      throw new WebAppConfigurationException("", e); 
    }
    localConfig = WebAppConfigurationCombiner.combine(globalConfig, localConfig);
    return new WebAppImpl(uriPrefix, contextbase, classLoader, localConfig,
        containerContext);
  }

  /**
   * Convenience method for creating a webapp with just a URI prefix and a
   * WebAppConfiguration.
   */
  public static WebApp create(String uriPrefix, File contextbase,
                              WebAppConfiguration wac,
                              ServletContainerContext containerContext)
      throws WebAppConfigurationException, ClassNotFoundException,
      IllegalAccessException, InstantiationException {
    return create(uriPrefix, contextbase,
        Thread.currentThread().getContextClassLoader(), wac, containerContext);
  }

  /**
   * {@inheritDoc}
   */
  public String getUriPrefix() {
    return uriPrefix;
  }


  /**
   * {@inheritDoc}
   */
  public boolean isStarted() {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  public void stop() {
    throw new UnsupportedOperationException();
  }

  /**
   * Returns a <code>ServletContext</code> object that corresponds to this
   * webapp.
   *
   * @return the <code>ServletContext</code> object that corresponds to this
   *         webapp , or null if either none exists.
   * @see javax.servlet.RequestDispatcher
   */
  public ServletContext getContext() {
    return context;
  }

  public Object getAttribute(String name) {
    return null;
  }

  public File getContextBase() {
    return contextbase;
  }

  public HttpSessionAttributeListener getHttpSessionAttributeListener() {
    return sessionAttributeListeners;
  }

  public HttpSessionListener getHttpSessionListener() {
    return sessionListeners;
  }

  /**
   * Returns a {@link RequestDispatcher} object that acts as a wrapper for the
   * resource located at the given path. A <code>RequestDispatcher</code>
   * object can be used to forward a request to the resource or to include the
   * resource in a response. The resource can be dynamic or static.
   * <p/>
   * <p/>
   * The pathname must begin with a "/" and is interpreted as relative to the
   * current context root. Use <code>getContext</code> to obtain a
   * <code>RequestDispatcher</code> for resources in foreign contexts. This
   * method returns <code>null</code> if the <code>ServletContext</code>
   * cannot return a <code>RequestDispatcher</code>.
   *
   * @param path a <code>String</code> specifying the pathname to the resource
   * @return a <code>RequestDispatcher</code> object that acts as a wrapper
   *         for the resource at the specified path, or <code>null</code> if
   *         the <code>ServletContext</code> cannot return a
   *         <code>RequestDispatcher</code>
   * @see RequestDispatcher
   * @see ServletContext#getContext
   */
  RequestDispatcher getRequestDispatcher(String path)
      throws URISyntaxException {
    if (path == null) {
      throw new NullPointerException("null path");
    }
    return RequestDispatcherImpl.create(path, regexHandler);
  }

  RequestDispatcher getNamedDispatcher(String name) throws URISyntaxException {
    String path = manager.getFirstUrlPatternForServletName(name);
    return (path == null) ? null : getRequestDispatcher(path);
  }


  LoggerFactory getLoggerFactory() {
    if (loggerFactory == null) {
      loggerFactory = new DefaultLoggerFactory();
    }
    return loggerFactory;
  }

  String getInitParameter(String name) {
    return contextParams.get(name);
  }

  Enumeration<String> getInitParameterNames() {
    return contextParams.keys();
  }


  String getDisplayname() {
    return config.getDisplayName();
  }

  /**
   * {@inheritDoc}
   */
  public void doFilter(ServletRequest request, ServletResponse response)
      throws IOException, ServletException {
    // set our context classloader
    Thread.currentThread().setContextClassLoader(classLoader);
    // send a requestInititalized event
    ServletRequestEvent sre = new ServletRequestEvent(context, request);
    srListeners.requestInitialized(sre);
    // if (!sraListeners.isEmpty()) {
    // if we have at least one request attribute listener, wrap the
    // request so we can intercept calls to setAttribute/removeAttribute
    request = wrapRequestForServletRequestAttributeListeners(request);
    // }
    RegularExpressionRequestHandler handler = regexHandler.getRequestHandler();
    handler.doFilter(request, response);
  }

  private ServletRequest wrapRequestForServletRequestAttributeListeners(
      ServletRequest request) {
    if (request instanceof HttpServletRequest) {
      return new HttpServletRequestAttributeListenerWrapper(
          (HttpServletRequest) request, context, sraListeners);
    } else {
      return new ServletRequestAttributeListenerWrapper(request, context,
          sraListeners);
    }
  }

}
