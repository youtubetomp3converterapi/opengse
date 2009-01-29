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

package com.google.opengse.httputil;

import com.google.opengse.HeaderUtil;

import javax.servlet.http.Cookie;

/**
 * @author jennings
 *         Date: Nov 2, 2008
 */
public class CookieUtil {

  private CookieUtil() {
  }

  /**
   * Converts a Cookie to a String.
   * 
   * @param cookie
   * @return
   */
  public static String toString(Cookie cookie) {
    StringBuffer buf = new StringBuffer();
    buf.append(cookie.getName());
    buf.append("=");
    buf.append(cookie.getValue());

    if (cookie.getComment() != null) {
      buf.append("; Comment=");
      buf.append(cookie.getComment());
    }
    if (cookie.getDomain() != null) {
      buf.append("; Domain=");
      buf.append(cookie.getDomain());
    }
    if (cookie.getMaxAge() != -1) {
      if (cookie.getVersion() == 0) {
        // Only print a cookie warning once to avoid log spamming.
//        if (HAVE_SHOWN_COOKIE_VERSION_WARNING.compareAndSet(false, true)) {
//          LOGGER.warning(cookie.getName() + " cookie has obsolete version 0; "
//              + "only cookies with version >= 1 should be used; "
//              + "this warning will only be issued once");
//        }
        // netscape (version 0) cookies use expires instead of max-age
        // subtract a day if max age is 0 to avoid clock skew issues
        int maxAge = cookie.getMaxAge();
        long expires = System.currentTimeMillis()
            + (maxAge == 0 ? -86400 : maxAge) * 1000L;
        buf.append("; Expires=");
        buf.append(HeaderUtil.toDateHeader(expires));

        // some very early versions of netscape navigator do not obey
        // the expires attribute unless the path attribute is
        // explicitly set.
        if (cookie.getPath() == null) {
          cookie.setPath("/");
        }
      } else {
        buf.append("; Max-Age=");
        buf.append(cookie.getMaxAge());
      }
    }
    if (cookie.getPath() != null) {
      buf.append("; Path=");
      buf.append(cookie.getPath());
    }
    if (cookie.getSecure()) {
      buf.append("; Secure");
    }
    return buf.toString();
  }

}
