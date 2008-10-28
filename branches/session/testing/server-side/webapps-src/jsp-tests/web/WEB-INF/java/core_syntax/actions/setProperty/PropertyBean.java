/*
 * $Header: /home/cvs/jakarta-watchdog-4.0/src/server/jsp-tests/WEB-INF/classes/core_syntax/actions/setProperty/PropertyBean.java,v 1.1 2002/01/28 23:30:34 rlubke Exp $ 
 * $Revision: 1.1 $
 * $Date: 2002/01/28 23:30:34 $
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

package core_syntax.actions.setProperty;

import java.io.Serializable;

/**
 * PropertyBean.java
 * Simple Java Bean to verify that a JSP Container
 * will make use of a Bean's PropertyEditory class
 * if one is available.
 *
 * Created: Tue Oct 23 09:28:19 2001
 *
 * @version $Revision: 1.1 $
 */

public class PropertyBean implements Serializable 
{
    private String _pString   = null;
    private Boolean _pBoolean = null;
    private Integer _pInt     = null;

    public PropertyBean () {
        _pString = "StringValue";
        _pBoolean = new Boolean( "false" );
        _pInt = new Integer( "10" );
    }
    
    /**
     * Get the value of pString.
     * @return value of pString.
     */
    public String getPString() {
        return _pString;
    }
    
    /**
     * Set the value of pString.
     * @param v  Value to assign to pString.
     */
    public void setPString( String  v ) {
        this._pString = v;
    }
    
    /**
     * Get the value of pBoolean.
     * @return value of pBoolean.
     */
    public Boolean getPBoolean() {
        return _pBoolean;
    }
    
    /**
     * Set the value of pBoolean.
     * @param v  Value to assign to pBoolean.
     */
    public void setPBoolean( Boolean  v ) {
        this._pBoolean = v;
    }

    /**
     * Get the value of pInt.
     * @return value of pInt.
     */
    public Integer getPInteger() {
        return _pInt;
    }
    
    /**
     * Set the value of pInt.
     * @param v  Value to assign to pInt.
     */
    public void setPInteger( Integer  v ) {
        this._pInt = v;
    }
}// PropertyBean
