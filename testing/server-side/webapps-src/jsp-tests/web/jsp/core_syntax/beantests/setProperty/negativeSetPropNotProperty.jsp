<html>
<title>negativeSetPropNotProperty</title>
<body>
<% /** 	Name : negativeSetPropNotProperty
	Description : Accessing a non-existant property of an explicit bean 
                      through a getProperty action
	Result : Should return an error page.
**/ %>	 
<!-- Declaring the bean with out body -->
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.setProperty.SetpropBean" />
<jsp:setProperty name="myBean" property="foo" value="change" />
</body></body>
</html> 
