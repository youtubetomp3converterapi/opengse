
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveBodyExists</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /**  Name : positiveBodyExists
    Description : Try to create a valid useBean tag in the JSP page with a
                      non-empty body and with a class name that has already
                      been created.
    Result :Should return the page contents except what is contained within
                the body.
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- Declaring the bean with body -->
]]></jsp:text>
<jsp:useBean id="myBean" scope="request" class="core_syntax.beantests.useBean.String_IntBean">
<jsp:text><![CDATA[
]]></jsp:text>
<jsp:setProperty name="myBean" property="name" value="APPLE" />
<jsp:text><![CDATA[
]]></jsp:text>
</jsp:useBean>
<jsp:text><![CDATA[
]]></jsp:text>
<jsp:getProperty name="myBean" property="name" />
<jsp:text><![CDATA[
</body>
</html> 
]]></jsp:text>

</jsp:root>