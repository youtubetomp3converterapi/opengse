
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>negativeLangAny</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /**	Name: NegativeLangAny
		Description:Create two jsp directives with the language
			  attribute set to "java" in the first case and anything 
			  other than "java" in the second.
		Result: Fatal translation error
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[		
<!-- language directive set to java & something else  -->
<!-- we expect translation failure  -->
]]></jsp:text>
<jsp:directive.page language="java" errorpage="myerror.jsp" />
<jsp:text><![CDATA[
]]></jsp:text>
<jsp:directive.page language="javascript" />
<jsp:text><![CDATA[
hello
</body>
</html>
]]></jsp:text>

</jsp:root>