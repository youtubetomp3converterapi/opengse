
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveNoBody</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : positiveNoBody
	Description : Create a new instance of a bean with no body.
                  Access the name property.
	Result : Returns the default instance value of the name property: hello.
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- Declaring the bean with out body -->
]]></jsp:text>
<jsp:useBean id="myBean" scope="request" class="core_syntax.actions.useBean.String_IntBean" />
<jsp:text><![CDATA[
]]></jsp:text>
<jsp:getProperty name="myBean" property="name" />
<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>