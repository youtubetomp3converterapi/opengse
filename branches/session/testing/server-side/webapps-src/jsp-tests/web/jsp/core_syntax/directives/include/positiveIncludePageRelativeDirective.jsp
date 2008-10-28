<html>
<title>positiveIncludePageRelative</title>
<body>
<% /** 	Name : positiveIncludePageRelative
	Description : Create a JSP page with the include directive using a 
	              page-relative path. The included 
	              page should exist in the desired location
	Result :Send out the HTML of the first page with the output of the 
	        second page and revert back to sending the first page once 
	        second page is complete.
**/ %>
<!-- testing if including of files work -->
<%@ include file = "includecommon.jsp" %>

</body>
</html>
