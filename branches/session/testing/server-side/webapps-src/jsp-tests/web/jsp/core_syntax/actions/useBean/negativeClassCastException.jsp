<html>
<title>negativeClassCastException</title>
<body>
<% /** 	Name : negativeClassCastException
	Description : Using a bean of a particular type declared with
                  session scope from another page, attempt to verify
                  that a ClassCastException is thrown if the bean
                  on this page specifies a conflicting type.
	Result : Catch the ClassCastException and print a message.
**/ %>
<% try { %>
<jsp:useBean id="ncounter" type="core_syntax.actions.useBean.NewCounter" scope="session" />
<% } catch ( java.lang.ClassCastException cce ) {
    out.println( "ClassCastException successfully caught.\nTest status: PASS" );
   }
%>
</body>
</html>
