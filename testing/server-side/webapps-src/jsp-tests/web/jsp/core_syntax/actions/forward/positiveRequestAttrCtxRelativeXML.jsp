
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveRequestAttrAbs</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : positiveRequestAttrCtxRelative
	Description :     We are testing for request time dynamic inclusion 
	using <jsp:forward page > tag. We test if we get the forwarded jsp got
        parsed at request time and forwarded to the output using a 
        context-relative path.
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[	 
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ String test="/jsp/core_syntax/actions/forward/forwardcommon"; 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- Request-time Dynamic inclusion, with a context-relative path -->
]]></jsp:text>
<jsp:forward page= '%= test+".jsp" %' />
<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>