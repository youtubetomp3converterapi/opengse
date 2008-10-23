<html>
<title>positivePrintLong</title>
<body>
<%
/*
 Name : positivePrintLong
*/
%>
<!-- this is to test if print(long l) method works -->
<%! long l=0; %>
<% 
    out.print(l);
    out.print(Long.MIN_VALUE);
    out.print(Long.MAX_VALUE);
%>
</body>
</html>
