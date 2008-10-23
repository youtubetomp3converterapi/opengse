<html>
<title>checkException Test </title>
<body>
<% /**	Name:checkExceptionTest
		Description: Cause a java.lang.ArithmeticException by 
                     dividing by zero.  The Exception should
                     be passed to the error page as specified
                     by the errorPage attribute of the page
                     directive.  The errorpage will verify the
                     type of exception.
			 type java.lang.Throwable
        Result: Errorpage is called up, where this check is done.
                Should return true.					 
**/ %>
<!-- errorpage -->
<%@ page errorPage="Errorpage.jsp" %>

<%
 int i=0; 
 int j=9;
 int k=j/i;
%>
</body>
</html>
