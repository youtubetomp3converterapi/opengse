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

package com.google.opengse.httputil;

import com.google.opengse.parser.Parser;
import com.google.opengse.parser.Callback;
import com.google.opengse.parser.Chset;
import com.google.opengse.parser.Strcaselit;

import java.util.ArrayList;


/**
 * Utility for parsing the Range mime header. Parser derived from the Range
 * header specification in RFC 2616, section 14.35.
 *
 * <p>The BNF grammar:
 * <pre>
 *  ranges-specifier = byte-ranges-specifier
 *  byte-ranges-specifier = bytes-unit "=" byte-range-set
 *  byte-range-set  = 1#( byte-range-spec | suffix-byte-range-spec )
 *  byte-range-spec = first-byte-pos "-" [last-byte-pos]
 *  first-byte-pos  = 1*DIGIT
 *  last-byte-pos   = 1*DIGIT
 *
 *  suffix-byte-range-spec = "-" suffix-length
 *  suffix-length = 1*DIGIT
 * </pre>
 *
 * @author Spencer Kimball
 */
public final class Range {
  private static final Parser<Range> PARSER;

  public static final class Pair {
    private final int start;
    private final int end;
    Pair(int start, int end) {
      this.start = start;
      this.end = end;
    }
    public final int getStart() { return start; }
    public final int getEnd() { return end; }
  }

  private boolean valid = true;
  private String units = null;
  private final ArrayList<Pair> ranges = new ArrayList<Pair>();

  /**
   * Is the range specification valid and describes only monotonically
   * increasing, non-overlapping ranges? These criteria are required by
   * HttpResponse before it will respond with a partial response (response code
   * 206).
   *
   * @param contentLength the size of the response entity
   * @return <code>true</code> if all ranges in the specification are
   *         monotonically increasing and non-overlapping.
   */
  public boolean isValid(int contentLength) {
    if (valid == false) {
      return false;
    }
    if (ranges.size() == 0) {
      return false;
    }

    int lastEnd = -1;
    for (int i = 0; i < ranges.size(); i++) {
      Pair p = getRange(i, contentLength);
      if (p.getStart() <= lastEnd || p.getEnd() <= lastEnd) {
        return false;
      }
      lastEnd = p.getEnd();
    }
    return true;
  }

  /**
   * @return the units of the range specification. Will always be "bytes"
   *         in accordance with the HTTP/1.1 spec.
   */
  public String getUnits() {
    return units;
  }

  /**
   * @return the number of byte ranges.
   */
  public int getNumRanges() {
    return ranges.size();
  }

  /**
   * Returns the byte range for the specified range index. The index must be
   * between 0 and getNumRanges().
   *
   * @return the specified byte range as a pair of bytes. The range is
   * inclusive: [start, end].
   * @throws IndexOutOfBoundsException if index is out of range
   */
  public Pair getRange(int index) {
    return ranges.get(index);
  }

  /**
   * Returns the byte range for the specified range index, bounded using the
   * supplied content_length. The index must be between 0 and getNumRanges().
   *
   * @return the specified byte range as a pair of bytes. The range is
   * inclusive: [start, end].
   * @throws IndexOutOfBoundsException if index is out of range
   */
  public Pair getRange(int index, int contentLength) {
    Pair p = ranges.get(index);
    int start = p.getStart() == -1 ?
                Math.max(0, (contentLength - p.getEnd())) :
                Math.min(p.getStart(), contentLength);
    // The byte range is inclusive, so use content_length - 1.
    int end = p.getStart() == -1 || p.getEnd() == -1 ?
              (contentLength - 1) :
              Math.min(p.getEnd(), (contentLength - 1));
    // For the case where we have a non-satisfiable range like "-0"...
    if (end < start) {
      end = start;
    }
    return new Pair(start, end);
  }

