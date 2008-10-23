<html>
<title>negativeDuplicateErrorPageFatalTranslationError</title>
<body>
<% /** 	Name : negativeDuplicateErrorPageFatalTranslationError
	Description : Verify that multiple uses of the errorPage attribute
                  result in a fatal translation error.
	Result : A fatal translation error.
**/ %>	 
<%@ page errorPage="FatalTranslationErrorPage.jsp" errorPage="FatalTranslationErrorPage.jsp" %>
</body>
</html>
