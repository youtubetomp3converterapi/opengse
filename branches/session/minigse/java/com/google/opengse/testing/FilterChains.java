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

package com.google.opengse.testing;

import java.io.IOException;

import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mike Jennings
 */
public final class FilterChains {
  private FilterChains() { throw new AssertionError(); }

  public static FilterChain withPlainMessage(String expectedMessage) {
    return new MessageFilterChain(expectedMessage);
  }

  private static class MessageFilterChain implements FilterChain {

    private final String msg;

    private MessageFilterChain(String msg) {
      this.msg = msg;
    }

    public void doFilter(ServletRequest request, ServletResponse resp)
        throws IOException {
      HttpServletResponse response = (HttpServletResponse) resp;
      response.setContentType("text/plain");
      response.getWriter().print(msg);
    }
  }
}
