
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveFlush</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /**
 Name : positiveFlush
 Description : Write some thing into the buffer and then call the flush() method.
 Expected to flush the buffer to the output stream.
 **/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- this is to test if flush() method works -->
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ 
    int buffer = out.getBufferSize();
    out.println("hello"); 
    out.flush();
    if ( buffer == out.getRemaining() ) {
        out.println( "Test status: PASSED" );
    } else {
        out.println( "Test status: FAILED" );
        out.println( "Expected buffer size: " + buffer );
        out.println( "Buffer size after flush: " + out.getRemaining() );
    }

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>