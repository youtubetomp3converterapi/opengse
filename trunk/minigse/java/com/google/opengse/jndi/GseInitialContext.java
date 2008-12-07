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

package com.google.opengse.jndi;

import java.util.Properties;

import javax.naming.Name;
import javax.naming.NamingException;

/**
 * The root JNDI context
 *
 * @author Mike Jennings
 */
final class GseInitialContext extends UnsupportedContext {
  private Properties env;
  public static final String JNDI_PREFIX = "/jndi/";

  private GseInitialContext(Properties env) {
    this.env = env;
    instance = this;
  }

  private static GseInitialContext instance;

  static GseInitialContext getInstance(Properties env) throws NamingException {
    if (instance == null) {
      new GseInitialContext(env);
    }
    return instance;
  }

  /**
   * Merge all of the properties of this initial context
   * with the supplied properties.
   *
   * @param props the properties to add
   * @return a copy of the new properties this initial context will use
   */
  Properties merge(Properties props) {
    env.putAll(props);
    return new Properties(env);
  }

  @Override
  public Object lookup(Name name) throws NamingException {
    return null;
  }

  @Override
  public Object lookup(String name) throws NamingException {
    if (name.startsWith(JNDI_PREFIX)) {
      return lookupJndiObject(name.substring(JNDI_PREFIX.length()));
    }
    return null;
  }

  private Object lookupJndiObject(String name) throws NamingException {
    String prefix = "default";
    int slash = name.indexOf('/');
    if (slash > 0) {
      prefix = name.substring(slash + 1);
      name = name.substring(0, slash);
    }
    return lookupJndiObject(prefix, name);
  }

  private Object lookupJndiObject(String prefix, String name) throws NamingException {
    if (name.indexOf('.') > 0) {
      return lookupJndiClass(prefix, name);
    }
    return null;
  }

  private Object lookupJndiClass(String prefix, String classname) throws NamingException {
    try {
      ClassLoader cl = getCurrentClassLoader();
      Class<?> clazz = cl.loadClass(classname);
      return resolve(clazz, prefix);
    } catch (ClassNotFoundException e) {
      throw new NamingException("Cannot find class '" + classname + "'");
    }
  }

  private static ClassLoader getCurrentClassLoader() {
    ClassLoader cl = Thread.currentThread().getContextClassLoader();
    if (cl == null) {
      cl = GseInitialContext.class.getClassLoader();
    }
    return cl;    
  }

  private <T> T resolve(Class<T> iface, String prefix) throws NamingException {
    if (!iface.isInterface()) {
      // we will only ever look up interfaces, never concrete or abstract classes
      // this exception should never happen
      throw new RuntimeException(iface.getName() + " is not an interface");
    }
    String ifaceName = iface.getName();
    String factoryClassKey = ifaceName + ".factory";
    String factoryClassname = env.getProperty(factoryClassKey, DefaultFactory.class.getName());
//    String factoryClassname = env.getProperty(factoryClassKey);
    if (factoryClassname == null) {
      throw new NamingException("No value found for key " + factoryClassKey);
    }
    Factory<T> factory = getFactory(ifaceName, factoryClassname);
    try {
      return iface.cast(factory.get(env, iface, prefix));
    } catch (ClassCastException e) {
      throw new NamingException(factoryClassname + " is not a Factory for " + ifaceName);
    }

  }


  @SuppressWarnings("unchecked")
  private static <T> Factory<T> getFactory(
      String ifaceName, String factoryClassname)
      throws NamingException {
    try {
      ClassLoader cl = getCurrentClassLoader();
      Class<?> factoryClass = cl.loadClass(factoryClassname);
      Object factory = factoryClass.newInstance();
      if (!(factory instanceof Factory)) {
        throw new NamingException(factoryClassname + " is not a Factory for " + ifaceName);
      }
      return (Factory<T>) factoryClass.newInstance();
    } catch (ClassNotFoundException e) {
      throw new NamingException("Cannot find class '" + factoryClassname + "'");
    } catch (IllegalAccessException e) {
      throw new NamingException("IllegalAccessException: class '" + factoryClassname + "'");
    } catch (InstantiationException e) {
      throw new NamingException("InstantiationException: class '" + factoryClassname + "'");
    }
  }

}
