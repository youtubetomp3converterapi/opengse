
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveClose</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
/*
 Name : positiveClose
 Description : Write something to the stream and close the stream. After closing
 the stream, try to write something into the stream. 
 */
 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- This is to test if close method works -->
<!-- We should get 'out' object to be null after closing output -->
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ out.println("hello"); 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ out.close(); 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ out.println("hello"); 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>