
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>checkRequest</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** Name:checkRequest
	   Description: Checks whether 'request' is an instance 
	   		of javax.servlet.ServletRequest and uses the request
	   		object for getting the protocol and getting parameter 
	   		value passing Years.
		Result:returns true,HTTP/1.0,2 	
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- checking for request object type -->
]]></jsp:text>

<jsp:expression>
<![CDATA[ (request instanceof javax.servlet.ServletRequest) ]]>

</jsp:expression>

<jsp:text><![CDATA[

<br>
<!-- request object used to access getProtocol -->
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ out.println(request.getProtocol()); 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<br>

<!-- request object used to access getParameter -->
<!-- Parameter "Years" is passed to request object -->
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ out.println(request.getParameter("Years")); 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[<br>
</body>
</html>
]]></jsp:text>

</jsp:root>