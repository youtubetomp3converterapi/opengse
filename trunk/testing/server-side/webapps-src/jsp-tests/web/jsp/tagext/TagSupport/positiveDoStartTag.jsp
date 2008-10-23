<html>
<title>positiveDoStartTag</title>
<body>
<% 
	/** 	
	Name : positiveDoStartTag
	Description : Pass an attribute to the test tag.
	              Print this inside the doStartTag() method,
	              indicating that this is called.
	Result :   The passed attribute value should be printed.
	**/  
%>
<%@ taglib uri="http://java.apache.org/tomcat/examples-taglib" prefix="eg" %>

<eg:test toBrowser="true" att1="doStartTag">
Test For </eg:test>
</body>
</html>
