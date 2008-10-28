<html>
<title>negativeDuplicateBufferFatalTranslationError</title>
<body>
<% /**	Name: negativeDuplicateBufferFatalTranslationError 
		Description: Verify that multiple uses of buffer attribute
                     result in a fatal translation error.
		Result: A fatal translation error
**/ %>		
<%@ page buffer="12kb" buffer="8kb" %>
</body>
</html>
