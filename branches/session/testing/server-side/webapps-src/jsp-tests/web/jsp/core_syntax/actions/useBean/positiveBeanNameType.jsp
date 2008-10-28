<html>
<title>positiveBeanNameType</title>
<body>
<% /** 	Name : positiveBeanNameType
	Description : Declaring the bean using beanName.  
    When using beanName, the bean can either be a serialized
    Object, or a fully qualified class. 
    This test uses a fully qualfied class with the same type.
	Result :We should get page output without error
**/ %>
<!-- Declaring the bean with beanName as a class -->
<jsp:useBean id="myBean" scope="request"
class="core_syntax.actions.useBean.NewCounter" 
type="core_syntax.actions.useBean.NewCounter" />
<!-- accessing the bean thru a scriptlet -->
<%
 out.println(myBean.getCount());
%>
</body>
</html>
