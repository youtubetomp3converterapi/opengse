<html>
<title>negativeDeclarationSyn </title>
<body>
<% /**  Name:negativeDeclarationSyn
		Description:Create a JSP page which has a syntax 
	   			error in the declaration statement .try to use 
	    		the declared variable in the rest of the page
		Result:Fatal Translation Error (Undefined)
**/ %>  	 	
<!-- error syntax is used in the declaration statement -->
<%! int i=627; > 
<% out.println(i); %>
</body>
</html>