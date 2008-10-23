
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>negativeSession</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /**	Name: negativeSession
		Description: Set the value of the session attribute to "false".
			  Then try to invoke a method on the implicit session object.
		Result:Fatal translation error
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[		
<!--- testing with session directive as false -it should not compile--->
]]></jsp:text>
<jsp:directive.page session="false" />
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