  /**
   * Is the range specification satisfiable given the content length? On
   * failure, the HTTP response code should be 416 (Requested range not
   * satisfiable).
   *
   * @param contentLength the size of the response entity
   * @return <code>true</code> if at least one byte range starts at less than
   *         content_length or at least one suffix byte range (a byte range
   *         with start position == -1) has a non-zero length.
   */
  public boolean isSatisfiable(int contentLength) {
    for (int i = 0; i < ranges.size(); i++) {
      if (ranges.get(i).getStart() != -1 &&
          ranges.get(i).getStart() < contentLength) {
        return true;
      }
      if (ranges.get(i).getStart() == -1 &&
          ranges.get(i).getEnd() > 0) {
        return true;
      }
    }
    return false;
  }

  public static Range parse(String str) {
    Range range = new Range();
    if (str != null) {
      PARSER.parse(str, range);
    }
    return range;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < ranges.size(); i++) {
      Pair p = ranges.get(i);
      if (i != 0) {
        sb.append(", ");
      }
      sb.append("[").append(p.getStart()).append(" - ")
        .append(p.getEnd()).append("]");
    }
    return sb.toString();
  }

  private static class UnitsAction implements Callback<Range> {
    public void handle(char[] buf, int start, int end, Range range) {
      range.units = (new String(buf, start, end - start)).toLowerCase();
      if (!range.units.equals("bytes")) {
        range.valid = false;
      }
    }
  }

  private static class StartAction implements Callback<Range> {
    public void handle(char[] buf, int start, int end, Range range) {
      try {
        int s = Integer.parseInt(new String(buf, start, end - start));
        range.ranges.add(new Pair(s, -1));
      } catch (NumberFormatException nfe) {
        range.valid = false;
      }
    }
  }

  private static class EndAction implements Callback<Range> {
    public void handle(char[] buf, int start, int end, Range range) {
      int lastIndex = range.ranges.size() - 1;
      if (range.ranges.get(lastIndex).getEnd() != -1) {
        range.valid = false;
      }
      try {
        int e = Integer.parseInt(new String(buf, start, end - start));
        int s = range.ranges.get(lastIndex).getStart();
        if (e < s) {
          range.valid = false;
        }
        range.ranges.set(lastIndex, new Pair(s, e));
      } catch (NumberFormatException nfe) {
        range.valid = false;
      }
    }
  }

  private static class SuffixAction implements Callback<Range> {
    public void handle(char[] buf, int start, int end, Range range) {
      try {
        int suffix = Integer.parseInt(new String(buf, start, end - start));
        range.ranges.add(new Pair(-1, suffix));
      } catch (NumberFormatException nfe) {
        range.valid = false;
      }
    }
  }

  static {
    Parser<Object> wsp = Chset.WHITESPACE.star();
    Chset special = new Chset("()<>@,;:\\\"/[]?=");
    Chset token = Chset.difference(
        Chset.difference(Chset.ANYCHAR, Chset.WHITESPACE), special);

    Parser<Range> firstBytePos =
      Chset.DIGIT.plus().action(new StartAction());
    Parser<Range> lastBytePos = Chset.DIGIT.plus().action(new EndAction());
    Parser<Range> byteRangeSpec = Parser.sequence(
        firstBytePos, wsp, new Chset('-'), wsp, lastBytePos.optional());
    Parser<Range> suffixLength = Chset.DIGIT.plus().action(new SuffixAction());
    Parser<Range> suffixByteRangeSpec = Parser.sequence(
        new Chset('-'), wsp, suffixLength);

    Parser<Range> byteRangeSet = Parser.sequence(
        Parser.alternative(byteRangeSpec, suffixByteRangeSpec),
        Parser.sequence(
            wsp, new Chset(','), wsp,
            Parser.alternative(byteRangeSpec,
                               suffixByteRangeSpec)).star());

    Parser<Object> bytesUnit = new Strcaselit("bytes");
    Parser<Object> otherRangeUnit = token.plus();
    Parser<Range> rangeUnit = Parser.alternative(
        bytesUnit, otherRangeUnit).action(new UnitsAction());

    PARSER = Parser.sequence(
        rangeUnit, wsp, new Chset('='), wsp, byteRangeSet);
  }
}

