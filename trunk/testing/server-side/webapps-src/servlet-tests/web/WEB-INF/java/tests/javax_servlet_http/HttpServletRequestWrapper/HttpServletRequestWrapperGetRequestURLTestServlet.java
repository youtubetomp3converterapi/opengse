/*
 * $Header: /home/cvs/jakarta-watchdog-4.0/src/server/servlet-tests/WEB-INF/classes/tests/javax_servlet_http/HttpServletRequestWrapper/HttpServletRequestWrapperGetRequestURLTestServlet.java,v 1.3 2002/08/05 02:33:32 rlubke Exp $ 
 * $Revision: 1.3 $
 * $Date: 2002/08/05 02:33:32 $
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

package tests.javax_servlet_http.HttpServletRequestWrapper;

import javax.servlet.http.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import common.util.StaticLog;

/**
 *	A Test for getRemoteUser method
 */

public class HttpServletRequestWrapperGetRequestURLTestServlet extends HttpServlet {

    StaticLog sl = new StaticLog();
  private static final String SERVER = "serverName";

  public void service ( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        sl.resetLog();

        boolean found1 = false;
        boolean found2 = false;
        boolean found3 = false;
        boolean found4 = false;

        String prefix = null;
        String server = null;
        String port = null;
        String path = null;

        for (Enumeration e = request.getHeaderNames(); e.hasMoreElements(); ) {
            String headerName = (String) e.nextElement();

            if ( headerName.equals( "prefix" ) ) {
                prefix = request.getHeader( headerName );
                found1 = true;
            } else if ( headerName.equals(SERVER) ) {
                server = request.getHeader( headerName );
                found2 = true;
            } else if ( headerName.equals( "port" ) ) {
                port = request.getHeader( headerName );
                found3 = true;
            } else if ( headerName.equals( "servletpath" ) ) {
                path = request.getHeader( headerName ).replace( '_', '/' );
                found4 = true;
            }
        }

        if ( ( found1 ) && ( found2 ) && ( found3 ) && ( found4 ) ) {
            String expectedResult = prefix + "://" + server + ":" + port + path;

            // handle cases where server runs on port 80
            String expectedResult2 = prefix + "://" + server + path;
                

            StringBuffer sresult = request.getRequestURL();

            if ( sresult.length() > 0 ) {

                String result = sresult.toString();

                if ( result.equals( expectedResult ) || result.equals( expectedResult2 ) ) {
                    out.println( "HttpServletRequestWrapperGetRequestURLTest test PASSED<BR>" );
                } else {
                    out.println( "HttpServletRequestWrapperGetRequestURLTest test FAILED <BR>" );
                    out.println( "	HttpServletRequestWrapper.getRequestURL() returned an incorrect result <BR>" );
                    out.println( "	Expected value ->|" + expectedResult + "|<BR>" );
                    if ( port.equals( "80" ) ) {
                        out.println( " OR Expected value ->|" + expectedResult2 + "|<BR>" );
                    }
                    out.println( "	Actual value ->|" + result + "|" );
                }
            } else {
                out.println( "HttpServletRequestWrapperGetRequestURLTest test FAILED <BR>" );
                out.println( "	HttpServletRequestWrapper.getRequestURL() returned an empty StringBuffer<BR>" );
            }
        } else {
            out.println( "HttpServletRequestWrapperGetRequestURLTest test FAILED <BR>" );
            out.println( "	Did not receive all the expected results from the client <BR>" );
            out.println( "   The prefix result= |" + prefix + "| <BR>" );
            out.println( "   The server name result= |" + server + "| <BR>" );
            out.println( "   The port number result= |" + port + "| <BR>" );
            out.println( "   The path result= |" + path + "| <BR>" );
        }

        Enumeration e = sl.readFromLog();

        while ( e.hasMoreElements() ) {
            String tmp = ( String ) e.nextElement();
            out.println( tmp );
        }
    }
}
