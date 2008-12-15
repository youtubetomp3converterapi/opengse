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

import com.google.opengse.ConnectionInformation;

import java.net.InetSocketAddress;

/**
 * Represents metadata for a request
 *
 * @author Mike Jennings
 */
public class RequestMetaData implements ConnectionInformation {
  private InetSocketAddress localSocketAddress;
  private InetSocketAddress remoteSocketAddress;
  private HttpRequestType type;
  private HttpHeaders headers;

  public RequestMetaData(InetSocketAddress localSocketAddress, InetSocketAddress remoteSocketAddress,
                         HttpRequestType type, HttpHeaders headers) {
    this.localSocketAddress = localSocketAddress;
    this.remoteSocketAddress = remoteSocketAddress;
    this.type = type;
    this.headers = headers;
  }

  public HttpRequestType getType() {
    return type;
  }

  public HttpHeaders getHeaders() {
    return headers;
  }

  // connectioninformation interface methods
  public String getServerName() {
    return localSocketAddress.getAddress().getHostName();
  }

  public int getServerPort() {
    return localSocketAddress.getPort();
  }

  public String getRemoteAddr() {
    return remoteSocketAddress.getAddress().getHostAddress();  
  }

  public String getRemoteHost() {
    return remoteSocketAddress.getHostName();
  }

  public boolean isSecure() {
    return false;
  }

  public int getRemotePort() {
    return remoteSocketAddress.getPort();
  }

  public String getLocalName() {
    return localSocketAddress.getHostName();
  }

  public String getLocalAddr() {
    return localSocketAddress.getAddress().toString();
  }

  public int getLocalPort() {
    return localSocketAddress.getPort();
  }
}
