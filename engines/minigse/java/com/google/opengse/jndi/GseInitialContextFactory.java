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

import java.util.Hashtable;
import java.util.Properties;
import java.util.Map.Entry;

import javax.naming.spi.InitialContextFactory;
import javax.naming.Context;
import javax.naming.NamingException;

/**
 * The main entry point for JNDI support in OpenGSE
 *
 * @author Mike Jennings
 */
public final class GseInitialContextFactory implements InitialContextFactory {

  /**
   * The JNDI subsystem invokes this method to get the initial JNDI context.
   * The Hashtable passed in to this method is populated from the classpath
   * resource "/jndi.properties" and from the (optional) hashtable passed into
   * the contructor of InitialContext. Key/value pairs passed into the
   * constructor of InitialContext overrides the key/value pairs in
   * jndi.properties
   *
   * @param env the combined key/value pairs from the /jndi.properties classpath
   * resource and the key/value pairs passed into the constructor of
   * InitialContext
   *
   * @return The initial JNDI context
   * @throws NamingException
   */
  public Context getInitialContext(Hashtable<?, ?> env)
      throws NamingException {
    Properties props = new Properties();
    for (Entry<?, ?> entry : env.entrySet()) {
      props.setProperty(entry.getKey().toString(), entry.getValue().toString());
    }
    return GseInitialContext.getInstance(props);
  }
}
