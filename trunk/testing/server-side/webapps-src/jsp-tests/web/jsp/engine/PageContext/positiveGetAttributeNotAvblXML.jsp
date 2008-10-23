
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveGetAttributeNotAvbl</title>
<body>
]]></jsp:text>
<jsp:useBean id="beans" class="engine.PageContext.TestBean" />
<jsp:text><![CDATA[
<!-- calling the getAttribute method in a scriptlet -->
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
try{
     Object o=pageContext.getAttribute("rm");
     if(o == null)
       out.println("SUCCESS");
     else
       out.println("FAIL");
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