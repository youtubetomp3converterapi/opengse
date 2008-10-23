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
 * Utility for parsing the Content-Type mime header. Parser derived
 * from the Content-Type header specification in RFC 2045.
 *
 * @author Peter Mattis
 */
public class ContentType extends MimeContentHeader {
  private static final Parser<ContentType> PARSER;

  private String media = null;
  private String subtype = null;

  private ContentType() {
    super("Content-Type");
  }

  public String getMedia() {
    return media;
  }

  public String getSubtype() {
    return subtype;
  }

  public String getType(String def) {
   if ((media != null) && (subtype != null)) {
      return media + "/" + subtype;
    }
    return def;
  }

  /** {@inheritDoc} */
  @Override
  protected String getMainValue() {
    StringBuilder buf = new StringBuilder();
    if (media != null) {
      buf.append(media);
    }
    if (subtype != null) {
      buf.append('/').append(subtype);
    }
    return buf.toString();
  }

  public static ContentType parse(String str) {
    ContentType ctype = new ContentType();
    if (str != null) {
      PARSER.parse(str, ctype);
    }
    return ctype;
  }

  private static class MediaAction implements Callback<ContentType> {
    public void handle(char[] buf, int start, int end, ContentType ctype) {
      ctype.media = (new String(buf, start, end - start)).toLowerCase();
    }
  }

  private static class SubtypeAction implements Callback<ContentType> {
    public void handle(char[] buf, int start, int end, ContentType ctype) {
      ctype.subtype = (new String(buf, start, end - start)).toLowerCase();
    }
  }

  static {
    // The type_char Chset is different from that of the ContentDisposition
    // in that the '+' character is accepted, e.g.
    // Content-Type: application/xhtml+xml
    Chset typeChar = Chset.union(Chset.ALNUM, new Chset(".+-"));
    // text and qtext also differ from the allowed param types in
    // ContentDisposition (ContentDisposition allows non-ascii characters)
    // and thus must be declared here and passed into createParameterParser().
    Chset text = new Chset("\1-\11\13\14\16-\177");
    Chset qtext = Chset.difference(new Chset("\1-\177"), new Chset("\15\"\\"));

    Parser<ContentType> media = typeChar.plus().action(new MediaAction());
    Parser<ContentType> subtype = typeChar.plus().action(new SubtypeAction());
    // subtype has been made optional despite the fact that RFC2045
    // does not specifically allow it. This will allow the parameter, value
    // sets to be parsed even when subtype is not specified.
    subtype = Parser.sequence(new Chset('/'), subtype);
    Parser<ContentType> mimeTypeDecl = Parser.sequence(media,
                                                       subtype.optional());
    PARSER = Parser.sequence(mimeTypeDecl,
                              createParameterParser(text, qtext));
  }
}
