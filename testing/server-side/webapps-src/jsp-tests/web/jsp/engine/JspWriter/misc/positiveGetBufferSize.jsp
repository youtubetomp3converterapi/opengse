<html>
<title>positiveGetBufferSize</title>
<body>
<%
/*
 Name : positiveGetBufferSize
 Description : Set the buffer size with the buffer directive,
 then use the getBufferSize() method.
 */
 %>
<!-- this is to test if getBufferSize method works -->
<!-- setting buffer size to 5kb using directive -->
<%@ page buffer="5kb" %>
<!-- calling getBufferSize() method -->
<% if (out.getBufferSize() >= 5120) {%>
true
<% } else { %>
false
<% } %>
<!-- expected to return true --> 
</body>
</html>
