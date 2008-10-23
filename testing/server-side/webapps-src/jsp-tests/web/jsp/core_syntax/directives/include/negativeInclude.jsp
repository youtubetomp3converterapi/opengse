<html>
<title>negativeIncludeAbs</title>
<body>
<% /** 	Name : negativeIncludeAbs
	Description :Create a JSP page with an include directive which
                     includes a file that does not exist in the location
                     specified. 
	Result :
**/ %>	 
<!-- testing with an unavailable include  file -->
<%@ include file = "includedNotThere.jsp" %>
</body>
</html>