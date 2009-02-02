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


  /*
  Uses tests.javax_servlet_http.HttpServletResponseWrapper.HttpServletResponseWrapperSetStatusMsgTestServlet on the server
   */
  @Test
  public void testHttpServletResponseWrapperSetStatusMsg()
      throws Exception {
    HttpRequestAsserter get = createGetAssertion("/servlet-tests/hsresw/HttpServletResponseWrapperSetStatusMsgTest");
    get.setExpectedContentType("text/html");
    get.setExpectedResponseCode(SC_FORBIDDEN);
    get.setExpectedErrorMessageContains("in HttpServletResponseWrapperSetStatusMsgTest servlet");
    get.connectToServerAndAssert();
  }


  /**
   * Uses tests.javax_servlet_http.HttpServletRequestWrapper.HttpServletRequestWrapperGetHeadersTestServlet
   *
   * Test for default behavior of this method to return getHeaders(String name) on the
   * wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14
   * 
   * @throws Exception
   */
  @Test
  public void testHttpServletRequestWrapperGetHeaders()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for default behavior of this method to return getHeaders(String name) on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequestWrapper/HttpServletRequestWrapperGetHeadersTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/hsreqw/HttpServletRequestWrapperGetHeadersTest HTTP/1.0");
    httpAssert.setRequestHeaders(
        "MyHeader:myheadervalue1|MyHeader:myheadervalue2");
    httpAssert.setTestName("HttpServletRequestWrapperGetHeadersTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet the tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  /**
   * Uses tests.javax_servlet_http.HttpServletRequest.GetHeadersTestServlet
   *  Returns all the values of the specified request header as an Enumeration of String objects.
   *  Specified in the Java Servlet Pages Specification v2.3, Sec 14.
   *
   * @throws Exception
   */
  @Test
  public void testGetHeaders()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns all the values of the specified request header as an Enumeration of String objects.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetHeadersTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetHeadersTest HTTP/1.0");
    httpAssert.setRequestHeaders(
        "Accept-Language:en-us|Accept-Language:ga-us");
    httpAssert.setTestName("GetHeadersTest");
    httpAssert.setTestStrategy(
        "A test for HttpServletRequest.getHeaders() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  /**
   * Test what request headers we have sent by invoking the servlet that
   * responds to "*.hdr"
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testSessionCookie()
      throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri("/contextpath/gimmecookie");
//    get.setRequestHeader("X-my-special-header", "ExtraSpecial, VerySpecial");
    get.setExpectedResponseLine("PASSED");
    get.expectHeader("SET-COOKIE");
    get.setExpectedResponseCode(200);
    get.connectToServerAndAssert();
    List<Cookie> cookies = get.getResponseCookies();
    assertEquals("More than one cookie found", 1, cookies.size());
    Cookie cookie = cookies.iterator().next();

    assertNotNull("Invalid cookie", cookie.getPath());

    // now we send that cookie back to the server
    get = createGetAssertion();
    get.setUri("/contextpath/sessioninfo");
//    get.expectHeader("SET-COOKIE");
    get.setExpectedResponseCode(200);
    get.setExpectedContentType("text/plain");
    cookie.setValue(cookie.getValue() + "foo");
    get.addRequestCookie(cookie);
    get.setExpectedResponseViaResource(
        "com/google/opengse/golden/noSessionRequested.txt");
    get.connectToServerAndAssert();
  }

  /**
   * Tells the forwarding servlet to forward the request to
   * {@code /bar/foo.test?woo=hoo} then checks the result.
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testForward2()
      throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri("/contextpath/fwd?to=/bar/foo.test%3Fwoo%3Dhoo");
    get.setExpectedResponseLine(
        "requestURI=/contextpath/bar/foo.test");
    get.setExpectedResponseLine(
        "contextPath=/contextpath");
    get.setExpectedResponseLine(
        "servletPath=/bar/foo.test");
    get.setExpectedResponseLine(
        "pathInfo=null");
    /*
     * we are forwarding to "/bar/foo.test?woo=hoo" from a servlet which gets
     * a query string of "to=something" the servlet we forward to should then
     * see a query string of "woo=hoo&to=something" (in this particular case,
     * "something" is "/bar/foo.test%3Fwoo=hoo"
     */
    get.setExpectedResponseLine(
        "queryString=woo=hoo&to=/bar/foo.test?woo=hoo");
    get.setExpectedResponseLine(
        "attribute javax.servlet.forward.context_path=/contextpath");
    get.setExpectedResponseLine(
        "attribute javax.servlet.forward.servlet_path=/fwd");
    get.setExpectedResponseLine(
        "attribute javax.servlet.forward.query_string=to=/bar/foo.test%3Fwoo%3Dhoo");
    get.setExpectedResponseCode(200);
    get.connectToServerAndAssert();
  }

  /**
   * See XPoweredByHeaderTestlet for the expected header format.
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testXPoweredByHeader()
      throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri("/contextpath/XPoweredByHeaderTest");
    get.setExpectedPassResponseLine();
    get.setExpectedResponseCode(200);
    get.connectToServerAndAssert();
  }

  /**
   * The filter should only be triggered via a forward dispatching.
   * web.xml:
   * <filter-mapping>
   * <filter-name>ServletForwardFilter</filter-name>
   * <url-pattern>/ForwardedDispatcherFilterTest</url-pattern>
   * <dispatcher>FORWARD</dispatcher>
   * </filter-mapping>
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testForwardOnlyDispatcherFilter()
      throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri("/contextpath/ForwardedDispatcherFilterTest");
    get.setExpectedResponseCode(200);
    get.setUnexpectedResponseLine("Hello from ServletForwardFilter request");
    get.setUnexpectedResponseLine("Hello from ServletForwardFilter response");
    get.connectToServerAndAssert();

    get = createGetAssertion();
    get.setUri(
        "/contextpath/DispatcherInclude?includeTarget=/ForwardedDispatcherFilterTest");
    get.setExpectedResponseCode(200);
    get.setUnexpectedResponseLine("Hello from ServletForwardFilter request");
    get.setUnexpectedResponseLine("Hello from ServletForwardFilter response");
    get.connectToServerAndAssert();

    get = createGetAssertion();
    get.setUri(
        "/contextpath/DispatcherForward?forwardTarget=/ForwardedDispatcherFilterTest");
    get.setExpectedResponseCode(200);
    get.setExpectedResponseLine(
        "Hello from ServletForwardFilter request");
    get.setExpectedResponseLine(
        "Hello from ServletForwardFilter response");
    get.connectToServerAndAssert();
  }

  /**
   * 500 is generated for an uncaught session exception.
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testUnhandledSessionException() throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri(
        "/contextpath/UnhandledSessionExceptionTest?id=noMoreAttribute");
    get.setExpectedResponseCode(500);
    get.setExpectedContentType("text/html");
    get.connectToServerAndAssert();
  }

  /**
   * The same as the above TC, except the path comes without a trailing "/".
   * According to the spec, this will be redirected (or rewritten) to
   * the one with trailing "/", i.e. if there is not servlet URL matching.
   *
   * Note servlet URL matching has to be exact, unless it's a wildcard matching.
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testPathWithoutTrailingSlashWelcomeFileAccess() throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri("/contextpath/welcome");
    get.setExpectedResponseCode(200);
    get.setExpectedResponseLine(
        "welcome1");
    get.connectToServerAndAssert();
  }

  /**
   * There is only one welcome file under welcome/welcome, and we use the
   * full path matching to locate the target - i.e. welcome2.txt.
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testPathMatchingWelcomeFileAccess() throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri("/contextpath/welcome/welcome");
    get.setExpectedResponseCode(200);
    get.setExpectedResponseLine(
        "welcome2");
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
    HttpRequestAsserter get = createGetAssertion();
    get.setUri("/contextpath/servletpath2/pathinfo");
    get.setExpectedContentType("text/plain");
    get.setExpectedResponseCode(200);
    get.setExpectedResponseLine("servletPath=");      // =""
    get.setExpectedResponseLine("pathInfo=/servletpath2/pathinfo");
    get.connectToServerAndAssert();
  }
  
}
