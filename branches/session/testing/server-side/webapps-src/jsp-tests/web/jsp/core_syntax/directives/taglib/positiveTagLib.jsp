<html>
<title>positiveTagLib</title>
<body>
<% 
	/** 	
	Name : positiveTagLib
	Description : Test for the taglib directive.A valid uri is given
	               
	Result :      No error
	**/  
%>
<%@ taglib uri="http://java.apache.org/tomcat/examples-taglib" prefix="eg" %>
<eg:test toBrowser="true" att1="foo">
Positive Test taglib directive </eg:test>
</body>
</html>
