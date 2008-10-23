<html>
<title>positiveGetAttributeNotAvbl</title>
<body>
<jsp:useBean id="beans" class="engine.PageContext.TestBean" />
<!-- calling the getAttribute method in a scriptlet -->
<%
try{
     Object o=pageContext.getAttribute("rm");
     if(o == null)
       out.println("SUCCESS");
     else
       out.println("FAIL");
   }catch(Exception e){
       out.println(e);
   }
 
%>
</body>
</html>

