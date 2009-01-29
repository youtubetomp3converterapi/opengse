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
import java.util.List;
import java.util.Collections;
import java.util.Enumeration;

/**
 * Utility for parsing Accept http headers. Parser derived
 * from the Accept header specification in RFC 2616.
 *
 * @author Spencer Kimball
 */
public class AcceptHeader implements Comparable<AcceptHeader> {
  private static final Parser<ArrayList<AcceptHeader>> parser;

  public int compareTo(AcceptHeader other) {
    Double d1 = quality_;
    Double d2 = other.quality_;
    return d2.compareTo(d1);
  }

  protected String type_ = null;
  protected double quality_ = 1.0;

  public AcceptHeader(String type, double quality) {
    this.type_ = type;
    this.quality_ = quality;
  }

  public String getType() {
    return type_;
  }

  public double getQuality() {
    return quality_;
  }

  public static List<AcceptHeader> parse(Enumeration<String> e) {
    if ((e == null) || !e.hasMoreElements()) {
      return new ArrayList<AcceptHeader>();
    }

    ArrayList<AcceptHeader> types = new ArrayList<AcceptHeader>();
    while (e.hasMoreElements()) {
      parser.parse(e.nextElement(), types);
    }
    Collections.sort(types);
    return types;
  }

  public static List<AcceptHeader> parse(String str) {
    ArrayList<AcceptHeader> types = new ArrayList<AcceptHeader>();
    parser.parse(str, types);
    Collections.sort(types);
    return types;
  }

  private static class TypeAction
      implements Callback<ArrayList<AcceptHeader>> {
    public void handle(char[] buf, int start, int end,
                       ArrayList<AcceptHeader> types) {
      AcceptHeader l = new AcceptHeader(null, 1.0);
      l.type_ = new String(buf, start, end - start);
      types.add(l);
    }
  }

  private static class QualityAction
      implements Callback<ArrayList<AcceptHeader>> {
    public void handle(char[] buf, int start, int end,
                       ArrayList<AcceptHeader> types) {
      AcceptHeader l = types.get(types.size() - 1);
      l.quality_ = Double.parseDouble(new String(buf, start, end - start));
    }
  }

  static {
    Chset wsp = Chset.WHITESPACE;

    Parser<Object> qnum =
      Parser.sequence(new Chset('.'), Chset.DIGIT.repeat(0, 3));
    qnum = Parser.sequence(new Chset("01"), qnum.optional());

    Parser<ArrayList<AcceptHeader>> qvalue;
    qvalue = Parser.sequence(wsp.star(), new Chset(';'));
    qvalue = Parser.sequence(qvalue, wsp.star());
    qvalue = Parser.sequence(qvalue, new Strcaselit("q="));
    qvalue = Parser.sequence(qvalue, qnum.action(new QualityAction()));

    Parser<Object> tag = Chset.not(Chset.union(Chset.WHITESPACE,
                                       new Chset(";,"))).plus();
    Parser<ArrayList<AcceptHeader>> type = tag.action(new TypeAction());
    type = Parser.alternative(type, (new Chset('*')).action(new TypeAction()));
    type = Parser.sequence(type, qvalue.optional());

    Parser<Object> sep = Parser.sequence(wsp.star(), new Chset(','));
    sep = Parser.sequence(sep, wsp.star());

    parser = type.list(sep);
  }
}
