<html>
<title>positiveScripletLiteral</title>
<body>
<% /**	Name: positiveScripletLiteral
		Description: Use the scriplet end tag as a literal
			  within the scriptlet(say in a out.println() 
			  statement).
		Result:Should return the page with the scriplet end 
			  tag embedded in the output stream.
**/ %>  			  
<!-- scriplet end tag is used as literal within the scriptlet -->
<% out.println("%\>"); %>
</body>
</html>
