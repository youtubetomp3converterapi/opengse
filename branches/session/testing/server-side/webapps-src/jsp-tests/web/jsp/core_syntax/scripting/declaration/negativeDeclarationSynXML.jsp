
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>negativeDeclarationSyn </title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /**  Name:negativeDeclarationSyn
		Description:Create a JSP page which has a syntax 
	   			error in the declaration statement .try to use 
	    		the declared variable in the rest of the page
		Result:Fatal Translation Error (Undefined)
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[  	 	
<!-- error syntax is used in the declaration statement -->
]]></jsp:text>

<jsp:declaration>
<![CDATA[ int i=627; > 
<% out.println(i); ]]>

</jsp:declaration>

<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>