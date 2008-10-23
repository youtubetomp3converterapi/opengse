<html>
<title>negativeLangIncludeJava</title>
<body>
<% /**	Name: negativeLangIncludeJava
		Description: Create a page with the language attribute set
			  to java and with an include attribute. The included page 
			  should have a jsp directive with language attribute set to
			  java.
		Result:Fatal translation error
**/ %>		
<!-- language directive set to java  -->
<!-- we include a page whose language directive set to java -->
<!-- we expect translation failure since we set directive of language twice -->
<%@ page language="java" %>
<% out.println("hello"); %>
<%@ include file="includedJava.jsp" %>
</body>
</html>