// Copyright 2002-2006 Google Inc.
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
import java.nio.channels.SocketChannel;

/**
 * Callback interface for accepting connections.
 *
 * <p>A server class will implement this interface in order to accept new
 * connections. An instance of the {@code AcceptCallback} is provided to {@link
 * com.google.opengse.core.NetSelector#listen(int,AcceptCallback,boolean)},
 * and will have its {@link #handleNewConnection(SocketChannel, byte[])} method
 * called each time a new client connects to the {@code NetSelector}.
 *
 * @see AcceptServer
 * @see NetSelector
 * @author Peter Mattis
 */
public interface AcceptCallback {
  /**
   * Called when a new connection (<code>channel</code>) is accepted
   * on the {@link java.nio.channels.ServerSocketChannel} this callback is
   * associated with.
   *
   * <p>Some bytes may have been read from the given connection already. If so,
   * the callee must process those bytes first before processing bytes from
   * the given connection.
   *
   * @param channel the new connection
   * @param bytesAlreadyRead bytes that have been read from channel already.
   * Cannot be null. Can be an empty array.
   */
  void handleNewConnection(SocketChannel channel,
      byte[] bytesAlreadyRead) throws IOException;
}