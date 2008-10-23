
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveClear</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
/*
 Name: positiveClear
 Description: Initially we write something to the buffer
 and later we call the clear() method. If we get a blank page output,
 clear works.
 */
 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- this is to test if clear() method clears the buffer -->
<!-- we are writing into a stream -->
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ out.println("hello"); 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- clearing the out stream using clear() method -->
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ out.clear(); 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- expecting blank page -->
</body>
</html>

]]></jsp:text>

</jsp:root>