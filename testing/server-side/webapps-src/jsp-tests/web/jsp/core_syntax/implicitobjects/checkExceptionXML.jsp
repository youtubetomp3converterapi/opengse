
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>checkException Test </title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /**	Name:checkExceptionTest
		Description: Cause a java.lang.ArithmeticException by 
                     dividing by zero.  The Exception should
                     be passed to the error page as specified
                     by the errorPage attribute of the page
                     directive.  The errorpage will verify the
                     type of exception.
			 type java.lang.Throwable
        Result: Errorpage is called up, where this check is done.
                Should return true.					 
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- errorpage -->
]]></jsp:text>
<jsp:directive.page errorPage="Errorpage.jsp" />
<jsp:text><![CDATA[

]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
 int i=0; 
 int j=9;
 int k=j/i;

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>