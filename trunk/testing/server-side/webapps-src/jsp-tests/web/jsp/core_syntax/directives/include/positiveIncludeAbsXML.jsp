
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveIncludeAbs</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : positiveIncludeAbs
	Description :Create a JSP page with the include attribute set for 
	             the jsp:request action with an absolute URL. The included
                     page should exist in the desired location 
	Result :Send out the HTML of the first page with the output of the 
	        second page and revert back to sending the first page once 
	        second page is complete.
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[	 
<!-- testing whether including works when we use absolute url relative to the-->
<!-- docroot of the application -->
]]></jsp:text>
<jsp:directive.include file= "/tests/core_syntax/directives/include/includedAbs.jsp" />
<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>