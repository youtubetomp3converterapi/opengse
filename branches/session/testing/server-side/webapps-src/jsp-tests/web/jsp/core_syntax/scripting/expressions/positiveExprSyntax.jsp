<html>

<title>positiveExprSyntax</title>
<body>
<% /**	Name:positiveExprSyntax
		Description: Create an expression in the page with no
			 space between the start tag and expression. Similarly 
			 have no space between the end of the expression and
			 end tag.
		Result: Should return the HTML of the page with the evaluated
			 value of the expression
**/ %>			 			 

<%! int i=10; %>
<!-- with no space between expression tags -->
<!-- expecting a value 100 -->
<%=i*i%>
</body>
</html>
 