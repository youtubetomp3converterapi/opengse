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

package com.google.opengse.clienttests;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertNotNull;

import javax.servlet.http.Cookie;

/**
 * This class is the client-side of the compliance tests.
 * Run this against a servlet engine that has deployed
 * contextpath.war, jsp-tests.war, servlet-compat.war and servlet-tests.war
 *
 * @author Mike Jennings
 */
public class Servlet25ComplianceTests extends ServletTestsWhichConnectToARemoteServer {

// servlet-tests

  @Test
  public void testHttpSessionAttributeReplaced()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for notification that an attribute has been replaced in a session, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.ServletContextAttributeListener. attributeReplaced method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpSessionAttributeListener/HttpSessionAttributeReplacedTest.html");

    
    httpAssert.setRequest(
        "GET /servlet-tests/HttpSessionAttributeReplacedTest HTTP/1.0");
    httpAssert.setTestName("HttpSessionAttributeReplacedTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet that adds/Replaces an attribute. The listener should detect the changes and writes a message out to a static log file. Servlet then reads the log and sends the contents back to the client.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }


  @Test
  public void testHttpSessionValueBound()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
            "Test for notification that the object is being bound to a session specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.http.HttpSessionBindingListener. valueBound method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
            "watchdog/resources/servlet-golden/javax_servlet_http/HttpSessionBindingListener/HttpSessionValueBoundTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/HttpSessionValueBoundTest HTTP/1.0");
    httpAssert.setTestName("HttpSessionValueBoundTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet that sets an attribute to the session. That attribute happens to be a Binding listener. The Listeners valueBound method should be called and a message is written to a static log file. The servlet then reads the log file and sends the data back to the client.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }


  @Test
  public void testHttpSessionBindingEventAdded()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test that when a new attribute is added to the session, the getName() method returns the name with which the object is bound to, the getSession() method returns the session that changed, and the getValue() method returns the value of the attribute being added - specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.http .HttpSessionBindingEvent");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpSessionBindingEvent/HttpSessionBindingEventAddedTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/HttpSessionBindingEventAddedTest HTTP/1.0");
    httpAssert.setTestName("HttpSessionBindingEventAddedTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet that adds an attribute. The listener should detect the add and writes the values returned by the getName, getSession(), and getValue() methods to a static log file. Servlet then reads the log and sends the contents back to the client.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpSessionValueUnBound()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for notification that the object is being unbound to a session specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.http.HttpSessionBindingListener. valueUnBound method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpSessionBindingListener/HttpSessionValueUnBoundTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/HttpSessionValueUnBoundTest HTTP/1.0");
    httpAssert.setTestName("HttpSessionValueUnBoundTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet that sets/removes an attribute from the session. That attribute happens to be a Binding listener. The Listeners valueBound/valueUnbound methods should be called and messages written to a static log file. The servlet then reads the log file and sends the data back to the client.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }


  @Test
  public void testHttpSessionAttributeRemoved()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for notification that an attribute has been removed from a session, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.http.HttpSessionAttributeListener .attributeRemoved method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpSessionAttributeListener/HttpSessionAttributeRemovedTest.html");


    httpAssert.setRequest(
            "GET /servlet-tests/HttpSessionAttributeRemovedTest HTTP/1.0");
    httpAssert.setTestName("HttpSessionAttributeRemovedTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet that adds/Removes an attribute. The listener should detect the changes and writes a message out to a static log file. Servlet then reads the log and sends the contents back to the client.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }


  @Test
  public void testHttpSessionAttributeAdded()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test that a notification is generated that a new attribute was added to the session, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.http .HttpSessionAttributeListener.attributeAdded method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpSessionAttributeListener/HttpSessionAttributeAddedTest.html");


    httpAssert.setRequest(
            "GET /servlet-tests/HttpSessionAttributeAddedTest HTTP/1.0");
    httpAssert.setTestName("HttpSessionAttributeAddedTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet that adds an attribute. The listener should detect the add and writes a message out to a static log file. Servlet then reads the log and sends the contents back to the client.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }



  @Test
  public void testHttpSessionDestroyed()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for notification that a session was invalidated, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.http.HttpSessionListener.sessionCreated method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpSessionListener/HttpSessionDestroyedTest.html");


    httpAssert.setRequest(
            "GET /servlet-tests/HttpSessionDestroyedTest HTTP/1.0");
    httpAssert.setTestName("HttpSessionDestroyedTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet that creates and the invalidates a session. The listener should detect the changes and write a message to a static log file. The Servlet then reads the log file and sends the contents back to the client. As a result of the test, the javax.servlet.http.HttpSessionEvent.getSession() method is tested.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }


  @Test
  public void testHttpSessionGetServletContext()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the ServletContext to which this session belongs, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpSession/HttpSessionGetServletContextTest.html");


    httpAssert.setRequest(
            "GET /servlet-tests/HttpSessionGetServletContextTest HTTP/1.0");
    httpAssert.setTestName("HttpSessionGetServletContextTest");
    httpAssert.setTestStrategy(
        "Call a servlet that makes API call for servlet context");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }


  @Test
  public void testHttpSessionEventGetSession()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
            "Test for the returned session that has changed, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.HttpSessionEvent.getSession method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
            "watchdog/resources/servlet-golden/javax_servlet_http/HttpSessionEvent/HttpSessionEventGetSessionTest.html");


    httpAssert.setRequest(
            "GET /servlet-tests/HttpSessionEventGetSessionTest HTTP/1.0");
    httpAssert.setTestName(
            "HttpSessionEventGetSessionTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet that creates a session. The listener writes the sessionid of the event to a static log file. The Servlet then reads the log file and sends the contents back to the client.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpSessionEventGetSource()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
            "Test for the object on which the Event initially occured, specified in the java.util.EventObject.getSource method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
            "watchdog/resources/servlet-golden/javax_servlet_http/HttpSessionEvent/HttpSessionEventGetSourceTest.html");


    httpAssert.setRequest(
            "GET /servlet-tests/HttpSessionEventGetSourceTest HTTP/1.0");
    httpAssert.setTestName("HttpSessionEventGetSourceTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet that creates a session. The listener writes the source of the event to a static log file. The Servlet then reads the log file and sends the contents back to the client.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpSessionCreated()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
            "Test for notification that a session was created, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.http.HttpSessionListener.sessionCreated method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
            "watchdog/resources/servlet-golden/javax_servlet_http/HttpSessionListener/HttpSessionCreatedTest.html");


    httpAssert.setRequest(
            "GET /servlet-tests/HttpSessionCreatedTest HTTP/1.0");
    httpAssert.setTestName(
            "HttpSessionCreatedTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet that creates a session. The listener should detect the creation and write a message to a static log file. The Servlet then reads the log file and sends the contents back to the client. As a result of the test, the javax.servlet.http.HttpSessionEvent.getSession() method is tested.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }


  @Test
  public void testHttpServletResponseWrapperSendErrorMsgIgnoreHeader()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
            "Test that headers added after a call to sendError(int,String), will be ignored by the container and will not be sent to the client. See Servlet Specification 2.3 section SRV.5.2.");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");


    httpAssert.setRequest(
            "GET /servlet-tests/hsresw/HttpServletResponseWrapperSendErrorMsgIgnoreHeaderTest HTTP/1.0");
    httpAssert.setReturnCode("410");
    httpAssert.setTestName(
            "HttpServletResponseWrapperSendErrorMsgIgnoreHeaderTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet whose response has been wrapped. The wrapper object will call sendError() and then add a header. The header should not be visible to the client.");
    httpAssert.setUnexpectedHeaders(
            "GenericResponseWrapper:sendErrorMsgIgnoreHeader");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }


  @Test
  public void testHttpServletResponseWrapperSendErrorIgnoreHeader()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test that headers added after a call to sendError(int), will be ignored by the container and will not be sent to the client. See Servlet Specification 2.3 section SRV.5.2.");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");


