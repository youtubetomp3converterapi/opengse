/*
 * $Header: /home/cvs/jakarta-watchdog-4.0/src/server/servlet-tests/WEB-INF/classes/tests/javax_servlet_http/HttpServletResponseWrapper/GenericResponseWrapper.java,v 1.2 2002/01/11 22:29:18 rlubke Exp $ 
 * $Revision: 1.2 $
 * $Date: 2002/01/11 22:29:18 $
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

package tests.javax_servlet_http.HttpServletResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class GenericResponseWrapper extends HttpServletResponseWrapper {

    public GenericResponseWrapper( HttpServletResponse response ) {
        super( response );
    }

    public void addDateHeader( String s, long l ) {
        super.addDateHeader( s, l );
        super.addHeader( "GenericResponseWrapper", "addDateHeader" );
    }

    public void addCookie( Cookie c ) {
        super.addCookie( c );
        super.addHeader( "GenericResponseWrapper", "addCookie" );
    }

    public void addHeader( String n, String v ) {
        super.addHeader( n, v );
        super.addHeader( "GenericResponseWrapper", "addHeader" );
    }

    public void addIntHeader( String n, int v ) {
        super.addIntHeader( n, v );
        super.addHeader( "GenericResponseWrapper", "addIntHeader" );
    }

    public boolean containsHeader( String n ) {
        super.addHeader( "GenericResponseWrapper", "containsHeader" );
        return ( super.containsHeader( n ) );
    }

    public String encodeURL( String u ) {
        super.addHeader( "GenericResponseWrapper", "encodeURL" );
        return ( super.encodeURL( u ) );
    }

    public String encodeRedirectURL( String u ) {
        super.addHeader( "GenericResponseWrapper", "encodeRedirectURL" );
        return ( super.encodeRedirectURL( u ) );
    }

    public void sendError( int v ) throws IOException {
        try {
            super.addHeader( "GenericResponseWrapper", "sendError" );
            super.sendError( v );
            /*
             * Added to ensure container implementations
             * ignore headers added after a call to sendError()
             */
            super.addHeader( "GenericResponseWrapper", "sendErrorIgnoreHeader" );
        } catch ( IOException ioe ) {
            throw new IOException();
        }
    }

    public void sendError( int v, String m ) throws IOException {
        try {
            super.addHeader( "GenericResponseWrapper", "sendErrorMsg" );
            super.sendError( v, m );
            /*
             * Added to ensure container implementations
             * ignore headers added after a call to sendError()
             */
            super.addHeader( "GenericResponseWrapper", "sendErrorMsgIgnoreHeader" );
        } catch ( IOException ioe ) {
            throw new IOException();
        }
    }

    public void sendRedirect( String l ) throws IOException {
        try {
            super.addHeader( "GenericResponseWrapper", "sendRedirect" );
            super.sendRedirect( l );
            /*
             * Added to ensure container implementations
             * ignore headers added after a call to sendRedirect()
             */
            super.addHeader( "GenericResponseWrapper", "sendRedirectIgnoreHeader" );
        } catch ( IOException ioe ) {
            throw new IOException();
        }
    }

    public void setDateHeader( String n, long v ) {
        super.setDateHeader( n, v );
        super.addHeader( "GenericResponseWrapper", "setDateHeader" );
    }

    public void setHeader( String n, String v ) {
        super.setHeader( n, v );
        super.addHeader( "GenericResponseWrapper", "setHeader" );
    }

    public void setIntHeader( String n, int v ) {
        super.setIntHeader( n, v );
        super.addHeader( "GenericResponseWrapper", "setIntHeader" );
    }

    public void setStatus( int v, String m ) {
        super.setStatus( v, m );
        super.addHeader( "GenericResponseWrapper", "setStatusMsg" );
    }

    public void setStatus( int v ) {
        super.setStatus( v );
        super.addHeader( "GenericResponseWrapper", "setStatus" );
    }
}
