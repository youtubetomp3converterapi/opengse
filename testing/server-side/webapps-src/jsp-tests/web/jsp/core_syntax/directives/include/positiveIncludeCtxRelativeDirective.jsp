<html>
<title>positiveIncludeCtxRelativeDirective</title>
<body>
<% /** 	Name : positiveIncludeCtxRelativeDirective
	Description : Create a JSP page with the include directive using 
	              a context-relative path. The included 
	              page should exist in the desired location
	Result :Send out the HTML of the first page with the output of the 
	        second page and revert back to sending the first page once 
	        second page is complete.
**/ %>
<!-- testing if including of files work -->
<%@ include file = "/jsp/core_syntax/directives/include/includecommon.jsp" %>

</body>
</html>
