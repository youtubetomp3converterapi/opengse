<html>
<title>positiveUseBeanInit</title>
<body>
<% /** 	Name : positiveUseBeanInit
	Description : Create a valid useBean action. In the body of the action, 
		      put setProperty statements to set specific properties of 
		      the bean.Ensure that the bean class does not exist before 
		      calling this page. Call the page for the first time.
	Result :Should return the value of the property that was set in the
                useBean body.
**/ %>	 
<!-- Declaring the bean   -->
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.setProperty.SetpropBean" />
<jsp:setProperty name="myBean" property="name" value="Tony" />

<!-- Accessing the property thru a scriptlet -->
<%
out.println(myBean.getName());
%>
</body>
</html> 
