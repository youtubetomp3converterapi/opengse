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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author jennings Date: Jul 22, 2008
 */
public interface SocketHandler {
  /**
   * Handle this socket.
   *
   * Implementors of this interface do not need to worry about closing the
   * input or output streams, or closing the socket.
   *
   * @param socket The socket to handle (in case remote address etc. are needed)
   * @param istr The input stream of the socket
   * @param ostr The output stream of the socket
   * @throws IOException
   */
  void handleSocket(Socket socket, InputStream istr, OutputStream ostr)
      throws IOException;
}
