/*
 * $Header: /home/cvs/jakarta-watchdog-4.0/src/server/jsp-tests/WEB-INF/classes/tags/BTag.java,v 1.4 2002/01/28 23:35:16 rlubke Exp $ 
 * $Revision: 1.4 $
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
 * A Tag Handler written from scratch without extending TagSupport
 * Takes one attribute: att
 */
public class BTag implements BodyTag {

    // Property Attributes
    public void setAtt( String s ) {
        att = s;
    }

    public String getAtt() {
        return att;
    }

    // Setters for standard tag handler attributes
    public void setPageContext( PageContext pc ) {
        this.pc = pc;
    }

    public void setParent( Tag p ) {
        this.parent = p;
    }

    public Tag getParent() {
        return parent;
    }

    // The action methods
    public int doStartTag() {
        //return EVAL_BODY_TAG;
        return EVAL_BODY_BUFFERED;
    }

    public void setBodyContent( BodyContent b ) {
        BodyContent body = b;
    }

    public void doInitBody() {}

    public int doAfterBody() {
        try {
            body.writeOut( pc.getOut() );
            body.clearBody();
        } catch ( Exception ex ) {
            // ignore
        }

        return SKIP_BODY;
    }

    public int doEndTag() {
        return EVAL_PAGE;
    }

    public void release() {}


    // ======

    private String att = null;
    private PageContext pc = null;
    private Tag parent = null;
    private BodyContent body = null;
}
