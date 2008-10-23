
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>negativeRemoveAttribute</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
/*
 Name : negativeRemoveAttribute
 Description : Trying to remove an unavailable attribute.
 Expected to get null.
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

<!-- using pageContext object to removeAttribute unavailable attruibute -->
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ pageContext.removeAttribute("State"); 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[

<!-- Trying to access removed  attribute by getAttribute() method-->
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ out.println(pageContext.getAttribute("State")); 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[

</body>
</html>
]]></jsp:text>

</jsp:root>