<html>
<title>positiveSetPropNoParam</title>
<body>
<% /** 	Name : positiveSetPropNoParam
	Description : Create a valid useBean action in JSP. Set a specific 
	              property of that bean from the request using a 
	              <jsp:setProperty property="propName"> action. Then access 
	              that property.Ensure that the request contains a parameter 
	              with the same name as the Bean Name.
	Result :As we are setting "param as Str=SAPPOTA"
		It should return "SAPPOTA".
**/ %>
<!-- Declaring the bean with body -->
<jsp:useBean id="myBean" scope="request" class="core_syntax.actions.setProperty.SetpropBean">
<jsp:setProperty name="myBean" property="str" />
</jsp:useBean>
<!-- Accessing the property thru a scriptlet -->

<%
out.println(myBean.getStr());
%>
</body>
</html>
