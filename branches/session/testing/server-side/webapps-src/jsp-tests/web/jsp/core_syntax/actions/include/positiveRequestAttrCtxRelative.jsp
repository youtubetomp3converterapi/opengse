<html>
<title>positiveRequestAttrCtxRelative</title>
<body>
<% /** 	Name : positiveRequestAttrCtxRelative
	Description :     We are testing for request time dynamic inclusion 
	using <jsp:include page > tag. We test if we get the included jsp got
        parsed at request time and included to the output. we use absolute
        path from contex-root root as url
**/ %>
<% String test="/jsp/core_syntax/actions/include/includecommon"; %>
<!-- Request-time Dynamic inclusion,with absolute url from context-root -->
<jsp:include page= '<%= test+".jsp" %>' flush="true" />
</body>
</html>
