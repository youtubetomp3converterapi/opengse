
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveGetRemainingBufferUnset</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
/*
 Name : positiveGetRemainingBufferUnset
 Description :Set the buffer directive to 'none' and call
the method getRemaining() in the JSP page.
*/

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- this is to test positiveGetRemainingBufferUnset  -->
<!-- setting buffer size to none -->
]]></jsp:text>
<jsp:directive.page buffer="none" />
<jsp:text><![CDATA[

<!-- using getRemaining() method to get the buffer size -->

]]></jsp:text>

<jsp:expression>
<![CDATA[ out.getRemaining() ]]>

</jsp:expression>

<jsp:text><![CDATA[
<!-- expected to return 0  -->
</body>
</html>
]]></jsp:text>

</jsp:root>