<html>
<title>positiveDeclaration</title>
<body>
<% /**	Name : positiveDeclaration
		Description: Create a JSP page with a valid Java 
			declaration element. Use the declared variable 
			in the page in a scriptlet.   
		Result: No error  
 **/ %>
<!-- correct syntax is used in the declaration statement -->
<%! int i=627; %>
<% out.println(i); %>
</body>
</html>
