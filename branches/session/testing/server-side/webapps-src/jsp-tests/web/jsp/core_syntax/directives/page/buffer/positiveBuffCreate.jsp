<html>
<title>positiveBuffCreate</title>
<body>
<% /**	Name: positiveBuffCreate
		Description: Create a buffer  size of say 12kb. Keep autoflush
			  set to false(default).Write characters to the out object. Invoke
			  the flush() method on out to flush the output to the client.
		Result:Should return the output that you sent to the client.
**/ %>		
<!-- with buff size 0f 12kb and autoflush false and do flush -->
<%@ page buffer="12kb" autoFlush="false" %>
<% for(int d=0;d<1000;d++) out.print(d+"    "); out.flush(); %>
</body>
</html>
