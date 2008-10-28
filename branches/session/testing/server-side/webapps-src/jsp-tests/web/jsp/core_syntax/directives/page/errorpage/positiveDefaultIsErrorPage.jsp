<html>
<title>positiveDefaultIsErrorPage</title>
<body>
<% /** 	Name : positiveDefaultIsErrorPage.jsp
	Description : Test the value of isErrorPage=false is default in a page. 
		      This is set in the 'errorpagedefault.jsp'	
**/ %>
<!-- We are setting the error page to "errorpagedefault.jsp" where isErrorPage is not set (false) -->
<%@ page errorPage="errorpagedefault.jsp" autoFlush="false" %>
<%! int i=0; %>
<%= 9/i %>
</body>
</html>
