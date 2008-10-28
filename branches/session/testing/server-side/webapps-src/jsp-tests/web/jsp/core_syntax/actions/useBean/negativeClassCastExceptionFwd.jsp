<html>
<title>negativeClassCastExceptionFwd</title>
<body>
<% /** 	Name : negativeClassCastFwd
        Description: Declare a bean with session scope
                     and then forward the request to another
                     page.
	    Result : None.  The interesting stuff will occur in the 
                 negativeClassCastException.jsp page.
**/ %>
<jsp:useBean id="ncounter"  class="core_syntax.actions.useBean.Counter" scope="session" />
<jsp:forward page="negativeClassCastException.jsp" />
</body>
</html>
