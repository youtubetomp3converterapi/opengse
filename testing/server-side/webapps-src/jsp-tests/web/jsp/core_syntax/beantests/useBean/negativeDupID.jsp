<html>
<title>negativeDupID</title>
<body>
<% /** 	Name : negativeDupID
	Description : Try to create two useBean tags in the same JSP page
                      with same ID.
	Result :Should return fatal translation error.
**/ %>	 <!-- Declaring the bean with out body -->
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.useBean.String_IntBean" />
<!-- Declaring the bean with out body.Here same name is used for the declaration -->
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.useBean.IntBean2" />
<!-- accessing the bean thru a scriptlet  -->
<%
 out.println(myBean.getName());
%>
</body>
</html> 
