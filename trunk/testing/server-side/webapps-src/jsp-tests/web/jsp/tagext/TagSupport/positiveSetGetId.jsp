<html>
<title>positiveSetGetId</title>
<body>
<% 
	/** 	
	Name : positiveSetGetId
	Description : passes an attribute to call setId and getId methods
		      of the implementing Tag Handler.
	            
	Result :   The setId and getId methods should work fine.The value
		   passed in the id attribute should be printed 
	**/  
%>


<%@ taglib uri="/TestLib.tld" prefix="test"  %>
<test:iterator iteration="0" setget="true" tagid="TestSetGetId">
</test:iterator>

</body>
</html>
