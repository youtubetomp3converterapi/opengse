<html>
<title>checkSession Test </title>
<body>
<% /**	Name:checkSession
		Description: Checking 'session' is of type javax.servlet.HttpSession
			  and also whether an identifier is assigned to this session
			  or not.
		Result: returns true		
**/ %>
<!-- checking for Session object state -->
<%= (session instanceof javax.servlet.http.HttpSession) %>
<br>
<br>
<% 
   if(session.getId()!=null) {
%> true <% }
   else {
%> false <% } %>
<br>
</body>
</html>
