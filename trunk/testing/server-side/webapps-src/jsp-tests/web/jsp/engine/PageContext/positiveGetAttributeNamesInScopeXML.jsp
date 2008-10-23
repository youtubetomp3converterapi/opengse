
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveGetAttributeNamesInScope</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
/*
 Name : positiveGetAttributeNamesInScope
  */

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[


<!-- declaring a bean in page scope -->

]]></jsp:text>
<jsp:useBean id="mybean" scope="page" class="engine.PageContext.TestBean" />
<jsp:text><![CDATA[


<!-- calling the getAttributeNamesInScope method in a scriptlet.Here we are checking for the 
return type of the method.Then checking for the name of an object, bean in this case, in the 
output.-->


]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
try{
    
    if((pageContext.getAttributeNamesInScope(pageContext.PAGE_SCOPE)) instanceof java.util.Enumeration)
     {
         java.util.Enumeration enm=pageContext.getAttributeNamesInScope(pageContext.PAGE_SCOPE);
         String s=" ";
         while(enm.hasMoreElements()) {
             Object o=enm.nextElement();
             s=s+(o.toString());
                       
         }
         
              int i=s.indexOf("mybean");
              if(i != -1){
                  out.println("SUCCESS");
              }else {
                  out.println("FAIL.Unable to identify the declared bean under page scope.");
              }
         
     }else{
         out.println("FAIL.The return type is not of type java.util.Enumeration");
     }
         
     
     
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