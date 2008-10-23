<html>
<title>negativeGetPropNotProperty</title>
<body>
<% /** 	Name : negativeGetPropNotProperty
	Description : Accessing a non-existant property of an explicit bean 
                      through a getProperty action
	Result : Should return an error page.
**/ %>	 
<!-- Declaring the bean with out body -->
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.getProperty.StringBean" />
<jsp:getProperty name="myBean" property="foo" />
</body></body>
</html> 
