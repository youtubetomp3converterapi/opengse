
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>checkApplication Test </title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /**	Name:checkApplicationTest
		Description: Returns true if application
		 	is of type javax.servlet.ServletContext
		 	else returns false. Then verify that the
            Context init parameter, contextParam1, is
            available using the application object.
		Result: Returns true if the application object
                is the correct object type and print 
                a non-null value for the context init 
                parameter.
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- checking for application object state -->

]]></jsp:text>

<jsp:expression>
<![CDATA[ (application instanceof javax.servlet.ServletContext) ]]>

</jsp:expression>

<jsp:text><![CDATA[<br>
]]></jsp:text>

<jsp:expression>
<![CDATA[ application.getInitParameter( "contextParam1" ) ]]>

</jsp:expression>

<jsp:text><![CDATA[<br>
</body>
</html>
]]></jsp:text>

</jsp:root>