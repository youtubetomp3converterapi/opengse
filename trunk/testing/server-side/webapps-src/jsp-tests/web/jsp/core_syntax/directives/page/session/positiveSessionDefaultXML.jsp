
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveSessionDefault</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /**	Name : positiveSessionDefault
		Description:Do not set the value of the session at all.
			    Try to invoke a method on the implicit session
			    object.
		Result: Returns the output of the method invoked on the session 
				object.
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[		
<!--- testing if implicit session is available by default  --->
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