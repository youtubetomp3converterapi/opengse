<html>
<title>positiveBodyNew</title>
<body>
<% /** 	Name : positiveBodyNew
	Description : Try to create a valid useBean tag in the JSP page with a
                      non-empty body. Ensure that the classname specified
                      does not exist.
	Result :Should return the contents of the HTML page along with what
                exists in the body.
**/ %>	 
<!-- Declaring the bean with body -->
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.useBean.String_IntBean" >
<jsp:setProperty name="myBean" property="name" value="APPLE" />

<jsp:getProperty name="myBean" property="name" />
</jsp:useBean>
</body>
</html> 
