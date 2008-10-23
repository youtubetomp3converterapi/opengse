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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * A simple test servlet that waits for a given amount of time before
 * responding.
 *
 * <p>This servlet accepts an integer {@code time} parameter (in milliseconds)
 * and sleeps for at least that duration before responding.
 *
 * @author Wenbo Zhu
 */
public final class WaitServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  public void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    PrintWriter out = null;
    //long start = System.currentTimeMillis();

    try {
      // Get the binary output stream
      out = response.getWriter();

      // Set the response type
      response.setContentType("text/html");

      // Get the input stream and read all the data
      InputStream is = request.getInputStream();
      byte[] buffer = new byte[8192];
      while (is.read(buffer) != -1) {
        // do nothing
      }

      // sleep for the wait interval
      long wait = Long.parseLong(request.getParameter("time"));
      Thread.sleep(wait);

      out.println("<html>\n<body>\n");
      out.println("</body>\n</html>\n");

    } catch (InterruptedException e) {
      throw new ServletException(e.getMessage());
    } catch (RuntimeException e) {
      throw new ServletException(e.getMessage());
    } finally {
      if (out != null) {
        out.close();
      }
    }
  }
}