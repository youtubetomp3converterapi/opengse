
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveGetPageContext</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[  /** Name :positiveGetPageContext
        Description :we use getDefaultFactory to get a valid factory object
                     and call getPageContext and on the obtained object call
                     a pagecontext method
        Result :we should get no compile time error and pagecontext method
                should work
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
    if(pc!=null) {
       if(pc.getResponse()!=null)
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