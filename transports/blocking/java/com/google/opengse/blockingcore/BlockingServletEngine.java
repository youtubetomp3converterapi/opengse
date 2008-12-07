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

import com.google.opengse.ServletEngine;
import com.google.opengse.ServletEngineConfiguration;
import com.google.opengse.HttpRequestHandler;
import com.google.opengse.HttpRequestHandlerAdapter;
import com.google.opengse.util.DispatchQueue;
import com.google.opengse.util.DispatchQueueImpl;

import javax.servlet.FilterChain;
import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Mike Jennings
 *         Date: Jul 22, 2008
 */
final class BlockingServletEngine implements ServletEngine {
  private final DispatchQueue queue;
  private final HttpRequestHandler handler;
  private final HttpSocketHandler socketHandler;
  private final ServletEngineConfiguration config;
  private ServerSocket serverSocket;

  BlockingServletEngine(
      final FilterChain dispatcher, final ServletEngineConfiguration config)
      throws InterruptedException, IOException {
    serverSocket = new ServerSocket(config.getPort());
    this.config = config;
    int nthreads = config.getMaxThreads();
    queue = new DispatchQueueImpl(nthreads);
    handler = new HttpRequestHandlerAdapter(dispatcher);
    socketHandler = new HttpSocketHandler(handler);
    for (int i = 0; i < nthreads; ++i) {
      queue.put(new AcceptSocketAndHandleRequest(socketHandler, serverSocket));
    }
  }

  public void awaitInitialization(final long timeout) {
    /*
     * TODO:  Is there nothing to do because this should already be initialized
     * after construction? If that's correct, maybe document as such?
     */
  }

  public int getPort() {
    return serverSocket.getLocalPort();
  }

  public boolean quit(long timeout) throws IOException {
    if (serverSocket != null) {
      serverSocket.close();
      serverSocket = null;
      return true;
    }
    return false;
  }

  public boolean isAccepting() {
    return false;
  }

  public void run() {
    /*
     * TODO:  Is there nothing to do because this should already be running
     * after construction? If that's correct, maybe document as such?
     */
  }
}
