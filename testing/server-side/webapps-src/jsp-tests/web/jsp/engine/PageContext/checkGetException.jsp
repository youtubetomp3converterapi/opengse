<%@ page isErrorPage="true" %>
<html>
<title>checkGetException</title>
<body>
<%
 /*
  Name : checkGetException
 */
%> 


<!-- checking for getException method -->

<%= (pageContext.getException()) instanceof java.lang.Exception %>
<%= ( exception == pageContext.getException() ) %>

</body>
</html>


