
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>negativeDuplicateIDFatalTranslation</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : negativeDuplicateIDFatalTranslation
	Description : Verify that duplicate useBean id's
                  within the same translation unit yeild
                  a fatal translation error.
	Result : Fatal translation error
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- Declare two beans with the same id -->
]]></jsp:text>
<jsp:useBean id="myBean" scope="page" class="core_syntax.actions.useBean.String_IntBean" />
<jsp:text><![CDATA[
]]></jsp:text>
<jsp:useBean id="myBean" scope="page" class="core_syntax.actions.useBean.String_IntBean" />
<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>