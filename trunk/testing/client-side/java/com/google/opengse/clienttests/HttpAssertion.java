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

import java.net.*;
import java.io.*;
import java.util.*;

/**
 * Based on jakarta-watchdog's GTest custom ant task.
 * Any mistakes in this code can be attributed to Mike Jennings.
 * 
 * @author Mike Jennings
 */
public class HttpAssertion {
  private static final String ZEROS = "00000000";
  private static final String CRLF = "\r\n";
  private static final int SHORTPADSIZE = 4;
  private static final int BYTEPADSIZE = 2;
  private static final int CARRIAGE_RETURN = 13;
  private static final int LINE_FEED = 10;

  private static int failureCount = 0;
  private static int passCount = 0;
  private static boolean hasFailed = false;

  String prefix = "http";
  String host = "localhost";
  String localHost = null;
  String localIP = null;
  int port = 8080;
  int debug = 0;

  String description = "No description";

  String request;
  Headers requestHeaders = new Headers();
  String content;

  // true if task is nested
  private boolean nested = false;

  // Expected response
  boolean magnitude = true;
  boolean exactMatch = false;

  // expect a response body
  boolean expectResponseBody = true;

  // Match the body against a resource (the "golden" result)
  String goldenResource;
  // Match the body against a string
  String responseMatch;
  // the response should include the following headers
  Headers expectHeaders = new Headers();

  // Headers that should not be found in response
  Headers unexpectedHeaders = new Headers();

  // Match request line
  String returnCode = "";
  String returnCodeMsg = "";

  // Actual response
  String responseLine;
  byte[] responseBody;
  Headers headers;


  // For Report generation
  static String resultFileName = null;
  static FileOutputStream resultOut = null;
  boolean firstTask = false;
  boolean lastTask = false;
  String expectedString;
  String actualString;

  String testName;
  String assertion;
  String testStrategy;

  URL requestURL;
  PrintWriter sysout;
  private final Properties props;
  private final String hostKey;
  private final String portKey;
  private final String requestKey;
  private final String requestHeaderKey;
  private final String responseHeaderKey;

  /**
   * Creates a new <code>GTest</code> instance.
   */
  public HttpAssertion(Properties props, PrintWriter out) {
    this.props = props;
    sysout = out;
    hostKey = getUnusedKey("host");
    portKey = getUnusedKey("port");
    requestKey = getUnusedKey("request");
    requestHeaderKey = getUnusedKey("requestHeader");
    responseHeaderKey = getUnusedKey("responseHeader");
  }

  private String getUnusedKey(String key) {
    if (!props.containsKey(key)) {
      return key;
    }
    for (int i = 1; i < 1000; ++i) {
      String ukey = key + i;
      if (!props.containsKey(ukey)) {
        return ukey;
      }
    }
    return getUnusedKey("_" + key);
  }

  /**
   * <code>setRequestHeaders</code> Configures the request
   * headers this should send to the target server.
   *
   * @param s a <code>String</code> value in for format
   *          of <field-name>:<field-value>
   */
  public void setRequestHeaders(String s) {
    requestHeaders.clear();
    StringTokenizer tok = new StringTokenizer(s, "|");
    while (tok.hasMoreElements()) {
      String header = (String) tok.nextElement();
      props.setProperty(requestHeaderKey, header);
      header = getAliasedProperty(requestHeaderKey);
      requestHeaders.addRequestHeader(header);
    }
  }

  /**
   * <code>setTestName</code> sets the current test name.
   *
   * @param tn current testname.
   */
  public void setTestName(String tn) {
    testName = tn;
  }

  /**
   * <code>setAssertion</code> sets the assertion text
   * for the current test.
   *
   * @param assertion assertion text
   */
  public void setAssertion(String assertion) {
    this.assertion = assertion;
  }

  /**
   * <code>setTestStrategy</code> sets the test strategy
   * for the current test.
   *
   * @param strategy test strategy text
   */
  public void setTestStrategy(String strategy) {
    testStrategy = strategy;
  }

  /**
   * <code>getTestName</code> returns the current
   * test name.
   *
   * @return a <code>String</code> value
   */
  public String getTestName() {
    return testName;
  }

  /**
   * <code>getAssertion</code> returns the current
   * assertion text.
   *
   * @return a <code>String</code> value
   */
  public String getAssertion() {
    return assertion;
  }

  /**
   * <code>getTestStrategy</code> returns the current
   * test strategy test.
   *
   * @return a <code>String</code> value
   */
  public String getTestStrategy() {
    return testStrategy;
  }

  /**
   * <code>setResultFileName</code> allows the user
   * to set the filename in which to write test results
   * to.
   *
   * @param fileName result filename
   * @throws IOException if an error occurs
   */
  public void setResultFileName(String fileName)
      throws IOException {
    if (firstTask) {
      resultFileName = fileName;
      File passedFile = new File(fileName);
      sysout.println(
          "Full Path of Result File-> " + passedFile.getAbsolutePath());
      resultOut = new FileOutputStream(passedFile);
      resultOut.write("<root>\n".getBytes());
    }
  }

