<html>
<title>positiveIncludeStaticRel</title>
<body>
<% /** 	Name : positiveIncludeStaticRel
	Description :We are testing for request time Static inclusion using
        <jsp:include page > tag. We test if we get the included static content
        got  included in the output.here we use relative path from current 
        context  as   url.
	Result :
**/ %>	 
<!-- testing Request-time static inclusion relative to from current context-->
<jsp:include page= "includecommon.html" />
</body>
</html>
