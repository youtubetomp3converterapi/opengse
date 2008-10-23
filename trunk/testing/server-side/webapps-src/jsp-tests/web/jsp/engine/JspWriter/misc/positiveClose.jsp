<html>
<title>positiveClose</title>
<body>
<%
/*
 Name : positiveClose
 Description : Write something to the stream and close the stream. After closing
 the stream, try to write something into the stream. 
 */
 %>
<!-- This is to test if close method works -->
<!-- We should get 'out' object to be null after closing output -->
<% out.println("hello"); %>
<% out.close(); %>
<% out.println("hello"); %>
</body>
</html>