  /**
   * <code>setFirstTask</code> denotes that current task
   * being executed is the first task within the list.
   *
   * @param val <code>boolean</code> value
   */
  public void setFirstTask(boolean val) {
    firstTask = val;
  }

  public void setFirstTask(String sval) {
    firstTask = Boolean.parseBoolean(sval);
  }

  /**
   * <code>setLastTask</code> denotes that the current task
   * being executed is the last task within the list.
   *
   * @param val <code>boolean</code> value
   */
  public void setLastTask(boolean val) {
    lastTask = val;
  }

  /**
   * <code>setPrefix</code> sets the protocol
   * prefix.  Defaults to "http"
   *
   * @param prefix Either http or https
   */
  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  /**
   * <code>setHost</code> sets hostname where
   * the target server is running. Defaults to
   * "localhost". An aliased property can be used, for example "${host}".
   *
   * @param h a <code>String</code> value
   */
  public void setHost(String h) {
    // set the unused hostKey property to the value in h
    props.setProperty(hostKey, h);
    // lookup the (possibly aliased) hostKey value
    this.host = getAliasedProperty(hostKey);
  }

  private String getAliasedProperty(String key) {
    return getAliasedProperty(key, null);
  }

  private String getAliasedProperty(String key, String def) {
    return PropertiesUtil.getAliasedProperty(props, key, def);
  }

  /**
   * <code>setPort</code> sets the port
   * that the target server is listening on.
   * Aliased values like "${port}" can be used.
   *
   * @param portS a <code>String</code> value
   */
  public void setPort(String portS) {
    props.setProperty(portKey, portS);
    this.port = Integer.parseInt(getAliasedProperty(portKey));
  }

  /**
   * <code>setExactMatch</code> determines if a
   * byte-by-byte comparsion is made of the server's
   * response and the test's goldenFile, or if
   * a token comparison is made.  By default, only
   * a token comparison is made ("false").
   *
   * @param exact a <code>String</code> value
   */
  public void setExactMatch(String exact) {
    exactMatch = Boolean.parseBoolean(exact);
  }

  public void setExactMatch(boolean exact) {
    exactMatch = exact;
  }

  /**
   * <code>setContent</code> String value upon which
   * the request header Content-Length is based upon.
   *
   * @param s a <code>String</code> value
   */
  public void setContent(String s) {
    this.content = s;
  }

  /**
   * <code>setDebug</code> enables debug output.
   * By default, this is disabled ( value of "0" ).
   *
   * @param debugS a <code>String</code> value
   */
  public void setDebug(String debugS) {
    debug = Integer.parseInt(debugS);
  }

  /**
   * <code>setMagnitude</code> Expected return
   * value of the test execution.
   * Defaults to "true"
   *
   * @param magnitudeS a <code>String</code> value
   */
  public void setMagnitude(String magnitudeS) {
    magnitude = Boolean.parseBoolean(magnitudeS);
  }

  public void setGoldenResource(String gr) {
    this.goldenResource = gr;
  }

  /**
   * <code>setExpectResponseBody</code> sets a flag
   * to indicate if a response body is expected from the
   * server or not
   *
   * @param b a <code>boolean</code> value
   */
  public void setExpectResponseBody(boolean b) {
    this.expectResponseBody = b;
  }


  public void setExpectResponseBody(String sval) {
    this.expectResponseBody = Boolean.parseBoolean(sval);
  }

  /**
   * <code>setExpectHeaders</code> Configures GTest
   * to look for the header passed in the server's
   * response.
   *
   * @param s a <code>String</code> value in the
   *          format of <header-field>:<header-value>
   */
  public void setExpectHeaders(String s) {
    this.expectHeaders = new Headers();
    StringTokenizer tok = new StringTokenizer(s, "|");
    while (tok.hasMoreElements()) {
      String header = (String) tok.nextElement();
      props.setProperty(responseHeaderKey, header);
      header = getAliasedProperty(responseHeaderKey);
      expectHeaders.addResponseHeader(header);
    }
  }

  /**
   * <code>setUnexpectedHeaders</code> Configures GTest
   * to look for the header passed to validate that it
   * doesn't exist in the server's response.
   *
   * @param s a <code>String</code> value in the
   *          format of <header-field>:<header-value>
   */
  public void setUnexpectedHeaders(String s) {
    this.unexpectedHeaders = new Headers();
    unexpectedHeaders.addResponseHeader(s);
  }

  public void setNested(String s) {
    nested = Boolean.parseBoolean(s);
  }

  /**
   * <code>setResponseMatch</code> Match the
   * passed value in the server's response.
   *
   * @param s a <code>String</code> value
   */
  public void setResponseMatch(String s) {
    this.responseMatch = s;
  }

  /**
   * <code>setRequest</code> Sets the HTTP/HTTPS
   * request to be sent to the target server
   * Ex.
   * GET /servlet_path/val HTTP/1.0
   *
   * @param s a <code>String</code> value in the form
   *          of METHOD PATH HTTP_VERSION
   */
  public void setRequest(String s) {
    props.setProperty(requestKey, s);
    this.request = getAliasedProperty(requestKey);
  }

