<html>
<title>negativeNotFoundAbstractInstantiationException</title>
<body>
<% /** 	Name : negativeNotFoundAbstractInstantiationException
	Description : Declare a bean (no other object will be
                  found in scope) with no beanName or type 
                  attributes, and the class attribute references
                  an abstract object.  Since the object
                  doesn't already exist, useBean will try to
                  create an instance and an InstantiationException
                  will occur.
	Result : Catch the InstantiationException and print a message.
**/ %>
<% try { %>
<jsp:useBean id="abstractBean" class="core_syntax.actions.useBean.AbstractBean" scope="page" />
<% } catch ( InstantiationException ie ) {
    out.println( "InstantiationException successfully caught.\nTest status: PASS" );
   }
%>
</body>
</html>
