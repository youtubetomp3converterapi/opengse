<html>
<title>checkPageContext</title>
<body>
<% /**	Name:checkPageContext 
        Description: Verify that implicit object pageContext
                     is an instance of javax.servlet.jsp.PageContext.
                     Then use PageContext.setAttribute() and 
                     PageContext.getAttribute() to set and retrieve
                     an attribute from the page.
		Result: true should be returned twice. 
**/ %>
<!-- checking for pageContext object type -->
<%= (pageContext instanceof javax.servlet.jsp.PageContext) %><br>
<%
    pageContext.setAttribute( "available", new String( "true" ), PageContext.PAGE_SCOPE );
    String temp = (String) pageContext.findAttribute( "available" );
    out.print( temp );
%><br>

</body>
</html>
