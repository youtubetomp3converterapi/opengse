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

package com.google.opengse.performance;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A servlet that echoes back whatever was sent in the body of the request.
 *
 * @author Wenbo Zhu
 */
@SuppressWarnings("serial")
public final class EchoServlet extends javax.servlet.http.HttpServlet {
  /**
   * Echoes back whatever was sent in the body of the request.
   *
   * <p>The response is always a document of type {@code "text/html"}, and the
   * request body is wrapped in {@code <html>} and {@code <body_buf>} tags.
   */
  @Override
  public void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException {
    PrintWriter out = null;
    try {
      // Get the binary output stream
      out = response.getWriter();

      // Set the response type
      response.setContentType("text/html");

      // Convert the input sequence to character string
      InputStream is = request.getInputStream();
      Reader ic = new InputStreamReader(is, request.getCharacterEncoding());

      StringBuilder bodyStr = new StringBuilder();
      char[] buffer = new char[8192];
      int length;
      while ((length = ic.read(buffer)) != -1) {
        bodyStr.append(buffer, 0, length);
      }

      out.println("<html>\n<body_buf>\n");
      out.println(bodyStr);
      out.println("\n</body_buf>\n</html>\n");

    } catch (RuntimeException e) {
      throw new ServletException(e.getMessage());
    } catch (IOException e) {
      throw new ServletException(e.getMessage());
    } finally {
      if (out != null) {
        out.close();
      }
    }
  }
}