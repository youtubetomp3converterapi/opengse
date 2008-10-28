/*
 * $Header: /home/cvs/jakarta-watchdog-4.0/src/server/servlet-tests/WEB-INF/classes/tests/javax_servlet/UnavailableException/UnavailableException_Constructor2TestServlet.java,v 1.2 2002/01/11 22:21:01 rlubke Exp $ 
 * $Revision: 1.2 $
 * $Date: 2002/01/11 22:21:01 $
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

package tests.javax_servlet.UnavailableException;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

public class UnavailableException_Constructor2TestServlet extends GenericServlet {

    /*
     * UnavailableException(String mesg,int sec)
     * constructs an UnavailabaleException object for
     * the specified servlet. This constructor reports
     * Temporary Unavailability
     */

    public void service ( ServletRequest request, ServletResponse response ) throws ServletException, IOException {

        /*
         *  Constructing  one and throwing  it.
         *  catching the exception
         */

        PrintWriter out = response.getWriter();
        String expectedResult1 = "Exceptional";
        int expectedResult2 = 20;
        UnavailableException ue = new UnavailableException( expectedResult1, expectedResult2 );

        try {
            throw ue;
        } catch ( Exception e ) {
            if ( e instanceof UnavailableException ) {
                int result2 = ue.getUnavailableSeconds();

                String result1 = e.getMessage();

                if ( result2 == expectedResult2 && result1.equals( expectedResult1 ) ) {
                    out.println( "UnavailableException_Constructor2Test test PASSED" );
                } else {
                    if ( !result1.equals( expectedResult1 ) ) {
                        out.println( "UnavailableException_Constructor2Test test FAILED <BR>" );
                        out.println( "Message from Exception does not contain 'Exceptional' <BR>" );
                    }

                    if ( result2 != expectedResult2 ) {
                        out.println( "UnavailableException_Constructor2Test test FAILED <BR>" );
                        out.println( "UnavailableException.getUnavailableSeconds() returned an incorrect result<BR>" );
                        out.println( "     Expected result = " + expectedResult2 + " <BR>" );
                        out.println( "     Actual result = |" + result2 + "| <BR>" );
                    }
                }
            } else {
                out.println( "UnavailableException_Constructor2Test test FAILED <BR>" );
                out.println( "Exception is not an instance of UnavailableException <BR>" );
            }
        }
    }
}
