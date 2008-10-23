/*
 * $Header: /home/cvs/jakarta-watchdog-4.0/src/server/servlet-tests/WEB-INF/classes/tests/javax_servlet/SingleThreadModel/SingleModelTestServlet.java,v 1.2 2002/01/11 22:21:01 rlubke Exp $ 
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

package tests.javax_servlet.SingleThreadModel;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *	A test for SingleThreadModel interface.
 *  The SingleThreadModel interface guarantees 
 *  that <b>only</b> a single thread shall execute
 *  a servlet's <code>service</code> method
 *  when such a servlet implements this 
 *  interface.
 *
 */

public class SingleModelTestServlet extends GenericServlet implements SingleThreadModel {

    /**
     * <code>threadCount</code> Indicates the number of concurrent threads.
     */
    private int threadCount = 0;

    /**
     * <code>sb</code> is a common StringBuffer for Exception reporting.
     */
    private StringBuffer sb = null;

    /**
     * Called by the servlet container to allow the servlet to respond to a request.
     *
     * @param <code>ServletRequest</code> The current ServletRequest
     * @param <code>ServletResponse</code> The current ServletResponse
     * @exception ServletException if an error occurs
     * @exception IOException if an error occurs
     */
    public void service ( ServletRequest request, ServletResponse response ) throws ServletException, IOException {
             
        /*
         * The threadCount variable should be at 0 each time service() is invoked
         * by the container.  If a request enters, and it's not zero, fail
         * the test by throwing a ServletException
         */
        try { 
            assertThreadCount( 0 );
            threadCount++;
        } catch ( MultipleThreadException mte ) {
            sb = new StringBuffer( 100 );
            sb.append( "Thread counter was not 0 upon entering the service() method\n" );
            sb.append( "The value found was: " );
            sb.append( threadCount );
            throw new ServletException( sb.toString() );
        } finally {
            sb = null;
        }
        
        /*
         * threadCount has now been incremented, loop
         * for a period of time and assert that threadCount
         * never changes. After the loop completes, decrement
         * threadCount and return.
         */
        try {
            for ( int i = 0; i < 200000; i++ ) {
                assertThreadCount( 1 );
            }
        } catch ( MultipleThreadException mte ) {
            sb = new StringBuffer( 75 );
            sb.append( "Thread count changed during processing!\n" );
            sb.append( "Expected a value of 1, but found: " );
            sb.append( threadCount );
            throw new ServletException( sb.toString() );
        } finally {
            sb = null;
            threadCount--;
        }
    }

    /**
     * <code>assertThreadCount</code> asserts that the instance
     * variable, threadCount, is the same value as that passed
     * into the method.
     *
     * @param val Expected value for threadCount
     * @exception MultipleThreadException if the value of threadCount
     *            is not equal to the value passed.
     */
    private void assertThreadCount( int val ) throws MultipleThreadException {
        if ( threadCount != val ) {
            throw new MultipleThreadException();
        }
    }

    /**
     * <code>MultipleThreadException</code> Indicated multiple threads
     * are currently running within the same instance.
     */
    private class MultipleThreadException extends java.lang.Exception {
        
        /**
         * Creates a new <code>MultipleThreadException</code> instance.
         */
        public MultipleThreadException() {
            super();
        }
    }
}
