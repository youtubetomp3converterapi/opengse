
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveRemoveAttribute</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
/*
 Name : positiveRemoveAttribute
*/

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- this is to test if removetAttribute() method works -->
<!-- using pageContext object to setAttribute -->

]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ pageContext.setAttribute("Country","india"); 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[

<!-- using pageContext object to removeAttribute "Name" attruibute -->
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ pageContext.removeAttribute("Country"); 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[

<!-- Trying to access removed "Name" attribute by getAttribute() method-->
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ out.println(pageContext.getAttribute("Country")); 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[

</body>
</html>

]]></jsp:text>

</jsp:root>