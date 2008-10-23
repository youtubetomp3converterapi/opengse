<html>
<title>myerrorpage</title>
<body>
<!-- error page for positiveErrorPage.jsp -->
<%@ page isErrorPage="true"  %>
<% out.println("This is a test error page"); %>
<% out.println("Exception got is: " + exception.getMessage()); %>
</body>
</html>
