<html>
<title>negativePrintBoolean</title>
<body>
<%
/*
 Name : negativePrintBoolean
*/
%>
<!-- This is to test if print(boolean) throws IOException if stream is closed -->
<%@ page import="java.io.*;" %>
<% out.println("hello"); %>
<% out.close(); %>

<!-- To report this situation, we dont have a stream to client available -->
<!-- We create a file in the directory where jsp is kept which is seen by javatest -->
<%! String dir; %>
<% String path=request.getPathTranslated(); %>
<% if(path!=null) {
            int where=path.lastIndexOf("negativePrintBoolean"); 
            dir=path.substring(0,where); 
            }else {
            dir=System.getProperty("user.home");
            }
            java.io.File file=new java.io.File(dir+System.getProperty("file.separator")+"negativePrintBoolean.err"); 
            java.io.FileWriter fw=new java.io.FileWriter(file);%>
<% boolean b=true; %>
<!-- Now lets try to flush the stream -->
<% try { out.println(b); fw.write("no IOException"); fw.flush();fw.close();
}catch(java.io.IOException ioe) { %>
<% fw.write("we got IOException"); fw.flush();fw.close(); }%>
</body>
</html>
