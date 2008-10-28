/*
 * $Header: /home/cvs/jakarta-watchdog-4.0/src/server/jsp-tests/WEB-INF/classes/tags/CheckCount.java,v 1.2 2002/01/28 23:35:16 rlubke Exp $ 
 * $Revision: 1.2 $
 * $Date: 2002/01/28 23:35:16 $
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

package tags;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/**
 * Check different types of counts related to the count tag.
 *
 * This tag takes several optional attributes:
 *   nexted - then number of enclosing count tags that should be active
 *               note that we need to assume the name of the class...  This should really
 *               be a parameter in TestLib.tld
 */
public class CheckCount extends TagSupport {

    //  nested
    public void setNested( String s ) {
        try {
            nestedCount = Integer.valueOf( s );
        } catch ( Exception ex ) {
            nestedCount = null;
        }
    }

    // startCount
    public void setStart( String s ) {
        try {
            startCount = Integer.valueOf( s );
        } catch ( Exception ex ) {
            startCount = null;
        }
    }

    // endCount
    public void setEnd( String s ) {
        try {
            endCount = Integer.valueOf( s );
        } catch ( Exception ex ) {
            endCount = null;
        }
    }


    // ================== Actions ====================

    // doStartTag
    public int doStartTag() {
        if ( nestedCount != null ) {
            // check for active nested tags.

            int k = nestedCount.intValue();
            int c = 0;
            Count t = ( Count ) TagSupport.findAncestorWithClass( this, tags.Count.class );

            while ( t != null ) {
                c += 1;
                t = ( Count ) TagSupport.findAncestorWithClass( t, tags.Count.class );
            }

            if ( c != k ) {
                throw new Error( "nested count: expecting " + k + " got " + c );
            }
        }

        if ( startCount != null ) {
            // check for start tag invocations
            int k = startCount.intValue();

            int c = Count.getCount( pageContext, Count.DO_START_TAG_COUNTER );

            if ( k != c ) {
                throw new Error( "start count: expecting " + k + " got " + c );
            }
        }

        if ( endCount != null ) {
            // check for end tag invocations
            int k = endCount.intValue();

            int c = Count.getCount( pageContext, Count.DO_END_TAG_COUNTER );

            if ( k != c ) {
                throw new Error( "end count: expecting " + k + " got " + c );
            }
        }
        return SKIP_BODY;
    }


    // private data

    private Integer nestedCount = null;
    private Integer startCount = null;
    private Integer endCount = null;
}
