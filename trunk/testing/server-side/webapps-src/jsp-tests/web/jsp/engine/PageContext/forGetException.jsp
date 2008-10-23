<html>
<title>forGetException</title>
<body>
<%
/**
 Name : forGetException
 Description : An exception is generated and the output is sent to an errorpage
 where the check for the getException() method is done.  Here, it is checked for 
 the return type.
 Result : true
**/
%>
<%@ page errorPage="checkGetException.jsp" %>
<%
int i=2;
int j=0;
int k=i/j;
out.println(k);
%>
</body>
</html>
