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

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.Cookie;

import com.google.opengse.parser.Callback;
import com.google.opengse.parser.Chset;
import com.google.opengse.parser.Parser;
import com.google.opengse.parser.Strcaselit;

/**
 * Utility for parsing the Cookie http header. Parser derived from
 * the original Netscape cookie specification:
 *   http://www.netscape.com/newsref/std/cookie_spec.html
 *
 * @author Peter Mattis
 */
public final class Cookies {
  private Cookies() { throw new AssertionError(); }

  private static final Logger LOGGER =
    Logger.getLogger(Cookies.class.getName());

  private static final Parser<Result> PARSER;

  private static class Result {
    ArrayList<Cookie> cookies_;
    int version_;
    boolean isPreviousCookieDropped;
    public Result() {
      this.cookies_ = new ArrayList<Cookie>();
      this.version_ = 0;
      isPreviousCookieDropped = false;
    }
  }

  public static ArrayList<Cookie> parse(Enumeration<String> enumeration) {
    if ((enumeration == null) || !enumeration.hasMoreElements()) {
      return null;
    }

    Result result = new Result();
    while (enumeration.hasMoreElements()) {
      String str = enumeration.nextElement();
      try {
        PARSER.parse(str, result);
      } catch (RuntimeException e) {
        result.cookies_.clear();
        LOGGER.log(Level.WARNING, "parsing cookie header '" + str + "'", e);
      }
    }

    return result.cookies_;
  }

  public static ArrayList<Cookie> parse(String str) {
    Result result = new Result();
    try {
      PARSER.parse(str, result);
    } catch (RuntimeException e) {
      result.cookies_.clear();
      LOGGER.log(Level.WARNING, "parsing cookie header '" + str + "'", e);
    }
    return result.cookies_;
  }

  private static String create(char[] buf, int start, int end) {
    if ((buf[start] == '\"') && (buf[end - 1] == '\"')) {
      if (end == start + 1) {
        // If the value is a single double quote the we don't want to leave end
        // < start since doing so would result in a StringIndexOutOfBound
        // exception when creating the String object below. Instead, we just
        // create an empty string for the value.
        LOGGER.log(Level.INFO, "malformed cookie value: '" +
                    new String(buf, start, end - start) + "'");
        start = end;
      } else {
        start += 1;
        end -= 1;
      }
    }
    return new String(buf, start, end - start);
  }

  private static class NameAction implements Callback<Result> {
    public void handle(char[] buf, int start, int end, Result result) {
      try {
        Cookie cookie = new Cookie(create(buf, start, end), "");
        cookie.setVersion(result.version_);
        result.cookies_.add(cookie);
        result.isPreviousCookieDropped = false;
      } catch (IllegalArgumentException ex) {
        // Drop this cookie and flag it is dropped so that we dont assign
        // the value of this cookie to previous cookie during ValueAction
        result.isPreviousCookieDropped = true;
        LOGGER.log(Level.INFO, "dropping cookie '" +
                    create(buf, start, end) + "'", ex);
      }
    }
  }

  private static class ValueAction implements Callback<Result> {
    public void handle(char[] buf, int start, int end, Result result) {
      if (result.isPreviousCookieDropped) {
        try {
          LOGGER.log(Level.INFO, "ignoring value for dropped cookie. '" +
                      create(buf, start, end) + "'");
        // Bug 223912: Don't throw exception if the cookie is bad
        } catch (StringIndexOutOfBoundsException errIndex) {
          LOGGER.log(Level.INFO,
                      "ignoring value for invalid dropped cookie. '" +
                      new String(buf, start, end - start) + "'");
        }
        return;
      }
      Cookie cookie = lastCookie(result.cookies_);
      cookie.setValue(create(buf, start, end));
    }
  }

  private static class VersionAction implements Callback<Result> {
    public void handle(char[] buf, int start, int end, Result result) {
      try {
        result.version_ =
          Integer.parseInt(new String(buf, start, end - start));
      } catch (NumberFormatException e) { /* ignored */ }
    }
  }

  private static class PathAction implements Callback<Result> {
    public void handle(char[] buf, int start, int end, Result result) {
      if (result.isPreviousCookieDropped) {
        LOGGER.log(Level.INFO, "ignoring path for dropped cookie. '" +
                    create(buf, start, end) + "'");
      } else {
        Cookie cookie = lastCookie(result.cookies_);
        cookie.setPath(create(buf, start, end));
      }
    }
  }

