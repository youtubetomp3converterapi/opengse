
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveGetBufferSizeUnBuffered</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
/*
 Name : positiveGetBufferSizeUnBuffered
 Description : Set the buffer directive to 'none'. 
 Then call the getBufferSize() method.
 */
 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- this is to test if getBufferSize method works with no buffer-->
<!-- setting buffer size to 'none' using directive -->
]]></jsp:text>
<jsp:directive.page buffer="none" />
<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ out.println("got="+out.getBufferSize()); 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- expected to return 0 as buffer is set 'none' --> 
</body>
</html>
]]></jsp:text>

</jsp:root>