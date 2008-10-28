<html>
<title>negativeNonJava</title>
<body>
<% /**	Name: negativeNonJava
		Description:Create a page with a scriptlet (before a jsp directive).
			  then put a jsp directive after the scriptlet with the language
			  not set to java
		Result: fatal translation error
**/ %>		
<!-- language directive set to something other than java after a scriptlet -->
<!-- we expect translation failure  -->
<% out.println("hello"); %>
<%@ page language="javascript" %>
hello
</body>
</html>