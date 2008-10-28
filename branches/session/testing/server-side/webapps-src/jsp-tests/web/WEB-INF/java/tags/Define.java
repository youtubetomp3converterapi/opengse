/*
 * $Header: /home/cvs/jakarta-watchdog-4.0/src/server/jsp-tests/WEB-INF/classes/tags/Define.java,v 1.2 2002/01/28 23:35:16 rlubke Exp $ 
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
 * Define an Integer object and do some iteration stuff
 *
 * Requires the following attributes:
 *  id - the ID
 *  scope - where in the PageContext space will it be
 *  life - what type of lifetime the definition will take
 * Optional attributes are:
 *  declare - whether the id is to be declared, or assumed to be so.
 *  startValue - value after doStartTag (invalid with life=="AT_END")
 *  endValue - value after doEndTag (invalid with life=="NESTED")
 *  initBodyValue - value after doInitTag (invalid with life=="AT_END")
 *  afterBodyIncrement - increment after doAfterTag (invalid with life=="AT_END")
 *  iterationCount - number of iterations to do (defaults to 1)
 */
public class Define extends BodyTagSupport {

    // the scope where to define the object
    public void setScope(String s) {
	if (s.equals("page")) {
	    scope = PageContext.PAGE_SCOPE;
	} else if (s.equals("session")) {
	    scope = PageContext.SESSION_SCOPE;
	} else if (s.equals("request")) {
	    scope = PageContext.REQUEST_SCOPE;
	} else if (s.equals("application")) {
	    scope = PageContext.APPLICATION_SCOPE;
	}
    }

    public String getScope() {
	switch (scope) {
	case PageContext.PAGE_SCOPE:
	    return "page";
	case PageContext.SESSION_SCOPE:
	    return "session";
	case PageContext.REQUEST_SCOPE:
	    return "request";
	case PageContext.APPLICATION_SCOPE:
	    return "application";
	default:
	    return null;
	}
    }

    // Lifetime 

    public void setLife(String s) {
	if (s.equals("nested")) {
	    life = VariableInfo.NESTED;
	} else if (s.equals("at_begin")) {
	    life = VariableInfo.AT_BEGIN;
	} else if (s.equals("at_end")) {
	    life = VariableInfo.AT_END;
	} else {
	    throw new Error("Oops! unexpected syntactic error");
	}
    } 

    public String getLife() {
	switch (life) {
	case VariableInfo.NESTED:
	    return "nested";
	case VariableInfo.AT_BEGIN:
	    return "at_begin";
	case VariableInfo.AT_END:
	    return "at_end";
	}
	return null;
    }

    // ====== Optional attributes =========


    // Whether to declare the scripting variable or not.

    public void setDeclare(String s) {
	declare = new Boolean(s);
    }


    // value after doStartTag (invalid with life=="AT_END")

    public void setStartValue(String v) {
	startValue = Integer.valueOf(v);
    }

    public String getStartValue() {
	return startValue.toString();
    }

    // value after doEndTag (invalid with life=="NESTED")

    public void setEndValue(String v) {
	endValue = Integer.valueOf(v);
    }

    public String getEndValue() {
	return endValue.toString();
    }

    // value after doInitBody (invalid with life=="AT_END")

    public void setInitBodyValue(String v) {
	initBodyValue = Integer.valueOf(v);
    }

    public String getInitBodyValue() {
	return initBodyValue.toString();
    }

    // increment after doAfterTag (invalid with life=="AT_END")

    public void setAfterBodyIncrement(String v) {
	afterBodyIncrement = Integer.parseInt(v);
    }

    // number of iterations to do (defaults to 1)

    public void setIterationCount(String v) {
	iterationCount = Integer.parseInt(v);
    }


    // ================== Actions ====================

    public int doStartTag() {
	if (startValue != null) {
	    pageContext.setAttribute(getId(), startValue, scope);
	}
	currentCount = 0;
System.err.println("finishing doStartTag()");
	return EVAL_BODY_TAG;
    }

    public void doInitBody() {
	if (initBodyValue != null) {
	    pageContext.setAttribute(getId(), initBodyValue, scope);
	}
	currentCount = 0;
System.err.println("finishing doInitBody()");
    }

    public int doAfterBody() throws JspException {
	BodyContent bd = getBodyContent();
	try {
	    bd.writeOut(getPreviousOut());
	    bd.clearBody();
	} catch (IOException ex) {
	    throw new JspTagException("io trouble in tag handler");
	}

	Integer value = (Integer) pageContext.getAttribute(getId());
        int k;
        if (value != null)
            k = value.intValue()+afterBodyIncrement;
        else
            k = afterBodyIncrement;

	pageContext.setAttribute(getId(), new Integer(k), scope);
	currentCount += 1;
	if (currentCount < iterationCount) {
System.err.println("finishing doAfterBody on EVAL_BODY_TAG");
	    return EVAL_BODY_TAG;
	} else {
System.err.println("finishing doAfterBody on SKIP_BODY");
	    return SKIP_BODY;
	}
    }

    public int doEndTag() {
	if (endValue != null) {
	    pageContext.setAttribute(getId(), endValue, scope);
	}
System.err.println("finishing doEndTag");
	return EVAL_PAGE;
    }

    // private data

    protected int scope = PageContext.PAGE_SCOPE;
    protected int life = VariableInfo.AT_BEGIN;
    protected Boolean declare;
    private Integer startValue;
    private Integer endValue;
    private Integer initBodyValue;
    private int afterBodyIncrement;
    protected int iterationCount = 1;
    protected int currentCount = 1;
}
