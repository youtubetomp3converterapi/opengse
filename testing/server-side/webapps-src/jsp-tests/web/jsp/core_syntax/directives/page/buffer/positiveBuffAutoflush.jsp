<html>
<title>positiveBuffAutoflush</title>
<body>
<% /**	Name: positiveBuffAutoflush
		Description: Set autoflush to true. Use default buffer size of 8kb.
			  Write more than 8kb of data to the out object
		Result: Should return all data written to out without throwing an
			  exception.
**/ %>		
<!-- with default buff size 0f 8kb and autoflush true write more data 22kb -->
<%@ page autoFlush="true" %>
<% for(int d=0;d<6000;d++) out.print(d+"    ");  %>
</body>
</html>
