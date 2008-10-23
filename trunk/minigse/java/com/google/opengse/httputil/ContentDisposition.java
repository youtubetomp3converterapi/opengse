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


/**
 * Utility for parsing the a Content-Disposition header. Parser derived
 * from the Content-Disposition header specification in RFC 1806.
 *
 * @author Spencer Kimball
 */
public class ContentDisposition extends MimeContentHeader {
  private static final Parser<ContentDisposition> PARSER;

  private String type_ = null;

  private ContentDisposition() {
    super("Content-Disposition");
  }

  public String getType() {
    return type_;
  }

  /** {@inheritDoc} */
  @Override
  protected String getMainValue() {
    return (type_ != null ? type_ : "");
  }

  public static ContentDisposition parse(String str) {
    ContentDisposition cd = new ContentDisposition();
    if (str != null) {
      PARSER.parse(str, cd);
    }
    return cd;
  }

  private static class TypeAction implements Callback<ContentDisposition> {
    public void handle(char[] buf, int start, int end, ContentDisposition cd) {
      cd.type_ = (new String(buf, start, end - start)).toLowerCase();
    }
  }

  static {
    Chset typeChar = Chset.union(Chset.ALNUM, new Chset(".-"));
    Parser<ContentDisposition> type =
        typeChar.plus().action(new TypeAction());
    // The text and qtext here differ from those in ContentType in that
    // they accept non-ascii characters to satisfy browser behavior. Non-ascii
    // characters are not legal according to the RFC, but some browsers send
    // them as the "filename" parameter.
    Chset text = Chset.difference(Chset.ANYCHAR, new Chset("\12\15"));
    Chset qtext = Chset.difference(Chset.ANYCHAR, new Chset("\15\"\\"));
    PARSER = Parser.sequence(type, createParameterParser(text, qtext));
  }
}
