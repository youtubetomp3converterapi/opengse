<html>
<title>negativeSetPropNotWrite</title>
<body>
<% /** 	Name : negativeSetPropNotWrite
	Description : Accessing a read-only property of an explicit bean 
                      through a getProperty action
	Result : Should return an error page.
**/ %>	 
<!-- Declaring the bean with out body -->
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.setProperty.SetpropBean" />
<jsp:setProperty name="myBean" property="bar" value="change" />
</body>
</html> 
