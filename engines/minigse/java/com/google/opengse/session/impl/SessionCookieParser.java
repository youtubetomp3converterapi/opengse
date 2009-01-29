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

package com.google.opengse.session.impl;

import com.google.opengse.parser.Parser;
import com.google.opengse.parser.Callback;
import com.google.opengse.parser.Chset;


/**
 * Utility for parsing the session IDs by server type from the
 * session cookie. The session cookie typically has the following
 * form:
 * <servertype>=<id>[:<servertype>=<id>[:...]]
 *
 * @author Spencer Kimball
 */
final class SessionCookieParser {
  private static final Parser<SessionCookie> PARSER;

  public static SessionCookie parse(String str) {
    SessionCookie scookie = new SessionCookie();
    if (str != null) {
      PARSER.parse(str, scookie);
    }
    return scookie;
  }

  private static class ServerTypeAction implements Callback<SessionCookie> {
    public void handle(char[] buf, int start, int end, SessionCookie scookie) {
      scookie.serverType_ = new String(buf, start, end - start);
    }
  }

  private static class IdAction implements Callback<SessionCookie> {
    public void handle(char[] buf, int start, int end, SessionCookie scookie) {
      String id = new String(buf, start, end - start);
      // only keep the value as legal if the length is 11 or 22 characters
      if (id.length() == 11 || id.length() == 22) {
        scookie.serverTypes_.put(scookie.serverType_, id);
      }
    }
  }

  static {
    /**
     * The id part of the session cookie is expected to be web escaped
     * Base64 encoded, so uses a-zA-Z0-9_-, instead of a-zA-Z0-9+/
     */
    Chset typeCharSet = Chset.union(Chset.ALNUM, new Chset("_-"));
    Chset idCharSet = Chset.union(Chset.ALNUM, new Chset("_-"));
    Parser<SessionCookie> servertype =
        typeCharSet.plus().action(new ServerTypeAction());
    Parser<SessionCookie> id = idCharSet.plus().action(new IdAction());
    Parser<SessionCookie> pair =
        Parser.sequence(servertype, new Chset('='), id);
    pair = Parser.sequence(servertype, pair);
    Parser<SessionCookie> extrapair = Parser.sequence(new Chset(':'), pair);

    PARSER = Parser.sequence(pair, extrapair.star());
  }
}