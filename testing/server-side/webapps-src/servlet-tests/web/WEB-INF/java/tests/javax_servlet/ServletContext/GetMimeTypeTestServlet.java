/*
 * $Header: /home/cvs/jakarta-watchdog-4.0/src/server/servlet-tests/WEB-INF/classes/tests/javax_servlet/ServletContext/GetMimeTypeTestServlet.java,v 1.3 2002/01/11 22:20:55 rlubke Exp $ 
 * $Revision: 1.3 $
 * $Date: 2002/01/11 22:20:55 $
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

package tests.javax_servlet.ServletContext;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

public class GetMimeTypeTestServlet extends GenericServlet {

    private static final String EXPECTED_MIME_TYPE = "application/x-java-class";
    private static final String MIME_OBJECT =
        "/WEB-INF/classes/tests/javax_servlet/ServletContext/GetMimeTypeTestServlet.class";

    public void service ( ServletRequest request, ServletResponse response )
    throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        ServletConfig config = this.getServletConfig();

        ServletContext context = config.getServletContext();
        String result = context.getMimeType( MIME_OBJECT );

        if ( result != null ) {
            if ( result.equals( EXPECTED_MIME_TYPE ) ) {
                out.println( "GetMimeTypeTest test PASSED" );
            } else {
                out.println( "GetMimeTypeTest test FAILED<BR>\n\t" +
                             "ServletContext.getMimeType(" + MIME_OBJECT + ") returned incorrect result<BR>\n\t" +
                             "Expected result = " + EXPECTED_MIME_TYPE + "<BR>\n\t" +
                             "Actual result = " + result + "<BR>" );
            }
        } else {
            out.println( "GetMimeTypeTest test FAILED<BR>\n\t" +
                         "ServletContext.getMimeType( " + MIME_OBJECT + " ) returned a null result<BR>\n\t" +
                         "Expected result = " + EXPECTED_MIME_TYPE + "<BR>" );
        }
    }
}
