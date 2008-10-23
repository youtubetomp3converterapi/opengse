<html>
<title>positiveDoAfterBody</title>
<body>
<% 
	/** 	
	Name : positiveDoAfterBody
	Description : Pass an attr to the test tag and 
	              print it inside the doAfterBody() method,
	              indicating that doAfterBody() is called.
	               
	               
	Result :     The passed attribute should be printed
	             
	**/  
%>


<%@ taglib uri="http://java.apache.org/tomcat/examples-taglib" prefix="eg" %>

<eg:test toBrowser="true" att1="doAfterBody">
Test For </eg:test>
</body>
</html>
