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
public class DefaultFactory<T> implements Factory<T> {
  public T get(Properties props, Class<T> iface, String namespace)
      throws NamingException {
    String implname = iface.getName() + "Impl";
    try {
      Class<?> clazz = Class.forName(implname);
      Object impl = clazz.newInstance();
      return iface.cast(impl);
    } catch (ClassNotFoundException e) {
      throw new NamingException(
              "Cannot find class '" + implname
              + "' try setting " + iface.getName() + ".factory property");
    } catch (IllegalAccessException e) {
      throw new NamingException(
              "IllegalAccessException: class '" + implname + "'");
    } catch (InstantiationException e) {
      throw new NamingException(
              "InstantiationException: class '" + implname + "'");
    }
  }
}
