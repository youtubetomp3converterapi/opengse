/*
 * $Header: /home/cvs/jakarta-watchdog-4.0/src/server/jsp-tests/WEB-INF/classes/core_syntax/actions/setProperty/SetpropBean.java,v 1.1 2002/01/28 23:30:34 rlubke Exp $ 
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

public class SetpropBean {

    //Declaring the variables
    private String name = "hello";
    private int num = 0;
    private String str;
    private int[] iArray = {15, 20, 35};
    private byte[] bArray = null;
    private char[] cArray = null;
    private short[] sArray = null;
    private float[] fArray = null;
    private long[] lArray = null;
    private double[] dArray = null;
    private boolean[] boArray = null;
    private Byte[] byteArray = null;
    private Character[] charArray = null;
    private Short[] shortArray = null;
    private Integer[] integerArray = null;
    private Float[] floatArray = null;
    private Long[] longArray = null;
    private Double[] doubleArray = null;
    private Boolean[] booleanArray = null;
    private String bar = "read-only";

    /**
     * Property 'Str'
     */
    public String getStr() {
        return str;
    }

    public void setStr( String a ) {
        this.str = a;
    }

    /**
     * Property 'Name'
     */
    public String getName() {
        return name;
    }

    public void setName( String s ) {
        this.name = s;
    }

    /**
     * Property 'Num'
     */
    public int getNum() {
        return num;
    }

    public void setNum( int numb ) {
        this.num = numb;
    }

    /**
     * property 'intAry'
     * This is an indexed property
     */
    public int[] getIArray() {
        return iArray;
    }

    public void setIArray( int[] i ) {
        this.iArray = i;
    }

    public byte[] getBArray() {
        return bArray;
    }
    
    public void setBArray( byte[]  b ) {
        this.bArray = b;
    }

    public char[] getCArray() {
        return cArray;
    }

    public void setCArray( char[] c ) {
        this.cArray = c;
    }

    public short[] getSArray() {
        return sArray;
    }

    public void setSArray( short[] s ) {
        this.sArray = s;
    }

    public float[] getFArray() {
        return fArray;
    }

    public void setFArray( float[] f ) {
        this.fArray = f;
    }

    public double[] getDArray() {
        return dArray;
    }
    
    public void setDArray( double[] d ) {
        this.dArray = d;
    }

    public long[] getLArray() {
        return lArray;
    }

    public void setLArray( long[] l ) {
        this.lArray = l;
    }

    public boolean[] getBoArray() {
        return boArray;
    }

    public void setBoArray( boolean[] b ) {
        this.boArray = b;
    }

    public Byte[] getByteArray() {
        return byteArray;
    }

    public void setByteArray( Byte[] b ) {
        this.byteArray = b;
    }

    public Character[] getCharArray() {
        return charArray;
    }

    public void setCharArray( Character[] c ) {
        this.charArray = c;
    }

    public Short[] getShortArray() {
        return shortArray;
    }

    public void setShortArray( Short[] s ) {
        this.shortArray = s;
    }

    public Integer[] getIntegerArray() {
        return integerArray;
    }

    public void setIntegerArray( Integer[] i ) {
        this.integerArray = i;
    }

    public Float[] getFloatArray() {
        return floatArray;
    }

    public void setFloatArray( Float[] f ) {
        this.floatArray = f;
    }

    public Long[] getLongArray() {
        return longArray;
    }

    public void setLongArray( Long[] l ) {
        this.longArray = l;
    }

    public Double[] getDoubleArray() {
        return doubleArray;
    }

    public void setDoubleArray( Double[] d ) {
        this.doubleArray = d;
    }

    public Boolean[] getBooleanArray() {
        return booleanArray;
    }

    public void setBooleanArray( Boolean[] b ) {
        this.booleanArray = b;
    }

    /**
     * property 'bar'
     * This is a read only property
     */
    public String getBar() {
        return bar;
    }
}
