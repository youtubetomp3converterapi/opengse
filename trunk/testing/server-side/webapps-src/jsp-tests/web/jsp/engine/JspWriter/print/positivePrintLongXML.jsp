
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positivePrintLong</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
/*
 Name : positivePrintLong
*/

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- this is to test if print(long l) method works -->
]]></jsp:text>

<jsp:declaration>
<![CDATA[ long l=0; ]]>

</jsp:declaration>

<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ 
    out.print(l);
    out.print(Long.MIN_VALUE);
    out.print(Long.MAX_VALUE);

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>