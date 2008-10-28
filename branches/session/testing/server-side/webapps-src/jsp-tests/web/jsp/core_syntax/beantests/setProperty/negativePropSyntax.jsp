<html>
<title>negativePropSyntax</title>
<body>
<% /** 	Name : negativePropSyntax
	Description : Create a valid useBean action in JSP. Use both a param 
		      and a value attribute in the setProperty action.
	Result : Fatal Translation Error
**/ %>	 
<%@ page errorpage="Errorpage.jsp" %>
<!-- Declaring the bean with out body -->
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.setProperty.SetpropBean" />
<!-- Setting the property.. here the syntax is misused -->
<jsp:setProperty name="myBean" property="*" value="Kash" />
<!-- Accessing the properties thru a scriptlet -->
<% out.println(myBean.getName()); %> 
</body>
</html>
