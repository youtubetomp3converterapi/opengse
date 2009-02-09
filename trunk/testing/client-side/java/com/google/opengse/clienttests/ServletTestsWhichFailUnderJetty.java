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
public class ServletTestsWhichFailUnderJetty extends ServletTestsWhichConnectToARemoteServer {
  @Test
  public void testHttpServletRequestWrapperGetRequestURL()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for default behavior of this method to return getRequestURL() on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequestWrapper/HttpServletRequestWrapperGetRequestURLTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/hsreqw/HttpServletRequestWrapperGetRequestURLTest HTTP/1.0");
    // we don't support variables  - like org.apache.watchdog.task.GTest
    httpAssert.setRequestHeaders(
        "prefix:http|server:${host}|port:${port}|servletpath:_servlet-tests_hsreqw_HttpServletRequestWrapperGetRequestURLTest");
    //httpAssert.setRequestHeaders(
    //    "prefix:http|server:${host}|port:${port}|servletpath:_servlet-tests_hsreqw_HttpServletRequestWrapperGetRequestURLTest");
    httpAssert.setTestName("HttpServletRequestWrapperGetRequestURLTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet the tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveContentType()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "When the contentType attribute of the page  directive is specified, it will set the character encoding and MIME type in the response to the client. JavaServer Pages Specification v1.2, Sec. 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setExpectHeaders("Content-Type:text/plain;charset=ISO-8859-1");
    httpAssert.setFirstTask("true");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/content/positiveContenttype.jsp HTTP/1.0");
    httpAssert.setTestName("positiveContentTypeTest");
    httpAssert.setTestStrategy(
        "Using the page directive, set the  contentType attribute to 'text/plain;charset=ISO-8859-1'. Verify on the client side that the  Content-Type header was properly set in the  response.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }


  @Test
  public void testJspXmlpositiveContentType()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "When the contentType attribute of the page  directive is specified, it will set the character encoding and MIME type in the response to the client. JavaServer Pages Specification v1.2, Sec. 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setExpectHeaders("Content-Type:text/plain;charset=ISO-8859-1");
    httpAssert.setFirstTask("true");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/content/positiveContenttypeXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveContentTypeTest");
    httpAssert.setTestStrategy(
        "Using the page directive, set the  contentType attribute to 'text/plain;charset=ISO-8859-1'. Verify on the client side that the  Content-Type header was properly set in the  response.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }


  /**
   * See XPoweredByHeaderTestlet for the expected header format.
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testXPoweredByHeader()
      throws Exception {
    HttpRequestAsserter get = createGetAssertion("/contextpath/XPoweredByHeaderTest");
    get.expectHeader("X-Powered-By");
    get.setExpectedResponseCode(200);
    get.connectToServerAndAssert();
  }

  /**
   * Test a pure wildcard servlet mapping will return request paths correctly.
   * E.g.
   * mapping: /* FooServlet
   * URL: /foo/bar
   * ServletPath: ""
   * Pathinfo: /foo/bar
   * <p/>
   * Note this also verifies that any user supplied /* mapping rule will take
   * precedence over the default /*mapping rule (which maps to the static file
   * servlet).
   * <p/>
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