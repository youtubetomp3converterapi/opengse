<html>

<title>Request time attributes evaluation</title>
<body>
<% /**	Name:request_time_attributes
		Description: This tests the evaluation order of 
                request time attributes in actions. The order of evaluation
	            is from left to right.
		
		Result:  Should calculate the expressions from left to right
 			 and return the correct values
		
**/ %>

<%! int i=10; %>
<%@ taglib  uri="/TestLib.tld"  prefix="request" %>
<request:ReqTime attr1="<%= i %>" />
<request:ReqTime attr1='<%= i++ %>' />
<request:ReqTime attr1="<%= i++ %>" attr2="<%= i++ %>"  />
<request:ReqTime attr1="<%= ++i %>" attr2="<%= ++i %>" attr3="<%= ++i %>"  />
<request:ReqTime attr1="<%= --i %>" attr2="<%= --i %>" attr3="<%= --i %>"  />
<%-- Make set i to original value so that we get the same output everytime we run this tests and we don't have to shutdown and restart the server --%>
<%i=10; %>

</body>
</html>
 
