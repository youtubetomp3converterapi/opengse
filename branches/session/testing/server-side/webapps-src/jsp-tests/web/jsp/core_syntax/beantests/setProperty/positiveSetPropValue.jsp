<html>
<title>positiveSetPropValue</title>
<body>
<% /** 	Name : positiveSetPropValue
	Description : Create a valid useBean action in JSP. Set a specific 
		      property of that bean from the request using a 
		      <jsp:setProperty value="propValue"> action. 
		      Then access that property.
	Result : Should return the new value of that property.
**/ %>	 

<!-- Declaring the bean with out body -->
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.setProperty.SetpropBean" />
<jsp:setProperty name="myBean" property="name" value="KHATTA-MEETHA" />
<!-- Accessing the property thru a scriptlet -->
<%
out.println(myBean.getName());
%>
</body>
</html> 
