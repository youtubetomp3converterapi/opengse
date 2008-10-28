<html>
<title>negativeRemoveAttribute</title>
<body>
<%
/*
 Name : negativeRemoveAttribute
 Description : Trying to remove an unavailable attribute.
 Expected to get null.
*/
%>
<!-- this is to test if removetAttribute() method works -->
<!-- using pageContext object to setAttribute -->

<% pageContext.setAttribute("Country","india"); %>

<!-- using pageContext object to removeAttribute unavailable attruibute -->
<% pageContext.removeAttribute("State"); %>

<!-- Trying to access removed  attribute by getAttribute() method-->
<% out.println(pageContext.getAttribute("State")); %>

</body>
</html>