<html>
<title>positiveUseBeanNext</title>
<body>
<% /** 	Name : positiveUseBeanNext
	Description : Create a valid useBean action. In the body of the action, 
		      put setProperty statements to set specific properties of 
		      the bean.Ensure that the bean already exists. Call the 
		      page and check the value of the property that you have 
		      specified in the useBean body.
	Result :Should return the non-initial value of the property.
**/ %>	 
<!-- Declaring the bean with out body -->
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.setProperty.SetpropBean" />
<jsp:setProperty name="myBean" property="num" value="90336" />
<jsp:setProperty name="myBean" property="str" value="APPLE" />
<!-- Accessing the property thru a scriptlet -->
<%
out.println(myBean.getNum());
out.println(myBean.getStr());
%>
</body>
</html> 
