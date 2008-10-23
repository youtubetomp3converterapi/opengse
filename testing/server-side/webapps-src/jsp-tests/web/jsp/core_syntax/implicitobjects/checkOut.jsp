<html>
<title>checkOut</title>
<body>
<% /** Name: checkOut
	   Description: Checking 'out' is of the type
	   		 javax.servlet.jsp.JspWriter
	   Result: true	   	
**/ %>
<!-- checking for out object type -->
<%= (out instanceof javax.servlet.jsp.JspWriter ) %>
</body>
</html>
