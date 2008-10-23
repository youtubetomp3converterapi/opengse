
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveIncludePageRelative</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : positiveIncludePageRelative
	Description : Create a JSP page with the include directive using a 
	              page-relative path. The included 
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
<jsp:directive.include file = "includecommon.jsp" />
<jsp:text><![CDATA[

</body>
</html>
]]></jsp:text>

</jsp:root>