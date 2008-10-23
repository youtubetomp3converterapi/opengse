<html>
<title>positiveNoBody</title>
<body>
<% /** 	Name : positiveNoBody
	Description : Try to create a valid useBean tag in the JSP page with
                      no body.
	Result :Should return the JSP page as it is to the browser.
**/ %>	 
<!-- Declaring the bean with out body -->
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.useBean.String_IntBean" />
<!-- accessing the bean thru a scriptlet -->
<%
 out.println(myBean.getName());
%>
</body>
</html> 
