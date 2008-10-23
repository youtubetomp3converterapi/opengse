
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

<![CDATA[ /** Name :positiveDoubleQuotes
       Description : checking request time attributes with a
                     scriptlet expression in doublequotes to do forwarding
       Result :we expect the output without error to the forwarded file
 **/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
]]></jsp:text>
<jsp:directive.page autoFlush="false" />
<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ String test="includecommon"; 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
]]></jsp:text>
<jsp:forward page= "%= test+\".html\" %" />
<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>