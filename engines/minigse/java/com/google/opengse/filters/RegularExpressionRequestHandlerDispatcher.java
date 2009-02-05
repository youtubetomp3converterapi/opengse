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

package com.google.opengse.filters;

import javax.servlet.FilterChain;

/**
 * This is a wrapper/compositor over {@code RegularExpressionRequestHandler}.
 *
 * It understands the "dispatcher" type in selecting the correct handler for
 * applying filters.
 *
 * TODO(jennings): refactor this?
 *
 * @author Wenbo Zhu
 */
public class RegularExpressionRequestHandlerDispatcher {

  private RegularExpressionRequestHandler requestHandler;
  private RegularExpressionRequestHandler forwardHandler;
  private RegularExpressionRequestHandler includeHandler;
  private RegularExpressionRequestHandler errorHandler;

  public RegularExpressionRequestHandler getRequestHandler() {
    return requestHandler;
  }

  public RegularExpressionRequestHandler getForwardHandler() {
    return forwardHandler;
  }

  public RegularExpressionRequestHandler getIncludeHandler() {
    return includeHandler;
  }

  public RegularExpressionRequestHandler getErrorHandler() {
    return errorHandler;
  }

  /**
   * Creates a RegularExpressionRequestHandlerDispatcher given a default
   * request handler.
   */
  public static RegularExpressionRequestHandlerDispatcher create(
      FilterChain defaultHandler) {
    RegularExpressionRequestHandlerDispatcher handlerDispatcher =
        new RegularExpressionRequestHandlerDispatcher();
    handlerDispatcher.requestHandler =
        RegularExpressionRequestHandler.create(defaultHandler);
    handlerDispatcher.forwardHandler =
        RegularExpressionRequestHandler.create(defaultHandler);
    handlerDispatcher.includeHandler =
        RegularExpressionRequestHandler.create(defaultHandler);
    handlerDispatcher.errorHandler =
        RegularExpressionRequestHandler.create(defaultHandler);
    return handlerDispatcher;
  }

}
