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

package com.google.opengse.core;

import com.google.opengse.parser.Callback;
import com.google.opengse.parser.Parser;
import com.google.opengse.parser.Strcaselit;
import com.google.opengse.parser.Chset;
import com.google.opengse.GSEConstants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * A static factory method for creating a Parser.
 *
 * @author Mike Jennings
 */
public final class ParserFactory {
  private ParserFactory() { throw new AssertionError(); }

  private static final Set<String> STANDARD_METHODS
      = new HashSet<String>(Arrays.asList(
        GSEConstants.GET,
        GSEConstants.PUT,
        GSEConstants.POST,
        GSEConstants.HEAD,
        GSEConstants.OPTIONS,
        GSEConstants.DELETE,
        GSEConstants.TRACE,
        GSEConstants.CONNECT));

  static boolean isStandardMethod(String method) {
    return STANDARD_METHODS.contains(method);
  }

  static class MethodAction implements Callback<RequestContext> {
    private final String method_;

    public MethodAction() {
      this(null);
    }

    public MethodAction(String method) {
      this.method_ = method;
    }

    /**
     * Parse the http method from the supplied characters.
     * @param buf
     * @param start
     * @param end
     * @param req
     */
    public void handle(char[] buf, int start, int end, RequestContext req) {
      if (method_ != null) {
        req.setMethod(method_);
      } else {
        req.setMethod(String.valueOf(buf, start, end - start));
      }
    }
  }

  // HTTP request line parsing code
  static class UriAction implements Callback<RequestContext> {
    public void handle(char[] buf, int start, int end, RequestContext req) {
      String uriStr = String.valueOf(buf, start, end - start);
      req.setURI(uriStr);
    }
  }

  static class MajorVersionAction implements Callback<RequestContext> {
    public void handle(char[] buf, int start, int end, RequestContext req) {
      req.setMajorVersion(Integer.parseInt(String.valueOf(buf, start,
          end - start)));
    }
  }

  static class MinorVersionAction implements Callback<RequestContext> {
    public void handle(char[] buf, int start, int end, RequestContext req) {
      req.setMinorVersion(Integer.parseInt(String.valueOf(buf, start,
                                                          end - start)));
    }
  }


  static Parser<RequestContext> getParser() {
    Chset wsp = new Chset(" \t\r\n");

    // recognize standard methods
    Parser<RequestContext> method = null;
    for (String methodName : STANDARD_METHODS) {
      Parser<RequestContext> p = new Strcaselit(methodName).action(
          new ParserFactory.MethodAction(methodName));
      if (method == null) {
        method = p;
      } else {
        method = Parser.alternative(method, p);
      }
    }

    // support nonstandard methods too
    method = Parser.alternative(
        method,
        Chset.ALPHA.plus().action(new ParserFactory.MethodAction()));

    Parser<RequestContext> uri
        = Chset.not(wsp).plus().action(new ParserFactory.UriAction());

    Parser<RequestContext> version =
      Parser.sequence(new Strcaselit("http/"),
                      Chset.DIGIT.plus().action(new MajorVersionAction()));
    version = Parser.sequence(version, new Chset('.'));
    version = Parser.sequence(version,
      Chset.DIGIT.plus().action(new MinorVersionAction()));

    Parser<RequestContext> reqline = Parser.sequence(method, wsp.plus());
    reqline = Parser.sequence(reqline, uri);
    reqline = Parser.sequence(reqline, wsp.plus());
    reqline = Parser.sequence(reqline, version);

    return reqline;
  }

}
