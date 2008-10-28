<html>
<title>positiveGetAttributeScope</title>
<body>
<%
/*
 Name : positiveGetAttributeScope
*/
%>
<!-- this is to test if getAttributeScope() method works -->
<!-- using pageContext object to getAttributeScope -->
<% pageContext.setAttribute("Country","india",1); %>
<!-- using pageContext object to getAttribute, to obtain value of "Country"-->
<% pageContext.getAttributesScope("Country"); %>
<% out.println(pageContext.getAttributesScope("Country")); %>

</body>
</html>
