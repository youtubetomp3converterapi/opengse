
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>checkPage</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /**	Name: checkPage
	    Description: Validate that the implicit page
                     object is of type java.lang.Object.
                     Also verify that this is the same
                     reference as page.
		Result: writes true twice.
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- checking for page object type -->
]]></jsp:text>

<jsp:expression>
<![CDATA[ (page instanceof java.lang.Object ) ]]>

</jsp:expression>

<jsp:text><![CDATA[<br>
]]></jsp:text>

<jsp:expression>
<![CDATA[ ( page == this ) ]]>

</jsp:expression>

<jsp:text><![CDATA[<br>
</body>
</html>
]]></jsp:text>

</jsp:root>