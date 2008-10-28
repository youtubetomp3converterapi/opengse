<html>
<title>positiveGetScope</title>
<body>
<% 
	/** 	
	Name : positiveGetScope
	Description : Create a VariableInfo object in the 
	             TestTagExtraInfo class.Create an object of 
	             TestTagExtraInfoClass.This class returns a 
	             variableInfo object.Using this object call the
	             getScope() method.
	             
	             
	Result :  Should return the lexical scope of the variable.
	**/  
%>	 

<%@ taglib uri="http://java.apache.org/tomcat/examples-taglib" prefix="eg" %>

<eg:test toBrowser="true" att1="doStartTag" att2="getScope">
Test For getScope method -- </eg:test>

</body>
</html>