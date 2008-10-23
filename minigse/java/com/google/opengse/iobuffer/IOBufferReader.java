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

import java.io.Reader;
import java.io.IOException;

/**
 * A simple dispatcher class which translates various
 * {@link java.io.Reader} methods into <code>IOBuffer</code>
 * methods.
 *
 * @see IOBuffer
 * @author Spencer Kimball
 */
public class IOBufferReader extends Reader {

  /** The underlying IOBuffer */
  private final IOBuffer buf_;

  /**
   * The IOBuffer constructor.
   *
   * @param buf the underlying IOBuffer object
   */
  public IOBufferReader(IOBuffer buf) {
    this.buf_ = buf;
  }

  @Override
  public void close() {
  }
  @Override
  public void mark(int limit) throws IOException {
    throw new UnsupportedOperationException();
  }
  @Override
  public boolean markSupported() {
    return false;
  }
  @Override
  public int read() {
    return buf_.read();
  }
  @Override
  public int read(char[] cbuf) {
    return buf_.read(cbuf);
  }
  @Override
  public int read(char[] cbuf, int off, int len) {
    return buf_.read(cbuf, off, len);
  }
  @Override
  public boolean ready() {
    return buf_.getReadBuffer() != null;
  }
  @Override
  public void reset() throws IOException {
    throw new UnsupportedOperationException();
  }
  @Override
  public long skip(long n) throws IOException {
    return buf_.skip(n);
  }
}

