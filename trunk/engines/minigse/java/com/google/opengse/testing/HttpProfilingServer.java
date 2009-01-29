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

package com.google.opengse.testing;

import com.google.opengse.ServletEngineConfiguration;
import com.google.opengse.ServletEngineConfigurationImpl;
import com.google.opengse.ServletEngine;
import com.google.opengse.HttpRequestHandler;
import com.google.opengse.HttpRequest;
import com.google.opengse.HttpResponse;
import com.google.opengse.ServletEngineFactory;
import com.google.opengse.jndi.JNDIMain;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * A server that does basic Http profiling.
 *
 * @author Mike Jennings
 */
public final class HttpProfilingServer {

  private HttpProfilingServer() { /* Launcher class: do not instantiate. */ }

  /**
   * <p>Run this and then hit http://${host}:8080/foo?chunks=50&chunksize=100 in
   * order to get 50 chunks of size 100 bytes sent.
   */
  public static void main(String[] args) throws Exception {
    ServletEngineConfiguration config =
        ServletEngineConfigurationImpl.create(8080, 5);
    ServletEngineFactory engineFactory
        = JNDIMain.lookup(ServletEngineFactory.class);
    ServletEngine engine = engineFactory.createServletEngine(new ByteSpewer(), config);
    engine.run();
  }

  private static class ByteSpewer implements HttpRequestHandler, FilterChain {
    private byte[] buf;

    private void fillBuf() {
      for (int i = 0; i < buf.length; ++i) {
        buf[i] = (byte) 'X';
      }
    }

    public void handleRequest(HttpRequest request, HttpResponse response) {
      try {
        processRequest(request, response);
      } catch (IOException e) {
        throw new RuntimeException(e);
      } catch (ServletException e) {
        throw new RuntimeException(e);
      }
    }

    private static String getParameter(HttpRequest request, String name) {
      String[] vals = request.getParameterMap().get(name);
      return (vals == null) ? null : vals[0];
    }

    private static String getParameter(HttpServletRequest request, String name) {
      String[] vals = (String[]) request.getParameterMap().get(name);
      return (vals == null) ? null : vals[0];
    }

    private void processRequest(HttpRequest request, HttpResponse response)
        throws IOException, ServletException {
      String schunks = getParameter(request, "chunks");
      if (schunks == null) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST,
            "No chunks param specified");
        return;
      }
      int chunks = Integer.parseInt(schunks);
      if (chunks < 1) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST,
            "Invalid chunks param specified");
        return;
      }
      String schunksize = getParameter(request,"chunksize");
      if (schunksize == null) {
        schunksize = "512";
      }
      int chunksize = Integer.parseInt(schunksize);
      if (chunksize < 1) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST,
            "Invalid chunksize param specified");
        return;
      }
      if (buf == null || buf.length < chunksize) {
        buf = new byte[chunksize];
        fillBuf();
      }
      response.setContentType("application/binary");
      ServletOutputStream ostr = response.getOutputStream();
      for (int i = 0; i < chunks; ++i) {
        ostr.write(buf, 0, chunksize);
      }
    }

    public void doFilter(ServletRequest _request, ServletResponse _response)
        throws IOException, ServletException {
      HttpServletResponse response = (HttpServletResponse) _response;
      HttpServletRequest request = (HttpServletRequest) _request;
      String schunks = getParameter(request, "chunks");
      if (schunks == null) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST,
            "No chunks param specified");
        return;
      }
      int chunks = Integer.parseInt(schunks);
      if (chunks < 1) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST,
            "Invalid chunks param specified");
        return;
      }
      String schunksize = getParameter(request,"chunksize");
      if (schunksize == null) {
        schunksize = "512";
      }
      int chunksize = Integer.parseInt(schunksize);
      if (chunksize < 1) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST,
            "Invalid chunksize param specified");
        return;
      }
      if (buf == null || buf.length < chunksize) {
        buf = new byte[chunksize];
        fillBuf();
      }
      response.setContentType("application/binary");
      ServletOutputStream ostr = response.getOutputStream();
      for (int i = 0; i < chunks; ++i) {
        ostr.write(buf, 0, chunksize);
      }
    }
  }

}
