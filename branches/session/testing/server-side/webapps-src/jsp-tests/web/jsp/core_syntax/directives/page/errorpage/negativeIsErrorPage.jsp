<html>
<title>negativeIsErrorPage</title>
<body>
<% /** 	Name : negativeIsErrorPage
	Description : Set the value of iserrorpage to false in a page. 
	              Now try to access the "exception" variable of this page 
	              in a scriptlet.
	Result :Fatal Translation Error
**/ %>	 
<!-- We are setting the error page to "errorpageWrong.jsp" where iserrorpage is false -->
<%@ page errorPage="errorpageWrong.jsp" autoFlush="false" %>
<%! int i=0; %>
<%= 9/i %>
</body>
</html>
