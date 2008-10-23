<html>
<title>checkGetOut</title>
<body>
<%
/*
 Name : checkGetOut
*/
%>
<!-- checking for getOut method -->

<%= (pageContext.getOut()) instanceof javax.servlet.jsp.JspWriter %>
<%= ( out == pageContext.getOut() ) %>



</body>
</html>


