<html>
<title>positiveIncludeRel</title>
<body>
<% /** 	Name : positiveIncludeRel
	Description : Create a JSP page with the include attribute set for 
	              the jsp:request action with a relative URL. The included 
	              page should exist in the desired location
	Result :Send out the HTML of the first page with the output of the 
	        second page and revert back to sending the first page once 
	        second page is complete.
**/ %>	 
<!-- testing if including of files work -->
<%@ include file = "includecommon.html" %>
</body>
</html>
