
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveIncludeStaticRel</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : positiveIncludeStaticRel
	Description :We are testing for request time Static inclusion using
        <jsp:include page > tag. We test if we get the included static content
        got  included in the output.here we use relative path from current 
        context  as   url.
	Result :
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[	 
<!-- testing Request-time static inclusion relative to from current context-->
]]></jsp:text>
<jsp:include page= "includecommon.html" />
<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>