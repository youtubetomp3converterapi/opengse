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

package com.google.opengse.examples.noservlets;

import com.google.opengse.ServletEngine;
import com.google.opengse.ServletEngineFactory;
import com.google.opengse.ServletEngineConfigurationImpl;
import com.google.opengse.ServletEngineConfiguration;
import com.google.opengse.core.ServletEngineFactoryImpl;
//import com.google.opengse.jndi.JNDIMain;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

/**
 * An example which shows how to use OpenGSE without all that complicated
 * servlet stuff.
 *
 * Usage: invoke this main method and then try loading http://localhost:8080 in your web browser 
 *
 * @author Mike Jennings
 */
public class Main {
  public static void main(String[] args) throws Exception {

    FilterChain helloWorld = new FilterChain() {
      public void doFilter(ServletRequest request, ServletResponse resp)
      throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) resp;
        response.setContentType("text/plain");
        response.getWriter().println("Hello World");
      }
    };

// uncomment the following line if you want to be able to swap in different engines easily
//    ServletEngineFactory engineFactory = JNDIMain.lookup(ServletEngineFactory.class);

    // we'll hard-code the factory implementation now, for the sake of Simon Stewart
    // who's brother was killed by JNDIMain and he's never forgiven that class
    ServletEngineFactory engineFactory = new ServletEngineFactoryImpl();
    int port = 8080;
    int maxThreads = 5;
    ServletEngineConfiguration config = ServletEngineConfigurationImpl.create(port, maxThreads);
    ServletEngine engine = engineFactory.createServletEngine(helloWorld, config);
    engine.run();
  }

}
