
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>negativeNoErrorPageBuff</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : negativeNoErrorPageBuff
	Description : Create a jsp page that contains an exception. 
	              Set its errorpage attribute to a second page. On the 
	              second page, set the buffer to false and iserrorpage to 
	              true. Access the first page.
	Result :Fatal translation error
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[	 
<!-- We are setting the error page to "errorpage.jsp" where buffer is none -->
]]></jsp:text>
<jsp:directive.page errorPage="errorpageNoBuff.jsp" autoFlush="false" />
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