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

package com.google.opengse.blockingcore;

import java.io.IOException;

/**
 * @author jennings
 *         Date: Jul 22, 2008
 */
final class HttpRequestType {
  private static final String HTTP_1_0 = "HTTP/1.0";
  private String type, data, version;

  HttpRequestType(String line) throws IOException {
    type = getType(line);
    data = getRequestData(line);
    version = getHttpVersion(line);
    if (data == null) {
      throw new IOException("Cannot parse '" + line + "'");
    }
  }

  HttpRequestType(String type, String data, String version) {
    this.type = type;
    this.data = data;
    this.version = version;
  }

  HttpRequestType(String type, String data) {
    this(type, data, HTTP_1_0);
  }

  String getType() {
    return type;
  }

  private static String getType(String line) {
    if (line == null) {
      return null;
    }
    int space = line.indexOf(' ');
    if (space == -1) {
      return null;
    }
    return line.substring(0, space).toUpperCase();
  }

  String getRequestData() {
    return data;
  }

  private static String getRequestData(String line) {
    int space = line.indexOf(' ');
    if (space == -1) {
      return null;
    }
    String data = line.substring(space + 1);
    space = data.indexOf(' ');
    if (space == -1) {
      return data;
    } else {
      return data.substring(0, space);
    }
  }

  String getHttpVersion() {
    return version;
  }

  String getHttpVersion(String line) {
    int space = line.indexOf(' ');
    if (space == -1) {
      return HTTP_1_0;
    }
    String data = line.substring(space + 1);
    space = data.indexOf(' ');
    if (space == -1) {
      return HTTP_1_0;
    } else {
      return data.substring(space + 1);
    }
  }

  @Override public String toString() {
    return type + " " + data + " " + version;
  }

}