  /**
   * Private utility method to 'massage' a request string that
   * may or may not have replacement markers for the request parameters.
   *
   * @param req    the request to manipulate
   * @param socket local socket.  Used to rebuild specified query strings.
   * @throws IOException if an error occurs
   */
  private void rebuildRequest(String req, Socket socket) throws IOException {
    this.request = replaceMarkers(req, socket);
    String addressString =
        request.substring(request.indexOf("/"), request.indexOf("HTTP")).trim();

    if (addressString.indexOf("?") > -1) {
      addressString = addressString.substring(0, addressString.indexOf("?"));
    }

    requestURL = new URL("http", host, port, addressString);
  }

  /**
   * <code>setReturnCode</code> Sets the expected
   * return code from the server's response.
   *
   * @param code a valid HTTP response status code
   */
  public void setReturnCode(String code) {
    this.returnCode = code;
  }

  public void setReturnCode(int code) {
    this.returnCode = Integer.toString(code);
  }

  /**
   * Describe <code>setReturnCodeMsg</code> Sets the expected
   * return message to be found in the server's
   * response.
   *
   * @param message a <code>String</code> value
   */
  public void setReturnCodeMsg(String message) {
    this.returnCodeMsg = message;
  }


  /**
   * <code>execute</code> Executes the test.
   *
   * @throws IOException if an error occurs
   */
  public boolean hasFailed() throws IOException {
    int currentFailureCount = failureCount;
    try {

      if (resultOut != null && !nested) {
        resultOut.write("<test>".getBytes());
        resultOut.write(("\n<testName>" + testName + "</testName>").getBytes());
        resultOut.write(
            ("\n<assertion>" + assertion + "</assertion>").getBytes());
        resultOut.write(
          ("\n<testStrategy>" + testStrategy + "</testStrategy>\n").getBytes());
      }

      dispatch(requestHeaders);

      boolean result = checkResponse(magnitude);

      if (!result) {
        hasFailed = true;
      }


      if (!hasFailed && !nested) {
        passCount++;
        if (resultOut != null) {
          resultOut.write("<result>PASS</result>\n".getBytes());
        }
        if (testName != null) {
          sysout.println(" PASSED " + testName + "\n        (" + request + ")");
        } else {
          sysout.println(" PASSED " + request);
        }
      } else if (hasFailed && !nested) {
        failureCount++;
        if (resultOut != null) {
          resultOut.write("<result>FAIL</result>\n".getBytes());
        }
        if (testName != null) {
          sysout.println(" FAILED " + testName + "\n        (" + request + ")");
        } else {
          sysout.println(" FAILED " + request);
        }
      }

      if (resultOut != null && !nested) {
        resultOut.write("</test>\n".getBytes());

        if (lastTask) {
          resultOut.write("</root>\n".getBytes());
          resultOut.close();
        }
      }
      if (lastTask) {
        sysout.println("\n\n------- TEST SUMMARY -------\n");
        sysout.println("*** " + passCount + " TEST(S) PASSED! ***");
        sysout.println("*** " + failureCount + " TEST(S) FAILED! ***");
      }

    } catch (IOException ex) {
      failureCount++;
      if ("No description".equals(description)) {
        sysout.println(" FAIL " + request);
      } else {
        sysout.println(" FAIL " + description + " (" + request + ")");
      }
      ex.printStackTrace();
    } finally {
      if (!nested) {
        hasFailed = false;
      }
    }
    return (failureCount > currentFailureCount);
  }

