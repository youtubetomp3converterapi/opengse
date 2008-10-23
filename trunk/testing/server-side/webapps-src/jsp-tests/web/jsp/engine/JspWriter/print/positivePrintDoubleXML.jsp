
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positivePrintDouble</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
/*
 Name : positivePrintDouble
*/

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- this is to test if print(double d) method works -->
]]></jsp:text>

<jsp:declaration>
<![CDATA[ double d=0.0; ]]>

</jsp:declaration>

<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ 
    out.print(d); 
    out.print(Double.MIN_VALUE);
    out.print(Double.MAX_VALUE);

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>