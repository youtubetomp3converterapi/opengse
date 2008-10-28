<html>
<title>checkPage</title>
<body>
<% /**	Name: checkPage
	    Description: Validate that the implicit page
                     object is of type java.lang.Object.
                     Also verify that this is the same
                     reference as page.
		Result: writes true twice.
**/ %>
<!-- checking for page object type -->
<%= (page instanceof java.lang.Object ) %><br>
<%= ( page == this ) %><br>
</body>
</html>
