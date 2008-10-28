
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>initializer</title>
<body>
<!-- javatest uses this jsp to get the directory where test jsps are kept -->
]]></jsp:text>

<jsp:declaration>
<![CDATA[ String where=null; ]]>

</jsp:declaration>

<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ String path=request.getPathTranslated(); 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ if(path!=null) {
                     int last=path.lastIndexOf("initializer.jsp");   
                     where= path.substring(0,last);
     
                   } else {
                       path=System.getProperty("user.home")+System.getProperty("file.separator");
                       where=path;
                     }
                     
     out.println("path="+where);   
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[  
</body>
</html>
]]></jsp:text>

</jsp:root>