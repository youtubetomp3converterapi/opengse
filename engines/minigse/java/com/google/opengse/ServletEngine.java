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

package com.google.opengse;

import java.util.concurrent.TimeoutException;
import java.io.IOException;

/**
 * @author Mike Jennings
 */
public interface ServletEngine extends Runnable {

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
   * <ul> <li>The server is started and completes initialization <li>Some other
   * thread {@link Thread#interrupt interrupts} the current thread <li>The
   * specified waiting time elapses </ul>
   *
   * @param timeout the maximum time to wait in ms
   * @throws java.util.concurrent.TimeoutException
   *                              if the waiting time elapsed before the server
   *                              initialized.
   * @throws InterruptedException if the current thread is interrupted while
   *                              waiting.
   */
  void awaitInitialization(long timeout)
      throws InterruptedException, TimeoutException;

  /**
   * Get the port this GSE is listening on.
   *
   * @return port the GSE is listening on
   */
  public int getPort();

  /**
   * Shuts down the server by closing the <code>HttpServer</code> object.
   *
   * @param timeout in milliseconds
   * @throws java.io.IOException is thrown if server close fails
   * @see com.google.opengse.core.HttpServer
   */
  public boolean quit(long timeout) throws IOException;

  /**
   * @return True if the underlying selector is accepting connections.
   */
  public boolean isAccepting();




}
