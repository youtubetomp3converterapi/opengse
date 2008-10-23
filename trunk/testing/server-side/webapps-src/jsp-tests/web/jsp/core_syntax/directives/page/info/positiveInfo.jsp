<html>
<title>positiveInfo</title>
<body>
<% /**	Name :positiveInfo
		Description: Set the value of the info attribute to a specific
			  value.Then invoke the getServletInfo method on the current 
			  page's Servlet object
		Result: Should return the correct value of the set string. 
**/ %>
<!-- Testing for the 'info' directive -->
<%@ page info="positiveInfo of JCK" %>
<% out.println(getServletInfo()); %>
</body>
</html>
