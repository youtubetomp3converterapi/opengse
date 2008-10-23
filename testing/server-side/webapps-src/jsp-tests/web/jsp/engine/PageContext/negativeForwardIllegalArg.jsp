<html>
<title>negativeForwardIllegalArg</title>
<body>
<%
/*
 Name : negativeForwardIllegalArg
 Description : Checking for the IllegalArgumentException.Here, the buffer is set to none and then the forward method is called with a mis argument, to generate this exception.
*/
%>
<%
try{
    pageContext.forward("/tests/engine/PageContext/Missing.jsp");
   }catch(Exception e){
       out.println(e);
   }
%>
</body>
</title>
