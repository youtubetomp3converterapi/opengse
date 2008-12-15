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

import java.io.OutputStream;
import java.io.IOException;

public class NoBodyOutputStream extends OutputStream {
  private static final int CR = '\r';
  private static final int LF = '\n';
  private OutputStream delegate;
  private int pattern;
  

  public NoBodyOutputStream(OutputStream delegate) {
    this.delegate = delegate;
    pattern = -1;
  }

  public void write(int thebyte) throws IOException {
    if (pattern == 3) {
      return;
    }

    delegate.write(thebyte);

    if (thebyte == CR) {
      if (pattern == -1 || pattern == 1) {
        ++pattern;
      } else {
        pattern = -1;
      }
    } else if (thebyte == LF) {
      if (pattern == 0 || pattern == 2) {
        ++pattern;
      }
    } else {
      pattern = -1;
    }
  }

  @Override
  public void flush() throws IOException {
    delegate.flush();
  }

  @Override
  public void close() throws IOException {
    delegate.close();
  }
}
