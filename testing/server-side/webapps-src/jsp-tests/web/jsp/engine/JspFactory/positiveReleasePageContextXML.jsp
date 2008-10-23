
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveReleasePageContext</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /**  Name :positiveReleasePageContext
        Description : use getDefaultFactory and use it to create a pageContext 
                      object. use the method releasePageContext with the 
                      pageContext created and then call getResponse method
        Result :we should get null as result of getResponse for the 
                pageContext after release method
 
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
    PageContext pc=jf.getPageContext(this,request,response,null,true,10,false);
    jf.releasePageContext(pc);
    if(pc!=null) {
       if(pc.getResponse()==null)
       out.println("it works");
    }else {
       out.println("it does not work");
    }
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