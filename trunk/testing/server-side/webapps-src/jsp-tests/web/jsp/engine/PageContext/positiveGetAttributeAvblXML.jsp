
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveGetAttributeAvbl</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
/*
 Name : positiveGetAttributeAvbl
 Description : Set an attribute(like useBean), 
 and then call the method getAttribute() to get it.
*/

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
]]></jsp:text>
<jsp:useBean id="beans" class="engine.PageContext.TestBean" />
<jsp:text><![CDATA[
<!-- calling the getAttribute method in a scriptlet -->
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
try{
     Object o=pageContext.getAttribute("beans");
     out.println(o.getClass().getName());
 }catch(Exception e){
     out.println(e);
 }

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html>

]]></jsp:text>

</jsp:root>