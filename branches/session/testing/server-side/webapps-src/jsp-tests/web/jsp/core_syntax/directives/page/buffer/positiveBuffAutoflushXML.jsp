
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveBuffAutoflush</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /**	Name: positiveBuffAutoflush
		Description: Set autoflush to true. Use default buffer size of 8kb.
			  Write more than 8kb of data to the out object
		Result: Should return all data written to out without throwing an
			  exception.
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[		
<!-- with default buff size 0f 8kb and autoflush true write more data 22kb -->
]]></jsp:text>
<jsp:directive.page autoFlush="true" />
<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ for(int d=0;d<6000;d++) out.print(d+"    ");  
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>