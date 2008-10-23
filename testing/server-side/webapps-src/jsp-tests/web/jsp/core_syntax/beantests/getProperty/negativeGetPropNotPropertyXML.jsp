
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>negativeGetPropNotProperty</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : negativeGetPropNotProperty
	Description : Accessing a non-existant property of an explicit bean 
                      through a getProperty action
	Result : Should return an error page.
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[	 
<!-- Declaring the bean with out body -->
]]></jsp:text>
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.getProperty.StringBean" />
<jsp:text><![CDATA[
]]></jsp:text>
<jsp:getProperty name="myBean" property="foo" />
<jsp:text><![CDATA[
</body></body>
</html> 
]]></jsp:text>

</jsp:root>