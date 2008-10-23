<html>
<title>negativeBeanName</title>
<body>
<!-- Declaring the bean with out body -->
<%@ page errorPage="errorPage.jsp" %>
<jsp:useBean id="myBean"  beanName="core_syntax.beantests.useBean.NCounter" 
type="core_syntax.beantests.useBean.NewCounter" />
<!-- accessing the bean thru a scriptlet -->
<%
 out.println(myBean.getCount());
%>
</body>
</html> 
