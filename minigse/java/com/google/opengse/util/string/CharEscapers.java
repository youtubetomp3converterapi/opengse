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

package com.google.opengse.util.string;

import java.io.IOException;

/**
 * Utility functions for dealing with {@code CharEscaper}s, and some commonly
 * used {@code CharEscaper} instances.
 *
 * @author Sven Mawson
 * @author Laurence Gonsalves
 */
public final class CharEscapers {
  private CharEscapers() {}

  /**
   * Escapes special characters from a string so it can safely be included in a
   * Python string literal. Does not have any special handling for non-ASCII
   * characters.
   */
  public static final CharEscaper PYTHON_ESCAPE = new CharEscaperBuilder()
      // TODO(laurence): perhaps this should escape non-ASCII characters?
      .addEscape('\n', "\\n")
      .addEscape('\r', "\\r")
      .addEscape('\t', "\\t")
      .addEscape('\\', "\\\\")
      .addEscape('\"', "\\\"")
      .addEscape('\'', "\\\'")
      .toEscaper();

  /**
   * Escapes special characters from a string so it can safely be included in an
   * XML document in element content.  Note that quotes are <em>not</em>
   * escaped, so <em>this is not safe for use in attribute values</em>. Use
   * {@link #XML_ESCAPE} for attribute values, or if you are unsure.  Also
   * removes non-whitespace control characters, as there is no way to represent
   * them in XML.
   */
  public static final CharEscaper XML_CONTENT_ESCAPE =
      newBasicXmlEscapeBuilder().toEscaper();

  private static CharEscaperBuilder newBasicXmlEscapeBuilder() {
    return new CharEscaperBuilder()
        .addEscape('&', "&amp;")
        .addEscape('<', "&lt;")
        .addEscape('>', "&gt;")
        .addEscapes(new char[] {
            '\000', '\001', '\002', '\003', '\004',
            '\005', '\006', '\007', '\010', '\013',
            '\014', '\016', '\017', '\020', '\021',
            '\022', '\023', '\024', '\025', '\026',
            '\027', '\030', '\031', '\032', '\033',
            '\034', '\035', '\036', '\037'}, "");
  }

  /**
   * Escapes special characters from a string so it can safely be included in a
   * Java string literal. Does <em>not</em> escape single-quotes, so use
   * JAVA_CHAR_ESCAPE if you are generating char literals, or if you are unsure.
   *
   * <p>Note that non-ASCII characters will be octal or Unicode escaped.
   */
  public static final CharEscaper JAVA_STRING_ESCAPE
      = new JavaCharEscaper(new CharEscaperBuilder()
          .addEscape('\b', "\\b")
          .addEscape('\f', "\\f")
          .addEscape('\n', "\\n")
          .addEscape('\r', "\\r")
          .addEscape('\t', "\\t")
          .addEscape('\"', "\\\"")
          .addEscape('\\', "\\\\")
          .toArray());

  /**
   * Escapes special characters from a string so it can safely be included in a
   * Java char literal or string literal.
   *
   * <p>Note that non-ASCII characters will be octal or Unicode escaped.
   *
   * <p>This is the same as {@link #JAVA_STRING_ESCAPE}, except that it also
   * escapes single-quotes.
   */
  public static final CharEscaper JAVA_CHAR_ESCAPE
      = new JavaCharEscaper(new CharEscaperBuilder()
          .addEscape('\b', "\\b")
          .addEscape('\f', "\\f")
          .addEscape('\n', "\\n")
          .addEscape('\r', "\\r")
          .addEscape('\t', "\\t")
          .addEscape('\'', "\\'")
          .addEscape('\"', "\\\"")
          .addEscape('\\', "\\\\")
          .toArray());

  /**
   * Escaper for java character escaping, contains both an array and a
   * backup function.  We're not overriding the array decorator because we
   * want to keep this as fast as possible, so no calls to super.escape first.
   */
  private static class JavaCharEscaper implements CharEscaper {

    private final char[][] replacements;
    private final int replacementLength;

    public JavaCharEscaper(char[][] replacements) {
      this.replacements = replacements;
      this.replacementLength = replacements.length;
    }

