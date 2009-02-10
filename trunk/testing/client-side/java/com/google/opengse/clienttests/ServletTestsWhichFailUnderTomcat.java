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

package com.google.opengse.clienttests;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.servlet.http.Cookie;
import java.util.List;

/**
 * Author: Mike Jennings
 * Date: Jan 30, 2009
 * Time: 3:24:23 PM
 */
public class ServletTestsWhichFailUnderTomcat extends ServletTestsWhichConnectToARemoteServer {

  /**
   * 500 is generated for an uncaught session exception.
   * Uses com.google.opengse.testlet.Error.UnhandledSessionExceptionTestlet
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testUnhandledSessionException() throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri(
        "/contextpath/UnhandledSessionExceptionTest?id=noMoreAttribute");
    get.setExpectedResponseCode(500);
//    get.setExpectedContentType("text/html");
    get.connectToServerAndAssert();
  }


  /**
   * Test a pure wildcard servlet mapping will return request paths correctly.
   * E.g.
   * mapping: /* FooServlet
   * URL: /foo/bar
   * ServletPath: ""
   * Pathinfo: /foo/bar
   *
   * Note this also verifies that any user supplied /* mapping rule will take
   * precedence over the default /*mapping rule (which maps to the static file
   * servlet).
   *
   * TODO: the /* mapping required for this test interferes with the default
   * static file servlet. Need figure out a way how to test them together,
   * i.e. under the same context path.
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testRequestPathsUnderWildcardOnlyServletMapping()
      throws Exception {
    HttpRequestAsserter get = createGetAssertion("/contextpath/servletpath2/pathinfo");
    get.setExpectedContentType("text/plain");
    get.setExpectedResponseCode(200);
    get.setExpectedResponseLine("WildcardServletDispatchFilter.servletPath=");      // =""
    get.setExpectedResponseLine("WildcardServletDispatchFilter.pathInfo=/servletpath2/pathinfo");
    get.connectToServerAndAssert();
  }
  
}
