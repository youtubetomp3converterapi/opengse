<html>
<title>negativeDupIDSession2</title>
<body>
<% /** 	Name : negativeDupIDSession2
	Description :Try to create two useBean tags on different JSP pages
                     with same ID, but with scope attribute being set to
                     "session". 
	Result :Fatal Translation Error.
**/ %>	 
<!-- Declaring the bean with out body -->
<jsp:useBean id="myBean" scope="session" class="core_syntax.beantests.useBean.Counter" />
<!-- accessing the bean thru a scriptlet  -->
<%
 out.println(myBean.getCount());
%>
</body>
</html> 