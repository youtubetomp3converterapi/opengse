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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * Utility for parsing a chunk header that is part of a chunked transfer
 * coding.
 *
 * @author Spencer Kimball
 */
public class ChunkHeader {
  private static final Parser<ChunkHeader> PARSER;

  private int chunk_size_ = -1;
  private ArrayList<Parameter> parameters_ = null;

  public int getChunkSize() {
    return chunk_size_;
  }

  public Iterator<Parameter> parameterIterator() {
    if (parameters_ != null) {
      return parameters_.iterator();
    }
    return null;
  }

  public String getParameter(String name, String def) {
    if (parameters_ != null) {
      for (Parameter param : parameters_) {
        if (name.equals(param.name_)) {
          return param.value_;
        }
      }
    }
    return def;
  }

  public static ChunkHeader parse(String str) throws IOException {
    ChunkHeader ch = new ChunkHeader();
    if (str != null) {
      int res = PARSER.parse(str, ch);
      if (res != str.length()) {
        throw new IOException("could not parse chunk header: " + str);
      }
    }
    return ch;
  }

  private static class ChunkSizeAction implements Callback<ChunkHeader> {
    public void handle(char[] buf, int start, int end, ChunkHeader ch) {
      ch.chunk_size_ =
          Integer.decode("0x" + new String(buf, start, end - start));
    }
  }

  private static class ParamNameAction implements Callback<ChunkHeader> {
    public void handle(char[] buf, int start, int end, ChunkHeader ch) {
      Parameter param = new Parameter();
      param.name_ = (new String(buf, start, end - start)).toLowerCase();
      if (ch.parameters_ == null) {
        ch.parameters_ = new ArrayList<Parameter>();
      }
      ch.parameters_.add(param);
    }
  }

  private static class ParamValueAction implements Callback<ChunkHeader> {
    public void handle(char[] buf, int start, int end, ChunkHeader ch) {
      Parameter param = ch.parameters_.get(ch.parameters_.size() - 1);
      param.value_ = new String(buf, start, end - start).
          replaceAll("\\\\(.)", "$1");
    }
  }

  static class Parameter {
    public String name_ = null;
    public String value_ = null;
  }

  static {
    // The parser is derived from the HTTP 1.1 specification in RFC 2616,
    // section 3.6.1.
    Chset special = new Chset("()<>@,;:\\\"/[]?={}");
    Chset token = Chset.difference(Chset.difference(Chset.ANYCHAR,
                                                    Chset.WHITESPACE),
                                   special);
    Chset text = new Chset("\1-\11\13\14\16-\177");
    Chset qtext = Chset.difference(new Chset("\1-\177"), new Chset("\15\"\\"));
    Chset hexChar = new Chset("0123456789abcdefABCDEF");
    Parser<ChunkHeader> chunkSize =
        hexChar.plus().action(new ChunkSizeAction());
    Parser<Object> wsp = Chset.WHITESPACE.star();
    chunkSize = Parser.sequence(wsp, chunkSize);
    chunkSize = Parser.sequence(chunkSize, wsp);
    Parser<ChunkHeader> quotedPair = Parser.sequence(new Chset('\\'), text);
    Parser<ChunkHeader> quotedString = Parser.alternative(qtext, quotedPair);
    quotedString = quotedString.star().action(new ParamValueAction());
    quotedString = Parser.sequence(new Chset('"'), quotedString);
    quotedString = Parser.sequence(quotedString, new Chset('"'));

    Parser<ChunkHeader> name = token.plus().action(new ParamNameAction());
    Parser<ChunkHeader> value = token.plus().action(new ParamValueAction());
    value = Parser.alternative(value, quotedString);

    Parser<ChunkHeader> parameters = Parser.sequence(wsp, new Chset(';'));
    parameters = Parser.sequence(parameters, wsp);
    parameters = Parser.sequence(parameters, name);
    parameters = Parser.sequence(parameters, wsp);
    parameters = Parser.sequence(parameters, new Chset('='));
    parameters = Parser.sequence(parameters, wsp);
    parameters = Parser.sequence(parameters, value);

    PARSER = Parser.sequence(chunkSize, parameters.star());
  }
}
