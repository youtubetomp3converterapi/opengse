<html>
<title>positiveSessionDefault</title>
<body>
<% /**	Name : positiveSessionDefault
		Description:Do not set the value of the session at all.
			    Try to invoke a method on the implicit session
			    object.
		Result: Returns the output of the method invoked on the session 
				object.
**/ %>		
<!--- testing if implicit session is available by default  --->
<% out.println("got "+session.isNew()); %>
</body>
</html>