<html>
<title>positiveGetAttributeNamesInScope</title>
<body>
<%
/*
 Name : positiveGetAttributeNamesInScope
  */
%>


<!-- declaring a bean in page scope -->

<jsp:useBean id="mybean" scope="page" class="engine.PageContext.TestBean" />


<!-- calling the getAttributeNamesInScope method in a scriptlet.Here we are checking for the 
return type of the method.Then checking for the name of an object, bean in this case, in the 
output.-->


<%
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
%>


</body>
</html>

