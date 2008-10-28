<html>
<title>negativeNotFoundInterfaceInstantiationException</title>
<body>
<% /** 	Name : negativeNotFoundInterfaceInstantiationException
	Description : Declare a bean (no other object will be
                  found in scope) with no beanName or type
                  attributes, and the class attribute references
                  an interface.  Since the object
                  doesn't already exist, useBean will try to
                  create an instance and an InstantiationException
                  will occur.
	Result : Catch the InstantiationException and print a message.
**/ %>
<% try { %>
<jsp:useBean id="interfaceBean" class="core_syntax.actions.useBean.InterfaceBean" scope="page" />
<% } catch ( java.lang.InstantiationException ie ) {
    out.println( "InstantiationException successfully caught.\nTest status: PASS" );
   }
%>
</body>
</html>
