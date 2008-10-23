<html>
<title>negativeSession</title>
<body>
<% /**	Name: negativeSession
		Description: Set the value of the session attribute to "false".
			  Then try to invoke a method on the implicit session object.
		Result:Fatal translation error
**/ %>		
<!--- testing with session directive as false -it should not compile--->
<%@ page session="false" %>
<% out.println("got "+session.isNew()); %>
</body>
</html>