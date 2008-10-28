
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveGetProps</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : positiveGetProps
	Description : Create a valid useBean tag in the JSP. Access one of its
                      properties through a getProperty tag.
	Result : Should return the value of the property as part of the returned 
	HTML 
**/  
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[	 
<!-- Declaring the bean with out body -->
]]></jsp:text>
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.getProperty.StringBean" />
<jsp:text><![CDATA[
]]></jsp:text>
<jsp:getProperty name="myBean" property="name" />
<jsp:text><![CDATA[
</body>
</html> 
]]></jsp:text>

</jsp:root>