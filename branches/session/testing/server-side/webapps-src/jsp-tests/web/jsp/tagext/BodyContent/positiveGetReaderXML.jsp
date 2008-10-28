
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveGetReader</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ 
	/** 	
	Name : positiveGetReader
	Description : Create a BodyContent object and call
	              the getReader() method. 
	Result :      Resulting object should be of type
	             java.io.Reader
	**/  

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[	 
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[	
	javax.servlet.jsp.tagext.BodyContent bc = pageContext.pushBody();
	bc.println("Moon");

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- checking for Reader object -->
]]></jsp:text>

<jsp:expression>
<![CDATA[ (bc.getReader()) instanceof java.io.Reader ]]>

</jsp:expression>

<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
        pageContext.popBody();

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[

</body>
</html>
]]></jsp:text>

</jsp:root>