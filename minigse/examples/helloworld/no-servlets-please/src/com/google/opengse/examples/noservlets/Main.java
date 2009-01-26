// Copyright 2008 Google Inc. All Rights Reserved.

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
 * @author jennings@google.com (Mike Jennings)
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