  /**
   * <code>checkResponse</code> Executes various response
   * checking mechanisms against the server's response.
   * Checks include:
   * <ul>
   * <li>expected headers
   * <li>unexpected headers
   * <li>return codes and messages in the Status-Line
   * <li>response body comparison againt a goldenfile
   * </ul>
   *
   * @param testCondition a <code>boolean</code> value
   * @return a <code>boolean</code> value
   * @throws Exception if an error occurs
   */
  private boolean checkResponse(boolean testCondition) throws IOException {
    boolean match;

    if (responseLine != null) {
      // If returnCode doesn't match
      if (responseLine.indexOf("HTTP/1.") > -1) {

        if (!returnCode.equals("")) {
          boolean resCode = (responseLine.indexOf(returnCode) > -1);
          boolean resMsg = (responseLine.indexOf(returnCodeMsg) > -1);

          if (returnCodeMsg.equals("")) {
            match = resCode;
          } else {
            match = (resCode && resMsg);
          }

          if (match != testCondition) {
            sysout.println(" Error in: " + request);
            sysout.println("    Expected Status-Line with one or all of the following values:");
            sysout.println("    Status-Code: " + returnCode);
            sysout.println("    Reason-Phrase: " + returnCodeMsg);
            sysout.println("    Received: " + responseLine);

            if (resultOut != null) {
              String expectedStatusCode = "<expectedStatusCode>"
                  + returnCode + "</expectedReturnCode>\n";
              String expectedReasonPhrase = "<expectedReasonPhrase>"
                    + returnCodeMsg + "</expectedReasonPhrase>";
              actualString = "<actualStatusLine>"
                    + responseLine + "</actualStatusLine>\n";
              resultOut.write(expectedStatusCode.getBytes());
              resultOut.write(expectedReasonPhrase.getBytes());
              resultOut.write(actualString.getBytes());
            }
            return false;
          } else {
            if (debug > 0) {
              sysout.println(" Expected values found in Status-Line");
            }
          }
        }
      } else {
        sysout.println(
                "  Error:  Received invalid HTTP version in response header from target Server");
        sysout.println(
                "         Target server must support HTTP 1.0 or HTTP 1.1");
        sysout.println(
                "         Response from server: " + responseLine);
        return false;
      }
    } else {
      sysout.println(" Error in: " + request);
      sysout.println("        Expecting response from server, received null");
      return false;
    }

    /*
     * Check for headers the test expects to be in the server's response
     */

    // Duplicate set of response headers
    Headers copiedHeaders = headers.cloneHeaders();

    // used for error reporting
    String currentHeaderField = null;
    String currentHeaderValue = null;

    if (!expectHeaders.isEmpty()) {
      boolean found = false;

      if (debug > 0) {
        sysout.println(" Looking for expected response headers...");
      }

      if (!headers.isEmpty()) {
        for (String expFieldName : expectHeaders.headerNames()) {
          found = false;
          currentHeaderField = expFieldName;
          List<String> expectValues = expectHeaders.getValues(expFieldName);
          for (String headerFieldName : copiedHeaders.headerNames()) {
            List<String> headerValues
                = copiedHeaders.getValues(headerFieldName);

            // compare field names and values in an HTTP 1.x compliant fashion
            if ((headerFieldName.equalsIgnoreCase(expFieldName))) {
              int eSize = expectValues.size();

              // number of expected headers found in server response
              int numberFound = 0;

              for (int i = 0; i < eSize; i++) {
                currentHeaderValue = expectValues.get(i);

                /*
                * Handle the Content-Type header appropriately
                * based on the the test is configured to look for.
                */
                if (currentHeaderField.equalsIgnoreCase("content-type")) {
                  String resVal = headerValues.get(0);
                  if (currentHeaderValue.indexOf(';') > -1) {
                    if (debug > 0) {
                      sysout.println(
                          " Exact match for Content-Type header required.");
                    }
                    if (contentTypeValuesAreTheSame(
                            currentHeaderValue, resVal)) {
                      numberFound++;
                      headerValues.remove(0);
                    }
                  } else if (resVal.indexOf(currentHeaderValue) > -1) {
                    if (debug > 0) {
                      sysout.println(
                        " Approximate match for Content-Type header required.");
                    }
                    numberFound++;
                    headerValues.remove(0);
                  }
                } else if (currentHeaderField.equalsIgnoreCase("location")) {
                  String resVal = headerValues.get(0);
                  int idx = currentHeaderValue.indexOf(":80/");
                  if (idx > -1) {
                    String tempValue = currentHeaderValue.substring(0, idx) +
                        currentHeaderValue.substring(idx + 3);
                    if (currentHeaderValue.equals(resVal) ||
                        tempValue.equals(resVal)) {
                      numberFound++;
                      headerValues.remove(0);
                    }
                  } else {
                    if (currentHeaderValue.equals(resVal)) {
                      numberFound++;
                      headerValues.remove(0);
                    }
                  }
                } else if (headerValues.contains(currentHeaderValue)) {
                  numberFound++;
                  headerValues.remove(headerValues.indexOf(currentHeaderValue));
                }
              }
              if (numberFound == eSize) {
                found = true;
              }
            }
          }
          if (!found) {
            /*
            * Expected headers not found in server response.
            * Break the processing loop.
            */
            break;
          }
        }
      }

      if (!found) {
        StringBuilder actualBuffer = new StringBuilder(128);
        sysout.printf(
            " Unable to find the expected header: '%s:%s' in the server's response.%n",
            currentHeaderField,  currentHeaderValue);
        if (resultOut != null) {
          expectedString = "<expectedHeader>"
              + currentHeaderField + ": " + currentHeaderValue
              + "</expectedHeader>\n";
        }
        if (!headers.isEmpty()) {
          sysout.println(" The following headers were received: ");
          for (String headerName : headers.headerNames()) {
            List<String> vals = headers.getValues(headerName);
            String[] val = vals.toArray(new String[vals.size()]);
            for (int i = 0; i < val.length; i++) {
              sysout.println("\tHEADER -> '" + headerName + "': '" + val[i] + "'");
              if (resultOut != null) {
                actualBuffer.append("<actualHeader>")
                    .append(headerName).append(": ")
                    .append(val[i])
                    .append("</actualHeader>\n");
              }
            }
          }
          if (resultOut != null) {
            resultOut.write(expectedString.getBytes());
            resultOut.write(actualBuffer.toString().getBytes());
          }
        }
        return false;
      }
    }

    /*
    * Check to see if we're looking for unexpected headers.
    * If we are, compare the values in the unexectedHeaders
    * ArrayList against the headers from the server response.
    * if the unexpected header is found, then return false.
    */

    if (!unexpectedHeaders.isEmpty()) {
      boolean found = false;
      if (debug > 0) {
        sysout.println(" looking for unexpected headers...");
      }

      // Check if we got any unexpected headers

      if (!copiedHeaders.isEmpty()) {
        for (String unexpectedFieldName : unexpectedHeaders.headerNames()) {
          found = false;
          List<String> unexpectedValues
              = unexpectedHeaders.getValues(unexpectedFieldName);

          for (String headerFieldName : copiedHeaders.headerNames()) {
            List<String> headerValues
                = copiedHeaders.getValues(headerFieldName);

            // compare field names and values in an HTTP 1.x compliant fashion
            if ((headerFieldName.equalsIgnoreCase(unexpectedFieldName))) {
              int eSize = unexpectedValues.size();
              int numberFound = 0;
              for (int i = 0; i < eSize; i++) {
                if (headerValues.contains(unexpectedValues.get(i))) {
                  numberFound++;
                  headerValues.remove(
                          headerValues.indexOf(unexpectedValues.get(i)));
                }
              }
              if (numberFound == eSize) {
                sysout.println(" Unexpected header received from server: "
                        + unexpectedFieldName);
                found = true;
              }
            }
          }
          if (!found) {
            /*
            * Expected headers not found in server response.
            * Break the processing loop.
            */
            break;
          }
        }
      }

      if (found) {
        return false;
      }
    }


    if (responseMatch != null) {
      // check if we got the string we wanted
      if (expectResponseBody && responseBody == null) {
        sysout.println(" ERROR: got no response, expecting " + responseMatch);
        return false;
      }
      String responseBodyString = new String(responseBody);
      if (responseBodyString.indexOf(responseMatch) < 0) {
        sysout.println(" ERROR: expecting match on " + responseMatch);
        sysout.println("Received: ");
        sysout.println(responseBodyString);
      }
    }

    if (!expectResponseBody && responseBody != null) {
      if (debug > 0) {
        sysout.println(
            "Received a response body from the server where none was expected");
      }
      return false;
    }

    // compare the body
    if (goldenResource == null) {
      // unspecified resource to compare the body against means no comparison
      // occurs, hence the positive result.
      return true;
    }

    // Get the expected result from the "golden" file.
    byte[] expResult = getExpectedResult();

    // Compare the results and set the status
    boolean cmp;

    if (exactMatch) {
      if (debug > 0) {
        sysout.println(
            " Performing exact match of server response and goldenfile");
      }
      cmp = compare(responseBody, expResult);
    } else {
      if (debug > 0) {
        sysout.println(
            " Performing token match of server response and goldenfile");
      }
      cmp = compareWeak(responseBody, expResult);
    }

    if (cmp != testCondition) {

      if (resultOut != null) {
        expectedString = "<expectedBody>"
            + new String(expResult) + "</expectedBody>\n";
        actualString = "<actualBody>"
            + new String(responseBody) + "</actualBody>\n";
        resultOut.write(expectedString.getBytes());
        resultOut.write(actualString.getBytes());
      }
      return false;
    }

    return true;
  }


