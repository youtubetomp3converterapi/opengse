<html>
<title>negativeScripletSyntax</title>
<body>
<% /**	Name:negativeScripletSyntax
		Description: Create an invalid scriplet by
			 not terminating the java statement with
			 a semicolon(;)
		Result: Should throw an exception during the
			 compile
**/ %>			  			 
<!-- semi colon is dropped in the scriptlet --> 
<% String s="india" %>
<% out.println(s); %>
</body>
</html>

