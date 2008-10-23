<html>
<title>checkGetRequest</title>
<body>
<%
/*
 Name : checkGetRequest
*/
%>
<!-- checking for getRequest method -->



<%= pageContext.getRequest() instanceof javax.servlet.ServletRequest %>
<%= ( request == pageContext.getRequest() ) %>


</body>
</html>
