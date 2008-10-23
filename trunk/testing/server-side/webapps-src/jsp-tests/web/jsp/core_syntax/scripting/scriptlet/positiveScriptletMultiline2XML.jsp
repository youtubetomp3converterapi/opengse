
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveScriptletMultiline2</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /**  Name: positiveScriptletMultiline2
        Description: Testing with multiple scriptlets, which spans multiple lines in such a
        way that each starts and ends in the same line
        Result:Should not throw any error 
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[      
<!-- multiple lines scriptlet --> 
]]></jsp:text>

<jsp:declaration>
<![CDATA[ int i=5; ]]>

</jsp:declaration>

<jsp:text><![CDATA[
]]></jsp:text>

<jsp:declaration>
<![CDATA[ int j=10; ]]>

</jsp:declaration>

<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ if(j>i){ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[10 ]]></jsp:text>

<jsp:scriptlet>

<![CDATA[} else { 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
5 ]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ } 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>