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

import com.google.opengse.UtilityClassTestCase;

import org.easymock.EasyMock;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import static org.junit.matchers.JUnitMatchers.containsString;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Unit test for {@link FilterChains}
 *
 * @author Mick Killianey
 */
public class FilterChainsTest extends UtilityClassTestCase {

  @Override protected Class<?> getClassUnderTest() {
    return FilterChains.class;
  }

  @Test
  public void testWithPlainMessageSetsContentTypeToTextPlainAndOutputIsMessage()
      throws IOException, ServletException {
    StringWriter capturedOutput = new StringWriter();
    PrintWriter printWriter = new PrintWriter(capturedOutput);
    String message = "Hello, world!";

    HttpServletRequest request
        = EasyMock.createMock(HttpServletRequest.class);
    HttpServletResponse response
        = EasyMock.createMock(HttpServletResponse.class);

    EasyMock.expect(response.getWriter()).andReturn(printWriter).anyTimes();
    response.setContentType("text/plain");
    EasyMock.expectLastCall().atLeastOnce();

    EasyMock.replay(request, response);

    FilterChain filterChain = FilterChains.withPlainMessage(message);
    filterChain.doFilter(request, response);

    assertThat(capturedOutput.toString(), containsString(message));

    EasyMock.verify(request, response);
  }

}
