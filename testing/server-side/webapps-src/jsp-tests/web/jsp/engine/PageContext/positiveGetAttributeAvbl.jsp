<html>
<title>positiveGetAttributeAvbl</title>
<body>
<%
/*
 Name : positiveGetAttributeAvbl
 Description : Set an attribute(like useBean), 
 and then call the method getAttribute() to get it.
*/
%>
<jsp:useBean id="beans" class="engine.PageContext.TestBean" />
<!-- calling the getAttribute method in a scriptlet -->
<%
try{
     Object o=pageContext.getAttribute("beans");
     out.println(o.getClass().getName());
 }catch(Exception e){
     out.println(e);
 }
%>
</body>
</html>

