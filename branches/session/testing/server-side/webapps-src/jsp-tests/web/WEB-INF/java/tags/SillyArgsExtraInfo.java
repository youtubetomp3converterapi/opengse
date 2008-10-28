/*
 * $Header: /home/cvs/jakarta-watchdog-4.0/src/server/jsp-tests/WEB-INF/classes/tags/SillyArgsExtraInfo.java,v 1.2 2002/01/28 23:35:16 rlubke Exp $ 
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
 * TagExtraInfo for an action that accepts only arguments A,B,C
 * such that A+B==C
 */
public class SillyArgsExtraInfo extends TagExtraInfo {

    public boolean isValid( TagData data ) {
        //System.out.println("isValid()");
        if ( data == null ) {
            return false;
        }

        int a, b, c;
        String oa, ob, oc;
        oa = data.getAttributeString( "a" );
        ob = data.getAttributeString( "b" );
        oc = data.getAttributeString( "c" );

        //System.out.println("a: "+oa+" b: "+ob+" c: "+oc);
        if ( ( oa == null ) || ( ob == null ) || ( oc == null ) ) {
            return false;
        }

        if ( Integer.parseInt( oa ) +
                Integer.parseInt( ob ) !=
                Integer.parseInt( oc ) ) {
            //System.out.println("false 'cos they are not equal");
            return false;
        }

        if ( data.getAttribute( "testLibInfo" ) != null ) {
            if ( !testLibInfo() ) {
                //System.out.println("false 'cos of testLibInfo");
                return false;
            }
        }

        return true;
    }

    private boolean testLibInfo() {
        // Now test that TagInfo is somewhat appropriate...
        TagInfo ti = getTagInfo();

        if ( !( ti.getTagClassName() + "ExtraInfo" ).equals( this.getClass().getName() ) ) {
            //System.out.println("ti.getTagClassName: "+ti.getTagClassName());
            return false;
        }

        TagAttributeInfo tai[] = ti.getAttributes();

        if ( tai.length != 3 && tai.length != 4 ) {
            //System.out.println("tai.length = "+tai.length);
            return false;	// there should be 3 attributes
        }

        if ( !has( tai, "a" ) || !has( tai, "b" ) || !has( tai, "c" ) ) {
            //System.out.println("has issue");
            return false;
        }

        TagLibraryInfo tli = ti.getTagLibrary();
        TagInfo ti2 = tli.getTag( ti.getTagName() );

        if ( !( ti2.getTagClassName() + "ExtraInfo" ).equals( this.getClass().getName() ) ) {
            //System.out.println("ti2.getTagClassName: "+ti2.getTagClassName());
            return false;
        }

        return true;
    }

    private static boolean has( TagAttributeInfo tai[], String s ) {
        for ( int i = 0; i < tai.length; i++ ) {
            if ( tai[ i ].getName().equals( s ) ) {
                return true;
            }
        }

        return false;
    }
}
