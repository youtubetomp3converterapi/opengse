
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>negativeBuffOverflow</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /**	Name: negativeBuffOverflow
		Description: Use the default buffer size of 8kb. Write out more
			  		 than 8kb of characters to the implicit out object.
		Result: Should throw java.io.IOException and forward to default 
				error page.
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[		
<!-- we keep autoFlush as false -->
<!--- with default buff size 0f 8kb we output more than that. we give 22k --->
]]></jsp:text>
<jsp:directive.page autoFlush="false" errorPage="errorpage.jsp" />
<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ for(int d=0;d<6000;d++) out.println(d+"    "); 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>