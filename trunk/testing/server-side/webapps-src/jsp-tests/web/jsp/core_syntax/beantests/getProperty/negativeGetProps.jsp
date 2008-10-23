<html>
<title>negativeGetProps</title>
<body>
<% /** 	Name : negativeGetProps
	Description : Access any property of a non-implicit bean through a
                      getProperty action without first declaring a corresponding
                      useBean action for that page.
	Result : Should return an error page.
**/ %>	 
<!-- accessing the bean thru a scriptlet before the declaration -->
<%
 out.println(myBean.getName());
%>
<!-- Declaring the bean with out body -->
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.getProperty.StringBean" />
</body>
</html> 