  private static class DomainAction implements Callback<Result> {
    public void handle(char[] buf, int start, int end, Result result) {
      if (result.isPreviousCookieDropped) {
        LOGGER.log(Level.INFO, "ignoring domain for dropped cookie. '" +
                    create(buf, start, end) + "'");
      } else {
        Cookie cookie = lastCookie(result.cookies_);
        cookie.setDomain(create(buf, start, end));
      }
    }
  }

  private static Cookie lastCookie(List<Cookie> cookies) {
    int size = cookies.size();
    return cookies.get(size - 1);
  }

  /**
   * From RFC 2109:
   *
   * cookie          =       "Cookie:" cookie-version
   *                         1*((";" | ",") cookie-value)
   * cookie-value    =       NAME "=" VALUE [";" path] [";" domain]
   * cookie-version  =       "$Version" "=" value
   * NAME            =       attr
   * VALUE           =       value
   * path            =       "$Path" "=" value
   * domain          =       "$Domain" "=" value
   *
   * NOTE: in order to correctly parse old cookies (from the original
   *  netscape specification), the cookie-version token above is taken
   *  as optional.
   * NOTE: the spec is further confused by Sun's servlet.jar, which
   *  disallows cookies created with "version", "path", "domain", all
   *  with lowercase and no '$' prefix. Either is allowed by this parser.
   * NOTE(4/23/05): Changed parsing to allow path and domain to be listed in
   *  either order (to support some bad mobile phone browsers).
   */
  static {
    Chset wsp = Chset.WHITESPACE;
    Chset nameToken = Chset.not(Chset.union(wsp, new Chset("=;,")));
    Chset valueToken = Chset.not(Chset.union(wsp, new Chset(";,")));

    Parser<Object> valueSep = Parser.sequence(wsp.star(), new Chset(";"));
    valueSep = Parser.sequence(valueSep, wsp.star());

    Parser<Object> cookieSep = Parser.sequence(wsp.star(), new Chset(";,"));
    cookieSep = Parser.sequence(cookieSep, wsp.star());
    cookieSep = cookieSep.plus();

    Parser<Object> version = new Strcaselit("version");
    version = Parser.sequence(new Chset("$").optional(), version);
    version = Parser.sequence(version, wsp.star());
    version = Parser.sequence(version, new Chset('='));
    version = Parser.sequence(version, wsp.star());
    Parser<Result> versionAction =
      Parser.sequence(version, valueToken.plus().action(new VersionAction()));
    versionAction =
      Parser.<Result>sequence(versionAction, cookieSep).optional();

    Parser<Object> path = new Strcaselit("path");
    path = Parser.sequence(new Chset("$").optional(), path);
    path = Parser.sequence(path, wsp.star());
    path = Parser.sequence(path, new Chset('='));
    path = Parser.sequence(path, wsp.star());
    Parser<Result> pathAction =
      Parser.sequence(path, valueToken.plus().action(new PathAction()));
    pathAction = Parser.sequence(valueSep, pathAction);

    Parser<Object> domain = new Strcaselit("domain");
    domain = Parser.sequence(new Chset("$").optional(), domain);
    domain = Parser.sequence(domain, wsp.star());
    domain = Parser.sequence(domain, new Chset('='));
    domain = Parser.sequence(domain, wsp.star());
    Parser<Result> domainAction =
      Parser.sequence(domain, valueToken.plus().action(new DomainAction()));
    domainAction = Parser.sequence(valueSep, domainAction);

    Parser<Result> name = nameToken.plus().action(new NameAction());
    Parser<Result> value = valueToken.plus().action(new ValueAction());
    Parser<Result> pathDomainAlternatives =
      Parser.<Result>alternative(pathAction, domainAction).star();
    value = Parser.sequence(value, pathDomainAlternatives);
    value = Parser.sequence(wsp.star(), value.optional());
    value = Parser.sequence(new Chset('='), value);

    Parser<Result> cookie = Parser.sequence(name, wsp.star());
    cookie = Parser.sequence(cookie, value.optional());

    PARSER = Parser.sequence(versionAction, cookie.list(cookieSep));
  }
}

