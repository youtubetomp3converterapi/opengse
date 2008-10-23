<html>
<title>positiveFlush</title>
<body>
<% /**
 Name : positiveFlush
 Description : Write some thing into the buffer and then call the flush() method.
 Expected to flush the buffer to the output stream.
 **/ %>
<!-- this is to test if flush() method works -->
<% 
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
%>
</body>
</html>
