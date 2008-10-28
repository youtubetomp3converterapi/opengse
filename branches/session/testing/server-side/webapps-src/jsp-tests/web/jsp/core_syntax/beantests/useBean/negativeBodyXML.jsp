
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>negativeBody</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : negativeBody
	Description : Try to create an invalid useBean tag with body (i.e. the
                      tag has a body, but the closing tag does not exist)
	Result :Should return fatal translation error.
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[	 <!-- Declaring the bean with body -->
<!-- Here the bean tag is not closed -->
]]></jsp:text>
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.useBean.String_IntBean" >
<jsp:text><![CDATA[
]]></jsp:text>
<jsp:setProperty name="myBean" property="Name" value="APPLE" />
<jsp:text><![CDATA[
]]></jsp:text>
<jsp:getProperty name="myBean" property="Name" />
<jsp:text><![CDATA[
</body>
</html> 
]]></jsp:text>

</jsp:root>