  private static boolean contentTypeValuesAreTheSame(
          String contentType1, String contentType2) {
    contentType1 = normalizeContentType(contentType1);
    contentType2 = normalizeContentType(contentType2);
    return contentType1.equals(contentType2);
  }

  private static String normalizeContentType(String contentType) {
    StringTokenizer st = new StringTokenizer(contentType, ";");
    StringBuilder sb = null;
    while (st.hasMoreTokens()) {
      if (sb == null) {
        sb = new StringBuilder();
        sb.append(st.nextToken().trim());
      } else {
        sb.append(';').append(st.nextToken().trim());
      }
    }
    return sb.toString();
  }

  /**
   * Sends the request and any configured request headers to the target server.
   */
  private void dispatch(Headers reqHeaders) throws IOException {
    // XXX headers are ignored
    Socket socket = new Socket(host, port);

    //socket obtained, rebuild the request.
    rebuildRequest(request, socket);

    InputStream in = new CRBufferedInputStream(socket.getInputStream());

    // Write the request
    socket.setSoLinger(true, 1000);

    OutputStream out = new BufferedOutputStream(
        socket.getOutputStream());
    StringBuffer reqbuf = new StringBuffer(128);

    // set the Host header
    reqHeaders.addRequestHeader("Host:" + host + ":" + port);

    // set the Content-Length header
    if (content != null) {
      reqHeaders.addRequestHeader("Content-Length:" + content.length());
    }


    if (debug > 0) {
      sysout.println(" REQUEST: " + request);
    }
    reqbuf.append(request).append(CRLF);

    // append all request headers
    for (String headerKey : reqHeaders.headerNames()) {
      StringBuffer tmpBuf = new StringBuffer(32);
      tmpBuf.append(headerKey).append(": ");
      List<String> values = reqHeaders.getValues(headerKey);
      for (int i = 0; i < values.size(); i++) {
        tmpBuf.append(values.get(i));   // follow GTest, and browsers
        if (i < values.size() - 1) {
          tmpBuf.append(", ");
        }
      }

      if (debug > 0) {
        sysout.println(" REQUEST HEADER: " + tmpBuf.toString());
      }
      tmpBuf.append(CRLF);
      reqbuf.append(tmpBuf.toString());
    }

//    if ((testSession != null) && (sessionHash.get(testSession) != null)) {
//      sysout.println("Sending Session Id : "
//          + (String)sessionHash.get(testSession));
//      pw.println("JSESSIONID:" + (String) sessionHash.get(testSession));
//    }

    if (request.indexOf("HTTP/1.") > -1) {
      reqbuf.append("").append(CRLF);
    }

    // append request content
    if (content != null) {
      reqbuf.append(content);
      // XXX no CRLF at the end -see HTTP specs!
    }

    byte[] reqbytes = reqbuf.toString().getBytes();

    try {
      // write the request
      out.write(reqbytes, 0, reqbytes.length);
      out.flush();
    } catch (IOException e) {
      sysout.println(" Error writing request " + e);
      if (debug > 0) {
        sysout.println("Message: " + e.getMessage());
        e.printStackTrace();
      }
    }

    // read the response
    try {

      responseLine = read(in);

      if (debug > 0) {
        sysout.println(" RESPONSE STATUS-LINE: " + responseLine);
      }

      headers = new Headers();
      headers.parseHeaders(in);

      byte[] result = readBody(in);

      if (result != null) {
        responseBody = result;
        if (debug > 0) {
          sysout.println(" RESPONSE BODY:\n" + new String(responseBody));
        }
      }

    } catch (SocketException ex) {
      sysout.println(" Socket Exception: " + ex);
      ex.printStackTrace();
    } finally {
      if (debug > 0) {
        sysout.println(" closing socket");
      }
      socket.close();
    }
  }

