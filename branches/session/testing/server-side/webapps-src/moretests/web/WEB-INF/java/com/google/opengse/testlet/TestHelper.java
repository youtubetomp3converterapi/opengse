// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.opengse.testlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.Enumeration;
import java.util.TreeSet;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;

/**
 * Utility methods to help the testlets
 *
 * @author Mike Jennings
 */
public class TestHelper {
  private static final String INCLUDE_PREFIX = "javax.servlet.include.";
  private static final String INCLUDE_REQUEST_URI = INCLUDE_PREFIX + "request_uri";
  private static final String INCLUDE_CONTEXT_PATH = INCLUDE_PREFIX + "context_path";
  private static final String INCLUDE_SERVLET_PATH = INCLUDE_PREFIX + "servlet_path";
  private static final String INCLUDE_PATH_INFO = INCLUDE_PREFIX + "path_info";
  private static final String INCLUDE_QUERY_STRING = INCLUDE_PREFIX + "query_string";


  private static final String FORWARD_PREFIX = "javax.servlet.forward.";
  private static final String FORWARD_REQUEST_URI = FORWARD_PREFIX + "request_uri";
  private static final String FORWARD_CONTEXT_PATH = FORWARD_PREFIX + "context_path";
  private static final String FORWARD_SERVLET_PATH = FORWARD_PREFIX + "servlet_path";
  private static final String FORWARD_PATH_INFO = FORWARD_PREFIX + "path_info";
  private static final String FORWARD_QUERY_STRING = FORWARD_PREFIX + "query_string";

  private TestHelper() {
  }

  public static void printUriStuff(HttpServletRequest request, PrintWriter out) throws ServletException, IOException {
    out.println("requestURI=" + request.getRequestURI());
    out.println("requestURL=" + request.getRequestURL());
    out.println("contextPath=" + request.getContextPath());
    out.println("servletPath=" + request.getServletPath());
    out.println("pathInfo=" + request.getPathInfo());
    String queryString = request.getQueryString();
    out.println("queryString=" + queryString);
  }

  public static void printAttributeStuff(HttpServletRequest request, PrintWriter out) throws ServletException, IOException {
    out.println("attribute " + INCLUDE_REQUEST_URI +"=" + request.getAttribute(INCLUDE_REQUEST_URI));
    out.println("attribute " + INCLUDE_CONTEXT_PATH +"=" + request.getAttribute(INCLUDE_CONTEXT_PATH));
    out.println("attribute " + INCLUDE_SERVLET_PATH +"=" + request.getAttribute(INCLUDE_SERVLET_PATH));
    out.println("attribute " + INCLUDE_PATH_INFO +"=" + request.getAttribute(INCLUDE_PATH_INFO));
    out.println("attribute " + INCLUDE_QUERY_STRING +"=" + request.getAttribute(INCLUDE_QUERY_STRING));
    out.println("attribute " + FORWARD_REQUEST_URI +"=" + request.getAttribute(FORWARD_REQUEST_URI));
    out.println("attribute " + FORWARD_CONTEXT_PATH +"=" + request.getAttribute(FORWARD_CONTEXT_PATH));
    out.println("attribute " + FORWARD_SERVLET_PATH +"=" + request.getAttribute(FORWARD_SERVLET_PATH));
    out.println("attribute " + FORWARD_PATH_INFO +"=" + request.getAttribute(FORWARD_PATH_INFO));
    out.println("attribute " + FORWARD_QUERY_STRING +"=" + request.getAttribute(FORWARD_QUERY_STRING));
  }

  public static void printHeaderStuff(HttpServletRequest request, PrintWriter out) throws ServletException, IOException {
    Enumeration hnames =request.getHeaderNames();
    Map<String, Set<String> > uheaders = new TreeMap<String, Set<String>>();
    while (hnames.hasMoreElements()) {
      String hname = (String)hnames.nextElement();
      Set<String> values = toSet(request.getHeaders(hname));
      uheaders.put(hname.toUpperCase(), values);
    }
    // okay, now we have the headers in a normalized form. Let's see what they are
    for (String hname : uheaders.keySet()) {
      Set<String> values = uheaders.get(hname);
      out.println(hname + ": " + toString(values));
    }
  }


  private static String toString(Set<String> set) {
    StringBuilder sb = null;
    for (String s : set) {
      if (sb == null) {
        sb = new StringBuilder(s);
      } else {
        sb.append(", ").append(s);
      }
    }
    return sb.toString();
  }

  private static Set<String> toSet(Enumeration en) {
    Set<String> set = new TreeSet<String>();
    while(en.hasMoreElements()) {
      set.add((String)en.nextElement());
    }
    return set;
  }

}
