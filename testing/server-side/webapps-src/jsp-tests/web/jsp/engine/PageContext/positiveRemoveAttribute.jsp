<html>
<title>positiveRemoveAttribute</title>
<body>
<%
/*
 Name : positiveRemoveAttribute
*/
%>
<!-- this is to test if removetAttribute() method works -->
<!-- using pageContext object to setAttribute -->

<% pageContext.setAttribute("Country","india"); %>

<!-- using pageContext object to removeAttribute "Name" attruibute -->
<% pageContext.removeAttribute("Country"); %>

<!-- Trying to access removed "Name" attribute by getAttribute() method-->
<% out.println(pageContext.getAttribute("Country")); %>

</body>
</html>

