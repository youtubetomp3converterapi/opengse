
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>forGetException</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
/**
 Name : forGetException
 Description : An exception is generated and the output is sent to an errorpage
 where the check for the getException() method is done.  Here, it is checked for 
 the return type.
 Result : true
**/

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
]]></jsp:text>
<jsp:directive.page errorPage="checkGetException.jsp" />
<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
int i=2;
int j=0;
int k=i/j;
out.println(k);

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>