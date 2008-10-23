<html>
<title>positiveForwardAbshtml.jsp</title>
<body>
<% /** 	Name : positiveForwardCtxRelativeHtml.jsp
	Description :We are testing for forwarding to a html page using
        <jsp:forward page > tag. We test if the forwarded static content
        got  forwarded in the output. Here we use a context-relative path.
**/ %>	 
<!-- testing context-relative forwarding to an HTML page -->
<jsp:forward page= "/jsp/core_syntax/actions/forward/forwardcommon.html" />
</body>
</html>
