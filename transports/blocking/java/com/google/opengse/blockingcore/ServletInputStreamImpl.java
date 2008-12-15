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


import javax.servlet.ServletInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.BufferedReader;

/**
 * @author jennings
 *         Date: Jul 27, 2008
 */
class ServletInputStreamImpl extends ServletInputStream {
  private InputStream realStream;
  private RequestMetaData requestMetaData;
  private int contentLength;
  private int bytesRead;
  private static final String CONTENT_LENGTH_HEADER = "Content-Length";

  ServletInputStreamImpl(RequestMetaData requestMetaData, InputStream realStream) throws IOException {
    this.requestMetaData = requestMetaData;
    this.realStream = new BufferedInputStream(realStream);
    HttpHeaders headers = requestMetaData.getHeaders();
    if (headers.containsHeader(CONTENT_LENGTH_HEADER)) {
      String contentLengthAsString = headers.getHeaderValues(CONTENT_LENGTH_HEADER).iterator().next();
      contentLength = Integer.parseInt(contentLengthAsString);
    } else {
      contentLength = -1; // unknown content length
    }
    bytesRead = 0;
  }

  public int read() throws IOException {
    if (contentLength != -1 && bytesRead == contentLength) {
      return -1;
    }
    int thebyte = realStream.read();
    if (thebyte != -1) {
      ++bytesRead;
    }
    return thebyte;
  }


/*
  We comment this out so all reads are funnelled through the read() method above
  @Override
  public int read(byte b[], int off, int len) throws IOException {
    return realStream.read(b, off, len);    
  }
*/
  
}
