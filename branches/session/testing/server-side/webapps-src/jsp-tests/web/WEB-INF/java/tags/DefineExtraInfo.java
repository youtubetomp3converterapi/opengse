/*
 * $Header: /home/cvs/jakarta-watchdog-4.0/src/server/jsp-tests/WEB-INF/classes/tags/DefineExtraInfo.java,v 1.2 2002/01/28 23:35:16 rlubke Exp $ 
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

public class DefineExtraInfo extends TagExtraInfo {

    public boolean isValid(TagData data) {
	if (data == null) {
	    return false;
	}
	if ((data.getAttribute("startValue") != null) &&
	    (data.getAttributeString("life").equals("at_end"))) {
	    return false;
	}
	if ((data.getAttribute("endValue") != null) &&
	    (data.getAttributeString("life").equals("nested"))) {
	    return false;
	}
	if ((data.getAttribute("initBodyValue") != null) &&
	    (data.getAttributeString("life").equals("at_end"))) {
	    return false;
	}
	if ((data.getAttribute("afterBodyIncrement") != null) &&
	    (data.getAttributeString("life").equals("at_end"))) {
	    return false;
	}
	return true;
    }

    public VariableInfo[] getVariableInfo(TagData data) {
	String id = data.getId();
	if (id == null) {
	    throw new Error("should not happen...");
	}
	String life = data.getAttributeString("life");
	String scope = data.getAttributeString("scope");
	String declare = data.getAttributeString("declare");
	if (declare == null) {
	    declare = "true";
	}

	VariableInfo back[] = {
	    new VariableInfo(id,
			     "java.lang.Integer",
			     declare.equals("true"), lifeInt(life))};
	return back;
    }

    // private fields

    private int lifeInt(String s) {
        if (s.equals("nested")) {
            return VariableInfo.NESTED;
        } else if (s.equals("at_begin")) {
            return VariableInfo.AT_BEGIN;
        } else if (s.equals("at_end")) {
            return VariableInfo.AT_END;
	}
	return -1;
    }
}
