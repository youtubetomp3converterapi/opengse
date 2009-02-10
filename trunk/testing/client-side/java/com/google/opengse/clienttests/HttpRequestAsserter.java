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

import com.google.opengse.clienttests.cookies.CookieUtil;
import com.google.opengse.clienttests.cookies.IteratorEnumeration;
import com.google.opengse.clienttests.cookies.Cookies;

import org.junit.Assert;

import javax.servlet.http.Cookie;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * @author jennings
 *         Date: Jun 19, 2008
 */
public class HttpRequestAsserter {
  private String urlToGet;
  private URL url;
  private boolean followRedirects;
  private boolean doInput;
  private final boolean doOutput;
  private String expectedContentType;
  private boolean shouldCheckContentType;
  private String expectedContentTypeCharset;
  private String host;
  private int port;
  private String goldenText;
  private final boolean disableKeepAlive;
  private Set<String> expectedResponseHeaders;
  private Set<String> unexpectedResponseHeaders;
  private Map<String, String> requestHeaders;
  private Set<String> expectedResponseLines;
  private String expectedContainedErrorMessage;
  private Set<String> unexpectedResponseLines;
  private Integer expectedResponseCode;
  Map<String, Set<String>> uheaders;
  private List<Cookie> requestCookies;
  private List<Cookie> responseCookies;
  private HttpURLConnection conn;
  private static final String CHARSET_EQUALS = "charset=";
  private Properties props;
  private String hostKey;
  private String portKey;
  private String bodyKey;

  public HttpRequestAsserter(Properties props) {
    this.props = props;
    followRedirects = false;
    doInput = true;
    doOutput = false;
    disableKeepAlive = true;
    setExpectedContentType("text/plain");
    expectedContentTypeCharset = null; // don't care
    expectedContainedErrorMessage = null; // don't care
    expectedResponseHeaders = null;
    unexpectedResponseHeaders = null;
    expectedResponseLines = null;
    unexpectedResponseLines = null;
    requestCookies = new ArrayList<Cookie>();
    hostKey = getUnusedKey("host");
    portKey = getUnusedKey("port");
    bodyKey = getUnusedKey("body");
    setHost("${host}");
    setPort("${port}");
  }

  public void setProperty(String key, String value) {
    if (value == null || value.indexOf('$') > -1) {
      throw new IllegalArgumentException("Cannot set " + key + "=" + value);
    }
    props.setProperty(key, value);
  }

  /**
   * <code>setHost</code> sets hostname where
   * the target server is running. Defaults to
   * "localhost". An aliased property can be used, for example "${host}".
   *
   * @param h a <code>String</code> value
   */
  public void setHost(String h) {
    if (h == null) {
      throw new NullPointerException();
    }
    // set the unused hostKey property to the value in h
    props.setProperty(hostKey, h);
    // lookup the (possibly aliased) hostKey value
    this.host = getAliasedProperty(hostKey);
    if (this.host == null) {
      throw new IllegalArgumentException("Could not resolve '" + h + "' to a hostname");
    }
  }

  public void addRequestCookie(Cookie cookie) {
    requestCookies.add(cookie);
  }

  /**
   * <code>setPort</code> sets the port
   * that the target server is listening on.
   * Aliased values like "${port}" can be used.
   *
   * @param portS a <code>String</code> value
   */
  public void setPort(String portS) {
    if (portS == null) {
      throw new NullPointerException();
    }
    props.setProperty(portKey, portS);
    String sport = getAliasedProperty(portKey);
    if (sport == null) {
      throw new IllegalArgumentException("Could not resolve '" + portS + "' to a hostname");
    }
    this.port = Integer.parseInt(sport);
  }


  private String getAliasedProperty(String key) {
    return getAliasedProperty(key, null);
  }

