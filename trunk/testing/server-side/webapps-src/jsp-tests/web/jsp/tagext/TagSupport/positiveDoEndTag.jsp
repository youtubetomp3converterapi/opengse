<html>
<title>positiveDoEndTag</title>
<body>
<% 
	/** 	
	Name : positiveDoEndTag
	Description : prints "JSP is cool" repeatedly depending on the 
		      value of the attribute and then prints "End of Tag
		      is Reached" .
	            
	Result :   "End of tag Reached" should be printed 
	**/  
%>


<%@ taglib uri="/TestLib.tld" prefix="test"  %>
<test:iterator iteration="0"  />

</body>
</html>
