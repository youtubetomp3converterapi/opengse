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

import java.util.Set;
import java.util.Enumeration;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.InputStream;

import javax.servlet.RequestDispatcher;

/**
 * All of the methods of {@link javax.servlet.ServletContext} that are specific
 * to a particular web application instance.
 */
public interface WebAppContext {

  /**
   * Returns a directory-like listing of all the paths to resources within the
   * web application whose longest sub-path matches the supplied path argument.
   * Paths indicating subdirectory paths end with a '/'. The returned paths are
   * all relative to the root of the web application and have a leading '/'. For
   * example, for a web application containing<br>
   * <br>
   *
   * /welcome.html<br>
   * /catalog/index.html<br>
   * /catalog/products.html<br>
   * /catalog/offers/books.html<br>
   * /catalog/offers/music.html<br>
   * /customer/login.jsp<br>
   * /WEB-INF/web.xml<br>
   * /WEB-INF/classes/com.acme.OrderServlet.class,<br>
   * <br>
   *
   * getResourcePaths("/") returns {"/welcome.html", "/catalog/", "/customer/",
   * "/WEB-INF/"}<br>
   * getResourcePaths("/catalog/") returns {"/catalog/index.html",
   * "/catalog/products.html", "/catalog/offers/"}.<br>
   *
   *
   *
   * @param path the partial path used to match the resources, which must start
   *        with a /
   * @return a Set containing the directory listing, or null if there are no
   *         resources in the web application whose path begins with the
   *         supplied path.
   *
   * @since Servlet 2.3
   */
  public Set<String> getResourcePaths(String path);



  /**
   * Returns a URL to the resource that is mapped to a specified
   * path. The path must begin with a "/" and is interpreted
   * as relative to the current context root.
   *
   * <p>This method allows the servlet container to make a resource
   * available to servlets from any source. Resources
   * can be located on a local or remote
   * file system, in a database, or in a <code>.war</code> file.
   *
   * <p>The servlet container must implement the URL handlers
   * and <code>URLConnection</code> objects that are necessary
   * to access the resource.
   *
   * <p>This method returns <code>null</code>
   * if no resource is mapped to the pathname.
   *
   * <p>Some containers may allow writing to the URL returned by
   * this method using the methods of the URL class.
   *
   * <p>The resource content is returned directly, so be aware that
   * requesting a <code>.jsp</code> page returns the JSP source code.
   * Use a <code>RequestDispatcher</code> instead to include results of
   * an execution.
   *
   * <p>This method has a different purpose than
   * <code>java.lang.Class.getResource</code>,
   * which looks up resources based on a class loader. This
   * method does not use class loaders.
   *
   * @param path         a <code>String</code> specifying
   *            the path to the resource
   *
   * @return           the resource located at the named path,
   *             or <code>null</code> if there is no resource
   *            at that path
   *
   * @exception java.net.MalformedURLException   if the pathname is not given in
   *             the correct form
   *
   */
  public URL getResource(String path) throws MalformedURLException;



  /**
   * Returns the resource located at the named path as
   * an <code>InputStream</code> object.
   *
   * <p>The data in the <code>InputStream</code> can be
   * of any type or length. The path must be specified according
   * to the rules given in <code>getResource</code>.
   * This method returns <code>null</code> if no resource exists at
   * the specified path.
   *
   * <p>Meta-information such as content length and content type
   * that is available via <code>getResource</code>
   * method is lost when using this method.
   *
   * <p>The servlet container must implement the URL handlers
   * and <code>URLConnection</code> objects necessary to access
   * the resource.
   *
   * <p>This method is different from
   * <code>java.lang.Class.getResourceAsStream</code>,
   * which uses a class loader. This method allows servlet containers
   * to make a resource available
   * to a servlet from any location, without using a class loader.
   *
   *
   * @param path  a <code>String</code> specifying the path
   *              to the resource
   *
   * @return     the <code>InputStream</code> returned to the
   *      servlet, or <code>null</code> if no resource
   *      exists at the specified path
   *
   *
   */
  public InputStream getResourceAsStream(String path);


