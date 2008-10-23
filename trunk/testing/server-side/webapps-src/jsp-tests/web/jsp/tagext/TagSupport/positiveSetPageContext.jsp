<html>
<title>positiveSetPageContext</title>
<body>
<% 
	/** 	
	Name : positiveSetPageContext
	Description : Page tag is used here.  setPageContext()
	              is used to set the page context.  Using this
	              page context obj we set an attribute.  The attribute
	              is passed to the jsp page.
	Result :   The attribute should be printed.
	**/  
%>


<%@ taglib uri="http://java.apache.org/tomcat/examples-taglib" prefix="eg" %>

<eg:page toBrowser="true" att1="setPageContext" >
 positiveSetPageContext Test Passed </eg:page>
</body>
</html>
