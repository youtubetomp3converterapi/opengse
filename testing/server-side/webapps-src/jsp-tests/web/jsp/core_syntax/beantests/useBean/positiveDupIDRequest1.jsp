<html>
<title>positiveDupIDRequest1</title>
<body>
<% /** 	Name : positiveDupIDRequest1
	Description :Try to create two useBean tags on different JSP pages
                     with same ID, but with scope attribute being set to
                     "request". 
	Result :Should not return an error.
**/ %>	 
<!-- Declaring the bean with out body -->
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.useBean.String_IntBean" />
<!-- accessing the bean thru a scriptlet  -->
<%
 out.println(myBean.getName());
%>
</body>
</html> 