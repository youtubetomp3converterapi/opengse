<html>
<title>positivePrintln</title>
<body>
<%
/*
 Name : positivePrintln
*/
%>
<!-- this is to test if println method works -->
<%! char[] c={'A','B','C'}; %>
<!-- trying to print each character in an array, in a new line -->
<% 
for( int i=0; i<3;i++ ) {   
    out.println(c[i]); 
}   
        
%>
</body>
</html>
