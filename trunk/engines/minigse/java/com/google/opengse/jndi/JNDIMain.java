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

import javax.naming.NamingException;
import javax.naming.InitialContext;
import java.util.Properties;

/**
 * @author Mike Jennings
 */
public final class JNDIMain {

  private JNDIMain() { /* Utility class: do not instantiate */ }

  private static GseInitialContext initialContext;

  /**
   * Initialize the initial context and add the given properties
   * to the initial context.
   *
   * @param env the properties to add to the initial context
   * @return the current environment of the initial context
   * @throws NamingException if anything goes wrong
   */
  public synchronized static Properties initialize(Properties env)
      throws NamingException {
    if (initialContext != null) {
      throw new NamingException("Already initialized");
    }
    // Create an initial context to force the JNDI stuff to create a GseInitialContext
    new InitialContext();
    initialContext = GseInitialContext.getInstance(env);
    return initialContext.merge(env);
  }

  public static <T> T lookup(Class<T> iface) throws NamingException {
    return lookup(iface, "default");
  }

  public static <T> T lookup(Class<T> iface, String namespace) throws NamingException {
    if (!iface.isInterface()) {
      // We only ever look up interfaces, so we should never get to here
      throw new RuntimeException(iface.getName() + " is not an interface");
    }
    InitialContext ic = new InitialContext();
    Object obj = ic.lookup(GseInitialContext.JNDI_PREFIX + iface.getName() + "/" + namespace);
    try {
      return iface.cast(obj);
    } catch (ClassCastException e) {
      throw new NamingException("Cannot lookup '" + iface.getName()
          + "' with namespace '" + namespace + ".");
    }

  }

  public synchronized static void reset() {
    initialContext = null;
  }

}
