
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveIncludeCtxRelativeHtml</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : positiveIncludeCtxRelativeHtml
	Description :We are testing for static inclusion of html page using
        <jsp:include page > tag. We test if we get the included static content
        got  included in the output. Here we use absolute path from current 
        context  as   url.
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- testing absolute inclusion of html relative to from current context-->
]]></jsp:text>
<jsp:include page= "/jsp/core_syntax/actions/include/includecommon.html" flush="true" />
<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>