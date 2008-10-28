<html>
<title>negativeLangAny</title>
<body>
<% /**	Name: NegativeLangAny
		Description:Create two jsp directives with the language
			  attribute set to "java" in the first case and anything 
			  other than "java" in the second.
		Result: Fatal translation error
**/ %>		
<!-- language directive set to java & something else  -->
<!-- we expect translation failure  -->
<%@ page language="java" errorpage="myerror.jsp" %>
<%@ page language="javascript" %>
hello
</body>
</html>
