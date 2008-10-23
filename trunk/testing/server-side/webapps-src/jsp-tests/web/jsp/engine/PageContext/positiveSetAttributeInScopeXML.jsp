
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveSetAttributeInScope</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
/*
 Name : positiveSetAttributeInScope
*/

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- this is to test if setAttribute() method works -->
<!-- using pageContext object to setAttribute -->
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ pageContext.setAttribute("Country","india",1); 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- using pageContext object to getAttributeScope, 
to obtain value of "Country"-->
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ pageContext.getAttributesScope("Country"); 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ out.println(pageContext.getAttributesScope("Country")); 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>