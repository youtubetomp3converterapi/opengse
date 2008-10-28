<html>
<title>negativeClearUnbuffered</title>
<body>
<%
/*
 Name : negativeClearUnbuffered
 Description : Make the JSP page unbuffered, using the directive, buffer="none". 
 Now try to use the clear() method.
*/
%>
<!-- This is to test if clear() method throws IllegalStateException if buffer is none -->
<%@ page buffer="none" %>
<% out.println("hello"); %>
<% try { out.clear(); }catch(IllegalStateException ise) { %>
<% out.println("we got Illegal state Exception"); }%>
</body>
</html>
