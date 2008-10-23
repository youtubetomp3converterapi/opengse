
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>negativeScripletSyntax</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /**	Name:negativeScripletSyntax
		Description: Create an invalid scriplet by
			 not terminating the java statement with
			 a semicolon(;)
		Result: Should throw an exception during the
			 compile
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[			  			 
<!-- semi colon is dropped in the scriptlet --> 
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ String s="india" 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ out.println(s); 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html>

]]></jsp:text>

</jsp:root>