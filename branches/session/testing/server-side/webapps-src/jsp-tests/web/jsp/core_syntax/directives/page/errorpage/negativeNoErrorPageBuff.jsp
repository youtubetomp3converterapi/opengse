<html>
<title>negativeNoErrorPageBuff</title>
<body>
<% /** 	Name : negativeNoErrorPageBuff
	Description : Create a jsp page that contains an exception. 
	              Set its errorpage attribute to a second page. On the 
	              second page, set the buffer to false and iserrorpage to 
	              true. Access the first page.
	Result :Fatal translation error
**/ %>	 
<!-- We are setting the error page to "errorpage.jsp" where buffer is none -->
<%@ page errorPage="errorpageNoBuff.jsp"  autoFlush="false" %>
<%! int i=0; %>
<%= 9/i %>
</body>
</html>
