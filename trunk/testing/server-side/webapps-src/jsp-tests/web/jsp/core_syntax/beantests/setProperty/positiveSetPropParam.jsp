<html>
<title>positiveSetPropParam</title>
<body>
<% /** 	Name : positiveSetPropParam
	Description : Create a valid useBean action in JSP. Set a specific 
		      property of that bean from the request using a 
		      <jsp:setPropertyparam="paramname> action. 
		      Then access that property.
	Result : As we are setting "param as Name=MANGO", It should 
		 return "MANGO".
**/ %>	 
<!-- Declaring the bean with out body -->
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.setProperty.SetpropBean" />
<jsp:setProperty name="myBean" property="name" param="Name" />
<!-- Accessing the property thru a scriptlet -->
<%
out.println(myBean.getName());
%>
</body>
</html> 
