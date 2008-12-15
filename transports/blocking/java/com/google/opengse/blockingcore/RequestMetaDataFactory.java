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
import java.io.ByteArrayOutputStream;
import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;
import java.net.Socket;
import java.net.InetSocketAddress;

/**
 * A factory for RequestMetaData objects
 *
 * @author Mike Jennings
 */
public class RequestMetaDataFactory {
  private static final int MAX_HEADERS_SIZE = 100000;
  private static final int CR = '\r';
  private static final int LF = '\n';

  private RequestMetaDataFactory() {
  }

  public static RequestMetaData extractMetaData(Socket socket, InputStream istr) throws IOException {
    int thebyte;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    int pattern = -1;

    while (baos.size() < MAX_HEADERS_SIZE) {
      thebyte = istr.read();
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
    BufferedReader reader = new BufferedReader(new StringReader(allHeaders));
    HttpRequestType requestType = new HttpRequestType(reader.readLine());
    HttpHeaders headers = new HttpHeaders();
    headers.readHeaders(reader);
    return new RequestMetaData((InetSocketAddress)socket.getLocalSocketAddress(),
        (InetSocketAddress)socket.getRemoteSocketAddress(), requestType, headers);
  }
}
