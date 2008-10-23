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

/**
 * @author Mike Jennings
 *         Date: Jun 6, 2008
 */
public interface SessionCacheFactory {

  /**
   * Creates a cache and adds it to the map so it can be accessed via getCache()
   *
   *
   * @param serverIdExt
   *          is a 16 bit integer that extends serverId, for servers which
   *          aren't uniquely identified by IP address alone. This value is
   *          typically the server port.
   */
  SessionCache createSessionCache(int serverIdExt);
}
