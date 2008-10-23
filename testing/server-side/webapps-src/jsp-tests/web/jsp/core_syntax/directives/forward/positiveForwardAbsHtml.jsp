<html>
<title>positiveForwardAbsHtml</title>
<body>
<% /** 	Name : positiveForwardAbsHtml
	Description :We are testing for forwarding to static pages using
        <jsp:forward page > tag. here we use absolute path from doc root as 
        url.
	Result :
**/ %>	 

<!-- testing whether forwarding works using absolute url relative to doc root-->
<jsp:forward page= "/tests/core_syntax/directives/forward/includecommon.html" />
</body>
</html>
