
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>negativeClose</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
/*
 Name : negativeClose
 Description : Attempt to write to the JspWriter after closing.  
               An Exception should be thrown, but is not caught,
               resulting in a HTTP 500 being sent to the client.
 */
 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
   try {
       out.close();
       out.println( "Arbitrary text" ); 
   } catch ( java.io.IOException ioe ) {
       application.setAttribute( "ioexception", ioe ); 
   }

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>