  /**
   *
   * Returns a {@link javax.servlet.RequestDispatcher} object that acts
   * as a wrapper for the resource located at the given path.
   * A <code>RequestDispatcher</code> object can be used to forward
   * a request to the resource or to include the resource in a response.
   * The resource can be dynamic or static.
   *
   * <p>The pathname must begin with a "/" and is interpreted as relative
   * to the current context root.  Use <code>getContext</code> to obtain
   * a <code>RequestDispatcher</code> for resources in foreign contexts.
   * This method returns <code>null</code> if the <code>ServletContext</code>
   * cannot return a <code>RequestDispatcher</code>.
   *
   * @param path   a <code>String</code> specifying the pathname
   *      to the resource
   *
   * @return     a <code>RequestDispatcher</code> object
   *      that acts as a wrapper for the resource
   *      at the specified path, or <code>null</code> if
   *      the <code>ServletContext</code> cannot return
   *      a <code>RequestDispatcher</code>
   *
   * @see     javax.servlet.RequestDispatcher
   * @see     javax.servlet.ServletContext#getContext
   *
   */
  public RequestDispatcher getRequestDispatcher(String path);



  /**
   * Returns a {@link RequestDispatcher} object that acts
   * as a wrapper for the named servlet.
   *
   * <p>Servlets (and JSP pages also) may be given names via server
   * administration or via a web application deployment descriptor.
   * A servlet instance can determine its name using
   * {@link javax.servlet.ServletConfig#getServletName}.
   *
   * <p>This method returns <code>null</code> if the
   * <code>ServletContext</code>
   * cannot return a <code>RequestDispatcher</code> for any reason.
   *
   * @param name   a <code>String</code> specifying the name
   *      of a servlet to wrap
   *
   * @return     a <code>RequestDispatcher</code> object
   *      that acts as a wrapper for the named servlet,
   *      or <code>null</code> if the <code>ServletContext</code>
   *      cannot return a <code>RequestDispatcher</code>
   *
   * @see     RequestDispatcher
   * @see     javax.servlet.ServletContext#getContext
   * @see     javax.servlet.ServletConfig#getServletName
   *
   */
  public RequestDispatcher getNamedDispatcher(String name);


  /**
   *
   * Writes the specified message to a servlet log file, usually
   * an event log. The name and type of the servlet log file is
   * specific to the servlet container.
   *
   *
   * @param msg   a <code>String</code> specifying the
   *      message to be written to the log file
   *
   */
  public void log(String msg);

  /**
   * Writes an explanatory message and a stack trace
   * for a given <code>Throwable</code> exception
   * to the servlet log file. The name and type of the servlet log
   * file is specific to the servlet container, usually an event log.
   *
   *
   * @param message     a <code>String</code> that
   *        describes the error or exception
   *
   * @param throwable   the <code>Throwable</code> error
   *        or exception
   *
   */
  public void log(String message, Throwable throwable);


  /**
   * Returns a <code>String</code> containing the real path
   * for a given virtual path. For example, the path "/index.html"
   * returns the absolute file path on the server's filesystem would be
   * served by a request for "http://host/contextPath/index.html",
   * where contextPath is the context path of this ServletContext..
   *
   * <p>The real path returned will be in a form
   * appropriate to the computer and operating system on
   * which the servlet container is running, including the
   * proper path separators. This method returns <code>null</code>
   * if the servlet container cannot translate the virtual path
   * to a real path for any reason (such as when the content is
   * being made available from a <code>.war</code> archive).
   *
   *
   * @param path   a <code>String</code> specifying a virtual path
   *
   *
   * @return     a <code>String</code> specifying the real path,
   *                  or null if the translation cannot be performed
   */
  public String getRealPath(String path);

