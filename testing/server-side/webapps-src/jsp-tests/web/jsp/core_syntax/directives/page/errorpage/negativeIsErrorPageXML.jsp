
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>negativeIsErrorPage</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : negativeIsErrorPage
	Description : Set the value of iserrorpage to false in a page. 
	              Now try to access the "exception" variable of this page 
	              in a scriptlet.
	Result :Fatal Translation Error
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[	 
<!-- We are setting the error page to "errorpageWrong.jsp" where iserrorpage is false -->
]]></jsp:text>
<jsp:directive.page errorPage="errorpageWrong.jsp" autoFlush="false" />
<jsp:text><![CDATA[
]]></jsp:text>

<jsp:declaration>
<![CDATA[ int i=0; ]]>

</jsp:declaration>

<jsp:text><![CDATA[
]]></jsp:text>

<jsp:expression>
<![CDATA[ 9/i ]]>

</jsp:expression>

<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>