
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>negativeLangJava</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /**	Name:negativeLangJava
		Description: Create two jsp directives on the same page
			 with language attribute set to "java" in both cases
		Result:Fatal translation error
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[		
<!-- test if language directive set to java twice cause translation failure -->
]]></jsp:text>
<jsp:directive.page language="java" />
<jsp:text><![CDATA[
]]></jsp:text>
<jsp:directive.page language="java" />
<jsp:text><![CDATA[
hello
</body>
</html>
]]></jsp:text>

</jsp:root>