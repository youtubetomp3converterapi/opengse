// Copyright 2007 Google Inc. All Rights Reserved.
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

package com.google.opengse.httputil;

import javax.servlet.http.HttpServletResponse;

/**
 * Some utility methods for modifying an HttpServletResponse.
 *
 * @author Devesh Parekh
 */
public final class ResponseUtil {

  /**
   * Sets HTTP/1.0 and HTTP/1.1 cache directives that disallow all caching.
   *
   * @param response the response to disallow caching for.
   */
  public static void setNoCacheHeaders(HttpServletResponse response) {
    response.setHeader("Cache-Control",
                       "no-cache, no-store, max-age=0, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "Fri, 01 Jan 1990 00:00:00 GMT");
  }

  private ResponseUtil() {
    // Non-instantiable.
  }
}
