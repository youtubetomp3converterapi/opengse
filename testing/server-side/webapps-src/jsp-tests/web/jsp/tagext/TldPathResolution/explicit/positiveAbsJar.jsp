<html>
<title>positiveAbsJar</title>
<body>
<% 
	/** 	
	Name : positiveAbsJar
	Description : Verify that the tld can be properly resolved,
                  when the web.xml mapping specifies a jar for the
                  <tld-location> element, using an absolute URI.
	            
	Result : No fatal translation error should occur.
	**/  
%>
<%@ taglib uri="http://java.apache.org/tlds/tld-absolute-jar" prefix="absolute" %>
</body>
</html>
