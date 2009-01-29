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

package com.google.opengse.webapp;

import com.google.opengse.configuration.WebAppServlet;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;

/**
 * Contains bookkeeping for servlets
 *
 * @author Mike Jennings
 * @deprecated
 */
@Deprecated
class JspFileRequestHandlerFactory
    extends ServletOrJspFileRequestHandlerFactory {

  private final String jspFile;

  JspFileRequestHandlerFactory(
      final ErrorPageManager errorManager,
      final WebAppServlet servletDef,
      final String jspFile) {
    super(errorManager, servletDef);
    this.jspFile = jspFile;
  }

  public FilterChain getRequestHandler() {
    return new JspFileRequestHandler(errorManager, servletDef, jspFile);
  }

  public void initialize(ServletContext context) {
    // no-op for jsp-file element
  }
}
