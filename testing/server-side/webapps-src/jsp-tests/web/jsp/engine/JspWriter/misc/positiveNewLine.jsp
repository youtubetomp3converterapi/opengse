<html>
<title>positiveNewLine</title>
<body>
<%
/*
 Name : positiveNewLine
 Description : Call the newLine() method in the JSP page.Check for 
 number of new lines in the code generated.
*/
%>
<!-- this is to test if newLine() method works -->
<%= System.getProperty("line.separator") %>
<!-- call newLine() method -->
<% 
out.newLine();
%>
</body>
</html>
