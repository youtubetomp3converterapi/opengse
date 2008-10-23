<html>
<title>positiveDupIDPage1</title>
<body>
<% /** 	Name : positiveDupIDPage1
	Description :Try to create two useBean tags on different JSP pages
                     with the same ID, and the scope attribute of each being
                     set to "page". Access each page one after the other. 
	Result :Should not return an error
**/ %>	 
<!-- Declaring the bean with out body -->
<jsp:useBean id="myBean" scope="page" class="core_syntax.beantests.useBean.String_IntBean" />
<!-- accessing the bean thru a scriptlet  -->
<%
 out.println(myBean.getName());
%>
</body>
</html> 