  private String getAliasedProperty(String key, String def) {
    return PropertiesUtil.getAliasedProperty(props, key, def);
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


  public void setUri(String uri) {
    this.urlToGet = "http://" + host + ":" + port + uri;
  }

  public void expectHeader(String headerName) {
    if (expectedResponseHeaders == null) {
      expectedResponseHeaders = new TreeSet<String>();
    }
    expectedResponseHeaders.add(headerName.toUpperCase());
  }

  public void doNotExpectHeader(String headerName) {
    if (unexpectedResponseHeaders == null) {
      unexpectedResponseHeaders = new TreeSet<String>();
    }
    unexpectedResponseHeaders.add(headerName.toUpperCase());
  }

  private boolean weHaveHeaderExpectations() {
    return (expectedResponseHeaders   != null && !expectedResponseHeaders.isEmpty() 
        || (unexpectedResponseHeaders != null && !unexpectedResponseHeaders.isEmpty()));
  }

  public Map<String, Set<String>> getNormalizedHeaders() {
    return uheaders;
  }


  private void getNormalizedHeaders(HttpURLConnection urlConnection) {
    Map<String, List<String>> headers = urlConnection.getHeaderFields();
    uheaders = convertHeadersToUppercase(headers);
  }

  private void grabResponseCookies() {
    Set<String> cookieStrings = uheaders.get("SET-COOKIE");
    if (cookieStrings == null || cookieStrings.isEmpty()) {
      responseCookies = new ArrayList<Cookie>();
    } else {
      responseCookies = Cookies.parse(new IteratorEnumeration<String>(cookieStrings.iterator()));
    }
  }

  public List<Cookie> getResponseCookies() {
    return responseCookies;
  }

  private void checkHeaderExpectations() {
    Set<String> actualHeaders = new TreeSet<String>(uheaders.keySet());
    /*
     * Check that we see all of the headers we expect to see
     * (don't care about extra headers)
     */
    if (expectedResponseHeaders != null) {
      // add all of the headers we got back to our set of expected headers
      expectedResponseHeaders.addAll(actualHeaders);
      assertEquals("Expected header(s) not found", expectedResponseHeaders, actualHeaders);
    }

    if (unexpectedResponseHeaders != null) {
      Set<String> copy = new TreeSet<String>(actualHeaders);
      copy.removeAll(unexpectedResponseHeaders);
      assertEquals("Unexpected header(s) found", copy, actualHeaders);
    }
  }

  private void assertEquals(
          String msg, Set<String> expected, Set<String> actual) {
    if (expected.equals(actual)) {
      return;
    }
    conn.disconnect();
    Assert.assertEquals(msg, toString(expected), toString(actual));
  }

  private String toString(Set<String> set) {
    StringBuilder sb = new StringBuilder();
    for (String s : set) {
      sb.append(s).append("\n");
    }
    return sb.toString();
  }

  private String toString(String[] lines) {
    StringBuilder sb = new StringBuilder();
    for (String s : lines) {
      sb.append(s).append("\n");
    }
    return sb.toString();
  }

  private static Set<String> toSet(String[] lines) {
    Set<String> set = new TreeSet<String>();
    Collections.addAll(set, lines);
//    for (String line : lines) {
//      set.add(line);
//    }
    return set;
  }

  private Map<String, Set<String>> convertHeadersToUppercase(
          Map<String, List<String>> headers) {
    Map<String, Set<String>> upperCaseHeaders
        = new TreeMap<String, Set<String>>();
    for (String header : headers.keySet()) {
      if (header != null) {
        List<String> values = headers.get(header);
        String upperCaseHeader = header.toUpperCase();
        upperCaseHeaders.put(upperCaseHeader, new TreeSet<String>(values));
      }
    }
    return upperCaseHeaders;
  }

  public void setRequestHeader(String name, String value) {
    if (requestHeaders == null) {
      requestHeaders = new TreeMap<String, String>();
    }
    requestHeaders.put(name, value);
  }

  public void connectToServerAndAssert() throws IOException {
    if (urlToGet == null && url == null) {
      Assert.fail("No uri set!");
    }
    if (url == null) {
      url = new URL(urlToGet);
    }
    conn = (HttpURLConnection) url.openConnection();
    conn.setInstanceFollowRedirects(followRedirects);
    conn.setDoInput(doInput);
    conn.setDoOutput(doOutput);
    conn.setAllowUserInteraction(false);
    conn.setUseCaches(false);
    if (disableKeepAlive) {
      conn.setRequestProperty("Connection", "Close");
    }
    if (requestHeaders != null) {
      for (String key : requestHeaders.keySet()) {
        String value = requestHeaders.get(key);
        conn.setRequestProperty(key, value);
      }
    }
    for (Cookie cookie : requestCookies) {
      conn.addRequestProperty("Cookie", CookieUtil.toString(cookie));
    }
    conn.connect();
    getNormalizedHeaders(conn);
    grabResponseCookies();
    if (weHaveHeaderExpectations()) {
      checkHeaderExpectations();
    }
    String contentType = conn.getContentType();
    Assert.assertNotNull("null content-type", contentType);
    String contentTypeCharset = null;
    int semi = contentType.indexOf(';');
    if (semi != -1) {
      contentTypeCharset = contentType.substring(semi + 1).trim();
      contentType = contentType.substring(0, semi).trim();
      if (contentTypeCharset.toLowerCase().startsWith(CHARSET_EQUALS)) {
        contentTypeCharset
            = contentTypeCharset.substring(CHARSET_EQUALS.length());
      }
    }

    if (shouldCheckContentType) {
      Assert.assertEquals("Bad Content-Type", expectedContentType, contentType);
    }
    if (expectedContentTypeCharset != null) {
      Assert.assertEquals("Bad Content-Type charset",
              expectedContentTypeCharset.toUpperCase(), contentTypeCharset.toUpperCase());
    }
    goldenText = getAliasedProperty(bodyKey);
    if (goldenText != null) {
      String body = toStringAndClose(conn.getInputStream());
      matchGoldenFile(goldenText, body);
    }

    if (expectedResponseCode != null) {
      int actualResponseCode = conn.getResponseCode();
      Assert.assertEquals("Bad response code",
              expectedResponseCode.intValue(), actualResponseCode);
    }

    if (expectedResponseCode != null && expectedResponseCode >= 500) {
      conn.disconnect();
      return;
    }

    String[] responseLines;
    InputStream errorStream = conn.getErrorStream();
    if (errorStream != null) {
      String errorMessage = toStringAndClose(errorStream);
      if (expectedContainedErrorMessage != null) {
        Assert.assertTrue("Could not find '"
            + expectedContainedErrorMessage +"' in '" + errorMessage + "'"
            , errorMessage.indexOf(expectedContainedErrorMessage) != -1);
      }
      responseLines = new String[0];
    } else {
      responseLines = toStringsAndClose(conn.getInputStream());
    }
    Set<String> responseLinesSet = toSet(responseLines);
    if (expectedResponseLines != null) {
      expectedResponseLines.removeAll(responseLinesSet);
      if (!expectedResponseLines.isEmpty()) {
        conn.disconnect();
        Assert.fail("Expected line '" + expectedResponseLines.iterator().next()
                + "' but got \n" + toString(responseLines));
      }
    }
    if (unexpectedResponseLines != null) {
      for (String line : unexpectedResponseLines) {
        if (responseLinesSet.contains(line)) {
          conn.disconnect();
          Assert.fail("Unexpected line '" + line + "' found from \n"
                  + toString(responseLines));
        }
      }
    }

    conn.disconnect();
  }

  public void setFollowRedirects(boolean followRedirects) {
    this.followRedirects = followRedirects;
  }


  public void setDoInput(boolean doInput) {
    this.doInput = doInput;
  }

  public void setExpectedContentType(String expectedContentType) {
    this.expectedContentType = expectedContentType;
    shouldCheckContentType = true;
  }

  public void setExpectedContentTypeIsAny() {
    shouldCheckContentType = false;
  }

  public void setExpectedContentTypeCharset(String expectedContentTypeCharset) {
    this.expectedContentTypeCharset = expectedContentTypeCharset;
  }

  public void setExpectedResponseViaResource(String goldenResource)
      throws IOException {
    ClassLoader cl = Thread.currentThread().getContextClassLoader();
    InputStream istr = cl.getResourceAsStream(goldenResource);
    if (istr == null) {
      Assert.fail("Cannot load classpath resource '" + goldenResource + "'");
    }
    setExpectedResponse(toStringAndClose(istr));
  }

  public void setExpectedResponse(String expectedResponse) {
    // the next 2 lines converts things like ${host} in the expected body
    // to things like "localhost" or "127.0.0.1"
    props.setProperty(bodyKey, expectedResponse);
  }

  public void setExpectedErrorMessageContains(String expectedContainedErrorMessage) {
    this.expectedContainedErrorMessage = expectedContainedErrorMessage;
  }

  public void setExpectedResponseLine(String expectedResponseLine) {
    if (expectedResponseLines == null) {
      expectedResponseLines = new TreeSet<String>();
    }
    expectedResponseLines.add(expectedResponseLine);
  }

  public void setUnexpectedResponseLine(String unexpectedResponseLine) {
    if (unexpectedResponseLines == null) {
      unexpectedResponseLines = new TreeSet<String>();
    }
    unexpectedResponseLines.add(unexpectedResponseLine);
  }

  public void setExpectedPassResponseLine() {
    setExpectedResponseLine("PASSED");    // default pass response
  }

  private static String[] toStringsAndClose(InputStream is) throws IOException {
    try {
      return toStrings(is);
    } finally {
      is.close();
    }    
  }

  private static String readLine(BufferedReader br) {
    try {
      return br.readLine();
    } catch (IOException ignored) {
      return null;
    }
  }

  private static String[] toStrings(InputStream is) throws IOException {
    List<String> lines = new ArrayList<String>();
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    String line;
    while ((line = readLine(br)) != null) {
      lines.add(line);
    }
    return lines.toArray(new String[lines.size()]);
  }

  private static String toStringAndClose(InputStream istr) throws IOException {
    try {
      return toString(istr);
    } finally {
      istr.close();
    }
  }

  /**
   * Convert an InputStream to a string, but does not close the InputStream.
   *
   * @param istr the input stream to copy
   * @return the converted string
   * @throws IOException if there was a problem reading from the input stream
   */
  private static String toString(InputStream istr) throws IOException {
    Reader reader = new InputStreamReader(istr);
    char[] buf = new char[512];
    int n;
    StringBuilder sb = new StringBuilder();
    while ((n = reader.read(buf)) == buf.length) {
      sb.append(buf, 0, n);
    }
    if (n > 0) {
      sb.append(buf, 0, n);
    }
    return sb.toString();
  }


  public void setExpectedResponseCode(int expectedResponseCode) {
    this.expectedResponseCode = expectedResponseCode;
  }

  private void matchGoldenFile(String golden, String body) {
    StringTokenizer st1 = new StringTokenizer(body);
    StringTokenizer st2 = new StringTokenizer(golden);

    while (st1.hasMoreTokens() && st2.hasMoreTokens()) {
        String tok1 = st1.nextToken();
        String tok2 = st2.nextToken();
      Assert.assertEquals("Different non-whitespace line text", tok2, tok1);
    }
    Assert.assertFalse(
        "More lines in message body than golden file", st1.hasMoreTokens());
    Assert.assertFalse(
        "Less lines in message body than golden file", st2.hasMoreTokens());
  }
}
