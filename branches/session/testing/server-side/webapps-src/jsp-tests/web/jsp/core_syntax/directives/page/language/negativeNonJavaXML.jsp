
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>negativeNonJava</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /**	Name: negativeNonJava
		Description:Create a page with a scriptlet (before a jsp directive).
			  then put a jsp directive after the scriptlet with the language
			  not set to java
		Result: fatal translation error
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[		
<!-- language directive set to something other than java after a scriptlet -->
<!-- we expect translation failure  -->
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ out.println("hello"); 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
]]></jsp:text>
<jsp:directive.page language="javascript" />
<jsp:text><![CDATA[
hello
</body>
</html>
]]></jsp:text>

</jsp:root>