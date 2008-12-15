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

package com.google.opengse.blockingcore;

import java.util.*;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Represents HTTP headers for either a request or a response.
 * An Http header is a mapping from a key to a list of values.
 * The key is case insensitive, so "Content-length" will displace
 * "Content-Length" but the original case of the key will be preserved when
 * it is written to a stream.
 * 
 */
final class HttpHeaders {
  private Map<String, String> ucase_headername_to_headername;
  private Map<String, List<String>> ucase_headers;

  HttpHeaders() {
    ucase_headername_to_headername = new HashMap<String, String>();
    ucase_headers = new HashMap<String, List<String>>();
  }

  void clear() {
    ucase_headers.clear();
    ucase_headername_to_headername.clear();
  }

  Collection<String> getHeaderNames() {
    return ucase_headername_to_headername.values();
  }

  void writeHeaders(PrintWriter out) {
    for (String ukey : ucase_headers.keySet()) {
      List<String> values = ucase_headers.get(ukey);
      if (values.isEmpty()) {
        continue;
      }
      // get the original rendering of the header "content-type" versus "Content-Type" etc.
      String key = ucase_headername_to_headername.get(ukey);
      // we know that values cannot be empty
      Iterator<String> iter = values.iterator();
      out.print(key + ": " + iter.next());
      while (iter.hasNext()) {
        out.print("," + iter.next());
      }
      out.print("\r\n");
    }
    // now we signal the end of headers by printing a blank line
    out.print("\r\n");
  }

  void readHeaders(BufferedReader reader) throws IOException {
    String line;
    int colon;
    while ((line = reader.readLine()) != null) {
      if (line.length() == 0) {
        break;
      }
      colon = line.indexOf(':');
      if (colon == -1) {
        throw new IOException("Unrecognized header line: '" + line + "'");
      }
      String key = line.substring(0, colon);
      String comma_delimetered_values = line.substring(colon + 1);
      List<String> values = getHeaderValues(key);
      parseCommaDelimeteredValues(values, comma_delimetered_values);
    }
  }

  private void parseCommaDelimeteredValues(List<String> values, String comma_delimetered_values) {
    //TODO(jennings) deal properly with this
    comma_delimetered_values = comma_delimetered_values.trim();
    values.add(comma_delimetered_values);
  }

  /**
   * Do a case-insensitive lookup of a given header name
   * @param key
   * @return a non-null list of strings (could be empty)
   */
  List<String> getHeaderValues(String key) {
    String ukey = key.toUpperCase();
    List<String> values = ucase_headers.get(ukey);
    if (values == null) {
      values = new ArrayList<String>();
      ucase_headername_to_headername.put(ukey, key);
      ucase_headers.put(ukey, values);
    }
    return values;
  }

  boolean containsHeader(String name) {
    return ucase_headers.containsKey(name.toUpperCase());
  }

  void setHeader(String key, String value) {
    if (value == null) {
      removeHeader(key);
      return;
    }
    List<String> values = getHeaderValues(key);
    values.clear();
    values.add(value);
  }

  void removeHeader(String key) {
    String ukey = key.toUpperCase();
    ucase_headername_to_headername.remove(ukey);
    ucase_headers.remove(ukey);
  }

  synchronized void addHeader(String key, String value) {
    List<String> values = getHeaderValues(key);
    values.add(value);
  }
  
}
