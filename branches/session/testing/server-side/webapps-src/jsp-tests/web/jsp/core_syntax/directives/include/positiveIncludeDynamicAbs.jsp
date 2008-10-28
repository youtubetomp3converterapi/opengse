<html>
<title>positiveIncludeDynamicAbs</title>
<body>
<% /** 	Name : positiveIncludeDynamicAbs
	Description :     We are testing for request time dynamic inclusion 
	using <jsp:include page > tag. We test if we get the included jsp got
        parsed at request time and included to the output. we use absolute
        path from doc root as url
        Result :
**/ %>	 
<!-- Request-time Dynamic inclusion,with absolute url from doc root -->
<jsp:include page= "/tests/core_syntax/directives/include/includecommon.jsp" />
</body>
</html>