  /**
   * Returns a <code>String</code> containing the value of the named
   * context-wide initialization parameter, or <code>null</code> if the
   * parameter does not exist.
   *
   * <p>This method can make available configuration information useful
   * to an entire "web application".  For example, it can provide a
   * webmaster's email address or the name of a system that holds
   * critical data.
   *
   * @param  name  a <code>String</code> containing the name of the
   *                  parameter whose value is requested
   *
   * @return     a <code>String</code> containing at least the
   *      servlet container name and version number
   *
   * @see javax.servlet.ServletConfig#getInitParameter
   */
  public String getInitParameter(String name);


  /**
   * Returns the names of the context's initialization parameters as an
   * <code>Enumeration</code> of <code>String</code> objects, or an
   * empty <code>Enumeration</code> if the context has no initialization
   * parameters.
   *
   * @return     an <code>Enumeration</code> of <code>String</code>
   *                  objects containing the names of the context's
   *                  initialization parameters
   *
   * @see javax.servlet.ServletConfig#getInitParameter
   */
  public Enumeration<String> getInitParameterNames();

  /**
   * Returns an <code>Enumeration</code> containing the
   * attribute names available
   * within this servlet context. Use the
   * {@link #getAttribute} method with an attribute name
   * to get the value of an attribute.
   *
   * @return     an <code>Enumeration</code> of attribute
   *      names
   *
   * @see    #getAttribute
   *
   */
  public Enumeration<String> getAttributeNames();

  /**
   *
   * Binds an object to a given attribute name in this servlet context. If
   * the name specified is already used for an attribute, this
   * method will replace the attribute with the new to the new attribute.
   * <p>If listeners are configured on the <code>ServletContext</code> the
   * container notifies them accordingly.
   * <p>
   * If a null value is passed, the effect is the same as calling
   * <code>removeAttribute()</code>.
   *
   * <p>Attribute names should follow the same convention as package
   * names. The Java Servlet API specification reserves names
   * matching <code>java.*</code>, <code>javax.*</code>, and
   * <code>sun.*</code>.
   *
   *
   * @param name   a <code>String</code> specifying the name
   *      of the attribute
   *
   * @param object   an <code>Object</code> representing the
   *      attribute to be bound
   */
  public void setAttribute(String name, Object object);

  /**
   * Removes the attribute with the given name from
   * the servlet context. After removal, subsequent calls to
   * {@link #getAttribute} to retrieve the attribute's value
   * will return <code>null</code>.

   * <p>If listeners are configured on the <code>ServletContext</code> the
   * container notifies them accordingly.

   *
   *
   * @param name  a <code>String</code> specifying the name
   *       of the attribute to be removed
   *
   */
  public void removeAttribute(String name);

  /**
   * Returns the servlet container attribute with the given name, or
   * <code>null</code> if there is no attribute by that name. An attribute
   * allows a servlet container to give the servlet additional information not
   * already provided by this interface. See your server documentation for
   * information about its attributes. A list of supported attributes can be
   * retrieved using <code>getAttributeNames</code>.
   *
   * <p>The attribute is returned as a <code>java.lang.Object</code> or some
   * subclass. Attribute names should follow the same convention as package
   * names. The Java Servlet API specification reserves names matching
   * <code>java.*</code>, <code>javax.*</code>, and <code>sun.*</code>.
   *
   * @param name a <code>String</code> specifying the name of the attribute
   * @return an <code>Object</code> containing the value of the attribute, or
   *         <code>null</code> if no attribute exists matching the given name
   * @see javax.servlet.ServletContext#getAttributeNames
   */
  public Object getAttribute(String name);

  /**
   * Returns the name of this web application corresponding to this
   * ServletContext as specified in the deployment descriptor for this web
   * application by the display-name element.
   *
   * @return The name of the web application or null if no name has been
   *         declared in the deployment descriptor.
   * @since Servlet 2.3
   */
  public String getServletContextName();

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
  public String getServletContextPath();
}
