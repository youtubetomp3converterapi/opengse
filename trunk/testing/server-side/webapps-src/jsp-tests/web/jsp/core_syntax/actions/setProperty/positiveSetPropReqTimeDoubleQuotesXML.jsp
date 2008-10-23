
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveSetPropReqTimeDoubleQuotes</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : positiveSetPropReqTimeDoubleQuotes
	Description : use a scriptlet expression in double quotes
                      as 'value' attribute in setProperty
	Result :we should get the expected page without error
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- testing if are able to set a  property using double quoted expression -->
]]></jsp:text>
<jsp:useBean id="myBean" class="core_syntax.actions.setProperty.MiscBean" />
<jsp:text><![CDATA[
]]></jsp:text>
<jsp:setProperty name="myBean" property="path" value="%= request.getProtocol() %" />
<jsp:text><![CDATA[
]]></jsp:text>
<jsp:getProperty name="myBean" property="path" />
<jsp:text><![CDATA[

</body>
</html>
]]></jsp:text>

</jsp:root>