<html>
<title>Positive/Negative test for Tag Validator class</title>
<body>
<% 
	/** 	
	Name : validate
	Description : Tests if the Tag validator class mentioned
		      in the TLD file is invoked properly with correct
		      parameter values.Also tests if the values returned 
		      are correct.
                    
	            
	Result :   Apprpriate values and error messages should be printed
                  
	**/  
%>	 

<%-- Note the prefix is more than 10 chars --%>
<%-- Also the order of these taglibs is important --%>

<%@ taglib uri="http://java.apache.org/tlds/tld-absolute" prefix="getInitParam" %>
<%@ taglib uri="http://java.apache.org/tlds/tld-absolute" prefix="setInitParam" %>
<%@ taglib uri="http://java.apache.org/tlds/tld-absolute" prefix="getInitParam" %>

</body>
</html>
