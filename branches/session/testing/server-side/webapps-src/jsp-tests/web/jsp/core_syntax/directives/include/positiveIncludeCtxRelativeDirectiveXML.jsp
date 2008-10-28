
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveIncludeCtxRelativeDirective</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : positiveIncludeCtxRelativeDirective
	Description : Create a JSP page with the include directive using 
	              a context-relative path. The included 
	              page should exist in the desired location
	Result :Send out the HTML of the first page with the output of the 
	        second page and revert back to sending the first page once 
	        second page is complete.
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- testing if including of files work -->
]]></jsp:text>
<jsp:directive.include file = "/jsp/core_syntax/directives/include/includecommon.jsp" />
<jsp:text><![CDATA[

</body>
</html>
]]></jsp:text>

</jsp:root>