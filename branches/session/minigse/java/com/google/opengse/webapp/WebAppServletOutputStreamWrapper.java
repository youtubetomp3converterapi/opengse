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

package com.google.opengse.webapp;

import com.google.opengse.wrappers.ServletOutputStreamWrapper;

import java.io.IOException;

import javax.servlet.ServletOutputStream;

/**
 * ServletOutputStream wrapper for webapps.
 *
 * @author Mike Jennings
 */
class WebAppServletOutputStreamWrapper extends ServletOutputStreamWrapper {
  private boolean contentWritten;

  WebAppServletOutputStreamWrapper(ServletOutputStream wrappedOutputStream) {
    super(wrappedOutputStream);
    contentWritten = false;
  }

  @Override
  public void print(String s) throws IOException {
    super.print(s);
    contentWritten = true;
  }

  @Override
  public void print(boolean b) throws IOException {
    super.print(b);
    contentWritten = true;
  }

  @Override
  public void print(char c) throws IOException {
    super.print(c);
    contentWritten = true;
  }

  @Override
  public void print(int i) throws IOException {
    super.print(i);
    contentWritten = true;
  }

  @Override
  public void print(long l) throws IOException {
    super.print(l);
  }

  @Override
  public void print(float f) throws IOException {
    super.print(f);
  }

  @Override
  public void print(double d) throws IOException {
    super.print(d);
    contentWritten = true;
  }

  @Override
  public void println() throws IOException {
    super.println();
    contentWritten = true;
  }

  @Override
  public void println(String s) throws IOException {
    super.println(s);
    contentWritten = true;
  }

  @Override
  public void println(boolean b) throws IOException {
    super.println(b);
    contentWritten = true;
  }

  @Override
  public void println(char c) throws IOException {
    super.println(c);
    contentWritten = true;
  }

  @Override
  public void println(int i) throws IOException {
    super.println(i);
    contentWritten = true;
  }

  @Override
  public void println(long l) throws IOException {
    super.println(l);
    contentWritten = true;
  }

  @Override
  public void println(float f) throws IOException {
    super.println(f);
    contentWritten = true;
  }

  @Override
  public void println(double d) throws IOException {
    super.println(d);
    contentWritten = true;
  }

  @Override
  public void write(byte b[]) throws IOException {
    super.write(b);
    contentWritten = true;
  }

  @Override
  public void write(byte b[], int off, int len) throws IOException {
    super.write(b, off, len);
    contentWritten = true;
  }

  @Override
  public void write(int b) throws IOException {
    super.write(b);
    contentWritten = true;
  }

  boolean isContentWritten() {
    return contentWritten;
  }
}
