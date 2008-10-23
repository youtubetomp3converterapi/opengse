<html>
<title>positiveImplicitJarMultiTld</title>
<body>
<% 
	/** 	
	Name : positiveImplicitJarMultiTld
	Description : Verify that the container can correctly 
                  recognize multiple tlds in a jar file,
                  and using the <uri> element within the tld,
                  create the appropiate mapping for client access.
	            
	Result : The tags will send output to the client.
	**/  
%>
<%@ taglib uri="/tldone" prefix="tldone" %>
<%@ taglib uri="/tldtwo" prefix="tldtwo" %>
<tldone:tldtag uri="Implicit URI - tld_uri_one"/>
<tldtwo:tldtag uri="Implicit URI - tld_uri_two"/>
</body>
</html>
