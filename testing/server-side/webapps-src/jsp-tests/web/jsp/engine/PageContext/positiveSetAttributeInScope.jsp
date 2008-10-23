<html>
<title>positiveSetAttributeInScope</title>
<body>
<%
/*
 Name : positiveSetAttributeInScope
*/
%>
<!-- this is to test if setAttribute() method works -->
<!-- using pageContext object to setAttribute -->
<% pageContext.setAttribute("Country","india",1); %>
<!-- using pageContext object to getAttributeScope, 
to obtain value of "Country"-->
<% pageContext.getAttributesScope("Country"); %>
<% out.println(pageContext.getAttributesScope("Country")); %>
</body>
</html>
