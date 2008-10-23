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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContextAttributeEvent;

/**
 * An implementation of WebAppContext
 *
 * @author Mike Jennings
 */
final class WebAppContextImpl implements WebAppContext {
  private final WebAppImpl webapp;
  private final File contextBase;
  private Logger logger;
  private final Hashtable<String, Object> attributes;

  WebAppContextImpl(WebAppImpl webapp) {
    this.webapp = webapp;
    contextBase = webapp.getContextBase();
    attributes = new Hashtable<String, Object>();
  }

  public Set<String> getResourcePaths(String path) {
    String rp = getRealPath(path);
    if (rp == null) {
      return null;
    }
    File dir = new File(rp);
    if (!dir.isDirectory()) {
      return null;
    }
    File[] files = dir.listFiles();
    if (files == null) {
      return null;
    }
    Set<String> paths = new TreeSet<String>();
    for (File file : files) {
      if (file.isFile()) {
        String name = file.getName();
        String resource = path + name;
        paths.add(resource);
      }
    }
    return paths;
  }

  public URL getResource(String path) throws MalformedURLException {
    File file = new File(getRealPath(path));
    if (file.exists()) {
      return file.toURL();
    } else {
      return null;
    }
  }

  public InputStream getResourceAsStream(String path) {
    URL resource = null;
    try {
      resource = getResource(path);
    } catch (MalformedURLException e) {
      return null;
    }
    if (resource == null) {
      return null;
    } else {
      try {
        return resource.openStream();
      } catch (IOException e) {
        return null;
      }
    }
  }

  public RequestDispatcher getRequestDispatcher(String path) {
    try {
      return webapp.getRequestDispatcher(path);
    } catch (URISyntaxException e) {
      ensureLoggerExists();
      logger.log(Level.SEVERE, "Bad URI syntax", e);
      return null;
    }
  }

  public RequestDispatcher getNamedDispatcher(String name) {
    try {
      return webapp.getNamedDispatcher(name);
    } catch (URISyntaxException e) {
      logger.log(Level.SEVERE, "Bad URI syntax", e);
      return null;
    }
  }

  private void ensureLoggerExists() {
    if (logger == null) {
      logger = webapp.getLoggerFactory().getLogger(webapp.getContext());
    }
  }

  public void log(String msg) {
    ensureLoggerExists();
    logger.info(msg);
  }

  public void log(String message, Throwable throwable) {
    ensureLoggerExists();
    logger.log(Level.INFO, "", throwable);
  }

  public String getRealPath(String path) {
    if (path == null || contextBase == null) {
      return null;
    }
    if ("".equals(path)) {
      return contextBase.toString();
    }

    if (path.startsWith("/")) {
      return getRealPath(path.substring(1));
    }
    File realPath
        = new File(contextBase, path.replace('/', File.separatorChar));
    return realPath.toString();
  }

  public String getInitParameter(String name) {
    return webapp.getInitParameter(name);
  }

  public Enumeration<String> getInitParameterNames() {
    return webapp.getInitParameterNames();
  }

  public Enumeration<String> getAttributeNames() {
    return attributes.keys();
  }

  public void setAttribute(String name, Object object) {
    Object previousValue = attributes.put(name, object);
    ServletContextAttributeEvent event;
    if (previousValue != null) {
      event = createEvent(name, previousValue);
      webapp.getGlobalServletContextAttributeListener()
          .attributeReplaced(event);
    } else {
      event = createEvent(name, object);
      webapp.getGlobalServletContextAttributeListener()
          .attributeAdded(event);
    }
  }

  public void removeAttribute(String name) {
    Object oldValue = attributes.remove(name);
    if (oldValue != null) {
      ServletContextAttributeEvent event = createEvent(name, oldValue);
      webapp.getGlobalServletContextAttributeListener().attributeRemoved(event);
    }
  }

  private ServletContextAttributeEvent createEvent(String name, Object value) {
    return new ServletContextAttributeEvent(webapp.getContext(), name, value);
  }

  public Object getAttribute(String name) {
    Object attribute = attributes.get(name);
    return (attribute != null) ? attribute : webapp.getAttribute(name);
  }

  public String getServletContextName() {
    return webapp.getDisplayname();
  }

  public String getServletContextPath() {
    return webapp.getUriPrefix();
  }
}
