<html>
<title>positiveGetString</title>
<body>
<% 
	/** 	
	Name : positiveGetString
	Description : Try to print the contents of BodyContent object 
				 using getString() method.
	Result :  Expected to print contents of BodyContent object. 
	**/  
%>	 

<%	
	// using pageContext.pushBody() to create a BodyContent object
	javax.servlet.jsp.tagext.BodyContent bc = pageContext.pushBody();
	bc.println("Checking For getString() method");
	out.println(bc.getString());
	pageContext.popBody();		
%>



</body>
</html>
