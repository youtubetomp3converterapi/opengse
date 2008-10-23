<html>
<title>positiveDoAfterBody</title>
<body>
<% 
	/** 	
	Name : positiveDoAfterBody
	Description : prints "JSP is cool" repeatedly depending on the 
		      value of the attribute and then prints "End of Tag
                      is Reached" .
	            
	Result :   "End of tag reached"
		   "JSP is cool"
	           "JSP is cool"
                   "End of tag reached"
	           "JSP is cool"
                   "JSP is cool"
		   "JSP is cool"
		   "JSP is cool"
		   "End of tag reached"
		   "JSP is cool"
		   "End of tag reached"
		   "JSP is cool"
		   "JSP is cool"
		   "JSP is cool"
		   "End of tag reached"
	**/  
%>


<%@ taglib uri="/TestLib.tld" prefix="test"  %>
<test:iterator iteration="0">
</test:iterator>
<test:iterator iteration="2">
</test:iterator>
<test:iterator iteration="4">
</test:iterator>
<test:iterator iteration="1">
</test:iterator>
<test:iterator iteration="3">
</test:iterator>

</body>
</html>
