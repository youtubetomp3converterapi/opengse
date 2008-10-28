<html>
<title>positiveClear</title>
<body>
<%
/*
 Name: positiveClear
 Description: Initially we write something to the buffer
 and later we call the clear() method. If we get a blank page output,
 clear works.
 */
 %>
<!-- this is to test if clear() method clears the buffer -->
<!-- we are writing into a stream -->
<% out.println("hello"); %>
<!-- clearing the out stream using clear() method -->
<% out.clear(); %>
<!-- expecting blank page -->
</body>
</html>

