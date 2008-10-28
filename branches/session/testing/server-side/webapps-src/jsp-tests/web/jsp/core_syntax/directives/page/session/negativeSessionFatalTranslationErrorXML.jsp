
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>negativeSessionFatalTranslationError</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /**	Name : negativeSessionFatalTranslationError
		Description:Set the value of the session attribute to "false".
			        Try to access the implicit session object.
		Result: A fatal translation error should occur.
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[		
<!--- verify that a fatal translation error ocurrs -->
]]></jsp:text>
<jsp:directive.page session="false" />
<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ 
    if ( session == null ) {
        out.println( "Session object shouldn't be available." );
    }

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>