/*
 * $Header: /home/cvs/jakarta-watchdog-4.0/src/server/servlet-tests/WEB-INF/classes/tests/javax_servlet_http/HttpServletRequest/GetHeadersTestServlet.java,v 1.3 2002/01/11 22:29:14 rlubke Exp $ 
 * $Revision: 1.3 $
 * $Date: 2002/01/11 22:29:14 $
 *
 * ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 1999-2002 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Tomcat", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package tests.javax_servlet_http.HttpServletRequest;

import common.util.StaticLog;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletConfig;
import java.util.Enumeration;
import java.util.Vector;
import java.util.Set;
import java.util.HashSet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *	A  Test for getHeader(String)  Method
 */

public class GetHeadersTestServlet extends HttpServlet {

  private static final String TEST_PASSED_MESSAGE = "GetHeadersTest test PASSED";
  private static final String TEST_FAILED_MESSAGE = "GetHeadersTest test FAILED";
  private static final String MYHEADERVALUE1 = "en-us";
  private static final String MYHEADERVALUE2 = "ga-us";
  private static final String MYHEADERVALUE1_COMMA_2 = MYHEADERVALUE1 + ", " + MYHEADERVALUE2;

  public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    PrintWriter out = response.getWriter();

    StaticLog.resetLog();

    String headerName = "Accept-Language";
    Set<String> headerValues;
    try {
      headerValues = getHeaderValues(request, headerName);
      if (headerValues.contains(MYHEADERVALUE1) && headerValues.contains(MYHEADERVALUE2)) {
        headerValues.remove(MYHEADERVALUE1);
        headerValues.remove(MYHEADERVALUE2);
        if (headerValues.isEmpty()) {
          out.println(TEST_PASSED_MESSAGE);
        } else {
          out.println(TEST_FAILED_MESSAGE);
          out.println("    HttpServletRequestWrapper.getHeaders(" + headerName + ") method did not return the correct number of headers <BR>");
          out.println("    Other headers received were :<BR>");
          for (String otherHeader : headerValues) {
            out.println("     " + otherHeader + "<BR>");
          }

        }
      } else {
        if (headerValues.contains(MYHEADERVALUE1_COMMA_2) && headerValues.size() == 1) {
          out.println(TEST_PASSED_MESSAGE);
        } else {
          if (!headerValues.contains(MYHEADERVALUE1)) {
            printHeaderNotFound(out, headerName, MYHEADERVALUE1);
          }
          if (!headerValues.contains(MYHEADERVALUE2)) {
            printHeaderNotFound(out, headerName, MYHEADERVALUE2);
          }
        }
      }
    } catch (IOException ex) {
      out.println(TEST_FAILED_MESSAGE);
      out.println("    HttpServletRequestWrapper.getHeaders(" + headerName + ") method return the same header name twice <BR>");
      ex.printStackTrace(out);
    }


    Enumeration<String> e = StaticLog.readFromLog();

    while (e.hasMoreElements()) {
      String tmp = e.nextElement();
      out.println(tmp);
    }
  }

  private static void printHeaderNotFound(PrintWriter out, String headerName, String headerValue) {
    out.println(TEST_FAILED_MESSAGE);
    out.println("    HttpServletRequestWrapper.getHeaders(" + headerName
        + ") method did not return header value '" + headerValue + "' <BR>");
  }

  private static Set<String> getHeaderValues(HttpServletRequest request, String headerName) throws IOException {
    Enumeration<String> values = (Enumeration<String>) request.getHeaders(headerName);
    Set<String> set = new HashSet<String>();
    while (values.hasMoreElements()) {
      String value = values.nextElement();
      if (!set.add(value)) {
        throw new IOException("Duplicate value: " + value);
      }
    }
    return set;
  }


    public void service_old ( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        int count = 0;
        int expectedCount = 2;
        String expectedResult1 = "en-us";
        boolean expectedResult1Found = false;
        String expectedResult2 = "ga-us";
        boolean expectedResult2Found = false;
        String param = "Accept-Language";
        Enumeration en = request.getHeaders( param );

        if ( en.hasMoreElements() ) {
            Vector v = new Vector();

            while ( en.hasMoreElements() ) {
                String name = ( String ) en.nextElement();

                if ( name.equalsIgnoreCase( expectedResult1 ) ) {
                    if ( !expectedResult1Found ) {
                        count++;
                        expectedResult1Found = true;
                    } else {
                        out.println( "GetHeadersTest  test FAILED<BR>" );
                        out.println( "    HttpServletRequest.getHeaders(" + param + ") method return the same header name twice <BR>" );
                        out.println( "    The header already received was " + expectedResult1 + " <BR>" );
                    }
                } else if ( name.equalsIgnoreCase( expectedResult2 ) ) {
                    if ( !expectedResult2Found ) {
                        count++;
                        expectedResult2Found = true;
                    } else {
                        out.println( "GetHeadersTest  test FAILED<BR>" );
                        out.println( "    HttpServletRequest.getHeaders(" + param + ") method return the same header name twice <BR>" );
                        out.println( "    The header already received was " + expectedResult2 + " <BR>" );
                    }
                } else {
                    v.add( name );
                }
            }

            if ( count != expectedCount ) {
                out.println( "GetHeadersTest  test FAILED<BR>" );
                out.println( "    HttpServletRequest.getHeaders(" + param + ") method did not return the correct number of headers <BR>" );
                out.println( "    Expected count = " + expectedCount + "<BR>" );
                out.println( "    Actual count = " + count + "<BR>" );
                out.println( "    The expected headers received were :<BR>" );

                if ( expectedResult1Found ) {
                    out.println( expectedResult1 + "<BR>" );
                }

                if ( expectedResult2Found ) {
                    out.println( expectedResult2 + "<BR>" );
                }

                out.println( "    Other headers received were :<BR>" );

                for ( int i = 0;i <= v.size() - 1;i++ ) {
                    out.println( "     " + v.elementAt( i ).toString() + "<BR>" );
                }
            } else {
                out.println( "GetHeadersTest test PASSED" );
            }
        } else {
            out.println( "GetHeadersTest  test FAILED<BR>" );
            out.println( "    HttpServletRequest.getHeaders(" + param + ") an empty enumeration <BR>" );

        }
    }
}
