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

import java.util.StringTokenizer;
import java.util.Map;
import java.util.HashMap;
import java.net.URLDecoder;
import java.io.UnsupportedEncodingException;

/**
 * Represents a request URI
 *
 * @author Mike Jennings
 */
public class RequestURI {
  private String data;
  private String rawQuery;
  private Map<String, String[]> paramMap;

  public RequestURI(String data) {
    this.data = data;
    paramMap = new HashMap<String, String[]>();
    int question = data.indexOf('?');
    if (question != -1) {
      rawQuery = data.substring(question + 1);
      this.data = data.substring(0, question);
      parseRawQuery();
    }
  }

  public Map<String, String[]> getParameterMap() {
    return paramMap;
  }

  private void parseRawQuery() {
    StringTokenizer st = new StringTokenizer(rawQuery, "&");
    while (st.hasMoreTokens()) {
      parseKeyValue(st.nextToken());
    }
  }

  private void parseKeyValue(String keyvalue) {
    int eq = keyvalue.indexOf('=');
    if (eq == -1) {
      return;
    }
    String key = decode(keyvalue.substring(0, eq));
    String value = decode(keyvalue.substring(eq + 1));
    addKeyValue(key, value);
  }

  private void addKeyValue(String key, String value) {
    String[] values = paramMap.get(key);
    if (values == null) {
      values = new String[1];
      values[0] = value;
      paramMap.put(key, values);
    } else {
      String[] newvalues = new String[values.length + 1];
      System.arraycopy(values, 0, newvalues, 0, values.length);
      newvalues[values.length] = value;
      paramMap.put(key, newvalues);
    }
  }

  private static String decode(String s) {
    try {
      return URLDecoder.decode(s, "UTF8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  public String getRawQuery() {
    return rawQuery;
  }

  public String getRawPath() {
    return data;
  }

  @Override
  public String toString() {
    return data;
  }
}
