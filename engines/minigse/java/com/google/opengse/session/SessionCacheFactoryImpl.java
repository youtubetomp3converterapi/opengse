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

package com.google.opengse.session;

import com.google.opengse.session.impl.ServletSessionCache;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Logger;

/**
 * @author Mike Jennings
 *         Date: Jun 6, 2008
 */
public class SessionCacheFactoryImpl implements SessionCacheFactory {
  private static final Logger LOGGER =
    Logger.getLogger(SessionCacheFactoryImpl.class.getName());

  public SessionCacheFactoryImpl() {
  }

  public SessionCache createSessionCache(int serverIdExt) {
    SessionConfiguration config
        = new SessionConfigurationImpl(getAddress() , serverIdExt);
    return new ServletSessionCache(config);
  }

  /**
   * Returns the local IP address.
   */
  private static int getAddress() {
    try {
      byte[] ipBytes = InetAddress.getLocalHost().getAddress();
      int addr = ((ipBytes[0] & 0xff) << 24) |
                 ((ipBytes[1] & 0xff) << 16) |
                 ((ipBytes[2] & 0xff) << 8) |
                 (ipBytes[3] & 0xff);
      return addr;
    } catch (UnknownHostException e) {
      LOGGER.warning("unable to determine server's IP address: " +
                     e.getMessage());
      return 0;
    }
  }


}
