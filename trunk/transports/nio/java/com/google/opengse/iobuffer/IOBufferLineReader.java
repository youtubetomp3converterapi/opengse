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

package com.google.opengse.iobuffer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.google.opengse.io.LineReader;


/**
 * An implementation of LineReader which uses
 * a ByteArrayOutputStream and an IOBuffer
 *
 * @author Mike Jennings
 */
public class IOBufferLineReader implements LineReader {

  private final IOBuffer src;
  private final ByteArrayOutputStream output;
  private final int maxCharsPerLine;

  public IOBufferLineReader(IOBuffer src, ByteArrayOutputStream output,
      int maxCharsPerLine) {
    this.src = src;
    this.output = output;
    this.maxCharsPerLine = maxCharsPerLine;
  }

  public String readLine() throws IOException {
    if (!src.readLine(output, maxCharsPerLine)) {
      return null;
    } else {
      String line = output.toString();
      output.reset();
      return line;
    }
  }
}
