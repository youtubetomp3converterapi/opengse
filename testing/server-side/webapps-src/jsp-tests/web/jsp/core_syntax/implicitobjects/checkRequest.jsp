<html>
<title>checkRequest</title>
<body>
<% /** Name:checkRequest
	   Description: Checks whether 'request' is an instance 
	   		of javax.servlet.ServletRequest and uses the request
	   		object for getting the protocol and getting parameter 
	   		value passing Years.
		Result:returns true,HTTP/1.0,2 	
**/ %>
<!-- checking for request object type -->
<%= (request instanceof javax.servlet.ServletRequest) %>

<br>
<!-- request object used to access getProtocol -->
<% out.println(request.getProtocol()); %>
<br>

<!-- request object used to access getParameter -->
<!-- Parameter "Years" is passed to request object -->
<% out.println(request.getParameter("Years")); %><br>
</body>
</html>
