<html>
<title>negativeNoBody</title>
<body>
<% /** 	Name : negativeNoBody
	Description : Try to create an invalid useBean tag 
	              (i.e. tag that is not closed)
	Result :Should return a fatal translation error. 
**/ %>	 <!-- Declaring the bean with body -->
<!-- Here the tag is not closed. Hence bound for error -->
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.useBean.String_IntBean" >
<jsp:getProperty name="myBean" property="Name" />
</body>
</html> 
