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

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * An implementation of javax.servlet.ServletContext
 * which delegates to WebAppContext and ServletContainerContext.
 *
 * @author Mike Jennings
 */
final class ServletContextImpl implements ServletContext,
    ServletContainerContext {

  private static final Enumeration<Object> EMPTY_ENUMERATION =
    new EmptyEnumeration<Object>();
  private final ServletContainerContext containerContext;
  private final WebAppContext webappContext;

  private ServletContextImpl(
      ServletContainerContext containerContext, WebAppContext webappContext) {
    this.containerContext = containerContext;
    this.webappContext = webappContext;
  }

  public static ServletContext create(ServletContainerContext containerContext,
      WebAppContext webappContext) {
    return new ServletContextImpl(containerContext, webappContext);
  }

  /**
   * Returns a {@code ServletContext} object that corresponds to a
   * specified URL on the server.
   *
   * <p>This method allows servlets to gain access to the context for various
   * parts of the server, and as needed to obtain
   * {@link javax.servlet.RequestDispatcher} objects from the context. The
   * given path must be begin with "/", is interpreted relative to the server's
   * document root and is matched against the context roots of other web
   * applications hosted on this container.
   *
   * <p>In a security conscious environment, the servlet container may return
   * {@code null} for a given URL.
   *
   * @param uripath a {@code String} specifying the context path of another
   *                web application in the container.
   * @return the {@code ServletContext} object that corresponds to the
   *         named URL, or null if either none exists or the container wishes to
   *         restrict this access.
   * @see javax.servlet.RequestDispatcher
   */
  public ServletContext getContext(String uripath) {
    return containerContext.getContext(uripath);
  }

  /**
   * Returns the major version of the Java Servlet API that this servlet
   * container supports. All implementations that comply with Version 2.4 must
   * have this method return the integer 2.
   *
   * @return 2
   */
  public int getMajorVersion() {
    return containerContext.getMajorVersion();
  }

  /**
   * Returns the minor version of the Servlet API that this servlet container
   * supports. All implementations that comply with Version 2.4 must have this
   * method return the integer 4.
   *
   * @return 4
   */
  public int getMinorVersion() {
    return containerContext.getMinorVersion();
  }

  /**
   * Returns the MIME type of the specified file, or {@code null} if the
   * MIME type is not known. The MIME type is determined by the configuration of
   * the servlet container, and may be specified in a web application deployment
   * descriptor. Common MIME types are {@code "text/html"} and
   * {@code "image/gif"}.
   *
   * @param file a {@code String} specifying the name of a file
   * @return a {@code String} specifying the file's MIME type
   */
  public String getMimeType(String file) {
    return containerContext.getMimeType(file);
  }

  /**
   * Returns a directory-like listing of all the paths to resources within the
   * web application whose longest sub-path matches the supplied path argument.
   * Paths indicating subdirectory paths end with a '/'. The returned paths are
   * all relative to the root of the web application and have a leading '/'. For
   * example, for a web application containing<br><br>
   *
   *<pre>
   *   /welcome.html
   *   /catalog/index.html
   *   /catalog/products.html
   *   /catalog/offers/books.html
   *   /catalog/offers/music.html
   *   /customer/login.jsp
   *   /WEB-INF/web.xml
   *   /WEB-INF/classes/com.acme.OrderServlet.class,
   * </pre>
   *
   * {@code getResourcePaths("/")} returns
   * <pre>{"/welcome.html", "/catalog/", "/customer/", "/WEB-INF/"}</pre>
   *
   * {@code getResourcePaths("/catalog/")} returns
   * <pre>{"/catalog/index.html", "/catalog/products.html", "/catalog/offers/"}
   * </pre>
   *
   * @param path the partial path used to match the resources, which must start
   *             with a /
   * @return a Set containing the directory listing, or null if there are no
   *         resources in the web application whose path begins with the
   *         supplied path.
   * @since Servlet 2.3
   */

  public Set<String> getResourcePaths(String path) {
    return webappContext.getResourcePaths(path);
  }

  /**
   * Returns a URL to the resource that is mapped to a specified path. The path
   * must begin with a "/" and is interpreted as relative to the current context
   * root.
   *
   * <p>This method allows the servlet container to make a resource available to
   * servlets from any source. Resources can be located on a local or remote
   * file system, in a database, or in a {@code .war} file.
   *
   * <p>The servlet container must implement the URL handlers and
   * {@code URLConnection} objects that are necessary to access the
   * resource.
   *
   * <p>This method returns {@code null} if no resource is mapped to the
   * pathname.
   *
   * <p>Some containers may allow writing to the URL returned by this method
   * using the methods of the URL class.
   *
   * <p>The resource content is returned directly, so be aware that requesting a
   * {@code .jsp} page returns the JSP source code. Use a
   * {@code RequestDispatcher} instead to include results of an execution.
   *
   * <p>This method has a different purpose than
   * {@link java.lang.Class#getResource(String)}, which looks up resources
   * based on a class loader. This method does not use class loaders.
   *
   * @param path a {@code String} specifying the path to the resource
   * @return the resource located at the named path, or {@code null} if
   *         there is no resource at that path
   * @throws java.net.MalformedURLException if the pathname is not given in the
   *                                        correct form
   */
  public URL getResource(String path) throws MalformedURLException {
    return webappContext.getResource(path);
  }

  /**
   * Returns the resource located at the named path as an
   * {@code InputStream} object.
   *
   * <p>The data in the {@code InputStream} can be of any type or length.
   * The path must be specified according to the rules given in
   * {@code getResource}. This method returns {@code null} if no
   * resource exists at the specified path.
   *
   * <p>Meta-information such as content length and content type that is
   * available via {@code getResource} method is lost when using this
   * method.
   *
   * <p>The servlet container must implement the URL handlers and
   * {@code URLConnection} objects necessary to access the resource.
   *
   * <p>This method is different from
   * {@code java.lang.Class#getResourceAsStream(String)}, which uses a
   * class loader. This method allows servlet containers to make a
   * resource available to a servlet from any location, without using a class
   * loader.
   *
   * @param path a {@code String} specifying the path to the resource
   * @return the {@code InputStream} returned to the servlet, or
   *         {@code null} if no resource exists at the specified path
   */

  public InputStream getResourceAsStream(String path) {
    return webappContext.getResourceAsStream(path);
  }

  /**
   * Returns a {@link javax.servlet.RequestDispatcher} object that acts as a
   * wrapper for the resource located at the given path. A
   * {@code RequestDispatcher} object can be used to forward a request to
   * the resource or to include the resource in a response. The resource can be
   * dynamic or static.
   *
   * <p>The pathname must begin with a "/" and is interpreted as relative to the
   * current context root.  Use {@code getContext} to obtain a
   * {@code RequestDispatcher} for resources in foreign contexts. This
   * method returns {@code null} if the {@code ServletContext} cannot
   * return a {@code RequestDispatcher}.
   *
   * @param path a {@code String} specifying the pathname to the resource
   * @return a {@code RequestDispatcher} object that acts as a wrapper for
   *         the resource at the specified path, or {@code null} if the
   *         {@code ServletContext} cannot return a {@code RequestDispatcher}
   * @see javax.servlet.RequestDispatcher
   * @see javax.servlet.ServletContext#getContext
   */
  public RequestDispatcher getRequestDispatcher(String path) {
    return webappContext.getRequestDispatcher(path);
  }

  /**
   * Returns a {@link javax.servlet.RequestDispatcher} object that acts as a
   * wrapper for the named servlet.
   *
   * <p>Servlets (and JSP pages also) may be given names via server
   * administration or via a web application deployment descriptor. A servlet
   * instance can determine its name using
   * {@link javax.servlet.ServletConfig#getServletName()}.
   *
   * <p>This method returns {@code null} if the {@code ServletContext}
   * cannot return a {@code RequestDispatcher} for any reason.
   *
   * @param name a {@code String} specifying the name of a servlet to wrap
   * @return a {@code RequestDispatcher} object that acts as a wrapper for
   *         the named servlet, or {@code null} if the
   *         {@code ServletContext} cannot return a {@code RequestDispatcher}
   * @see javax.servlet.RequestDispatcher
   * @see javax.servlet.ServletContext#getContext
   * @see javax.servlet.ServletConfig#getServletName
   */

  public RequestDispatcher getNamedDispatcher(String name) {
    return webappContext.getNamedDispatcher(name);
  }

  /**
   * @deprecated As of Java Servlet API 2.1, with no direct replacement.
   *
   *             <p>This method was originally defined to retrieve a servlet
   *             from a {@code ServletContext}. In this version, this
   *             method always returns {@code null} and remains only to
   *             preserve binary compatibility. This method will be permanently
   *             removed in a future version of the Java Servlet API.
   *
   *             <p>In lieu of this method, servlets can share information using
   *             the {@code ServletContext} class and can perform shared
   *             business logic by invoking methods on common non-servlet
   *             classes.
   */

  @Deprecated
  public Servlet getServlet(String name) throws ServletException {
    return null;
  }

  /**
   * @deprecated As of Java Servlet API 2.0, with no replacement.
   *
   *             <p>This method was originally defined to return an
   *             {@code Enumeration} of all the servlets known to this
   *             servlet context. In this version, this method always returns an
   *             empty enumeration and remains only to preserve binary
   *             compatibility. This method will be permanently removed in a
   *             future version of the Java Servlet API.
   */

  @Deprecated
  public Enumeration<Object> getServlets() {
    return EMPTY_ENUMERATION;
  }

  /**
   * @deprecated As of Java Servlet API 2.1, with no replacement.
   *
   *             <p>This method was originally defined to return an
   *             {@code Enumeration} of all the servlet names known to this
   *             context. In this version, this method always returns an empty
   *             {@code Enumeration} and remains only to preserve binary
   *             compatibility. This method will be permanently removed in a
   *             future version of the Java Servlet API.
   */

  @Deprecated
  public Enumeration<Object> getServletNames() {
    return EMPTY_ENUMERATION;
  }

  /**
   * Writes the specified message to a servlet log file, usually an event log.
   * The name and type of the servlet log file is specific to the servlet
   * container.
   *
   * @param msg a {@code String} specifying the message to be written to
   *            the log file
   */
  public void log(String msg) {
    webappContext.log(msg);
  }

  /**
   * @deprecated As of Java Servlet API 2.1, use {@link #log(String message,
   *             Throwable throwable)} instead.
   *
   *             <p>This method was originally defined to write an exception's
   *             stack trace and an explanatory error message to the servlet log
   *             file.
   */

  @Deprecated
  public void log(Exception exception, String msg) {
    webappContext.log(msg, exception);
  }

  /**
   * Writes an explanatory message and a stack trace for a given
   * {@code Throwable} exception to the servlet log file. The name and type
   * of the servlet log file is specific to the servlet container, usually an
   * event log.
   *
   * @param message   a {@code String} that describes the error or
   *                  exception
   * @param throwable the {@code Throwable} error or exception
   */

  public void log(String message, Throwable throwable) {
    webappContext.log(message, throwable);
  }

  /**
   * Returns a {@code String} containing the real path for a given virtual
   * path. For example, the path "/index.html" returns the absolute file path on
   * the server's filesystem would be served by a request for
   * "http://host/contextPath/index.html", where contextPath is the context path
   * of this ServletContext..
   *
   * <p>The real path returned will be in a form appropriate to the computer and
   * operating system on which the servlet container is running, including the
   * proper path separators. This method returns {@code null} if the
   * servlet container cannot translate the virtual path to a real path for any
   * reason (such as when the content is being made available from a
   * {@code .war} archive).
   *
   * @param path a {@code String} specifying a virtual path
   * @return a {@code String} specifying the real path, or null if the
   *         translation cannot be performed
   */
  public String getRealPath(String path) {
    return webappContext.getRealPath(path);
  }

  /**
   * Returns the name and version of the servlet container on which the servlet
   * is running.
   *
   * <p>The form of the returned string is {@code servername/versionnumber}.
   * For example, the JavaServer Web Development Kit may return the string
   * {@code "JavaServer Web Dev Kit/1.0"}.
   *
   * <p>The servlet container may return other optional information after the
   * primary string in parentheses, for example, {@code "JavaServer Web Dev
   * Kit/1.0 (JDK 1.1.6; Windows NT 4.0 x86)"}.
   *
   * @return a {@code String} containing at least the servlet container
   *         name and version number
   */
  public String getServerInfo() {
    return containerContext.getServerInfo();
  }

  /**
   * Returns a {@code String} containing the value of the named
   * context-wide initialization parameter, or {@code null} if the
   * parameter does not exist.
   *
   * <p>This method can make available configuration information useful to an
   * entire "web application".  For example, it can provide a webmaster's email
   * address or the name of a system that holds critical data.
   *
   * @param name a {@code String} containing the name of the parameter
   *             whose value is requested
   * @return a {@code String} containing at least the servlet container
   *         name and version number
   * @see javax.servlet.ServletConfig#getInitParameter
   */

  public String getInitParameter(String name) {
    return webappContext.getInitParameter(name);
  }

  /**
   * Returns the names of the context's initialization parameters as an
   * {@code Enumeration} of {@code String} objects, or an empty
   * {@code Enumeration} if the context has no initialization parameters.
   *
   * @return an {@code Enumeration} of {@code String} objects
   *         containing the names of the context's initialization parameters
   * @see javax.servlet.ServletConfig#getInitParameter
   */

  public Enumeration<String> getInitParameterNames() {
    return webappContext.getInitParameterNames();
  }

  /**
   * Returns the servlet container attribute with the given name, or
   * {@code null} if there is no attribute by that name. An attribute
   * allows a servlet container to give the servlet additional information not
   * already provided by this interface. See your server documentation for
   * information about its attributes. A list of supported attributes can be
   * retrieved using {@code getAttributeNames}.
   *
   * <p>The attribute is returned as a {@code java.lang.Object} or some
   * subclass. Attribute names should follow the same convention as package
   * names. The Java Servlet API specification reserves names matching
   * {@code java.*}, {@code javax.*}, and {@code sun.*}.
   *
   * @param name a {@code String} specifying the name of the attribute
   * @return an {@code Object} containing the value of the attribute, or
   *         {@code null} if no attribute exists matching the given name
   * @see javax.servlet.ServletContext#getAttributeNames
   */
  public Object getAttribute(String name) {
    Object attribute = containerContext.getAttribute(name);
    if (attribute != null) {
      return attribute;
    }
    return webappContext.getAttribute(name);
  }

  /**
   * Returns an {@code Enumeration} containing the attribute names
   * available within this servlet context. Use the {@link #getAttribute} method
   * with an attribute name to get the value of an attribute.
   *
   * @return an {@code Enumeration} of attribute names
   * @see #getAttribute
   */

  public Enumeration<String> getAttributeNames() {
    return webappContext.getAttributeNames();
  }

  /**
   * Binds an object to a given attribute name in this servlet context. If the
   * name specified is already used for an attribute, this method will replace
   * the attribute with the new to the new attribute. <p>If listeners are
   * configured on the {@code ServletContext} the container notifies them
   * accordingly. <p> If a null value is passed, the effect is the same as
   * calling {@code removeAttribute()}.
   *
   * <p>Attribute names should follow the same convention as package names. The
   * Java Servlet API specification reserves names matching {@code java.*},
   * {@code javax.*}, and {@code sun.*}.
   *
   * @param name   a {@code String} specifying the name of the attribute
   * @param object an {@code Object} representing the attribute to be
   *               bound
   */

  public void setAttribute(String name, Object object) {
    webappContext.setAttribute(name, object);
  }

  /**
   * Removes the attribute with the given name from the servlet context. After
   * removal, subsequent calls to {@link #getAttribute} to retrieve the
   * attribute's value will return {@code null}.
   *
   * <p>If listeners are configured on the {@code ServletContext} the
   * container notifies them accordingly.
   *
   * @param name a {@code String} specifying the name of the attribute to
   *             be removed
   */
  public void removeAttribute(String name) {
    webappContext.removeAttribute(name);
  }

  /**
   * Returns the name of this web application corresponding to this
   * ServletContext as specified in the deployment descriptor for this web
   * application by the display-name element.
   *
   * @return The name of the web application or null if no name has been
   *         declared in the deployment descriptor.
   * @since Servlet 2.3
   */

  public String getServletContextName() {
    return webappContext.getServletContextName();
  }

  /**
   * Returns the context path of the web application.
   *
   * The context path is the portion of the request URI that is used to select
   * the context of the request. The context path always comes first in a
   * request URI. The path starts with a "/" character but does not end with
   * a "/" character. For servlets in the default (root) context, this method
   * returns "".
   *
   * It is possible that a servlet container may match a context by more than
   * one context path. In such cases the HttpServletRequest.getContextPath()
   * will return the actual context path used by the request and it may differ
   * from the path returned by this method. The context path returned by this
   * method should be considered as the prime or preferred context path of the
   * application.
   *
   * @since servlet 2.5
   */
  public String getContextPath() {
    return webappContext.getServletContextPath();
  }

  private static class EmptyEnumeration<T> implements Enumeration<T> {

    public boolean hasMoreElements() {
      return false;
    }

    public T nextElement() {
      return null;
    }
  }
}
