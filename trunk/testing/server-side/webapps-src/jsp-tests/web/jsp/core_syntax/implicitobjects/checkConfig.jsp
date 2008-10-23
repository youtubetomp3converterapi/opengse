<html>
<title>checkConfig Test </title>
<body>
<% /**	Name:checkConfig
		Description: Checks whether configuration information
			 is being passed to the server page. Verify that the
             implicit config object is an instance of
             javax.servlet.ServletConfig and then print out the 
             values of two servlet initialization parameters.
		Result: Display true and the values of configParam1 and configParam2 
**/ %>
<!-- checking for config object state -->
<%= (config instanceof javax.servlet.ServletConfig) %>
<br>
<%= config.getInitParameter( "configParam1" ) %><br>
<%= config.getInitParameter( "configParam2" ) %><br>
</body>
</html>
