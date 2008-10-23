/*
 * $Header: /home/cvs/jakarta-watchdog-4.0/src/server/servlet-tests/WEB-INF/classes/tests/javax_servlet/ServletResponseWrapper/GenericResponseWrapper.java,v 1.1 2002/01/11 22:21:00 rlubke Exp $ 
 * $Revision: 1.1 $
 * $Date: 2002/01/11 22:21:00 $
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

package tests.javax_servlet.ServletResponseWrapper;

import javax.servlet.*;
import java.io.*;
import java.util.Locale;
import common.util.StaticLog;

public class GenericResponseWrapper extends ServletResponseWrapper {
    StaticLog sl = new StaticLog();

    public GenericResponseWrapper( ServletResponse response ) {
        super( response );
    }

    public void flushBuffer() throws IOException {
        super.flushBuffer();
        sl.writeToLog( "GenericResponseWrapper: flushBuffer<BR>" );
    }

    public int getBufferSize() {
        sl.writeToLog( "GenericResponseWrapper: getBufferSize<BR>" );
        return ( super.getBufferSize() );
    }

    public String getCharacterEncoding() {
        sl.writeToLog( "GenericResponseWrapper: getCharacterEncoding<BR>" );
        return ( super.getCharacterEncoding() );
    }

    public Locale getLocale() {
        sl.writeToLog( "GenericResponseWrapper: getLocale<BR>" );
        return ( super.getLocale() );
    }

    public ServletOutputStream getOutputStream() throws IOException {
        sl.writeToLog( "GenericResponseWrapper: getOutputStream<BR>" );
        return ( super.getOutputStream() );
    }

    public ServletResponse getResponse() {
        sl.writeToLog( "GenericResponseWrapper: getResponse<BR>" );
        return ( super.getResponse() );
    }

    public PrintWriter getWriter() throws IOException {
        sl.writeToLog( "GenericResponseWrapper: getWriter<BR>" );
        return ( super.getWriter() );
    }

    public boolean isCommitted() {
        sl.writeToLog( "GenericResponseWrapper: isCommitted<BR>" );
        return ( super.isCommitted() );
    }

    public void reset() {
        sl.writeToLog( "GenericResponseWrapper: reset<BR>" );
        super.reset();
    }

    public void resetBuffer() {
        sl.writeToLog( "GenericResponseWrapper: resetBuffer<BR>" );
        super.resetBuffer();
    }

    public void setBufferSize( int i ) {
        sl.writeToLog( "GenericResponseWrapper: setBufferSize<BR>" );
        super.setBufferSize( i );
    }

    public void setContentLength( int i ) {
        sl.writeToLog( "GenericResponseWrapper: setContentLength<BR>" );
        super.setContentLength( i );
    }

    public void setContentType( String s ) {
        sl.writeToLog( "GenericResponseWrapper: setContentType<BR>" );
        super.setContentType( s );
    }

    public void setLocale( Locale l ) {
        sl.writeToLog( "GenericResponseWrapper: setLocale<BR>" );
        super.setLocale( l );
    }

    public void setResponse( ServletResponse s ) {
        sl.writeToLog( "GenericResponseWrapper: setResponse<BR>" );
        super.setResponse( s );
    }
}
