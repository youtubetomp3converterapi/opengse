
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveSession</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /**	Name : positiveSession
		Description:Set the value of the session attribute to "true".
			   Then try to access the implicit session object and 
			   call one of its methods
		Result: Returns the output of the method invoked on the 
				session object
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[		
<!--- testing if implicit session object works --->
]]></jsp:text>
<jsp:directive.page session="true" />
<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ out.println("got "+session.isNew()); 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>