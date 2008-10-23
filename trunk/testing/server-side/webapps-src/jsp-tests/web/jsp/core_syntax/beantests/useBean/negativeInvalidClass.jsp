<html>
<title>negativeInvalidClass</title>
<body>
<% /** 	Name : negativeInvalidClass
	Description : Try to create a JSP with useBean tag that contains a
                      non-implicit class reference and no source file for that
                      class.
	Result :Fatal Translation Error
**/ %>	 
<!-- Declaring the not available bean with out body -->
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.useBean.NotAvbl" />
<!-- accessing the bean thru a scriptlet -->
<%
 out.println(myBean.getCount());
%>
</body>
</html> 
