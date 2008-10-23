
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>negativeBufferOverflowException</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /**	Name: negativeBufferOverflowException
		Description: Set autoflush to false. Write more
                     date out than the 8KB buffer can handle.
		Result: An exception should be thrown.
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[		
]]></jsp:text>
<jsp:directive.page autoFlush="false" />
<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ try {
      for (int d = 0; d < 60000; d++ ) {
          out.print(d+"    ");  
      }
   } catch ( Throwable t ) {
       out.clear();
       out.println( "Buffer overflow occurred.  Exception successfully caught." );
       out.println( "Test status: PASS" );
   }

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>