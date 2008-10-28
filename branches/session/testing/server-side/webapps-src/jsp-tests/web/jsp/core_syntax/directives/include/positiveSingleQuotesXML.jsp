
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveSingleQuotes</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[  /** Name : positiveSingleQuotes
        Description : we check if request time attribute work with include 
                     with a scriptlet expression given in single quotes
        Result : we expect the output with the file included without error
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
<jsp:include page= '%= test+\".jsp\" %' />
<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>