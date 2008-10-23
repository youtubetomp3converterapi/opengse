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
import java.io.Writer;

/**
 * A simple dispatcher class which translates various
 * {@link java.io.Writer} methods into <code>IOBuffer</code>
 * methods.
 *
 * @see IOBuffer
 * @author Spencer Kimball
 */
public class IOBufferWriter extends Writer {
  //private static final Logger logger_ =
  //  Logger.getLogger(IOBufferWriter.class.getName());

  /** The underlying IOBuffer */
  private IOBuffer buf_;

  /**
   * An exception indicating that the writer was closed and no further
   * operations can be performed.
   */
  public static class IOBufferWriterClosedException
      extends IllegalStateException {
    public IOBufferWriterClosedException() {
      super("invalid use; writer has been closed");
    }

    private static final long serialVersionUID = 1L;
  }

  /**
   * The IOBuffer constructor.
   *
   * @param buf the underlying IOBuffer object
   */
  public IOBufferWriter(IOBuffer buf) {
    this.buf_ = buf;
  }

  /** After this call, the writer will be unusable */
  @Override
  public void close() throws IOException {
    if (buf_ == null) {
      return;
    }
    try {
      buf_.flush();
      buf_.consume(true);
    } catch (IOException e) {
      //logger_.log(Level.WARNING, "error closing; closing", e);
      throw e;
    } finally {
      buf_ = null;
    }
  }
  @Override
  public void flush() throws IOException {
    if (buf_ == null) {
      throw new IOBufferWriterClosedException();
    }
    try {
      buf_.flush();
      buf_.consume();
    } catch (IOException e) {
      //logger_.log(Level.WARNING, "error flushing; closing", e);
      buf_ = null;
      throw e;
    }
  }
  @Override
  public void write(char[] cbuf) throws IOException {
    write(cbuf, 0, cbuf.length);
  }
  @Override
  public void write(String str) throws IOException {
    write(str, 0, str.length());
  }
  @Override
  public void write(String str, int off, int len) throws IOException {
    write(str.toCharArray(), off, len);
  }
  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    if (buf_ == null) {
      throw new IOBufferWriterClosedException();
    }
    try {
      buf_.write(cbuf, off, len);
    } catch (IOException e) {
      //logger_.log(Level.WARNING, "error writing; closing", e);
      buf_ = null;
      throw e;
    }
  }
  @Override
  public void write(int c) throws IOException {
    if (buf_ == null) {
      throw new IOBufferWriterClosedException();
    }
    try {
      buf_.write(c);
    } catch (IOException e) {
      //logger_.log(Level.WARNING, "error writing; closing", e);
      buf_ = null;
      throw e;
    }
  }
}
