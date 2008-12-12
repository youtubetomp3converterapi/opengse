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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * @author jennings
 *         Date: Jul 22, 2008
 */
public class AcceptSocketAndHandleRequest implements Runnable {
  private static final Logger LOG
      = Logger.getLogger(AcceptSocketAndHandleRequest.class.getName());
  private final SocketHandler handler;
  private final ServerSocket serverSocket;

  public AcceptSocketAndHandleRequest(SocketHandler handler, ServerSocket serverSocket) {
    this.handler = handler;
    this.serverSocket = serverSocket;
  }

  public void run() {
    while (!serverSocket.isClosed()) {
      acceptAndHandleWithNoExceptions();
    }
  }

  private void acceptAndHandleWithNoExceptions() {
    try {
      acceptAndHandle();
    } catch (IOException e) {
      LOG.log(Level.FINEST, "", e);
    }
  }

  private void acceptAndHandle() throws IOException {
    Socket socket = serverSocket.accept();
    try {
      handleSocket(socket);
    } finally {
      socket.close();
    }
  }

  private void handleSocket(Socket socket) throws IOException {
    InputStream istr = null;
    try {
      istr = socket.getInputStream();
      handleSocket(socket, istr);
    } finally {
      if (istr != null) {
        istr.close();
      }
    }
  }

  private void handleSocket(Socket socket, InputStream istr) throws IOException {
    OutputStream ostr = null;
    try {
      ostr = socket.getOutputStream();
      handleSocket(socket, istr, ostr);
    } finally {
      if (ostr != null) {
        ostr.close();
      }
    }
  }

  private void handleSocket(Socket socket, InputStream istr, OutputStream ostr)
      throws IOException {
    handler.handleSocket(socket, istr, ostr);
  }
}
