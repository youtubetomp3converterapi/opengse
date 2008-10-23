<html>
<title>errorpage</title>
<body>
<!-- this is errorpage for negativeBuffOverflow -->
<%@ page isErrorPage="true" %>
<% out.println("This is overflow error page"); %>
<% out.println(exception.getMessage()); %>
</body>
</html>