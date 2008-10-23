
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveExtends</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 
       Name : positiveExtends
       Description: we use extends directive to point our own defined class
                    as super class to the jsp
       Result: we should get true to the expression after the directive
  */ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
]]></jsp:text>
<jsp:directive.page extends="core_syntax.directives.page.extend.SuperPage" />
<jsp:text><![CDATA[
]]></jsp:text>

<jsp:expression>
<![CDATA[ (this instanceof core_syntax.directives.page.extend.SuperPage ) ]]>

</jsp:expression>

<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>