<html>
<title>positivePrintInt</title>
<body>
<%
/*
 Name : positivePrintInt
*/
%>
<!-- this is to test if print(Int i) method works -->
<%! int i=0; %>
<% 
    out.print(i); 
    out.print(Integer.MIN_VALUE);
    out.print(Integer.MAX_VALUE);
%>
</body>
</html>
