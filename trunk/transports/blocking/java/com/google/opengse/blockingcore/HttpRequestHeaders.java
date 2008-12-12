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

package com.google.opengse.blockingcore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import com.google.opengse.util.EmptyEnumeration;
import com.google.opengse.util.IteratorEnumeration;

/**
 * @author jennings
 *         Date: Jul 22, 2008
 */
final class HttpRequestHeaders {
  private HttpRequestType requestType;
  private Map<String, List<Header>> headerMap;

  HttpRequestHeaders(String allHeaders) throws IOException {
    requestType = null;
    BufferedReader reader = new BufferedReader(new StringReader(allHeaders));
    String line;
    headerMap = new HashMap<String, List<Header>>();
    while ((line = reader.readLine()) != null) {
      if (line.length() == 0) {
        break;
      } else {
        // first line is always the request type
        if (requestType == null) {
          requestType = new HttpRequestType(line);
        } else {
          // subsequent lines are headers
          Header header = new Header(line);
          List<Header> headerValues = headerMap.get(header.getHeaderName());
          if (headerValues == null) {
            headerValues = new ArrayList<Header>();
            headerMap.put(header.getHeaderName(), headerValues);
          }
          headerValues.add(header);
        }
      }
    }
  }

  HttpRequestType getRequestType() {
    return requestType;
  }

  String getHeader(String name) {
    List<Header> values = headerMap.get(name);
    if (values == null) {
      return null;
    }
    return values.iterator().next().getHeaderValue();
  }

  public Enumeration<String> getHeaders(String name) {
    List<Header> values = headerMap.get(name);
    if (values == null) {
      return EmptyEnumeration.please();
    }
    return new HeaderEnumerator(values);
  }

  public Enumeration<String> getHeaderNames() {
    return new IteratorEnumeration<String>(headerMap.keySet().iterator());
  }

  private static class HeaderEnumerator implements Enumeration<String> {
    private Iterator<Header> iterator_;

    public HeaderEnumerator(List<Header> headers) {
      this.iterator_ = headers.iterator();
    }

    public boolean hasMoreElements() {
      return iterator_.hasNext();
    }

    public String nextElement()
      throws NoSuchElementException {
      Header hdr = iterator_.next();
      return hdr.getHeaderValue();
    }
  }


}
