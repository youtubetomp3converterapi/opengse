/*
 * $Header: /home/cvs/jakarta-watchdog-4.0/src/server/servlet-tests/WEB-INF/classes/tests/javax_servlet/ServletRequestWrapper/GenericRequestWrapper.java,v 1.1 2002/01/11 22:20:58 rlubke Exp $ 
 * $Revision: 1.1 $
 * $Date: 2002/01/11 22:20:58 $
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

import javax.servlet.*;
import java.io.*;
import java.security.Principal;
import java.util.*;
import common.util.StaticLog;

public class GenericRequestWrapper extends ServletRequestWrapper {
    StaticLog sl = new StaticLog();
    public GenericRequestWrapper( ServletRequest request ) {
        super( request );
    }

    public Object getAttribute( String s ) {
        sl.writeToLog( "GenericRequestWrapper:getAttribute<BR>" );
        return ( super.getAttribute( s ) );
    }

    public Enumeration getAttributeNames() {
        sl.writeToLog( "GenericRequestWrapper:getAttributeNames<BR>" );
        return ( super.getAttributeNames() );
    }

    public String getCharacterEncoding() {
        sl.writeToLog( "GenericRequestWrapper:getCharacterEncoding<BR>" );
        return ( super.getCharacterEncoding() );
    }

    public int getContentLength() {
        sl.writeToLog( "GenericRequestWrapper:getContentLength<BR>" );
        return ( super.getContentLength() );
    }

    public String getContentType() {
        sl.writeToLog( "GenericRequestWrapper:getContentType<BR>" );
        return ( super.getContentType() );
    }

    public ServletInputStream getInputStream() throws IOException {
        sl.writeToLog( "GenericRequestWrapper:getInputStream<BR>" );
        return ( super.getInputStream() );
    }

    public Locale getLocale() {
        sl.writeToLog( "GenericRequestWrapper:getLocale<BR>" );
        return ( super.getLocale() );
    }

    public Enumeration getLocales() {
        sl.writeToLog( "GenericRequestWrapper:getLocales<BR>" );
        return ( super.getLocales() );
    }

    public String getParameter( String s ) {
        sl.writeToLog( "GenericRequestWrapper:getParameter<BR>" );
        return ( super.getParameter( s ) );
    }

    public Map getParameterMap() {
        sl.writeToLog( "GenericRequestWrapper:getParameterMap<BR>" );
        return ( super.getParameterMap() );
    }

    public Enumeration getParameterNames() {
        sl.writeToLog( "GenericRequestWrapper:getParameterNames<BR>" );
        return ( super.getParameterNames() );
    }

    public String[] getParameterValues( String s ) {
        sl.writeToLog( "GenericRequestWrapper:getParameterValues<BR>" );
        return ( super.getParameterValues( s ) );
    }

    public String getProtocol() {
        sl.writeToLog( "GenericRequestWrapper:getProtocol<BR>" );
        return ( super.getProtocol() );
    }

    public BufferedReader getReader() throws IOException {
        sl.writeToLog( "GenericRequestWrapper:getReader<BR>" );
        return ( super.getReader() );
    }

    public String getRealPath( String p ) {
        sl.writeToLog( "GenericRequestWrapper:getRealPath<BR>" );
        return ( super.getRealPath( p ) );
    }

    public String getRemoteAddr() {
        sl.writeToLog( "GenericRequestWrapper:getRemoteAddr<BR>" );
        return ( super.getRemoteAddr() );
    }

    public String getRemoteHost() {
        sl.writeToLog( "GenericRequestWrapper:getRemoteHost<BR>" );
        return ( super.getRemoteHost() );
    }

    public ServletRequest getRequest() {
        sl.writeToLog( "GenericRequestWrapper:getRequest<BR>" );
        return ( super.getRequest() );
    }

    public RequestDispatcher getRequestDispatcher( String s ) {
        sl.writeToLog( "GenericRequestWrapper:getRequestDispatcher<BR>" );
        return ( super.getRequestDispatcher( s ) );
    }

    public String getScheme() {
        sl.writeToLog( "GenericRequestWrapper:getScheme<BR>" );
        return ( super.getScheme() );
    }

    public String getServerName() {
        sl.writeToLog( "GenericRequestWrapper:getServerName<BR>" );
        return ( super.getServerName() );
    }

    public int getServerPort() {
        sl.writeToLog( "GenericRequestWrapper:getServerPort<BR>" );
        return ( super.getServerPort() );
    }

    public boolean isSecure() {
        sl.writeToLog( "GenericRequestWrapper:isSecure<BR>" );
        return ( super.isSecure() );
    }

    public void removeAttribute( String s ) {
        sl.writeToLog( "GenericRequestWrapper:removeAttribute<BR>" );
        super.removeAttribute( s );
    }

    public void setAttribute( String n, Object o ) {
        sl.writeToLog( "GenericRequestWrapper:setAttribute<BR>" );
        super.setAttribute( n, o );
    }

    public void setCharacterEncoding( String n ) throws UnsupportedEncodingException {
        sl.writeToLog( "GenericRequestWrapper:setCharacterEncoding<BR>" );
        super.setCharacterEncoding( n );
    }

    public void setRequest( ServletRequest r ) {
        sl.writeToLog( "GenericRequestWrapper:setRequest<BR>" );
        super.setRequest( r );
    }
}
