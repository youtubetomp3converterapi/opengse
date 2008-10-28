<html>
<title>negativeClose</title>
<body>
<%
/*
 Name : negativeClose
 Description : Attempt to write to the JspWriter after closing.  
               An Exception should be thrown, but is not caught,
               resulting in a HTTP 500 being sent to the client.
 */
 %>
<%
   try {
       out.close();
       out.println( "Arbitrary text" ); 
   } catch ( java.io.IOException ioe ) {
       application.setAttribute( "ioexception", ioe ); 
   }
%>
</body>
</html>
