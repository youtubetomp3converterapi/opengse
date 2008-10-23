<html>
<title>errorpage</title>
<body>
<%@ page isErrorPage="false" %>
<% out.println("This is test error page"); %>
<% out.println(exception.getMessage()); %>
</body>
</html>