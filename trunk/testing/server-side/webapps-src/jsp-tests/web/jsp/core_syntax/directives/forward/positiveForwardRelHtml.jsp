<html>
<title>positiveForwardRelHtml</title>
<body>
<% /** 	Name : positiveForwardRelHtml
	Description :We are testing for forwarding to static pages using 
	<jsp:forward page > tag. here we use relative path from current 
        context  as   url.
	Result : we expect the forwarded page to come
**/ %>	 

<!-- testing whether forwarding works using url relative to current context-->
<jsp:forward page= "includecommon.html" />
</body>
</html>
