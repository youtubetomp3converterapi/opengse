// Copyright 2002 Google Inc.
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

import java.io.IOException;

/**
 * Callback interface for quitting the server process. This interface is
 * called by the signal handler and the servlet to shutdown the server process.
 * The class {@link com.google.opengse.core.HttpServer} provides a default
 * implementation of this interface.
 */
public interface QuitCallback {
  /**
   * Shutdown the server process.
   *
   * @param timeout is the timeout value in milliseconds for this
   *        operation.
   * @return {@code true} if exit is graceful, false otherwise.
   */
  public boolean quit(long timeout) throws IOException;
}