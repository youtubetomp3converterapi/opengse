<html>
<title>positiveSetAttribute</title>
<body>
<%
/*
 Name : positiveSetAttribute
*/
%>
<!-- this is to test if setAttribute() method works -->
<!-- using pageContext object to setAttribute -->
<% pageContext.setAttribute("Country","india"); %>
<!-- using pageContext object to getAttribute, to obtain value of "Country"-->
<% pageContext.getAttribute("Country"); %>
<% out.println(pageContext.getAttribute("Country")); %>

</body>
</html>

