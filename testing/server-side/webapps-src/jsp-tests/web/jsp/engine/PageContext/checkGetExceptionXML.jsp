
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[]]></jsp:text>
<jsp:directive.page isErrorPage="true" />
<jsp:text><![CDATA[
<html>
<title>checkGetException</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
 /*
  Name : checkGetException
 */

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[ 


<!-- checking for getException method -->

]]></jsp:text>

<jsp:expression>
<![CDATA[ (pageContext.getException()) instanceof java.lang.Exception ]]>

</jsp:expression>

<jsp:text><![CDATA[
]]></jsp:text>

<jsp:expression>
<![CDATA[ ( exception == pageContext.getException() ) ]]>

</jsp:expression>

<jsp:text><![CDATA[

</body>
</html>


]]></jsp:text>

</jsp:root>