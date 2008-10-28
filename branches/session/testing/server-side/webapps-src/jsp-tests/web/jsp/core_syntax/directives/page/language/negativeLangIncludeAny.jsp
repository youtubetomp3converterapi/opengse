<html>
<title>negativeLangIncludeAny</title>
<body>
<% /**	Name: negativeLangIncludeAny
		Description: Create a page with the language attribute set to
			  "java" and with an include attribute. The included page
			  should have a jsp directive with language attribute not 
			  set to java.
		Result: Fatal translation error
**/ %>		
<!-- language directive set to java  -->
<!-- we include a page whose language directive set to something else-->
<!-- we expect translation failure  -->
<%@ page language="java" %>
<% out.println("hello"); %>
<%@ include file="included.jsp" %>
</body>
</html>