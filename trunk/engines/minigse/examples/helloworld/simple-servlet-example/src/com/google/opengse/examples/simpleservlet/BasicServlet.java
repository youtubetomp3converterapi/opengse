// Copyright 2009 Google Inc. All Rights Reserved.
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

package com.google.opengse.examples.simpleservlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

/**
 * A basic servlet which sends a "hello world"
 *
 * @author Mike Jennings
 */
public class BasicServlet extends HttpServlet {
  public BasicServlet() {
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
      ServletException, IOException {
    PrintWriter out = response.getWriter();
    out.print("Hello world!");
    out.flush();
  }
}
