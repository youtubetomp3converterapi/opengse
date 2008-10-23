
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveSetByteObj</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : positiveSetByteObj
	Description :use a setProperty to set a Byte value in a bean 
	Result :should return a Byte value.
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- We are testing if are able to set a byte property and get it -->
]]></jsp:text>
<jsp:useBean id="myBean" class="core_syntax.actions.setProperty.MiscBean" />
<jsp:text><![CDATA[
]]></jsp:text>
<jsp:setProperty name="myBean" property="objectByte" value="123" />
<jsp:text><![CDATA[
]]></jsp:text>
<jsp:getProperty name="myBean" property="objectByte" />
<jsp:text><![CDATA[

</body>
</html>
]]></jsp:text>

</jsp:root>