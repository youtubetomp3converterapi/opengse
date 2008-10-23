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

import junit.framework.TestCase;
import org.easymock.classextension.EasyMock;
import org.junit.Test;

import javax.servlet.FilterChain;

/**
 * @author jennings
 *         Date: Jul 13, 2008
 */
public class HttpRequestHandlerAdapterTest extends TestCase {
  @Test
  public void testHandleRequest() throws Exception {
    FilterChain chain = EasyMock.createMock(FilterChain.class);
    HttpRequest request = EasyMock.createMock(HttpRequest.class);
    HttpResponse response = EasyMock.createMock(HttpResponse.class);
    HttpRequestHandlerAdapter adapter = new HttpRequestHandlerAdapter(chain);
    chain.doFilter(EasyMock.isA(HttpServletRequestAdapter.class),
        EasyMock.isA(HttpServletResponseAdapter.class));
    EasyMock.expectLastCall();
    EasyMock.expect(request.getConnectionInformation()).andReturn(null);
    EasyMock.replay(chain, request, response);
    adapter.handleRequest(request, response);
    EasyMock.verify(chain, request, response);
  }
}
