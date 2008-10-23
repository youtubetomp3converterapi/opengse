<html>
<title>negativeNotFoundNoArgConstructorInstantiationException</title>
<body>
<% /** 	Name : negativeNotFoundNoArgConstructorInstantiationException
	Description : Declare a bean (no other object will be
                  found in scope) with no beanName or type
                  attributes, and the class attribute references
                  an object that has no no-arg constructor. 
                  Since the object doesn't already exist, 
                  useBean will try create an instance and
                  an InstantiationException will occur.
	Result : Catch the InstantiationException and print a message.
**/ %>
<% try { %>
<jsp:useBean id="noarg" class="core_syntax.actions.useBean.OneArgConstructorBean" scope="page" />
<% } catch ( java.lang.InstantiationException ie ) {
    out.println( "InstantiationException successfully caught.\nTest status: PASS" );
   }
%>
</body>
</html>
