
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>closeValidator</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
    Exception exc = (Exception) application.getAttribute( "ioexception" );
    application.removeAttribute( "ioexception" );
    if ( exc != null && exc instanceof java.io.IOException ) {
        response.addHeader( "status", "Test Status=PASSED" );
    } else {
        response.addHeader( "status", "Test Status=FAILED" );
    }

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>