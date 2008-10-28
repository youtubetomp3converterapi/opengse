<html>
<title>positiveNoBody</title>
<body>
<% /** 	Name : positiveNoBody
	Description : Create a new instance of a bean with no body.
                  Access the name property.
	Result : Returns the default instance value of the name property: hello.
**/ %>
<!-- Declaring the bean with out body -->
<jsp:useBean id="myBean" scope="request" class="core_syntax.actions.useBean.String_IntBean" />
<jsp:getProperty name="myBean" property="name" />
</body>
</html>
