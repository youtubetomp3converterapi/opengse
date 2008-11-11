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


import java.io.IOException;
import javax.servlet.ServletInputStream;

/**
 * A simple dispatcher class which translates various
 * {@link javax.servlet.ServletInputStream} methods into
 * <code>IOBuffer</code> methods.
 *
 * @see IOBuffer
 * @author Spencer Kimball
 */
public class IOBufferInputStream extends ServletInputStream {

  /** The underlying IOBuffer */
  private final IOBuffer buf_;

  /**
   * The IOBuffer constructor.
   *
   * @param buf the underlying IOBuffer object
   */
  public IOBufferInputStream(IOBuffer buf) {
    this.buf_ = buf;
  }

  @Override
  public int available() {
    return buf_.availableBytes();
  }
  @Override
  public void close() {
  }
  @Override
  public void mark(int limit) {
  }
  @Override
  public boolean markSupported() {
    return false;
  }
  @Override
  public int read() {
    return buf_.readByte();
  }
  @Override
  public int read(byte[] b) {
    return buf_.readBytes(b);
  }
  @Override
  public int read(byte[] b, int offset, int count) {
    return buf_.readBytes(b, offset, count);
  }
  @Override
  public void reset() throws IOException {
    throw new UnsupportedOperationException();
  }
  @Override
  public long skip(long n) {
    return buf_.skipBytes(n);
  }
}

