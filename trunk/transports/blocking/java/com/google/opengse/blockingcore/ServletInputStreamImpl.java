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

import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;

import javax.servlet.ServletInputStream;

/**
 * @author jennings
 *         Date: Jul 27, 2008
 */
class ServletInputStreamImpl extends ServletInputStream {
  private static final int CR = '\r';
  private static final int LF = '\n';
  private InputStream realStream;
  private static final int MAX_HEADERS_SIZE = 100000;
  private HttpRequestHeaders headers;

  ServletInputStreamImpl(InputStream realStream) throws IOException {
    this.realStream = new BufferedInputStream(realStream);
    readHeaders();
  }

  private void readHeaders() throws IOException {
    int thebyte;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    int pattern = -1;

    while (baos.size() < MAX_HEADERS_SIZE) {
      thebyte = realStream.read();
      if (thebyte == -1) {
        break;
      }
      baos.write(thebyte);
      if (thebyte == CR) {
        if (pattern == -1 || pattern == 1) {
          ++pattern;
        } else {
          pattern = -1;
        }
      } else if (thebyte == LF) {
        if (pattern == 0 || pattern == 2) {
          ++pattern;
          if (pattern == 3) {
            // this indicates that we have encountered 2 pairs of cr/lf bytes
            break;
          }
        }
      } else {
        pattern = -1;
      }
    }
    String allHeaders = baos.toString();
    headers = new HttpRequestHeaders(allHeaders);
  }

  public int read() throws IOException {
    return realStream.read();
  }

  @Override
  public int readLine(byte[] b, int off, int len) throws IOException {
    return super.readLine(b, off, len);
  }

  @Override
  public int read(byte b[], int off, int len) throws IOException {
    return realStream.read(b, off, len);    
  }

  Enumeration<String> getHeaders(String name) {
    return headers.getHeaders(name);
  }

  String getHeader(String name) {
    return headers.getHeader(name);
  }

  Enumeration<String> getHeaderNames() {
    return headers.getHeaderNames();
  }

  HttpRequestType getRequestType() {
    return headers.getRequestType();
  }
}
