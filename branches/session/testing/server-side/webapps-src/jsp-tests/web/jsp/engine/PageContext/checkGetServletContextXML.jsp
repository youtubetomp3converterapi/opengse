
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>checkGetServletContext</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
/*
 Name : checkGetServletContext
*/

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- checking for getServletContext method -->

]]></jsp:text>

<jsp:expression>
<![CDATA[ (pageContext.getServletContext()) instanceof javax.servlet.ServletContext ]]>

</jsp:expression>

<jsp:text><![CDATA[
]]></jsp:text>

<jsp:expression>
<![CDATA[ ( application == pageContext.getServletContext() ) ]]>

</jsp:expression>

<jsp:text><![CDATA[

</body>
</html>
]]></jsp:text>

</jsp:root>