<html>
<title>positiveIncludeCtxRelativeHtml</title>
<body>
<% /** 	Name : positiveIncludeCtxRelativeHtml
	Description :We are testing for static inclusion of html page using
        <jsp:include page > tag. We test if we get the included static content
        got  included in the output. Here we use absolute path from current 
        context  as   url.
**/ %>
<!-- testing absolute inclusion of html relative to from current context-->
<jsp:include page= "/jsp/core_syntax/actions/include/includecommon.html" flush="true" />
</body>
</html>
