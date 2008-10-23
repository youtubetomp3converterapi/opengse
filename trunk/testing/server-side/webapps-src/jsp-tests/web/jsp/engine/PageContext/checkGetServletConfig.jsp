<html>
<title>checkGetServletConfig</title>
<body>
<%
/*
 Name : checkGetServletConfig
*/
%>
<!-- checking for getServletConfig method -->

<%= (pageContext.getServletConfig()) instanceof javax.servlet.ServletConfig %>
<%= ( config == pageContext.getServletConfig() ) %>

</body>
</html>


