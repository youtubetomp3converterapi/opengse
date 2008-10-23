<html>
<title>negativeLangJava</title>
<body>
<% /**	Name:negativeLangJava
		Description: Create two jsp directives on the same page
			 with language attribute set to "java" in both cases
		Result:Fatal translation error
**/ %>		
<!-- test if language directive set to java twice cause translation failure -->
<%@ page language="java" %>
<%@ page language="java" %>
hello
</body>
</html>