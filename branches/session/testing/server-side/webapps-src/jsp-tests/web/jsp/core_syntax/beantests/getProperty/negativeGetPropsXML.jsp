
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>negativeGetProps</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : negativeGetProps
	Description : Access any property of a non-implicit bean through a
                      getProperty action without first declaring a corresponding
                      useBean action for that page.
	Result : Should return an error page.
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[	 
<!-- accessing the bean thru a scriptlet before the declaration -->
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
 out.println(myBean.getName());

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- Declaring the bean with out body -->
]]></jsp:text>
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.getProperty.StringBean" />
<jsp:text><![CDATA[
</body>
</html> 
]]></jsp:text>

</jsp:root>