    public char[] escape(char c) {
      // First check if our array has a valid escaping.
      if (c < replacementLength) {
        char[] r = replacements[c];
        if (r != null) {
          return r;
        }
      }

      // This range is un-escaped.
      if (c >= 0x20 && c <= 0x7E) {
        return null;
      } else if (c <= 0xFF) {
        // Convert c to an octal-escaped string.
        // Equivalent to String.format("\\%03o", (int)c);
        char[] r = new char[4];
        r[0] = '\\';
        r[3] = DIGITS[c & 7];
        c >>>= 3;
        r[2] = DIGITS[c & 7];
        c >>>= 3;
        r[1] = DIGITS[c & 7];
        return r;
      } else {
        // Convert c to a hex-escaped string.
        // Equivalent to String.format("\\u%04x", (int)c);
        char[] r = new char[6];
        r[0] = '\\';
        r[1] = 'u';
        r[5] = DIGITS[c & 15];
        c >>>= 4;
        r[4] = DIGITS[c & 15];
        c >>>= 4;
        r[3] = DIGITS[c & 15];
        c >>>= 4;
        r[2] = DIGITS[c & 15];
        return r;
      }
    }

    /**
     * All possible chars for representing a number as a String
     */
    private static final char[] DIGITS = {
      '0' , '1' , '2' , '3' , '4' , '5' ,
      '6' , '7' , '8' , '9' , 'a' , 'b' ,
      'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
      'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
      'o' , 'p' , 'q' , 'r' , 's' , 't' ,
      'u' , 'v' , 'w' , 'x' , 'y' , 'z'
    };
  }

  /**
   * Escapes special characters from a string so it can safely be included in an
   * XML document in either element content or attribute values.  Also removes
   * nul-characters and control characters, as there is no way to represent them
   * in XML.
   */
  public static final CharEscaper XML_ESCAPE = newBasicXmlEscapeBuilder()
      .addEscape('"', "&quot;")
      .addEscape('\'', "&apos;")
      .toEscaper();

  /**
   * Fast escape method that takes an escaper which acts as a mapping between
   * characters and a string to escape them with.  This is a heavily optimized
   * version of string escaping, any changes to this method should be carefully
   * reviewed, as it is used by most of the escaping functions.
   *
   * @param s the string to be escaped.
   * @param escaper the escaper to map from characters to replacement strings.
   * @return an escaped version of the input string.
   * @throws NullPointerException if the string or escaper is null.
   */
  public static String escape(String s, CharEscaper escaper) {

    // This will throw a NullPointerException if s is null, this is what the
    // behavior of all the escaping methods was, so it is expected.
    int slen = s.length();

    // Superfastpath inlineable loop for strings that don't need escaping.
    for (int index = 0; index < slen; index++) {
      if (escaper.escape(s.charAt(index)) != null) {
        return escapeSlow(s, escaper, index);
      }
    }

    return s;
  }

  /**
   * A thread-local destination buffer to keep us from creating new buffers.
   * The starting size is 1024 characters.  If we grow past this we don't
   * put it back in the threadlocal, we just keep going and grow as needed.
   */
  private static final ThreadLocal<char[]> DEST_TL = new ThreadLocal<char[]>() {
    @Override
    protected char[] initialValue() {
      return new char[1024];
    }
  };

  /**
   * The amount of padding to use when growing the escape buffer.
   */
  private static final int DEST_PAD = 32;

