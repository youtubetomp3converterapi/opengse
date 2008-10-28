
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveDupIDRequest1</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : positiveDupIDRequest1
	Description :Try to create two useBean tags on different JSP pages
                     with same ID, but with scope attribute being set to
                     "request". 
	Result :Should not return an error.
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[	 
<!-- Declaring the bean with out body -->
]]></jsp:text>
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.useBean.String_IntBean" />
<jsp:text><![CDATA[
<!-- accessing the bean thru a scriptlet  -->
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