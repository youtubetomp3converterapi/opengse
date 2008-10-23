
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>negativeSetPropNotWrite</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : negativeSetPropNotWrite
	Description : Accessing a read-only property of an explicit bean 
                      through a getProperty action
	Result : Should return an error page.
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[	 
<!-- Declaring the bean with out body -->
]]></jsp:text>
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.setProperty.SetpropBean" />
<jsp:text><![CDATA[
]]></jsp:text>
<jsp:setProperty name="myBean" property="bar" value="change" />
<jsp:text><![CDATA[
</body>
</html> 
]]></jsp:text>

</jsp:root>