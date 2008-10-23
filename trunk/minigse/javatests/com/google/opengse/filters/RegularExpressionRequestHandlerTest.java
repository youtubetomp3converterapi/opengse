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

import com.google.opengse.filters.RegularExpressionRequestHandler.Entry;
import com.google.opengse.filters.RegularExpressionRequestHandler.PathInfoRequest;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.FilterChain;

/**
 * JUnit tests for RegularExpressionRequestHandler.
 *
 * @author Michael Guntsch
 */
public class RegularExpressionRequestHandlerTest extends TestCase {

  private static final Pattern[] TEST_PATTERNS = {
    Pattern.compile("blabliblu/.*\\.jsp(_a*)"), Pattern.compile("bla/.*\\.jsp"),
    Pattern.compile(".*\\.jsp"),
    RegularExpressionRequestHandler.MATCH_EVERYTHING_PATTERN
  };
  private static final String EXISTING_PATH_WITHOUT_PATHINFO =
    "bla/HelloWorld.jsp";
  private static final String PATHINFO = "_aaa";
  private static final String EXISTING_PATH_WITH_PATHINFO =
    "blabliblu/HelloWorld.jsp" + PATHINFO;
  private static final String NONEXISTING_PATH = "YabbaDabbaDoo!";


  private static final Map<Pattern, FilterChain> TEST_HANDLERS =
    new HashMap<Pattern, FilterChain>() {
      private static final long serialVersionUID = 6L;
      {
        put(TEST_PATTERNS[0], EasyMock.createMock(FilterChain.class));
        put(TEST_PATTERNS[1], EasyMock.createMock(FilterChain.class));
        put(TEST_PATTERNS[2], EasyMock.createMock(FilterChain.class));
        put(TEST_PATTERNS[3], EasyMock.createMock(FilterChain.class));
      }
    };

  private RegularExpressionRequestHandler regexpHandler;
  private SortedSet<Entry> entries;
  private HttpServletRequest request;
  private HttpServletResponse response;

  @Override
  protected void setUp() throws Exception {
    entries = new TreeSet<Entry>();
    regexpHandler = RegularExpressionRequestHandler.create(
        TEST_HANDLERS.get(TEST_PATTERNS[3]), entries);
    for (Pattern pattern : TEST_HANDLERS.keySet()) {
      regexpHandler.setHandler(pattern, TEST_HANDLERS.get(pattern));
    }

    request = EasyMock.createMock(HttpServletRequest.class);
    response = EasyMock.createMock(HttpServletResponse.class);
    EasyMock.replay(response);
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
    for (Pattern pattern : TEST_HANDLERS.keySet()) {
      EasyMock.reset(TEST_HANDLERS.get(pattern));
    }
  }

  public void testEntrySortingWorks() throws Exception {
    int i = 0;
    for (Entry entry : entries) {
      assertEquals(entry.getPattern().toString(),
          TEST_PATTERNS[i++].toString());
    }
  }

  private void handleRequestAndExpectCall(FilterChain requestHandler,
      String path, String pathInfo) throws Exception {
    if (pathInfo != null) {
      requestHandler.doFilter(EasyMock.eq(new PathInfoRequest(request,
          pathInfo)), EasyMock.eq(response));
    } else {
      requestHandler.doFilter(EasyMock.eq(request), EasyMock.eq(response));
    }
    for (int i = 0; i < TEST_HANDLERS.size(); i++) {
      EasyMock.replay(TEST_HANDLERS.get(TEST_PATTERNS[i]));
    }
    EasyMock.expect(request.getServletPath()).andReturn(path);
    EasyMock.replay(request);

    regexpHandler.doFilter(request, response);

    for (int i = 0; i < TEST_HANDLERS.size(); i++) {
      EasyMock.verify(TEST_HANDLERS.get(TEST_PATTERNS[i]));
    }
    EasyMock.verify(request);
  }

  public void testCustomEntryWithoutPathInfoIsFound() throws Exception {
    assertTrue(TEST_PATTERNS[1].matcher(
        EXISTING_PATH_WITHOUT_PATHINFO).matches());
    handleRequestAndExpectCall(TEST_HANDLERS.get(TEST_PATTERNS[1]),
        EXISTING_PATH_WITHOUT_PATHINFO, null);
  }

  public void testCustomEntryWithPathInfoIsFound() throws Exception {
    assertTrue(TEST_PATTERNS[0].matcher(EXISTING_PATH_WITH_PATHINFO).matches());
    handleRequestAndExpectCall(TEST_HANDLERS.get(TEST_PATTERNS[0]),
        EXISTING_PATH_WITH_PATHINFO, PATHINFO);
  }

  public void testDefaultEntryIsFound() throws Exception {
    assertTrue(TEST_PATTERNS[3].matcher(NONEXISTING_PATH).matches());
    handleRequestAndExpectCall(TEST_HANDLERS.get(TEST_PATTERNS[3]),
        NONEXISTING_PATH, null);
  }
}
