/*
 * $Header: /home/cvs/jakarta-watchdog-4.0/src/server/servlet-tests/WEB-INF/classes/tests/javax_servlet_http/HttpServletRequestWrapper/GenericRequestWrapper.java,v 1.1 2002/01/11 22:29:16 rlubke Exp $ 
 * $Revision: 1.1 $
 * $Date: 2002/01/11 22:29:16 $
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

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.security.Principal;
import java.util.Enumeration;
import common.util.StaticLog;

public class GenericRequestWrapper extends HttpServletRequestWrapper {
    StaticLog sl = new StaticLog();
    public GenericRequestWrapper( HttpServletRequest request ) {
        super( request );
    }

    public String getAuthType() {
        sl.writeToLog( "GenericRequestWrapper:getAuthType<BR>" );
        return ( super.getAuthType() );
    }

    public String getContextPath() {
        sl.writeToLog( "GenericRequestWrapper:getContextPath<BR>" );
        return ( super.getContextPath() );
    }

    public Cookie[] getCookies() {
        sl.writeToLog( "GenericRequestWrapper:getCookies<BR>" );
        return ( super.getCookies() );
    }

    public long getDateHeader( String s ) {
        sl.writeToLog( "GenericRequestWrapper:getDateHeader<BR>" );
        return ( super.getDateHeader( s ) );
    }

    public String getHeader( String s ) {
        sl.writeToLog( "GenericRequestWrapper:getHeader<BR>" );
        return ( super.getHeader( s ) );
    }

    public Enumeration getHeaders( String s ) {
        sl.writeToLog( "GenericRequestWrapper:getHeaders<BR>" );
        return ( super.getHeaders( s ) );
    }

    public Enumeration getHeaderNames() {
        sl.writeToLog( "GenericRequestWrapper:getHeaderNames<BR>" );
        return ( super.getHeaderNames() );
    }

    public int getIntHeader( String s ) {
        sl.writeToLog( "GenericRequestWrapper:getIntHeader<BR>" );
        return ( super.getIntHeader( s ) );
    }

    public String getMethod() {
        sl.writeToLog( "GenericRequestWrapper:getMethod<BR>" );
        return ( super.getMethod() );
    }

    public String getPathInfo() {
        sl.writeToLog( "GenericRequestWrapper:getPathInfo<BR>" );
        return ( super.getPathInfo() );
    }

    public String getPathTranslated() {
        sl.writeToLog( "GenericRequestWrapper:getPathTranslated<BR>" );
        return ( super.getPathTranslated() );
    }

    public String getQueryString() {
        sl.writeToLog( "GenericRequestWrapper:getQueryString<BR>" );
        return ( super.getQueryString() );
    }

    public String getRemoteUser() {
        sl.writeToLog( "GenericRequestWrapper:getRemoteUser<BR>" );
        return ( super.getRemoteUser() );
    }

    public String getRequestedSessionId() {
        sl.writeToLog( "GenericRequestWrapper:getRequestedSessionId<BR>" );
        return ( super.getRequestedSessionId() );
    }

    public String getRequestURI() {
        sl.writeToLog( "GenericRequestWrapper:getRequestURI<BR>" );
        return ( super.getRequestURI() );
    }

    public StringBuffer getRequestURL() {
        sl.writeToLog( "GenericRequestWrapper:getRequestURL<BR>" );
        return ( super.getRequestURL() );
    }

    public String getServletPath() {
        sl.writeToLog( "GenericRequestWrapper:getServletPath<BR>" );
        return ( super.getServletPath() );
    }

    public HttpSession getSession() {
        sl.writeToLog( "GenericRequestWrapper:getSession<BR>" );
        return ( super.getSession() );
    }

    public HttpSession getSession( boolean b ) {
        sl.writeToLog( "GenericRequestWrapper:getSession(boolean)<BR>" );
        return ( super.getSession( b ) );
    }

    public Principal getUserPrincipal() {
        sl.writeToLog( "GenericRequestWrapper:getUserPrincipal<BR>" );
        return ( super.getUserPrincipal() );
    }

    public boolean isRequestedSessionIdFromCookie() {
        sl.writeToLog( "GenericRequestWrapper:isRequestedSessionIdFromCookie<BR>" );
        return ( super.isRequestedSessionIdFromCookie() );
    }

    public boolean isRequestedSessionIdFromURL() {
        sl.writeToLog( "GenericRequestWrapper:isRequestedSessionIdFromURL<BR>" );
        return ( super.isRequestedSessionIdFromURL() );
    }

    public boolean isRequestedSessionIdValid() {
        sl.writeToLog( "GenericRequestWrapper:isRequestedSessionIdValid<BR>" );
        return ( super.isRequestedSessionIdValid() );
    }

    public boolean isUserInRole( String s ) {
        sl.writeToLog( "GenericRequestWrapper:isUserInRole<BR>" );
        return ( super.isUserInRole( s ) );
    }
}
