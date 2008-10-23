
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>negativeInclude</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
/*
 Name : negativeInclude
*/

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- this is to test if include() method works -->
<!-- using pageContext object to include -->
<!-- we trying call a non extisting file by include method, 
should throw IOException -->
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
try{
    pageContext.include("/tests/engine/PageContext/Back.jsp");
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