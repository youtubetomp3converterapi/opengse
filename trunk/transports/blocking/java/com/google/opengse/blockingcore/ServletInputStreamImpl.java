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

import javax.servlet.ServletInputStream;
import java.io.*;

/**
 * @author jennings
 *         Date: Jul 27, 2008
 */
class ServletInputStreamImpl extends ServletInputStream {
  private InputStream realStream;
  private BufferedReader reader;


  ServletInputStreamImpl(InputStream realStream) {
    this.realStream = new BufferedInputStream(realStream);
    reader = new BufferedReader(new InputStreamReader(this.realStream));
  }

  public int read() throws IOException {
    return realStream.read();
  }

  @Override
  public int readLine(byte[] b, int off, int len) throws IOException {
    return super.readLine(b, off, len);
  }

  @Override
  public int read(byte b[], int off, int len) throws IOException {
    return realStream.read(b, off, len);    
  }

  BufferedReader getReader() {
    return reader;
  }
}
