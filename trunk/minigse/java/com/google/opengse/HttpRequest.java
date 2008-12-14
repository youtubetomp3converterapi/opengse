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

package com.google.opengse;

import java.util.Enumeration;
import java.util.Map;
import java.util.Locale;
import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.io.BufferedReader;

import javax.servlet.ServletInputStream;

/**
 * An interface which is a subset of HttpServletRequest. Each method signature
 * must match the method signature of the corresponding HttpServletRequest method.
 */
public interface HttpRequest {

  String getHeader(String name);

  Enumeration<String> getHeaders(String name);

  Enumeration<String> getHeaderNames();

  String getMethod();

  String getPathTranslated();

  String getQueryString();

  String getRequestURI();

  StringBuffer getRequestURL();

  void setCharacterEncoding(String env)
      throws UnsupportedEncodingException;


  Map<String, String[]> getParameterMap();

  Locale getLocale();

  Enumeration<Locale> getLocales();

  String getProtocol();

  String getScheme();

  /**
   * Retrieves the body of the request as binary data using
   * a {@link ServletInputStream}.  Either this method or
   * {@link #getReader} may be called to read the body, not both.
   *
   * @return			a {@link ServletInputStream} object containing
   * 				the body of the request
   *
   * @exception IllegalStateException  if the {@link #getReader} method
   * 					 has already been called for this request
   *
   * @exception IOException    	if an input or output exception occurred
   *
   */
  ServletInputStream getInputStream() throws IOException;
  
  /**
   * Retrieves the body of the request as character data using
   * a <code>BufferedReader</code>.  The reader translates the character
   * data according to the character encoding used on the body.
   * Either this method or {@link #getInputStream} may be called to read the
   * body, not both.
   *
   *
   * @return					a <code>BufferedReader</code>
   *						containing the body of the request
   *
   * @exception UnsupportedEncodingException 	if the character set encoding
   * 						used is not supported and the
   *						text cannot be decoded
   *
   * @exception IllegalStateException   	if {@link #getInputStream} method
   * 						has been called on this request
   *
   * @exception IOException  			if an input or output exception occurred
   *
   * @see 					#getInputStream
   *
   */
  BufferedReader getReader() throws IOException;

  /**
   * Get all of the connection information for this request, or
   * null if this is not supported by the implementation.
   */
  ConnectionInformation getConnectionInformation();
}
