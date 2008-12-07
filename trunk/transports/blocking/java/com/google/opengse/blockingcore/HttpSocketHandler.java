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

import com.google.opengse.HttpRequestHandler;
import com.google.opengse.HttpResponse;

import javax.servlet.ServletOutputStream;
import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

/**
 * @author jennings
 *         Date: Jul 22, 2008
 */
public class HttpSocketHandler implements SocketHandler {
  private final HttpRequestHandler handler;

  public HttpSocketHandler(HttpRequestHandler handler) {
    this.handler = handler;
  }

  public void handleSocket(Socket socket, InputStream istr, OutputStream ostr)
      throws IOException {
    Connection conn = new Connection(socket, istr, ostr);
    ServletInputStreamImpl inputStream = new ServletInputStreamImpl(conn.getInputStream());
    HttpRequestImpl req = new HttpRequestImpl(inputStream);
    ServletOutputStreamImpl outputStream = new ServletOutputStreamImpl(conn.getOutputStream(), 2048);
    try {
      HttpResponse resp = new HttpResponseImpl(req, outputStream);
      handler.handleRequest(req, resp);
    } finally {
      // ensure that any buffered bytes get committed. No byte left behind!
      outputStream.commit();
    }
  }
}
