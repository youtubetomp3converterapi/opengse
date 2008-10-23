
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveExprComment </title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /**	Name:positiveExprComment
		Description: Create a JSP page with a JSP 
			  expression inserted into a HTML comment
		Result: The HTML should contain the value of the
			  expression inserted into the comment
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
]]></jsp:text>

<jsp:declaration>
<![CDATA[ int i=10,j=5; ]]>

</jsp:declaration>

<jsp:text><![CDATA[
<!-- expecting a value 50 in the html comments tag -->
<!-- ]]></jsp:text>

<jsp:expression>
<![CDATA[ i*j  ]]>

</jsp:expression>

<jsp:text><![CDATA[-->
</body>
</html>
]]></jsp:text>

</jsp:root>