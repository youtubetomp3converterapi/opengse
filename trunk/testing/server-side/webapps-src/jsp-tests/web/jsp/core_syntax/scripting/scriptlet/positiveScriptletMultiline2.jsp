<html>
<title>positiveScriptletMultiline2</title>
<body>
<% /**  Name: positiveScriptletMultiline2
        Description: Testing with multiple scriptlets, which spans multiple lines in such a
        way that each starts and ends in the same line
        Result:Should not throw any error 
**/ %>      
<!-- multiple lines scriptlet --> 
<%! int i=5; %>
<%! int j=10; %>
<% if(j>i){ %>10 <%} else { %>
5 <% } %>
</body>
</html>
