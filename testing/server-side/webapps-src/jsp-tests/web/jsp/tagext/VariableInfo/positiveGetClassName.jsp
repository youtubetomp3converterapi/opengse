<html>
<title>positiveGetClassName</title>
<body>
<% 
	/** 	
	Name : positiveGetClassName
	Description : Create a VariableInfo object in the 
	             TestTagExtraInfo class.Create an object of 
	             TestTagExtraInfoClass.This class returns a 
	             variableInfo object.Using this object call the
	             getClassName() method.
	
	Result : Should return the className
	**/  
%>	 

<%@ taglib uri="http://java.apache.org/tomcat/examples-taglib" prefix="eg" %>

<eg:test toBrowser="true" att1="doStartTag" att2="getClassName">
Test For getClassName -- </eg:test>

</body>
</html>