
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveForwardRelHtml</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : positiveForwardRelHtml
	Description :We are testing for forwarding to static pages using 
	<jsp:forward page > tag. here we use relative path from current 
        context  as   url.
	Result : we expect the forwarded page to come
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[	 

<!-- testing whether forwarding works using url relative to current context-->
]]></jsp:text>
<jsp:forward page= "includecommon.html" />
<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>