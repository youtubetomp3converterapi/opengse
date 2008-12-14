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
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Iterator;
import java.util.Collection;

/**
 * Utility for parsing the Accept-Language http header. Parser derived
 * from the Accept-Language header specification in RFC 2068.
 *
 * @author Peter Mattis
 */
public class Locales implements Comparable<Locales> {
  private static final Parser<ArrayList<Locales>> PARSER;

  public int compareTo(Locales other) {
    Double d1 = quality;
    Double d2 = other.quality;
    return d2.compareTo(d1);
  }

  protected String language = null;
  protected String country = null;
  protected String variant = null;
  protected double quality = 1.0;

  public static ArrayList<Locale> parse(Enumeration<String> e) {
    if ((e == null) || !e.hasMoreElements()) {
      return new ArrayList<Locale>();
    }

    ArrayList<Locales> locales = new ArrayList<Locales>();
    while (e.hasMoreElements()) {
      PARSER.parse(e.nextElement(), locales);
    }
    Collections.sort(locales);
    return convert(locales);
  }

  public static ArrayList<Locale> parse(Collection<String> localesAsStrings) {
    if (localesAsStrings.isEmpty()) {
      return new ArrayList<Locale>();
    }

    ArrayList<Locales> locales = new ArrayList<Locales>();
    for (String localeAsString : localesAsStrings) {
      PARSER.parse(localeAsString, locales);
    }
    Collections.sort(locales);
    return convert(locales);
  }

  public static ArrayList<Locale> parse(String str) {
    ArrayList<Locales> locales = new ArrayList<Locales>();
    PARSER.parse(str, locales);
    Collections.sort(locales);
    return convert(locales);
  }

  private static ArrayList<Locale> convert(List<Locales> locales) {
    ArrayList<Locale> res = new ArrayList<Locale>();
    for (Locales locale : locales) {
      if (locale.quality > 0.0) {
        Locale l = (locale.language == null) ?
                   Locale.getDefault() :
                   new Locale(locale.language,
                              makeSafe(locale.country),
                              makeSafe(locale.variant));
        res.add(l);
      }
    }
    return res;
  }

  private static String makeSafe(String s) {
    return (s == null) ? "" : s;
  }

  private static class LanguageAction implements Callback<ArrayList<Locales>> {
    public void handle(char[] buf, int start, int end,
                       ArrayList<Locales> locales) {
      Locales l = new Locales();
      l.language = new String(buf, start, end - start);
      locales.add(l);
    }
  }

  private static class CountryAction implements Callback<ArrayList<Locales>> {
    public void handle(char[] buf, int start, int end,
                       ArrayList<Locales> locales) {
      Locales l = locales.get(locales.size() - 1);
      l.country = new String(buf, start, end - start);
    }
  }

  private static class VariantAction implements Callback<ArrayList<Locales>> {
    public void handle(char[] buf, int start, int end,
                       ArrayList<Locales> locales) {
      Locales l = locales.get(locales.size() - 1);
      l.variant = new String(buf, start, end - start);
    }
  }

  private static class StarAction implements Callback<ArrayList<Locales>> {
    public void handle(char[] buf, int start, int end,
                       ArrayList<Locales> locales) {
      locales.add(new Locales());
    }
  }

  private static class QualityAction implements Callback<ArrayList<Locales>> {
    public void handle(char[] buf, int start, int end,
                       ArrayList<Locales> locales) {
      Locales l = locales.get(locales.size() - 1);
      l.quality = Double.parseDouble(new String(buf, start, end - start));
    }
  }

  static {
    Chset wsp = Chset.WHITESPACE;

    Parser<Object> qnum = Parser.sequence(new Chset('.'),
                                          Chset.DIGIT.repeat(0, 3));
    qnum = Parser.sequence(new Chset("01"), qnum.optional());

    Parser<ArrayList<Locales>> qvalue =
      Parser.sequence(wsp.star(), new Chset(';'));
    qvalue = Parser.sequence(qvalue, wsp.star());
    qvalue = Parser.sequence(qvalue, new Strcaselit("q="));
    qvalue = Parser.sequence(qvalue, qnum.action(new QualityAction()));

    Parser<Object> tag = Chset.ALPHA.repeat(1, 8);
    Parser<ArrayList<Locales>> locale =
      Parser.sequence(tag.action(new VariantAction()),
                      tag.list(new Chset('-')).optional());
    locale = Parser.sequence(new Chset('-'), locale);
    locale = Parser.sequence(tag.action(new CountryAction()),
                             locale.optional());
    locale = Parser.sequence(new Chset('-'), locale);
    locale = Parser.sequence(tag.action(new LanguageAction()),
                             locale.optional());
    locale = Parser.alternative(locale,
                                (new Chset('*')).action(new StarAction()));
    locale = Parser.sequence(locale, qvalue.optional());

    Parser<Object> sep = Parser.sequence(wsp.star(), new Chset(','));
    sep = Parser.sequence(sep, wsp.star());

    PARSER = locale.list(sep);
  }
}

