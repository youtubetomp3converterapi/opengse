<html>
<title>positiveIncludeDynamicRel</title>
<body>
<% /** 	Name : positiveIncludeDynamicRel
	Description : We are testing for request time dynamic inclusion using
        <jsp:include page > tag. We test if we get the included jsp got
        parsed at request time and included to the output. we use relative
        path from current context as url
	Result :
**/ %>	 
<!-- Request-time Dynamic inclusion url relative to current context. -->
<jsp:include page= "includecommon.jsp" />
</body>
</html>