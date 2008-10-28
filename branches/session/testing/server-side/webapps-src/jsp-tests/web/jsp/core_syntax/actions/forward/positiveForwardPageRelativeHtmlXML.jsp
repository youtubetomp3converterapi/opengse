
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveForwardRelhtml.jsp</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : positiveForwardPageRelativeHtml.jsp
	Description :We are testing for request time Static forwarding using
        <jsp:forward page > tag. We test the forwarded static content
        got  forwarded in the output.  Here we use a page-relative path. 
	Result :
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[	 
<!-- testing static forwarding of a page-relative HTML page -->
]]></jsp:text>
<jsp:forward page= "forwardcommon.html" />
<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>