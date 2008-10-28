<html>
<title>positiveRelJar</title>
<body>
<% 
	/** 	
	Name : positiveAbsJar
	Description : Verify that the tld can be properly resolved,
                  when the web.xml mapping specifies a jar for the
                  <tld-location> element, using a relative URI.
	            
	Result : The tag will send output to the client.
	**/  
%>
<%@ taglib uri="/tld_jar" prefix="relative" %>
<relative:tldtag uri="Relative JAR reference"/>
</body>
</html>
