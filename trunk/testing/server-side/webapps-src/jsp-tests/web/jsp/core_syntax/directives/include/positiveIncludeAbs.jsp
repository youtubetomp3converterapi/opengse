<html>
<title>positiveIncludeAbs</title>
<body>
<% /** 	Name : positiveIncludeAbs
	Description :Create a JSP page with the include attribute set for 
	             the jsp:request action with an absolute URL. The included
                     page should exist in the desired location 
	Result :Send out the HTML of the first page with the output of the 
	        second page and revert back to sending the first page once 
	        second page is complete.
**/ %>	 
<!-- testing whether including works when we use absolute url relative to the-->
<!-- docroot of the application -->
<%@ include file= "/tests/core_syntax/directives/include/includedAbs.jsp" %>
</body>
</html>