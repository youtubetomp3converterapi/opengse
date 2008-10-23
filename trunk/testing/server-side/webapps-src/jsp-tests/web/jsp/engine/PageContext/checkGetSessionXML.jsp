
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>checkGetSession</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
/*
 Name : checkGetSession
*/

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- checking for getSession method -->

]]></jsp:text>

<jsp:expression>
<![CDATA[(pageContext.getSession()) instanceof javax.servlet.http.HttpSession ]]>

</jsp:expression>

<jsp:text><![CDATA[
]]></jsp:text>

<jsp:expression>
<![CDATA[ ( session == pageContext.getSession() ) ]]>

</jsp:expression>

<jsp:text><![CDATA[

</body>
</html>
]]></jsp:text>

</jsp:root>