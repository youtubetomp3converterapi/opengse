
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveForwardAbshtml.jsp</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : positiveForwardCtxRelativeHtml.jsp
	Description :We are testing for forwarding to a html page using
        <jsp:forward page > tag. We test if the forwarded static content
        got  forwarded in the output. Here we use a context-relative path.
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[	 
<!-- testing context-relative forwarding to an HTML page -->
]]></jsp:text>
<jsp:forward page= "/jsp/core_syntax/actions/forward/forwardcommon.html" />
<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>