<html>
<title>positiveGetRemainingBufferUnset</title>
<body>
<%
/*
 Name : positiveGetRemainingBufferUnset
 Description :Set the buffer directive to 'none' and call
the method getRemaining() in the JSP page.
*/
%>
<!-- this is to test positiveGetRemainingBufferUnset  -->
<!-- setting buffer size to none -->
<%@ page buffer="none" %>

<!-- using getRemaining() method to get the buffer size -->

<%= out.getRemaining() %>
<!-- expected to return 0  -->
</body>
</html>
