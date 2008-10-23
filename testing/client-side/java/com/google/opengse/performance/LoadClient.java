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

package com.google.opengse.performance;

import java.io.IOException;

/**
 * LoadClient: extends the simulator Client class to provide
 * randomized request payloads and send each request to a
 * wait servlet with a randomized latency to simulate work
 * being done at the server.
 *
 * @author Spencer Kimball
 */
public class LoadClient extends Client {
  private IOBuffer golden_req_ = new IOBuffer();

  /**
   * Constructor.
   */
  public LoadClient(String name) {
    super(name);

    this.size_ = Math.max(0.5, RANDOM.nextGaussian () *
                          LoadSimulator.FLAG_SIZE_STD_DEV +
                          LoadSimulator.FLAG_SIZE_MEAN);
    this.latency_ = Math.max(1, RANDOM.nextGaussian () *
                             LoadSimulator.FLAG_LATENCY_STD_DEV +
                             LoadSimulator.FLAG_LATENCY_MEAN);

    // get the request bytes
    golden_req_.clear();
    int nbytes = (int) (this.size_ * 1024);
    byte[] head = (
        "POST /performance/wait?time=" + ((int) this.latency_) +
        " HTTP/1.0\r\n" + "Connection: " +
        (Simulator.FLAG_keepalive ?
        "keep-alive\r\n" : "close\r\n") +
        "Content-Length: " + nbytes +
        "\r\n\r\n").getBytes();
    byte[] request = new byte[nbytes];
    for (int i = 0; i < nbytes; i++) {
      request[i] = (byte) (i % 256);
    }
    try {
      golden_req_.writeBytes(head);
      golden_req_.writeBytes(request);
      golden_req_.flush();
    } catch (IOException e) { /* ignored */ }
  }

  @Override
  public void setRequest() {
    req_ = new IOBuffer(golden_req_);
  }

  @Override
  public void handleResponse() {
    // do nothing
  }
}
