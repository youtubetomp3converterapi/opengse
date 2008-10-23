
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>checkPageContext</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /**	Name:checkPageContext 
        Description: Verify that implicit object pageContext
                     is an instance of javax.servlet.jsp.PageContext.
                     Then use PageContext.setAttribute() and 
                     PageContext.getAttribute() to set and retrieve
                     an attribute from the page.
		Result: true should be returned twice. 
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- checking for pageContext object type -->
]]></jsp:text>

<jsp:expression>
<![CDATA[ (pageContext instanceof javax.servlet.jsp.PageContext) ]]>

</jsp:expression>

<jsp:text><![CDATA[<br>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
    pageContext.setAttribute( "available", new String( "true" ), PageContext.PAGE_SCOPE );
    String temp = (String) pageContext.findAttribute( "available" );
    out.print( temp );

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[<br>

</body>
</html>
]]></jsp:text>

</jsp:root>