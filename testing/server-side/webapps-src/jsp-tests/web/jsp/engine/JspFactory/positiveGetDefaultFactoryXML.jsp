
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveGetDefaultFactory</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** Name :positiveGetDefaultFactory
       Description : we call the static method getDefaultFactory
       Result : should return a non null value for the default factory 
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
]]></jsp:text>
<jsp:directive.page import="javax.servlet.jsp.*"/>
<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ JspFactory jf=JspFactory.getDefaultFactory(); 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ if(jf!=null) {
       out.println("it works"); 
   } else {
       out.println("it does not work");
   }

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html>

     
]]></jsp:text>

</jsp:root>