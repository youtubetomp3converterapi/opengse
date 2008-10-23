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

import javax.servlet.*;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletRequest;

/**
 * @author jennings
*         Date: Jun 22, 2008
*/
class HttpServletRequestAttributeListenerWrapper
    extends HttpServletRequestWrapper {
  private final ServletContext context;
  private final ServletRequestAttributeListener listener;

  public HttpServletRequestAttributeListenerWrapper(
      final HttpServletRequest request,
      final ServletContext context,
      final ServletRequestAttributeListener listener) {
    super(request);
    this.context = context;
    this.listener = listener;
  }

  private ServletRequestAttributeEvent createEvent(
          final String name, final Object value) {
    return new ServletRequestAttributeEvent(context, this, name, value);
  }

  @Override
  public void setAttribute(final String name, final Object value) {
    final Object oldValue = getAttribute(name);
    if (oldValue != null) {
      listener.attributeReplaced(createEvent(name, oldValue));
    } else {
      listener.attributeAdded(createEvent(name, value));
    }
    super.setAttribute(name, value);
  }

  @Override
  public void removeAttribute(final String name) {
    final Object oldValue = getAttribute(name);
    if (oldValue != null) {
      listener.attributeRemoved(createEvent(name, oldValue));
    }
    super.removeAttribute(name);
  }
}