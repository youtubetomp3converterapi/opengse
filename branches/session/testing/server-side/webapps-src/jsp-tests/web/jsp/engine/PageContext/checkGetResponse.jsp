<html>
<title>checkGetResponse</title>
<body>
<%
/*
 Name : checkGetResponse
*/
%>
<!-- checking for getResponse method -->

<%= (pageContext.getResponse()) instanceof javax.servlet.ServletResponse %>
<%= ( response == pageContext.getResponse() ) %>

</body>
</html>
