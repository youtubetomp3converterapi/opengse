
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>negativeIncludeAbs</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : negativeIncludeAbs
	Description :Create a JSP page with an include directive which
                     includes a file that does not exist in the location
                     specified. 
	Result :
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[	 
<!-- testing with an unavailable include  file -->
]]></jsp:text>
<jsp:directive.include file = "includedNotThere.jsp" />
<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>