<html>
<title>positiveGetParent</title>
<body>
<%
	/** 	
	Name : positiveGetParent
	Description : Call the "test" tag passing an attribute
	              "getParent".  The "test" tag implementation class
	              calls the getParent() method and returns the parent
	              class name.  Print the name.
	Result :   The parent class name should be printed.
	**/  
%>
<%@ taglib uri="http://java.apache.org/tomcat/examples-taglib" prefix="eg" %>
<eg:test toBrowser="true" att1="getParent" att2="getParent">
getParent method </eg:test>
</body>
</html>
