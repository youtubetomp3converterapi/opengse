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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;

/**
 * @author jennings
 *         Date: Jul 22, 2008
 */
class Connection {
  private final Socket socket;
  private final InputStream istr;
  private final OutputStream ostr;
  private final  Writer outWriter;
  private final PrintWriter out;
  private final BufferedReader reader;

  Connection(
      final Socket socket, final InputStream istr, final OutputStream ostr)
      throws IOException {
    this.socket = socket;
    this.istr = istr;
    this.ostr = ostr;
    reader = new BufferedReader(new InputStreamReader(istr));
    outWriter = new OutputStreamWriter(ostr);
    out = new PrintWriter(outWriter);
  }

  BufferedReader getReader() {
    return reader;
  }

  PrintWriter getWriter() {
    return out;
  }

  OutputStream getOutputStream() {
    return ostr;
  }

  InputStream getInputStream() {
    return istr;
  }

}

