
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveDoubleQuotes</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /**  Name : positiveDoubleQuotes
        Description : we check if request time attributes work with include
                      and we use them in double quoted expression
        Result : we should get the included file without error
 **/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ String test="/tests/core_syntax/directives/include/includecommon"; 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
]]></jsp:text>
<jsp:include page= "%= test+\".jsp\" %" />
<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>