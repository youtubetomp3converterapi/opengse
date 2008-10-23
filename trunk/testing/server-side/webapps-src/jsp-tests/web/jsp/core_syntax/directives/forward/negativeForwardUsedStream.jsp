<html>
<title>negativeForwardUsedStream</title>
<body>
<% /** 	Name : negativeForwardUsedStream
	Description :We are testing for forwarding to static pages using 
	<jsp:forward page > tag after writing to the stream. we should get 
	IllegalStateException
	Result :
**/ %>	 
<!-- testing whether forwarding works if we stream is already used -->
<% out.flush(); %>
<% try { %>
<jsp:forward page= "/tests/core_syntax/directives/include/includecommon.html" />
   <% }catch(IllegalStateException ie) {out.println("IllegalState"); } %>
</body>
</html>
