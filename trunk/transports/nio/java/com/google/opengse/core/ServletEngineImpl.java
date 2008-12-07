// Copyright 2007 Google Inc. All Rights Reserved.
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

package com.google.opengse.core;

import com.google.opengse.HttpRequestHandler;
import com.google.opengse.HttpRequestHandlerAdapter;
import com.google.opengse.ServletEngine;
import com.google.opengse.ServletEngineConfiguration;
import com.google.opengse.ServletEngineConfigurationImpl;
import com.google.opengse.util.DispatchQueue;
import com.google.opengse.util.DispatchQueueImpl;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.servlet.FilterChain;

/**
 * Implement the {@link ServletEngine} interface.
 *
 * @author Mike Jennings
 */
public class ServletEngineImpl implements ServletEngine {
  private final HttpServer server_;
  /**
   * Latch to signal when the network thread has begun listening for requests.
   */
  private final CountDownLatch startUpLatch = new CountDownLatch(1);
  /**
   * Timer callback used to determine that the server has been initialized.
   */
  private class StartupCallback implements NetTimerCallback {
    public void handleTimerFired() {
      startUpLatch.countDown();
    }
  }

  /**
   * Waits for the server initialization to complete. This is useful for tests
   * that need to run a GSE server in another thread and perform actions on the
   * server.
   *
   * <p>If the server has already been initialized, then this method returns
   * immediately (even if the server was later shut down). If the server has not
   * been started, the current thread blocks until one of the following things
   * happen:
   *
   * <ul>
   *   <li>The server is started and completes initialization.</li>
   *   <li>Another thread {@link Thread#interrupt() interrupts} the current
   *     thread.</li>
   *   <li>The specified waiting time elapses.</li>
   * </ul>
   *
   * @param timeout the maximum time to wait in ms
   * @throws java.util.concurrent.TimeoutException if the waiting time elapsed
   *    before the server initialized.
   * @throws InterruptedException if the current thread is interrupted while
   *    waiting.
   */
  public void awaitInitialization(long timeout)
      throws InterruptedException, TimeoutException {
    if (!startUpLatch.await(timeout, TimeUnit.MILLISECONDS)) {
      throw new TimeoutException("Timeout waiting for GSE to initialize");
    }
  }


  /**
   * Create a ServletEngine.
   *
   * @param dispatcher
   * @param config
   * @throws IOException
   * @throws InterruptedException
   */
  ServletEngineImpl(FilterChain dispatcher, ServletEngineConfiguration config)
      throws IOException, InterruptedException {
    NetSelector selector = new NetSelector();
    DispatchQueue queue = new DispatchQueueImpl(config.getMaxThreads());
    HttpRequestHandler handler = new HttpRequestHandlerAdapter(dispatcher);
    server_ = new HttpServer(selector, queue, handler, config);
    server_.getSelector().listen(config.getPort(), server_, false);
  }

  ServletEngineImpl(
      HttpRequestHandler handler, ServletEngineConfiguration config)
      throws IOException, InterruptedException {
    NetSelector selector = new NetSelector();
    DispatchQueue queue = new DispatchQueueImpl(config.getMaxThreads());
    server_ = new HttpServer(selector, queue, handler, config);
    server_.getSelector().listen(config.getPort(), server_, false);
  }

  public static ServletEngine create(
      FilterChain dispatcher, ServletEngineConfiguration config)
      throws IOException, InterruptedException {
    return new ServletEngineImpl(dispatcher, config);
  }

  /**
   * Convenience method to create a servlet engine.
   */
  public static ServletEngine create(
      int port, int numberOfThreads, FilterChain dispatcher)
      throws IOException, InterruptedException {
    ServletEngineConfiguration config =
        ServletEngineConfigurationImpl.create(port, numberOfThreads);
    return create(dispatcher, config);
  }

  public static ServletEngine create(
      HttpRequestHandler handler, ServletEngineConfiguration config)
      throws IOException, InterruptedException {
    return new ServletEngineImpl(handler, config);
  }

  /**
   * Get the port this GSE is listening on.
   *
   * @return port the GSE is listening on
   */
  public int getPort() {
    return server_.getSelector().getPort();
  }

  /**
   * Shuts down the server by closing the <code>HttpServer</code> object.
   *
   * @param timeout in milliseconds
   * @throws java.io.IOException is thrown if server close fails
   * @see HttpServer
   */
  public boolean quit(long timeout) throws IOException {
    return server_.quit(timeout);
  }


  public void run() {
    server_.getSelector().schedule(new StartupCallback(), 0);
    server_.getSelector().runForever();
  }

  /**
   * @return True if the underlying selector is accepting connections.
   */
  public boolean isAccepting() {
    return server_.getSelector().isAccepting();
  }
}
