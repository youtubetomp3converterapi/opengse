// Copyright 2002-2006 Google Inc.
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

package com.google.opengse.core;

import com.google.opengse.iobuffer.IOBuffer;
import com.google.opengse.util.string.StringUtil;
import com.google.opengse.util.IteratorEnumeration;
import com.google.opengse.util.EmptyEnumeration;
import com.google.opengse.HeaderUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Provides a data structure for holding MIME headers. Used by
 * HttpRequest and HttpResponse to implement the appropriate portions
 * of the HttpServletRequest and HttpServletResponse APIs.
 *
 * @author Peter Mattis
 */
public class MimeHeaders {
  /**
   * Header length limit. to prevent D.O.S. attacks
   */
  protected static final int HEADER_LENGTH_LIMIT = 8192;
  /**
   * Header size limit (# of header lines). to prevent D.O.S. attacks
   */
  protected static final int HEADER_LIMIT = 32;


  /**
   * The list of MIME headers (String objects)
   */
  private ArrayList<Header> headers_ = new ArrayList<Header>();

  /**
   * The character encoding (defaults to "ISO-8859-1")
   */
  private String charsetName_ = "ISO-8859-1";

  public MimeHeaders() {
  }

  public MimeHeaders(String charsetName) {
    this.charsetName_ = charsetName;
  }

  public boolean parse(ByteArrayOutputStream lineBuffer, IOBuffer buf)
    throws IOException {
    while (buf.readLine(lineBuffer, HEADER_LENGTH_LIMIT)) {
      if (headers_.size() >= HEADER_LIMIT) {
        throw new IOException("header count exceeds limit of " +
                              HEADER_LIMIT);
      }
      boolean res = parseLine(lineBuffer.toString(charsetName_));
      lineBuffer.reset();
      if (res) {
        return true;
      }
    }
    return false;
  }

  private boolean parseLine(CharSequence seq)
    throws IOException {
    int pos = 0;

    if (seq.charAt(pos) == '\r') {
      pos += 1;
    }
    if (seq.charAt(pos) == '\n') {
      // an empty line indicates the end of the mime headers
      return true;
    }

    int len = seq.length();
    if ((len > 0) && (seq.charAt(len - 1) == '\n')) {
      len -= 1;
      if ((len > 0) && (seq.charAt(len - 1) == '\r')) {
        len -= 1;
      }
    }

    if (Character.isWhitespace(seq.charAt(pos))) {
      // continuation header
      if (headers_.isEmpty()) {
        throw new IOException("malformed mime headers: " + seq);
      }
      Header hdr = headers_.get(headers_.size() - 1);

      String value = seq.subSequence(pos, len).toString();
      String oldValue = hdr.values_.get(hdr.values_.size() - 1);
      String newValue = oldValue + value;
      hdr.values_.set(hdr.values_.size() - 1, newValue);
      return false;
    }

    // search for the ':'
    int start = pos;
    while ((pos < len) && (seq.charAt(pos) != ':')) {
      pos += 1;
    }

    String name = seq.subSequence(start, pos).toString();
    Header hdr = findHeader(name, true, true);

    if (pos < len) {
      // Strip off any whitespace at the start of the value
      pos += 1;
      while ((pos < len) && Character.isWhitespace(seq.charAt(pos))) {
        pos += 1;
      }
      String value = seq.subSequence(pos, len).toString();
      processHeadValue(hdr, value);
    }

    return false;
  }

  /**
   * We need understand/process multi-value head and parse the value
   * according to RFC-2616.
   */
  private void processHeadValue(Header hdr, String value) {
    if (hdr.name_.startsWith("Accept")) {
      addMultiValueHeader(hdr, value);
      /*
       * hack for the watchdog: we can't simply default to multi-value
       * (for safety and performance)
       */
    } else if (hdr.name_.endsWith("Header")) {
      addMultiValueHeader(hdr, value);
    } else {
      addSingleValueHeader(hdr, value);
    }
  }

  private void addSingleValueHeader(Header hdr, String value) {
    hdr.values_.add(value);
  }

  private void addMultiValueHeader(Header hdr, String value) {
    for (String singleValue : value.split(",")) {
      hdr.values_.add(singleValue.trim());
    }
  }

  public void addHeader(String name, String value) {
    Header hdr = findHeader(name, true, false);

    /*
     * Remove all instances of the header delimiter (CR or LF).
     *
     * Uncareful code may directly use client specified data as the value of an
     * HTTP header. A malicious client can take advantage of this by placing a
     * header delimiter in the data and appending arbitrary headers to it. This
     * removal prevents such header injection.
     */
    if (value != null) {
       value = StringUtil.collapse(value, "\r\n", "");
    }
    hdr.values_.add(value);
  }

