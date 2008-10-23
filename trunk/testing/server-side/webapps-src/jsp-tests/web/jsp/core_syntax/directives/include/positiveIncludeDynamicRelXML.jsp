
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveIncludeDynamicRel</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : positiveIncludeDynamicRel
	Description : We are testing for request time dynamic inclusion using
        <jsp:include page > tag. We test if we get the included jsp got
        parsed at request time and included to the output. we use relative
        path from current context as url
	Result :
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[	 
<!-- Request-time Dynamic inclusion url relative to current context. -->
]]></jsp:text>
<jsp:include page= "includecommon.jsp" />
<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>