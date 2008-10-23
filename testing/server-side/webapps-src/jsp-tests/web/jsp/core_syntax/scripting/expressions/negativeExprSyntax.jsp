<html>
<title>negativeExprSyntax</title>
<body>
<% /**	Name:negativeExprSyntax
		Description: Create an invalid expression element .
			 Try to call the JSP page	
   		Result: Fatal translation error
**/ %>   		 			 
<%! int i=10; %>
<!-- invalid semi colon is used in the expression element -->
<%= i*i; %>
</body>
<html>
 