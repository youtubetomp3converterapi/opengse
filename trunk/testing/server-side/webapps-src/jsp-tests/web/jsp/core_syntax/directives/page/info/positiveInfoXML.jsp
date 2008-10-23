
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveInfo</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /**	Name :positiveInfo
		Description: Set the value of the info attribute to a specific
			  value.Then invoke the getServletInfo method on the current 
			  page's Servlet object
		Result: Should return the correct value of the set string. 
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- Testing for the 'info' directive -->
]]></jsp:text>
<jsp:directive.page info="positiveInfo of JCK" />
<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ out.println(getServletInfo()); 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>