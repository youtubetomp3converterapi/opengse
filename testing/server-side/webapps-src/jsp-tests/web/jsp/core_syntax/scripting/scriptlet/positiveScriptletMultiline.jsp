<html>
<title>positiveScriptletMultiline</title>
<body>
<%	/** Name:positiveScripletMultiline
		Description: Create a valid scriplet in JSP that
			spans multiple lines.
		Result: Should not throw any error
**/ %>		  
<!-- multiple lines scriptlet --> 
<% 	int i=5;
	int j=10;
	if(j>i){ 
 %> 10 <% } 
	else { 
	%> 5 <% } %>
</body>
</html>
