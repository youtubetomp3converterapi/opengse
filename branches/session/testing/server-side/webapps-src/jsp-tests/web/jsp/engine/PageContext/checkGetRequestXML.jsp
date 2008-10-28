
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>checkGetRequest</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
/*
 Name : checkGetRequest
*/

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- checking for getRequest method -->



]]></jsp:text>

<jsp:expression>
<![CDATA[ pageContext.getRequest() instanceof javax.servlet.ServletRequest ]]>

</jsp:expression>

<jsp:text><![CDATA[
]]></jsp:text>

<jsp:expression>
<![CDATA[ ( request == pageContext.getRequest() ) ]]>

</jsp:expression>

<jsp:text><![CDATA[


</body>
</html>
]]></jsp:text>

</jsp:root>