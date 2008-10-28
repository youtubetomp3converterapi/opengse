<html>
<title>negativeGetPropNotRead</title>
<body>
<% /** 	Name : negativeGetPropNotRead
	Description : Accessing a write-only property of an explicit bean 
                      through a getProperty action
	Result : Should return an error page.
**/ %>	 
<!-- Declaring the bean with out body -->
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.getProperty.StringBean" />
<jsp:getProperty name="myBean" property="bar" />
</body>
</html> 
