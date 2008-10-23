<%@ page session="false" %>
<html>
<title>negativeSessionScopeFatalTranslationError</title>
<body>
<% /** 	Name : negativeSessionScopeFatalTranslationError
	Description : Declare a bean with scope set to session,
                  and configure the page so that it doesn't
                  participate in a session.  This should 
                  cause a fatal translation error.
	Result : Fatal translation error
**/ %>
<!-- Declaring the bean with beanName as a class -->
<jsp:useBean id="myBean" scope="session"
    class="core_syntax.actions.useBean.NewCounter" />
<%
 out.println(myBean.getCount());
%>
</body>
</html>
