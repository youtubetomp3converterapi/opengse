
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveSetLongObj</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : positiveSetLongObj
	Description : use a setProperty to set a Long value in a bean
	Result :should return a Long value.
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[	 
<!-- We are testing if are able to set a Long property and get it -->
]]></jsp:text>
<jsp:useBean id="myBean" class="core_syntax.beantests.setProperty.MiscBean" />
<jsp:text><![CDATA[
]]></jsp:text>
<jsp:setProperty name="myBean" property="objectLong" value="12345" />
<jsp:text><![CDATA[
]]></jsp:text>
<jsp:getProperty name="myBean" property="objectLong" />
<jsp:text><![CDATA[

</body>
</html> 
]]></jsp:text>

</jsp:root>