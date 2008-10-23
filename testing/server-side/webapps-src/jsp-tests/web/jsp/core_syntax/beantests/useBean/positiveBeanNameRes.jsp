<html>
<title>positiveBeanNameRes</title>
<body>
<% /** 	Name : positiveBeanNameRes
	Description : Declaring the bean with beanName as a resource file
	Result :We should get page output without error
**/ %>	 
<!-- Declaring the bean with beanName as a resource -->
<jsp:useBean id="myBean" scope="request"
beanName="core_syntax.beantests.useBean.NewCounter"
type="core_syntax.beantests.useBean.NewCounter" />

<!-- accessing the bean thru a scriptlet -->
<%
 out.println(myBean.getCount());
%>
</body>
</html> 
