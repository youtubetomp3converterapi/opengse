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

package com.google.opengse;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

/**
 * Utility methods for requests.
 *
 * @author Mike Jennings
 */
public final class RequestUtils {

  private RequestUtils() { /* Utility class: do not instantiate */ }

  public static URI getURI(HttpServletRequest req) {
    try {
      return new URI(req.getRequestURI());
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  public static URI getURI(HttpRequest req) {
    try {
      return new URI(req.getRequestURI());
    } catch (URISyntaxException e) {
      // getRequestURI() should *never* be a badly-formatted URI, which is why
      // we throw a plain old RuntimeException
      throw new RuntimeException(e);
    }
  }

  public static StringBuilder getRequestURLPrefix(HttpServletRequest req) {
    StringBuilder buf = new StringBuilder();

    // add in the scheme
    String scheme = req.getScheme();
    buf.append(scheme);
    buf.append("://");

    // add in the authority (username, password, host, port)
    URI requestUri = RequestUtils.getURI(req);
    String authority = requestUri.getRawAuthority();
    if (authority != null) {
      buf.append(authority);
    } else {
      // if the URI has no authority info, construct from the host header
      buf.append(req.getServerName() + ":" + req.getServerPort());
    }
    return buf;
  }
}
