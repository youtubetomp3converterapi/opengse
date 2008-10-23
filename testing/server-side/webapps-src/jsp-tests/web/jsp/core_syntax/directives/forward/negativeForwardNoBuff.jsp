<html>
<title>negativeForwardNoBuff</title>
<body>
<% /** 	Name : negativeForwardNoBuff
	Description :We are testing for forwarding to static pages using
        <jsp:forward page > tag without a buffer. we should get 
        IllegalStateException
	Result :
**/ %>	 
<!-- testing whether forwarding works if we stream is unbuffered-->
<%@ page buffer="none"  %>
<% try { %>
<jsp:forward page= "/tests/core_syntax/directives/include/includecommon.html" />
   <% }catch(IllegalStateException ie) {out.println("IllegalState"); } %>
</body>
</html>