  /**
   * "Slowpath" version of escaping, at this point we know that escaping is
   * needed, so we get a buffer to dump the data into and start
   * copying/escaping.
   */
  private static String escapeSlow(String s, CharEscaper ce, int index) {

    int slen = s.length();

    // Get a destination buffer and setup some loop variables.
    char[] dest = DEST_TL.get();
    int destSize = dest.length;
    int destIndex = 0;
    int lastEscape = 0;

    // Loop through the rest of the string, replacing when needed into the
    // destination buffer, which gets grown as needed as well.
    for (; index < slen; index++) {

      // Get a replacement for the current character.
      char[] r = ce.escape(s.charAt(index));

      // If no replacement is needed, just continue.
      if (r == null) {
        continue;
      }

      int rlen = r.length;
      int charsSkipped = index - lastEscape;  // Characters we skipped over.

      // This is the size needed to add the replacement, not the full
      // size needed by the string.  We only regrow when we absolutely must.
      int sizeNeeded = destIndex + charsSkipped + rlen;
      if (destSize < sizeNeeded) {
        destSize = sizeNeeded + (slen - index) + DEST_PAD;
        dest = growBuffer(dest, destIndex, destSize);
      }

      // If we have skipped any characters, we need to copy them now.
      if (charsSkipped > 0) {
        s.getChars(lastEscape, index, dest, destIndex);
        destIndex += charsSkipped;
      }

      // Copy the replacement string into the dest buffer as needed.
      if (rlen > 0) {
        System.arraycopy(r, 0, dest, destIndex, rlen);
        destIndex += rlen;
      }
      lastEscape = index + 1;
    }

    // Copy leftover characters if there are any.
    int charsLeft = slen - lastEscape;
    if (charsLeft > 0) {
      int sizeNeeded = destIndex + charsLeft;
      if (destSize < sizeNeeded) {

        // Regrow and copy, expensive!  No padding as this is the final copy.
        dest = growBuffer(dest, destIndex, sizeNeeded);
      }
      s.getChars(lastEscape, slen, dest, destIndex);
      destIndex = sizeNeeded;
    }
    return new String(dest, 0, destIndex);
  }

  /**
   * Helper method to grow the character buffer as needed, this only happens
   * once in a while so it's ok if it's in a method call.  If the index passed
   * in is 0 then no copying will be done.
   */
  private static final char[] growBuffer(char[] dest, int index, int size) {
    char[] copy = new char[size];
    if (index > 0) {
      System.arraycopy(dest, 0, copy, 0, index);
    }
    return copy;
  }

  /**
   * Escapes special characters from a string so it can safely be included in an
   * HTML document in either element content or attribute values. Does
   * <em>not</em> alter non-ASCII characters or control characters.
   */
  public static final CharEscaper HTML_ESCAPE = new CharEscaperBuilder()
      .addEscape('"', "&quot;")
      .addEscape('\'', "&#39;")
      .addEscape('&', "&amp;")
      .addEscape('<', "&lt;")
      .addEscape('>', "&gt;")
      .toEscaper();


  /**
   * Wraps an {@code Appendable} with another {@code Appendable} that will
   * escape characters (using a {@code CharEscaper}) before passing them to the
   * underlying {@code Appendable}. Note that if your end goal is to just create
   * a String, consider using {@link #escape(String, CharEscaper)} rather than
   * wrapping a {@code StringBuilder}.
   *
   * @param out the underlying {@code Appendable} to append escaped output to
   * @param escaper the {@code CharEscaper} which maps from characters to
   * replacement strings
   * @return an {@code Appendable} that will escape any characters "appended" to
   * it, and append the resulting escaped characters to {@code out}
   * @throws NullPointerException if {@code out} or {@code escaper} is null
   */
  public static Appendable escape(final Appendable out,
                                  final CharEscaper escaper) {
    // Fail fast if either input is null.
    Objects.nonNull(out);
    Objects.nonNull(escaper);

    return new Appendable() {
      public Appendable append(CharSequence csq) throws IOException {
        return append(csq, 0, csq.length());
      }

      public Appendable append(CharSequence csq, int start, int end)
          throws IOException {
        // TODO(laurence): investigate whether it's worthwhile to shorten the
        // "no escape" path (pushing the "escape" path into a separate method)
        // to make this more "inlineable".
        int unescapedChunkStart = start;
        for (int i = start; i < end; i++) {
          char[] escaped = escaper.escape(csq.charAt(i));
          if (escaped != null) {
            if (unescapedChunkStart < i) {
              out.append(csq, unescapedChunkStart, i);
            }
            outputChars(escaped);
            unescapedChunkStart = i + 1;
          }
        }
        if (unescapedChunkStart < end) {
          out.append(csq, unescapedChunkStart, end);
        }
        return this;
      }

      public Appendable append(char c) throws IOException {
        char[] escaped = escaper.escape(c);
        if (escaped == null) {
          out.append(c);
        } else {
          outputChars(escaped);
        }
        return this;
      }

      private void outputChars(char[] chars) throws IOException {
        for (char c : chars) {
          out.append(c);
        }
      }
    };
  }
}
