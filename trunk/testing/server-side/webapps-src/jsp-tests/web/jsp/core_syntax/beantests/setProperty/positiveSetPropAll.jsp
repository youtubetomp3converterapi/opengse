<html>
<title>positiveSetPropAll</title>
<body>
<% /** 	Name : positiveSetPropAll
	Description : Create a valid useBean action in JSP. Set all properties 
		      of that bean from the request using a 
		      <jsp:setProperty property="*"> action. Then access all 
		      properties of the bean.
	Result : As we are setting "param as Name=MANGO,num=90336,str=hello,"
		It should return "hello,MANGO,90336".
**/ %>	 
<!-- Declaring the bean with out body -->
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.setProperty.SetpropBean" />
<jsp:setProperty name="myBean" property="*" />
<!-- Accessing the properties thru a scriptlet -->
<% out.println(myBean.getStr()); %> <br> <% out.println(myBean.getName()); %>
<br> <% out.println(myBean.getNum()); %>
</body>
</html> 
