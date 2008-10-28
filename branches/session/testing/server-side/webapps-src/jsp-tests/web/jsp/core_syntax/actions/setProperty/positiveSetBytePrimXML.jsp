
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveSetBytePrim</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : positiveSetBytePrim
	Description : use a setProperty to set a byte value in a bean
	Result :should return a byte value.
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- We are testing if are able to set a primitiveByte property and get it -->
]]></jsp:text>
<jsp:useBean id="myBean" class="core_syntax.actions.setProperty.MiscBean" />
<jsp:text><![CDATA[
]]></jsp:text>
<jsp:setProperty name="myBean" property="primitiveByte" value="123" />
<jsp:text><![CDATA[
]]></jsp:text>
<jsp:getProperty name="myBean" property="primitiveByte" />
<jsp:text><![CDATA[

</body>
</html>
]]></jsp:text>

</jsp:root>