    httpAssert.setRequest(
            "GET /servlet-tests/hsresw/HttpServletResponseWrapperSendErrorIgnoreHeaderTest HTTP/1.0");
    httpAssert.setReturnCode("410");
    httpAssert.setTestName(
            "HttpServletResponseWrapperSendErrorIgnoreHeaderTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet whose response has been wrapped. The wrapper object will call sendError() and then add a header. The header should not be visible to the client.");
    httpAssert.setUnexpectedHeaders(
            "GenericResponseWrapper:sendErrorIgnoreHeader");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }


  @Test
  public void testServletToJSPErrorPage()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
            "Returns the error handling request attributes from an error page (JSP)., specified in the Java Servlet Pages Specification v2.3, Chapter 9");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
            "watchdog/resources/servlet-golden/javax_servlet/Error/ServletToJSPErrorPageTest.html");


    httpAssert.setRequest(
            "GET /servlet-tests/ServletToJSPErrorPageTest HTTP/1.0");
    httpAssert.setTestName(
            "ServletToJSPErrorPageTest");
    httpAssert.setTestStrategy(
        "A servlet throws java.lang.ArrayIndexOutOfBoundsException and the error page (servlet) for that exception is executed.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }


  @Test
  public void testServletToJSPError502Page()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
            "Returns the error handling request attributes from an error page (JSP)., specified in the Java Servlet Pages Specification v2.3, Chapter 9");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/Error/ServletToJSPError502PageTest.html");


    httpAssert.setRequest(
            "GET /servlet-tests/ServletToJSPError502PageTest HTTP/1.0");
    httpAssert.setTestName("ServletToJSPError502PageTest");
    httpAssert.setTestStrategy(
        "A servlet throws java.lang.ArrayIndexOutOfBoundsException and the error page (servlet) for that exception is executed.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testRemoveAttribute_01()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
            "Throws IllegalStateException if this method is called on an invalidated session ,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
            "watchdog/resources/servlet-golden/javax_servlet_http/HttpSession/RemoveAttribute_01Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/RemoveAttribute_01Test HTTP/1.0");
    httpAssert.setTestName("RemoveAttribute_01Test");
    httpAssert.setTestStrategy(
        "A negative test for HttpSession.removeAttribute() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpSessionGetAttribute_01()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
            "Returns null if no object is bound under the name., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
            "watchdog/resources/servlet-golden/javax_servlet_http/HttpSession/HttpSessionGetAttribute_01Test.html");


    httpAssert.setRequest(
            "GET /servlet-tests/HttpSessionGetAttribute_01Test HTTP/1.0");
    httpAssert.setTestName("HttpSessionGetAttribute_01Test");
    httpAssert.setTestStrategy(
        "A negative test for HttpSession.getAttribute() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }


  @Test
  public void testGetRemoteUser_01()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
            "Returns null if the user has not been authenticated., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
            "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetRemoteUser_01Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetRemoteUser_01Test HTTP/1.0");
    httpAssert.setTestName("GetRemoteUser_01Test");
    httpAssert.setTestStrategy(
        "A negative test for HttpServletRequest.getRemoteUser() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testSendError_StringIgnoreHeader()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
            "Verify that headers added after sendError(int,String) are ignored by the container. Servlet Specification 2.3 section SRV.5.2.");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");


    httpAssert.setRequest(
            "GET /servlet-tests/SendError_StringIgnoreHeaderTest HTTP/1.0");
    httpAssert.setReturnCode("410");
    httpAssert.setTestName("SendError_StringIgnoreHeaderTest");
    httpAssert.setTestStrategy(
        "Issue a request to target servlet which will call sendError() and then add a header to the response. The added header should not be visible to the client.");
    httpAssert.setUnexpectedHeaders(
            "HttpServletResponse:sendErrorMsgIgnoreHeader");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetAuthTypeWithoutProtection()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns null if the request was not authenticated,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetAuthTypeWithoutProtectionTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetAuthTypeWithoutProtectionTest HTTP/1.0");
    httpAssert.setTestName("GetAuthTypeWithoutProtectionTest");
    httpAssert.setTestStrategy(
        "A Test for HttpServletRequest.getAuthType() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }


  @Test
  public void testHttpSessionGetAttributeNames_01()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Throws IllegalStateException if this method is called on an invalidated session,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpSession/HttpSessionGetAttributeNames_01Test.html");


    httpAssert.setRequest(
            "GET /servlet-tests/HttpSessionGetAttributeNames_01Test HTTP/1.0");
    httpAssert.setTestName("HttpSessionGetAttributeNames_01Test");
    httpAssert.setTestStrategy(
        "A negative test for HttpSession.getAttributeNames() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }


  @Test
  public void testSendErrorIgnoreHeader()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
            "Verify that headers added after sendError(int) are ignored by the container. Servlet Specification 2.3 section SRV.5.2.");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");


    httpAssert.setRequest(
            "GET /servlet-tests/SendErrorIgnoreHeaderTest HTTP/1.0");
    httpAssert.setReturnCode("410");
    httpAssert.setTestName("SendErrorIgnoreHeaderTest");
    httpAssert.setTestStrategy(
        "Issue a request to target servlet which will call sendError() and then add a header to the response. The added header should not be visible to the client.");
    httpAssert.setUnexpectedHeaders(
            "HttpServletResponse:sendErrorIgnoreHeader");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetPathTranslatedNull()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns null if the URL has no extra path information after the servlet name but before the query string,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetPathTranslatedNullTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetPathTranslatedNullTest HTTP/1.0");
    httpAssert.setTestName("GetPathTranslatedNullTest");
    httpAssert.setTestStrategy(
        "A test for HttpServletRequest.getPathTranslated() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetPathInfo()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns any extra path information associated with the URL the client sent when it made this request.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetPathInfoTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetPathInfoTest/pathinfostring1/pathinfostring2 HTTP/1.0");
    httpAssert.setTestName("GetPathInfoTest");
    httpAssert.setTestStrategy(
        "A test for HttpServletRequest.getPathInfo() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletToHTMLErrorPage()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Serves back the resource (HTML) as indicated by the location entry, specified in the Java Servlet Pages Specification v2.3, Chapter 9");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/Error/ServletToHTMLErrorPageTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ServletToHTMLErrorPageTest HTTP/1.0");
    httpAssert.setTestName("ServletToHTMLErrorPageTest");
    httpAssert.setTestStrategy(
        "A servlet throws java.lang.NumberFormatException and the error page (HTML) for that exception is executed.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletToServletError501Page()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the error handling request attributes from a servlet error page(SERVLET)., specified in the Java Servlet Pages Specification v2.3, Chapter 9");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/Error/ServletToServletError501PageTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ServletToServletError501PageTest HTTP/1.0");
    httpAssert.setTestName("ServletToServletError501PageTest");
    httpAssert.setTestStrategy(
        "A servlet throws java.lang.ArithmeticException and the error page (servlet) for that exception is executed.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }


  @Test
  public void testServletToServletErrorPage()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the error handling request attributes from a servlet error page(SERVLET)., specified in the Java Servlet Pages Specification v2.3, Chapter 9");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/Error/ServletToServletErrorPageTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ServletToServletErrorPageTest HTTP/1.0");
    httpAssert.setTestName("ServletToServletErrorPageTest");
    httpAssert.setTestStrategy(
        "A servlet throws java.lang.ArithmeticException and the error page (servlet) for that exception is executed.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testDoFilter()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The doFilter method of the Filter is called by the container each time a request/response pair is passed through the stack due to a client request for the Servlet in the stack, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.Filter.doFilter method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setFirstTask("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/Filter/DoFilterTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/DoFilterTest HTTP/1.0");
    httpAssert.setTestName("DoFilterTest");
    httpAssert.setTestStrategy(
        "Client attempts to access a servlet and the filter configured for that servlet should be invoked.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testInitFilterConfig()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The container calls this method when the Filter is instantiated and passes in a FilterConfig object. specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.setFilter method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/Filter/InitFilterConfigTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/InitFilterConfigTest HTTP/1.0");
    httpAssert.setTestName("InitFilterConfigTest");
    httpAssert.setTestStrategy(
        "Client attempts to access a servlet and the filter configured for that servlet.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testDoDestroyed()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Called by the servlet container to indicate to a servlet that the servlet is being taken out of service, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.Servlet.destroy() method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/Servlet/DoDestroyedTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/DoDestroyedTest HTTP/1.0");
    httpAssert.setTestName("DoDestroyedTest");
    httpAssert.setTestStrategy(
        "Testing that destroy method is not called during service method execution");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testDoInit1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Called by the servlet container to indicate to a servlet that the servlet is being placed into service., specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.Servlet.init() method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");


    httpAssert.setRequest(
        "GET /servlet-tests/DoInit1Test HTTP/1.0");
    // we expect a 404 indicating a resource is "permanently" unavailable
    httpAssert.setReturnCode("404");
    httpAssert.setTestName("DoInit1Test");
    httpAssert.setTestStrategy(
        "A negative test for the init method. We will throw UnavailableException from inside init. The Servlet should not be initialized");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testDoInit2()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Called by the servlet container to indicate to a servlet that the servlet is being placed into service., specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.Servlet.init() method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/Servlet/DoInit2Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/DoInit2Test HTTP/1.0");
    httpAssert.setTestName("DoInit2Test");
    httpAssert.setTestStrategy(
        "Inside CoreServletTest, which is the parent servlet, we are implementing init() and setting a boolean variable to true. We'll check for the variables here in the DoInit2Test");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testDoServletConfig()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns a ServletConfig object, which contains initialization and startup parameters for this servlet., specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.Servlet.getServletConfig() method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/Servlet/DoServletConfigTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/DoServletConfigTest HTTP/1.0");
    httpAssert.setTestName("DoServletConfigTest");
    httpAssert.setTestStrategy(
        "Create a servlet and test for the getServletConfig() method to be a non-null value and an initial paramter can be retrieved");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testDoServletInfo()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns information about the servlet, such as author, version, and copyright., specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.Servlet.getServletInfo() method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/Servlet/DoServletInfoTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/DoServletInfoTest HTTP/1.0");
    httpAssert.setTestName("DoServletInfoTest");
    httpAssert.setTestStrategy(
        "Create a servlet and test that information is returned");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testPU()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Servlet lifecycle test, check if UnavailableException.isPermanent() is true, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/Servlet/PUTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/PUTest HTTP/1.0");
    httpAssert.setTestName("PUTest");
    httpAssert.setTestStrategy(
        "Create a servlet, throw UnavailableException and test if isPermanent() method is true");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testDoService()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Called by the servlet container to allow the servlet to respond to a request, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.Servlet.service() method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/Servlet/DoServiceTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/DoServiceTest HTTP/1.0");
    httpAssert.setTestName("DoServiceTest");
    httpAssert.setTestStrategy(
        "Inside CoreServletTest, which is the parent servlet, we will override init method and assign some value to the String. We'll check for that value in the service method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testDestroy()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Called by the servlet container to indicate to a servlet that the servlet is being taken out of service., specified in the Java Servlet Pages Specification v2.3, Sec 16 - javax.servlet.GenericServlet.Destroy() method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");


    httpAssert.setRequest(
        "GET /servlet-tests/DestroyTest HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("DestroyTest");
    httpAssert.setTestStrategy(
        "Create a GenericServlet and take out of service using destroy method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetServletConfig()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns this servlet's ServletConfig object, specified in the Java Servlet Pages Specification v2.3, Sec 16 - javax.servlet.GenericServlet.ServletConfig() method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/GenericServlet/GetServletConfigTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetServletConfigTest HTTP/1.0");
    httpAssert.setTestName("GetServletConfigTest");
    httpAssert.setTestStrategy(
        "Create a GenericServlet and check for its ServletConfig object existence");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetServletContext()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns this servlet's ServletContext object, specified in the Java Servlet Pages Specification v2.3, Sec 16 - javax.servlet.GenericServlet.ServletContext() method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/GenericServlet/GetServletContextTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetServletContextTest HTTP/1.0");
    httpAssert.setTestName("GetServletContextTest");
    httpAssert.setTestStrategy(
        "Create a GenericServlet and check for its ServletContext object existence");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetServletInfo()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns this servlet's ServletInfo object, specified in the Java Servlet Pages Specification v2.3, Sec 16 - javax.servlet.GenericServlet.ServletContext() method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/GenericServlet/GetServletInfoTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetServletInfoTest HTTP/1.0");
    httpAssert.setTestName("GetServletInfoTest");
    httpAssert.setTestStrategy(
        "Create a GenericServlet and check for its ServletInfo object values");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testForward()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Forwards a request from a servlet to another resource (servlet, JSP file, or HTML file) on the server, specified in the Java Servlet Pages Specification v2.3, Sec 16 - javax.servlet.RequestDispatcher.ForwardTest() method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/RequestDispatcher/ForwardTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ForwardTest HTTP/1.0");
    httpAssert.setTestName("ForwardTest");
    httpAssert.setTestStrategy(
        "Create a servlet, get its RequestDispatcher and use it to forward to a servlet");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testForward_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Throws a java.lang.IllegalStateException, if the response was already committed, specified in the Java Servlet Pages Specification v2.3, Sec 16 - javax.servlet.RequestDispatcher.ForwardTest() method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/RequestDispatcher/Forward_1Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/Forward_1Test HTTP/1.0");
    httpAssert.setTestName("Forward_1Test");
    httpAssert.setTestStrategy(
        "A negative test for RequestDispatcher.forward() method. Create a servlet, print a string to the buffer, flush the buffer to commit the string, get its RequestDispatcher and use it to forward to a servlet.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testInclude()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Includes the content of a resource (servlet, JSP page, HTML file) in the response, enabling programmatic server-side includes., specified in the Java Servlet Pages Specification v2.3, Sec 16 - javax.servlet.RequestDispatcher.Include() method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/RequestDispatcher/IncludeTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/IncludeTest HTTP/1.0");
    httpAssert.setTestName("IncludeTest");
    httpAssert.setTestStrategy(
        "Create a servlet, get its RequestDispatcher and use it to include a servlet");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testInclude_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The included servlet cannot change the response status code or set headers; any attempt to make a change is ignored. The request and response parameters must be the same objects as were passed to the calling servlet's service method., specified in the Java Servlet Pages Specification v2.3, Sec 16 - javax.servlet.RequestDispatcher.Include() method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setExpectHeaders("Content-Type:text/plain");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/RequestDispatcher/Include_1Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/Include_1Test HTTP/1.0");
    httpAssert.setTestName("Include_1Test");
    httpAssert.setTestStrategy(
        "A negative test for RequestDispatcher.include() test. Create a servlet, set its Content-Type to be 'text/plain', get its RequestDispatcher and use it to include a servlet. The included servlet tries to change the Content-Type to be text/html. Test at the client side for correct Content-Type.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetServletConfigInitParameterNames()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the names of the servlet's initialization parameters as an Enumeration of String objects, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletConfig/GetServletConfigInitParameterNamesTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetServletConfigInitParameterNamesTest HTTP/1.0");
    httpAssert.setTestName("GetServletConfigInitParameterNamesTest");
    httpAssert.setTestStrategy(
        "Set init parameters in the web.xml file and check for the enumerated values in the servlet.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetServletConfigInitParameterNames_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "If no initialization parameters are set, an empty Enumeration if the servlet has no initialization parameters, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletConfig/GetServletConfigInitParameterNames_1Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetServletConfigInitParameterNames_1Test HTTP/1.0");
    httpAssert.setTestName("GetServletConfigInitParameterNames_1Test");
    httpAssert.setTestStrategy(
        "A negative test for ServletConfig.getInitParameterNames() Do not set init parameters in the web.xml file and check for null value in the servlet.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetServletConfigInitParameter()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns a String containing the value of the named initialization parameter, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletConfig/GetServletConfigInitParameterTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetServletConfigInitParameterTest HTTP/1.0");
    httpAssert.setTestName("GetServletConfigInitParameterTest");
    httpAssert.setTestStrategy(
        "Set init parameters in the web.xml file and check for the value in the servlet.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetServletConfigInitParameter_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "If no initialization parameter is set, this method returns a null value, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletConfig/GetServletConfigInitParameter_1Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetServletConfigInitParameter_1Test HTTP/1.0");
    httpAssert.setTestName("GetServletConfigInitParameter_1Test");
    httpAssert.setTestStrategy(
        "A negative test for ServletConfig.getInitParameter(). Do not set init parameters in the web.xml file and check for null value after in the servlet.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletConfigGetServletContext()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns a reference to the ServletContext in which the servlet is executing, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletConfig/ServletConfigGetServletContextTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ServletConfigGetServletContextTest HTTP/1.0");
    httpAssert.setTestName("ServletConfigGetServletContextTest");
    httpAssert.setTestStrategy(
        "Try to get the ServletContext for this servlet itself");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetServletName()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the name of this servlet instance, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletConfig/GetServletNameTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetServletNameTest HTTP/1.0");
    httpAssert.setTestName("GetServletNameTest");
    httpAssert.setTestStrategy(
        "Try to get the ServletName for this servlet itself");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetMajorVersion()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the major version of the Java Servlet API that this servlet container supports, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletContext/GetMajorVersionTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetMajorVersionTest HTTP/1.0");
    httpAssert.setTestName("GetMajorVersionTest");
    httpAssert.setTestStrategy(
        "Test the ServletContext.getMajorVersion() for this servlet itself");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetMimeType_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns null if the MIME type is not known, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletContext/GetMimeType_1Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetMimeType_1Test HTTP/1.0");
    httpAssert.setTestName("GetMimeType_1Test");
    httpAssert.setTestStrategy(
        "A negative test for getMimeType(). Test the ServletContext.getMimeType() for this servlet itself");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetRealPath()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns a String containing the real path for a given virtual path, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletContext/GetRealPathTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetRealPathTest HTTP/1.0");
    httpAssert.setTestName("GetRealPathTest");
    httpAssert.setTestStrategy(
        "Test the ServletContext.getRealPath() for this servlet itself");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetResourceAsStream()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the resource located at the named path as an InputStream object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletContext/GetResourceAsStreamTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetResourceAsStreamTest HTTP/1.0");
    httpAssert.setTestName("GetResourceAsStreamTest");
    httpAssert.setTestStrategy(
        "A Test for getResourceAs Stream method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetResourceAsStream_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns null if no resource exists at the specified path, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletContext/GetResourceAsStream_1Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetResourceAsStream_1Test HTTP/1.0");
    httpAssert.setTestName("GetResourceAsStream_1Test");
    httpAssert.setTestStrategy(
        "A negative test for getResourceAsStream() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetResource()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns a URL to the resource that is mapped to a specified path, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletContext/GetResourceTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetResourceTest HTTP/1.0");
    httpAssert.setTestName("GetResourceTest");
    httpAssert.setTestStrategy(
        "A Test for ServletContext.getResource(String) method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetResource_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "This method returns null if no resource is mapped to the pathname, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletContext/GetResource_1Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetResource_1Test HTTP/1.0");
    httpAssert.setTestName("GetResource_1Test");
    httpAssert.setTestStrategy(
        "A negative test for ServletContext.getResource(String) method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetServerInfo()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the name and version of the servlet container on which the servlet is running., specified in the Java Servlet Pages Specification V2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletContext/GetServerInfoTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetServerInfoTest HTTP/1.0");
    httpAssert.setTestName("GetServerInfoTest");
    httpAssert.setTestStrategy(
        "Test for ServletContext.getServerInfo() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testLog_String()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Writes the specified message to a servlet log file, usually an event log., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletContext/Log_StringTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/Log_StringTest HTTP/1.0");
    httpAssert.setTestName("Log_StringTest");
    httpAssert.setTestStrategy(
        "Test for log(String message),by passing the message string. The server specific log file can be looked up to see an outting");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testLog_StringThrowable()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Writes an explanatory message and a stack trace for a given Throwable exception to the servlet log file., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletContext/Log_StringThrowableTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/Log_StringThrowableTest HTTP/1.0");
    httpAssert.setTestName("Log_StringThrowableTest");
    httpAssert.setTestStrategy(
        "Test for log(String message,Throwable)");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletContextGetAttribute()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the servlet container attribute with the given name, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletContext/ServletContextGetAttributeTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ServletContextGetAttributeTest HTTP/1.0");
    httpAssert.setTestName("ServletContextGetAttributeTest");
    httpAssert.setTestStrategy(
        "Try to get the attributes for this servlet itself");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletContextGetAttribute_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns null if there is no attribute by that name, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletContext/ServletContextGetAttribute_1Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ServletContextGetAttribute_1Test HTTP/1.0");
    httpAssert.setTestName("ServletContextGetAttribute_1Test");
    httpAssert.setTestStrategy(
        "A negative test for ServletContext.getAttribute(). Test for null attribute values for this servlet itself");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletContextGetContext()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns a ServletContext object that corresponds to a specified URL on the server, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletContext/ServletContextGetContextTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ServletContextGetContextTest HTTP/1.0");
    httpAssert.setTestName("ServletContextGetContextTest");
    httpAssert.setTestStrategy(
        "Test for ServletContext object for this servlet itself");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletContextGetInitParameterNames()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the names of the context's initialization parameters as an Enumeration of String objects, or an empty Enumeration if the context has no initialization parameters, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletContext/ServletContextGetInitParameterNamesTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ServletContextGetInitParameterNamesTest HTTP/1.0");
    httpAssert.setTestName("ServletContextGetInitParameterNamesTest");
    httpAssert.setTestStrategy(
        "Test the ServletContext.getInitParameterNames() for this servlet itself");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletContextGetInitParameter()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns a String containing the value of the named context-wide initialization parameter, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletContext/ServletContextGetInitParameterTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ServletContextGetInitParameterTest HTTP/1.0");
    httpAssert.setTestName("ServletContextGetInitParameterTest");
    httpAssert.setTestStrategy(
        "Test the ServletContext.getInitParameter(String) for this servlet itself");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletContextGetInitParameter_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns a null if the parameter does not exist, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletContext/ServletContextGetInitParameter_1Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ServletContextGetInitParameter_1Test HTTP/1.0");
    httpAssert.setTestName("ServletContextGetInitParameter_1Test");
    httpAssert.setTestStrategy(
        "A negative test for ServletContext.getInitParameter(). Test the ServletContext.getInitParameterNames() for this servlet itself");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletContextRemoveAttribute()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Removes the attribute with the given name from the servlet context., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletContext/ServletContextRemoveAttributeTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ServletContextRemoveAttributeTest HTTP/1.0");
    httpAssert.setTestName("ServletContextRemoveAttributeTest");
    httpAssert.setTestStrategy(
        "Test for ServletContext.removeAttribute() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletContextSetAttribute()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Binds an object to a given attribute name in this servlet context., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletContext/ServletContextSetAttributeTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ServletContextSetAttributeTest HTTP/1.0");
    httpAssert.setTestName("ServletContextSetAttributeTest");
    httpAssert.setTestStrategy(
        "Test for ServletContext.setAttribute() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletContextGetAttributeNames()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns an Enumeration containing the attribute names available within this servlet context specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletContext/ServletContextGetAttributeNamesTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ServletContextGetAttributeNamesTest HTTP/1.0");
    httpAssert.setTestName("ServletContextGetAttributeNamesTest");
    httpAssert.setTestStrategy(
        "Servlet retrieves attributes which it set itself");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetMinorVersion()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the minor version of the Java Servlet API that this servlet container supports, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletContext/GetMinorVersionTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetMinorVersionTest HTTP/1.0");
    httpAssert.setTestName("GetMinorVersionTest");
    httpAssert.setTestStrategy(
        "Test the ServletContext.getMinorVersion() for this servlet itself");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetMimeType()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the MIME type of the specified file, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletContext/GetMimeTypeTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetMimeTypeTest HTTP/1.0");
    httpAssert.setTestName("GetMimeTypeTest");
    httpAssert.setTestStrategy(
        "Test the ServletContext.getMimeType() for this servlet itself");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletContextGetRequestDispatcher()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns a RequestDispatcher object that acts as a wrapper for the resource located at the given path, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletContext/ServletContextGetRequestDispatcherTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ServletContextGetRequestDispatcherTest HTTP/1.0");
    httpAssert.setTestName("ServletContextGetRequestDispatcherTest");
    httpAssert.setTestStrategy(
        "Test the ServletContext.getRequestDispatcher(String) for this servlet itself");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetNamedDispatcher()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns a RequestDispatcher object that acts as a wrapper for the named servlet. This method returns null if the ServletContext cannot return a RequestDispatcher for any reason, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletContext/GetNamedDispatcherTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetNamedDispatcherTest HTTP/1.0");
    httpAssert.setTestName("GetNamedDispatcherTest");
    httpAssert.setTestStrategy(
        "Servlet verify's that the result from the getNamedDispatcher call and the getServletName call are the same for the servlet.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetRootCause()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the exception that caused this servlet exception., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletException/GetRootCauseTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetRootCauseTest HTTP/1.0");
    httpAssert.setTestName("GetRootCauseTest");
    httpAssert.setTestStrategy(
        "A Test for getRootCause method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletExceptionConstructor1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Constructs a new servlet exception, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletException/ServletExceptionConstructor1Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ServletExceptionConstructor1Test HTTP/1.0");
    httpAssert.setTestName("ServletExceptionConstructor1Test");
    httpAssert.setTestStrategy(
        "A Test for ServletException() constructor method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletExceptionConstructor2()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "A Test for ServletException(String) constructor method, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletException/ServletExceptionConstructor2Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ServletExceptionConstructor2Test HTTP/1.0");
    httpAssert.setTestName("ServletExceptionConstructor2Test");
    httpAssert.setTestStrategy(
        "A Test for ServletException(String) constructor method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletExceptionConstructor3()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Constructs a new servlet exception when the servlet needs to throw an exception and include a message about the 'root cause' exception that interfered with its normal operation, including a description message., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletException/ServletExceptionConstructor3Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ServletExceptionConstructor3Test HTTP/1.0");
    httpAssert.setTestName("ServletExceptionConstructor3Test");
    httpAssert.setTestStrategy(
        "A Test for ServletException(Throwable) constructor method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletExceptionConstructor4()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Constructs a new servlet exception when the servlet needs to throw an exception and include a message about the 'root cause' exception that interfered with its normal operation., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletException/ServletExceptionConstructor4Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ServletExceptionConstructor4Test HTTP/1.0");
    httpAssert.setTestName("ServletExceptionConstructor4Test");
    httpAssert.setTestStrategy(
        "A Test for ServletException(String,Throwable) constructor method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void  testReadLine() throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Reads the input stream, one line at a time., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setContent("ULTRA SPARC");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletInputStream/ReadLineTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ReadLineTest HTTP/1.0");
    httpAssert.setTestName("ReadLineTest");
    httpAssert.setTestStrategy(
        "A Test for readLine method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testPrint_String()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Writes a String to the client, without a carriage return-line feed (CRLF) character at the end., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletOutputStream/Print_StringTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/Print_StringTest HTTP/1.0");
    httpAssert.setTestName("Print_StringTest");
    httpAssert.setTestStrategy(
        "Test for print(java.lang.String s) method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testPrint_boolean()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Writes a boolean value to the client, with no carriage return-line feed (CRLF) character at the end., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletOutputStream/Print_booleanTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/Print_booleanTest HTTP/1.0");
    httpAssert.setTestName("Print_booleanTest");
    httpAssert.setTestStrategy(
        "Test for print(boolean b) method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testPrint_char()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Writes a character to the client, with no carriage return-line feed (CRLF) at the end., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletOutputStream/Print_charTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/Print_charTest HTTP/1.0");
    httpAssert.setTestName("Print_charTest");
    httpAssert.setTestStrategy(
        "Test for print(char c) method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testPrint_double()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Writes a double to the client, with no carriage return-line feed (CRLF) at the end., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletOutputStream/Print_doubleTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/Print_doubleTest HTTP/1.0");
    httpAssert.setTestName("Print_doubleTest");
    httpAssert.setTestStrategy(
        "Test for print(double d) method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testPrint_float()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Writes a float to the client, followed by a carriage return-line feed (CRLF)., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletOutputStream/Print_floatTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/Print_floatTest HTTP/1.0");
    httpAssert.setTestName("Print_floatTest");
    httpAssert.setTestStrategy(
        "Test for println(float f) method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testPrint_int()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Writes an integer to the client, with no carriage return-line feed (CRLF) at the end., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletOutputStream/Print_intTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/Print_intTest HTTP/1.0");
    httpAssert.setTestName("Print_intTest");
    httpAssert.setTestStrategy(
        "Test for print(integer i) method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testPrint_long()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Writes a long to the client, with no carriage return-line feed (CRLF) at the end., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletOutputStream/Print_longTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/Print_longTest HTTP/1.0");
    httpAssert.setTestName("Print_longTest");
    httpAssert.setTestStrategy(
        "Test for print(long l) method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testPrintln()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Writes a carriage return-line feed (CRLF) to the client., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletOutputStream/PrintlnTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/PrintlnTest HTTP/1.0");
    httpAssert.setTestName("PrintlnTest");
    httpAssert.setTestStrategy(
        "Test for println () method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testPrintln_String()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Writes a String to the client, followed by a carriage return-line feed (CRLF)., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletOutputStream/Println_StringTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/Println_StringTest HTTP/1.0");
    httpAssert.setTestName("Println_StringTest");
    httpAssert.setTestStrategy(
        "Test for println(java.lang.String s) method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testPrintln_boolean()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Writes a boolean to the client, followed by a carriage return-line feed (CRLF)., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletOutputStream/Println_booleanTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/Println_booleanTest HTTP/1.0");
    httpAssert.setTestName("Println_booleanTest");
    httpAssert.setTestStrategy(
        "Test for println(boolean b) method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testPrintln_char()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Writes a char to the client, followed by a carriage return-line feed (CRLF)., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletOutputStream/Println_charTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/Println_charTest HTTP/1.0");
    httpAssert.setTestName("Println_charTest");
    httpAssert.setTestStrategy(
        "Test for println(char c) method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testPrintln_double()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Writes a double to the client, followed by a carriage return-line feed (CRLF)., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletOutputStream/Println_doubleTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/Println_doubleTest HTTP/1.0");
    httpAssert.setTestName("Println_doubleTest");
    httpAssert.setTestStrategy(
        "Test for println(double d) method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testPrintln_float()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Writes a float to the client, with no carriage return-line feed (CRLF) at the end., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletOutputStream/Println_floatTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/Println_floatTest HTTP/1.0");
    httpAssert.setTestName("Println_floatTest");
    httpAssert.setTestStrategy(
        "Test for print(float f) method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testPrintln_int()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Writes an integer to the client, followed by a carriage return-line feed (CRLF)., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletOutputStream/Println_intTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/Println_intTest HTTP/1.0");
    httpAssert.setTestName("Println_intTest");
    httpAssert.setTestStrategy(
        "Test for println(integer i) method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testPrintln_long()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Writes a long to the client, followed by a carriage return-line feed (CRLF)., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletOutputStream/Println_longTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/Println_longTest HTTP/1.0");
    httpAssert.setTestName("Println_longTest");
    httpAssert.setTestStrategy(
        "Test for println(long l) method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetContentLength()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the length, in bytes, of the request body and made available by the input stream, or -1 if the length is not known., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setContent("12345678901234567890");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequest/GetContentLengthTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetContentLengthTest HTTP/1.0");
    httpAssert.setRequestHeaders(
        "Content-Type:text/plain");
    httpAssert.setTestName("GetContentLengthTest");
    httpAssert.setTestStrategy(
        "A Test For ServletRequest.getContentLength() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetContentType()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the MIME type of the body of the request, or null if the type is not known., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequest/GetContentTypeTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetContentTypeTest HTTP/1.0");
    httpAssert.setRequestHeaders(
        "Content-Type:text/plain");
    httpAssert.setTestName("GetContentTypeTest");
    httpAssert.setTestStrategy(
        "A Test For ServletRequest.getContentType() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetInputStream()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Retrieves the body of the request as binary data using a ServletInputStream., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequest/GetInputStreamTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetInputStreamTest HTTP/1.0");
    httpAssert.setTestName("GetInputStreamTest");
    httpAssert.setTestStrategy(
        "A Test For ServletRequest.getInputStream() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetInputStream_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The exception IllegalStateException will be thrown if the getReader method has already been called for this request, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequest/GetInputStream_1Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetInputStream_1Test HTTP/1.0");
    httpAssert.setTestName("GetInputStream_1Test");
    httpAssert.setTestStrategy(
        "Servlet attempts to call getInputStream after getReader has already been called");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetLocale()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the preferred Locale that the client will accept content in, based on the Accept-Language header, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequest/GetLocaleTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetLocaleTest HTTP/1.0");
    httpAssert.setRequestHeaders(
        "Accept-Language:en-US");
    httpAssert.setTestName("GetLocaleTest");
    httpAssert.setTestStrategy(
        "Client sets the locale that it will accept and calls servlet. Servlet verifies it received the correct locale");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetLocales()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns an Enumeration of Locale objects indicating, in decreasing order starting with the preferred locale, the locales that are acceptable to the client based on the Accept-Language header. servlet., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequest/GetLocalesTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetLocalesTest HTTP/1.0");
    httpAssert.setRequestHeaders(
        "Accept-Language:en-US,en-GB");
    httpAssert.setTestName("GetLocalesTest");
    httpAssert.setTestStrategy(
        "Client sets the locales that it will accept and calls servlet. Servlet verifies it received the correct locale");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetParameterNames()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns an Enumeration of String objects containing the names of the parameters contained in this request., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequest/GetParameterNamesTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetParameterNamesTest?BestLanguage=Java&BestJSP=Java2 HTTP/1.0");
    httpAssert.setTestName("GetParameterNamesTest");
    httpAssert.setTestStrategy(
        "Client passes 2 parameters to the servlet. Servlet verifies it receives the correct parameters.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetParameterNames_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns an empty Enumerationif no input parameter names are given to the servlet., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequest/GetParameterNames_1Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetParameterNames_1Test HTTP/1.0");
    httpAssert.setTestName("GetParameterNames_1Test");
    httpAssert.setTestStrategy(
        "A negative test for ServletRequest.getParameterNames() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetParameter()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the value of a request parameter as a String specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequest/GetParameterTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetParameterTest?BestLanguage=Java HTTP/1.0");
    httpAssert.setTestName("GetParameterTest");
    httpAssert.setTestStrategy(
        "A test for ServletRequest.getParameter(String) method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetParameterValues()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns an array of String objects containing all of the values the given request parameter has, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequest/GetParameterValuesTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetParameterValuesTest?Containers=JSP&Containers=Servlet HTTP/1.0");
    httpAssert.setTestName("GetParameterValuesTest");
    httpAssert.setTestStrategy(
        "Client sends a single parameter that has 2 values to the servlet. Servlet verifies it received both values.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetParameterValues_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns null as the parameter does not exist., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequest/GetParameterValues_1Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetParameterValues_1Test HTTP/1.0");
    httpAssert.setTestName("GetParameterValues_1Test");
    httpAssert.setTestStrategy(
        "A negative test for ServletRequest.getParameterValues() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetParameter_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns null as the parameter does not exist., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequest/GetParameter_1Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetParameter_1Test HTTP/1.0");
    httpAssert.setTestName("GetParameter_1Test");
    httpAssert.setTestStrategy(
        "A negative test for ServletRequest.getParameter() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetProtocol()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the name and version of the protocol the request uses in the form protocol/majorVersion.minorVersion, for example, HTTP/1.1., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequest/GetProtocolTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetProtocolTest HTTP/1.0");
    httpAssert.setTestName("GetProtocolTest");
    httpAssert.setTestStrategy(
        "A test for ServletRequest.getProtocol() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetReader()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Retrieves the body of the request as character data using a BufferedReader., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequest/GetReaderTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetReaderTest HTTP/1.0");
    httpAssert.setTestName("GetReaderTest");
    httpAssert.setTestStrategy(
        "Get an InputStream object using ServletRequest.getInputStream()");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetReader_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Retrieves the body of the request as character data using a BufferedReader., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequest/GetReader_1Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetReader_1Test HTTP/1.0");
    httpAssert.setTestName("GetReader_1Test");
    httpAssert.setTestStrategy(
        "A negative test for ServletRequest.getReader() method. Get an InputStream object using ServletRequest.getInputStream() then try to get the Reader Object. An IllegalStateException should be thrown");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetRemoteAddr()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the Internet Protocol (IP) address of the client that sent the request., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequest/GetRemoteAddrTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetRemoteAddrTest?Address=|client.ip| HTTP/1.0");
    httpAssert.setTestName("GetRemoteAddrTest");
    httpAssert.setTestStrategy(
        "A test for ServletRequest.getRemoteAddress() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetRemoteHost()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the fully qualified name of the client that sent the request, or the IP address of the client if the name cannot be determined., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequest/GetRemoteHostTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetRemoteHostTest?Address=|client.host|&Address=|client.ip| HTTP/1.0");
    httpAssert.setTestName("GetRemoteHostTest");
    httpAssert.setTestStrategy(
        "A test for ServletRequest.getRemoteHost() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetScheme()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the name of the scheme used to make this request, for example, http, https, or ftp.., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequest/GetSchemeTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetSchemeTest HTTP/1.0");
    httpAssert.setTestName("GetSchemeTest");
    httpAssert.setTestStrategy(
        "A test for ServletRequest.getScheme() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetServerName()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the host name of the server that received the request specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequest/GetServerNameTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetServerNameTest?hostname=${host} HTTP/1.0");
    httpAssert.setTestName("GetServerNameTest");
    httpAssert.setTestStrategy(
        "A test for ServletRequest.getServerName() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetServerPort()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the port number on which this request was received., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequest/GetServerPortTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetServerPortTest?port=${port} HTTP/1.0");
    httpAssert.setTestName("GetServerPortTest");
    httpAssert.setTestStrategy(
        "A test for ServletRequest.getServerPort() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletRequestGetAttributeNames()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns an Enumeration containing the names of the attributes available to this request specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequest/ServletRequestGetAttributeNamesTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ServletRequestGetAttributeNamesTest HTTP/1.0");
    httpAssert.setTestName("ServletRequestGetAttributeNamesTest");
    httpAssert.setTestStrategy(
        "A Test For ServletRequest.getAttributeNames() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletRequestGetAttribute()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the value of the named attribute as an Object specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequest/ServletRequestGetAttributeTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ServletRequestGetAttributeTest HTTP/1.0");
    httpAssert.setTestName("ServletRequestGetAttributeTest");
    httpAssert.setTestStrategy(
        "A Test For ServletRequest.getAttributeName(String) method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletRequestGetAttribute_01()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns null if no attribute of the given name exists., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequest/ServletRequestGetAttribute_01Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ServletRequestGetAttribute_01Test HTTP/1.0");
    httpAssert.setTestName("ServletRequestGetAttribute_01Test");
    httpAssert.setTestStrategy(
        "A Test For ServletRequest.getAttributeName(String) method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletRequestGetCharacterEncoding()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the name of the character encoding used in the body of this request encoding, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequest/ServletRequestGetCharacterEncodingTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ServletRequestGetCharacterEncodingTest HTTP/1.0");
    httpAssert.setRequestHeaders(
        "Content-Type:text/plain; charset=ISO-8859-1");
    httpAssert.setTestName("ServletRequestGetCharacterEncodingTest");
    httpAssert.setTestStrategy(
        "Servlet verifies is receives the default encoding of IS0-8858-1 method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletRequestGetCharacterEncoding_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns a null if the request does not specify a character encoding, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequest/ServletRequestGetCharacterEncoding_1Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ServletRequestGetCharacterEncoding_1Test HTTP/1.0");
    httpAssert.setTestName("ServletRequestGetCharacterEncoding_1Test");
    httpAssert.setTestStrategy(
        "Servlet verifies it receives a null result");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletRequestSetCharacterEncoding_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Throws java.io.UnsupportedEncodingException if the encoding specified is not valid encoding, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequest/ServletRequestSetCharacterEncoding_1Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ServletRequestSetCharacterEncoding_1Test HTTP/1.0");
    httpAssert.setTestName("ServletRequestSetCharacterEncoding_1Test");
    httpAssert.setTestStrategy(
        "servlet attempt to set an invalid encoding and exception should be thrown");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletRequestGetRequestDispatcher()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns a RequestDispatcher object that acts as a wrapper for the resource located at the given path., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequest/ServletRequestGetRequestDispatcherTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ServletRequestGetRequestDispatcherTest HTTP/1.0");
    httpAssert.setTestName("ServletRequestGetRequestDispatcherTest");
    httpAssert.setTestStrategy(
        "A test for ServletRequest.getRequestDispatcher() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletRequestSetAttribute()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Stores an attribute in this request, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequest/ServletRequestSetAttributeTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ServletRequestSetAttributeTest HTTP/1.0");
    httpAssert.setTestName("ServletRequestSetAttributeTest");
    httpAssert.setTestStrategy(
        "Servlet sets an attribute and then verifies it can be read back");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetUnavailableSeconds()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the number of seconds the servlet expects to be temporarily unavailable., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/UnavailableException/GetUnavailableSecondsTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetUnavailableSecondsTest HTTP/1.0");
    httpAssert.setTestName("GetUnavailableSecondsTest");
    httpAssert.setTestStrategy(
        "A test for UnavailableException.getUnavailableSeconds() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testIsPermanent()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns a boolean indicating whether the servlet is permanently unavailable., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/UnavailableException/IsPermanentTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/IsPermanentTest HTTP/1.0");
    httpAssert.setTestName("IsPermanentTest");
    httpAssert.setTestStrategy(
        "A test for UnavailableException.isPermanent() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testUnavailableException_Constructor1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Constructs a new exception with a descriptive message indicating that the servlet is permanently unavailable., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/UnavailableException/UnavailableException_Constructor1Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/UnavailableException_Constructor1Test HTTP/1.0");
    httpAssert.setTestName("UnavailableException_Constructor1Test");
    httpAssert.setTestStrategy(
        "A test for UnavailableException(String mesg). It constructs an UnavailabaleException object for the specified servlet. This constructor tests for permanent unavailability");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testUnavailableException_Constructor2()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Constructs a new exception with a descriptive message indicating that the servlet is temporarily unavailable and giving an estimate of how long it will be unavailable., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/UnavailableException/UnavailableException_Constructor2Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/UnavailableException_Constructor2Test HTTP/1.0");
    httpAssert.setTestName("UnavailableException_Constructor2Test");
    httpAssert.setTestStrategy(
        "A test for UnavailableException(String mesg). It constructs an UnavailabaleException object for the specified servlet. This constructor tests for temporarily unavailability");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testFlushBuffer()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Forces any content in the buffer to be written to the client specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletResponse/FlushBufferTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/FlushBufferTest HTTP/1.0");
    httpAssert.setTestName("FlushBufferTest");
    httpAssert.setTestStrategy(
        "Servlet writes data in the buffer and flushes it");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetBufferSize()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the actual buffer size used for the response., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletResponse/GetBufferSizeTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetBufferSizeTest HTTP/1.0");
    httpAssert.setTestName("GetBufferSizeTest");
    httpAssert.setTestStrategy(
        "A test for ServletResponse.getBufferSize() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetOutputStream_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "illegalStateException is thrown if the getWriter method has been called on this response ,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletResponse/GetOutputStream_1Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetOutputStream_1Test HTTP/1.0");
    httpAssert.setTestName("GetOutputStream_1Test");
    httpAssert.setTestStrategy(
        "A negative test for ServletResponse.getOutputStream() method. We will get a PrintWriter object first and we will try to get an OutPutStream Object. IllegalStateException should be thrown");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetWriter_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "IllegalStateException is thrown if the getOutputStream method has already been called for this response object , specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletResponse/GetWriter_1Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetWriter_1Test HTTP/1.0");
    httpAssert.setTestName("GetWriter_1Test");
    httpAssert.setTestStrategy(
        "A negative test for ServletResponse.getWriter() method. We will get a PrintWriter object first and we will try to get an OutPutStream Object. IllegalStateException should be thrown.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testIsCommitted()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns a boolean indicating if the response has been committed., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletResponse/IsCommittedTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/IsCommittedTest HTTP/1.0");
    httpAssert.setTestName("IsCommittedTest");
    httpAssert.setTestStrategy(
        "A test for ServletResponse.isCommitted() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testReset()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Clears any data that exists in the buffer,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletResponse/ResetTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ResetTest HTTP/1.0");
    httpAssert.setTestName("ResetTest");
    httpAssert.setTestStrategy(
        "Servlet writes out to buffer then clears it. Should only get pass message back");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testReset_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Throws IllegalStateException if the response has already been committed, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletResponse/Reset_1Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/Reset_1Test HTTP/1.0");
    httpAssert.setTestName("Reset_1Test");
    httpAssert.setTestStrategy(
        "A negative Test for ServletResponse.reset() method. Commit the response has been committed, and test if this method throws an IllegalStateException.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletResponseGetCharacterEncoding()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the name of the charset used for the MIME body sent in this response., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletResponse/ServletResponseGetCharacterEncodingTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ServletResponseGetCharacterEncodingTest HTTP/1.0");
    httpAssert.setTestName("ServletResponseGetCharacterEncodingTest");
    httpAssert.setTestStrategy(
        "A test for ServletResponse.getCharacterEncoding() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testSetBufferSize()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Sets the preferred buffer size for the body of the response. specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletResponse/SetBufferSizeTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/SetBufferSizeTest HTTP/1.0");
    httpAssert.setTestName("SetBufferSizeTest");
    httpAssert.setTestStrategy(
        "A test for ServletResponse.setBufferSize() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testSetBufferSize_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "illegalStateException is thrown if this method is called after content has been written, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletResponse/SetBufferSize_1Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/SetBufferSize_1Test HTTP/1.0");
    httpAssert.setTestName("SetBufferSize_1Test");
    httpAssert.setTestStrategy(
        "A negative test for ServletResponse.setBufferSize() method. Invoke setBufferSize method, after the content is written using ServletOutputStream. Test for IllegalStateException error");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testSetContentLength()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Sets the length of the content body in the response, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setExpectHeaders("Content-Length:33");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletResponse/SetContentLengthTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/SetContentLengthTest HTTP/1.0");
    httpAssert.setTestName("SetContentLengthTest");
    httpAssert.setTestStrategy(
        "A test for ServletResponse.setContentLength() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testSetContentType()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Sets the content type of the response being sent to the client., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setExpectHeaders("Content-Type:text/html");


    httpAssert.setRequest(
        "GET /servlet-tests/SetContentTypeTest HTTP/1.0");
    httpAssert.setTestName("SetContentTypeTest");
    httpAssert.setTestStrategy(
        "A test for ServletResponse.setContentType() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testSetLocale()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Sets the locale of the response, setting the headers (including the Content-Type's charset) as appropriate., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setExpectHeaders("Content-Language:en-US");


    httpAssert.setRequest(
        "GET /servlet-tests/SetLocaleTest HTTP/1.0");
    httpAssert.setTestName("SetLocaleTest");
    httpAssert.setTestStrategy(
        "A test for ServletResponse.setLocale() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetFilterName()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "This method returns the filter-name of this filter as defined in the deployment descriptor, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.FilterConfig.getFilterName method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/FilterConfig/GetFilterNameTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetFilterNameTest HTTP/1.0");
    httpAssert.setTestName("GetFilterNameTest");
    httpAssert.setTestStrategy(
        "Client attempts to access a servlet and the filter configured for that servlet should be invoked.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetInitParamNames()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The Filter returns the names of the servlet's initialization parameters as an Enumeration of String objects, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.Config.getInitParamterNames method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/FilterConfig/GetInitParamNamesTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetInitParamNamesTest HTTP/1.0");
    httpAssert.setTestName("GetInitParamNamesTest");
    httpAssert.setTestStrategy(
        "Client attempts to access a servlet and the filter configured for that servlet should be invoked.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetInitParamNamesNull()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The Filter returns an empty Enumeration if the    names of the servlet's initialization parameters do not exist, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.Config.getInitParamterNames method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/FilterConfig/GetInitParamNamesNullTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetInitParamNamesNullTest HTTP/1.0");
    httpAssert.setTestName("GetInitParamNamesNullTest");
    httpAssert.setTestStrategy(
        "Client attempts to access a servlet and the filter configured for that servlet should be invoked.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetInitParam()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The Filter returns a String containing the value of the named initialization parameter, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.Config.getInitParameter method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/FilterConfig/GetInitParamTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetInitParamTest HTTP/1.0");
    httpAssert.setTestName("GetInitParamTest");
    httpAssert.setTestStrategy(
        "Client attempts to access a servlet and the filter configured for that servlet should be invoked.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetInitParamNull()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The Filter returns a null if the parameter does not exist, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.Config.getInitParameter method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/FilterConfig/GetInitParamNullTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetInitParamNullTest HTTP/1.0");
    httpAssert.setTestName("GetInitParamNullTest");
    httpAssert.setTestStrategy(
        "Client attempts to access a servlet and the filter configured for that servlet should be invoked.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testFilterChain()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The next filter in the chain to be invoked, or if the calling filter is the last filter in the chain, causes the resource at the end of the chain to be invoked, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.FilterChain.doFilter method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/FilterChain/FilterChainTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/FilterChainTest HTTP/1.0");
    httpAssert.setTestName("FilterChainTest");
    httpAssert.setTestStrategy(
        "Client attempts to access a servlet and both filters configured for that servlet should be invoked.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletContextAttributeAdded()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test that a notification is generated that a new attribute was added to the servlet context, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet. ServletContextAttributeListener.attributeAdded method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletContextAttributeListener/ServletContextAttributeAddedTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ServletContextAttributeAddedTest HTTP/1.0");
    httpAssert.setTestName("ServletContextAttributeAddedTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet that adds an attribute. The listener should detect the add and write a message out to a static log file. Servlet then reads the file and sends the files contents back to the client.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletContextAttributeRemoved()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test that a notification is generated that an existing attribute has been removed from the servlet context, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.ServletContextAttributeListener. attributeRemoved method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletContextAttributeListener/ServletContextAttributeRemovedTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ServletContextAttributeRemovedTest HTTP/1.0");
    httpAssert.setTestName("ServletContextAttributeRemovedTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet that adds/removes an attribute. The listener should detect the two actions and write a message out to a static log file. Servlet then reads the file and sends the files contents back to the client.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletContextAttributeReplaced()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test that a notification is generated that an existing attribute has been replaced from the servlet context, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.ServletContextAttributeListener. attributeReplaced method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletContextAttributeListener/ServletContextAttributeReplacedTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ServletContextAttributeReplacedTest HTTP/1.0");
    httpAssert.setTestName("ServletContextAttributeReplacedTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet that adds/replaces an attribute. The listener should detect the two actions and write a message to a static log file. Servlet then reads the file and sends the files contents back to the client.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testContextInitialized()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for notification that the web application is ready to process requests after the context is initialized, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.ServletContextListener.contextInitialized method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletContextListener/ContextInitializedTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ContextInitializedTest HTTP/1.0");
    httpAssert.setTestName("ContextInitializedTest");
    httpAssert.setTestStrategy(
        "A ServletContext Listener is deployed and the listener write a message indicating so to a static log file. The client calls a servlet that reads the log and send the info back to the client");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletContextAttributeAddedEvent()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test that a notification is generated that a new attribute was added to the servlet context, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet. ServletContextAttributeListener.attributeAdded method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletContextAttributeEvent/ServletContextAttributeAddedEventTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ServletContextAttributeAddedEventTest HTTP/1.0");
    httpAssert.setTestName("ServletContextAttributeAddedEventTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet that adds an attribute. The listener should detect the add and write the name and value out to a static log file. Servlet then reads the log file and sends the contents back to the client.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletContextAttributeRemovedEvent()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test that a notification is generated that an existing attribute has been removed from the servlet context, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.ServletContextAttributeListener. attributeRemoved method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletContextAttributeEvent/ServletContextAttributeRemovedEventTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ServletContextAttributeRemovedEventTest HTTP/1.0");
    httpAssert.setTestName("ServletContextAttributeRemovedEventTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet that adds/removes an attribute. The listener should detect the two actions and write the name and value out to a static log file. Servlet then reads the log file and sends the contents back to the client.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletContextAttributeReplacedEvent()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test that a notification is generated that an existing attribute has been replaced from the servlet context, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.ServletContextAttributeListener. attributeReplaced method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletContextAttributeEvent/ServletContextAttributeReplacedEventTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ServletContextAttributeReplacedEventTest HTTP/1.0");
    httpAssert.setTestName("ServletContextAttributeReplacedEventTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet that adds/replaces an attribute. The listener should detect the two actions and write the name and value out to a static log file. Servlet then reads the log file and sends the contents back to the client.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletContextAttributeEventConstructor()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test that ServletContextAttributeEvent can be constructed from the given context for the given attribute name and value specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.ServletContextAttributeEvent.");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletContextAttributeEvent/ServletContextAttributeEventConstructorTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ServletContextAttributeEventConstructorTest HTTP/1.0");
    httpAssert.setTestName("ServletContextAttributeEventConstructorTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet that creates a ServletContextAttributeEvent object.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletContextGetSource()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for the object on which the Event initially occurred specified in the java.util.EventObject.getSource method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletContextEvent/ServletContextGetSourceTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ServletContextGetSourceTest HTTP/1.0");
    httpAssert.setTestName("ServletContextGetSourceTest");
    httpAssert.setTestStrategy(
        "Deploy a servlet context event listener. When the context gets initialized, write a status message to a static log. Client calls servlet which reads the static log looking for a specific message and returns the message to the client.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletContextGetServletContext()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the ServletContext that has changed, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.ServletContextEvent.getServletContext method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletContextEvent/ServletContextGetServletContextTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ServletContextGetServletContextTest HTTP/1.0");
    httpAssert.setTestName("ServletContextGetServletContextTest");
    httpAssert.setTestStrategy(
        "Deploy a servlet context event listener. When the context gets initialized, write a status message to a static log. Client calls servlet which reads the static log looking for a specific message and returns the message to the client.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletRequestWrapperGetAttributeNames()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to call getAttributeNames() on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequestWrapper/ServletRequestWrapperGetAttributeNamesTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sreqw/ServletRequestWrapperGetAttributeNamesTest HTTP/1.0");
    httpAssert.setTestName("ServletRequestWrapperGetAttributeNamesTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletRequestWrapperGetAttribute()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to call getAttribute(String name)on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequestWrapper/ServletRequestWrapperGetAttributeTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sreqw/ServletRequestWrapperGetAttributeTest HTTP/1.0");
    httpAssert.setTestName("ServletRequestWrapperGetAttributeTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletRequestWrapperGetAttribute_01()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to call getAttribute(String name)on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequestWrapper/ServletRequestWrapperGetAttribute_01Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sreqw/ServletRequestWrapperGetAttribute_01Test HTTP/1.0");
    httpAssert.setTestName("ServletRequestWrapperGetAttribute_01Test");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests that null is returned for a get of an attribute that does not exist and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletRequestWrapperGetCharacterEncoding()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to return getCharacterEncoding() on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequestWrapper/ServletRequestWrapperGetCharacterEncodingTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sreqw/ServletRequestWrapperGetCharacterEncodingTest HTTP/1.0");
    httpAssert.setRequestHeaders(
        "Content-Type:text/plain; charset=ISO-8859-1");
    httpAssert.setTestName("ServletRequestWrapperGetCharacterEncodingTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletRequestWrapperGetContentLength()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to return getContentLength() on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setContent("calling ServletRequestWrapperGetContentLengthTest");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequestWrapper/ServletRequestWrapperGetContentLengthTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sreqw/ServletRequestWrapperGetContentLengthTest HTTP/1.0");
    httpAssert.setRequestHeaders(
        "Content-Type:text/plain");
    httpAssert.setTestName("ServletRequestWrapperGetContentLengthTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletRequestWrapperGetContentType()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to return getContentLength() on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequestWrapper/ServletRequestWrapperGetContentTypeTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sreqw/ServletRequestWrapperGetContentTypeTest HTTP/1.0");
    httpAssert.setRequestHeaders(
        "Content-Type:text/plain");
    httpAssert.setTestName("ServletRequestWrapperGetContentTypeTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletRequestWrapperGetInputStream()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to return getInputStream() on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequestWrapper/ServletRequestWrapperGetInputStreamTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sreqw/ServletRequestWrapperGetInputStreamTest HTTP/1.0");
    httpAssert.setTestName("ServletRequestWrapperGetInputStreamTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletRequestWrapperGetInputStream_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to return getInputStream() on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequestWrapper/ServletRequestWrapperGetInputStream_1Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sreqw/ServletRequestWrapperGetInputStream_1Test HTTP/1.0");
    httpAssert.setTestName("ServletRequestWrapperGetInputStream_1Test");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then attempts to get a reader object after one has already been gotten, then the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletRequestWrapperGetLocale()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to return getLocale() on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequestWrapper/ServletRequestWrapperGetLocaleTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sreqw/ServletRequestWrapperGetLocaleTest HTTP/1.0");
    httpAssert.setRequestHeaders(
        "Accept-Language:en-us");
    httpAssert.setTestName("ServletRequestWrapperGetLocaleTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletRequestWrapperGetLocales()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to return getLocales() on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequestWrapper/ServletRequestWrapperGetLocalesTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sreqw/ServletRequestWrapperGetLocalesTest HTTP/1.0");
    httpAssert.setRequestHeaders(
        "Accept-Language:en-US,en-GB");
    httpAssert.setTestName("ServletRequestWrapperGetLocalesTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletRequestWrapperGetParameterMap()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to return getParameterMap() on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequestWrapper/ServletRequestWrapperGetParameterMapTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sreqw/ServletRequestWrapperGetParameterMapTest?BestLanguage=Java&BestJSP=Java2 HTTP/1.0");
    httpAssert.setTestName("ServletRequestWrapperGetParameterMapTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletRequestWrapperGetParameterNames()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to return getParameterNames() on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequestWrapper/ServletRequestWrapperGetParameterNamesTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sreqw/ServletRequestWrapperGetParameterNamesTest?BestLanguage=Java&BestJSP=Java2 HTTP/1.0");
    httpAssert.setTestName("ServletRequestWrapperGetParameterNamesTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletRequestWrapperGetParameterNames_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to return getParameterNames() on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequestWrapper/ServletRequestWrapperGetParameterNames_1Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sreqw/ServletRequestWrapperGetParameterNames_1Test HTTP/1.0");
    httpAssert.setTestName("ServletRequestWrapperGetParameterNames_1Test");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests that no paramters are returned if none are set and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletRequestWrapperGetParameter()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to return getParameter(String) on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequestWrapper/ServletRequestWrapperGetParameterTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sreqw/ServletRequestWrapperGetParameterTest?BestLanguage=Java HTTP/1.0");
    httpAssert.setTestName("ServletRequestWrapperGetParameterTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletRequestWrapperGetParameterValues()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to return getParameterValues(String) on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequestWrapper/ServletRequestWrapperGetParameterValuesTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sreqw/ServletRequestWrapperGetParameterValuesTest?Containers=JSP&Containers=Servlet HTTP/1.0");
    httpAssert.setTestName("ServletRequestWrapperGetParameterValuesTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletRequestWrapperGetParameterValues_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to return getParameterValues(String) on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequestWrapper/ServletRequestWrapperGetParameterValues_1Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sreqw/ServletRequestWrapperGetParameterValues_1Test HTTP/1.0");
    httpAssert.setTestName("ServletRequestWrapperGetParameterValues_1Test");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests that a null is returned for a non existing item and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletRequestWrapperGetParameter_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to return getParameter(String) on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequestWrapper/ServletRequestWrapperGetParameter_1Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sreqw/ServletRequestWrapperGetParameter_1Test HTTP/1.0");
    httpAssert.setTestName("ServletRequestWrapperGetParameter_1Test");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests that null is returned for a non-existing parameter and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletRequestWrapperGetProtocol()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to return getProtocol() on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequestWrapper/ServletRequestWrapperGetProtocolTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sreqw/ServletRequestWrapperGetProtocolTest HTTP/1.0");
    httpAssert.setTestName("ServletRequestWrapperGetProtocolTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletRequestWrapperGetReader()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to return getReader() on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequestWrapper/ServletRequestWrapperGetReaderTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sreqw/ServletRequestWrapperGetReaderTest HTTP/1.0");
    httpAssert.setTestName("ServletRequestWrapperGetReaderTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletRequestWrapperGetReader_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to return getReader() on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequestWrapper/ServletRequestWrapperGetReader_1Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sreqw/ServletRequestWrapperGetReader_1Test HTTP/1.0");
    httpAssert.setTestName("ServletRequestWrapperGetReader_1Test");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests that an exception is thrown when an attempt to get a reader after one has altready been gotten and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletRequestWrapperGetRemoteAddr()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to return getRemoteAddr() on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequestWrapper/ServletRequestWrapperGetRemoteAddrTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sreqw/ServletRequestWrapperGetRemoteAddrTest?Address=|client.ip| HTTP/1.0");
    httpAssert.setTestName("ServletRequestWrapperGetRemoteAddrTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletRequestWrapperGetRemoteHost()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to return getRemoteHost() on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequestWrapper/ServletRequestWrapperGetRemoteHostTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sreqw/ServletRequestWrapperGetRemoteHostTest?Address=|client.host|&Address=|client.ip| HTTP/1.0");
    httpAssert.setTestName("ServletRequestWrapperGetRemoteHostTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletRequestWrapperGetRequestDispatcher()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to return getRequestDispatcher() on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequestWrapper/ServletRequestWrapperGetRequestDispatcherTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sreqw/ServletRequestWrapperGetRequestDispatcherTest HTTP/1.0");
    httpAssert.setTestName("ServletRequestWrapperGetRequestDispatcherTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletRequestWrapperGetScheme()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to return getScheme() on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequestWrapper/ServletRequestWrapperGetSchemeTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sreqw/ServletRequestWrapperGetSchemeTest HTTP/1.0");
    httpAssert.setTestName("ServletRequestWrapperGetSchemeTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletRequestWrapperGetServerName()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to return getServerName() on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequestWrapper/ServletRequestWrapperGetServerNameTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sreqw/ServletRequestWrapperGetServerNameTest?hostname=${host} HTTP/1.0");
    httpAssert.setTestName("ServletRequestWrapperGetServerNameTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletRequestWrapperGetServerPort()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to return getServerPort() on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequestWrapper/ServletRequestWrapperGetServerPortTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sreqw/ServletRequestWrapperGetServerPortTest?port=${port} HTTP/1.0");
    httpAssert.setTestName("ServletRequestWrapperGetServerPortTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletRequestWrapperIsSecure()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to return isSecure() on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequestWrapper/ServletRequestWrapperIsSecureTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sreqw/ServletRequestWrapperIsSecureTest HTTP/1.0");
    httpAssert.setTestName("ServletRequestWrapperIsSecureTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletRequestWrapperRemoveAttribute()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to return removeAttribute() on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequestWrapper/ServletRequestWrapperRemoveAttributeTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sreqw/ServletRequestWrapperRemoveAttributeTest HTTP/1.0");
    httpAssert.setTestName("ServletRequestWrapperRemoveAttributeTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletRequestWrapperSetAttribute()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to return setAttribute() on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletRequestWrapper/ServletRequestWrapperSetAttributeTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sreqw/ServletRequestWrapperSetAttributeTest HTTP/1.0");
    httpAssert.setTestName("ServletRequestWrapperSetAttributeTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletResponseWrapperFlushBuffer()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to call flushBuffer() on the wrapped response object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletResponseWrapper/ServletResponseWrapperFlushBufferTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sresw/ServletResponseWrapperFlushBufferTest HTTP/1.0");
    httpAssert.setTestName("ServletResponseWrapperFlushBufferTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's response has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletResponseWrapperGetBufferSize()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to return getBufferSize() on the wrapped response object , specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletResponseWrapper/ServletResponseWrapperGetBufferSizeTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sresw/ServletResponseWrapperGetBufferSizeTest HTTP/1.0");
    httpAssert.setTestName("ServletResponseWrapperGetBufferSizeTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's response has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletResponseWrapperGetCharacterEncoding()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to return getCharacterEncoding() on the wrapped response object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletResponseWrapper/ServletResponseWrapperGetCharacterEncodingTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sresw/ServletResponseWrapperGetCharacterEncodingTest HTTP/1.0");
    httpAssert.setTestName("ServletResponseWrapperGetCharacterEncodingTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's response has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletResponseWrapperGetOutputStream_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to return getOutputStream() on the wrapped response object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletResponseWrapper/ServletResponseWrapperGetOutputStream_1Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sresw/ServletResponseWrapperGetOutputStream_1Test HTTP/1.0");
    httpAssert.setTestName("ServletResponseWrapperGetOutputStream_1Test");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's response has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletResponseWrapperGetWriter_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to return getWriter() on the wrapped response object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletResponseWrapper/ServletResponseWrapperGetWriter_1Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sresw/ServletResponseWrapperGetWriter_1Test HTTP/1.0");
    httpAssert.setTestName("ServletResponseWrapperGetWriter_1Test");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's response has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletResponseWrapperIsCommitted()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to return isCommitted() on the wrapped response object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletResponseWrapper/ServletResponseWrapperIsCommittedTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sresw/ServletResponseWrapperIsCommittedTest HTTP/1.0");
    httpAssert.setTestName("ServletResponseWrapperIsCommittedTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's response has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletResponseWrapperResetBuffer()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to call resetBuffer() on the wrapped response object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setExpectHeaders("Content-Type:text/html");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletResponseWrapper/ServletResponseWrapperResetBufferTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sresw/ServletResponseWrapperResetBufferTest HTTP/1.0");
    httpAssert.setTestName("ServletResponseWrapperResetBufferTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's response has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletResponseWrapperReset()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to call reset() on the wrapped response object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletResponseWrapper/ServletResponseWrapperResetTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sresw/ServletResponseWrapperResetTest HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("ServletResponseWrapperResetTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's response has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletResponseWrapperReset_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "When the response has been committed, this method throws an IllegalStateException, specified in the Java Servlet Pages Specification v2.3, Sec 14.");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletResponseWrapper/ServletResponseWrapperReset_1Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sresw/ServletResponseWrapperReset_1Test HTTP/1.0");
    httpAssert.setTestName("ServletResponseWrapperReset_1Test");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's response has been wrapped. The servlet attempts to reset the buffer after it has already been flushed. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletResponseWrapperSetBufferSize()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to call setBufferSize(int size) on the wrapped response object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletResponseWrapper/ServletResponseWrapperSetBufferSizeTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sresw/ServletResponseWrapperSetBufferSizeTest HTTP/1.0");
    httpAssert.setTestName("ServletResponseWrapperSetBufferSizeTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's response has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletResponseWrapperSetBufferSize_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to call setBufferSize(int size) on the wrapped response object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletResponseWrapper/ServletResponseWrapperSetBufferSize_1Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sresw/ServletResponseWrapperSetBufferSize_1Test HTTP/1.0");
    httpAssert.setTestName("ServletResponseWrapperSetBufferSize_1Test");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's response has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletResponseWrapperSetContentLength()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to call setContentLength(int len) on the wrapped response object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setExpectHeaders("Content-Length:106");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletResponseWrapper/ServletResponseWrapperSetContentLengthTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sresw/ServletResponseWrapperSetContentLengthTest HTTP/1.0");
    httpAssert.setTestName("ServletResponseWrapperSetContentLengthTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's response has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletResponseWrapperSetContentType()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to call setContentType(String type) on the wrapped response object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setExpectHeaders("Content-Type:text/html");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletResponseWrapper/ServletResponseWrapperSetContentTypeTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sresw/ServletResponseWrapperSetContentTypeTest HTTP/1.0");
    httpAssert.setTestName("ServletResponseWrapperSetContentTypeTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's response has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletResponseWrapperSetLocale()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to call setLocale(Locale loc) on the wrapped response object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setExpectHeaders("Content-Language:en-US");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletResponseWrapper/ServletResponseWrapperSetLocaleTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sresw/ServletResponseWrapperSetLocaleTest HTTP/1.0");
    httpAssert.setTestName("ServletResponseWrapperSetLocaleTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's response has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testServletResponseWrapperGetLocale()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default behavior of this method is to return getLocale() on the wrapped response object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet/ServletResponseWrapper/ServletResponseWrapperGetLocaleTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/sresw/ServletResponseWrapperGetLocaleTest HTTP/1.0");
    httpAssert.setTestName("ServletResponseWrapperGetLocaleTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's response has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet then tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetSecure()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns false if the browser can send cookies using any protocol.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/Cookie/GetSecureTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetSecureTest HTTP/1.0");
    httpAssert.setTestName("GetSecureTest");
    httpAssert.setTestStrategy(
        "A Test for Cookie.getSecure() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetValue()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the value of the cookie.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/Cookie/GetValueTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetValueTest HTTP/1.0");
    httpAssert.setTestName("GetValueTest");
    httpAssert.setTestStrategy(
        "A Test for Cookie.getValue() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetVersion()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the version of the protocol this cookie complies with, 0 if the cookie complies with the original Netscape specification,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/Cookie/GetVersionTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetVersionTest HTTP/1.0");
    httpAssert.setTestName("GetVersionTest");
    httpAssert.setTestStrategy(
        "A Test for Cookie.getVersion() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testSetComment()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Specifies a comment that describes a cookie's purpose., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/Cookie/SetCommentTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/SetCommentTest HTTP/1.0");
    httpAssert.setTestName("SetCommentTest");
    httpAssert.setTestStrategy(
        "A Test for Cookie.setComment() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testSetDomain()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Specifies the domain within which this cookie should be presented.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/Cookie/SetDomainTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/SetDomainTest HTTP/1.0");
    httpAssert.setTestName("SetDomainTest");
    httpAssert.setTestStrategy(
        "A Test for Cookie.setDomain() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testSetMaxAge()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Sets the maximum age of the cookie in seconds.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/Cookie/SetMaxAgeTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/SetMaxAgeTest HTTP/1.0");
    httpAssert.setTestName("SetMaxAgeTest");
    httpAssert.setTestStrategy(
        "A Test for Cookie.setMaxAge() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testSetPath()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Specifies a path for the cookie to which the client should return the cookie.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/Cookie/SetPathTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/SetPathTest HTTP/1.0");
    httpAssert.setTestName("SetPathTest");
    httpAssert.setTestStrategy(
        "A Test for Cookie.setPath() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testSetSecure()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Indicates to the browser whether the cookie should only be sent using a secure protocol, such as HTTPS or SSL., the default value is false specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/Cookie/SetSecureTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/SetSecureTest HTTP/1.0");
    httpAssert.setTestName("SetSecureTest");
    httpAssert.setTestStrategy(
        "A Test for Cookie.setSecureTest() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testSetValue()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Assigns a new value to a cookie after the cookie is created. specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/Cookie/SetValueTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/SetValueTest HTTP/1.0");
    httpAssert.setTestName("SetValueTest");
    httpAssert.setTestStrategy(
        "A Test for Cookie.setValueTest() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testSetVersion()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Sets the version of the cookie protocol this cookie complies with, 0 if the cookie should comply with the original Netscape specification;,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/Cookie/SetVersionTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/SetVersionTest HTTP/1.0");
    httpAssert.setTestName("SetVersionTest");
    httpAssert.setTestStrategy(
        "A Test for Cookie.setVersion() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testAddCookie()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Adds the specified cookie to the response.,specified in the Java Servl et Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setExpectHeaders("Set-Cookie:BestLanguage=Java");


    httpAssert.setRequest(
        "GET /servlet-tests/AddCookieTest HTTP/1.0");
    httpAssert.setTestName("AddCookieTest");
    httpAssert.setTestStrategy(
        "A test for HttpServletResponse.addCookie() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testAddDateHeader()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Adds a response header with the given name and date-value., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setExpectHeaders("DateInfo:Sat, 25 Apr 1970 07:29:03 GMT");


    httpAssert.setRequest(
        "GET /servlet-tests/AddDateHeaderTest HTTP/1.0");
    httpAssert.setTestName("AddDateHeaderTest");
    httpAssert.setTestStrategy(
        "A test for HttpServletResponse.addDateHeader() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testAddHeader()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Adds a response header with the given name and value., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setExpectHeaders(
        "MyStrHeader:Java|MyStrHeader:Java2|MyStrHeader2:Java3");


    httpAssert.setRequest(
        "GET /servlet-tests/AddHeaderTest HTTP/1.0");
    httpAssert.setTestName("AddHeaderTest");
    httpAssert.setTestStrategy(
        "A test for HttpServletResponse.addHeader() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testAddIntHeader()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Adds a response header with the given name and integer value.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setExpectHeaders("MyIntHeader2:4|MyIntHeader:2|MyIntHeader:3");


    httpAssert.setRequest(
        "GET /servlet-tests/AddIntHeaderTest HTTP/1.0");
    httpAssert.setTestName("AddIntHeaderTest");
    httpAssert.setTestStrategy(
        "A test for HttpServletResponse.addIntHeader() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testContainsHeader()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns true if the named response header has already been set.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setExpectHeaders("MyIntHeader:20");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletResponse/ContainsHeaderTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ContainsHeaderTest HTTP/1.0");
    httpAssert.setTestName("ContainsHeaderTest");
    httpAssert.setTestStrategy(
        "A test for HttpServletResponse.containsHeader() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testContainsHeader_01()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns false if the named response header has not already been set.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletResponse/ContainsHeader_01Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ContainsHeader_01Test HTTP/1.0");
    httpAssert.setTestName("ContainsHeader_01Test");
    httpAssert.setTestStrategy(
        "A negative test for HttpServletResponse.containsHeader() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testSendError()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Sends an error response to the client using the specified status code,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");


    httpAssert.setRequest(
        "GET /servlet-tests/SendErrorTest HTTP/1.0");
    httpAssert.setReturnCode("410");
    httpAssert.setTestName("SendErrorTest");
    httpAssert.setTestStrategy(
        "A test for HttpServletResponse.sendError(int sc) method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }


  @Test
  public void testWithLeadingSlash()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "A 2.2 web application deployment descriptor who's url mapping begins with a '/' can be deployed in a 2.3 environment, specified in the Java Servlet Pages Specification v2.3, Sec 11");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/compat/WithLeadingSlashTest.html");


    httpAssert.setRequest(
        "GET /servlet-compat/WithLeadingSlashTest HTTP/1.0");
    httpAssert.setTestName("WithLeadingSlashTest");
    httpAssert.setTestStrategy(
        "The DD url-pattern has a '/' at the beginning of the string. The web app should deploy and be able to be called by a client.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testWithoutLeadingSlash()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "A 2.2 web application deployment descriptor who's url mapping does not begin with a '/' can be deployed in a 2.3 environment, specified in the Java Servlet Pages Specification v2.3, Sec 11");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/compat/WithoutLeadingSlashTest.html");


    httpAssert.setRequest(
        "GET /servlet-compat/WithoutLeadingSlashTest HTTP/1.0");
    httpAssert.setTestName("WithoutLeadingSlashTest");
    httpAssert.setTestStrategy(
        "The DD url-pattern that does not have a '/' at the beginning of the string. The web app should deploy and be able to be called by a client.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }


  @Test
  public void testHttpServletGetServletContext()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns this servlet's ServletContext object, specified in the Java Servlet Pages Specification v2.3, Sec 16 - javax.servlet.GenericServlet.ServletContext() method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServlet/HttpServletGetServletContextTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/HttpServletGetServletContextTest HTTP/1.0");
    httpAssert.setTestName("HttpServletGetServletContextTest");
    httpAssert.setTestStrategy(
        "Create a servlet and check for its ServletContext object existence");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletGetServletInfo()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns this servlet's ServletInfo object, specified in the Java Servlet Pages Specification v2.3, Sec 16 - javax.servlet.GenericServlet.ServletContext() method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServlet/HttpServletGetServletInfoTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/HttpServletGetServletInfoTest HTTP/1.0");
    httpAssert.setTestName("HttpServletGetServletInfoTest");
    httpAssert.setTestStrategy(
        "Create a servlet and check for its ServletInfo object values");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testClassFile()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "This class which is in the WEB-INF/classes directory is available to the application class loader specified in the Java Servlet Pages Specification v2.3, Chapter 9");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/webapps/dirstruct/ClassFileTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/ClassFileTest HTTP/1.0");
    httpAssert.setTestName("ClassFileTest");
    httpAssert.setTestStrategy(
        "The serlvet which is in the WEB-INF/classes directory is called by the client and should execute.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }
  @Test
  public void testHttpServletDoServletConfig()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns a ServletConfig object, which contains initialization and startup parameters for this servlet., specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.Servlet.getServletConfig() method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServlet/HttpServletDoServletConfigTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/HttpServletDoServletConfigTest HTTP/1.0");
    httpAssert.setTestName("HttpServletDoServletConfigTest");
    httpAssert.setTestStrategy(
        "Create a servlet and test for the getServletConfig() method to be a non-null value and an initial paramter can be retrieved");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletDoServletInfo()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns information about the servlet, such as author, version, and copyright., specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.Servlet.getServletInfo() method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServlet/HttpServletDoServletInfoTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/HttpServletDoServletInfoTest HTTP/1.0");
    httpAssert.setTestName("HttpServletDoServletInfoTest");
    httpAssert.setTestStrategy(
        "Create a servlet and test that information is returned");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletDoService()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Called by the servlet container to allow the servlet to respond to a request, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.Servlet.service() method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServlet/HttpServletDoServiceTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/HttpServletDoServiceTest HTTP/1.0");
    httpAssert.setTestName("HttpServletDoServiceTest");
    httpAssert.setTestStrategy(
        "Inside HttpServletCoreServletTest, which is the parent servlet, we will override init method and assign some value to the String. We'll check for that value in the service method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletPU()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Servlet lifecycle test, check if UnavailableException.isPermanent() is true, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServlet/HttpServletPUTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/HttpServletPUTest HTTP/1.0");
    httpAssert.setTestName("HttpServletPUTest");
    httpAssert.setTestStrategy(
        "Create a servlet, throw UnavailableException and test if isPermanent() method is true");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletDestroy()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Called by the servlet container to indicate to a servlet that the servlet is being taken out of service., specified in the Java Servlet Pages Specification v2.3, Sec 16 - javax.servlet.GenericServlet.Destroy() method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");


    httpAssert.setRequest(
        "GET /servlet-tests/HttpServletDestroyTest HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("HttpServletDestroyTest");
    httpAssert.setTestStrategy(
        "Create a servlet and take out of service using destroy method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletGetServletConfig()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns this servlet's ServletConfig object, specified in the Java Servlet Pages Specification v2.3, Sec 16 - javax.servlet.GenericServlet.ServletConfig() method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServlet/HttpServletGetServletConfigTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/HttpServletGetServletConfigTest HTTP/1.0");
    httpAssert.setTestName("HttpServletGetServletConfigTest");
    httpAssert.setTestStrategy(
        "Create a servlet and check for its ServletConfig object existence");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletResponseWrapperSetDateHeader()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for default behavior of this method is to call setDateHeader(String, long) on the wrapped response object, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.HttpServletResponseWrapper. setDateHeader(String name, long date) method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setExpectHeaders("GenericResponseWrapper:setDateHeader|DateInfo:Sat, 25 Apr 1970 07:29:03 GMT");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletResponseWrapper/HttpServletResponseWrapperSetDateHeaderTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/hsresw/HttpServletResponseWrapperSetDateHeaderTest HTTP/1.0");
    httpAssert.setTestName("HttpServletResponseWrapperSetDateHeaderTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's response has been wrapped. The wrapper object adds a specific header to the response object and calls the responses setDateHeader method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletResponseWrapperSetHeader()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for default behavior of this method is to return setHeader(String, String) on the wrapped response object, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.HttpServletResponseWrapper. setHeader(String name, String value) method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setExpectHeaders("MyStrHeader:Java|GenericResponseWrapper:addHeader|GenericResponseWrapper:setHeader");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletResponseWrapper/HttpServletResponseWrapperSetHeaderTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/hsresw/HttpServletResponseWrapperSetHeaderTest HTTP/1.0");
    httpAssert.setTestName("HttpServletResponseWrapperSetHeaderTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's response has been wrapped. The wrapper object adds a specific header to the response object and calls the responses setHeader method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletResponseWrapperSetIntHeader()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for default behavior of this method is to call setIntHeader(String, int) on the wrapped response object, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.HttpServletResponseWrapper. setIntHeader(String name, int value) method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setExpectHeaders("MyIntHeader:2|GenericResponseWrapper:addIntHeader|GenericResponseWrapper:setIntHeader");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletResponseWrapper/HttpServletResponseWrapperSetIntHeaderTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/hsresw/HttpServletResponseWrapperSetIntHeaderTest HTTP/1.0");
    httpAssert.setTestName("HttpServletResponseWrapperSetIntHeaderTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's response has been wrapped. The wrapper object adds a specific header to the response object and calls the responses setIntHeader method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  /**
   *
   * Test for default behavior of this method is to call setStatus(int, String) on the wrapped
   * response object, specified in the Java Servlet Pages Specification v2.3, Sec 14
   * - javax.servlet.HttpServletResponseWrapper. setStatus(int sc, String msg) method");
   *
   * Uses tests.javax_servlet_http.HttpServletResponseWrapper.HttpServletResponseWrapperSetStatusMsgTestServlet on the server
   * 
   * @throws Exception if anything goes wrong
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

  @Test
  public void testHttpServletResponseWrapperSetStatus()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for default behavior of this method is to call setStatus(int) on the wrapped response object, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.HttpServletResponseWrapper.setStatus(int sc) method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setExpectHeaders("GenericResponseWrapper:setStatus");


    httpAssert.setRequest(
        "GET /servlet-tests/hsresw/HttpServletResponseWrapperSetStatusTest HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("HttpServletResponseWrapperSetStatusTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's response has been wrapped. The wrapper object adds a specific header to the response object and calls the responses setStatus method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletDoDestroyed()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Called by the servlet container to indicate to a servlet that the servlet is being taken out of service, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.Servlet.destroy() method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServlet/HttpServletDoDestroyedTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/HttpServletDoDestroyedTest HTTP/1.0");
    httpAssert.setTestName("HttpServletDoDestroyedTest");
    httpAssert.setTestStrategy(
        "Testing that destroy method is not called during service method execution");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletDoInit1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Called by the servlet container to indicate to a servlet that the servlet is being placed into service., specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.Servlet.init() method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");


    httpAssert.setRequest(
        "GET /servlet-tests/HttpServletDoInit1Test HTTP/1.0");
    // we expect a 404 indicating a resource is "permanently" unavailable
    // (for "temporary" unavailability, we would expect a 503
    httpAssert.setReturnCode("404");
    httpAssert.setTestName("HttpServletDoInit1Test");
    httpAssert.setTestStrategy(
        "A negative test for the init method. We will throw UnavailableException from inside init.The Servlet should not be initialized");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletDoInit2()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Called by the servlet container to indicate to a servlet that the servlet is being placed into service., specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.Servlet.init() method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServlet/HttpServletDoInit2Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/HttpServletDoInit2Test HTTP/1.0");
    httpAssert.setTestName("HttpServletDoInit2Test");
    httpAssert.setTestStrategy(
        "Inside CoreServletTest, which is the parent servlet, we are implementing init() and setting a boolean variable to true. We'll check for the variables here in the HttpServletDoInit2Test");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletResponseWrapperEncodeURL()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for default behavior of this method is to call encodeURL(String url) on the wrapped response object, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.HttpServletResponseWrapper.encodeURL() method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setExpectHeaders("GenericResponseWrapper:encodeURL");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletResponseWrapper/HttpServletResponseWrapperEncodeURLTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/hsresw/HttpServletResponseWrapperEncodeURLTest HTTP/1.0");
    httpAssert.setTestName("HttpServletResponseWrapperEncodeURLTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's response has been wrapped. The wrapper object adds a specific header to the response object and calls the responses encodeURL method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletResponseWrapperEncodeRedirectURL()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for default behavior of this method is to return encodeRedirectURL(String url) on the wrapped response object, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.HttpServletResponseWrapper. encodeRedirectURL(String) method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setExpectHeaders("GenericResponseWrapper:encodeRedirectURL");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletResponseWrapper/HttpServletResponseWrapperEncodeRedirectURLTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/hsresw/HttpServletResponseWrapperEncodeRedirectURLTest HTTP/1.0");
    httpAssert.setTestName("HttpServletResponseWrapperEncodeRedirectURLTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's response has been wrapped. The wrapper object adds a specific header to the response object and calls the responses encodeRedirectURL method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletResponseWrapperSendError()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for default behavior of this method is to call sendError(int sc) on the wrapped response object, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.HttpServletResponseWrapper.sendError(int) method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setExpectHeaders("GenericResponseWrapper:sendError");


    httpAssert.setRequest(
        "GET /servlet-tests/hsresw/HttpServletResponseWrapperSendErrorTest HTTP/1.0");
    httpAssert.setReturnCode("410");
    httpAssert.setTestName("HttpServletResponseWrapperSendErrorTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's response has been wrapped. The wrapper object adds a specific header to the response object and calls the responses sendError method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletResponseWrapperAddHeader()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for default behavior of this method is to return addHeader(String name, String value) on the wrapped response object, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.HttpServletResponseWrapper. addHeader() method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setExpectHeaders("MyStrHeader:Java|MyStrHeader:Java2|GenericResponseWrapper:addHeader|GenericResponseWrapper:addHeader|GenericResponseWrapper:addHeader|MyStrHeader2:Java3");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletResponseWrapper/HttpServletResponseWrapperAddHeaderTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/hsresw/HttpServletResponseWrapperAddHeaderTest HTTP/1.0");
    httpAssert.setTestName("HttpServletResponseWrapperAddHeaderTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's response has been wrapped. The wrapper object adds a specific header to the response object and calls the responses addHeader method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletResponseWrapperAddIntHeader()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for default behavior of this method is to call addIntHeader(String name, int value) on the wrapped response object, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.HttpServletResponseWrapper. addIntHeader(String,Int) method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setExpectHeaders(
        "MyIntHeader:2|GenericResponseWrapper:addIntHeader");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletResponseWrapper/HttpServletResponseWrapperAddIntHeaderTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/hsresw/HttpServletResponseWrapperAddIntHeaderTest HTTP/1.0");
    httpAssert.setTestName("HttpServletResponseWrapperAddIntHeaderTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's response has been wrapped. The wrapper object adds a specific header to the response object and calls the responses addIntHeader method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletResponseWrapperContainsHeader()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for default behavior of this method is to call containsHeader(String name) on the wrapped response object, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.HttpServletResponseWrapper. containsHeader(String) method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setExpectHeaders("MyStrHeader:HttpServletResponseWrapperContainsHeaderTest|GenericResponseWrapper:setHeader|GenericResponseWrapper:containsHeader");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletResponseWrapper/HttpServletResponseWrapperContainsHeaderTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/hsresw/HttpServletResponseWrapperContainsHeaderTest HTTP/1.0");
    httpAssert.setTestName("HttpServletResponseWrapperContainsHeaderTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's response has been wrapped. The wrapper object adds a specific header to the response object and calls the responses containsHeader method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletResponseWrapperAddDateHeader()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for default behavior of this method is to call addDateHeader(String long) on the wrapped response object, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.HttpServletResponseWrapper.addDateHeader() method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setExpectHeaders("MyDateHeader:Mon, 12 Jan 1970 10:20:54 GMT|GenericResponseWrapper:addDateHeader");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletResponseWrapper/HttpServletResponseWrapperAddDateHeaderTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/hsresw/HttpServletResponseWrapperAddDateHeaderTest HTTP/1.0");
    httpAssert.setTestName("HttpServletResponseWrapperAddDateHeaderTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's response has been wrapped. The wrapper object adds a specific header to the response object and calls the responses addDateHeader method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletResponseWrapperAddCookie()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for default behavior of this method is to call addCookie(Cookie cookie) on the wrapped response object, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.HttpServletResponseWrapper.addCookie() method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setExpectHeaders("GenericResponseWrapper:addCookie|Set-Cookie:BestLanguage=Java");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletResponseWrapper/HttpServletResponseWrapperAddCookieTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/hsresw/HttpServletResponseWrapperAddCookieTest HTTP/1.0");
    httpAssert.setTestName("HttpServletResponseWrapperAddCookieTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's response has been wrapped. The wrapper object adds a specific header to the response object and calls the responses addCookie method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletResponseWrapperConstructor()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Construct a request object wrapping the given request, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.HttpServletResponseWrapper");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletResponseWrapper/HttpServletResponseWrapperConstructorTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/hsresw/HttpServletResponseWrapperConstructorTest HTTP/1.0");
    httpAssert.setTestName("HttpServletResponseWrapperConstructorTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who constructs a Wrapper object from the response object.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletRequestWrapperIsRequestedSessionIdValid_01()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for a false return from this method on the wrapped request object, specified in the Java Servlet Pages Specification v2.3 Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequestWrapper/HttpServletRequestWrapperIsRequestedSessionIdValid_01Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/hsreqw/HttpServletRequestWrapperIsRequestedSessionIdValid_01Test HTTP/1.0");
    httpAssert.setTestName(
        "HttpServletRequestWrapperIsRequestedSessionIdValid_01Test");
    httpAssert.setTestStrategy(
        "A test for HttpServletRequestWrapper.isRequestedSessionIdValid() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletRequestWrapperGetSession()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for default behavior of this method to return getSession() on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequestWrapper/HttpServletRequestWrapperGetSessionTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/hsreqw/HttpServletRequestWrapperGetSessionTest HTTP/1.0");
    httpAssert.setTestName("HttpServletRequestWrapperGetSessionTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet the tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletRequestWrapperGetSessionBoolean()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for default behavior of this method to return getSession(boolean) on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequestWrapper/HttpServletRequestWrapperGetSessionBooleanTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/hsreqw/HttpServletRequestWrapperGetSessionBooleanTest HTTP/1.0");
    httpAssert.setTestName("HttpServletRequestWrapperGetSessionBooleanTest");
    httpAssert.setTestStrategy(
        "A test for HttpServletRequestWrapper.getSession(boolean) method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletRequestWrapperIsRequestedSessionIdFromCookie_01()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for a false return from this method on the wrapped request object, specified in the Java Servlet Pages Specification v2.3 Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequestWrapper/HttpServletRequestWrapperIsRequestedSessionIdFromCookie_01Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/hsreqw/HttpServletRequestWrapperIsRequestedSessionIdFromCookie_01Test HTTP/1.0");
    httpAssert.setTestName("HttpServletRequestWrapperIsRequestedSessionIdFromCookie_01Test");
    httpAssert.setTestStrategy(
        "A test for HttpServletRequestWrapper.isRequestedSessionIdFromCookie() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }


  /**
   * Uses tests.javax_servlet_http.HttpServletRequestWrapper.HttpServletRequestWrapperIsRequestedSessionIdFromURL_01TestServlet
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testHttpServletRequestWrapperIsRequestedSessionIdFromURL_01()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for a false return from this method on the wrapped request object, specified in the Java Servlet Pages Specification v2.3 Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequestWrapper/HttpServletRequestWrapperIsRequestedSessionIdFromURL_01Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/hsreqw/HttpServletRequestWrapperIsRequestedSessionIdFromURL_01Test HTTP/1.0");
    httpAssert.setTestName(
        "HttpServletRequestWrapperIsRequestedSessionIdFromURL_01Test");
    httpAssert.setTestStrategy(
        "A test for HttpServletRequestWrapper.isRequestedSessionIdFromURL() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletRequestWrapperGetRequestedSessionId()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for default behavior of this method to return getRequestedSessionId() on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequestWrapper/HttpServletRequestWrapperGetRequestedSessionIdTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/hsreqw/HttpServletRequestWrapperGetRequestedSessionIdTest HTTP/1.0");
    httpAssert.setTestName(
        "HttpServletRequestWrapperGetRequestedSessionIdTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet the tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletRequestWrapperGetQueryString()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for default behavior of this method to return getQueryString() on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequestWrapper/HttpServletRequestWrapperGetQueryStringTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/hsreqw/HttpServletRequestWrapperGetQueryStringTest?language=Java HTTP/1.0");
    httpAssert.setTestName("HttpServletRequestWrapperGetQueryStringTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet the tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletRequestWrapperGetRequestURI()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for default behavior of this method to return getRequestURI() on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequestWrapper/HttpServletRequestWrapperGetRequestURITest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/hsreqw/HttpServletRequestWrapperGetRequestURITest HTTP/1.0");
    httpAssert.setTestName("HttpServletRequestWrapperGetRequestURITest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet the tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletRequestWrapperGetServletPath()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for default behavior of this method to return getServletPath() on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequestWrapper/HttpServletRequestWrapperGetServletPathTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/hsreqw/HttpServletRequestWrapperGetServletPathTest HTTP/1.0");
    httpAssert.setTestName("HttpServletRequestWrapperGetServletPathTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet the tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletRequestWrapperGetHeader()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for default behavior of this method to return getHeader(String name) on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequestWrapper/HttpServletRequestWrapperGetHeaderTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/hsreqw/HttpServletRequestWrapperGetHeaderTest HTTP/1.0");
    httpAssert.setRequestHeaders(
        "User-Agent:Mozilla/4.0");
    httpAssert.setTestName("HttpServletRequestWrapperGetHeaderTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet the tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletRequestWrapperGetIntHeader()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for default behavior of this method to return getIntHeader(String name) on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequestWrapper/HttpServletRequestWrapperGetIntHeaderTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/hsreqw/HttpServletRequestWrapperGetIntHeaderTest HTTP/1.0");
    httpAssert.setRequestHeaders(
        "MyIntHeader:123");
    httpAssert.setTestName("HttpServletRequestWrapperGetIntHeaderTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet the tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletRequestWrapperGetMethod()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for default behavior of this method to return getMethod() on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequestWrapper/HttpServletRequestWrapperGetMethodTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/hsreqw/HttpServletRequestWrapperGetMethodTest HTTP/1.0");
    httpAssert.setTestName("HttpServletRequestWrapperGetMethodTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet the tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletRequestWrapperGetCookies()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for default behavior of this method to return getCookies() on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequestWrapper/HttpServletRequestWrapperGetCookiesTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/hsreqw/HttpServletRequestWrapperGetCookiesTest HTTP/1.0");
    httpAssert.setRequestHeaders(
        "Cookie:BestLanguage=Java");
    httpAssert.setTestName("HttpServletRequestWrapperGetCookiesTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet the tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletRequestWrapperGetDateHeader()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for default behavior of this method to return getDateHeader(String name) on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequestWrapper/HttpServletRequestWrapperGetDateHeaderTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/hsreqw/HttpServletRequestWrapperGetDateHeaderTest HTTP/1.0");
    httpAssert.setRequestHeaders(
        "If-Modified-Since:Sat, 01 Jan 2000 00:00:01 GMT");
    httpAssert.setTestName("HttpServletRequestWrapperGetDateHeaderTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet the tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletRequestWrapperGetHeaderNames()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for default behavior of this method to return getHeaders(String name) on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequestWrapper/HttpServletRequestWrapperGetHeaderNamesTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/hsreqw/HttpServletRequestWrapperGetHeaderNamesTest HTTP/1.0");
    httpAssert.setRequestHeaders(
        "Accept-Language:en-us|Accept-Language2:ga-us");
    httpAssert.setTestName("HttpServletRequestWrapperGetHeaderNamesTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet the tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletRequestWrapperConstructor()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "A HttpServletRequestWrapper object should be returned when the request object is passed into the constructor. Java Specification v2.3 Sec 14.");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequestWrapper/HttpServletRequestWrapperConstructorTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/hsreqw/HttpServletRequestWrapperConstructorTest HTTP/1.0");
    httpAssert.setTestName("HttpServletRequestWrapperConstructorTest");
    httpAssert.setTestStrategy(
        "Construct a request object wrapping the given request.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testSetAttribute()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Binds an object to this session, using the name specified. If an object of the same name is already bound to the session, the object is replaced, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpSession/SetAttributeTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/SetAttributeTest HTTP/1.0");
    httpAssert.setTestName("SetAttributeTest");
    httpAssert.setTestStrategy(
        "A test for HttpSession.setAttribute() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testRemoveAttribute()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Removes the object bound with the specified name from this session.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpSession/RemoveAttributeTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/RemoveAttributeTest HTTP/1.0");
    httpAssert.setTestName("RemoveAttributeTest");
    httpAssert.setTestStrategy(
        "A test for HttpSession.removeAttribute() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testIsNew()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns true if the client does not yet know about the session or if the client chooses not to join the session., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpSession/IsNewTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/IsNewTest HTTP/1.0");
    httpAssert.setTestName("IsNewTest");
    httpAssert.setTestStrategy(
        "A test for HttpSession.getIsNew() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpSessionGetAttribute()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the object bound with the specified name in this session, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpSession/HttpSessionGetAttributeTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/HttpSessionGetAttributeTest HTTP/1.0");
    httpAssert.setTestName("HttpSessionGetAttributeTest");
    httpAssert.setTestStrategy(
        "A test for HttpSession.getAttribute() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testSendRedirect_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Throws IllegalStateException if the response was committed ,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletResponse/SendRedirect_1Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/SendRedirect_1Test HTTP/1.0");
    httpAssert.setTestName("SendRedirect_1Test");
    httpAssert.setTestStrategy(
        "A negative test for HttpServletResponse.sendRedirect() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testSetDateHeader()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Sets a response header with the given name and date-value., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setExpectHeaders("DateInfo:Sat, 25 Apr 1970 07:29:03 GMT");


    httpAssert.setRequest(
        "GET /servlet-tests/SetDateHeaderTest HTTP/1.0");
    httpAssert.setTestName("SetDateHeaderTest");
    httpAssert.setTestStrategy(
        "A test for HttpServletResponse.sendDateHeader() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testSetHeader()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Sets a response header with the given name and value., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setExpectHeaders("MyStrHeader:Java");


    httpAssert.setRequest(
        "GET /servlet-tests/SetHeaderTest HTTP/1.0");
    httpAssert.setTestName("SetHeaderTest");
    httpAssert.setTestStrategy(
        "A test for HttpServletResponse.setHeader() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testSetIntHeader()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Sets a response header with the given name and integer value.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setExpectHeaders("MyIntHeader:2");


    httpAssert.setRequest(
        "GET /servlet-tests/SetIntHeaderTest HTTP/1.0");
    httpAssert.setTestName("SetIntHeaderTest");
    httpAssert.setTestStrategy(
        "A test for HttpServletResponse.setIntHeader() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testSetStatus()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Sets the status code for this response.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");


    httpAssert.setRequest(
        "GET /servlet-tests/SetStatusTest HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("SetStatusTest");
    httpAssert.setTestStrategy(
        "A test for HttpServletResponse.setStatus() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetCreationTime()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the time when this session was created, measured in milliseconds since midnight January 1, 1970 GMT., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpSession/GetCreationTimeTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetCreationTimeTest HTTP/1.0");
    httpAssert.setTestName("GetCreationTimeTest");
    httpAssert.setTestStrategy(
        "A test for HttpSession.getCreationTime() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetId()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns a string containing the unique identifier assigned to this session.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpSession/GetIdTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetIdTest HTTP/1.0");
    httpAssert.setTestName("GetIdTest");
    httpAssert.setTestStrategy(
        "A test for HttpSession.getId() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetLastAccessedTime()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the last time the client sent a request associated with this session, as the number of milliseconds since midnight January 1, 1970 GMT.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpSession/GetLastAccessedTimeTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetLastAccessedTimeTest HTTP/1.0");
    httpAssert.setTestName("GetLastAccessedTimeTest");
    httpAssert.setTestStrategy(
        "A test for HttpSession.getLastAccessedTime() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetMaxInactiveInterval()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the maximum time interval, in seconds, that the servlet container will keep this session open between client accesses.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpSession/GetMaxInactiveIntervalTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetMaxInactiveIntervalTest HTTP/1.0");
    httpAssert.setTestName("GetMaxInactiveIntervalTest");
    httpAssert.setTestStrategy(
        "A test for HttpSession.getMaxInactiveInterval() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testSetMaxInactiveInterval()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Specifies the time, in seconds, between client requests before the servlet container will invalidate this session ,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpSession/SetMaxInactiveIntervalTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/SetMaxInactiveIntervalTest HTTP/1.0");
    httpAssert.setTestName("SetMaxInactiveIntervalTest");
    httpAssert.setTestStrategy(
        "A test for HttpSession.setMaxInactiveInterval() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpSessionGetAttributeNames()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns an Enumeration of String objects containing the names of all the objects bound to this session.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpSession/HttpSessionGetAttributeNamesTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/HttpSessionGetAttributeNamesTest HTTP/1.0");
    httpAssert.setTestName("HttpSessionGetAttributeNamesTest");
    httpAssert.setTestStrategy(
        "A test for HttpSession.getAttributeNames() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpSessionGetAttributeNamesEmpty()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns an empty Enumeration since there were no attributes objects bound to this session.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpSession/HttpSessionGetAttributeNamesEmptyTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/HttpSessionGetAttributeNamesEmptyTest HTTP/1.0");
    httpAssert.setTestName("HttpSessionGetAttributeNamesEmptyTest");
    httpAssert.setTestStrategy(
        "A test for HttpSession.getAttributeNames() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetRequestURI()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the part of this request's URL from the protocol name up to the query string in the first line of the HTTP request.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetRequestURITest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetRequestURITest HTTP/1.0");
    httpAssert.setTestName("GetRequestURITest");
    httpAssert.setTestStrategy(
        "A test for HttpServletRequest.getRequestURI() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetRequestURIWithQS()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the part of this request's URL from the protocol name up to the query string in the first line of the HTTP request.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetRequestURIWithQSTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetRequestURIWithQSTest?language=java HTTP/1.0");
    httpAssert.setTestName("GetRequestURIWithQSTest");
    httpAssert.setTestStrategy(
        "A test for HttpServletRequest.getRequestURI() testing with query string.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetRequestedSessionId_01()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns null if the request did not specify a session ID, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetRequestedSessionId_01Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetRequestedSessionId_01Test HTTP/1.0");
    httpAssert.setTestName("GetRequestedSessionId_01Test");
    httpAssert.setTestStrategy(
        "A negative test for HttpServletRequest.getRequestedSessionId() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetServletPath()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the part of this request's URL that calls the servlet.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetServletPathTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetServletPathTest HTTP/1.0");
    httpAssert.setTestName("GetServletPathTest");
    httpAssert.setTestStrategy(
        "A test for HttpServletRequest.getServletPath() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testIsRequestedSessionIdFromCookie_01()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns false if session Id did not come in as a cookie ,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/IsRequestedSessionIdFromCookie_01Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/IsRequestedSessionIdFromCookie_01Test HTTP/1.0");
    httpAssert.setTestName("IsRequestedSessionIdFromCookie_01Test");
    httpAssert.setTestStrategy(
        "A negative test for HttpServletRequest.isRequestedSessionIdFromCookie() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testIsRequestedSessionIdFromURL_01()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns false if session Id did not come in as part of a URL, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/IsRequestedSessionIdFromURL_01Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/IsRequestedSessionIdFromURL_01Test HTTP/1.0");
    httpAssert.setTestName("IsRequestedSessionIdFromURL_01Test");
    httpAssert.setTestStrategy(
        "A test for HttpServletRequest.isRequestedSessionIdFromURL() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testIsRequestedSessionIdValid_01()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns false if this request does not have an id for a valid session in the current session context, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/IsRequestedSessionIdValid_01Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/IsRequestedSessionIdValid_01Test HTTP/1.0");
    httpAssert.setTestName("IsRequestedSessionIdValid_01Test");
    httpAssert.setTestStrategy(
        "A test for HttpServletRequest.isRequestedSessionIdValid() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetHeader()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the value of the specified request header as a String, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetHeaderTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetHeaderTest HTTP/1.0");
    httpAssert.setRequestHeaders(
        "User-Agent:Mozilla/4.0");
    httpAssert.setTestName("GetHeaderTest");
    httpAssert.setTestStrategy(
        "A test for HttpServletRequest.getHeader() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetHeaderLCase()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the value of the specified lower cased request header as a String, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetHeaderLCaseTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetHeaderLCaseTest HTTP/1.0");
    httpAssert.setRequestHeaders(
        "User-Agent:Mozilla/4.0");
    httpAssert.setTestName("GetHeaderLCaseTest");
    httpAssert.setTestStrategy(
        "A test for HttpServletRequest.getHeader() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetHeaderMxCase()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the value of the specified mixed case request header as a String, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetHeaderMxCaseTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetHeaderMxCaseTest HTTP/1.0");
    httpAssert.setRequestHeaders(
        "User-Agent:Mozilla/4.0");
    httpAssert.setTestName("GetHeaderMxCaseTest");
    httpAssert.setTestStrategy(
        "A test for HttpServletRequest.getHeader() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetSession_01()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns null if create is false and the request has no valid HttpSession, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetSession_01Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetSession_01Test HTTP/1.0");
    httpAssert.setTestName("GetSession_01Test");
    httpAssert.setTestStrategy(
        "Tests that getSession(false) returns null");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testCookieClone()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Overrides the standard java.lang.Object.clone() method to return a copy of this cookie.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/Cookie/CookieCloneTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/CookieCloneTest HTTP/1.0");
    httpAssert.setTestName("CookieCloneTest");
    httpAssert.setTestStrategy(
        "A Test for Cookie.clone() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testCookie_Constructor()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Constructs a cookie with a specified name and value., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/Cookie/Cookie_ConstructorTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/Cookie_ConstructorTest HTTP/1.0");
    httpAssert.setTestName("Cookie_ConstructorTest");
    httpAssert.setTestStrategy(
        "A Test for Cookie(String name,String value) constructor");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testCookie_Constructor_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Throws IllegalArgumentException if the cookie name contains illegal characters (for example, a comma, space, or semicolon) or it is one of the tokens reserved for use by the cookie protocol, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/Cookie/Cookie_Constructor_1Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/Cookie_Constructor_1Test HTTP/1.0");
    httpAssert.setTestName("Cookie_Constructor_1Test");
    httpAssert.setTestStrategy(
        "A negative Test for Cookie(String name,String value) constructor. We include some invalid chars in the Cookie name and test for IllegalArgumentException");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetComment()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the comment describing the purpose of this cookie., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/Cookie/GetCommentTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetCommentTest HTTP/1.0");
    httpAssert.setTestName("GetCommentTest");
    httpAssert.setTestStrategy(
        "A Test for Cookie.getComment() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetComment_01()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns null if the cookie has no comment.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/Cookie/GetComment_01Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetComment_01Test HTTP/1.0");
    httpAssert.setTestName("GetComment_01Test");
    httpAssert.setTestStrategy(
        "A Test for Cookie.getComment() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetDomain()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the domain name set for this cookie.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/Cookie/GetDomainTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetDomainTest HTTP/1.0");
    httpAssert.setTestName("GetDomainTest");
    httpAssert.setTestStrategy(
        "A Test for Cookie.getDomain() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetMaxAge()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the maximum age of the cookie, specified in seconds ,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/Cookie/GetMaxAgeTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetMaxAgeTest HTTP/1.0");
    httpAssert.setTestName("GetMaxAgeTest");
    httpAssert.setTestStrategy(
        "A Test for Cookie.getMaxAge() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetMaxAge_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns by default, -1 indicating the cookie will persist until browser shutdown.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/Cookie/GetMaxAge_1Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetMaxAge_1Test HTTP/1.0");
    httpAssert.setTestName("GetMaxAge_1Test");
    httpAssert.setTestStrategy(
        "A negative test for Cookie.getMaxAge() method. We will try to get Cookies default maxAge which is '-1'");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetName()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the name of the cookie.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/Cookie/GetNameTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetNameTest HTTP/1.0");
    httpAssert.setTestName("GetNameTest");
    httpAssert.setTestStrategy(
        "A Test for Cookie.getName() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetPath()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the path on the server to which the browser returns this cookie.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/Cookie/GetPathTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetPathTest HTTP/1.0");
    httpAssert.setTestName("GetPathTest");
    httpAssert.setTestStrategy(
        "A Test for Cookie.getPath() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetPathTranslated_01()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns null if the URL does not have any extra path information.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetPathTranslated_01Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetPathTranslated_01Test HTTP/1.0");
    httpAssert.setTestName("GetPathTranslated_01Test");
    httpAssert.setTestStrategy(
        "A negative test for HttpServletRequest.getPathTranslated() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetQueryString()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the query string that is contained in the request URL after the path, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetQueryStringTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetQueryStringTest?language=Java HTTP/1.0");
    httpAssert.setTestName("GetQueryStringTest");
    httpAssert.setTestStrategy(
        "A test for HttpServletRequest.getQueryString() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetQueryString_01()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns null if the URL contains no query string, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetQueryString_01Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetQueryString_01Test HTTP/1.0");
    httpAssert.setTestName("GetQueryString_01Test");
    httpAssert.setTestStrategy(
        "A negative test for HttpServletRequest.getQueryString() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetPathInfo_01()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns null if there was no extra path information sent with this request.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetPathInfo_01Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetPathInfo_01Test HTTP/1.0");
    httpAssert.setTestName("GetPathInfo_01Test");
    httpAssert.setTestStrategy(
        "A negative test for HttpServletRequest.getPathInfo() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetPathTranslated()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns any extra path information after the servlet name but before the query string, and translates it to a real path.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetPathTranslatedTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetPathTranslatedTest/javax_servlet HTTP/1.0");
    httpAssert.setTestName("GetPathTranslatedTest");
    httpAssert.setTestStrategy(
        "A test for HttpServletRequest.getPathTranslated() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetIntHeader()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the value of the specified request header as an integer.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetIntHeaderTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetIntHeaderTest HTTP/1.0");
    httpAssert.setRequestHeaders(
        "MyIntHeader:123");
    httpAssert.setTestName("GetIntHeaderTest");
    httpAssert.setTestStrategy(
        "A test for HttpServletRequest.getIntHeader() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetIntHeaderLCase()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the value of the specified lower cased request header as an integer.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetIntHeaderLCaseTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetIntHeaderLCaseTest HTTP/1.0");
    httpAssert.setRequestHeaders(
        "MyIntHeader:123");
    httpAssert.setTestName("GetIntHeaderLCaseTest");
    httpAssert.setTestStrategy(
        "A test for HttpServletRequest.getIntHeader() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetIntHeaderMxCase()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the value of the specified mixed case request header as an integer.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetIntHeaderMxCaseTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetIntHeaderMxCaseTest HTTP/1.0");
    httpAssert.setRequestHeaders(
        "MyIntHeader:123");
    httpAssert.setTestName("GetIntHeaderMxCaseTest");
    httpAssert.setTestStrategy(
        "A test for HttpServletRequest.getIntHeader() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetIntHeader_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Throws NumberFormatException, if the header value can't be converted to an int,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetIntHeader_1Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetIntHeader_1Test HTTP/1.0");
    httpAssert.setRequestHeaders(
        "MyNonIntHeader:Java");
    httpAssert.setTestName("GetIntHeader_1Test");
    httpAssert.setTestStrategy(
        "A negative test for HttpServletRequest.getIntHeader() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetIntHeader_2()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns -1 if the request doesn't have a header of this name ,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetIntHeader_2Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetIntHeader_2Test HTTP/1.0");
    httpAssert.setTestName("GetIntHeader_2Test");
    httpAssert.setTestStrategy(
        "A negative test for HttpServletRequest.getIntHeader() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetMethod()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the name of the HTTP method with which this request was made, for example, GET, POST, or PUT.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetMethodTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetMethodTest HTTP/1.0");
    httpAssert.setTestName("GetMethodTest");
    httpAssert.setTestStrategy(
        "A test for HttpServletRequest.getMethod() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetMethod_HEAD()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the name of the HTTP method with which this request was made, for example, GET, POST, or PUT.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setExpectHeaders("status:GetMethod_HEADTest PASSED");
    httpAssert.setExpectResponseBody("false");


    httpAssert.setRequest(
        "HEAD /servlet-tests/GetMethod_HEADTest HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("GetMethod_HEADTest");
    httpAssert.setTestStrategy(
        "A test for HttpServletRequest.getMethod() HEAD method.");
    httpAssert.setDebug("1");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetMethod_POST()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the name of the HTTP method with which this request was made, for example, GET, POST, or PUT.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetMethod_POSTTest.html");


    httpAssert.setRequest(
        "POST /servlet-tests/GetMethod_POSTTest HTTP/1.0");
    httpAssert.setTestName("GetMethod_POSTTest");
    httpAssert.setTestStrategy(
        "A test for HttpServletRequest.getMethod() POST method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetCookies()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns an array containing all of the Cookie objects the client sent with this request.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetCookiesTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetCookiesTest HTTP/1.0");
    httpAssert.setRequestHeaders(
        "Cookie:BestLanguage=Java");
    httpAssert.setTestName("GetCookiesTest");
    httpAssert.setTestStrategy(
        "A Test for HttpServletRequest.getCookies() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetCookies_01()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns returns null if no cookies were sent with the request ,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetCookies_01Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetCookies_01Test HTTP/1.0");
    httpAssert.setTestName("GetCookies_01Test");
    httpAssert.setTestStrategy(
        "A negative test for HttpServletRequest.getCookies() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetDateHeader()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the value of the specified request header as a long value that represents a Date object.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetDateHeaderTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetDateHeaderTest HTTP/1.0");
    httpAssert.setRequestHeaders(
        "If-Modified-Since:Sat, 01 Jan 2000 00:00:01 GMT");
    httpAssert.setTestName("GetDateHeaderTest");
    httpAssert.setTestStrategy(
        "A negative test for HttpServletRequest.getDateHeader() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetDateHeaderLCase()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Interprets and returns the lower case value of the specified request header as a long value that represents a Date object.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetDateHeaderLCaseTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetDateHeaderLCaseTest HTTP/1.0");
    httpAssert.setRequestHeaders(
        "If-Modified-Since:sat, 01 jan 2000 00:00:01 gmt");
    httpAssert.setTestName("GetDateHeaderLCaseTest");
    httpAssert.setTestStrategy(
        "A negative test for HttpServletRequest.getDateHeader() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetDateHeaderMxCase()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Interprets and returns the mixed case value of the specified request header as a long value that represents a Date object.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetDateHeaderMxCaseTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetDateHeaderMxCaseTest HTTP/1.0");
    httpAssert.setRequestHeaders(
        "If-Modified-Since:SaT, 01 jAn 2000 00:00:01 GmT");
    httpAssert.setTestName("GetDateHeaderMxCaseTest");
    httpAssert.setTestStrategy(
        "A negative test for HttpServletRequest.getDateHeader() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetDateHeader_01()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "If the request did not have a header of the specified name, this method returns -1,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetDateHeader_01Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetDateHeader_01Test HTTP/1.0");
    httpAssert.setTestName("GetDateHeader_01Test");
    httpAssert.setTestStrategy(
        "A negative test for HttpServletRequest.getDateHeader() method. We sent no Header from the client side, so we should get a value of '-1'");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetDateHeader_02()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Throws illegalArgumentException, If the header value can't be converted to a date, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetDateHeader_02Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetDateHeader_02Test HTTP/1.0");
    httpAssert.setRequestHeaders(
        "If-Modified-Since:java");
    httpAssert.setTestName("GetDateHeader_02Test");
    httpAssert.setTestStrategy(
        "A negative test for HttpServletRequest.getDateHeader() method. We sent a Header which is not of 'Date' format so we should get IllegalArgumentException.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetDateHeader_02LCase()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Throws illegalArgumentException, even when using a lowercase header it's value can't be converted to a date, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetDateHeader_02LCaseTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetDateHeader_02LCaseTest HTTP/1.0");
    httpAssert.setRequestHeaders(
        "If-Modified-Since:java");
    httpAssert.setTestName("GetDateHeader_02LCaseTest");
    httpAssert.setTestStrategy(
        "A negative test for HttpServletRequest.getDateHeader() method. We sent a Header which is not of 'Date' format so we should get IllegalArgumentException.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetDateHeader_02MxCase()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Throws illegalArgumentException, even when using a mixed case header it's value can't be converted to a date, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetDateHeader_02MxCaseTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetDateHeader_02MxCaseTest HTTP/1.0");
    httpAssert.setRequestHeaders(
        "If-Modified-Since:java");
    httpAssert.setTestName("GetDateHeader_02MxCaseTest");
    httpAssert.setTestStrategy(
        "A negative test for HttpServletRequest.getDateHeader() method. We sent a Header which is not of 'Date' format so we should get IllegalArgumentException.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetHeaderNames()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns an enumeration of all the header names this request contains., specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetHeaderNamesTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetHeaderNamesTest HTTP/1.0");
    httpAssert.setRequestHeaders(
        "Cookie:BestLanguage=java|If-Modified-Since:Sat, 01 Jan 2000 00:00:01 GMT");
    httpAssert.setTestName("GetHeaderNamesTest");
    httpAssert.setTestStrategy(
        "A test for HttpServletRequest.getHeaderNames() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetHeader_01()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns null if the request does not have a header of that name,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetHeader_01Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetHeader_01Test HTTP/1.0");
    httpAssert.setTestName("GetHeader_01Test");
    httpAssert.setTestStrategy(
        "A negative test for HttpServletRequest.getHeader() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetHeaders_01()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns an empty enumeration if the request does not have a header of the specified name, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetHeaders_01Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetHeaders_01Test HTTP/1.0");
    httpAssert.setTestName("GetHeaders_01Test");
    httpAssert.setTestStrategy(
        "A negative test for HttpServletRequest.getHeaders() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testGetHeadersEmpty()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "If the specified request header doesn't exist an empty Enumeration is returned.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetHeadersEmptyTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetHeadersEmptyTest HTTP/1.0");
    httpAssert.setTestName("GetHeadersEmptyTest");
    httpAssert.setTestStrategy(
        "A test for HttpServletRequest.getHeaders() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpSessionBindingEventConstructor1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Constructs an event that notifies an object that it has been bound to or unbound from a session. To receive the event, the object must implement HttpSessionBindingListener specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.http.HttpSessionBindingEvent");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpSessionBindingEvent/HttpSessionBindingEventConstructor1Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/HttpSessionBindingEventConstructor1Test HTTP/1.0");
    httpAssert.setTestName("HttpSessionBindingEventConstructor1Test");
    httpAssert.setTestStrategy(
        "Client calls a servlet that creates a HttpSessionBindingEvent object using the 2 argument method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpSessionBindingEventConstructor2()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Constructs an event that notifies an object that it has been bound to or unbound from a session. To receive the event, the object must implement HttpSessionBindingListener specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.http.HttpSessionBindingEvent");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpSessionBindingEvent/HttpSessionBindingEventConstructor2Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/HttpSessionBindingEventConstructor2Test HTTP/1.0");
    httpAssert.setTestName("HttpSessionBindingEventConstructor2Test");
    httpAssert.setTestStrategy(
        "Client calls a servlet that creates a HttpSessionBindingEvent object using the 2 argument method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  /**
   * Uses tests.javax_servlet_http.HttpServletRequestWrapper.HttpServletRequestWrapperGetRequestURLTestServlet
   * @throws Exception if anything goes wrong
   */
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
        "prefix:http|serverName:${host}|port:${port}|servletpath:_servlet-tests_hsreqw_HttpServletRequestWrapperGetRequestURLTest");
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
  public void testGetContextPath()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Returns the portion of the request URI that indicates the context of the request.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequest/GetContextPathTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/GetContextPathTest HTTP/1.0");
    httpAssert.setTestName("GetContextPathTest");
    httpAssert.setTestStrategy(
        "A Test for HttpServletRequest.getContextPath() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletRequestWrapperGetContextPath()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for default behavior of this method to return getContextPath() on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequestWrapper/HttpServletRequestWrapperGetContextPathTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/hsreqw/HttpServletRequestWrapperGetContextPathTest HTTP/1.0");
    httpAssert.setTestName("HttpServletRequestWrapperGetContextPathTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet the tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletRequestWrapperGetAuthType()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test if method returns the default getAuthType on the wrapped request object,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequestWrapper/HttpServletRequestWrapperGetAuthTypeTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/hsreqw/HttpServletRequestWrapperGetAuthTypeTest HTTP/1.0");
    httpAssert.setTestName("HttpServletRequestWrapperGetAuthTypeTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet the tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  // causes follow-on failures!
  @Test
  public void testHttpServletRequestWrapperGetRemoteUser()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for default behavior of this method to return getRemoteUser() on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequestWrapper/HttpServletRequestWrapperGetRemoteUserTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/hsreqw/HttpServletRequestWrapperGetRemoteUserTest HTTP/1.0");
    httpAssert.setTestName("HttpServletRequestWrapperGetRemoteUserTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet the tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testSendRedirect()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Sends a temporary redirect response to the client using the specified redirect location URL that is based on the server root,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setExpectHeaders(
        "Location:http://${host}:${port}/RedirectedTest");

    httpAssert.setRequest(
        "GET /servlet-tests/SendRedirectTest HTTP/1.0");
    httpAssert.setReturnCode("302");
    httpAssert.setTestName("SendRedirectTest");
    httpAssert.setTestStrategy(
        "A test for HttpServletResponse.sendRedirect() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testSendRedirectForWebApp()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Sends a temporary redirect response to the client using the specified redirect location URL that is based on the context-root,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setExpectHeaders(
        "Location:http://${host}:${port}/servlet-tests/RedirectedTest");
    //httpAssert.setExpectHeaders("Location:http://${host}:${port}/servlet-tests/RedirectedTest");


    httpAssert.setRequest(
        "GET /servlet-tests/SendRedirectForWebAppTest HTTP/1.0");
    httpAssert.setReturnCode("302");
    httpAssert.setTestName("SendRedirectForWebAppTest");
    httpAssert.setTestStrategy(
        "A test for HttpServletResponse.sendRedirect() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletResponseWrapperSendRedirect()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for default behavior of this method is to return sendRedirect(String) on the wrapped response object of a URL that is based on the server root, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.HttpServletResponseWrapper. sendRedirect(String location) method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setExpectHeaders("Location:http://${host}:${port}/HttpServletResponseWrapperRedirectedTest");
    //httpAssert.setExpectHeaders(
    //"Location:http://${host}:${port}/HttpServletResponseWrapperRedirectedTest");


    httpAssert.setRequest(
        "GET /servlet-tests/hsresw/HttpServletResponseWrapperSendRedirectTest HTTP/1.0");
    httpAssert.setReturnCode("302");
    httpAssert.setTestName("HttpServletResponseWrapperSendRedirectTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's response has been wrapped. The wrapper object adds a specific header to the response object and calls the responses sendRedirect method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletResponseWrapperSendRedirectForWebApp()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for default behavior of this method is to return sendRedirect(String) on the wrapped response object of a URL that is based on the context-root, specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.HttpServletResponseWrapper. sendRedirect(String location) method");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setExpectHeaders("Location:http://${host}:${port}/servlet-tests/hsresw/HttpServletResponseWrapperRedirectedTest");
    //httpAssert.setExpectHeaders("Location:http://${host}:${port}/servlet-tests/hsresw/HttpServletResponseWrapperRedirectedTest");


    httpAssert.setRequest(
        "GET /servlet-tests/hsresw/HttpServletResponseWrapperSendRedirectForWebAppTest HTTP/1.0");
    httpAssert.setReturnCode("302");
    httpAssert.setTestName(
        "HttpServletResponseWrapperSendRedirectForWebAppTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's response has been wrapped. The wrapper object adds a specific header to the response object and calls the responses sendRedirect method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testSendRedirectIgnoreHeader()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Verify that headers added after sendRedirect(String) are ignored by the container. Servlet Specification 2.3 section SRV.5.2.");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");


    httpAssert.setRequest(
        "GET /servlet-tests/SendRedirectIgnoreHeaderTest HTTP/1.0");
    httpAssert.setReturnCode("302");
    httpAssert.setTestName("SendRedirectIgnoreHeaderTest");
    httpAssert.setTestStrategy(
        "Issue a request to target servlet which will call sendRedirect() and then add a header to the response. The added header should not be visible to the client.");
    httpAssert.setUnexpectedHeaders(
        "HttpServletResponse:sendRedirectIgnoreHeader");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletResponseWrapperSendRedirectIgnoreHeader()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test that headers added after a call to sendRedirect(), will be ignored by the container and will not be sent to the client. See Servlet Specification 2.3 section SRV.5.2.");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");


    httpAssert.setRequest(
        "GET /servlet-tests/hsresw/HttpServletResponseWrapperSendRedirectIgnoreHeaderTest HTTP/1.0");
    httpAssert.setReturnCode("302");
    httpAssert.setTestName(
        "HttpServletResponseWrapperSendRedirectIgnoreHeaderTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet whose response has been wrapped. The wrapper object will call sendRedirect() and then add a header. The header should not be visible to the client.");
    httpAssert.setUnexpectedHeaders(
        "GenericResponseWrapper:sendRedirectIgnoreHeader");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testInvalidate()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Invalidates this session and unbinds any objects bound to it.,specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpSession/InvalidateTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/InvalidateTest HTTP/1.0");
    httpAssert.setTestName("InvalidateTest");
    httpAssert.setTestStrategy(
        "A test for HttpSession.invalidate() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testIsNew_01()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Throws IllegalStateException if this method is called on an already invalidated session, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpSession/IsNew_01Test.html");


    httpAssert.setRequest(
        "GET /servlet-tests/IsNew_01Test HTTP/1.0");
    httpAssert.setTestName("IsNew_01Test");
    httpAssert.setTestStrategy(
        "A negative test for HttpSession.IsNew() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  /**
   * Uses tests.javax_servlet_http.HttpServletRequestWrapper.HttpServletRequestWrapperGetHeadersTestServlet
   *
   * Test for default behavior of this method to return getHeaders(String name) on the
   * wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14
   *
   * @throws Exception if anything goes wrong
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
   * @throws Exception if anything goes wrong
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

  @Test
  public void testHttpSessionBindingEventRemoved()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test that when an attribute is removed from the session, the getName() method returns the name with which the object was bound to, the getSession() method returns the session that changed, and the getValue() method returns the value of the attribute being removed - specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.http .HttpSessionBindingEvent");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpSessionBindingEvent/HttpSessionBindingEventRemovedTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/HttpSessionBindingEventRemovedTest HTTP/1.0");
    httpAssert.setTestName("HttpSessionBindingEventRemovedTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet that adds/removes an attribute. The listener should detect the changes and writes the values returned by the getName, getSession(), and getValue() methods to a static log file. Servlet then reads the log and sends the contents back to the client.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpSessionBindingEventReplaced()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test that when an attribute is replaced from the session, the getName() method returns the name with which the object is bound to, the getSession() method returns the session that changed, and the getValue() method returns the new value of the attribute - specified in the Java Servlet Pages Specification v2.3, Sec 14 - javax.servlet.http .HttpSessionBindingEvent");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpSessionBindingEvent/HttpSessionBindingEventReplacedTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/HttpSessionBindingEventReplacedTest HTTP/1.0");
    httpAssert.setTestName("HttpSessionBindingEventReplacedTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet that adds/replaces an attribute. The listener should detect the changes and writes the values returned by the getName, getSession(), and getValue() methods to a static log file. Servlet then reads the log and sends the contents back to the client. the log and sends the contents back to the client.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletRequestWrapperGetPathInfo()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for default behavior of this method to return getPathInfo() on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequestWrapper/HttpServletRequestWrapperGetPathInfoTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/hsreqw/HttpServletRequestWrapperGetPathInfoTest/pathinfostring1/pathinfostring2 HTTP/1.0");
    httpAssert.setTestName("HttpServletRequestWrapperGetPathInfoTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet the tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testHttpServletRequestWrapperGetPathTranslated()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for default behavior of this method to return getPathTranslated() on the wrapped request object, specified in the Java Servlet Pages Specification v2.3, Sec 14");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("true");
    httpAssert.setGoldenResource(
        "watchdog/resources/servlet-golden/javax_servlet_http/HttpServletRequestWrapper/HttpServletRequestWrapperGetPathTranslatedTest.html");


    httpAssert.setRequest(
        "GET /servlet-tests/hsreqw/HttpServletRequestWrapperGetPathTranslatedTest/javax_servlet HTTP/1.0");
    httpAssert.setTestName("HttpServletRequestWrapperGetPathTranslatedTest");
    httpAssert.setTestStrategy(
        "Client calls a servlet who's request has been wrapped. The wrapper object writes a message to a static log file and calls the wrapped objects method. Servlet the tests the returned value and returns the result of the test plus the contents of the static log file.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }


// jsp-tests

  /**
   *
   * When the contentType attribute of the page  directive is specified, it will set the character
   * encoding and MIME type in the response to the client.
   * JavaServer Pages Specification v1.2, Sec. 2.10.1;
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testpositiveContentType() throws Exception {
    HttpRequestAsserter get = createGetAssertion("/jsp-tests/jsp/core_syntax/directives/page/content/positiveContenttype.jsp");
    get.setExpectedContentType("text/plain");
    get.setExpectedResponseCode(200);
    get.setExpectedContentType("text/plain");
    get.setExpectedContentTypeCharset("ISO-8859-1");
    get.connectToServerAndAssert();
  }

  @Test
  public void testnegativeDuplicateContentFatalTranslationError()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Duplicate contentType attribute/values within a given translation unit shall result in a fatal translation error. JavaServer Pages Specification v1.2, Sec. 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/content/negativeDuplicateContentFatalTranslationError.jsp HTTP/1.0");
    httpAssert.setReturnCode("500");
    httpAssert.setTestName("negativeDuplicateContentFatalTranslationErrorTest");
    httpAssert.setTestStrategy(
        "Declare a page directive with contentType attributes specified.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveExtends()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The extends attribute of the page directive identifies a fully qualfied class name into which the JSP page transformed. JavaServer Pages Specification v1.2, Sec. 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/directives/page/extend/positiveExtends.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/extend/positiveExtends.jsp HTTP/1.0");
    httpAssert.setTestName("positiveExtendsTest");
    httpAssert.setTestStrategy(
        "Provide the extends attribute with a fully qualified class.  The resulting JSP implementation class will use instanceof to validate that this page instance is an instance of the class that it extends.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveDefaultIsErrorPage()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default value of the 'isErrorPage' attribute is false.  JavaServer Pages Specification v1.2, Sec. 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/errorpage/positiveDefaultIsErrorPage.jsp HTTP/1.0");
    httpAssert.setReturnCode("500");
    httpAssert.setTestName("positiveDefaultIsErrorPageTest");
    httpAssert.setTestStrategy(
        "Verify that the 'isErrorPage' attribute is false by  generating an exception in the called page and then have the error page attempt to access the implicit exception object.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveErrorPage()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "When the isErrorPage attribute is set to true, the implicit exception object will be available and its value is a reference to the offending throwable from the source JSP page in error.  JavaServer Pages Specification v1.2, Sec 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/directives/page/errorpage/positiveErrorPage.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/errorpage/positiveErrorPage.jsp HTTP/1.0");
    httpAssert.setTestName("positiveErrorPageTest");
    httpAssert.setTestStrategy(
        "In the initial JSP page, generate a java.lang.Arithmetic Exception by dividing an int value by 0.  Validate the Exception type by using instanceof against the exception object.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testnegativeFatalTranslationError()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "A fatal translation error shall result if a JSP error page has the isErrorPage attribute set to false and an attempt is made to access the implicit exception object. JavaServer Pages Specification v1.2, Sec 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/errorpage/negativeFatalTranslationError.jsp HTTP/1.0");
    httpAssert.setReturnCode("500");
    httpAssert.setTestName("negativeFatalTranslationErrorTest");
    httpAssert.setTestStrategy(
        "Generate an exception from the requested page with the errorPage attribute set.  The Error page has isErrorPage set to false and will attempt to access the exception object.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveImport()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The import attribute of the page directive  denotes classes that the translated JSP page will import and thus making these classes available to the scripting environment. JavaServer Pages Specification v1.2, Sec. 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/directives/page/imports/positiveImport.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/imports/positiveImport.jsp HTTP/1.0");
    httpAssert.setTestName("positiveImportTest");
    httpAssert.setTestStrategy(
        "Use the import attribute to import 'java.util.Properties'.  Validated that a Properties object can be created and used.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testimplicitImportLang()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Translated JSP pages must automatically import classes of the java.lang package. JavaServer Pages Specification v1.2, Sec. 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/directives/page/imports/implicitImportLang.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/imports/implicitImportLang.jsp HTTP/1.0");
    httpAssert.setTestName("implicitImportLangTest");
    httpAssert.setTestStrategy(
        "Validate that classes from the java.lang  package are implicitly imported by creating and using a java.lang.Integer object.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testimplicitImportJsp()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Translated JSP pages must automatically import classes of the javax.servlet.jsp package. JavaServer Pages Specification v1.2, Sec. 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/directives/page/imports/implicitImportJsp.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/imports/implicitImportJsp.jsp HTTP/1.0");
    httpAssert.setTestName("implicitImportJspTest");
    httpAssert.setTestStrategy(
        "Validate that classes from the javax.servlet.jsp package are implicitly imported by calling  JspFactory.getDefaultFactory() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testimplicitImportServlet()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Translated JSP pages must automatically import classes of the javax.servlet package. JavaServer Pages Specification v1.2, Sec. 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/directives/page/imports/implicitImportServlet.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/imports/implicitImportServlet.jsp HTTP/1.0");
    httpAssert.setTestName("implicitImportServletTest");
    httpAssert.setTestStrategy(
        "Validate that classes from the javax.servlet package are implicitly imported by creating  and using an instance of RequestDispatcher.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testimplicitImportHttp()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Translated JSP pages must automatically import classes of the javax.servlet.http package. JavaServer Pages Specification v1.2, Sec. 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/directives/page/imports/implicitImportHttp.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/imports/implicitImportHttp.jsp HTTP/1.0");
    httpAssert.setTestName("implicitImportHttpTest");
    httpAssert.setTestStrategy(
        "Validate that classes from the javax.servlet.http package are implicitly imported by creating  and using an instance of HttpUtils.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveMultipleImport()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "A fatal translation error shall result if there is more than one occurrence of a page directive attribute with the exception of the import attribute. JavaServer Pages Specification v1.2, Sec. 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/imports/positiveMultipleImport.jsp HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("positiveMultipleImportTest");
    httpAssert.setTestStrategy(
        "Declare a page directive with two import attributes.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveInfo()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The arbitrary string incorporated into the translated page by using the info attribute of the page directive, is available via Servlet.getServletInfo(); JavaServer Pages Specification v1.2, Sec 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/directives/page/info/positiveInfo.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/info/positiveInfo.jsp HTTP/1.0");
    httpAssert.setTestName("positiveInfoTest");
    httpAssert.setTestStrategy(
        "Set the info attribute of the page directive. Call getServletInfo().");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveLang()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The only defined and required scripting language value for the language attribute is 'java' JavaServer Pages Specification v1.2, Sec. 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/directives/page/language/positiveLang.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/language/positiveLang.jsp HTTP/1.0");
    httpAssert.setTestName("positiveLangTest");
    httpAssert.setTestStrategy(
        "Validate that the language attribute can be set to 'java' without an error.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveSession()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "If the session attribute of the page directive  is set to 'true', then the implicit script  language variable named 'session' of type javax.servlet.http.HttpSession references the current/new session for the page JavaServer Pages Specification v1.2, Sec. 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/session/positiveSession.jsp HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("positiveSessionTest");
    httpAssert.setTestStrategy(
        "Set the session attribute to 'true' and validate that the implicit session variable can be accessed and used.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveSessionDefault()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The implicit session session variable will be available to the page by default as the default value for the session attribute is 'true' JavaServer Pages Specification v1.2, Sec. 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/session/positiveSessionDefault.jsp HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("positiveSessionDefaultTest");
    httpAssert.setTestStrategy(
        "Do not set the session attribute in the page. Validate that the implicit session variable can be accessed and used.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testnegativeSessionFatalTranslationError()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "When the session attribute is set to 'false', the JSP page does not participate in a session; the implicit session variable is unavailable, and any reference to it within the body of the JSP page is illegal and shall result in a fatal translation error. JavaServer Pages Specification v1.2, Sec. 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/session/negativeSessionFatalTranslationError.jsp HTTP/1.0");
    httpAssert.setReturnCode("500");
    httpAssert.setTestName("negativeSessionFatalTranslationErrorTest");
    httpAssert.setTestStrategy(
        "Validate that setting the session attribute to false will result in a fatal translation error if the  implicit session variable is referenced.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveBuffAutoflush()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "When the page buffer is full, content will automatically be flushed. JavaServer Pages Specification v1.2, Sec. 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/buffer/positiveBuffAutoflush.jsp HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("positiveBuffAutoflushTest");
    httpAssert.setTestStrategy(
        "Leaving the defaults for autoFlush and buffer, validate that the buffer is automatically flushed once the  buffer is full.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveBuffCreate()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "If the buffer attribute is set, it is legal to set autoFlush to 'false'. Doing so requires a manual flush from the page writer. JavaServer Pages Specification v1.2, Sec. 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/buffer/positiveBuffCreate.jsp HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("positiveBuffCreateTest");
    httpAssert.setTestStrategy(
        "Validate that the page can configure a buffer and set the autoFlush attribute to false.   Write data to the output stream and manually  flush the content");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testnegativeDuplicateBufferFatalTranslationError()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Duplicate buffer attributes within a given translation unit results in a fatal translation error. JavaServer Pages v1.2, Sec 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/buffer/negativeDuplicateBufferFatalTranslationError.jsp HTTP/1.0");
    httpAssert.setReturnCode("500");
    httpAssert.setTestName("negativeDuplicateBufferFatalTranslationErrorTest");
    httpAssert.setTestStrategy(
        "Declare a page directive with two buffer attributes.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testnegativeBufferOverflowException()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "If the 'autoFlush' attribute is false, an Exception will be raised if an overflow occurs. JavaServer Pages v1.2, Sec 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/directives/page/buffer/negativeBufferOverflowException.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/buffer/negativeBufferOverflowException.jsp HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("negativeBufferOverflowExceptionTest");
    httpAssert.setTestStrategy(
        "Declare a page directive with autoFlush set to false.   Overflow the buffer and verify the Exception is caught.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveIncludeCtxRelativeDirective()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The include directive inserts the text of the resource specified by the file attribute.  Included content can be referenced using a context-relative path. JavaServer Pages Specification v1.2, Sec 2.10.3");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/directives/include/positiveIncludeCtxRelativeDirective.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/include/positiveIncludeCtxRelativeDirective.jsp HTTP/1.0");
    httpAssert.setTestName("positiveIncludeCtxRelativeDirectiveTest");
    httpAssert.setTestStrategy(
        "Using an include directive, include content referenced by a context-relative path.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveIncludePageRelativeDirective()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The include directive inserts the text of the resource specified by the file attribute.  Included content can be referenced using a page-relative path. JavaServer Pages Specification v1.2, Sec 2.10.3");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/directives/include/positiveIncludePageRelativeDirective.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/include/positiveIncludePageRelativeDirective.jsp HTTP/1.0");
    httpAssert.setTestName("positiveIncludePageRelativeDirectiveTest");
    httpAssert.setTestStrategy(
        "Using an include directive, include content referenced by a page-relative path.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveTagLib()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The taglib directive declares that the page  uses a tag library, uniquely identifies  the tag library using a URI and associates a  tag prefix that will distinguish usage of the  actions in the library.  JavaServer Pages Specification v1.2, Sec. 2.10.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/directives/taglib/positiveTagLib.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/taglib/positiveTagLib.jsp HTTP/1.0");
    httpAssert.setTestName("positiveTagLibTest");
    httpAssert.setTestStrategy(
        "Validate that the taglib directive is recognized by the container by declaring a new tag and  calling an action against that tag.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveDeclaration()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Declarations should be a complete declarative statement,  or sequence thereof, according to the syntax of the  scripting language specified. JavaServer Pages Specification v1.2, Sec. 2.11.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/scripting/declaration/positiveDeclaration.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/scripting/declaration/positiveDeclaration.jsp HTTP/1.0");
    httpAssert.setTestName("positiveDeclarationTest");
    httpAssert.setTestStrategy(
        "Validate the scripting declarations are properly recognized, by declaring and assigning a value to an int variable, and displaying the value of the variable.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveExpr()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "An expression element in a JSP page is a scripting  language expression that is evaluated and the result is coerced to a String.  The result is subsequently emitted into the current out JspWriter object. JavaServer Pages Specification v1.2, Sec. 2.11.3");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/scripting/expressions/positiveExpr.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/scripting/expressions/positiveExpr.jsp HTTP/1.0");
    httpAssert.setTestName("positiveExprTest");
    httpAssert.setTestStrategy(
        "Validate that the container can correctly support a basic expression by validating the output returned.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveExprComment()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Expressions can be embedded in HTML comments to produce comments returned in the output stream containing dynamic content. JavaServer Pages Specification v1.2, Sec. 2.5.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/scripting/expressions/positiveExprComment.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/scripting/expressions/positiveExprComment.jsp HTTP/1.0");
    httpAssert.setTestName("positiveExprCommentTest");
    httpAssert.setTestStrategy(
        "Validate that an HTML stye comment with an embedded expression returns the value of the expression within the comment.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveExprWhiteSpace()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Whitespace is optional after the <%= and before the %> delimiters of the expression element. JavaServer Pages Specification v1.2, Sec. 2.11");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/scripting/expressions/positiveExprWhiteSpace.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/scripting/expressions/positiveExprWhiteSpace.jsp HTTP/1.0");
    httpAssert.setTestName("positiveExprWhiteSpaceTest");
    httpAssert.setTestStrategy(
        "Validate that the container correctly handles different whitespace values with an expression element.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveScriptlet()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "All scriptlet fragments in a given translation  unit are combined in the order they appear in  the JSP page, they must yield a valid statement,  or sequence of statements, in the specified  scripting language. JavaServer Pages Specification v1.2, Sec. 2.11.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/scripting/scriptlet/positiveScriptlet.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/scripting/scriptlet/positiveScriptlet.jsp HTTP/1.0");
    httpAssert.setTestName("positiveScriptletTest");
    httpAssert.setTestStrategy(
        "Correct syntax is used in the scriptlet");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testcheckSession()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The session scripting variable is implictly made made available to the scripting environment and and is associated with an object of type javax.servlet.http.HttpSession.  JavaServer Pages Specification v1.2, Sec. 2.8.3");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/implicitobjects/checkSession.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/implicitobjects/checkSession.jsp HTTP/1.0");
    httpAssert.setTestName("checkSessionTest");
    httpAssert.setTestStrategy(
        "Validate that the object associated with the  session scripting variable is of type  javax.servlet.http.HttpSession and that a method can be called against it.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testcheckConfig()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The config scripting variable is implictly made made available to the scripting environment and and is associated with an object of type javax.servlet.ServletConfig.  JavaServer Pages Specification v1.2, Sec. 2.8.3");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/implicitobjects/checkConfig.html");


    httpAssert.setRequest(
        "GET /jsp-tests/CheckConfig HTTP/1.0");
    httpAssert.setTestName("checkConfigTest");
    httpAssert.setTestStrategy(
        "Validate that the object associated with the  config scripting variable is of type  javax.servlet.ServletConfig and that a method can be called against it.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testcheckException()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The exception scripting variable is implictly made made available to the scripting environment (if the JSP page is an error page) and is associated with  an object of type java.lang.Throwable.  JavaServer Pages Specification v1.2, Sec. 2.8.3");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/implicitobjects/checkException.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/implicitobjects/checkException.jsp HTTP/1.0");
    httpAssert.setTestName("checkExceptionTest");
    httpAssert.setTestStrategy(
        "Validate that the object associated with the  exception scripting variable is of an instance of the exception type thrown (a subclass of java.lang.Throwable).");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testcheckOut()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The out scripting variable is implictly made made available to the scripting environment and and is associated with an object of type javax.servlet.jsp.JspWriter.  JavaServer Pages Specification v1.2, Sec. 2.8.3");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/implicitobjects/checkOut.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/implicitobjects/checkOut.jsp HTTP/1.0");
    httpAssert.setTestName("checkOutTest");
    httpAssert.setTestStrategy(
        "Validate that the object associated with the  out scripting variable is of type  javax.servlet.jsp.JspWriter.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testcheckPage()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The page scripting variable is implictly made made available to the scripting environment and and is associated with an object of type java.lang.Object.  JavaServer Pages Specification v1.2, Sec. 2.8.3");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/implicitobjects/checkPage.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/implicitobjects/checkPage.jsp HTTP/1.0");
    httpAssert.setTestName("checkPageTest");
    httpAssert.setTestStrategy(
        "Validate that the object associated with the  page scripting variable is of type  java.lang.Object.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testcheckPageContext()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The pageContext scripting variable is implictly made made available to the scripting environment and and is associated with an object of type javax.servlet.jsp.PageContext.  JavaServer Pages Specification v1.2, Sec. 2.8.3");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/implicitobjects/checkPageContext.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/implicitobjects/checkPageContext.jsp HTTP/1.0");
    httpAssert.setTestName("checkPageContextTest");
    httpAssert.setTestStrategy(
        "Validate that the object associated with the  pageContext scripting variable is of type  javax.servlet.jsp.PageContext and that a method can be called against it.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testcheckRequest()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The request scripting variable is implictly made made available to the scripting environment and and is associated with an object of type javax.servlet.http.HttpServletRequest or javax.servlet.ServletRequest (protocol dependant).  JavaServer Pages Specification v1.2, Sec. 2.8.3");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/implicitobjects/checkRequest.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/implicitobjects/checkRequest.jsp?Years=2 HTTP/1.0");
    httpAssert.setTestName("checkRequestTest");
    httpAssert.setTestStrategy(
        "Validate that the object associated with the  request scripting variable is of type  javax.servlet.Request (parent class of HttpServletRequest) and that a method can be called against it.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testcheckResponse()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The response scripting variable is implictly made made available to the scripting environment and and is associated with an object of type javax.servlet.http.HttpServletResponse or javax.servlet.ServletResponse (protocol dependant).  JavaServer Pages Specification v1.2, Sec. 2.8.3");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setExpectHeaders("TestHeader:Method call OK");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/implicitobjects/checkResponse.jsp HTTP/1.0");
    httpAssert.setTestName("checkResponseTest");
    httpAssert.setTestStrategy(
        "Validate that the object associated with the  response scripting variable is of type  javax.servlet.Response (parent class of HttpServletResponse) and that a method can be called against it.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testcheckApplication()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The application scripting variable is implictly made made available to the scripting environment and and is associated with an object of type javax.servlet.ServletContext.  JavaServer Pages Specification v1.2, Sec. 2.8.3");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/implicitobjects/checkApplication.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/implicitobjects/checkApplication.jsp HTTP/1.0");
    httpAssert.setTestName("checkApplicationTest");
    httpAssert.setTestStrategy(
        "Validate that the object associated with the  application scripting variable is of type  javax.servlet.ServletContext that a method can be called against it.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveSetBooleanObj()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Boolean properties of a Bean can be set with a String  Constant.  Conversion from String to Boolean will be automatically provided by java.lang.Boolean.valueOf(String). JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetBooleanObj.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetBooleanObj.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetBooleanObjTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance and set a Boolean property of the bean using a String constant.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveSetBooleanPrim()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Primitive boolean properties of a Bean can be set with a String  Constant.  Conversion from String to boolean will be automatically provided by java.lang.Boolean.valueOf(String). JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetBooleanPrim.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetBooleanPrim.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetBooleanPrimTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance and set a boolean property of the bean using a String constant.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveSetByteObj()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Byte properties of a Bean can be set with a String  Constant.  Conversion from String to Byte will be automatically provided by java.lang.Byte.valueOf(String). JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetByteObj.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetByteObj.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetByteObjTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance and set a Byte property of the bean using a String constant.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveSetBytePrim()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Primitive byte properties of a Bean can be set with a String  Constant.  Conversion from String to byte will be automatically provided by java.lang.Byte.valueOf(String). JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetBytePrim.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetBytePrim.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetBytePrimTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance and set a byte property of the bean using a String constant.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveSetCharObj()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Character properties of a Bean can be set with a String  Constant.  Conversion from String to Character will be automatically provided by String.charAt(0). JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetCharObj.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetCharObj.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetCharObjTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance and set a Character property of the bean using a String constant.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveSetCharPrim()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Primitive char properties of a Bean can be set with a String  Constant.  Conversion from String to char will be automatically provided by String.charAt(0). JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetCharPrim.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetCharPrim.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetCharPrimTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance and set a char property of the bean using a String constant.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveSetDoubleObj()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Double properties of a Bean can be set with a String  Constant.  Conversion from String to Double will be automatically provided by java.lang.Double.valueOf(String). JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetDoubleObj.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetDoubleObj.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetDoubleObjTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance and set a Double property of the bean using a String constant.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveSetDoublePrim()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Primitive double properties of a Bean can be set with a String  Constant.  Conversion from String to double will be automatically provided by java.lang.Double.valueOf(String). JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetDoublePrim.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetDoublePrim.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetDoublePrimTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance and set a double property of the bean using a String constant.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveSetFloatObj()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Float properties of a Bean can be set with a String  Constant.  Conversion from String to Float will be automatically provided by java.lang.Float.valueOf(String). JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetFloatObj.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetFloatObj.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetFloatObjTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance and set a Float property of the bean using a String constant.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveSetFloatPrim()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Primitive float properties of a Bean can be set with a String  Constant.  Conversion from String to float will be automatically provided by java.lang.Float.valueOf(String). JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetFloatPrim.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetFloatPrim.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetFloatPrimTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance and set a float property of the bean using a String constant.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveSetIndexedProp()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Indexed properties in a Bean can be set using jsp:setProperty. When assigning values to indexed properties, the value must be an array.  No Type converstions are applied during assigment. JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetIndexedProp.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetIndexedProp.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetIndexedPropTest");
    httpAssert.setTestStrategy(
        "Create a bean using useBean tag, use setProperty and set properties using the following array types:  byte char short int float long double boolean Byte Character Short Integer Float Long Double Boolean Access each of the properties via scripting, iterate through the array, and display the values.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveSetIntObj()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Integer properties of a Bean can be set with a String  Constant.  Conversion from String to Integer will be automatically provided by java.lang.Integer.valueOf(String). JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetIntObj.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetIntObj.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetIntObjTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance and set an Integer property of the bean using a String constant.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveSetIntPrim()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Primitive int properties of a Bean can be set with a String  Constant.  Conversion from String to int will be automatically provided by java.lang.Integer.valueOf(String). JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetIntPrim.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetIntPrim.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetIntPrimTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance and set an int property of the bean using a String constant.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveSetLongObj()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Long properties of a Bean can be set with a String  Constant.  Conversion from String to Long will be automatically provided by java.lang.Long.valueOf(String). JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetLongObj.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetLongObj.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetLongObjTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance and set an Long property of the bean using a String constant.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveSetLongPrim()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Primitive long properties of a Bean can be set with a String  Constant.  Conversion from String to long will be automatically provided by java.lang.long.valueOf(String). JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetLongPrim.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetLongPrim.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetLongPrimTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance and set a long property of the bean using a String constant.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveSetPropAll()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "If the property attribute of jsp:setProperty is set to '*', the tag will iterate over the current Servlet request parameters, matching parameter names and value type(s) to property names and setter method types(s), setting each matched property to the value of the matching parameter. JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetPropAll.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetPropAll.jsp?name=Frodo&num=116165&str=Validated HTTP/1.0");
    httpAssert.setTestName("positiveSetPropAllTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance and set the property attribute to '*'.  The following properties should be set by the tag: name, num, str.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveSetPropNoParam()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "When the param attribute is omitted, the request parameter name is assumed to be the same as the Bean property name. JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetPropNoParam.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetPropNoParam.jsp?str=SAPPOTA HTTP/1.0");
    httpAssert.setTestName("positiveSetPropNoParamTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance. jsp:setProperty only specifies the name and property properties.  The container should set the value of the Bean's property to the value of the request parameter that has the same name as specified by the property attribute.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveSetPropParam()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "A single bean property can be set using a request  parameter from the Request object.  JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetPropParam.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetPropParam.jsp?Name=MANGO HTTP/1.0");
    httpAssert.setTestName("positiveSetPropParamTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance. jsp:setProperty only specifies the param property. The container should set the value of the Bean's property to the value of the request parameter that has the same name as specified by the param attribute.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveSetPropReqTimeSingleQuotes()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The value attribute can accept request-time  attribute expressions (single-quoted) as a value. JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetPropReqTimeSingleQuotes.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetPropReqTimeSingleQuotes.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetPropReqTimeSingleQuotesTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance. Set the value of a bean property using a  request-time attribute expression delimited by single quotes.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveSetPropReqTimeDoubleQuotes()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The value attribute can accept request-time  attribute expressions (double-quoted) as a value. JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetPropReqTimeDoubleQuotes.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetPropReqTimeDoubleQuotes.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetPropReqTimeDoubleQuotesTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance. Set the value of a bean property using a  request-time attribute expression delimited by double quotes.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveSetPropValue()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Properties in bean can be set using a String Constant. JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetPropValue.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetPropValue.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetPropValueTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance. Set the value of a bean property using the value attribute.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveBeanPropertyEditor()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The container will use a bean's  property editor when setting properties, if  introspection indicates one exists. JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveBeanPropertyEditor.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveBeanPropertyEditor.jsp HTTP/1.0");
    httpAssert.setTestName("positiveBeanPropertyEditorTest");
    httpAssert.setTestStrategy(
        "Create a bean using useBean tag, use setProperty and  and verfiy results using getProperty.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveGetProps()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "jsp:getProperty action places the value of a Bean instance property, converted to a String, into the implicit out  object, which is displayed as output. Java Server Pages Specification v1.2, Sec 4.3");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/getProperty/positiveGetProps.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/getProperty/positiveGetProps.jsp HTTP/1.0");
    httpAssert.setTestName("positiveGetPropsTest");
    httpAssert.setTestStrategy(
        "Create a bean using jsp:useBean tag, use jsp:getProperty to  access and validate the property.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testnegativeGetPropObjectNotFoundException()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "If accessing a property, and the object is not found, a request-time exception is raised. Java Server Pages Specification v1.2, Sec 4.3");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/getProperty/negativeGetPropObjectNotFoundException.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/getProperty/negativeGetPropObjectNotFoundException.jsp HTTP/1.0");
    httpAssert.setTestName("negativeGetPropObjectNotFoundExceptionTest");
    httpAssert.setTestStrategy(
        "Access a property of a non-existant bean and catch an exception.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveBeanNameType()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "A bean can be declared using the beanName and type attributes, where type is the same class specified by the beanName attribute. JavaServer Pages Specification v1.2, Sec. 4.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/useBean/positiveBeanNameType.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/useBean/positiveBeanNameType.jsp HTTP/1.0");
    httpAssert.setTestName("positiveBeanNameTypeTest");
    httpAssert.setTestStrategy(
        "Use jsp:useBean to create a bean where the beanName and type attributes have the same values.  Verify that the bean can be used by invoking a method on the bean inside a scriplet.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveBeanNameTypeCast()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "A bean can be declared using the beanName and type attributes, where type is a superclass of the class specified by the beanName attribute. JavaServer Pages Specification v1.2, Sec. 4.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/useBean/positiveBeanNameTypeCast.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/useBean/positiveBeanNameTypeCast.jsp HTTP/1.0");
    httpAssert.setTestName("positiveBeanNameTypeCastTest");
    httpAssert.setTestStrategy(
        "Use jsp:useBean to create a bean where the beanName specifies one particular type, and type specifies a superclass of the value specified by beanName.  Verify that the bean can be used by invoking a method on the bean inside a scriplet.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveBeanNameSerialized()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The container must be able to instantiate  a serialized bean specified in the beanName  attribute of jsp:useBean.  JavaServer Pages Specification v1.2, JSP 4.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/useBean/positiveBeanNameSerialized.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/useBean/positiveBeanNameSerialized.jsp HTTP/1.0");
    httpAssert.setTestName("positiveBeanNameSerializedTest");
    httpAssert.setTestStrategy(
        "Create a bean using useBean action where beanName refers to a serialized bean  and call a method on the bean to verify  that the serialized instance returns an expected value.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveBodyNew()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Bean properties can be set within the body of the jsp:useBean action. JavaServer Pages Specification v1.2, Sec 4.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/useBean/positiveBodyNew.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/useBean/positiveBodyNew.jsp HTTP/1.0");
    httpAssert.setTestName("positiveBodyNewTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new instance. Within the body of the jsp:useBean action, use jsp:setProperty to initialize a Bean property.  After closing the jsp:useBean action, use jsp:getProperty to validate  that the property was indeed set.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositivePageScopedObject()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Bean objects created with 'page' scope, will be available for the current page only.  The reference to the bean must be discarded upon completion of the current request by the page body. JavaServer Pages Specfication v1.2, Sec. 4.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setExpectHeaders("status:Test Status=PASSED");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/useBean/positivePageScopedObject.jsp HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("positivePageScopedObjectTest");
    httpAssert.setTestStrategy(
        "In one JSP page, create a new bean object using jsp:useBean with the scope set to 'page'.  After the object has been created, forward the request to a second JSP page to validate that an object associated with the same ID used in the first JSP page is not available in the current PageContext.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveRequestScopedObject()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Bean objects created with 'request' scope, will be available in the current page's ServletRequest object. JavaServer Pages Specfication v1.2, Sec. 4.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setExpectHeaders("status:Test Status=PASSED");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/useBean/positiveRequestScopedObject.jsp HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("positiveRequestScopedObjectTest");
    httpAssert.setTestStrategy(
        "In one JSP page, create a new bean object using jsp:useBean with the scope set to 'request'.  After the object has been created, forward the request to a second JSP page to validate that an object associated with the same ID used in the first JSP page is available in the current HttpServletRequest.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveSessionScopedObject()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Bean objects created with 'session' scope, will be available in the current page's HttpSession object. JavaServer Pages Specfication v1.2, Sec. 4.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setExpectHeaders("status:Test Status=PASSED");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/useBean/positiveSessionScopedObject.jsp HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("positiveSessionScopedObjectTest");
    httpAssert.setTestStrategy(
        "In one JSP page, create a new bean object using jsp:useBean with the scope set to 'session'.  After the object has been created, forward the request to a second JSP page to validate that an object associated with the same ID used in the first JSP page is available in the current HttpSession.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveApplicationScopedObject()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Bean objects created with 'application' scope, will be available in the current page's ServletContext object. JavaServer Pages Specfication v1.2, Sec. 4.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setExpectHeaders("status:Test Status=PASSED");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/useBean/positiveApplicationScopedObject.jsp HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("positiveApplicationScopedObjectTest");
    httpAssert.setTestStrategy(
        "In one JSP page, create a new bean object using jsp:useBean with the scope set to 'application'.  After the object has been created, forward the request to a second JSP page to validate that an object associated with the same ID used in the first JSP page is available in the current ServletContext.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveNoBody()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The jsp:useBean action does not require a body.  JavaServer Pages Specification v1.2, Sec. 4.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/useBean/positiveNoBody.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/useBean/positiveNoBody.jsp HTTP/1.0");
    httpAssert.setTestName("positiveNoBodyTest");
    httpAssert.setTestStrategy(
        "Explicit test to ensure that the jsp:useBean action can be used without a body.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveClassTypeCast()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Using 'class' and 'type' together in useBean tag, 'Class' is assignable to 'type'.  JavaServler Pages Specification v1.2, Sec 4.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/useBean/positiveClassTypeCast.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/useBean/positiveClassTypeCast.jsp HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("positiveClassTypeCastTest");
    httpAssert.setTestStrategy(
        "Create a new bean instance with a particulare class set for the class attribute, and a  parent class for the type attribute.  Validate That the instance is cast without an Exception.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testnegativeDuplicateIDFatalTranslationError()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Duplicate useBean id's found in the same translation unit shall result in a fatal translation error. JavaServer Pages Specification 1.2 Sec. 4.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/useBean/negativeDuplicateIDFatalTranslationError.jsp HTTP/1.0");
    httpAssert.setReturnCode("500");
    httpAssert.setTestName("negativeDuplicateIDFatalTranslationErrorTest");
    httpAssert.setTestStrategy(
        "Create two beans with the same id attribute. Validate that a Fatal Translation error occurs.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testnegativeSessionScopeFatalTranslationError()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "It is a fatal translation error to attempt to use session scope when the JSP page has declared via the page directive, that it does not participate in a session. JavaServer Pages Specification 1.2 Sec. 4.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/useBean/negativeSessionScopeFatalTranslationError.jsp HTTP/1.0");
    httpAssert.setReturnCode("500");
    httpAssert.setTestName("negativeSessionScopeFatalTranslationErrorTest");
    httpAssert.setTestStrategy(
        "Use the page directive to set the session attribute to false and then declare a bean with session scope. Validate that a Fatal Translation error occurs.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testnegativeClassCastException()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "A java.lang.ClassCastException shall occur at request time when the assignment of the object referenced to the scripting variable fails. JavaServer Pages Specification 1.2 Sec. 4.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/useBean/negativeClassCastException.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/useBean/negativeClassCastExceptionFwd.jsp HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("negativeClassCastExceptionTest");
    httpAssert.setTestStrategy(
        "In one JSP page, declare a bean of a particular type with session scope.  Once declared, this page will forward to a second JSP page which will try to  reference the previously declared bean in the session  scope, but will define the type attribute with  an incompatible type.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testnegativeNotFoundTypeInstantiationException()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "If the object specified by useBean is not found in the specified scope and neither class nor beanName are given, a java.lang.InstantiationException  shall occur. JavaServer Pages Specification 1.2 Sec. 4.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/useBean/negativeNotFoundTypeInstantiationException.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/useBean/negativeNotFoundTypeInstantiationException.jsp HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("negativeNotFoundTypeInstantiationExceptionTest");
    httpAssert.setTestStrategy(
        "Define a new bean within the JSP page without class or beanName defined.  Catch the Exception and print a message.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveForwardCtxRelative()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "A request can be forwarded to a JSP page, within the  same context, using a page relative-path.  JavaServer Pages Specification 1.2 Sec. 4.5");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/forward/positiveForwardCtxRelative.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/forward/positiveForwardCtxRelative.jsp HTTP/1.0");
    httpAssert.setTestName("positiveForwardCtxRelativeTest");
    httpAssert.setTestStrategy(
        "Validate that jsp:forward can forward a request  to a JSP page within the same context using  a page relative-path.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveForwardCtxRelativeHtml()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "A request can be forwarded to a static resource, within the same context, using a page-relative path.  JavaServer Pages Specification 1.2 Sec. 4.5");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/forward/positiveForwardCtxRelativeHtml.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/forward/positiveForwardCtxRelativeHtml.jsp HTTP/1.0");
    httpAssert.setTestName("positiveForwardCtxRelativeHtmlTest");
    httpAssert.setTestStrategy(
        "Validate that jsp:forward can forward a request to  a static resource within the same context using  a page-relative path.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveForwardPageRelative()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "A request can be forwarded to a JSP page, within the  same context, using a page relative-path.  JavaServer Pages Specification 1.2 Sec. 4.5");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/forward/positiveForwardPageRelative.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/forward/positiveForwardPageRelative.jsp HTTP/1.0");
    httpAssert.setTestName("positiveForwardPageRelativeTest");
    httpAssert.setTestStrategy(
        "Validate that jsp:forward can forward a request  to a JSP page within the same context using  a page-relative path.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveForwardPageRelativeHtml()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "A request can be forwarded to a static resource, within the same context, using a page-relative path.  JavaServer Pages Specification 1.2 Sec. 4.5");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/forward/positiveForwardPageRelativeHtml.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/forward/positiveForwardPageRelativeHtml.jsp HTTP/1.0");
    httpAssert.setTestName("positiveForwardPageRelativeHtmlTest");
    httpAssert.setTestStrategy(
        "Validate that jsp:forward can forward a request  to a static resource within the same context using  a page-relative path.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveForwardRequestAttrCtxRelative()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The page attribute of jsp:forward can accept a request-time attribute with a context-relative path value. JavaServer Pages v1.2, Sec. 4.5");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/forward/positiveRequestAttrCtxRelative.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/forward/positiveRequestAttrCtxRelative.jsp HTTP/1.0");
    httpAssert.setTestName("positiveForwardRequestAttrCtxRelativeTest");
    httpAssert.setTestStrategy(
        "Validate that jsp:forward can properly accept a  request-time attribute containing a context-relative path value.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveForwardRequestAttrPageRelative()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The page attribute of jsp:forward can accept a request-time attribute with a page-relative path value. JavaServer Pages v1.2, Sec. 4.5");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/forward/positiveRequestAttrPageRelative.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/forward/positiveRequestAttrPageRelative.jsp HTTP/1.0");
    httpAssert.setTestName("positiveForwardRequestAttrPageRelativeTest");
    httpAssert.setTestStrategy(
        "Validate that jsp:forward can properly accept a  request-time attribute containing a page-relative path value.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveIncludeCtxRelative()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "jsp:include provides for the inclusion  of dynamic resources, within the same context, using a context-relative path. JavaServer Pages Specification v1.2, Sec. 4.4");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/include/positiveIncludeCtxRelative.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/include/positiveIncludeCtxRelative.jsp HTTP/1.0");
    httpAssert.setTestName("positiveIncludeCtxRelativeTest");
    httpAssert.setTestStrategy(
        "Include content, using a context-relative path, from JSP page into the current JSP page.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveIncludeCtxRelativeHtml()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "jsp:include provides for the inclusion  of static resources, within the same context, using a context-relative path. JavaServer Pages Specification v1.2, Sec. 4.4");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/include/positiveIncludeCtxRelativeHtml.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/include/positiveIncludeCtxRelativeHtml.jsp HTTP/1.0");
    httpAssert.setTestName("positiveIncludeCtxRelativeHtmlTest");
    httpAssert.setTestStrategy(
        "Include content, using a context-relative path, from a static HTML page into the current JSP page.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveIncludePageRelative()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "jsp:include provides for the inclusion  of dynamic resources, within the same context, using a page-relative path. JavaServer Pages Specification v1.2, Sec. 4.4");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/include/positiveIncludePageRelative.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/include/positiveIncludePageRelative.jsp HTTP/1.0");
    httpAssert.setTestName("positiveIncludePageRelativeTest");
    httpAssert.setTestStrategy(
        "Include content, using a page-relative path, from a JSP page into the current JSP page.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveIncludeRequestAttrCtxRelative()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The page attribute of jsp:include accepts request-time attributes containing context-relative path values.");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/include/positiveRequestAttrCtxRelative.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/include/positiveRequestAttrCtxRelative.jsp HTTP/1.0");
    httpAssert.setTestName("positiveIncludeRequestAttrCtxRelativeTest");
    httpAssert.setTestStrategy(
        "Validate the page attribute of jsp:include can correctly accept request-time attribute values which contain context-relative paths.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveIncludeRequestAttrPageRelative()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The page attribute of jsp:include accepts request-time attributes containing page-relative path values.");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/include/positiveRequestAttrPageRelative.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/include/positiveRequestAttrPageRelative.jsp HTTP/1.0");
    httpAssert.setTestName("positiveIncludeRequestAttrPageRelativeTest");
    httpAssert.setTestStrategy(
        "Validate the page attribute of jsp:include can correctly accept request-time attribute values which contain page-relative paths.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveForward()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Calling PageContext.forward(java.lang.String relUrlPath) with a relativeUrlPath that doesn't begin with a leading '/' will forward the current ServletRequest and ServletResponse to another active component in the application relative to the URL of the request that was mapped to the calling JSP. JavaServer Pages Specification v1.2, Sec. 9.2.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/PageContext/positiveForward.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/PageContext/positiveForward.jsp HTTP/1.0");
    httpAssert.setTestName("positiveForwardTest");
    httpAssert.setTestStrategy(
        "Call PageContext.forward(String) with a page-relative path and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveGetAttributeAvbl()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "PageContext.getAttribute(java.lang.String name) will return the object associated with the name in the page scope. JavaServer Pages Specification v1.2, Sec. 9.2.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/PageContext/positiveGetAttributeAvbl.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/PageContext/positiveGetAttributeAvbl.jsp HTTP/1.0");
    httpAssert.setTestName("positiveGetAttributeAvblTest");
    httpAssert.setTestStrategy(
        "Call PageContext.getAttribute() to retrieve an object located in the page scope.  Validate that methods can be successfully called against the returned object.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveGetAttributeNotAvbl()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "PageContext.getAttribute(java.lang.String name) will return null of there is no objected assocated with the passed name in the page scope. JavaServer Pages Specification v1.2, Sec. 9.2.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/PageContext/positiveGetAttributeNotAvbl.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/PageContext/positiveGetAttributeNotAvbl.jsp HTTP/1.0");
    httpAssert.setTestName("positiveGetAttributeNotAvblTest");
    httpAssert.setTestStrategy(
        "Call PageContext.getAttribute(String) passing in a name in which there is no associated object in the page scope.  Validate that the value returned is null.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveGetAttributeNamesInScope()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "PageContext.getAttributeNamesInScope(int scope) will return an enumeration of all attributes in the given scope. JavaServer Pages Specification v1.2, Sec. 9.2.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/PageContext/positiveGetAttributeNamesInScope.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/PageContext/positiveGetAttributeNamesInScope.jsp HTTP/1.0");
    httpAssert.setTestName("positiveGetAttributeNamesInScopeTest");
    httpAssert.setTestStrategy(
        "Call PageContext.getAttributeInScope(int) passing in PageContext.PAGE_SCOPE.  Validate that the expected object is found within the returned enumeration.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveGetAttributeScope()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "PageContext.getAttributeScope(java.lang.String name) returns scope where the attribute is defined. JavaServer Pages Specification v1.2, Sec. 9.2.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/PageContext/positiveGetAttributeScope.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/PageContext/positiveGetAttributeScope.jsp HTTP/1.0");
    httpAssert.setTestName("positiveGetAttributeScopeTest");
    httpAssert.setTestStrategy(
        "Call PageContext.getAttributeScope(String) against an attribute in page scope.  Validate that the proper scope value is returned.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testcheckGetSession()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "PageContext.getSession() returns the current HttpSession object. JavaServer Pages Specification v1.2, Sec. 9.2.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/PageContext/checkGetSession.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/PageContext/checkGetSession.jsp HTTP/1.0");
    httpAssert.setTestName("checkGetSessionTest");
    httpAssert.setTestStrategy(
        "Call PageContext.getSession() and verify that the HttpSession instance has the same reference as that referenced by the session scripting variable.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testcheckGetResponse()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "PageContext.getResponse() returns the current ServletResponse object. JavaServer Pages Specification v1.2, Sec. 9.2.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/PageContext/checkGetResponse.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/PageContext/checkGetResponse.jsp HTTP/1.0");
    httpAssert.setTestName("checkGetResponseTest");
    httpAssert.setTestStrategy(
        "Call PageContext.getResponse() and verify that the ServletResponse instance has the same reference as that referenced by the response scripting variable.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testcheckGetRequest()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "PageContext.getRequest() returns the current ServletRequest object. JavaServer Pages Specification v1.2, Sec. 9.2.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/PageContext/checkGetRequest.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/PageContext/checkGetRequest.jsp HTTP/1.0");
    httpAssert.setTestName("checkGetRequestTest");
    httpAssert.setTestStrategy(
        "Call PageContext.getRequest() and verify that the ServletRequest instance has the same reference as that referenced by the request scripting variable.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testcheckGetServletContext()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "PageContext.getServletContext() returns the current ServletContext object. JavaServer Pages Specification v1.2, Sec. 9.2.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/PageContext/checkGetServletContext.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/PageContext/checkGetServletContext.jsp HTTP/1.0");
    httpAssert.setTestName("checkGetServletContextTest");
    httpAssert.setTestStrategy(
        "Call PageContext.getServletContext() and verify that the ServletContext instance has the same reference as that referenced by the application scripting variable.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testcheckGetServletConfig()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "PageContext.getServletConfig() returns the current ServletConfig object. JavaServer Pages Specification v1.2, Sec. 9.2.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/PageContext/checkGetServletConfig.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/PageContext/checkGetServletConfig.jsp HTTP/1.0");
    httpAssert.setTestName("checkGetServletConfigTest");
    httpAssert.setTestStrategy(
        "Call PageContext.getServletConfig() and verify that the ServletConfig instance has the same reference as that referenced by the config scripting variable.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testcheckGetOut()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "PageContext.getOut() returns the current JspWriter object. JavaServer Pages Specification v1.2, Sec. 9.2.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/PageContext/checkGetOut.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/PageContext/checkGetOut.jsp HTTP/1.0");
    httpAssert.setTestName("checkGetOutTest");
    httpAssert.setTestStrategy(
        "Call PageContext.getOut() and verify that the JspWriter instance has the same reference as that referenced by the out scripting variable.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveInclude()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Calling PageContext.include(java.lang.String relUrlPath) with a relativeUrlPath that doesn't begin with a leading '/' will cause the specified content, relative to the URL of the request that was mapped to the calling JSP, to be processed as part of the current ServletRequest and ServletResponse.  JavaServer Pages Specification v1.2, Sec. 9.2.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/PageContext/positiveInclude.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/PageContext/positiveInclude.jsp HTTP/1.0");
    httpAssert.setTestName("positiveIncludeTest");
    httpAssert.setTestStrategy(
        "Call PageContext.include(String) with a page-relative path and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveRemoveAttribute()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "PageContext.removeAttribute(java.lang.String name) will remove the  object reference associated with the passed name, from all scopes. JavaServer Pages Specification v1.2, Sec. 9.2.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/PageContext/positiveRemoveAttribute.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/PageContext/positiveRemoveAttribute.jsp HTTP/1.0");
    httpAssert.setTestName("positiveRemoveAttributeTest");
    httpAssert.setTestStrategy(
        "Call PageContext.removeAttribute(String) and verify that the  attribute is indeed removed.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveSetAttribute()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "PageContext.setAttribute(java.lang.String name, java.lang.Object attribute) will register the name and object specified within the page scope. JavaServer Pages Specification v1.2, Sec. 9.2.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/PageContext/positiveSetAttribute.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/PageContext/positiveSetAttribute.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetAttributeTest");
    httpAssert.setTestStrategy(
        "Call PageContext.setAttribute(String,Object) and validate that attribute can be obtained via a call to PageContext.getAttribute(String).");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveSetAttributeInScope()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "PageContext.setAttributeInScope(java.lang.String name, java.lang.Object attribute, int) will register the name and object within the specified scope. JavaServer Pages Specification v1.2, Sec. 9.2.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/PageContext/positiveSetAttributeInScope.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/PageContext/positiveSetAttributeInScope.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetAttributeInScopeTest");
    httpAssert.setTestStrategy(
        "Call PageContext.setAttribute(String,Object,int) and validate that the attribute is in the scope specified via a call to PageContext.getAttributeScope(String).");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveJSPInitJSP()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Container implementations must ensure that getServletConfig() will return the desired value in cases where page authors override jspInit().  JavaServer Pages Specification v1.2, Sec. 9.1.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/HttpJspPage/positiveJSPInitJSP.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/HttpJspPage/positiveJSPInitJSP.jsp HTTP/1.0");
    httpAssert.setTestName("positiveJSPInitJSPTest");
    httpAssert.setTestStrategy(
        "Override the jspInit() method in a declaration. Validate that a call to getServletConfig returns a non-null value.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositivePrintBoolean()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.print(boolean b) will print the String produced by java.lang.String.valueOf(boolean).  The byte's displayed will be according to the platform's default character encoding. JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintBoolean.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintBoolean.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintBooleanTest");
    httpAssert.setTestStrategy(
        "Call print(boolean b) and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositivePrintChar()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.print(char c) prints passed value according to the platform's default character encoding. Server Pages Specification v1.2, Sec 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintChar.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintChar.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintCharTest");
    httpAssert.setTestStrategy(
        "Call print(char c) and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositivePrintCharArray()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.print(char[] s) will print an array of characters according to the platform's default  character encoding. JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintCharArray.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintCharArray.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintCharArrayTest");
    httpAssert.setTestStrategy(
        "Call print(char[] c) and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositivePrintDouble()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.print(double d) prints a String representation of a double-precision floating-point number, generated by  java.lang.String.valueOf(double), with the bytes translated to the platform's default character encoding. JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintDouble.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintDouble.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintDoubleTest");
    httpAssert.setTestStrategy(
        "Call print(double d) and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositivePrintFloat()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.print(float f) prints a String representation of a floating point number, generated by java.lang.String.valueOf(float), with the bytes translated to the platform's default character encoding. JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintFloat.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintFloat.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintFloatTest");
    httpAssert.setTestStrategy(
        "Call print(float f) and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositivePrintInt()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.print(int i) prints a String representation of an integer, generated by java.lang.String.valueOf(int),  with the bytes translated to the platform's default  character encoding. JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintInt.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintInt.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintIntTest");
    httpAssert.setTestStrategy(
        "Call print(int i) and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositivePrintLong()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.print(long l) prints a String representation of a long integer, generated by java.lang.String.valueOf(long),  with the bytes translated to the platform's default  character encoding. JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintLong.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintLong.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintLongTest");
    httpAssert.setTestStrategy(
        "Call print(long l) and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositivePrintObj()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.print(java.lang.Object obj) prints a String  representation of an object, generated by  java.lang.String.valueOf(Object), with the bytes translated  to the platform's default character encoding. JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintObj.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintObj.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintObjTest");
    httpAssert.setTestStrategy(
        "Call print(java.lang.Object obj) and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositivePrintStr()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.print(java.lang.String str) prints a String  representation of an object, with the bytes translated  to the platform's default character encoding. JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintStr.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintStr.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintStrTest");
    httpAssert.setTestStrategy(
        "Call print(java.lang.String str) and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositivePrintln()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.println() will terminate the current line by writing the ling separator string. JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintln.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintln.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintlnTest");
    httpAssert.setTestStrategy(
        "Call println() and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositivePrintlnBoolean()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.println(boolean b) prints a boolean value and then terminates the line.  This method behaves as though it invokes print(boolean b) and then println(). JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintlnBoolean.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintlnBoolean.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintlnBooleanTest");
    httpAssert.setTestStrategy(
        "Call println(boolean b) and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositivePrintlnChar()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.println(char c) prints a char value and then terminates the line.  This method behaves as though it invokes print(char c) and then println(). JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintlnChar.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintlnChar.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintlnCharTest");
    httpAssert.setTestStrategy(
        "Call println(char c) and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositivePrintlnCharArray()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.println(char[] s) prints an array of characters and then terminates the line.  This method behaves as though it invokes print(char[] s) and then println(). JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintlnCharArray.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintlnCharArray.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintlnCharArrayTest");
    httpAssert.setTestStrategy(
        "Call println(char c) and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositivePrintlnDouble()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.println(double d) prints a double-precision floating-point value and then terminates the line.   This method behaves as though it invokes print(double d)  and then println(). JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintlnDouble.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintlnDouble.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintlnDoubleTest");
    httpAssert.setTestStrategy(
        "Call println(double d) and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositivePrintlnFloat()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.println(float f) prints a floating-point value  and then terminates the line.  This method behaves as though  it invokes print(float f) and then println(). JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintlnFloat.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintlnFloat.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintlnFloatTest");
    httpAssert.setTestStrategy(
        "Call println(float f) and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositivePrintlnInt()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.println(int i) prints an integer value  and then terminates the line.  This method behaves as though  it invokes print(int i) and then println(). JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintlnInt.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintlnInt.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintlnIntTest");
    httpAssert.setTestStrategy(
        "Call println(int i) and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositivePrintlnLong()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.println(long l) prints a long integer value  and then terminates the line.  This method behaves as though  it invokes print(long l) and then println(). JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintlnLong.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintlnLong.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintlnLongTest");
    httpAssert.setTestStrategy(
        "Call println(long l) and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositivePrintlnObj()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.println(java.lang.Object obj) prints an Object  value and then terminates the line.  This  method behaves as though it invokes print(java.lang.Object obj)  and then println(). JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintlnObj.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintlnObj.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintlnObjTest");
    httpAssert.setTestStrategy(
        "Call println(java.lang.Object obj) and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositivePrintlnStr()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.println(java.lang.String str) prints a long  integer value and then terminates the line.  This  method behaves as though it invokes print(java.lang.String str)  and then println(). JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintlnStr.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintlnStr.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintlnStrTest");
    httpAssert.setTestStrategy(
        "Call println(java.lang.String str) and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveClear()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.clear() will cause the contents of the output  buffer to be cleared. JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/misc/positiveClear.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/misc/positiveClear.jsp HTTP/1.0");
    httpAssert.setTestName("positiveClearTest");
    httpAssert.setTestStrategy(
        "Using a page with the default buffer size of 8kb, write data to the buffer and call clear.  The cleared data should not be present in the response.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveFlush()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.flush() will cause the stream to write any saved content to the intended destiation. JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/misc/positiveFlush.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/misc/positiveFlush.jsp HTTP/1.0");
    httpAssert.setTestName("positiveFlushTest");
    httpAssert.setTestStrategy(
        "Obtain the current buffer size being used, then write and flush some sample text. Validate that the stream was indeed flushed by checking the number of bytes remaining in  the buffer.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveGetBufferSize()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.getBufferSize() returns the size of the buffer in bytes used by the particular JspWriter instance. JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/misc/positiveGetBufferSize.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/misc/positiveGetBufferSize.jsp HTTP/1.0");
    httpAssert.setTestName("positiveGetBufferSizeTest");
    httpAssert.setTestStrategy(
        "Set the buffer of the page to 5kb.  Call getBufferSize() and validate that the value returned is as expected.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveGetBufferSizeUnBuffered()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "When buffering is disabled within a particular  JSP page, a call to JspWriter.getBufferSize()  will return 0 (zero). JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/misc/positiveGetBufferSizeUnBuffered.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/misc/positiveGetBufferSizeUnBuffered.jsp HTTP/1.0");
    httpAssert.setTestName("positiveGetBufferSizeUnBufferedTest");
    httpAssert.setTestStrategy(
        "Set the buffer to 'none' and call getBufferSize(). Validate that the value returned is 0 (zero).");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveGetRemaining()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.getRemaining() will return the number of  unused bytes in the buffer. JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/misc/positiveGetRemaining.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/misc/positiveGetRemaining.jsp HTTP/1.0");
    httpAssert.setTestName("positiveGetRemainingTest");
    httpAssert.setTestStrategy(
        "Write 6 bytes of data to the buffer and call getRemaining().  The value returned should be 6 bytes less than the value returned by  getBufferSize().");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveGetRemainingBufferUnset()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.getRemaining() will return 0 (zero) if the JSP page doesn't use a buffer. JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/misc/positiveGetRemainingBufferUnset.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/misc/positiveGetRemainingBufferUnset.jsp HTTP/1.0");
    httpAssert.setTestName("positiveGetRemainingBufferUnsetTest");
    httpAssert.setTestStrategy(
        "Set the buffer of the JSP page to 'none', and validate that a call to getRemaining() returns 0 (zero).");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveIsAutoFlush()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.isAutoFlush() will inidicate whether the JspWriter will flush its buffer automatically. JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/misc/positiveIsAutoFlush.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/misc/positiveIsAutoFlush.jsp HTTP/1.0");
    httpAssert.setTestName("positiveIsAutoFlushTest");
    httpAssert.setTestStrategy(
        "JSP pages automatically flush their buffer by default.  Call isAutoFlush() and validated that 'true' is returned.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveNewLine()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.newLine() will write a line separator as defined by the system property 'line.separator'. JavaServer Pages Specification v1.2 , Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/misc/positiveNewLine.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/misc/positiveNewLine.jsp HTTP/1.0");
    httpAssert.setTestName("positiveNewLineTest");
    httpAssert.setTestStrategy(
        "Validate the a call to newLine() produces the expected results.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveTagGetAttribute()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test a TagData object by passing a hashtable and printing  the contents of the hashtable using the getAttribute()  method of TagData., specified in the Java Server Pages  Specification v1.2, Sec 10.5.7");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/TagData/positiveTagGetAttribute.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TagData/positiveTagGetAttribute.jsp HTTP/1.0");
    httpAssert.setTestName("positiveTagGetAttributeTest");
    httpAssert.setTestStrategy(
        "Testing TagData release() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveTagSetAttribute()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test a TagData object by passing a hashtable created using  setAttribute() method of the TagData and print the contents of the hashtable, specified in the Java Server Pages  Specification v1.2, Sec 10.5.7");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/TagData/positiveTagSetAttribute.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TagData/positiveTagSetAttribute.jsp HTTP/1.0");
    httpAssert.setTestName("positiveTagSetAttributeTest");
    httpAssert.setTestStrategy(
        "Testing TagData setAttribute() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveTagGetAttributeString()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test a TagData object by passing a hashtable created using  setAttribute() method of the TagData and print the  contents of the hashtable using the getAttributeString()  method, specified in the Java Server Pages Specification  v1.2, Sec 10.5.7");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/TagData/positiveTagGetAttributeString.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TagData/positiveTagGetAttributeString.jsp HTTP/1.0");
    httpAssert.setTestName("positiveTagGetAttributeStringTest");
    httpAssert.setTestStrategy(
        "Testing TagData getAttributeString() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveGetValues()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for getValues() method which returns an enumeration  and print the contents, specified in the Java Server  Pages Specification v1.2, Sec 10.1.4");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/TagSupport/positiveGetValues.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TagSupport/positiveGetValues.jsp HTTP/1.0");
    httpAssert.setTestName("positiveGetValuesTest");
    httpAssert.setTestStrategy(
        "Testing TagSupport getValues() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveGetValue()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for getValue() method, specified in the Java Server  Pages Specification v1.2, Sec 10.1.4");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/TagSupport/positiveGetValue.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TagSupport/positiveGetValue.jsp HTTP/1.0");
    httpAssert.setTestName("positiveGetValueTest");
    httpAssert.setTestStrategy(
        "Testing TagSupport getValue() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveRemoveValue()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for removeValue() method, specified in the Java  Server Pages Specification v1.2, Sec 10.1.4");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/TagSupport/positiveRemoveValue.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TagSupport/positiveRemoveValue.jsp HTTP/1.0");
    httpAssert.setTestName("positiveRemoveValueTest");
    httpAssert.setTestStrategy(
        "Testing TagSupport removeValue() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveSetValue()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for setValue() method, specified in the Java Server  Pages Specification v1.2, Sec 10.1.4");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/TagSupport/positiveSetValue.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TagSupport/positiveSetValue.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetValueTest");
    httpAssert.setTestStrategy(
        "Testing TagSupport setValue() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveGetParent()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for getParent() method which returns the parent class  name, specified in the Java Server Pages Specification  v1.2, Sec 10.1.4");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/TagSupport/positiveGetParent.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TagSupport/positiveGetParent.jsp HTTP/1.0");
    httpAssert.setTestName("positiveGetParentTest");
    httpAssert.setTestStrategy(
        "Testing TagSupport getParent() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testdefault_return_values()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Tests if the default values are returned from the methods of TagSupport class  v1.2, Sec 10.1.4");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/TagSupport/default_return_values.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TagSupport/default_return_values.jsp HTTP/1.0");
    httpAssert.setTestName("default_return_valuesTest");
    httpAssert.setTestStrategy(
        "testing TagSupport default return values by calling super.FunctionName");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveTSDoEndTag()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Tests that doEndTag metjod is called when the end of the tag is found  v1.2, Sec 10.1.4");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/TagSupport/positiveDoEndTag.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TagSupport/positiveDoEndTag.jsp HTTP/1.0");
    httpAssert.setTestName("positiveTSDoEndTagTest");
    httpAssert.setTestStrategy(
        "testing TagSupport doEndTag method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveSetGetId()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for setId and getId methods of TagSupport  v1.2, Sec 10.1.4");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/TagSupport/positiveSetGetId.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TagSupport/positiveSetGetId.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetGetIdTest");
    httpAssert.setTestStrategy(
        "testing TagSupport getId and setId in the same test");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositivefindAncestorWithClass()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for Nested classes. Tests the findAncestorWithClass method for TagSupport  v1.2, Sec 10.1.4");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/TagSupport/positivefindAncestorWithClass.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TagSupport/positivefindAncestorWithClass.jsp HTTP/1.0");
    httpAssert.setTestName("positivefindAncestorWithClassTest");
    httpAssert.setTestStrategy(
        "Testing TagSupport.findAncestorWithClass method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveDoStartTag()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for checking if doStartTag() is called correctly  v1.2, Sec 10.1.4");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/TagSupport/positiveDoStartTag.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TagSupport/positiveDoStartTag.jsp HTTP/1.0");
    httpAssert.setTestName("positiveDoStartTagTest");
    httpAssert.setTestStrategy(
        "Testing TagSupport doStartTag() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveSetPageContext()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test if setPageContext() can be used to set the page context.Using this page context obj set an attribute which is passed to the jsp page  v1.2, Sec 10.1.4");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/TagSupport/positiveSetPageContext.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TagSupport/positiveSetPageContext.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetPageContextTest");
    httpAssert.setTestStrategy(
        "Testing TagSupport setPageContext() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveTSDoAfterBody()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test the DoAfterBody method of the TagSupport class.     Create a Tag handler that outputs a message a given number of times from the DoAFterBody  v1.2, Sec 10.1.4");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/TagSupport/positiveDoAfterBody.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TagSupport/positiveDoAfterBody.jsp HTTP/1.0");
    httpAssert.setTestName("positiveTSDoAfterBodyTest");
    httpAssert.setTestStrategy(
        "Testing TagSupport setPageContext() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveDoInitBody()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "BodyTagSupport.doInitBody() will be called before the first body evaluation. JavaServer Pages Specification v1.2, Sec. 10.2.3");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/BodyTagSupport/positiveDoInitBody.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/BodyTagSupport/positiveDoInitBody.jsp HTTP/1.0");
    httpAssert.setTestName("positiveDoInitBodyTest");
    httpAssert.setTestStrategy(
        "Validate that doInitBody() is called by the container.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveBTSDoAfterBody()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "BodyTagSupport.doAfterBody() will be called after the  body has been evaluated.  The body will not be  reevaluated and the page processing will continue. JavaServerPages Specification v1.2, Sec. 10.2.3");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/BodyTagSupport/positiveDoAfterBody.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/BodyTagSupport/positiveDoAfterBody.jsp HTTP/1.0");
    httpAssert.setTestName("positiveBTSDoAfterBodyTest");
    httpAssert.setTestStrategy(
        "Validate that doAfterBody() is called by the container.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveBTSDoEndTag()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "BodyTagSupport.doEndTag() by default will return EVAL_PAGE when processing the end tag. JavaServer Pages Specification v1.2, Sec. 10.2.3");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/BodyTagSupport/positiveDoEndTag.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/BodyTagSupport/positiveDoEndTag.jsp HTTP/1.0");
    httpAssert.setTestName("positiveBTSDoEndTagTest");
    httpAssert.setTestStrategy(
        "Validate that doEndTag() is called by the container.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveLifeCycle()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The complete lifecycle for a tag extending BodyTagSupport will contain calls to doStartTag(), doInitBody(), doAfterBody(), doEndTag(), and release(). JavaServer Pages Specification v1.2, Sec 10.2.3");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/BodyTagSupport/positiveLifeCycle.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/BodyTagSupport/positiveLifeCycle.jsp HTTP/1.0");
    httpAssert.setTestName("positiveLifeCycleTest");
    httpAssert.setTestStrategy(
        "Validate that the appropriate methods are called in order when processing the tag.  NOTE:  release() is not tested as as the point release() is called is container dependant.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testtag_attr_setting_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test that attribute setting is as wanted, specified in the  Java Server Pages Specification v1.2, Sec 10.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/LifeCycle/tag_attr_setting_1.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/LifeCycle/tag_attr_setting_1.jsp HTTP/1.0");
    httpAssert.setTestName("tag_attr_setting_1Test");
    httpAssert.setTestStrategy(
        "Testing LifeCycle process");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testtag_attr_setting_2()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test that attribute setting is as wanted and intermix some  different tags, specified in the Java Server Pages  Specification v1.2, Sec 10.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/LifeCycle/tag_attr_setting_2.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/LifeCycle/tag_attr_setting_2.jsp HTTP/1.0");
    httpAssert.setTestName("tag_attr_setting_2Test");
    httpAssert.setTestStrategy(
        "Testing LifeCycle process");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testtag_attr_setting_3()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Another test for attribute setting intermixed with some  different tags, specified in the Java Server Pages  Specification v1.2, Sec 10.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/LifeCycle/tag_attr_setting_3.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/LifeCycle/tag_attr_setting_3.jsp HTTP/1.0");
    httpAssert.setTestName("tag_attr_setting_3Test");
    httpAssert.setTestStrategy(
        "Testing LifeCycle process");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testtag_check_BodyContent_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Check that BodyContent provides access to the proper  information, specified in the Java Server Pages  Specification v1.2, Sec 10.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/LifeCycle/tag_check_BodyContent_1.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/LifeCycle/tag_check_BodyContent_1.jsp HTTP/1.0");
    httpAssert.setTestName("tag_check_BodyContent_1Test");
    httpAssert.setTestStrategy(
        "Testing LifeCycle process");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testtag_check_BodyContent_2()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Check that BodyContent provides access to the proper  information, specified in the Java Server Pages  Specification v1.2, Sec 10.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/LifeCycle/tag_check_BodyContent_2.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/LifeCycle/tag_check_BodyContent_2.jsp HTTP/1.0");
    httpAssert.setTestName("tag_check_BodyContent_2Test");
    httpAssert.setTestStrategy(
        "Testing LifeCycle process");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testtag_check_BodyContent_3()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Check that BodyContent provides access to the proper  information, specified in the Java Server Pages  Specification v1.2, Sec 10.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/LifeCycle/tag_check_BodyContent_3.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/LifeCycle/tag_check_BodyContent_3.jsp HTTP/1.0");
    httpAssert.setTestName("tag_check_BodyContent_3Test");
    httpAssert.setTestStrategy(
        "Testing LifeCycle process");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testtag_check_BodyContent_4()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Check that BodyContent provides access to the proper  information, specified in the Java Server Pages  Specification v1.2, Sec 10.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/LifeCycle/tag_check_BodyContent_4.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/LifeCycle/tag_check_BodyContent_4.jsp HTTP/1.0");
    httpAssert.setTestName("tag_check_BodyContent_4Test");
    httpAssert.setTestStrategy(
        "Testing LifeCycle process");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testtag_check_BodyContent_5()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Check that BodyContent provides access to the proper  information, specified in the Java Server Pages  Specification v1.2, Sec 10.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/LifeCycle/tag_check_BodyContent_5.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/LifeCycle/tag_check_BodyContent_5.jsp HTTP/1.0");
    httpAssert.setTestName("tag_check_BodyContent_5Test");
    httpAssert.setTestStrategy(
        "Testing LifeCycle process");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testtag_check_comments_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Check that comments are processed correctly, specified in  the Java Server Pages Specification v1.2, Sec 10.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/LifeCycle/tag_check_comments_1.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/LifeCycle/tag_check_comments_1.jsp HTTP/1.0");
    httpAssert.setTestName("tag_check_comments_1Test");
    httpAssert.setTestStrategy(
        "Testing LifeCycle process");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testtag_check_comments_2()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Check that HTML-style comments are processed correctly, specified in  the Java Server Pages Specification v1.2, Sec 10.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/LifeCycle/tag_check_comments_2.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/LifeCycle/tag_check_comments_2.jsp HTTP/1.0");
    httpAssert.setTestName("tag_check_comments_2Test");
    httpAssert.setTestStrategy(
        "Testing LifeCycle process");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testtag_doEndTag_skip_page()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for doEndTag SKIP_PAGE, specified in the Java Server  Pages Specification v1.2, Sec 10.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/LifeCycle/tag_doEndTag_skip_page.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/LifeCycle/tag_doEndTag_skip_page.jsp HTTP/1.0");
    httpAssert.setTestName("tag_doEndTag_skip_pageTest");
    httpAssert.setTestStrategy(
        "Testing LifeCycle process");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testtag_doStart_eval_body_include()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for doStartTag EVAL_BODY_INCLUDE, specified in the Java  Server Pages Specification v1.2, Sec 10.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/LifeCycle/tag_doStart_eval_body_include.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/LifeCycle/tag_doStart_eval_body_include.jsp HTTP/1.0");
    httpAssert.setTestName("tag_doStart_eval_body_includeTest");
    httpAssert.setTestStrategy(
        "Testing LifeCycle process");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testtag_doStart_skip_body()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for doStartTag SKIP_BODY, specified in the Java Server  Pages Specification v1.2, Sec 10.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/LifeCycle/tag_doStart_skip_body.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/LifeCycle/tag_doStart_skip_body.jsp HTTP/1.0");
    httpAssert.setTestName("tag_doStart_skip_bodyTest");
    httpAssert.setTestStrategy(
        "Testing LifeCycle process");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testtag_translationtime_verify_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for translation-time verification, specified in the  Java Server Pages Specification v1.2, Sec 10.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/LifeCycle/tag_translationtime_verify_1.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/LifeCycle/tag_translationtime_verify_1.jsp HTTP/1.0");
    httpAssert.setTestName("tag_translationtime_verify_1Test");
    httpAssert.setTestStrategy(
        "Testing LifeCycle process");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpersistentValues()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Tests if the attribute values are retaines when tags are nested inside a tag  Java Server Pages Specification v1.2, Sec 10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/misc/persistentValues.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/misc/persistentValues.jsp HTTP/1.0");
    httpAssert.setTestName("persistentValuesTest");
    httpAssert.setTestStrategy(
        "testing persistence of tag attributes");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testrequest_time_attributes()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Tests the evaluation order of the expressions if attribute values are runtime expressions Java Server Pages Specification v1.2, Sec 2.13.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/misc/request_time_attributes.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/misc/request_time_attributes.jsp HTTP/1.0");
    httpAssert.setTestName("request_time_attributesTest");
    httpAssert.setTestStrategy(
        "Testing associativity of expression evaluation");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveImplicitJarMultiTld()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Verify that the container, with no explicit taglib mappings within the web.xml, can recognize multiple tlds stored in a jar, and create the appropriate  mapping so that the client can use the tags. JavaServer Pages Specification 1.2 Sections: JSP 7.3.4 JSP 7.3.8");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/TldPathResolution/implicit/positiveImplicitJarMultiTld.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TldPathResolution/implicit/positiveImplicitJarMultiTld.jsp HTTP/1.0");
    httpAssert.setTestName("positiveImplicitJarMultiTldTest");
    httpAssert.setTestStrategy(
        "Define two distinct tags for this page, and use the tags so that output will be generated.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveAbsTld()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Verify that the requested tld, using an absoulte URI,  resolves to the appropriate tld. JavaServer Pages Specification 1.2 Sections: JSP 7.3.2 JSP 7.3.3 JSP 7.3.6  JSP 7.3.6.1 JSP 7.3.6.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TldPathResolution/explicit/positiveAbsTld.jsp HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("positiveAbsTldTest");
    httpAssert.setTestStrategy(
        "Using a taglib directive, set the uri attribute to an absolute URI.  If successful, no fatal translation error should occur.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveRelTld()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Verify that the requested tld, using a relative URI,  resolves to the appropriate tld. JavaServer Pages Specification 1.2 Sections: JSP 7.3.2 JSP 7.3.3 JSP 7.3.6  JSP 7.3.6.1 JSP 7.3.6.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/TldPathResolution/explicit/positiveRelTld.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TldPathResolution/explicit/positiveRelTld.jsp HTTP/1.0");
    httpAssert.setTestName("positiveRelTldTest");
    httpAssert.setTestStrategy(
        "Using a taglib directive, set the uri attribute to a relative URI.  Verify that the tag can be successfully used.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveAbsJar()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Verify that the requested tld, using an absoulte URI,  resolves to jar which in turn resolves to a single TLD  (taglib.tld) within the jar. JavaServer Pages Specification 1.2 Sections: JSP 7.3.2 JSP 7.3.3 JSP 7.3.6  JSP 7.3.6.1 JSP 7.3.6.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TldPathResolution/explicit/positiveAbsJar.jsp HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("positiveAbsJarTest");
    httpAssert.setTestStrategy(
        "Using a taglib directive, set the uri attribute to an absolute URI.  If successful, no fatal translation error should occur.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveRelJar()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Verify that the requested tld, using a relative URI,  resolves to a jar which in ture resolves to a single TLD (taglib.tld) within the jar. JavaServer Pages Specification 1.2 Sections: JSP 7.3.2 JSP 7.3.3 JSP 7.3.6  JSP 7.3.6.1 JSP 7.3.6.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/TldPathResolution/explicit/positiveRelJar.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TldPathResolution/explicit/positiveRelJar.jsp HTTP/1.0");
    httpAssert.setTestName("positiveRelJarTest");
    httpAssert.setTestStrategy(
        "Using a taglib directive, set the uri attribute to a relative URI.  Verify that the tag can be successfully used.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testpositiveDirectTldReference()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Verify that a tag can be used when the URI of the taglib directive refers to the TLD directly. JavaServer Pages Specification 1.2 Sections: JSP 7.3.6.3");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/TldPathResolution/explicit/positiveDirectTldReference.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TldPathResolution/explicit/positiveDirectTldReference.jsp HTTP/1.0");
    httpAssert.setTestName("positiveDirectTldReferenceTest");
    httpAssert.setTestStrategy(
        "Using a taglib directive, reference the desired TLD directly.  Verify that the tag can be successfully used.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testnegativeAbsFatalTranslationError()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Verify that a fatal translation error is generated when using an absoulte URI,  fails to resolve to a tld. JavaServer Pages Specification 1.2 Sections: JSP 7.3.6.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TldPathResolution/explicit/negativeAbsFatalTranslationError.jsp HTTP/1.0");
    httpAssert.setReturnCode("500");
    httpAssert.setTestName("negativeAbsFatalTranslationErrorTest");
    httpAssert.setTestStrategy(
        "Using a taglib directive, set the uri attribute to an incorrect absolute URI. A fatal translation error should occur.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testprecompileNoValue()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "If the jsp-tests request parameter has no value, the request will not be delivered to the target JSP page. JavaServer Pages Specification v1.2, Sec. 8.4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/misc/precompilation/precompile.jsp?jsp_precompile HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("precompileNoValueTest");
    httpAssert.setTestStrategy(
        "Validate that no response body is returned when jsp-tests has no value.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testprecompileFalse()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "If the jsp-tests request parameter is set to false, the request will not be delivered to the target JSP page. JavaServer Pages Specification v1.2, Sec. 8.4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/misc/precompilation/precompile.jsp?jsp_precompile=false HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("precompileFalseTest");
    httpAssert.setTestStrategy(
        "Validate that no response body is returned when jsp-tests is set to false.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testprecompileTrue()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "If the jsp-tests request parameter is set to true, the request will not be delivered to the target JSP page. JavaServer Pages Specification v1.2, Sec. 8.4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/misc/precompilation/precompile.jsp?jsp_precompile=true HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("precompileTrueTest");
    httpAssert.setTestStrategy(
        "Validate that no response body is returned when jsp-tests is set to true.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testprecompileNegative()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Valid parameter values for jsp-tests are 'true', 'false', and no value.  Any other value will result in an HTTP Error; 500 (Server Error). JavaServer Pages specification v1.2, Sec. 8.4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");

    httpAssert.setLastTask(true);

    httpAssert.setRequest(
        "GET /jsp-tests/jsp/misc/precompilation/precompile.jsp?jsp_precompile=any_invalid_value HTTP/1.0");
    httpAssert.setReturnCode("500");
    httpAssert.setTestName("precompileNegativeTest");
    httpAssert.setTestStrategy(
        "Set the jsp-tests request paramter to a non valid value and validate that a 500 error occurs.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

// jsp xml tests
/**
 *  When the contentType attribute of the page  directive is specified, it will set the character
 *  encoding and MIME type in the response to the client.
 *  JavaServer Pages Specification v1.2, Sec. 2.10.1
 *
 * Strategy: Using the page directive, set the  contentType attribute to
 *    'text/plain;charset=ISO-8859-1'.
 * Verify on the client side that the Content-Type header was properly set in the  response.

 *
 * @throws Exception if anything goes wrong
 */
@Test
  public void testJspXmlpositiveContentType() throws Exception {
    HttpRequestAsserter get = createGetAssertion("/jsp-tests/jsp/core_syntax/directives/page/content/positiveContenttypeXML.jsp");
    get.setExpectedContentType("text/plain");
    get.setExpectedResponseCode(200);
    get.setExpectedContentType("text/plain");
    get.setExpectedContentTypeCharset("ISO-8859-1");
    get.connectToServerAndAssert();
  }

  @Test
  public void testJspXmlnegativeDuplicateContentFatalTranslationError()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Duplicate contentType attribute/values within a given translation unit shall result in a fatal translation error. JavaServer Pages Specification v1.2, Sec. 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/content/negativeDuplicateContentFatalTranslationErrorXML.jsp HTTP/1.0");
    httpAssert.setReturnCode("500");
    httpAssert.setTestName("negativeDuplicateContentFatalTranslationErrorTest");
    httpAssert.setTestStrategy(
        "Declare a page directive with contentType attributes specified.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveExtends()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The extends attribute of the page directive identifies a fully qualfied class name into which the JSP page transformed. JavaServer Pages Specification v1.2, Sec. 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/directives/page/extend/positiveExtends.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/extend/positiveExtendsXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveExtendsTest");
    httpAssert.setTestStrategy(
        "Provide the extends attribute with a fully qualified class.  The resulting JSP implementation class will use instanceof to validate that this page instance is an instance of the class that it extends.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlnegativeDuplicateExtendsFatalTranslationError()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Duplicate extends attributes within a given translation unit shall result in a fatal translation error. JavaServer Pages Specification v1.2, Sec. 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/extend/negativeDuplicateExtendsFatalTranslationErrorXML.jsp HTTP/1.0");
    httpAssert.setReturnCode("500");
    httpAssert.setTestName("negativeDuplicateExtendsFatalTranslationErrorTest");
    httpAssert.setTestStrategy(
        "Declare a page directive with two extends attributes. Validate that a fatal translation error occurs.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveDefaultIsErrorPage()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The default value of the 'isErrorPage' attribute is false.  JavaServer Pages Specification v1.2, Sec. 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/errorpage/positiveDefaultIsErrorPageXML.jsp HTTP/1.0");
    httpAssert.setReturnCode("500");
    httpAssert.setTestName("positiveDefaultIsErrorPageTest");
    httpAssert.setTestStrategy(
        "Verify that the 'isErrorPage' attribute is false by  generating an exception in the called page and then have the error page attempt to access the implicit exception object.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveErrorPage()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "When the isErrorPage attribute is set to true, the implicit exception object will be available and its value is a reference to the offending throwable from the source JSP page in error.  JavaServer Pages Specification v1.2, Sec 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/directives/page/errorpage/positiveErrorPage.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/errorpage/positiveErrorPageXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveErrorPageTest");
    httpAssert.setTestStrategy(
        "In the initial JSP page, generate a java.lang.Arithmetic Exception by dividing an int value by 0.  Validate the Exception type by using instanceof against the exception object.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlnegativeFatalTranslationError()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "A fatal translation error shall result if a JSP error page has the isErrorPage attribute set to false and an attempt is made to access the implicit exception object. JavaServer Pages Specification v1.2, Sec 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/errorpage/negativeFatalTranslationErrorXML.jsp HTTP/1.0");
    httpAssert.setReturnCode("500");
    httpAssert.setTestName("negativeFatalTranslationErrorTest");
    httpAssert.setTestStrategy(
        "Generate an exception from the requested page with the errorPage attribute set.  The Error page has isErrorPage set to false and will attempt to access the exception object.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlnegativeDuplicateErrorPageFatalTranslationError()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Duplicate errorPage attributes/values within a given translation unit shall result in a fatal translation error. JavaServer Pages Specification v1.2, Sec 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/errorpage/negativeDuplicateErrorPageFatalTranslationErrorXML.jsp HTTP/1.0");
    httpAssert.setReturnCode("500");
    httpAssert.setTestName(
        "negativeDuplicateErrorPageFatalTranslationErrorTest");
    httpAssert.setTestStrategy(
        "Declare a page directive with two errorPage attributes. Validate that a fatal translation error occurs.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveImport()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The import attribute of the page directive  denotes classes that the translated JSP page will import and thus making these classes available to the scripting environment. JavaServer Pages Specification v1.2, Sec. 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/directives/page/imports/positiveImport.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/imports/positiveImportXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveImportTest");
    httpAssert.setTestStrategy(
        "Use the import attribute to import 'java.util.Properties'.  Validated that a Properties object can be created and used.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlimplicitImportLang()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Translated JSP pages must automatically import classes of the java.lang package. JavaServer Pages Specification v1.2, Sec. 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/directives/page/imports/implicitImportLang.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/imports/implicitImportLangXML.jsp HTTP/1.0");
    httpAssert.setTestName("implicitImportLangTest");
    httpAssert.setTestStrategy(
        "Validate that classes from the java.lang  package are implicitly imported by creating and using a java.lang.Integer object.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlimplicitImportJsp()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Translated JSP pages must automatically import classes of the javax.servlet.jsp package. JavaServer Pages Specification v1.2, Sec. 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/directives/page/imports/implicitImportJsp.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/imports/implicitImportJspXML.jsp HTTP/1.0");
    httpAssert.setTestName("implicitImportJspTest");
    httpAssert.setTestStrategy(
        "Validate that classes from the javax.servlet.jsp package are implicitly imported by calling  JspFactory.getDefaultFactory() method.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlimplicitImportServlet()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Translated JSP pages must automatically import classes of the javax.servlet package. JavaServer Pages Specification v1.2, Sec. 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/directives/page/imports/implicitImportServlet.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/imports/implicitImportServletXML.jsp HTTP/1.0");
    httpAssert.setTestName("implicitImportServletTest");
    httpAssert.setTestStrategy(
        "Validate that classes from the javax.servlet package are implicitly imported by creating  and using an instance of RequestDispatcher.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlimplicitImportHttp()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Translated JSP pages must automatically import classes of the javax.servlet.http package. JavaServer Pages Specification v1.2, Sec. 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/directives/page/imports/implicitImportHttp.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/imports/implicitImportHttpXML.jsp HTTP/1.0");
    httpAssert.setTestName("implicitImportHttpTest");
    httpAssert.setTestStrategy(
        "Validate that classes from the javax.servlet.http package are implicitly imported by creating  and using an instance of HttpUtils.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveMultipleImport()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "A fatal translation error shall result if there is more than one occurrence of a page directive attribute with the exception of the import attribute. JavaServer Pages Specification v1.2, Sec. 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/imports/positiveMultipleImportXML.jsp HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("positiveMultipleImportTest");
    httpAssert.setTestStrategy(
        "Declare a page directive with two import attributes.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveInfo()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The arbitrary string incorporated into the translated page by using the info attribute of the page directive, is available via Servlet.getServletInfo(); JavaServer Pages Specification v1.2, Sec 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/directives/page/info/positiveInfo.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/info/positiveInfoXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveInfoTest");
    httpAssert.setTestStrategy(
        "Set the info attribute of the page directive. Call getServletInfo().");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlnegativeDuplicateInfoFatalTranslationError()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Duplicate info attributes within a given translation unit shall result in a fatal translation error. JavaServer Pages Specification v1.2, Sec 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/info/negativeDuplicateInfoFatalTranslationErrorXML.jsp HTTP/1.0");
    httpAssert.setReturnCode("500");
    httpAssert.setTestName("negativeDuplicateInfoFatalTranslationErrorTest");
    httpAssert.setTestStrategy(
        "Declare a page directive with two info attributes. Validate that a fatal translation error occurs.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveLang()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The only defined and required scripting language value for the language attribute is 'java' JavaServer Pages Specification v1.2, Sec. 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/directives/page/language/positiveLang.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/language/positiveLangXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveLangTest");
    httpAssert.setTestStrategy(
        "Validate that the language attribute can be set to 'java' without an error.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlnegativeDuplicateLanguageFatalTranslationError()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Duplicate language attributes within a given translation unit shall result in a fatal translation error. JavaServer Pages Specification v1.2, Sec. 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/language/negativeDuplicateLanguageFatalTranslationErrorXML.jsp HTTP/1.0");
    httpAssert.setReturnCode("500");
    httpAssert.setTestName(
        "negativeDuplicateLanguageFatalTranslationErrorTest");
    httpAssert.setTestStrategy(
        "Declare a page directive with two language attributes. Validate that a fatal translation error occurs.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveSession()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "If the session attribute of the page directive  is set to 'true', then the implicit script  language variable named 'session' of type javax.servlet.http.HttpSession references the current/new session for the page JavaServer Pages Specification v1.2, Sec. 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/session/positiveSessionXML.jsp HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("positiveSessionTest");
    httpAssert.setTestStrategy(
        "Set the session attribute to 'true' and validate that the implicit session variable can be accessed and used.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveSessionDefault()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The implicit session session variable will be available to the page by default as the default value for the session attribute is 'true' JavaServer Pages Specification v1.2, Sec. 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/session/positiveSessionDefaultXML.jsp HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("positiveSessionDefaultTest");
    httpAssert.setTestStrategy(
        "Do not set the session attribute in the page. Validate that the implicit session variable can be accessed and used.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlnegativeSessionFatalTranslationError()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "When the session attribute is set to 'false', the JSP page does not participate in a session; the implicit session variable is unavailable, and any reference to it within the body of the JSP page is illegal and shall result in a fatal translation error. JavaServer Pages Specification v1.2, Sec. 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/session/negativeSessionFatalTranslationErrorXML.jsp HTTP/1.0");
    httpAssert.setReturnCode("500");
    httpAssert.setTestName("negativeSessionFatalTranslationErrorTest");
    httpAssert.setTestStrategy(
        "Validate that setting the session attribute to false will result in a fatal translation error if the  implicit session variable is referenced.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlnegativeDuplicateSessionFatalTranslationError()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Duplicate session attributes within a given translation unit shall result in a fatal translation error. JavaServer Pages Specification v1.2, Sec 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/session/negativeDuplicateSessionFatalTranslationErrorXML.jsp HTTP/1.0");
    httpAssert.setReturnCode("500");
    httpAssert.setTestName("negativeDuplicateSessionFatalTranslationErrorTest");
    httpAssert.setTestStrategy(
        "Declare a page directive with two session attributes. Validate that a fatal translation error occurs.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveBuffAutoflush()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "When the page buffer is full, content will automatically be flushed. JavaServer Pages Specification v1.2, Sec. 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/buffer/positiveBuffAutoflushXML.jsp HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("positiveBuffAutoflushTest");
    httpAssert.setTestStrategy(
        "Leaving the defaults for autoFlush and buffer, validate that the buffer is automatically flushed once the  buffer is full.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveBuffCreate()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "If the buffer attribute is set, it is legal to set autoFlush to 'false'. Doing so requires a manual flush from the page writer. JavaServer Pages Specification v1.2, Sec. 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/buffer/positiveBuffCreateXML.jsp HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("positiveBuffCreateTest");
    httpAssert.setTestStrategy(
        "Validate that the page can configure a buffer and set the autoFlush attribute to false.   Write data to the output stream and manually  flush the content");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlnegativeDuplicateBufferFatalTranslationError()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Duplicate buffer attributes within a given translation unit results in a fatal translation error. JavaServer Pages v1.2, Sec 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/buffer/negativeDuplicateBufferFatalTranslationErrorXML.jsp HTTP/1.0");
    httpAssert.setReturnCode("500");
    httpAssert.setTestName("negativeDuplicateBufferFatalTranslationErrorTest");
    httpAssert.setTestStrategy(
        "Declare a page directive with two buffer attributes.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlnegativeBufferOverflowException()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "If the 'autoFlush' attribute is false, an Exception will be raised if an overflow occurs. JavaServer Pages v1.2, Sec 2.10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/directives/page/buffer/negativeBufferOverflowException.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/page/buffer/negativeBufferOverflowExceptionXML.jsp HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("negativeBufferOverflowExceptionTest");
    httpAssert.setTestStrategy(
        "Declare a page directive with autoFlush set to false.   Overflow the buffer and verify the Exception is caught.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveIncludeCtxRelativeDirective()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The include directive inserts the text of the resource specified by the file attribute.  Included content can be referenced using a context-relative path. JavaServer Pages Specification v1.2, Sec 2.10.3");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/directives/include/positiveIncludeCtxRelativeDirective.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/include/positiveIncludeCtxRelativeDirectiveXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveIncludeCtxRelativeDirectiveTest");
    httpAssert.setTestStrategy(
        "Using an include directive, include content referenced by a context-relative path.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveIncludePageRelativeDirective()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The include directive inserts the text of the resource specified by the file attribute.  Included content can be referenced using a page-relative path. JavaServer Pages Specification v1.2, Sec 2.10.3");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/directives/include/positiveIncludePageRelativeDirective.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/directives/include/positiveIncludePageRelativeDirectiveXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveIncludePageRelativeDirectiveTest");
    httpAssert.setTestStrategy(
        "Using an include directive, include content referenced by a page-relative path.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveDeclaration()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Declarations should be a complete declarative statement,  or sequence thereof, according to the syntax of the  scripting language specified. JavaServer Pages Specification v1.2, Sec. 2.11.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/scripting/declaration/positiveDeclaration.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/scripting/declaration/positiveDeclarationXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveDeclarationTest");
    httpAssert.setTestStrategy(
        "Validate the scripting declarations are properly recognized, by declaring and assigning a value to an int variable, and displaying the value of the variable.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveExpr()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "An expression element in a JSP page is a scripting  language expression that is evaluated and the result is coerced to a String.  The result is subsequently emitted into the current out JspWriter object. JavaServer Pages Specification v1.2, Sec. 2.11.3");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/scripting/expressions/positiveExpr.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/scripting/expressions/positiveExprXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveExprTest");
    httpAssert.setTestStrategy(
        "Validate that the container can correctly support a basic expression by validating the output returned.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveExprComment()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Expressions can be embedded in HTML comments to produce comments returned in the output stream containing dynamic content. JavaServer Pages Specification v1.2, Sec. 2.5.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/scripting/expressions/positiveExprComment.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/scripting/expressions/positiveExprCommentXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveExprCommentTest");
    httpAssert.setTestStrategy(
        "Validate that an HTML stye comment with an embedded expression returns the value of the expression within the comment.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveExprWhiteSpace()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Whitespace is optional after the <%= and before the %> delimiters of the expression element. JavaServer Pages Specification v1.2, Sec. 2.11");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/scripting/expressions/positiveExprWhiteSpace.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/scripting/expressions/positiveExprWhiteSpaceXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveExprWhiteSpaceTest");
    httpAssert.setTestStrategy(
        "Validate that the container correctly handles different whitespace values with an expression element.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveScriptlet()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "All scriptlet fragments in a given translation  unit are combined in the order they appear in  the JSP page, they must yield a valid statement,  or sequence of statements, in the specified  scripting language. JavaServer Pages Specification v1.2, Sec. 2.11.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/scripting/scriptlet/positiveScriptlet.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/scripting/scriptlet/positiveScriptletXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveScriptletTest");
    httpAssert.setTestStrategy(
        "Correct syntax is used in the scriptlet");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlcheckSession()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The session scripting variable is implictly made made available to the scripting environment and and is associated with an object of type javax.servlet.http.HttpSession.  JavaServer Pages Specification v1.2, Sec. 2.8.3");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/implicitobjects/checkSession.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/implicitobjects/checkSessionXML.jsp HTTP/1.0");
    httpAssert.setTestName("checkSessionTest");
    httpAssert.setTestStrategy(
        "Validate that the object associated with the  session scripting variable is of type  javax.servlet.http.HttpSession and that a method can be called against it.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlcheckConfig()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The config scripting variable is implictly made made available to the scripting environment and and is associated with an object of type javax.servlet.ServletConfig.  JavaServer Pages Specification v1.2, Sec. 2.8.3");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/implicitobjects/checkConfig.html");


    httpAssert.setRequest(
        "GET /jsp-tests/CheckConfig HTTP/1.0");
    httpAssert.setTestName("checkConfigTest");
    httpAssert.setTestStrategy(
        "Validate that the object associated with the  config scripting variable is of type  javax.servlet.ServletConfig and that a method can be called against it.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlcheckException()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The exception scripting variable is implictly made made available to the scripting environment (if the JSP page is an error page) and is associated with  an object of type java.lang.Throwable.  JavaServer Pages Specification v1.2, Sec. 2.8.3");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/implicitobjects/checkException.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/implicitobjects/checkExceptionXML.jsp HTTP/1.0");
    httpAssert.setTestName("checkExceptionTest");
    httpAssert.setTestStrategy(
        "Validate that the object associated with the  exception scripting variable is of an instance of the exception type thrown (a subclass of java.lang.Throwable).");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlcheckOut()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The out scripting variable is implictly made made available to the scripting environment and and is associated with an object of type javax.servlet.jsp.JspWriter.  JavaServer Pages Specification v1.2, Sec. 2.8.3");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/implicitobjects/checkOut.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/implicitobjects/checkOutXML.jsp HTTP/1.0");
    httpAssert.setTestName("checkOutTest");
    httpAssert.setTestStrategy(
        "Validate that the object associated with the  out scripting variable is of type  javax.servlet.jsp.JspWriter.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlcheckPage()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The page scripting variable is implictly made made available to the scripting environment and and is associated with an object of type java.lang.Object.  JavaServer Pages Specification v1.2, Sec. 2.8.3");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/implicitobjects/checkPage.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/implicitobjects/checkPageXML.jsp HTTP/1.0");
    httpAssert.setTestName("checkPageTest");
    httpAssert.setTestStrategy(
        "Validate that the object associated with the  page scripting variable is of type  java.lang.Object.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlcheckPageContext()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The pageContext scripting variable is implictly made made available to the scripting environment and and is associated with an object of type javax.servlet.jsp.PageContext.  JavaServer Pages Specification v1.2, Sec. 2.8.3");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/implicitobjects/checkPageContext.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/implicitobjects/checkPageContextXML.jsp HTTP/1.0");
    httpAssert.setTestName("checkPageContextTest");
    httpAssert.setTestStrategy(
        "Validate that the object associated with the  pageContext scripting variable is of type  javax.servlet.jsp.PageContext and that a method can be called against it.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlcheckRequest()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The request scripting variable is implictly made made available to the scripting environment and and is associated with an object of type javax.servlet.http.HttpServletRequest or javax.servlet.ServletRequest (protocol dependant).  JavaServer Pages Specification v1.2, Sec. 2.8.3");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/implicitobjects/checkRequest.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/implicitobjects/checkRequestXML.jsp?Years=2 HTTP/1.0");
    httpAssert.setTestName("checkRequestTest");
    httpAssert.setTestStrategy(
        "Validate that the object associated with the  request scripting variable is of type  javax.servlet.Request (parent class of HttpServletRequest) and that a method can be called against it.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlcheckResponse()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The response scripting variable is implictly made made available to the scripting environment and and is associated with an object of type javax.servlet.http.HttpServletResponse or javax.servlet.ServletResponse (protocol dependant).  JavaServer Pages Specification v1.2, Sec. 2.8.3");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setExpectHeaders("TestHeader:Method call OK");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/implicitobjects/checkResponseXML.jsp HTTP/1.0");
    httpAssert.setTestName("checkResponseTest");
    httpAssert.setTestStrategy(
        "Validate that the object associated with the  response scripting variable is of type  javax.servlet.Response (parent class of HttpServletResponse) and that a method can be called against it.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlcheckApplication()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The application scripting variable is implictly made made available to the scripting environment and and is associated with an object of type javax.servlet.ServletContext.  JavaServer Pages Specification v1.2, Sec. 2.8.3");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/implicitobjects/checkApplication.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/implicitobjects/checkApplicationXML.jsp HTTP/1.0");
    httpAssert.setTestName("checkApplicationTest");
    httpAssert.setTestStrategy(
        "Validate that the object associated with the  application scripting variable is of type  javax.servlet.ServletContext that a method can be called against it.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveSetBooleanObj()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Boolean properties of a Bean can be set with a String  Constant.  Conversion from String to Boolean will be automatically provided by java.lang.Boolean.valueOf(String). JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetBooleanObj.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetBooleanObjXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetBooleanObjTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance and set a Boolean property of the bean using a String constant.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveSetBooleanPrim()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Primitive boolean properties of a Bean can be set with a String  Constant.  Conversion from String to boolean will be automatically provided by java.lang.Boolean.valueOf(String). JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetBooleanPrim.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetBooleanPrimXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetBooleanPrimTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance and set a boolean property of the bean using a String constant.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveSetByteObj()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Byte properties of a Bean can be set with a String  Constant.  Conversion from String to Byte will be automatically provided by java.lang.Byte.valueOf(String). JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetByteObj.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetByteObjXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetByteObjTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance and set a Byte property of the bean using a String constant.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveSetBytePrim()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Primitive byte properties of a Bean can be set with a String  Constant.  Conversion from String to byte will be automatically provided by java.lang.Byte.valueOf(String). JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetBytePrim.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetBytePrimXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetBytePrimTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance and set a byte property of the bean using a String constant.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveSetCharObj()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Character properties of a Bean can be set with a String  Constant.  Conversion from String to Character will be automatically provided by String.charAt(0). JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetCharObj.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetCharObjXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetCharObjTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance and set a Character property of the bean using a String constant.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveSetCharPrim()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Primitive char properties of a Bean can be set with a String  Constant.  Conversion from String to char will be automatically provided by String.charAt(0). JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetCharPrim.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetCharPrimXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetCharPrimTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance and set a char property of the bean using a String constant.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveSetDoubleObj()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Double properties of a Bean can be set with a String  Constant.  Conversion from String to Double will be automatically provided by java.lang.Double.valueOf(String). JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetDoubleObj.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetDoubleObjXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetDoubleObjTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance and set a Double property of the bean using a String constant.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveSetDoublePrim()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Primitive double properties of a Bean can be set with a String  Constant.  Conversion from String to double will be automatically provided by java.lang.Double.valueOf(String). JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetDoublePrim.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetDoublePrimXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetDoublePrimTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance and set a double property of the bean using a String constant.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveSetFloatObj()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Float properties of a Bean can be set with a String  Constant.  Conversion from String to Float will be automatically provided by java.lang.Float.valueOf(String). JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetFloatObj.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetFloatObjXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetFloatObjTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance and set a Float property of the bean using a String constant.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveSetFloatPrim()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Primitive float properties of a Bean can be set with a String  Constant.  Conversion from String to float will be automatically provided by java.lang.Float.valueOf(String). JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetFloatPrim.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetFloatPrimXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetFloatPrimTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance and set a float property of the bean using a String constant.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveSetIndexedProp()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Indexed properties in a Bean can be set using jsp:setProperty. When assigning values to indexed properties, the value must be an array.  No Type converstions are applied during assigment. JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetIndexedProp.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetIndexedPropXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetIndexedPropTest");
    httpAssert.setTestStrategy(
        "Create a bean using useBean tag, use setProperty and set properties using the following array types:  byte char short int float long double boolean Byte Character Short Integer Float Long Double Boolean Access each of the properties via scripting, iterate through the array, and display the values.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveSetIntObj()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Integer properties of a Bean can be set with a String  Constant.  Conversion from String to Integer will be automatically provided by java.lang.Integer.valueOf(String). JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetIntObj.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetIntObjXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetIntObjTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance and set an Integer property of the bean using a String constant.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveSetIntPrim()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Primitive int properties of a Bean can be set with a String  Constant.  Conversion from String to int will be automatically provided by java.lang.Integer.valueOf(String). JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetIntPrim.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetIntPrimXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetIntPrimTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance and set an int property of the bean using a String constant.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveSetLongObj()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Long properties of a Bean can be set with a String  Constant.  Conversion from String to Long will be automatically provided by java.lang.Long.valueOf(String). JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetLongObj.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetLongObjXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetLongObjTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance and set an Long property of the bean using a String constant.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveSetLongPrim()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Primitive long properties of a Bean can be set with a String  Constant.  Conversion from String to long will be automatically provided by java.lang.long.valueOf(String). JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetLongPrim.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetLongPrimXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetLongPrimTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance and set a long property of the bean using a String constant.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveSetPropAll()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "If the property attribute of jsp:setProperty is set to '*', the tag will iterate over the current Servlet request parameters, matching parameter names and value type(s) to property names and setter method types(s), setting each matched property to the value of the matching parameter. JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetPropAll.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetPropAllXML.jsp?name=Frodo&num=116165&str=Validated HTTP/1.0");
    httpAssert.setTestName("positiveSetPropAllTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance and set the property attribute to '*'.  The following properties should be set by the tag: name, num, str.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveSetPropNoParam()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "When the param attribute is omitted, the request parameter name is assumed to be the same as the Bean property name. JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetPropNoParam.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetPropNoParamXML.jsp?str=SAPPOTA HTTP/1.0");
    httpAssert.setTestName("positiveSetPropNoParamTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance. jsp:setProperty only specifies the name and property properties.  The container should set the value of the Bean's property to the value of the request parameter that has the same name as specified by the property attribute.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveSetPropParam()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "A single bean property can be set using a request  parameter from the Request object.  JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetPropParam.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetPropParamXML.jsp?Name=MANGO HTTP/1.0");
    httpAssert.setTestName("positiveSetPropParamTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance. jsp:setProperty only specifies the param property. The container should set the value of the Bean's property to the value of the request parameter that has the same name as specified by the param attribute.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveSetPropReqTimeSingleQuotes()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The value attribute can accept request-time  attribute expressions (single-quoted) as a value. JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetPropReqTimeSingleQuotes.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetPropReqTimeSingleQuotesXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetPropReqTimeSingleQuotesTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance. Set the value of a bean property using a  request-time attribute expression delimited by single quotes.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveSetPropReqTimeDoubleQuotes()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The value attribute can accept request-time  attribute expressions (double-quoted) as a value. JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetPropReqTimeDoubleQuotes.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetPropReqTimeDoubleQuotesXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetPropReqTimeDoubleQuotesTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance. Set the value of a bean property using a  request-time attribute expression delimited by double quotes.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveSetPropValue()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Properties in bean can be set using a String Constant. JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveSetPropValue.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveSetPropValueXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetPropValueTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new bean instance. Set the value of a bean property using the value attribute.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveBeanPropertyEditor()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The container will use a bean's  property editor when setting properties, if  introspection indicates one exists. JavaServer Pages Specification v1.2, Sec. 4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/setProperty/positiveBeanPropertyEditor.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/setProperty/positiveBeanPropertyEditorXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveBeanPropertyEditorTest");
    httpAssert.setTestStrategy(
        "Create a bean using useBean tag, use setProperty and  and verfiy results using getProperty.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveGetProps()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "jsp:getProperty action places the value of a Bean instance property, converted to a String, into the implicit out  object, which is displayed as output. Java Server Pages Specification v1.2, Sec 4.3");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/getProperty/positiveGetProps.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/getProperty/positiveGetPropsXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveGetPropsTest");
    httpAssert.setTestStrategy(
        "Create a bean using jsp:useBean tag, use jsp:getProperty to  access and validate the property.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlnegativeGetPropObjectNotFoundException()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "If accessing a property, and the object is not found, a request-time exception is raised. Java Server Pages Specification v1.2, Sec 4.3");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/getProperty/negativeGetPropObjectNotFoundException.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/getProperty/negativeGetPropObjectNotFoundExceptionXML.jsp HTTP/1.0");
    httpAssert.setTestName("negativeGetPropObjectNotFoundExceptionTest");
    httpAssert.setTestStrategy(
        "Access a property of a non-existant bean and catch an exception.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveBeanNameType()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "A bean can be declared using the beanName and type attributes, where type is the same class specified by the beanName attribute. JavaServer Pages Specification v1.2, Sec. 4.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/useBean/positiveBeanNameType.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/useBean/positiveBeanNameTypeXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveBeanNameTypeTest");
    httpAssert.setTestStrategy(
        "Use jsp:useBean to create a bean where the beanName and type attributes have the same values.  Verify that the bean can be used by invoking a method on the bean inside a scriplet.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveBeanNameTypeCast()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "A bean can be declared using the beanName and type attributes, where type is a superclass of the class specified by the beanName attribute. JavaServer Pages Specification v1.2, Sec. 4.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/useBean/positiveBeanNameTypeCast.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/useBean/positiveBeanNameTypeCastXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveBeanNameTypeCastTest");
    httpAssert.setTestStrategy(
        "Use jsp:useBean to create a bean where the beanName specifies one particular type, and type specifies a superclass of the value specified by beanName.  Verify that the bean can be used by invoking a method on the bean inside a scriplet.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveBeanNameSerialized()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The container must be able to instantiate  a serialized bean specified in the beanName  attribute of jsp:useBean.  JavaServer Pages Specification v1.2, JSP 4.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/useBean/positiveBeanNameSerialized.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/useBean/positiveBeanNameSerializedXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveBeanNameSerializedTest");
    httpAssert.setTestStrategy(
        "Create a bean using useBean action where beanName refers to a serialized bean  and call a method on the bean to verify  that the serialized instance returns an expected value.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveBodyNew()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Bean properties can be set within the body of the jsp:useBean action. JavaServer Pages Specification v1.2, Sec 4.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/useBean/positiveBodyNew.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/useBean/positiveBodyNewXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveBodyNewTest");
    httpAssert.setTestStrategy(
        "Using jsp:useBean, create a new instance. Within the body of the jsp:useBean action, use jsp:setProperty to initialize a Bean property.  After closing the jsp:useBean action, use jsp:getProperty to validate  that the property was indeed set.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositivePageScopedObject()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Bean objects created with 'page' scope, will be available for the current page only.  The reference to the bean must be discarded upon completion of the current request by the page body. JavaServer Pages Specfication v1.2, Sec. 4.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setExpectHeaders("status:Test Status=PASSED");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/useBean/positivePageScopedObjectXML.jsp HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("positivePageScopedObjectTest");
    httpAssert.setTestStrategy(
        "In one JSP page, create a new bean object using jsp:useBean with the scope set to 'page'.  After the object has been created, forward the request to a second JSP page to validate that an object associated with the same ID used in the first JSP page is not available in the current PageContext.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveRequestScopedObject()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Bean objects created with 'request' scope, will be available in the current page's ServletRequest object. JavaServer Pages Specfication v1.2, Sec. 4.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setExpectHeaders("status:Test Status=PASSED");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/useBean/positiveRequestScopedObjectXML.jsp HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("positiveRequestScopedObjectTest");
    httpAssert.setTestStrategy(
        "In one JSP page, create a new bean object using jsp:useBean with the scope set to 'request'.  After the object has been created, forward the request to a second JSP page to validate that an object associated with the same ID used in the first JSP page is available in the current HttpServletRequest.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveSessionScopedObject()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Bean objects created with 'session' scope, will be available in the current page's HttpSession object. JavaServer Pages Specfication v1.2, Sec. 4.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setExpectHeaders("status:Test Status=PASSED");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/useBean/positiveSessionScopedObjectXML.jsp HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("positiveSessionScopedObjectTest");
    httpAssert.setTestStrategy(
        "In one JSP page, create a new bean object using jsp:useBean with the scope set to 'session'.  After the object has been created, forward the request to a second JSP page to validate that an object associated with the same ID used in the first JSP page is available in the current HttpSession.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveApplicationScopedObject()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Bean objects created with 'application' scope, will be available in the current page's ServletContext object. JavaServer Pages Specfication v1.2, Sec. 4.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setExpectHeaders("status:Test Status=PASSED");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/useBean/positiveApplicationScopedObjectXML.jsp HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("positiveApplicationScopedObjectTest");
    httpAssert.setTestStrategy(
        "In one JSP page, create a new bean object using jsp:useBean with the scope set to 'application'.  After the object has been created, forward the request to a second JSP page to validate that an object associated with the same ID used in the first JSP page is available in the current ServletContext.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveNoBody()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The jsp:useBean action does not require a body.  JavaServer Pages Specification v1.2, Sec. 4.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/useBean/positiveNoBody.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/useBean/positiveNoBodyXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveNoBodyTest");
    httpAssert.setTestStrategy(
        "Explicit test to ensure that the jsp:useBean action can be used without a body.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveClassTypeCast()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Using 'class' and 'type' together in useBean tag, 'Class' is assignable to 'type'.  JavaServler Pages Specification v1.2, Sec 4.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/useBean/positiveClassTypeCast.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/useBean/positiveClassTypeCastXML.jsp HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("positiveClassTypeCastTest");
    httpAssert.setTestStrategy(
        "Create a new bean instance with a particulare class set for the class attribute, and a  parent class for the type attribute.  Validate That the instance is cast without an Exception.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlnegativeDuplicateIDFatalTranslationError()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Duplicate useBean id's found in the same translation unit shall result in a fatal translation error. JavaServer Pages Specification 1.2 Sec. 4.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/useBean/negativeDuplicateIDFatalTranslationErrorXML.jsp HTTP/1.0");
    httpAssert.setReturnCode("500");
    httpAssert.setTestName("negativeDuplicateIDFatalTranslationErrorTest");
    httpAssert.setTestStrategy(
        "Create two beans with the same id attribute. Validate that a Fatal Translation error occurs.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlnegativeSessionScopeFatalTranslationError()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "It is a fatal translation error to attempt to use session scope when the JSP page has declared via the page directive, that it does not participate in a session. JavaServer Pages Specification 1.2 Sec. 4.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/useBean/negativeSessionScopeFatalTranslationErrorXML.jsp HTTP/1.0");
    httpAssert.setReturnCode("500");
    httpAssert.setTestName("negativeSessionScopeFatalTranslationErrorTest");
    httpAssert.setTestStrategy(
        "Use the page directive to set the session attribute to false and then declare a bean with session scope. Validate that a Fatal Translation error occurs.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlnegativeClassCastException()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "A java.lang.ClassCastException shall occur at request time when the assignment of the object referenced to the scripting variable fails. JavaServer Pages Specification 1.2 Sec. 4.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/useBean/negativeClassCastException.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/useBean/negativeClassCastExceptionFwdXML.jsp HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("negativeClassCastExceptionTest");
    httpAssert.setTestStrategy(
        "In one JSP page, declare a bean of a particular type with session scope.  Once declared, this page will forward to a second JSP page which will try to  reference the previously declared bean in the session  scope, but will define the type attribute with  an incompatible type.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlnegativeNotFoundTypeInstantiationException()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "If the object specified by useBean is not found in the specified scope and neither class nor beanName are given, a java.lang.InstantiationException  shall occur. JavaServer Pages Specification 1.2 Sec. 4.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/useBean/negativeNotFoundTypeInstantiationException.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/useBean/negativeNotFoundTypeInstantiationExceptionXML.jsp HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("negativeNotFoundTypeInstantiationExceptionTest");
    httpAssert.setTestStrategy(
        "Define a new bean within the JSP page without class or beanName defined.  Catch the Exception and print a message.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveForwardCtxRelative()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "A request can be forwarded to a JSP page, within the  same context, using a page relative-path.  JavaServer Pages Specification 1.2 Sec. 4.5");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/forward/positiveForwardCtxRelative.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/forward/positiveForwardCtxRelativeXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveForwardCtxRelativeTest");
    httpAssert.setTestStrategy(
        "Validate that jsp:forward can forward a request  to a JSP page within the same context using  a page relative-path.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveForwardCtxRelativeHtml()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "A request can be forwarded to a static resource, within the same context, using a page-relative path.  JavaServer Pages Specification 1.2 Sec. 4.5");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/forward/positiveForwardCtxRelativeHtml.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/forward/positiveForwardCtxRelativeHtmlXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveForwardCtxRelativeHtmlTest");
    httpAssert.setTestStrategy(
        "Validate that jsp:forward can forward a request to  a static resource within the same context using  a page-relative path.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveForwardPageRelative()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "A request can be forwarded to a JSP page, within the  same context, using a page relative-path.  JavaServer Pages Specification 1.2 Sec. 4.5");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/forward/positiveForwardPageRelative.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/forward/positiveForwardPageRelativeXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveForwardPageRelativeTest");
    httpAssert.setTestStrategy(
        "Validate that jsp:forward can forward a request  to a JSP page within the same context using  a page-relative path.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveForwardPageRelativeHtml()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "A request can be forwarded to a static resource, within the same context, using a page-relative path.  JavaServer Pages Specification 1.2 Sec. 4.5");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/forward/positiveForwardPageRelativeHtml.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/forward/positiveForwardPageRelativeHtmlXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveForwardPageRelativeHtmlTest");
    httpAssert.setTestStrategy(
        "Validate that jsp:forward can forward a request  to a static resource within the same context using  a page-relative path.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveForwardRequestAttrCtxRelative()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The page attribute of jsp:forward can accept a request-time attribute with a context-relative path value. JavaServer Pages v1.2, Sec. 4.5");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/forward/positiveRequestAttrCtxRelative.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/forward/positiveRequestAttrCtxRelativeXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveForwardRequestAttrCtxRelativeTest");
    httpAssert.setTestStrategy(
        "Validate that jsp:forward can properly accept a  request-time attribute containing a context-relative path value.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveForwardRequestAttrPageRelative()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The page attribute of jsp:forward can accept a request-time attribute with a page-relative path value. JavaServer Pages v1.2, Sec. 4.5");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/forward/positiveRequestAttrPageRelative.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/forward/positiveRequestAttrPageRelativeXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveForwardRequestAttrPageRelativeTest");
    httpAssert.setTestStrategy(
        "Validate that jsp:forward can properly accept a  request-time attribute containing a page-relative path value.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveIncludeCtxRelative()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "jsp:include provides for the inclusion  of dynamic resources, within the same context, using a context-relative path. JavaServer Pages Specification v1.2, Sec. 4.4");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/include/positiveIncludeCtxRelative.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/include/positiveIncludeCtxRelativeXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveIncludeCtxRelativeTest");
    httpAssert.setTestStrategy(
        "Include content, using a context-relative path, from JSP page into the current JSP page.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveIncludeCtxRelativeHtml()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "jsp:include provides for the inclusion  of static resources, within the same context, using a context-relative path. JavaServer Pages Specification v1.2, Sec. 4.4");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/include/positiveIncludeCtxRelativeHtml.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/include/positiveIncludeCtxRelativeHtmlXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveIncludeCtxRelativeHtmlTest");
    httpAssert.setTestStrategy(
        "Include content, using a context-relative path, from a static HTML page into the current JSP page.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveIncludePageRelative()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "jsp:include provides for the inclusion  of dynamic resources, within the same context, using a page-relative path. JavaServer Pages Specification v1.2, Sec. 4.4");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/include/positiveIncludePageRelative.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/include/positiveIncludePageRelativeXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveIncludePageRelativeTest");
    httpAssert.setTestStrategy(
        "Include content, using a page-relative path, from a JSP page into the current JSP page.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveIncludeRequestAttrCtxRelative()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The page attribute of jsp:include accepts request-time attributes containing context-relative path values.");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/include/positiveRequestAttrCtxRelative.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/include/positiveRequestAttrCtxRelativeXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveIncludeRequestAttrCtxRelativeTest");
    httpAssert.setTestStrategy(
        "Validate the page attribute of jsp:include can correctly accept request-time attribute values which contain context-relative paths.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveIncludeRequestAttrPageRelative()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "The page attribute of jsp:include accepts request-time attributes containing page-relative path values.");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/core_syntax/actions/include/positiveRequestAttrPageRelative.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/core_syntax/actions/include/positiveRequestAttrPageRelativeXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveIncludeRequestAttrPageRelativeTest");
    httpAssert.setTestStrategy(
        "Validate the page attribute of jsp:include can correctly accept request-time attribute values which contain page-relative paths.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveForward()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Calling PageContext.forward(java.lang.String relUrlPath) with a relativeUrlPath that doesn't begin with a leading '/' will forward the current ServletRequest and ServletResponse to another active component in the application relative to the URL of the request that was mapped to the calling JSP. JavaServer Pages Specification v1.2, Sec. 9.2.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/PageContext/positiveForward.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/PageContext/positiveForwardXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveForwardTest");
    httpAssert.setTestStrategy(
        "Call PageContext.forward(String) with a page-relative path and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveGetAttributeAvbl()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "PageContext.getAttribute(java.lang.String name) will return the object associated with the name in the page scope. JavaServer Pages Specification v1.2, Sec. 9.2.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/PageContext/positiveGetAttributeAvbl.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/PageContext/positiveGetAttributeAvblXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveGetAttributeAvblTest");
    httpAssert.setTestStrategy(
        "Call PageContext.getAttribute() to retrieve an object located in the page scope.  Validate that methods can be successfully called against the returned object.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveGetAttributeNotAvbl()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "PageContext.getAttribute(java.lang.String name) will return null of there is no objected assocated with the passed name in the page scope. JavaServer Pages Specification v1.2, Sec. 9.2.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/PageContext/positiveGetAttributeNotAvbl.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/PageContext/positiveGetAttributeNotAvblXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveGetAttributeNotAvblTest");
    httpAssert.setTestStrategy(
        "Call PageContext.getAttribute(String) passing in a name in which there is no associated object in the page scope.  Validate that the value returned is null.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveGetAttributeNamesInScope()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "PageContext.getAttributeNamesInScope(int scope) will return an enumeration of all attributes in the given scope. JavaServer Pages Specification v1.2, Sec. 9.2.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/PageContext/positiveGetAttributeNamesInScope.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/PageContext/positiveGetAttributeNamesInScopeXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveGetAttributeNamesInScopeTest");
    httpAssert.setTestStrategy(
        "Call PageContext.getAttributeInScope(int) passing in PageContext.PAGE_SCOPE.  Validate that the expected object is found within the returned enumeration.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveGetAttributeScope()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "PageContext.getAttributeScope(java.lang.String name) returns scope where the attribute is defined. JavaServer Pages Specification v1.2, Sec. 9.2.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/PageContext/positiveGetAttributeScope.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/PageContext/positiveGetAttributeScopeXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveGetAttributeScopeTest");
    httpAssert.setTestStrategy(
        "Call PageContext.getAttributeScope(String) against an attribute in page scope.  Validate that the proper scope value is returned.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlcheckGetSession()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "PageContext.getSession() returns the current HttpSession object. JavaServer Pages Specification v1.2, Sec. 9.2.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/PageContext/checkGetSession.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/PageContext/checkGetSessionXML.jsp HTTP/1.0");
    httpAssert.setTestName("checkGetSessionTest");
    httpAssert.setTestStrategy(
        "Call PageContext.getSession() and verify that the HttpSession instance has the same reference as that referenced by the session scripting variable.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlcheckGetResponse()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "PageContext.getResponse() returns the current ServletResponse object. JavaServer Pages Specification v1.2, Sec. 9.2.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/PageContext/checkGetResponse.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/PageContext/checkGetResponseXML.jsp HTTP/1.0");
    httpAssert.setTestName("checkGetResponseTest");
    httpAssert.setTestStrategy(
        "Call PageContext.getResponse() and verify that the ServletResponse instance has the same reference as that referenced by the response scripting variable.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlcheckGetRequest()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "PageContext.getRequest() returns the current ServletRequest object. JavaServer Pages Specification v1.2, Sec. 9.2.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/PageContext/checkGetRequest.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/PageContext/checkGetRequestXML.jsp HTTP/1.0");
    httpAssert.setTestName("checkGetRequestTest");
    httpAssert.setTestStrategy(
        "Call PageContext.getRequest() and verify that the ServletRequest instance has the same reference as that referenced by the request scripting variable.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlcheckGetServletContext()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "PageContext.getServletContext() returns the current ServletContext object. JavaServer Pages Specification v1.2, Sec. 9.2.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/PageContext/checkGetServletContext.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/PageContext/checkGetServletContextXML.jsp HTTP/1.0");
    httpAssert.setTestName("checkGetServletContextTest");
    httpAssert.setTestStrategy(
        "Call PageContext.getServletContext() and verify that the ServletContext instance has the same reference as that referenced by the application scripting variable.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlcheckGetServletConfig()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "PageContext.getServletConfig() returns the current ServletConfig object. JavaServer Pages Specification v1.2, Sec. 9.2.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/PageContext/checkGetServletConfig.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/PageContext/checkGetServletConfigXML.jsp HTTP/1.0");
    httpAssert.setTestName("checkGetServletConfigTest");
    httpAssert.setTestStrategy(
        "Call PageContext.getServletConfig() and verify that the ServletConfig instance has the same reference as that referenced by the config scripting variable.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlcheckGetOut()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "PageContext.getOut() returns the current JspWriter object. JavaServer Pages Specification v1.2, Sec. 9.2.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/PageContext/checkGetOut.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/PageContext/checkGetOutXML.jsp HTTP/1.0");
    httpAssert.setTestName("checkGetOutTest");
    httpAssert.setTestStrategy(
        "Call PageContext.getOut() and verify that the JspWriter instance has the same reference as that referenced by the out scripting variable.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveInclude()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Calling PageContext.include(java.lang.String relUrlPath) with a relativeUrlPath that doesn't begin with a leading '/' will cause the specified content, relative to the URL of the request that was mapped to the calling JSP, to be processed as part of the current ServletRequest and ServletResponse.  JavaServer Pages Specification v1.2, Sec. 9.2.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/PageContext/positiveInclude.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/PageContext/positiveIncludeXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveIncludeTest");
    httpAssert.setTestStrategy(
        "Call PageContext.include(String) with a page-relative path and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveRemoveAttribute()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "PageContext.removeAttribute(java.lang.String name) will remove the  object reference associated with the passed name, from all scopes. JavaServer Pages Specification v1.2, Sec. 9.2.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/PageContext/positiveRemoveAttribute.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/PageContext/positiveRemoveAttributeXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveRemoveAttributeTest");
    httpAssert.setTestStrategy(
        "Call PageContext.removeAttribute(String) and verify that the  attribute is indeed removed.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveSetAttribute()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "PageContext.setAttribute(java.lang.String name, java.lang.Object attribute) will register the name and object specified within the page scope. JavaServer Pages Specification v1.2, Sec. 9.2.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/PageContext/positiveSetAttribute.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/PageContext/positiveSetAttributeXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetAttributeTest");
    httpAssert.setTestStrategy(
        "Call PageContext.setAttribute(String,Object) and validate that attribute can be obtained via a call to PageContext.getAttribute(String).");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveSetAttributeInScope()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "PageContext.setAttributeInScope(java.lang.String name, java.lang.Object attribute, int) will register the name and object within the specified scope. JavaServer Pages Specification v1.2, Sec. 9.2.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/PageContext/positiveSetAttributeInScope.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/PageContext/positiveSetAttributeInScopeXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetAttributeInScopeTest");
    httpAssert.setTestStrategy(
        "Call PageContext.setAttribute(String,Object,int) and validate that the attribute is in the scope specified via a call to PageContext.getAttributeScope(String).");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveJSPInitJSP()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Container implementations must ensure that getServletConfig() will return the desired value in cases where page authors override jspInit().  JavaServer Pages Specification v1.2, Sec. 9.1.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/HttpJspPage/positiveJSPInitJSP.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/HttpJspPage/positiveJSPInitJSPXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveJSPInitJSPTest");
    httpAssert.setTestStrategy(
        "Override the jspInit() method in a declaration. Validate that a call to getServletConfig returns a non-null value.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositivePrintBoolean()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.print(boolean b) will print the String produced by java.lang.String.valueOf(boolean).  The byte's displayed will be according to the platform's default character encoding. JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintBoolean.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintBooleanXML.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintBooleanTest");
    httpAssert.setTestStrategy(
        "Call print(boolean b) and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositivePrintChar()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.print(char c) prints passed value according to the platform's default character encoding. Server Pages Specification v1.2, Sec 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintChar.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintCharXML.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintCharTest");
    httpAssert.setTestStrategy(
        "Call print(char c) and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositivePrintCharArray()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.print(char[] s) will print an array of characters according to the platform's default  character encoding. JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintCharArray.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintCharArrayXML.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintCharArrayTest");
    httpAssert.setTestStrategy(
        "Call print(char[] c) and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositivePrintDouble()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.print(double d) prints a String representation of a double-precision floating-point number, generated by  java.lang.String.valueOf(double), with the bytes translated to the platform's default character encoding. JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintDouble.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintDoubleXML.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintDoubleTest");
    httpAssert.setTestStrategy(
        "Call print(double d) and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositivePrintFloat()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.print(float f) prints a String representation of a floating point number, generated by java.lang.String.valueOf(float), with the bytes translated to the platform's default character encoding. JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintFloat.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintFloatXML.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintFloatTest");
    httpAssert.setTestStrategy(
        "Call print(float f) and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositivePrintInt()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.print(int i) prints a String representation of an integer, generated by java.lang.String.valueOf(int),  with the bytes translated to the platform's default  character encoding. JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintInt.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintIntXML.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintIntTest");
    httpAssert.setTestStrategy(
        "Call print(int i) and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositivePrintLong()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.print(long l) prints a String representation of a long integer, generated by java.lang.String.valueOf(long),  with the bytes translated to the platform's default  character encoding. JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintLong.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintLongXML.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintLongTest");
    httpAssert.setTestStrategy(
        "Call print(long l) and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositivePrintObj()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.print(java.lang.Object obj) prints a String  representation of an object, generated by  java.lang.String.valueOf(Object), with the bytes translated  to the platform's default character encoding. JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintObj.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintObjXML.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintObjTest");
    httpAssert.setTestStrategy(
        "Call print(java.lang.Object obj) and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositivePrintStr()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.print(java.lang.String str) prints a String  representation of an object, with the bytes translated  to the platform's default character encoding. JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintStr.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintStrXML.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintStrTest");
    httpAssert.setTestStrategy(
        "Call print(java.lang.String str) and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositivePrintln()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.println() will terminate the current line by writing the ling separator string. JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintln.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintlnXML.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintlnTest");
    httpAssert.setTestStrategy(
        "Call println() and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositivePrintlnBoolean()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.println(boolean b) prints a boolean value and then terminates the line.  This method behaves as though it invokes print(boolean b) and then println(). JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintlnBoolean.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintlnBooleanXML.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintlnBooleanTest");
    httpAssert.setTestStrategy(
        "Call println(boolean b) and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositivePrintlnChar()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.println(char c) prints a char value and then terminates the line.  This method behaves as though it invokes print(char c) and then println(). JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintlnChar.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintlnCharXML.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintlnCharTest");
    httpAssert.setTestStrategy(
        "Call println(char c) and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositivePrintlnCharArray()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.println(char[] s) prints an array of characters and then terminates the line.  This method behaves as though it invokes print(char[] s) and then println(). JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintlnCharArray.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintlnCharArrayXML.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintlnCharArrayTest");
    httpAssert.setTestStrategy(
        "Call println(char c) and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositivePrintlnDouble()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.println(double d) prints a double-precision floating-point value and then terminates the line.   This method behaves as though it invokes print(double d)  and then println(). JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintlnDouble.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintlnDoubleXML.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintlnDoubleTest");
    httpAssert.setTestStrategy(
        "Call println(double d) and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositivePrintlnFloat()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.println(float f) prints a floating-point value  and then terminates the line.  This method behaves as though  it invokes print(float f) and then println(). JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintlnFloat.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintlnFloatXML.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintlnFloatTest");
    httpAssert.setTestStrategy(
        "Call println(float f) and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositivePrintlnInt()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.println(int i) prints an integer value  and then terminates the line.  This method behaves as though  it invokes print(int i) and then println(). JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintlnInt.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintlnIntXML.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintlnIntTest");
    httpAssert.setTestStrategy(
        "Call println(int i) and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositivePrintlnLong()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.println(long l) prints a long integer value  and then terminates the line.  This method behaves as though  it invokes print(long l) and then println(). JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintlnLong.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintlnLongXML.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintlnLongTest");
    httpAssert.setTestStrategy(
        "Call println(long l) and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositivePrintlnObj()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.println(java.lang.Object obj) prints an Object  value and then terminates the line.  This  method behaves as though it invokes print(java.lang.Object obj)  and then println(). JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintlnObj.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintlnObjXML.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintlnObjTest");
    httpAssert.setTestStrategy(
        "Call println(java.lang.Object obj) and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositivePrintlnStr()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.println(java.lang.String str) prints a long  integer value and then terminates the line.  This  method behaves as though it invokes print(java.lang.String str)  and then println(). JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/print/positivePrintlnStr.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/print/positivePrintlnStrXML.jsp HTTP/1.0");
    httpAssert.setTestName("positivePrintlnStrTest");
    httpAssert.setTestStrategy(
        "Call println(java.lang.String str) and validate the result.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveClear()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.clear() will cause the contents of the output  buffer to be cleared. JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/misc/positiveClear.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/misc/positiveClearXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveClearTest");
    httpAssert.setTestStrategy(
        "Using a page with the default buffer size of 8kb, write data to the buffer and call clear.  The cleared data should not be present in the response.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveFlush()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.flush() will cause the stream to write any saved content to the intended destiation. JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/misc/positiveFlush.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/misc/positiveFlushXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveFlushTest");
    httpAssert.setTestStrategy(
        "Obtain the current buffer size being used, then write and flush some sample text. Validate that the stream was indeed flushed by checking the number of bytes remaining in  the buffer.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveGetBufferSize()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.getBufferSize() returns the size of the buffer in bytes used by the particular JspWriter instance. JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/misc/positiveGetBufferSize.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/misc/positiveGetBufferSizeXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveGetBufferSizeTest");
    httpAssert.setTestStrategy(
        "Set the buffer of the page to 5kb.  Call getBufferSize() and validate that the value returned is as expected.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveGetBufferSizeUnBuffered()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "When buffering is disabled within a particular  JSP page, a call to JspWriter.getBufferSize()  will return 0 (zero). JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/misc/positiveGetBufferSizeUnBuffered.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/misc/positiveGetBufferSizeUnBufferedXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveGetBufferSizeUnBufferedTest");
    httpAssert.setTestStrategy(
        "Set the buffer to 'none' and call getBufferSize(). Validate that the value returned is 0 (zero).");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveGetRemaining()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.getRemaining() will return the number of  unused bytes in the buffer. JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/misc/positiveGetRemaining.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/misc/positiveGetRemainingXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveGetRemainingTest");
    httpAssert.setTestStrategy(
        "Write 6 bytes of data to the buffer and call getRemaining().  The value returned should be 6 bytes less than the value returned by  getBufferSize().");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveGetRemainingBufferUnset()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.getRemaining() will return 0 (zero) if the JSP page doesn't use a buffer. JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/misc/positiveGetRemainingBufferUnset.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/misc/positiveGetRemainingBufferUnsetXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveGetRemainingBufferUnsetTest");
    httpAssert.setTestStrategy(
        "Set the buffer of the JSP page to 'none', and validate that a call to getRemaining() returns 0 (zero).");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveIsAutoFlush()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.isAutoFlush() will inidicate whether the JspWriter will flush its buffer automatically. JavaServer Pages Specification v1.2, Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/misc/positiveIsAutoFlush.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/misc/positiveIsAutoFlushXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveIsAutoFlushTest");
    httpAssert.setTestStrategy(
        "JSP pages automatically flush their buffer by default.  Call isAutoFlush() and validated that 'true' is returned.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveNewLine()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "JspWriter.newLine() will write a line separator as defined by the system property 'line.separator'. JavaServer Pages Specification v1.2 , Sec. 9.2.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/engine/JspWriter/misc/positiveNewLine.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/engine/JspWriter/misc/positiveNewLineXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveNewLineTest");
    httpAssert.setTestStrategy(
        "Validate the a call to newLine() produces the expected results.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveTagGetAttribute()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test a TagData object by passing a hashtable and printing  the contents of the hashtable using the getAttribute()  method of TagData., specified in the Java Server Pages  Specification v1.2, Sec 10.5.7");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/TagData/positiveTagGetAttribute.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TagData/positiveTagGetAttributeXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveTagGetAttributeTest");
    httpAssert.setTestStrategy(
        "Testing TagData release() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveTagSetAttribute()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test a TagData object by passing a hashtable created using  setAttribute() method of the TagData and print the contents of the hashtable, specified in the Java Server Pages  Specification v1.2, Sec 10.5.7");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/TagData/positiveTagSetAttribute.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TagData/positiveTagSetAttributeXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveTagSetAttributeTest");
    httpAssert.setTestStrategy(
        "Testing TagData setAttribute() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveTagGetAttributeString()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test a TagData object by passing a hashtable created using  setAttribute() method of the TagData and print the  contents of the hashtable using the getAttributeString()  method, specified in the Java Server Pages Specification  v1.2, Sec 10.5.7");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/TagData/positiveTagGetAttributeString.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TagData/positiveTagGetAttributeStringXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveTagGetAttributeStringTest");
    httpAssert.setTestStrategy(
        "Testing TagData getAttributeString() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveGetValues()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for getValues() method which returns an enumeration  and print the contents, specified in the Java Server  Pages Specification v1.2, Sec 10.1.4");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/TagSupport/positiveGetValues.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TagSupport/positiveGetValuesXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveGetValuesTest");
    httpAssert.setTestStrategy(
        "Testing TagSupport getValues() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveGetValue()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for getValue() method, specified in the Java Server  Pages Specification v1.2, Sec 10.1.4");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/TagSupport/positiveGetValue.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TagSupport/positiveGetValueXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveGetValueTest");
    httpAssert.setTestStrategy(
        "Testing TagSupport getValue() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveRemoveValue()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for removeValue() method, specified in the Java  Server Pages Specification v1.2, Sec 10.1.4");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/TagSupport/positiveRemoveValue.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TagSupport/positiveRemoveValueXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveRemoveValueTest");
    httpAssert.setTestStrategy(
        "Testing TagSupport removeValue() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveSetValue()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for setValue() method, specified in the Java Server  Pages Specification v1.2, Sec 10.1.4");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/TagSupport/positiveSetValue.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TagSupport/positiveSetValueXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetValueTest");
    httpAssert.setTestStrategy(
        "Testing TagSupport setValue() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmldefault_return_values()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Tests if the default values are returned from the methods of TagSupport class  v1.2, Sec 10.1.4");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/TagSupport/default_return_values.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TagSupport/default_return_valuesXML.jsp HTTP/1.0");
    httpAssert.setTestName("default_return_valuesTest");
    httpAssert.setTestStrategy(
        "testing TagSupport default return values by calling super.FunctionName");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveTSDoEndTag()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Tests that doEndTag metjod is called when the end of the tag is found  v1.2, Sec 10.1.4");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/TagSupport/positiveDoEndTag.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TagSupport/positiveDoEndTagXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveTSDoEndTagTest");
    httpAssert.setTestStrategy(
        "testing TagSupport doEndTag method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveSetGetId()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for setId and getId methods of TagSupport  v1.2, Sec 10.1.4");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/TagSupport/positiveSetGetId.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TagSupport/positiveSetGetIdXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveSetGetIdTest");
    httpAssert.setTestStrategy(
        "testing TagSupport getId and setId in the same test");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositivefindAncestorWithClass()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for Nested classes. Tests the findAncestorWithClass method for TagSupport  v1.2, Sec 10.1.4");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/TagSupport/positivefindAncestorWithClass.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TagSupport/positivefindAncestorWithClassXML.jsp HTTP/1.0");
    httpAssert.setTestName("positivefindAncestorWithClassTest");
    httpAssert.setTestStrategy(
        "Testing TagSupport.findAncestorWithClass method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveTSDoAfterBody()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test the DoAfterBody method of the TagSupport class.     Create a Tag handler that outputs a message a given number of times from the DoAFterBody  v1.2, Sec 10.1.4");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/TagSupport/positiveDoAfterBody.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TagSupport/positiveDoAfterBodyXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveTSDoAfterBodyTest");
    httpAssert.setTestStrategy(
        "Testing TagSupport setPageContext() method");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmltag_attr_setting_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test that attribute setting is as wanted, specified in the  Java Server Pages Specification v1.2, Sec 10.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/LifeCycle/tag_attr_setting_1.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/LifeCycle/tag_attr_setting_1XML.jsp HTTP/1.0");
    httpAssert.setTestName("tag_attr_setting_1Test");
    httpAssert.setTestStrategy(
        "Testing LifeCycle process");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmltag_attr_setting_2()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test that attribute setting is as wanted and intermix some  different tags, specified in the Java Server Pages  Specification v1.2, Sec 10.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/LifeCycle/tag_attr_setting_2.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/LifeCycle/tag_attr_setting_2XML.jsp HTTP/1.0");
    httpAssert.setTestName("tag_attr_setting_2Test");
    httpAssert.setTestStrategy(
        "Testing LifeCycle process");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmltag_attr_setting_3()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Another test for attribute setting intermixed with some  different tags, specified in the Java Server Pages  Specification v1.2, Sec 10.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/LifeCycle/tag_attr_setting_3.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/LifeCycle/tag_attr_setting_3XML.jsp HTTP/1.0");
    httpAssert.setTestName("tag_attr_setting_3Test");
    httpAssert.setTestStrategy(
        "Testing LifeCycle process");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmltag_check_BodyContent_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Check that BodyContent provides access to the proper  information, specified in the Java Server Pages  Specification v1.2, Sec 10.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/LifeCycle/tag_check_BodyContent_1.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/LifeCycle/tag_check_BodyContent_1XML.jsp HTTP/1.0");
    httpAssert.setTestName("tag_check_BodyContent_1Test");
    httpAssert.setTestStrategy(
        "Testing LifeCycle process");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmltag_check_BodyContent_2()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Check that BodyContent provides access to the proper  information, specified in the Java Server Pages  Specification v1.2, Sec 10.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/LifeCycle/tag_check_BodyContent_2.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/LifeCycle/tag_check_BodyContent_2XML.jsp HTTP/1.0");
    httpAssert.setTestName("tag_check_BodyContent_2Test");
    httpAssert.setTestStrategy(
        "Testing LifeCycle process");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmltag_check_BodyContent_3()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Check that BodyContent provides access to the proper  information, specified in the Java Server Pages  Specification v1.2, Sec 10.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/LifeCycle/tag_check_BodyContent_3.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/LifeCycle/tag_check_BodyContent_3XML.jsp HTTP/1.0");
    httpAssert.setTestName("tag_check_BodyContent_3Test");
    httpAssert.setTestStrategy(
        "Testing LifeCycle process");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmltag_check_BodyContent_4()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Check that BodyContent provides access to the proper  information, specified in the Java Server Pages  Specification v1.2, Sec 10.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/LifeCycle/tag_check_BodyContent_4.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/LifeCycle/tag_check_BodyContent_4XML.jsp HTTP/1.0");
    httpAssert.setTestName("tag_check_BodyContent_4Test");
    httpAssert.setTestStrategy(
        "Testing LifeCycle process");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmltag_check_BodyContent_5()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Check that BodyContent provides access to the proper  information, specified in the Java Server Pages  Specification v1.2, Sec 10.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/LifeCycle/tag_check_BodyContent_5.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/LifeCycle/tag_check_BodyContent_5XML.jsp HTTP/1.0");
    httpAssert.setTestName("tag_check_BodyContent_5Test");
    httpAssert.setTestStrategy(
        "Testing LifeCycle process");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmltag_check_comments_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Check that comments are processed correctly, specified in  the Java Server Pages Specification v1.2, Sec 10.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/LifeCycle/tag_check_comments_1.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/LifeCycle/tag_check_comments_1XML.jsp HTTP/1.0");
    httpAssert.setTestName("tag_check_comments_1Test");
    httpAssert.setTestStrategy(
        "Testing LifeCycle process");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmltag_check_comments_2()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Check that HTML-style comments are processed correctly, specified in  the Java Server Pages Specification v1.2, Sec 10.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/LifeCycle/tag_check_comments_2.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/LifeCycle/tag_check_comments_2XML.jsp HTTP/1.0");
    httpAssert.setTestName("tag_check_comments_2Test");
    httpAssert.setTestStrategy(
        "Testing LifeCycle process");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmltag_doEndTag_skip_page()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for doEndTag SKIP_PAGE, specified in the Java Server  Pages Specification v1.2, Sec 10.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/LifeCycle/tag_doEndTag_skip_page.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/LifeCycle/tag_doEndTag_skip_pageXML.jsp HTTP/1.0");
    httpAssert.setTestName("tag_doEndTag_skip_pageTest");
    httpAssert.setTestStrategy(
        "Testing LifeCycle process");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmltag_doStart_eval_body_include()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for doStartTag EVAL_BODY_INCLUDE, specified in the Java  Server Pages Specification v1.2, Sec 10.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/LifeCycle/tag_doStart_eval_body_include.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/LifeCycle/tag_doStart_eval_body_includeXML.jsp HTTP/1.0");
    httpAssert.setTestName("tag_doStart_eval_body_includeTest");
    httpAssert.setTestStrategy(
        "Testing LifeCycle process");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmltag_doStart_skip_body()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for doStartTag SKIP_BODY, specified in the Java Server  Pages Specification v1.2, Sec 10.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/LifeCycle/tag_doStart_skip_body.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/LifeCycle/tag_doStart_skip_bodyXML.jsp HTTP/1.0");
    httpAssert.setTestName("tag_doStart_skip_bodyTest");
    httpAssert.setTestStrategy(
        "Testing LifeCycle process");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmltag_translationtime_verify_1()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Test for translation-time verification, specified in the  Java Server Pages Specification v1.2, Sec 10.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/LifeCycle/tag_translationtime_verify_1.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/LifeCycle/tag_translationtime_verify_1XML.jsp HTTP/1.0");
    httpAssert.setTestName("tag_translationtime_verify_1Test");
    httpAssert.setTestStrategy(
        "Testing LifeCycle process");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpersistentValues()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Tests if the attribute values are retaines when tags are nested inside a tag  Java Server Pages Specification v1.2, Sec 10.1");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/misc/persistentValues.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/misc/persistentValuesXML.jsp HTTP/1.0");
    httpAssert.setTestName("persistentValuesTest");
    httpAssert.setTestStrategy(
        "testing persistence of tag attributes");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveImplicitJarMultiTld()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Verify that the container, with no explicit taglib mappings within the web.xml, can recognize multiple tlds stored in a jar, and create the appropriate  mapping so that the client can use the tags. JavaServer Pages Specification 1.2 Sections: JSP 7.3.4 JSP 7.3.8");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/TldPathResolution/implicit/positiveImplicitJarMultiTld.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TldPathResolution/implicit/positiveImplicitJarMultiTldXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveImplicitJarMultiTldTest");
    httpAssert.setTestStrategy(
        "Define two distinct tags for this page, and use the tags so that output will be generated.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveAbsTld()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Verify that the requested tld, using an absoulte URI,  resolves to the appropriate tld. JavaServer Pages Specification 1.2 Sections: JSP 7.3.2 JSP 7.3.3 JSP 7.3.6  JSP 7.3.6.1 JSP 7.3.6.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TldPathResolution/explicit/positiveAbsTldXML.jsp HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("positiveAbsTldTest");
    httpAssert.setTestStrategy(
        "Using a taglib directive, set the uri attribute to an absolute URI.  If successful, no fatal translation error should occur.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveAbsJar()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Verify that the requested tld, using an absoulte URI,  resolves to jar which in turn resolves to a single TLD  (taglib.tld) within the jar. JavaServer Pages Specification 1.2 Sections: JSP 7.3.2 JSP 7.3.3 JSP 7.3.6  JSP 7.3.6.1 JSP 7.3.6.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TldPathResolution/explicit/positiveAbsJarXML.jsp HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("positiveAbsJarTest");
    httpAssert.setTestStrategy(
        "Using a taglib directive, set the uri attribute to an absolute URI.  If successful, no fatal translation error should occur.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlpositiveRelJar()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Verify that the requested tld, using a relative URI,  resolves to a jar which in ture resolves to a single TLD (taglib.tld) within the jar. JavaServer Pages Specification 1.2 Sections: JSP 7.3.2 JSP 7.3.3 JSP 7.3.6  JSP 7.3.6.1 JSP 7.3.6.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");
    httpAssert.setGoldenResource(
        "watchdog/resources/jsp-golden/tagext/TldPathResolution/explicit/positiveRelJar.html");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/tagext/TldPathResolution/explicit/positiveRelJarXML.jsp HTTP/1.0");
    httpAssert.setTestName("positiveRelJarTest");
    httpAssert.setTestStrategy(
        "Using a taglib directive, set the uri attribute to a relative URI.  Verify that the tag can be successfully used.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlprecompileNoValue()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "If the jsp-tests request parameter has no value, the request will not be delivered to the target JSP page. JavaServer Pages Specification v1.2, Sec. 8.4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/misc/precompilation/precompileXML.jsp?jsp_precompile HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("precompileNoValueTest");
    httpAssert.setTestStrategy(
        "Validate that no response body is returned when jsp-tests has no value.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlprecompileFalse()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "If the jsp-tests request parameter is set to false, the request will not be delivered to the target JSP page. JavaServer Pages Specification v1.2, Sec. 8.4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/misc/precompilation/precompileXML.jsp?jsp_precompile=false HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("precompileFalseTest");
    httpAssert.setTestStrategy(
        "Validate that no response body is returned when jsp-tests is set to false.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlprecompileTrue()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "If the jsp-tests request parameter is set to true, the request will not be delivered to the target JSP page. JavaServer Pages Specification v1.2, Sec. 8.4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");


    httpAssert.setRequest(
        "GET /jsp-tests/jsp/misc/precompilation/precompileXML.jsp?jsp_precompile=true HTTP/1.0");
    httpAssert.setReturnCode("200");
    httpAssert.setTestName("precompileTrueTest");
    httpAssert.setTestStrategy(
        "Validate that no response body is returned when jsp-tests is set to true.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

  @Test
  public void testJspXmlprecompileNegative()
      throws Exception {
    HttpAssertion httpAssert = createAssertion();
    httpAssert.setAssertion(
        "Valid parameter values for jsp-tests are 'true', 'false', and no value.  Any other value will result in an HTTP Error; 500 (Server Error). JavaServer Pages specification v1.2, Sec. 8.4.2");
    httpAssert.setDebug("0");
    httpAssert.setExactMatch("false");

    httpAssert.setLastTask(true);

    httpAssert.setRequest(
        "GET /jsp-tests/jsp/misc/precompilation/precompileXML.jsp?jsp_precompile=any_invalid_value HTTP/1.0");
    httpAssert.setReturnCode("500");
    httpAssert.setTestName("precompileNegativeTest");
    httpAssert.setTestStrategy(
        "Set the jsp-tests request paramter to a non valid value and validate that a 500 error occurs.");
    if (httpAssert.hasFailed()) {
      httpFail();
    }
  }

// more-tests

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
    String requestedId = cookie.getValue() + "foo";
    cookie.setValue(requestedId);
    get.addRequestCookie(cookie);
    get.setExpectedResponseViaResource(
        "com/google/opengse/golden/noSessionRequested.txt");
    get.setProperty("requestedId", requestedId);
    get.connectToServerAndAssert();
  }


  /**
   * Do a basic sanity-check on all of the URI-related methods
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testUriMatch()
      throws Exception {
    // uses com.google.opengse.testlet.URITestlet on the server-side
    HttpRequestAsserter get = createGetAssertion();
    get.setUri("/contextpath/foo/bar.test");
    get.setExpectedResponseViaResource(
        "com/google/opengse/golden/urimatch.txt");
    get.connectToServerAndAssert();
  }

  /**
   * Same as testUriMatch but with a query string added
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testUriMatchWithQuery()
      throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri("/contextpath/foo/bar.test?source=open");
    get.setExpectedResponseViaResource(
        "com/google/opengse/golden/urimatchWithQuery.txt");
    get.connectToServerAndAssert();
  }

  /**
   * Test what request headers we have sent by invoking the servlet that
   * responds to "*.hdr"
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testHeaders()
      throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri("/contextpath/foo/bar.hdr");
    get.setRequestHeader("X-my-special-header", "ExtraSpecial, VerySpecial");
    get.setExpectedResponseLine(
        "X-MY-SPECIAL-HEADER: ExtraSpecial, VerySpecial");
    get.setExpectedResponseCode(200);
    get.connectToServerAndAssert();
  }

  /**
   * Tells the forwarding servlet to forward the request to
   * {@code /bar/foo.test?woo=hoo} then checks the result.
   * Uses com.google.opengse.testlet.ForwardTestlet on the server
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
        "queryString=woo=hoo&to=/bar/foo.test%3Fwoo%3Dhoo");
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
   * Test ServletContext#getContextPath works.
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testGetContextPath2()
      throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri("/contextpath/GetContextPathTest");
    get.setExpectedResponseLine(
        "contextPath=/contextpath");
    get.setExpectedResponseCode(200);
    get.connectToServerAndAssert();
  }

  /**
   * Test ServletContext#getServletContextName works.
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testGetServletContextName()
      throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri("/contextpath/GetServletContextNameTest");
    get.setExpectedResponseLine(
        "servletContextName=MoreTests");
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
    HttpRequestAsserter get = createGetAssertion("/contextpath/XPoweredByHeaderTest");
    get.expectHeader("X-Powered-By");
    get.setExpectedResponseCode(200);
    get.connectToServerAndAssert();
  }

  /**
   * Test the request listener is invoked with the request initialized event.
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testRequestInitializedEvent()
      throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri("/contextpath/RequestInitializedEventTest");
    get.setExpectedResponseLine(
        "Hello from MyServletRequestListener: request initialized");
    get.setExpectedResponseCode(200);
    get.connectToServerAndAssert();
  }

  /**
   * Test the request listener is invoked with the request destroyed event.
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testRequestDestroyedEvent()
      throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri("/contextpath/RequestDestroyedEventTest");
    get.setExpectedPassResponseLine();
    get.setExpectedResponseCode(200);
    get.connectToServerAndAssert();
  }

  /**
   * Test the request attribute listener is invoked with the attribute added
   * event.
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testAttributeAddedEvent()
      throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri("/contextpath/AttributeAddedEventTest");
    get.setExpectedPassResponseLine();
    get.setExpectedResponseLine(
        "Hello from MyServletRequestAttributeListener: attributed added");
    get.setExpectedResponseCode(200);
    get.connectToServerAndAssert();
  }

  /**
   * Test the request attribute listener is invoked with the attribute removed
   * event.
   *
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testAttributeRemovedEvent()
      throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri("/contextpath/AttributeRemovedEventTest");
    get.setExpectedPassResponseLine();
    get.setExpectedResponseLine(
        "Hello from MyServletRequestAttributeListener: attributed removed");
    get.setExpectedResponseCode(200);
    get.connectToServerAndAssert();
  }

  /**
   * Test the request attribute listener is invoked with the attribute replaced
   * event.
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testAttributeReplacedEvent()
      throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri("/contextpath/AttributeReplacedEventTest");
    get.setExpectedPassResponseLine();
    get.setExpectedResponseLine(
        "Hello from MyServletRequestAttributeListener: attributed replaced");
    get.setExpectedResponseCode(200);
    get.connectToServerAndAssert();
  }

  /**
   * The filter should only be triggered via a direct (external) request
   * dispatching.
   *
   * web.xml:
   * <filter-mapping>
   * <filter-name>ServletRequestFilter</filter-name>
   * <url-pattern>/DispatcherFilterTest</url-pattern>
   * <dispatcher>REQUEST</dispatcher>
   * </filter-mapping>
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testRequestOnlyDispatcherFilter() throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri("/contextpath/DispatcherFilterTest");
    get.setExpectedResponseCode(200);
    get.setExpectedResponseLine(
        "Hello from ServletRequestFilter request");
    get.setExpectedResponseLine(
        "Hello from ServletRequestFilter response");
    get.connectToServerAndAssert();

    get = createGetAssertion();
    get.setUri(
        "/contextpath/DispatcherInclude?includeTarget=/DispatcherFilterTest");
    get.setExpectedResponseCode(200);
    get.setUnexpectedResponseLine("Hello from ServletRequestFilter request");
    get.setUnexpectedResponseLine("Hello from ServletRequestFilter response");
    get.connectToServerAndAssert();

    get = createGetAssertion();
    get.setUri(
        "/contextpath/DispatcherForward?forwardTarget=/DispatcherFilterTest");
    get.setExpectedResponseCode(200);
    get.setUnexpectedResponseLine("Hello from ServletRequestFilter request");
    get.setUnexpectedResponseLine("Hello from ServletRequestFilter response");
    get.connectToServerAndAssert();
  }

  /**
   * The filter should only be triggered via an include dispatching.
   * web.xml:
   * <filter-mapping>
   * <filter-name>ServletIncludeFilter</filter-name>
   * <url-pattern>/IncludedDispatcherFilterTest</url-pattern>
   * <dispatcher>INCLUDE</dispatcher>
   * </filter-mapping>
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testIncludeOnlyDispatcherFilter() throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri("/contextpath/IncludedDispatcherFilterTest");
    get.setExpectedResponseCode(200);
    get.setUnexpectedResponseLine("Hello from ServletIncludeFilter request");
    get.setUnexpectedResponseLine("Hello from ServletIncludeFilter response");
    get.connectToServerAndAssert();

    get = createGetAssertion();
    get.setUri(
        "/contextpath/DispatcherInclude?includeTarget=/IncludedDispatcherFilterTest");
    get.setExpectedResponseCode(200);
    get.setExpectedResponseLine(
        "Hello from ServletIncludeFilter request");
    get.setExpectedResponseLine(
        "Hello from ServletIncludeFilter response");
    get.connectToServerAndAssert();

    get = createGetAssertion();
    get.setUri(
        "/contextpath/DispatcherForward?forwardTarget=/IncludedDispatcherFilterTest");
    get.setExpectedResponseCode(200);
    get.setUnexpectedResponseLine("Hello from ServletIncludeFilter request");
    get.setUnexpectedResponseLine("Hello from ServletIncludeFilter response");
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
   * The filter should only be triggered via an error page dispatching.
   * web.xml:
   * <filter-mapping>
   * <filter-name>ServletErrorFilter</filter-name>
   * <url-pattern>/ErroredDispatcherFilterTest</url-pattern>
   * <dispatcher>ERROR</dispatcher>
   * </filter-mapping>
   *
   * @throws Exception if anything goes wrong
   *
   */
  @Test
  public void testErrorDispatcherFilter() throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri("/contextpath/Error599");
    get.setExpectedResponseCode(599);
    get.setExpectedResponseLine(
        "Hello from ServletErrorFilter request");
    get.setExpectedResponseLine(
        "Hello from ServletErrorFilter response");
    get.connectToServerAndAssert();

    get = createGetAssertion();
    get.setUri("/contextpath/ErroredDispatcherFilterTest");
    get.setExpectedResponseCode(200);
    get.setUnexpectedResponseLine("Hello from ServletErrorFilter request");
    get.setUnexpectedResponseLine("Hello from ServletErrorFilter response");
    get.connectToServerAndAssert();

    get = createGetAssertion();
    get.setUri(
        "/contextpath/DispatcherInclude?includeTarget=/ErroredDispatcherFilterTest");
    get.setExpectedResponseCode(200);
    get.setUnexpectedResponseLine("Hello from ServletErrorFilter request");
    get.setUnexpectedResponseLine("Hello from ServletErrorFilter response");
    get.connectToServerAndAssert();

    get = createGetAssertion();
    get.setUri(
        "/contextpath/DispatcherForward?forwardTarget=/ErroredDispatcherFilterTest");
    get.setExpectedResponseCode(200);
    get.setUnexpectedResponseLine("Hello from ServletErrorFilter request");
    get.setUnexpectedResponseLine("Hello from ServletErrorFilter response");
    get.connectToServerAndAssert();
  }

  /**
   * 500 is generated for an uncaught session exception.
   * Uses com.google.opengse.testlet.Error.UnhandledSessionExceptionTestlet
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testUnhandledSessionException() throws Exception {
    HttpRequestAsserter get = createGetAssertion("/contextpath/UnhandledSessionExceptionTest?id=noMoreAttribute");
    get.setExpectedResponseCode(500);
    get.setExpectedContentTypeIsAny();
    get.connectToServerAndAssert();
  }

  /**
   * Listeners should be invoked according to web.xml order.
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testServletContextListenerOrder() throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri("/contextpath/ServletContextListenerOrderTest");
    get.setExpectedResponseCode(200);
    get.setExpectedResponseLine(
            "ServletContextListener1ServletContextListener2");
    get.connectToServerAndAssert();
  }

  /**
   * Servlet can access (any) files under WEB-INF via resource uri.
   * 
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testWebInfResource()
      throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri("/contextpath/WebInfResourceTest");
    get.setExpectedResponseCode(200);
    get.setExpectedPassResponseLine();
    get.connectToServerAndAssert();
  }

  /**
   * No direct access to files under WEB-INF is allowed.
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testWebInfFilesNotAccessible() throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri("/contextpath/WEB-INF/testdata/resource.txt");
    get.setExpectedContentTypeIsAny();
    get.setExpectedResponseCode(404);
//    get.setExpectedErrorMessageContains("Not Found");
    try {
      get.connectToServerAndAssert();
//      fail("Expected a FileNotFoundException to be thrown");
    } catch (FileNotFoundException ignored) { /* expected */ }
  }

  /**
   * dispatch=1 is from a dispatcher created via absolute path, while
   * dispatch=2 is from a dispatcher created via a relative path
   * (i.e. relative to the original request path).
   * <p/>
   * See ServletRequest.GetRequestDispatcherPathTestlet for more detail.
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testGetRequestDispatcherPath() throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri("/contextpath/subcontextpath/GetRequestDispatcherPathTest");
    get.setExpectedResponseCode(200);
    get.setExpectedResponseLine(
        "dispatch=1");
    // relative path dispatch (to the request path)
    get.setExpectedResponseLine(
        "dispatch=2");
    get.connectToServerAndAssert();
  }

  /**
   * This tests make sure that the welcome files can (always) be accessed
   * via direct URI path.
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testDirectWelcomeFileAccess() throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri("/contextpath/welcome/welcome1.txt");
    get.setExpectedResponseCode(200);
    get.setExpectedResponseLine(
        "welcome1");
    get.connectToServerAndAssert();

    get = createGetAssertion();
    get.setUri("/contextpath/welcome/welcome/welcome2.txt");
    get.setExpectedResponseCode(200);
    get.setExpectedResponseLine(
        "welcome2");
    get.connectToServerAndAssert();
  }

  /**
   * We have two welcome files under web/welcome/welcome, and according
   * to web.xml, we use the first one (weblcome1) if both are available.
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testOrderedWelcomeFileAccess() throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri("/contextpath/welcome/");
    get.setExpectedResponseCode(200);
    get.setExpectedResponseLine(
        "welcome1");
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
    HttpRequestAsserter get = createGetAssertion("/contextpath/welcome");
    // tomcat sends a redirect to the above url with a slash appended
    // so we need to follow it
    get.setFollowRedirects(true);
    get.setExpectedResponseCode(200);
    get.setExpectedResponseLine("welcome1");
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
    get.setFollowRedirects(true);
    get.setExpectedResponseCode(200);
    get.setExpectedResponseLine("welcome2");
    get.connectToServerAndAssert();
  }

  /**
   * The filter should be triggered via the wildcard servlet matching rule in
   * web.xml:
   *
   * <filter-mapping>
   *   <filter-name>WildcardServleDispatchFilter</filter-name>
   *   <servlet-name>*</servlet-name>
   *   <dispatcher>FORWARD</dispatcher>
   *   <dispatcher>INCLUDE</dispatcher>
   *   <dispatcher>REQUEST</dispatcher>
   * </filter-mapping>
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testWildcardServletIncludeFilter()
      throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri(
        "/contextpath/DispatcherInclude?includeTarget=/IncludedDispatcherFilterTest");
    get.setExpectedResponseCode(200);
    get.setExpectedResponseLine(
        "Hello from wildcardDispatchFilter");
    get.connectToServerAndAssert();
  }

  /**
   * The filter should be triggered via the wildcard servlet matching rule in
   * web.xml:
   *
   * <filter-mapping>
   *   <filter-name>WildcardServleDispatchFilter</filter-name>
   *   <servlet-name>*</servlet-name>
   *   <dispatcher>FORWARD</dispatcher>
   *   <dispatcher>INCLUDE</dispatcher>
   *   <dispatcher>REQUEST</dispatcher>
   * </filter-mapping>
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testWildcardServletForwardFilter() throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri(
        "/contextpath/DispatcherForward?forwardTarget=/IncludedDispatcherFilterTest");
    get.setExpectedResponseCode(200);
    get.setExpectedResponseLine(
        "Hello from wildcardDispatchFilter");
    get.connectToServerAndAssert();
  }

  /**
   * The filter should be triggered via the wildcard servlet matching rule in
   * web.xml:
   *
   * <filter-mapping>
   *   <filter-name>WildcardServleDispatchFilter</filter-name>
   *   <servlet-name>*</servlet-name>
   *   <dispatcher>FORWARD</dispatcher>
   *   <dispatcher>INCLUDE</dispatcher>
   *   <dispatcher>REQUEST</dispatcher>
   * </filter-mapping>
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testWildcardServletRequestFilter() throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri("/contextpath/IncludedDispatcherFilterTest");
    get.setExpectedResponseCode(200);
    get.setExpectedResponseLine(
        "Hello from wildcardDispatchFilter");
    get.connectToServerAndAssert();
  }

  /**
   * The filter should be triggered via the mixed servlet matching rule in
   * web.xml:
   *
   * <filter-mapping>
   *   <filter-name>MixedMatchingFilter</filter-name>
   *   <url-pattern>/DispatcherInclude</url-pattern>        <==
   *   <url-pattern>/DispatcherForward</url-pattern>
   *   <servlet-name>DispatcherFilterTest</servlet-name>
   *   <dispatcher>REQUEST</dispatcher>
   *   <dispatcher>INCLUDE</dispatcher>                       <==
   *   <dispatcher>FORWARD</dispatcher>
   * </filter-mapping>
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testMixedServletIncludeFilter()
      throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri(
        "/contextpath/DispatcherInclude?includeTarget=/IncludedDispatcherFilterTest");
    get.setExpectedResponseCode(200);
    get.setExpectedResponseLine(
        "Hello from MixedMatchingFilter");
    get.connectToServerAndAssert();
  }

  /**
   * The filter should be triggered via the mixed servlet matching rule in
   * web.xml:
   *
   * <filter-mapping>
   *   <filter-name>MixedMatchingFilter</filter-name>
   *   <url-pattern>/DispatcherInclude</url-pattern>
   *   <url-pattern>/DispatcherForward</url-pattern>          <==
   *   <servlet-name>DispatcherFilterTest</servlet-name>
   *   <dispatcher>REQUEST</dispatcher>
   *   <dispatcher>INCLUDE</dispatcher>
   *   <dispatcher>FORWARD</dispatcher>                       <==
   * </filter-mapping>
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testMixedServletForwardFilter() throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri(
        "/contextpath/DispatcherForward?forwardTarget=/IncludedDispatcherFilterTest");
    get.setExpectedResponseCode(200);
    get.setExpectedResponseLine(
        "Hello from MixedMatchingFilter");
    get.connectToServerAndAssert();
  }

  /**
   * The filter should be triggered via the mixed servlet matching rule in
   * web.xml:
   *
   * <filter-mapping>
   *   <filter-name>MixedMatchingFilter</filter-name>
   *   <url-pattern>/DispatcherInclude</url-pattern>
   *   <url-pattern>/DispatcherForward</url-pattern>
   *   <servlet-name>DispatcherFilterTest</servlet-name>     <==
   *   <dispatcher>REQUEST</dispatcher>                      <==
   *   <dispatcher>INCLUDE</dispatcher>
   *   <dispatcher>FORWARD</dispatcher>
   * </filter-mapping>
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testMixedServletRequestFilter() throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri("/contextpath/DispatcherFilterTest");    // direct request
    get.setExpectedResponseCode(200);
    get.setExpectedResponseLine(
        "Hello from MixedMatchingFilter");
    get.connectToServerAndAssert();
  }


  /**
   * Used to test the invocation order of filters.
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testFilterOrder1() throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri("/contextpath/FilterOrder1Testlet");
    get.setExpectedResponseCode(200);
    get.setExpectedResponseLine(
            "FilterOrder2Filter-FilterOrder1Filter-FilterOrder1Testlet");
    get.connectToServerAndAssert();
  }

  /**
   * Used to test the invocation order of filters.
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testFilterOrder2() throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri("/contextpath/FilterOrder2Testlet");
    get.setExpectedResponseCode(200);
    get.setExpectedResponseLine(
            "FilterOrder2Filter-FilterOrder1Filter-FilterOrder2Testlet");
    get.connectToServerAndAssert();
  }

  /**
   * Test if we can load a static file via include (an absolute path that is
   * relative to the current context).
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testStaticFileInclude()
      throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri("/contextpath/StaticFileIncludeTest");
    get.setExpectedResponseCode(200);
    get.setExpectedResponseLine(
        "Included.");
    get.connectToServerAndAssert();
  }

  /**
   * Test a wildcard servlet mapping will return request paths correctly.
   * E.g.
   * mapping: /foo/* FooServlet
   * URL: /foo/bar
   * ServletPath: /foo
   * Pathinfo: /bar
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testRequestPathsUnderWildcardServletMapping()
      throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri("/contextpath/servletpath/pathinfo");
    get.setExpectedContentType("text/plain");
    get.setExpectedResponseCode(200);
    get.setExpectedResponseLine("servletPath=/servletpath");
    get.setExpectedResponseLine("pathInfo=/pathinfo");
    get.connectToServerAndAssert();
  }

  /**
   * Test an exact servlet mapping will return path info as null.
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testRequestPathInfoExactServletMapping()
      throws Exception {
    HttpRequestAsserter get = createGetAssertion();
    get.setUri("/contextpath/servletpath/exactmatch");
    get.setExpectedContentType("text/plain");
    get.setExpectedResponseCode(200);
    get.setExpectedResponseLine("pathInfo=null");
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

  /**
   * Test HttpSession.setMaxInactiveInterval() method
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testSessionMaxInactiveInterval() throws Exception {
    // uses com.google.opengse.testlet.HttpSession.SessionMaxInactiveIntervalTestlet on the server-side
    HttpRequestAsserter get = createGetAssertion("/contextpath/sessionmaxinactiveinterval");
    get.setExpectedPassResponseLine();
    get.connectToServerAndAssert();
  }

/*
  @Test
  public void testSessionExpired()
      throws Exception {
    // uses com.google.opengse.testlet.HttpSession.SessionExpiredTestlet on the server-side
    HttpRequestAsserter get = createGetAssertion("/contextpath/sessionexpired");
    get.setExpectedPassResponseLine();
    get.connectToServerAndAssert();
  }
*/

  /**
   * com.google.opengse.testlet.Filter.WildcardFilter is mapped to "/*" but only services
   * a request if it sees "/servletpath3/" in either the pathInfo or servletPath. If it services
   * the request, it prints out the value of the servletPath and pathInfo request attributes.
   *
   * From HttpServletRequest.getServletPath() javadoc:
   *
   * "  @return		a <code>String</code> containing
   *  			the name or path of the servlet being
   *  			called, as specified in the request URL,
   *  			decoded, or an empty string if the servlet
   *  			used to process the request is matched
   *  			using the "/*" pattern."
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testWildcardFilterPaths()
      throws Exception {
    HttpRequestAsserter get = createGetAssertion("/contextpath/servletpath3/foo");
    get.setExpectedContentType("text/plain");
    get.setExpectedResponseCode(200);
    get.setExpectedResponseLine("WildcardFilter.servletPath=");      // =""
    get.setExpectedResponseLine("WildcardFilter.pathInfo=/servletpath3/foo");
    get.connectToServerAndAssert();
  }

}
