/*
 * $Header: /home/cvs/jakarta-watchdog-4.0/src/server/servlet-tests/WEB-INF/classes/tests/javax_servlet/ServletRequestWrapper/ServletRequestWrapperGetParameterValuesTestServlet.java,v 1.1 2002/01/11 22:20:59 rlubke Exp $ 
 * $Revision: 1.1 $
 * $Date: 2002/01/11 22:20:59 $
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

package tests.javax_servlet.ServletRequestWrapper;

import common.util.StaticLog;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import java.util.Enumeration;
import java.util.Vector;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

public class ServletRequestWrapperGetParameterValuesTestServlet extends GenericServlet {

    StaticLog sl = new StaticLog();

    public void service ( ServletRequest request, ServletResponse response ) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        sl.resetLog();

        String[] en = request.getParameterValues( "Containers" );
        int enumlength = en.length;
        String expectedResult1 = "JSP";
        String expectedResult2 = "Servlet";
        boolean expectedResult1Found = false;
        boolean expectedResult2Found = false;
        int expectedCount = 2;
        int count = 0;
        int arraycount = 0;

        if ( enumlength > 0 ) {
            Vector v = new Vector();

            while ( arraycount < enumlength ) {
                String result = en[ arraycount++ ];

                if ( result.equals( expectedResult1 ) ) {
                    if ( !expectedResult1Found ) {
                        count++;
                        expectedResult1Found = true;
                    } else {
                        out.println( "ServletRequestWrapperGetParameterValuesTest test FAILED<BR>" );
                        out.println( "    ServletRequestWrapper.getParameterValues() return the same value twice <BR>" );
                        out.println( "    The value already received was " + expectedResult1 + " <BR>" );
                    }
                }

                if ( result.equals( expectedResult2 ) ) {
                    if ( !expectedResult2Found ) {
                        count++;
                        expectedResult2Found = true;
                    } else {
                        out.println( "ServletRequestWrapperGetParameterValuesTest test FAILED<BR>" );
                        out.println( "    ServletRequestWrapper.getParameterValues() return the same value twice <BR>" );
                        out.println( "    The value already received was " + expectedResult2 + " <BR>" );
                    }
                } else {
                    v.add( result );
                }
            }

            if ( count != expectedCount ) {
                out.println( "ServletRequestWrapperGetParameterValuesTest test FAILED <BR>" );
                out.println( "    ServletRequestWrapper.getParameterValues() did not return the proper number of parameter values<BR>" );
                out.println( "    Expected count = " + expectedCount + " <BR>" );
                out.println( "    Actual count = |" + count + "|<BR>" );
                out.println( "    The expected parameter values received were :<BR>" );

                if ( expectedResult1Found ) {
                    out.println( expectedResult1 + "<BR>" );
                }

                if ( expectedResult2Found ) {
                    out.println( expectedResult2 + "<BR>" );
                }

                out.println( "    Other parameter values received were :<BR>" );

                for ( int i = 0;i <= v.size() - 1;i++ ) {
                    out.println( "     " + v.elementAt( i ).toString() + "<BR>" );
                }
            } else {
                out.println( "ServletRequestWrapperGetParameterValuesTest test PASSED<BR>" );
            }

            Enumeration e = sl.readFromLog();

            while ( e.hasMoreElements() ) {
                String tmp = ( String ) e.nextElement();
                out.println( tmp );
            }

        } else {
            out.println( "ServletRequestWrapperGetParameterValuesTest test FAILED<BR>" );
            out.println( "    ServletRequestWrapper.getParameterValues() returned an empty array<BR>" );
        }
    }
}
