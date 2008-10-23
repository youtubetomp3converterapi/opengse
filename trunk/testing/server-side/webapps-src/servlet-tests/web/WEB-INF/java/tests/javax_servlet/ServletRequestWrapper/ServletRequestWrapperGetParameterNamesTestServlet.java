/*
 * $Header: /home/cvs/jakarta-watchdog-4.0/src/server/servlet-tests/WEB-INF/classes/tests/javax_servlet/ServletRequestWrapper/ServletRequestWrapperGetParameterNamesTestServlet.java,v 1.1 2002/01/11 22:20:59 rlubke Exp $ 
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

public class ServletRequestWrapperGetParameterNamesTestServlet extends GenericServlet {

    StaticLog sl = new StaticLog();

    public void service ( ServletRequest request, ServletResponse response ) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        sl.resetLog();

        Enumeration en = request.getParameterNames();

        int count = 0;
        int expectedCount = 2;
        String expectedResult1 = "BestLanguage";
        String expectedResult2 = "BestJSP";
        boolean expectedResult1Found = false;
        boolean expectedResult2Found = false;

        if ( en.hasMoreElements() ) {
            Vector v = new Vector();

            while ( en.hasMoreElements() ) {
                String result = ( String ) en.nextElement();

                if ( result.equals( expectedResult1 ) ) {
                    if ( !expectedResult1Found ) {
                        count++;
                        expectedResult1Found = true;
                    } else {
                        out.println( "ServletRequestWrapperGetParameterNamesTest test FAILED<BR>" );
                        out.println( "    ServletRequestWrapper.getParameterNames() return the same name twice <BR>" );
                        out.println( "    The name already received was " + expectedResult1 + " <BR>" );
                    }
                }

                if ( result.equals( expectedResult2 ) ) {
                    if ( !expectedResult2Found ) {
                        count++;
                        expectedResult2Found = true;
                    } else {
                        out.println( "ServletRequestWrapperGetParameterNamesTest test FAILED<BR>" );
                        out.println( "    ServletRequestWrapper.getParameterNames() return the same name twice <BR>" );
                        out.println( "    The name already received was " + expectedResult2 + " <BR>" );
                    }
                }

            }

            if ( count != expectedCount ) {
                out.println( "ServletRequestWrapperGetParameterNamesTest test FAILED <BR>" );
                out.println( "    ServletRequestWrapper.getParameterNames() did not return the proper number of parameter names<BR>" );
                out.println( "    Expected count = " + expectedCount + " <BR>" );
                out.println( "    Actual count = |" + count + "|<BR>" );
                out.println( "    The expected parameter names received were :<BR>" );

                if ( expectedResult1Found ) {
                    out.println( expectedResult1 + "<BR>" );
                }

                if ( expectedResult2Found ) {
                    out.println( expectedResult2 + "<BR>" );
                }

                out.println( "    Other parameter names received were :<BR>" );

                for ( int i = 0;i <= v.size() - 1;i++ ) {
                    out.println( "     " + v.elementAt( i ).toString() + "<BR>" );
                }
            } else {
                out.println( "ServletRequestWrapperGetParameterNamesTest test PASSED<BR>" );
            }
        } else {
            out.println( "ServletRequestWrapperGetParameterNamesTest test FAILED <BR>" );
            out.println( "    ServletRequestWrapper.getParameterNames() returned an empty emuneration<BR>" );
        }

        Enumeration e = sl.readFromLog();

        while ( e.hasMoreElements() ) {
            String tmp = ( String ) e.nextElement();
            out.println( tmp );
        }
    }
}
