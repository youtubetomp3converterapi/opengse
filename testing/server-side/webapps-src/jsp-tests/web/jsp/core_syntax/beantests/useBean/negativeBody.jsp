<html>
<title>negativeBody</title>
<body>
<% /** 	Name : negativeBody
	Description : Try to create an invalid useBean tag with body (i.e. the
                      tag has a body, but the closing tag does not exist)
	Result :Should return fatal translation error.
**/ %>	 <!-- Declaring the bean with body -->
<!-- Here the bean tag is not closed -->
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.useBean.String_IntBean" >
<jsp:setProperty name="myBean" property="Name" value="APPLE" />
<jsp:getProperty name="myBean" property="Name" />
</body>
</html> 
