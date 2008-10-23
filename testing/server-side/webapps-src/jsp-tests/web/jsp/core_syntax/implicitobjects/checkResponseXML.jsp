
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>checkResponse</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /**  Name:checkResponse
        Description: Checks whether response is an object
             of type javax.servlet.ServletResponse. Then verify
             that a method can be called against the response object.
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- checking for response object type -->
]]></jsp:text>

<jsp:expression>
<![CDATA[ (response instanceof javax.servlet.ServletResponse) ]]>

</jsp:expression>

<jsp:text><![CDATA[<br>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
    response.addHeader("TestHeader", "Method call OK");

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[

</body>
</html>
]]></jsp:text>

</jsp:root>