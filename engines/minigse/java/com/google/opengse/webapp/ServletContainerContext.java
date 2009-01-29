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

import javax.servlet.ServletContext;

/**
 * All of the methods of javax.servlet.ServletContext that are not specific
 * to a particular web application instance.
 */
public interface ServletContainerContext {

  /**
   * Returns the major version of the Java Servlet API that this servlet
   * container supports. All implementations that comply with Version 2.4 must
   * have this method return the integer 2.
   *
   * @return 2
   */
  public int getMajorVersion();

  /**
   * Returns the minor version of the Servlet API that this servlet container
   * supports. All implementations that comply with Version 2.4 must have this
   * method return the integer 4.
   *
   * @return 4
   */
  public int getMinorVersion();

  /**
   * Returns the MIME type of the specified file, or <code>null</code> if the
   * MIME type is not known. The MIME type is determined by the configuration of
   * the servlet container, and may be specified in a web application deployment
   * descriptor. Common MIME types are <code>"text/html"</code> and
   * <code>"image/gif"</code>.
   *
   * @param file a <code>String</code> specifying the name of a file
   * @return a <code>String</code> specifying the file's MIME type
   */
  public String getMimeType(String file);

  /**
   * Returns a <code>ServletContext</code> object that corresponds to a
   * specified URL on the server.
   *
   * <p>This method allows servlets to gain access to the context for various
   * parts of the server, and as needed obtain
   * {@link javax.servlet.RequestDispatcher}
   * objects from the context. The given path must be begin with "/", is
   * interpreted relative to the server's document root and is matched against
   * the context roots of other web applications hosted on this container.
   *
   * <p>In a security conscious environment, the servlet container may return
   * <code>null</code> for a given URL.
   *
   * @param uripath
   * a <code>String</code> specifying the context path of another
   * web application in the container.
   *
   * @return
   * the <code>ServletContext</code> object that corresponds to the
   * named URL, or null if either none exists or the container wishes to
   * restrict this access.
   * @see javax.servlet.RequestDispatcher
   */
  public ServletContext getContext(String uripath);

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
   * Returns the name and version of the servlet container on which the servlet
   * is running.
   *
   * <p>The form of the returned string is: {@code servername/versionnumber}.
   * For example, the JavaServer Web Development Kit may return the string
   * {@code JavaServer Web Dev Kit/1.0}.
   *
   * <p>The servlet container may return other optional information after the
   * primary string in parentheses, for example:
   * {@code JavaServer Web Dev Kit/1.0 (JDK 1.1.6; Windows NT 4.0 x86)}.
   *
   * @return a string containing at least the servlet container's
   *    name and version number
   */
  public String getServerInfo();
}
