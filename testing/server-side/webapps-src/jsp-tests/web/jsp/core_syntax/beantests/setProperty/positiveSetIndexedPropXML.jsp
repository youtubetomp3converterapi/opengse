
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveSetIndexedProp</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
/**
 *  TestCase name : positiveSetIndexedProp
 *  Description   : Here, the setProperty tag is used to set the value for
 *                  an indexed property.An array is declared and defined in a
 *                  scriptlet and then assigned through the setProperty tag, with 
 *                  an expression.
 *  Result        : Expected to set the value of the array.
 */
 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[

]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
//Declaring the array
int[] iAry={24,25,26}; 

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[

<!-- Declaring the bean without body -->
]]></jsp:text>
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.setProperty.SetpropBean" />
<jsp:text><![CDATA[
]]></jsp:text>
<jsp:setProperty name="myBean" property="intAry" value="%= iAry %" />
<jsp:text><![CDATA[
<!-- Accessing the property through a scriptlet -->

]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
// Accessing the set property.
int[] ary=myBean.getIntAry();
int l=ary.length;
for(int j=0;j<l;j++)
out.println(ary[j]);

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[

</body>
</html> 
]]></jsp:text>

</jsp:root>