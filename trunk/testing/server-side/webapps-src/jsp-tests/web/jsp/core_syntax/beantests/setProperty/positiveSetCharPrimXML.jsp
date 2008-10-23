
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveSetCharPrim</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : positiveSetCharPrim
	Description : use setProperty to set a char value in a bean.
	Result :should return a char value.
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[	 
<!-- We are testing if are able to set a Primitive Character property and get it -->
]]></jsp:text>
<jsp:useBean id="myBean" class="core_syntax.beantests.setProperty.MiscBean" />
<jsp:text><![CDATA[
]]></jsp:text>
<jsp:setProperty name="myBean" property="primitiveChar" value="R" />
<jsp:text><![CDATA[
]]></jsp:text>
<jsp:getProperty name="myBean" property="primitiveChar" />
<jsp:text><![CDATA[

</body>
</html> 
]]></jsp:text>

</jsp:root>