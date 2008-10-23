<html>
<title>checkApplication Test </title>
<body>
<% /**	Name:checkApplicationTest
		Description: Returns true if application
		 	is of type javax.servlet.ServletContext
		 	else returns false. Then verify that the
            Context init parameter, contextParam1, is
            available using the application object.
		Result: Returns true if the application object
                is the correct object type and print 
                a non-null value for the context init 
                parameter.
**/ %>
<!-- checking for application object state -->

<%= (application instanceof javax.servlet.ServletContext) %><br>
<%= application.getInitParameter( "contextParam1" ) %><br>
</body>
</html>
