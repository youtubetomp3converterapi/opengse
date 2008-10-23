<html>
<title>positiveIncludeStaticAbs</title>
<body>
<% /** 	Name : positiveIncludeStaticAbs
	Description : We are testing for request time Static inclusion using
        <jsp:include page > tag. We test if we get the included static content
        got  included in the output.here we use absolute path from doc root as 
        url.
	Result :
**/ %>	 
<!-- testing Request-time static inclusion with absolute url from docroot -->
<jsp:include page= "/tests/core_syntax/directives/include/includecommon.html" />
</body>
</html>
