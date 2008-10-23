
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveNoBody</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : positiveNoBody
	Description : Try to create a valid useBean tag in the JSP page with
                      no body.
	Result :Should return the JSP page as it is to the browser.
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[	 
<!-- Declaring the bean with out body -->
]]></jsp:text>
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.useBean.String_IntBean" />
<jsp:text><![CDATA[
<!-- accessing the bean thru a scriptlet -->
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
 out.println(myBean.getName());

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html> 
]]></jsp:text>

</jsp:root>