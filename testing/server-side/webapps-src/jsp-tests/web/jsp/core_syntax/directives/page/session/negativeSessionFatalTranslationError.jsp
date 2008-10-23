<html>
<title>negativeSessionFatalTranslationError</title>
<body>
<% /**	Name : negativeSessionFatalTranslationError
		Description:Set the value of the session attribute to "false".
			        Try to access the implicit session object.
		Result: A fatal translation error should occur.
**/ %>		
<!--- verify that a fatal translation error ocurrs -->
<%@ page session="false" %>
<% 
    if ( session == null ) {
        out.println( "Session object shouldn't be available." );
    }
%>
</body>
</html>
