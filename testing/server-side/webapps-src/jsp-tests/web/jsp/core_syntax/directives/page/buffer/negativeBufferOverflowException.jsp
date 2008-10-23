<html>
<title>negativeBufferOverflowException</title>
<body>
<% /**	Name: negativeBufferOverflowException
		Description: Set autoflush to false. Write more
                     date out than the 8KB buffer can handle.
		Result: An exception should be thrown.
**/ %>		
<%@ page autoFlush="false" %>
<% try {
      for (int d = 0; d < 60000; d++ ) {
          out.print(d+"    ");  
      }
   } catch ( Throwable t ) {
       out.clear();
       out.println( "Buffer overflow occurred.  Exception successfully caught." );
       out.println( "Test status: PASS" );
   }
%>
</body>
</html>
