<html>
<title>negativeForwardIllegalState</title>
<body>
<%
/*
 Name : negativeForwardIllegalState
 Description : Checking for the IllegalArgumentException.Here, the buffer is set to none
 and then the forward method is called , to generate this exception.
*/
%>
<%@ page buffer="none" %>
<%
try{
    pageContext.forward("/tests/engine/PageContext/forward.jsp");
   }catch(IllegalStateException e){
       out.println(e);
   }
%>
</body>
</title>