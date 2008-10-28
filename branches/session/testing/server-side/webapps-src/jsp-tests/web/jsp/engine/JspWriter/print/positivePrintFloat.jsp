<html>
<title>positivePrintFloat</title>
<body>
<%
/*
 Name : positivePrintFloat
*/
%>
<!-- this is to test if print(float f) method works -->
<%! float f=0.0F; %>
<% 
    out.print(f);
    out.print(Float.MIN_VALUE);
    out.print(Float.MAX_VALUE);
%>
</body>
</html>