  public void addDateHeader(String name, long value) {
    addHeader(name, HeaderUtil.toDateHeader(value));
  }

  public void addIntHeader(String name, int value) {
    addHeader(name, Integer.toString(value));
  }

  public boolean containsHeader(String name) {
    return (findHeader(name, false, false) != null);
  }

  public void setHeader(String name, String value) {
    Header hdr = findHeader(name, true, false);
    hdr.values_.clear();
    /*
     * Remove all instances of the header delimiter (CR or LF).
     *
     * Uncareful code may directly use client specified data as the value of an
     * HTTP header. A malicious client can take advantage of this by placing a
     * header delimiter in the data and appending arbitrary headers to it. This
     * removal prevents such header injection.
     */
    if (value != null) {
      value = StringUtil.collapse(value, "\r\n", "");
    }
    hdr.values_.add(value);
  }

  public void setDateHeader(String name, long value) {
    setHeader(name, HeaderUtil.toDateHeader(value));
  }

  public void setIntHeader(String name, int value) {
    setHeader(name, Integer.toString(value));
  }

  public String getHeader(String name) {
    Header hdr = findHeader(name, false, false);
    if (hdr != null) {
      return hdr.values_.get(0);
    }
    return null;
  }

  public Enumeration<String> getHeaders(String name) {
    Header hdr = findHeader(name, false, false);
    if (hdr != null) {
      // do NOT replace the following line with Iterators.asEnumeration()
      return new IteratorEnumeration<String>(hdr.values_.iterator());
    }
    return EmptyEnumeration.please();
  }

  public Enumeration<String> getHeaderNames() {
    return new HeaderEnumerator(headers_);
  }

  public boolean headerHasValue(String name, String value) {
    Header hdr = findHeader(name, false, false);
    if (hdr == null) {
      return false;
    }
    for (String aValue : hdr.values_) {
      if (value.equalsIgnoreCase(aValue)) {
        return true;
      }
    }
    return false;
  }

  public long getDateHeader(String name) {
    return HeaderUtil.toDateHeaderLong(getHeader(name));
  }

  public int getIntHeader(String name) {
    return HeaderUtil.toIntHeader(getHeader(name));
  }

  public void removeHeader(String name) {
    Header hdr = findHeader(name, false, false);
    if (hdr != null) {
      headers_.remove(headers_.indexOf(hdr));
    }
  }

  public void clearHeaders() {
    headers_.clear();
  }

  private Header findHeader(String name, boolean create,
                            boolean moveToBack) {
    for (int i = 0; i < headers_.size(); i++) {
      Header hdr = headers_.get(i);
      if (hdr.name_.equalsIgnoreCase(name)) {
        if (moveToBack) {
          headers_.remove(i);
          headers_.add(hdr);
        }
        return hdr;
      }
    }

    if (create) {
      Header hdr = new Header(name);
      headers_.add(hdr);
      return hdr;
    }

    return null;
  }

  public void print(PrintWriter writer) {
    for (Header hdr : headers_) {
      for (String value : hdr.values_) {
        writer.print(hdr.name_ + ": " + value + "\r\n");
      }
    }
    writer.print("\r\n");
  }

  public void writeIOBuffer(IOBuffer buf) throws IOException {
    byte[] colonSpace = ": ".getBytes();
    byte[] crlf = "\r\n".getBytes();

    for (Header hdr : headers_) {
      for (String value : hdr.values_) {
        if (value != null) {
          buf.writeBytes(hdr.name_.getBytes()); // ok, should be in latin-1
          buf.writeBytes(colonSpace);
          buf.writeBytes(value.getBytes(charsetName_));
          buf.writeBytes(crlf);
        }
      }
    }
    buf.writeBytes(crlf);
  }

  @Override
  public String toString() {
    StringWriter sw = new StringWriter();
    print(new PrintWriter(sw));
    return sw.toString();
  }

  private static class Header {
    String name_;
    ArrayList<String> values_ = new ArrayList<String>();

    Header(String name) {
      this.name_ = name;
    }
  }

  private static class HeaderEnumerator implements Enumeration<String> {
    private Iterator<Header> iterator_;

    public HeaderEnumerator(ArrayList<Header> headers) {
      this.iterator_ = headers.iterator();
    }

    public boolean hasMoreElements() {
      return iterator_.hasNext();
    }

    public String nextElement()
      throws NoSuchElementException {
      Header hdr = iterator_.next();
      return hdr.name_;
    }
  }
}