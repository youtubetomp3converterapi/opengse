package com.google.opengse.session.impl2;

import com.google.opengse.session.SessionProvider;
import com.google.opengse.jndi.Factory;

import javax.naming.NamingException;
import java.util.Properties;// Copyright 2008 Google Inc. All Rights Reserved.
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

/**
 * @author jennings
 *         Date: Sep 19, 2008
 */
public final class SessionProviderFactory implements Factory<SessionProvider> {
  
  public SessionProviderFactory() {
  }

  public SessionProvider get(Properties props, Class<SessionProvider> iface, String namespace) throws NamingException {
    return new SessionProviderImpl();
  }
}
