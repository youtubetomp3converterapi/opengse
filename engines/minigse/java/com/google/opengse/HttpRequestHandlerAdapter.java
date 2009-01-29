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

package com.google.opengse;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;

/**
 * Adapts an HttpRequestHandler to a FilterChain. The FilterChain that this adapter
 * wraps should not throw any checked exceptions. All exceptions are meant to be handled
 * by the FilterChain and appropriate messages displayed to the user.
 *
 * The HttpRequestHandler.handleRequest() method does not throw any checked exceptions
 * (see the javadoc for that method) so any IOExceptions or ServletExceptions (which should NOT occur)
 * get translated into generic RuntimeException objects.
 *
 * @author Mike Jennings
 */
public class HttpRequestHandlerAdapter implements HttpRequestHandler {
  private FilterChain dispatcher;

  public HttpRequestHandlerAdapter(FilterChain dispatcher) {
    this.dispatcher = dispatcher;
  }

  public void handleRequest(HttpRequest request, HttpResponse response) {
    try {
      dispatcher.doFilter(new HttpServletRequestAdapter(request), new HttpServletResponseAdapter(response));
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (ServletException e) {
      throw new RuntimeException(e);
    }
  }
}
