<html>
<title>positiveSession</title>
<body>
<% /**	Name : positiveSession
		Description:Set the value of the session attribute to "true".
			   Then try to access the implicit session object and 
			   call one of its methods
		Result: Returns the output of the method invoked on the 
				session object
**/ %>		
<!--- testing if implicit session object works --->
<%@ page session="true" %>
<% out.println("got "+session.isNew()); %>
</body>
</html>