<html>
<title>positiveGetProps</title>
<body>
<% /** 	Name : positiveGetProps
	Description : Create a valid useBean tag in the JSP. Access one of its
                      properties through a getProperty tag.
	Result : Should return the value of the property as part of the returned 
	HTML 
**/  %>
<!-- Declaring the bean with out body -->
<jsp:useBean id="myBean" scope="request" class="core_syntax.actions.getProperty.StringBean" />
<jsp:getProperty name="myBean" property="name" />
</body>
</html>
