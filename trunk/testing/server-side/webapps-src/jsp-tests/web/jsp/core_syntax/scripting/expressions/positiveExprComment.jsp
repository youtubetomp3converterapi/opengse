<html>
<title>positiveExprComment </title>
<body>
<% /**	Name:positiveExprComment
		Description: Create a JSP page with a JSP 
			  expression inserted into a HTML comment
		Result: The HTML should contain the value of the
			  expression inserted into the comment
**/ %>
<%! int i=10,j=5; %>
<!-- expecting a value 50 in the html comments tag -->
<!-- <%= i*j  %>-->
</body>
</html>