  private static final String CLIENT_IP = "client.ip";
  private static final String CLIENT_HOME = "client.host";

  /**
   * Replaces any |client.ip| and |client.host| parameter marks
   * with the host and IP values of the host upon which Watchdog
   * is running.
   *
   * @param req    An HTTP request.
   * @param socket the socket
   * @return the request with the markers replaced
   */
  private String replaceMarkers(String req, Socket socket) {


    if (localIP == null || localHost == null) {
      InetAddress addr = socket.getLocalAddress();
      localHost = addr.getHostName();
      localIP = addr.getHostAddress();
    }

    if (req.indexOf('|') > -1) {
      StringTokenizer tok = new StringTokenizer(request, "|");
      StringBuffer sb = new StringBuffer(50);

      while (tok.hasMoreElements()) {
        String token = tok.nextToken();
        if (token.equals(CLIENT_IP)) {
          sb.append(localIP);
        } else if (token.equals(CLIENT_HOME)) {
          sb.append(localHost);
        } else {
          sb.append(token);
        }
      }
      return sb.toString();
    } else {
      return req;
    }
  }


  /**
   * <code>getExpectedResult</code> returns a byte array
   * containing the content of the configured goldenfile
   *
   * @return goldenfile as a byte[]
   * @throws IOException if an error occurs
   */
  private byte[] getExpectedResult() throws IOException {
    byte[] expResult = {'N', 'O', ' ',
        'G', 'O', 'L', 'D', 'E', 'N', 'F', 'I', 'L', 'E', ' ',
        'F', 'O', 'U', 'N', 'D'};

    try {
      InputStream in = getGoldenBytes();
      if (in == null) {
        throw new NullPointerException("Could not find " + goldenResource + " in the classpath");
      }
      return readBody(in);
    } catch (IOException ex) {
      sysout.println("Could not read from resource: " + goldenResource);
      return expResult;
    }
  }

  private InputStream getGoldenBytes() throws IOException {
    if (goldenResource == null) {
      throw new IOException("No resource found");
    }
    return Thread.currentThread()
        .getContextClassLoader()
        .getResourceAsStream(goldenResource);
  }

  /**
   * <code>compare</code> compares the two byte arrays passed
   * in to verify that the lengths of the arrays are equal, and
   * that the content of the two arrays, byte for byte are equal.
   *
   * @param fromServer     a <code>byte[]</code> value
   * @param fromGoldenFile a <code>byte[]</code> value
   * @return <code>boolean</code> true if equal, otherwise false
   */
  private boolean compare(byte[] fromServer, byte[] fromGoldenFile) {
    if (fromServer == null || fromGoldenFile == null) {
      return false;
    }

    /*
    * Check to see that the respose and golden file lengths
    * are equal.  If they are not, dump the hex and don't
    * bother comparing the bytes.  If they are equal,
    * iterate through the byte arrays and compare each byte.
    * If the bytes don't match, dump the hex representation
    * of the server response and the goldenfile and return
    * false.
    */
    if (fromServer.length != fromGoldenFile.length) {
      StringBuffer sb = new StringBuffer(50);
      sb.append(" Response and golden files lengths do not match!\n");
      sb.append(" Server response length: ");
      sb.append(fromServer.length);
      sb.append("\n Goldenfile length: ");
      sb.append(fromGoldenFile.length);
      sysout.println(sb.toString());
      // dump the hex representation of the byte arrays
      dumpHex(fromServer, fromGoldenFile);

      return false;
    } else {

      int i = 0;
      int j = 0;

      while ((i < fromServer.length) && (j < fromGoldenFile.length)) {
        if (fromServer[i] != fromGoldenFile[j]) {
          sysout.println(" Error at position " + (i + 1));
          // dump the hex representation of the byte arrays
          dumpHex(fromServer, fromGoldenFile);

          return false;
        }

        i++;
        j++;
      }
    }

    return true;
  }

