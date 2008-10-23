/*
 * $Header: /home/cvs/jakarta-watchdog-4.0/src/server/jsp-tests/WEB-INF/classes/tags/Count.java,v 1.2 2002/01/28 23:35:16 rlubke Exp $ 
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
import java.util.*;

/**
 * This is a tag handler that will validate that the JSP compiler
 * is invoking the methods in the proper order
 *
 * The validity checking seems most easily described using a simple check on
 * what was the previously called method.
 *
 * This tag takes several optional attributes:
 *   att1, att2 do nothing
 *   includeBody - whether the body is to be included or not ("true" is default)
 *   skipPage - whether to skip the rest of the page ("false" is default)
 */
public class Count extends TagSupport {

    public static String CREATED = "Created";
    public static String SET_PC = "Set_pc";
    public static String SET_PARENT = "Set_parent";
    public static String SET_ATT_1 = "Set_att_1";
    public static String SET_ATT_2 = "Set_att_2";
    public static String SET_INCLUDE_BODY = "Set_include_body";
    public static String SET_SKIP_PAGE = "Set_skip_page";
    public static String DO_START_TAG = "Do_start_tag";
    public static String DO_END_TAG = "Do_end_tag";
    public static String RELEASE = "Release";

    public static String DO_START_TAG_COUNTER = "Do_start_tag_counter";
    public static String DO_END_TAG_COUNTER = "Do_end_tag_counter";

    // setpageContext
    public void setPageContext( PageContext pc ) {
        if ( ( state == DO_START_TAG ) ) {
            throw new Error( "Cannot setPageContext when in: " + state );
        }

        state = SET_PC;

        if ( pc == null ) {
            throw new Error( "Cannot set a null PageContext" );
        }

        super.setPageContext( pc );
    }

    // setParent
    public void setParent( Tag parent ) {
        if ( ( state == DO_START_TAG ) ||
                ( state == CREATED ) ||
                ( state == RELEASE ) ) {
            throw new Error( "Cannot setParent when in: " + state );
        }

        state = SET_PARENT;
        super.setParent( parent );
    }

    // getParent
    public Tag getParent() {
        return super.getParent();
    }

    //  att1
    public void setAtt1( String s ) {
        if ( ( state == SET_PARENT ) ||
                ( aSetter( state ) ) ||
            ( state == DO_END_TAG ) ) {}
        else {
            throw new Error( "Cannot setAtt1 when in: " + state );
        }

        state = SET_ATT_1;
        att1 = s;
    }

    public String getAtt1() {
        return att1;
    }

    //  att2
    public void setAtt2( String s ) {
        if ( ( state == SET_PARENT ) ||
                ( aSetter( state ) ) ||
            ( state == DO_END_TAG ) ) {}
        else {
            throw new Error( "Cannot setAtt1 when in: " + state );
        }

        state = SET_ATT_1;
        att1 = s;
    }

    public String getAtt2() {
        return att2;
    }

    //  includeBody
    public void setIncludeBody( String s ) {
        if ( ( state == SET_PARENT ) ||
                ( aSetter( state ) ) ||
            ( state == DO_END_TAG ) ) {}
        else {
            throw new Error( "Cannot setIncludeBody when in: " + state );
        }

        state = SET_INCLUDE_BODY;
        includeBody = s;
    }

    public String getIncludeBody() {
        return includeBody;
    }

    //  skipPage
    public void setSkipPage( String s ) {
        if ( ( state == SET_PARENT ) ||
                ( aSetter( state ) ) ||
            ( state == DO_END_TAG ) ) {}
        else {
            throw new Error( "Cannot setSkipPage when in: " + state );
        }

        state = SET_SKIP_PAGE;
        skipPage = s;
    }

    public String getSkipPage() {
        return skipPage;
    }

    // ================== Actions ====================

    // doStartTag
    public int doStartTag() {
        if ( ( aSetter( state ) ) ||
                ( state == SET_PARENT ) ||
            ( state == DO_END_TAG ) ) {}
        else {
            throw new Error( "Cannot doStartTag when in: " + state );
        }

        state = DO_START_TAG;
        addCount( pageContext, DO_START_TAG_COUNTER );

        if ( includeBody.equals( "true" ) ) {
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }

    // doEndTag
    public int doEndTag() {
        if ( ( state != DO_START_TAG ) ) {
            throw new Error( "Cannot doEndTag when in: " + state );
        }

        state = DO_END_TAG;
        addCount( pageContext, DO_END_TAG_COUNTER );

        if ( skipPage.equals( "true" ) ) {
            return SKIP_PAGE;
        } else {
            return EVAL_PAGE;
        }
    }


    // release
    public void release() {
        if ( ( state == DO_START_TAG ) ) {
            throw new Error( "Cannot release when in: " + state );
        }

        state = RELEASE;
        super.release();
    }

    // ========== Public methods for counting ============

    public static void addCount( PageContext pc, String s ) {
        synchronized ( pc ) {
            Integer c = ( Integer ) pc.getAttribute( s );

            if ( c == null ) {
                pc.setAttribute( s, new Integer( 1 ),
                                 PageContext.PAGE_SCOPE );
            } else {
                int i = c.intValue();
                pc.setAttribute( s, new Integer( i + 1 ),
                                 PageContext.PAGE_SCOPE );
            }
        }
    }

    public static int getCount( PageContext pc, String s ) {
        synchronized ( pc ) {
            Integer c = ( Integer ) pc.getAttribute( s );

            if ( c == null ) {
                return 0;
            } else {
                return c.intValue();
            }
        }
    }


    //  Auxiliary methods
    protected boolean aSetter( String state ) {
        if ( ( state == SET_INCLUDE_BODY ) ||
                ( state == SET_SKIP_PAGE ) ||
                ( state == SET_ATT_1 ) ||
                ( state == SET_ATT_2 ) ) {
            return true;
        } else {
            return false;
        }
    }

    // private data

    private String state = CREATED;
    private String att1 = null;
    private String att2 = null;
    private String includeBody = "true";
    private String skipPage = "false";
}
