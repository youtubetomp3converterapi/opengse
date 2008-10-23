/*
 * $Header: /home/cvs/jakarta-watchdog-4.0/src/server/jsp-tests/WEB-INF/classes/tags/LifeCycleTag.java,v 1.2 2002/01/28 23:35:16 rlubke Exp $ 
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
import java.io.IOException;

/**
 * LifeCycleTag class is used to tests tagext API's 
 */
public class LifeCycleTag extends ExampleTagBase
    implements BodyTag {

    boolean toBrowser = false;
    String TestString = "";
    String endString = " method <br>";
    private String atts1, atts2, atts3, atts4, atts5, atts6;
    int count = 0;

    // setting the value from jsp att1,att1 from JSP test case
    public void setAtt1( String value ) {
        atts1 = value;
    }

    public String getAtt1() {
        return atts1;
    }

    public void setAtt2( String value ) {
        atts2 = value;
    }

    public String getAtt2() {
        return atts2;
    }

    public void setAtt3( String value ) {
        atts3 = value;
    }

    public String getAtt3() {
        return atts3;
    }

    public void setAtt4( String value ) {
        atts4 = value;
    }

    public String getAtt4() {
        return atts4;
    }

    public void setAtt5( String value ) {
        atts5 = value;
    }

    public String getAtt5() {
        return atts5;
    }

    public void setAtt6( String value ) {
        atts6 = value;
    }

    public String getAtt6() {
        return atts6;
    }

    public void setToBrowser( String value ) {
        if ( value == null )
            toBrowser = false;
        else if ( value.equalsIgnoreCase( "true" ) )
            toBrowser = true;
        else
            toBrowser = false;
    }

    // Test for setPageContext in TagSupport class.
    public void setPageContext( PageContext pc ) {
        if ( "pageContext".equalsIgnoreCase ( this.getAtt1() ) ) {

            TestString = TestString + this.getAtt1() + endString;
        }
    }

    // Test for doStartTag method in TagSupport class.
    public int doStartTag() {
        if ( "doStartTag".equalsIgnoreCase ( this.getAtt2() ) ) {
            TestString = TestString + this.getAtt2() + endString;
        } // if condition

        //return EVAL_BODY_TAG;
        return EVAL_BODY_BUFFERED;
    }

    // Test for doInitBody method in BodyTagsupport class
    public void doInitBody() throws JspException {
        if ( "doInitBody".equalsIgnoreCase ( this.getAtt3() ) ) {
            TestString = TestString + this.getAtt3() + endString;
        }
    }

    //Test for doAfterBody method in BodyTagSupport class
    public int doAfterBody() throws JspException {
        try {

            if ( "doAfterBody".equalsIgnoreCase ( this.getAtt4() ) ) {
                TestString = TestString + this.getAtt4() + endString;
            }

            String s = bodyOut.getString();
            System.err.println( s );

            if ( false )
                bodyOut.println( TestString );

            return SKIP_BODY;
        } catch ( IOException ex ) {
            throw new JspException( ex.toString() );
        }
    }

    // Test for doEndTag method in BodyTagSupport class
    public int doEndTag() throws JspException {

        if ( "doEndTag".equalsIgnoreCase ( this.getAtt5() ) ) {
            TestString = TestString + this.getAtt5() + endString;
        }

        try {
            if ( toBrowser ) {
                bodyOut.println( TestString );
                bodyOut.writeOut( bodyOut.getEnclosingWriter() );
            }
        } catch ( IOException ioe ) {}

        return EVAL_PAGE;
    }

    // Test for release method in BodyTagSupport class
    public void release() {}
}
