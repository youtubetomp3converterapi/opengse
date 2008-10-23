<html>
<title>positiveDirectTldReference</title>
<body>
<% 
	/** 	
	Name : positiveDirectTldReference
	Description : Verify that a tag can be used when the 
                  TLD is directly referenced in the uri
                  attribute of the taglib directive.
	            
	Result : The tag should send output to the client.
	**/  
%>
<%@ taglib uri="/WEB-INF/tlds/example-taglib.tld" prefix="direct" %>
<direct:test toBrowser="true" att1="att1">
Validated
</direct:test>
</body>
</html>
