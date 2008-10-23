<html>
<title>positiveDoInitBody</title>
<body>
<% 
	/** 	
	Name : positiveDoInitBody
	Description : Pass an attribute to the test tag
	              and print it inside the doInitBody()
	              method, indicating that the method is invoked.
	Result :   The attribute passed should be printed.
	**/  
%>


<%@ taglib uri="http://java.apache.org/tomcat/examples-taglib" prefix="eg" %>

<eg:test toBrowser="true" att1="doInitBody">
Test For </eg:test>
</body>
</html>
