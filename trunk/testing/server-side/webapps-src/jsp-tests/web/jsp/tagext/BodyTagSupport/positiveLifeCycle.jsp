<html>
<title>positiveLifeCycle</title>
<body>
<% 
	/** 	
	Name : positiveLifeCycle
	Description : Tests all aspects of LifeCycle aside from release(). 
	Result : Should print all attributes passed. 
	**/  
%>


<%@ taglib uri="http://java.apache.org/tomcat/examples-taglib" prefix="eg" %>

<eg:lifeCycle toBrowser="true" att1="pageContext" att2="doStartTag" att3="doInitBody"
att4="doAfterBody" att5="doEndTag" >
 </eg:lifeCycle>
</body>
</html>
