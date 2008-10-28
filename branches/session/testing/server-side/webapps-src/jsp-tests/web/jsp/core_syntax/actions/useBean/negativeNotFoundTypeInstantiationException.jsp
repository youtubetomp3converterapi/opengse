<html>
<title>negativeNotFoundTypeInstantiationException</title>
<body>
<% /** 	Name : negativeNotFoundTypeInstantiationException
	Description : Declare a bean (no other object will be
                  found in scope) with no beanName or class
                  attributes, only type.  Since the object
                  doesn't already exist, an InstantiationException
                  will occur.
	Result : Catch the InstantiationException and print a message.
**/ %>
<% try { %>
<jsp:useBean id="ncounter" type="core_syntax.actions.useBean.NewCounter" scope="session" />
<% } catch ( java.lang.InstantiationException ie ) {
    out.println( "InstantiationException successfully caught.\nTest status: PASS" );
   }
%>
</body>
</html>
