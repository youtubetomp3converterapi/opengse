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

package com.google.opengse;

/**
 * Holds constants used by the GSE
 *
 * @author Mike Jennings
 */
public final class GSEConstants {

  private GSEConstants() { /* Utility class: do not instantiate */ }

  public static final String SESSIONKEY_CLIENTADDRESS =
      "com.google.opengse.ClientAddress";
  static final String ATTRIBUTE_SOCKET_REMOTE_ADDRESS
      = "com.google.opengse.socket_remote_address";
  public static final String HEAD = "HEAD";
  public static final String CONTENT_ENCODING_IDENTITY = "identity";
  public static final String CONTENT_ENCODING_GZIP = "gzip";
  public static final String TRANSFER_ENCODING_IDENTITY = "identity";
  public static final String TRANSFER_ENCODING_CHUNKED = "chunked";
  public static final String POST = "POST";
  public static final String PUT = "PUT";
  public static final String OPTIONS = "OPTIONS";
  public static final String GET = "GET";
  public static final String DELETE = "DELETE";
  public static final String TRACE = "TRACE";
  public static final String CONNECT = "CONNECT";
}
