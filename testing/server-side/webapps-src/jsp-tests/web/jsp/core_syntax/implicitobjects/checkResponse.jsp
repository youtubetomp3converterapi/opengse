<html>
<title>checkResponse</title>
<body>
<% /**  Name:checkResponse
        Description: Checks whether response is an object
             of type javax.servlet.ServletResponse. Then verify
             that a method can be called against the response object.
**/ %>
<!-- checking for response object type -->
<%= (response instanceof javax.servlet.ServletResponse) %><br>
<%
    response.addHeader("TestHeader", "Method call OK");
%>

</body>
</html>
