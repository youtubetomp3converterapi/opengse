<html>
<title>positiveDoEndTag</title>
<body>
<% 
	/** 	
	Name : positiveDoEndTag
	Description : Pass an attribute to the test tag
	              and print it inside the doEndTag()
	              indicating that the method is called.
	Result :   Should print the attribute that is passed.
	**/  
%>


<%@ taglib uri="http://java.apache.org/tomcat/examples-taglib" prefix="eg" %>

<eg:test toBrowser="true" att1="doEndTag">
Test For </eg:test>
</body>
</html>
