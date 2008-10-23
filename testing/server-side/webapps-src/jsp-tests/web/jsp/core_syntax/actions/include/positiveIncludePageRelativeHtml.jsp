<html>
<title>positiveIncludePageRelativeHtml</title>
<body>
<% /** 	Name : positiveIncludePageRelativeHtml
	Description :We are testing for request time Static inclusion using
        <jsp:include page > tag. We test if we get the included static content
        got  included in the output.here we use relative path from current 
        context  as   url.
	Result :
**/ %>
<!-- testing static inclusion of html relative to from current context-->
<jsp:include page= "includecommon.html" flush="true" />
</body>
</html>
