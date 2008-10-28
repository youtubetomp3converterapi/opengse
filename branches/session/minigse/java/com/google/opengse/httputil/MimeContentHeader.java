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

import com.google.opengse.parser.Callback;
import com.google.opengse.parser.Parser;
import com.google.opengse.parser.Chset;
import com.google.opengse.util.string.StringUtil;
import com.google.opengse.util.string.CharEscaper;
import com.google.opengse.util.string.CharEscaperBuilder;
import com.google.opengse.util.string.CharEscapers;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * A base class for mime content headers that contain parameter lists,
 * such as Content-Type and Content-Disposition. The logic consolidated here
 * consists of parameter manipulation and formatting output. The concrete
 * subclasses contain the actual parsing logic.
 *
 * @author Darick Tong
 */
abstract class MimeContentHeader {

  private final int headerNameLength_;
  private List<Parameter> parameters_ = null;

  /**
   * Returns the value of the first parameter found with the
   * specified {@code name}, and if not found, returns {@code def}.
   *
   * @param name the name of the parameter to get.
   * @param def the default value if the parameter is not found.
   * @return the parameter value, or {@code def} if none.
   */
  public final String getParameter(String name, String def) {
    if (parameters_ != null) {
      for (Parameter param : parameters_) {
        if (name.equalsIgnoreCase(param.name_)) {
          return param.value_;
        }
      }
    }
    return def;
  }

  /**
   * Sets the parameter, adding it if it does not exist, and if it does,
   * replacing all occurrences with a single parameter.
   *
   * @param name the name of the parameter.
   * @param value the value of the parameter.
   */
  public final void setParameter(String name, String value) {
    getSingularParameter(name).value_ = value;
  }

  /**
   * Returns the first parameter with the given name, removing the rest.
   * If none are found, creates a new parameter with that name. In other
   * words, calling this method ensures that a one and only one parameter
   * with the given name exists.
   *
   * @param name the name of the parameter.
   * @return the singular Parameter object.
   */
  private Parameter getSingularParameter(String name) {
    if (parameters_ == null) {
      parameters_ = new ArrayList<Parameter>();
    }
    Parameter found = null;
    for (Iterator<Parameter> it = parameters_.iterator(); it.hasNext(); ) {
      Parameter param = it.next();
      if (name.equalsIgnoreCase(param.name_)) {
        if (found == null) {
          found = param;
        } else {
          it.remove();
        }
      }
    }
    if (found == null) {
      found = new Parameter();
      found.name_ = name;
      parameters_.add(found);
    }
    return found;
  }

  /**
   * Removes all instances of parameters with the given name.
   *
   * @param name the name of the parameter(s) to remove.
   */
  public final void removeParameter(String name) {
    if (parameters_ != null) {
      for (Iterator<Parameter> it = parameters_.iterator(); it.hasNext(); ) {
        if (name.equalsIgnoreCase(it.next().name_)) {
          it.remove();
          // Don't break because there may be multiple parameters with
          // the same name and we need to remove all of them.
        }
      }
    }
  }

  /**
   * @return a string representation of the header. If you need this to be
   *   wrapped according to the MIME specification, use
   *   {@link #toWrappedString}.
   */
  @Override
  public final String toString() {
    return toFormattedString(false);
  }

  /**
   * @return a value which wraps at parameter boundaries when the line
   *    exceeds 76 characters, as per the MIME header specification.
   */
  public final String toWrappedString() {
    return toFormattedString(true);
  }

  // Maximum line length according the MIME message specification.
  private static final int MAX_LINE_LENGTH = 76;

  private final String toFormattedString(boolean wrap) {
    StringBuilder buf = new StringBuilder(getMainValue());
    if (parameters_ != null) {
      formatParameters(buf, headerNameLength_, parameters_, wrap);
    }
    return buf.toString();
  }

  /**
   * @param buf the StringBuilder to append to.
   * @param headerNameLen length of the header name which should be
   *   accounted for when wrapping the line.
   * @param params the parameters to append.
   * @param wrap whether the parameters should be wrapped according to the
   *   MIME header specification.
   */
  static void formatParameters(StringBuilder buf, int headerNameLen,
                               Iterable<Parameter> params, boolean wrap) {
    // Account for the ": " in [Header-Name: ]
    int len = headerNameLen + buf.length() + 2;
    for (Parameter param : params) {
      buf.append(";");
      len++;
      String paramStr = paramToString(param);
      if (wrap) {
        int paramLen = paramStr.length() + 1;   // Add one for the space ' '
        if (len + paramLen >= MAX_LINE_LENGTH) {
          buf.append('\n');
          len = 0;
        }
        len += paramLen;
      }
      buf.append(" ").append(paramStr);
    }
  }

