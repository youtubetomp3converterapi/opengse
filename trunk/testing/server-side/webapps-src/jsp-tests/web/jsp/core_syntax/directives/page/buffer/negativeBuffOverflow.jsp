<html>
<title>negativeBuffOverflow</title>
<body>
<% /**	Name: negativeBuffOverflow
		Description: Use the default buffer size of 8kb. Write out more
			  		 than 8kb of characters to the implicit out object.
		Result: Should throw java.io.IOException and forward to default 
				error page.
**/ %>		
<!-- we keep autoFlush as false -->
<!--- with default buff size 0f 8kb we output more than that. we give 22k --->
<%@ page autoFlush="false" errorPage="errorpage.jsp" %>
<% for(int d=0;d<6000;d++) out.println(d+"    "); %>
</body>
</html>