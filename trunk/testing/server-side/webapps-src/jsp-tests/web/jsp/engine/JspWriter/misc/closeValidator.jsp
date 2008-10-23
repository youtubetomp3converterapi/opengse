<html>
<title>closeValidator</title>
<body>
<%
    Exception exc = (Exception) application.getAttribute( "ioexception" );
    application.removeAttribute( "ioexception" );
    if ( exc != null && exc instanceof java.io.IOException ) {
        response.addHeader( "status", "Test Status=PASSED" );
    } else {
        response.addHeader( "status", "Test Status=FAILED" );
    }
%>
</body>
</html>