  /**
   * <code>compareWeak</code> creates new Strings from the passed arrays
   * and then uses a StringTokenizer to compare non-whitespace tokens.
   *
   * @param fromServer     a <code>byte[]</code> value
   * @param fromGoldenFile a <code>byte[]</code> value
   * @return a <code>boolean</code> value
   */
  private boolean compareWeak(byte[] fromServer, byte[] fromGoldenFile) {
    if (fromServer == null || fromGoldenFile == null) {
      return false;
    }

    boolean status = true;

    String server = new String(fromServer);
    String golden = new String(fromGoldenFile);

    StringTokenizer st1 = new StringTokenizer(server);

    StringTokenizer st2 = new StringTokenizer(golden);

    while (st1.hasMoreTokens() && st2.hasMoreTokens()) {
      String tok1 = st1.nextToken();
      String tok2 = st2.nextToken();

      if (!tok1.equals(tok2)) {
        sysout.println("\t FAIL*** : Rtok1 = " + tok1
            + ", Etok2 = " + tok2);
        status = false;
      }
    }

    if (st1.hasMoreTokens() || st2.hasMoreTokens()) {
      status = false;
    }

    if (!status) {
      StringBuffer sb = new StringBuffer(255);
      sb.append("ERROR: Server's response and configured goldenfile do not match!\n");
      sb.append("Response received from server:\n");
      sb.append("---------------------------------------------------------\n");
      sb.append(server);
      sb.append("\nContent of Goldenfile:\n");
      sb.append("---------------------------------------------------------\n");
      sb.append(golden);
      sb.append("\n");
      sysout.println(sb.toString());
    }
    return status;
  }

  /**
   * <code>readBody</code> reads the body of the response
   * from the InputStream.
   *
   * @param input an <code>InputStream</code>
   * @return a <code>byte[]</code> representation of the response
   */
  private byte[] readBody(InputStream input) {
    StringBuffer sb = new StringBuffer(255);
    while (true) {
      try {
        int ch = input.read();

        if (ch < 0) {
          if (sb.length() == 0) {
            return (null);
          } else {
            break;
          }
        }
        sb.append((char) ch);

      } catch (IOException ex) {
        return null;
      }
    }
    return sb.toString().getBytes();
  }


  /**
   * Read a line from the specified servlet input stream, and strip off
   * the trailing carriage return and newline (if any).  Return the remaining
   * characters that were read as a string.7
   *
   * @param input the input stream
   * @return The line that was read, or <code>null</code> if end of file
   *         was encountered
   */
  private String read(InputStream input) {
    // Read the next line from the input stream
    StringBuffer sb = new StringBuffer();

    while (true) {
      try {
        int ch = input.read();
        if (ch < 0) {
          if (sb.length() == 0) {
            if (debug > 0) {
              sysout.println(" Error reading line " + ch + " " + sb.toString());
            }
            return "";
          } else {
            break;
          }
        } else if (ch == LINE_FEED) {
          break;
        }

        sb.append((char) ch);
      } catch (IOException ex) {
        sysout.println(" Error reading : " + ex);
        debug = 1;

        if (debug > 0) {
          sysout.println("Partial read: " + sb.toString());
          ex.printStackTrace();
        }
      }
    }
    return sb.toString();
  }

  /**
   * <code>dumpHex</code> helper method to dump formatted
   * hex output of the server response and the goldenfile.
   *
   * @param serverBytes a <code>byte[]</code> value
   * @param goldenBytes     a <code>byte[]</code> value
   */
  private void dumpHex(byte[] serverBytes, byte[] goldenBytes) {
    StringBuffer outBuf = new StringBuffer(
        (serverBytes.length + goldenBytes.length) * 2);

    outBuf.append(" Hex dump of server response and goldenfile below.\n");
    outBuf.append("\n");
    outBuf.append(" ### RESPONSE FROM SERVER ###\n");
    outBuf.append("----------------------------\n");
    outBuf.append(getHexValue(serverBytes, 0, serverBytes.length));
    outBuf.append("\n");
    outBuf.append("\n");
    outBuf.append(" ### GOLDEN FILE ###\n");
    outBuf.append("-------------------\n");
    outBuf.append(getHexValue(goldenBytes, 0, goldenBytes.length));
    outBuf.append("\n");
    outBuf.append("\n");
    outBuf.append(" ### END OF DUMP ###\n");

    sysout.println(outBuf.toString());

  }

