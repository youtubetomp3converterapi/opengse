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

import java.util.*;
import java.util.logging.Logger;
import java.io.InputStream;
import java.io.IOException;

/**
 * @author jennings
 *         Date: Jun 1, 2008
 */
public class Headers {

  private static final Logger LOGGER
      = Logger.getLogger(Headers.class.getName());

  private static final int LINE_FEED = 10;
  private Map<String, List<String>> requestHeaders;
  private Vector<String> cookieVector;


  public Headers() {
    clear();
  }

  public String toString() {
    return requestHeaders.toString();
  }

  public boolean isEmpty() {
    return requestHeaders.isEmpty();
  }

  public Set<String> headerNames() {
    return requestHeaders.keySet();
  }

  public List<String> getValues(String headerName) {
    return requestHeaders.get(headerName);
  }

  public String getValuesAsString(String headerName) {
    List<String> values = requestHeaders.get(headerName);
    if (values == null) {
      return null;
    }
    StringBuilder sb = null;
    for (String value : values) {
      if (sb == null) {
        sb = new StringBuilder();
        sb.append(value);
      } else {
        sb.append(", ").append(value);
      }
    }
    return sb.toString();
  }
  /**
   * Parse the incoming HTTP request headers, and set the corresponding
   * request properties.
   *
   * @throws java.io.IOException if an input/output error occurs
   */
  void parseHeaders(InputStream is) throws IOException {
    clear();
    cookieVector = new Vector<String>();

    while (true) {
      // Read the next header line
      String line = read(is);

      if ((line == null) || (line.length() < 1)) {
        break;
      }

      parseHeader(line, false);
    }

  }

   /**
   * Read a line from the specified servlet input stream, and strip off
   * the trailing carriage return and newline (if any).  Return the remaining
   * characters that were read as a string.7
   *
   * @throws IOException if an input/output error occurred
   * @returns The line that was read, or <code>null</code> if end of file
   * was encountered
   */
  private String read(InputStream input) throws IOException {
    // Read the next line from the input stream
    StringBuffer sb = new StringBuffer();

    while (true) {
      try {
        int ch = input.read();
        // System.out.println("XXX " + (char)ch );
        if (ch < 0) {
          if (sb.length() == 0) {
            return "";
          } else {
            break;
          }
        } else if (ch == LINE_FEED) {
          break;
        }

        sb.append((char) ch);
      } catch (IOException ex) {
        System.out.println(" Error reading : " + ex);
      }
    }
    return sb.toString();
  }


  /**
   * Clear the request headers.
   */
  void clear() {
    requestHeaders = new HashMap<String, List<String>>();
  }

  /**
   * <code>setHeaderDetails</code> Wrapper method for parseHeader.
   * Allows easy addition of headers to the specified
   * HashMap
   *
   * @param line       a <code>String</code> value
   * @param isRequest  a <code>boolean</code> indicating if the passed Header
   *                   HashMap is for request headers
   */
  private void setHeaderDetails(String line, boolean isRequest) {
    StringTokenizer stk = new StringTokenizer(line, "##");

    while (stk.hasMoreElements()) {
      String presentHeader = stk.nextToken();
      parseHeader(presentHeader, isRequest);
    }
  }

  /**
   * Add a header meant for requests.
   * 
   * @param line
   */
  void addRequestHeader(String line) {
    setHeaderDetails(line, true);
  }

  /**
   * Add a header meant for responses.
   * 
   * @param line
   */
  void addResponseHeader(String line) {
    setHeaderDetails(line, false);
  }


  /**
   * <code>parseHeader</code> parses input headers in format of "key:value"
   * The parsed header field-name will be used as a key in the passed
   * HashMap object, and the values found will be stored in an ArrayList
   * associated with the field-name key.
   *
   * @param line      String representation of an HTTP header line.
   * @param isRequest set to true if the headers being processed are
   *                  requestHeaders.
   */
  private void parseHeader(String line, boolean isRequest) {
    // Parse the header name and value
    int colon = line.indexOf(":");

    if (colon < 0) {
      System.out.println(" ERROR: Header is in incorrect format: " + line);
      return;
    }

    String name = line.substring(0, colon).trim();
    String value = line.substring(colon + 1).trim();

    if ((cookieVector != null) && (name.equalsIgnoreCase("Set-Cookie"))) {
      cookieVector.addElement(value);
//      if ( ( value.indexOf("JSESSIONID") > -1 )
//          || (value.indexOf("jsessionid")  > -1 ) )
//      {
//           String sessionId= value.substring( value.indexOf("=")+1);
//           if ( testSession != null )
//           {
//             sessionHash.put( testSession, sessionId );
//           }
//           System.out.println("Got Session-ID : " + sessionId );
//      }
    }

    /*
     * The bulk of our JUnit test log is getting filled up with this and
     * making it difficult to see the actual tests.  If it needs to get
     * re-added back in, can it be a lower log level, like FINE or FINEST?
     */
//    LOGGER.info("HEADER: " + name + " " + value);

    List<String> values = requestHeaders.get(name);
    if (values == null) {
      values = new ArrayList<String>();
      requestHeaders.put(name, values);
    }
    // HACK
    if (value.indexOf(',') > -1
            && !isRequest
            && !name.equalsIgnoreCase("Date")) {
      StringTokenizer st = new StringTokenizer(value, ",");
      while (st.hasMoreElements()) {
        values.add(st.nextToken());
      }
    } else {
      values.add(value);
    }

  }


  /**
   * <code>cloneHeaders</code> returns a "cloned"
   * version of this Headers object
   *
   * @return a <code>Headers</code> value which is a clone of this
   */
 Headers cloneHeaders() {
    Headers dupMap = new Headers();
    for (String key : requestHeaders.keySet()) {
      List<String> origValues = requestHeaders.get(key);
      ArrayList<String> dupValues = new ArrayList<String>();

      String[] dupVal = origValues.toArray(new String[origValues.size()]);
      for (String aDupVal : dupVal) {
        dupValues.add(new String(aDupVal));
      }

      dupMap.requestHeaders.put(key, dupValues);
    }
    return dupMap;
  }
}
