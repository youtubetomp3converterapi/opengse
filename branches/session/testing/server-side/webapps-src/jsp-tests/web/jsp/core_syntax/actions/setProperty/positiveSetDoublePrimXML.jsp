
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveSetDoublePrim</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : positiveSetDoublePrim
	Description : use a setProperty to set a double value in a bean
	Result :should return a double value.
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- We are testing if are able to set a PrimitiveDouble property and get it -->
]]></jsp:text>
<jsp:useBean id="myBean" class="core_syntax.actions.setProperty.MiscBean" />
<jsp:text><![CDATA[
]]></jsp:text>
<jsp:setProperty name="myBean" property="primitiveDouble" value="123456" />
<jsp:text><![CDATA[
]]></jsp:text>
<jsp:getProperty name="myBean" property="primitiveDouble" />
<jsp:text><![CDATA[

</body>
</html>
]]></jsp:text>

</jsp:root>