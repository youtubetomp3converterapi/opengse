
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>checkConfig Test </title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /**	Name:checkConfig
		Description: Checks whether configuration information
			 is being passed to the server page. Verify that the
             implicit config object is an instance of
             javax.servlet.ServletConfig and then print out the 
             values of two servlet initialization parameters.
		Result: Display true and the values of configParam1 and configParam2 
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- checking for config object state -->
]]></jsp:text>

<jsp:expression>
<![CDATA[ (config instanceof javax.servlet.ServletConfig) ]]>

</jsp:expression>

<jsp:text><![CDATA[
<br>
]]></jsp:text>

<jsp:expression>
<![CDATA[ config.getInitParameter( "configParam1" ) ]]>

</jsp:expression>

<jsp:text><![CDATA[<br>
]]></jsp:text>

<jsp:expression>
<![CDATA[ config.getInitParameter( "configParam2" ) ]]>

</jsp:expression>

<jsp:text><![CDATA[<br>
</body>
</html>
]]></jsp:text>

</jsp:root>