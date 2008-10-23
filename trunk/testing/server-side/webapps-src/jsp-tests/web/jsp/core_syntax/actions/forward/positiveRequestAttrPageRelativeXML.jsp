
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveRequestAttrRelative</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /**  Name : positiveRequestAttrPageRelative
        Description : We check if request time attributes work by forwarding
                      the request using a page-relative path.
        Result : we should get the forwarded file without error
 **/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ String test="forwardcommon"; 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
]]></jsp:text>
<jsp:forward page= '%= test+".jsp" %' />
<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>