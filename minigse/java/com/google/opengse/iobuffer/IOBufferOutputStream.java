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
import javax.servlet.ServletOutputStream;

/**
 * A simple dispatcher class which translates various
 * {@link javax.servlet.ServletOutputStream} methods into
 * <code>IOBuffer</code> methods.
 *
 * @see IOBuffer
 * @author Spencer Kimball
 */
public class IOBufferOutputStream extends ServletOutputStream {

  /** The underlying IOBuffer */
  private IOBuffer buf_;

  /**
   * An exception indicating that the stream was closed and no further
   * operations can be performed.
   */
  public static class IOBufferOutputStreamClosedException
      extends IllegalStateException {
    public IOBufferOutputStreamClosedException() {
      super("invalid use; stream has been closed");
    }

    private static final long serialVersionUID = 1L;
  }

  /**
   * The IOBuffer constructor.
   *
   * @param buf the underlying IOBuffer object
   */
  public IOBufferOutputStream(IOBuffer buf) {
    this.buf_ = buf;
  }

  /** After this call, the stream will be unusable */
  @Override
  public void close() throws IOException {
    if (buf_ == null) {
      return;
    }
    buf_.flush();
    buf_.consume(true);
    buf_ = null;
  }
  @Override
  public void flush() throws IOException {
    if (buf_ == null) {
      throw new IOBufferOutputStreamClosedException();
    }
    buf_.flush();
    buf_.consume();
  }
  @Override
  public void write(byte[] b) throws IOException {
    if (buf_ == null) {
      throw new IOBufferOutputStreamClosedException();
    }
    buf_.writeBytes(b);
  }
  @Override
  public void write(byte[] b, int offset, int count) throws IOException {
    if (buf_ == null) {
      throw new IOBufferOutputStreamClosedException();
    }
    buf_.writeBytes(b, offset, count);
  }
  @Override
  public void write(int b) throws IOException {
    if (buf_ == null) {
      throw new IOBufferOutputStreamClosedException();
    }
    buf_.writeByte(b);
  }
}

