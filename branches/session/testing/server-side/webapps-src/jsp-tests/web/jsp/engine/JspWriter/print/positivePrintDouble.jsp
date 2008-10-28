<html>
<title>positivePrintDouble</title>
<body>
<%
/*
 Name : positivePrintDouble
*/
%>
<!-- this is to test if print(double d) method works -->
<%! double d=0.0; %>
<% 
    out.print(d); 
    out.print(Double.MIN_VALUE);
    out.print(Double.MAX_VALUE);
%>
</body>
</html>
