<html>
<title>positiveSetPropAll</title>
<body>
<% /** 	Name : positiveSetPropAll
	Description : Create a valid useBean action in JSP. Set all properties 
		          of that bean from the request using a 
		          <jsp:setProperty property="*"> action. Then access all 
		          properties of the bean.
	Result : The client request will set name=Frodo, num=116165, str=Validated, as
             a result, these values should be displayed when accessed.
**/ %>
<!-- Declaring the bean with out body -->
<jsp:useBean id="myBean" scope="request" class="core_syntax.actions.setProperty.SetpropBean" />
<jsp:setProperty name="myBean" property="*" />
<!-- Accessing the properties thru a scriptlet -->
<% out.print(myBean.getStr()); %><br>
<% out.print(myBean.getName()); %><br>
<% out.print(myBean.getNum()); %><br>

</body>
</html>
