
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>negativeNoBody</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : negativeNoBody
	Description : Try to create an invalid useBean tag 
	              (i.e. tag that is not closed)
	Result :Should return a fatal translation error. 
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[	 <!-- Declaring the bean with body -->
<!-- Here the tag is not closed. Hence bound for error -->
]]></jsp:text>
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.useBean.String_IntBean" >
<jsp:text><![CDATA[
]]></jsp:text>
<jsp:getProperty name="myBean" property="Name" />
<jsp:text><![CDATA[
</body>
</html> 
]]></jsp:text>

</jsp:root>