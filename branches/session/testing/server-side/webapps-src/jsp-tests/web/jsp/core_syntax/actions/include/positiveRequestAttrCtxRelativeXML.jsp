
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveRequestAttrCtxRelative</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : positiveRequestAttrCtxRelative
	Description :     We are testing for request time dynamic inclusion 
	using <jsp:include page > tag. We test if we get the included jsp got
        parsed at request time and included to the output. we use absolute
        path from contex-root root as url
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ String test="/jsp/core_syntax/actions/include/includecommon"; 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- Request-time Dynamic inclusion,with absolute url from context-root -->
]]></jsp:text>
<jsp:include page= '%= test+".jsp" %' flush="true" />
<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>