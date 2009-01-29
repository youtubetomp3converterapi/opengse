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
import java.util.Properties;

/**
 * @author jennings
 *         Date: Apr 16, 2008
 */
public interface Factory<T> {

  /**
   * Construct an object of type T, given properties and a prefix
   * @param props Properties that may or may not be used to create the object
   * @param iface the interface class of the object to instantiate
   * @param namespace example:
   * /jndi/com.google.opengse.Bar/foo has a namespace of foo
   * @return an instance of type iface
   * @throws NamingException if there was a problem
   */
  public T get(Properties props, Class<T> iface, String namespace)
      throws NamingException;
}

