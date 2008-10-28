
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveBuffCreate</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /**	Name: positiveBuffCreate
		Description: Create a buffer  size of say 12kb. Keep autoflush
			  set to false(default).Write characters to the out object. Invoke
			  the flush() method on out to flush the output to the client.
		Result:Should return the output that you sent to the client.
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[		
<!-- with buff size 0f 12kb and autoflush false and do flush -->
]]></jsp:text>
<jsp:directive.page buffer="12kb" autoFlush="false" />
<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ for(int d=0;d<1000;d++) out.print(d+"    "); out.flush(); 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>