  // Turns the <"> and <\> characters into quoted pairs.
  private static final CharEscaper QUOTE_ESCAPE =
      new CharEscaperBuilder()
      .addEscape('\\', "\\\\")
      .addEscape('"', "\\\"")
      .toEscaper();

  // tspecials are characters that must be in a quoted-string.
  private static final String TSPECIAL_CHARS = "()<>@,;:\\\"/[]?=";

  // Combine with white space to determine all chars that need quoting.
  static final String MUST_QUOTE_CHARS =
      StringUtil.WHITE_SPACES + TSPECIAL_CHARS;

  /**
   * @param param a name/value parameter.
   * @return name=value if value has no spaces, and name="value" if it does.
   */
  static String paramToString(Parameter param) {
    StringBuilder buf = new StringBuilder(param.name_);
    buf.append('=');
    if (StringUtil.indexOfChars(param.value_, MUST_QUOTE_CHARS) < 0) {
      // No special characters that need to be quoted.
      buf.append(param.value_);
    } else {
      // Add quotes around the value, escaping quotes if necessary.
      String escapedValue = CharEscapers.escape(param.value_, QUOTE_ESCAPE);
      buf.append('"').append(escapedValue).append('"');
    }
    return buf.toString();
  }

  /**
   * Constructs a new instance with the given header name. The header name
   * is used in calculating the line length when formatting a wrapped value.
   */
  protected MimeContentHeader(String headerName) {
    headerNameLength_ = headerName.length();
  }

  /**
   * @return the main value of the header, without any parameters.
   */
  protected abstract String getMainValue();

  static class Parameter {
    public String name_ = null;
    public String value_ = null;
  }

  private static class ParamNameAction implements Callback<MimeContentHeader> {
    public void handle(char[] buf, int start, int end, MimeContentHeader hdr) {
      Parameter param = new Parameter();
      param.name_ = (new String(buf, start, end - start)).toLowerCase();
      if (hdr.parameters_ == null) {
        hdr.parameters_ = new ArrayList<Parameter>();
      }
      hdr.parameters_.add(param);
    }
  }

  private static class ParamValueAction
      implements Callback<MimeContentHeader> {
    public void handle(char[] buf, int start, int end, MimeContentHeader hdr) {
      Parameter param = hdr.parameters_.get(hdr.parameters_.size() - 1);
      param.value_ = new String(buf, start, end - start);
    }
  }

  /**
   * Creates a Parser for the parameter portion of the header. The {@code text}
   * and {@code qtext} are required because subclasses may need to accept
   * different patterns as parameter values. For example, some browsers
   * send non-ascii characters in the "filename" parameter for the
   * Content-Disposition header.
   *
   * @param text a Chset representing a character in a quoted pair.
   * @param qtext a Chset representing a character that need not be quoted.
   */
  static Parser<MimeContentHeader> createParameterParser(
      Chset text, Chset qtext) {
    Chset special = new Chset(TSPECIAL_CHARS);
    Chset token = Chset.difference(Chset.difference(Chset.ANYCHAR,
                                                    Chset.WHITESPACE),
                                   special);

    // TODO(spencer): handling of quoted pairs is not correct in this parser.
    // They are simply left as \CHAR. Changing this now, however, would cause
    // existing servers to get different (possibly incompatible) inputs.
    // See ChunkHeader.java for a correct implementation.
    Parser<MimeContentHeader> quotedPair =
        Parser.sequence(new Chset('\\'), text);
    Parser<MimeContentHeader> quotedString =
        Parser.alternative(qtext, quotedPair);
    quotedString = quotedString.star().action(new ParamValueAction());
    quotedString = Parser.sequence(new Chset('"'), quotedString);
    quotedString = Parser.sequence(quotedString, new Chset('"'));

    Parser<MimeContentHeader> name =
        token.plus().action(new ParamNameAction());
    Parser<MimeContentHeader> value =
      token.plus().action(new ParamValueAction());
    value = Parser.alternative(value, quotedString);

    Parser<Object> wsp = Chset.WHITESPACE.star();
    Parser<MimeContentHeader> parameters =
      Parser.sequence(wsp, new Chset(';'));
    parameters = Parser.sequence(parameters, wsp);
    parameters = Parser.sequence(parameters, name);
    parameters = Parser.sequence(parameters, wsp);
    parameters = Parser.sequence(parameters, new Chset('='));
    parameters = Parser.sequence(parameters, wsp);
    parameters = Parser.sequence(parameters, value);
    return parameters.star();
  }
}
