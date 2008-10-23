
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveSessionScope</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : positiveSessionScope
	Description : Create the JSP page with useBean scope set to session.
                      Load the CounterBean.
	Result :Counter must increment each time page is loaded.
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[	 
<!-- Declaring the bean with out body -->
]]></jsp:text>
<jsp:directive.page import="javax.servlet.http.*"/>
<jsp:text><![CDATA[
]]></jsp:text>
<jsp:useBean id="myBean" scope="session" class="core_syntax.beantests.useBean.Counter" />
<jsp:text><![CDATA[
<!-- accessing the bean thru a scriptlet -->
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ Cookie[] cok=request.getCookies(); 
   for(int j=0;j<cok.length;j++) {
   out.println(cok[j].getName()+ "  "+cok[j].getValue());
   }
   
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
      
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
out.newLine();
 out.println(myBean.getCount());

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html> 
]]></jsp:text>

</jsp:root>