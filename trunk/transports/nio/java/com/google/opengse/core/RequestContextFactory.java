// Copyright 2007 Google Inc. All Rights Reserved.

package com.google.opengse.core;

import com.google.opengse.parser.Parser;
import com.google.opengse.iobuffer.IOBuffer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * A Factory for RequestContext objects.
 *
 * @author Mike Jennings
 */
public final class RequestContextFactory {
  private RequestContextFactory() { throw new AssertionError(); }

  private static final Parser<RequestContext> PARSER
      = ParserFactory.getParser();

  public static boolean parse(RequestContext context,
      ByteArrayOutputStream line_buf, IOBuffer buf) throws IOException {
    return context.parse(PARSER, line_buf, buf);
  }
}
