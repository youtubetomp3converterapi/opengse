<html>
<title>positivePageScope</title>
<body>
<% /** 	Name : positivePageScope
	Description : Create the JSP page with useBean scope set to page.
                      Load the CounterBean. Try to reload the page multiple
                      times.
	Result :Should return the initial value of the counter each time page 
		is loaded.
**/ %>	 
<!-- Declaring the bean with out body -->
<jsp:useBean id="myBean" scope="page" class="core_syntax.beantests.useBean.Counter" />
<!-- accessing the bean thru a scriptlet -->
<%
 out.println(myBean.getCount());
%>
</body>
</html> 
