<html>
<title>positiveIsAutoFlush</title>
<body>
<%
/*
 Name : positiveIsAutoFlush
 Description : Call isAutoFlush and verify the default is 'true'
*/
%>
<!-- this is to test positiveIsAutoFlush -->

<!-- using isAutoFlush() method to get check flush state -->
<%= out.isAutoFlush() %>
<!-- expected to return true  -->
</body>
</html>
