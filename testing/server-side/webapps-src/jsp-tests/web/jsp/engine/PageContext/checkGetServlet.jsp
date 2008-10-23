<html>
<title>checkGetServlet</title>
<body>
<%
/*
 Name : checkGetServlet
*/
%>
<!-- checking for getServlet method -->

<%= (pageContext.getServlet()) instanceof javax.servlet.Servlet %>

</body>
</html>


