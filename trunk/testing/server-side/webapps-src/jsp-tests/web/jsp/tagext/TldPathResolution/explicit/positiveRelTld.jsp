<html>
<title>positiveRelTld</title>
<body>
<% 
	/** 	
	Name : positiveRelTld
	Description : Verify that the tld can be properly resolved
                  using a relative URI.
	            
	Result : The tag will return output to client.
	**/  
%>
<%@ taglib uri="/tld_relative" prefix="relative" %>
<relative:test toBrowser="true" att1="att1">
Validated
</relative:test>
</body>
</html>
