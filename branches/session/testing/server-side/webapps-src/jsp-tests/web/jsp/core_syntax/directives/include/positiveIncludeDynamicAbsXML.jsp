
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveIncludeDynamicAbs</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : positiveIncludeDynamicAbs
	Description :     We are testing for request time dynamic inclusion 
	using <jsp:include page > tag. We test if we get the included jsp got
        parsed at request time and included to the output. we use absolute
        path from doc root as url
        Result :
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[	 
<!-- Request-time Dynamic inclusion,with absolute url from doc root -->
]]></jsp:text>
<jsp:include page= "/tests/core_syntax/directives/include/includecommon.jsp" />
<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>