  /**
   * <code>getHexValue</code> displays a formatted hex
   * representation of the passed byte array.  It also
   * allows for only a specified offset and length of
   * a particular array to be returned.
   *
   * @param bytes <code>byte[]</code> array to process.
   * @param pos   <code>int</code> specifies offset to begin processing.
   * @param len   <code>int</code> specifies the number of bytes to process.
   * @return <code>String</code> formatted hex representation of processed
   *         array.
   */
  private String getHexValue(byte[] bytes, int pos, int len) {
    StringBuffer outBuf = new StringBuffer(bytes.length * 2);
    int bytesPerLine = 36;
    int cnt = 1;
    int groups = 4;
    int curPos = pos;
    int linePos = 1;
    boolean displayOffset = true;

    while (len-- > 0) {
      if (displayOffset) {

        outBuf.append("\n").append(paddedHexString(pos, SHORTPADSIZE,
            true)).append(": ");
        displayOffset = false;
      }

      outBuf.append(paddedHexString(bytes[pos], BYTEPADSIZE, false));
      linePos += 2;  // Byte is padded to 2 characters

      if ((cnt % 4) == 0) {
        outBuf.append(" ");
        linePos++;
      }

      // Now display the characters that are printable
      if ((cnt % (groups * 4)) == 0) {
        outBuf.append(" ");

        while (curPos <= pos) {
          if (!Character.isWhitespace((char) bytes[curPos])) {
            outBuf.append((char) bytes[curPos]);
          } else {
            outBuf.append(".");
          }

          curPos++;
        }

        curPos = pos + 1;
        linePos = 1;
        displayOffset = true;
      }

      cnt++;
      pos++;
    }

    // pad out the line with spaces
    while (linePos++ <= bytesPerLine) {
      outBuf.append(" ");
    }

    outBuf.append(" ");
    // Now display the printable characters for the trailing bytes
    while (curPos < pos) {
      if (!Character.isWhitespace((char) bytes[curPos])) {
        outBuf.append((char) bytes[curPos]);
      } else {
        outBuf.append(".");
      }

      curPos++;
    }

    return outBuf.toString();
  }

  /**
   * <code>paddedHexString</code> pads the passed value
   * based on the specified wordsize and the value of the
   * prefixFlag.
   *
   * @param val        an <code>int</code> value
   * @param wordsize   an <code>int</code> value
   * @param prefixFlag a <code>boolean</code> value
   * @return a <code>String</code> value
   */
  private String paddedHexString(int val, int wordsize,
                                 boolean prefixFlag) {

    String hexPrefix = prefixFlag ? "0x" : "";
    String hexVal = Integer.toHexString(val);

    if (hexVal.length() > wordsize) {
      hexVal = hexVal.substring(hexVal.length() - wordsize);
    }

    return (hexPrefix + (wordsize > hexVal.length() ?
        ZEROS.substring(0, wordsize - hexVal.length()) : "") + hexVal);
  }


  /**
   * <code>CRBufferedInputStream</code> is a modified version of
   * the java.io.BufferedInputStream class.  The fill code is
   * the same, but the read is modified in that if a carriage return
   * is found in the response stream from the target server,
   * it will skip that byte and return the next in the stream.
   */
  private class CRBufferedInputStream extends BufferedInputStream {

    private static final int DEFAULT_BUFFER = 2048;

    /**
     * Creates a new <code>CRBufferedInputStream</code> instance.
     *
     * @param in an <code>InputStream</code> value
     */
    public CRBufferedInputStream(InputStream in) {
      super(in, DEFAULT_BUFFER);
    }

    /**
     * <code>read</code> reads a single byte value per call.
     * If, the byte read, is a carriage return, the next byte
     * in the stream in returned instead.
     *
     * @return an <code>int</code> value
     * @throws IOException if an error occurs
     */
    @Override public int read() throws IOException {
      if (in == null) {
        throw new IOException("Stream closed");
      }
      if (pos >= count) {
        fill();
        if (pos >= count) {
          return -1;
        }
      }
      int val = buf[pos++] & 0xff;
      if (val == CARRIAGE_RETURN) {
        if (pos >= count) {
          fill();
          if (pos >= count) {
            return -1;
          }
        }
        return buf[pos++] & 0xff;
      }
      return val;
    }

    /**
     * <code>fill</code> is used to fill the internal
     * buffer used by this BufferedInputStream class.
     *
     * @throws IOException if an error occurs
     */
    private void fill() throws IOException {
      if (markpos < 0) {
        pos = 0;        /* no mark: throw away the buffer */
      } else if (pos >= buf.length) {
        if (markpos > 0) {  /* can throw away early part of the buffer */
          int sz = pos - markpos;
          System.arraycopy(buf, markpos, buf, 0, sz);
          pos = sz;
          markpos = 0;
        } else if (buf.length >= marklimit) {
          markpos = -1;   /* buffer got too big, invalidate mark */
          pos = 0;    /* drop buffer contents */
        } else {        /* grow buffer */
          int nsz = pos * 2;
          if (nsz > marklimit) {
            nsz = marklimit;
          }
          byte nbuf[] = new byte[nsz];
          System.arraycopy(buf, 0, nbuf, 0, pos);
          buf = nbuf;
        }
      }
      count = pos;
      int n = in.read(buf, pos, buf.length - pos);
      if (n > 0) {
        count = n + pos;
      }
    }
  }
}

