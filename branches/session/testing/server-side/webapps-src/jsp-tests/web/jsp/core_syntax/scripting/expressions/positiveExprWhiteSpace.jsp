<html>
<title>positiveExprWhiteSpace</title>
<body>
<% /**	Name:positiveExprWhiteSpace
		Description: Create different expressions, varying the whitespace.
		Result: Should return the HTML of the page with the evaluated
			    value of the expression
**/ %>
<%! int i=10; %>
<%=i*i%>
<%= i*i%>
<%=i*i %>
<%= i*i %>
</body>
</html>
