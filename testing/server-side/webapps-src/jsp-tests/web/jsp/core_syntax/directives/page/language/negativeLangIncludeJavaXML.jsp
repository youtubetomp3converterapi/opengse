
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>negativeLangIncludeJava</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /**	Name: negativeLangIncludeJava
		Description: Create a page with the language attribute set
			  to java and with an include attribute. The included page 
			  should have a jsp directive with language attribute set to
			  java.
		Result:Fatal translation error
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[		
<!-- language directive set to java  -->
<!-- we include a page whose language directive set to java -->
<!-- we expect translation failure since we set directive of language twice -->
]]></jsp:text>
<jsp:directive.page language="java" />
<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ out.println("hello"); 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
]]></jsp:text>
<jsp:directive.include file="includedJava.jsp" />
<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>