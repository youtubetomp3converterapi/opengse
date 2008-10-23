<html>
<title>negativeFlush</title>
<body>
<% /**
 Name : negativeFlush
 Description : We are testing if method flush() flushes the contents of 
               the buffer.Initially we write something into buffer and 
               later call the flush method.
**/ %>

<!-- This is to test if flush method throws IOException if stream is closed -->
<%@ page import="java.io.*;" %>
<% out.println("hello"); %>
<% out.close(); %>

<!-- To report this situation, we dont have a stream to client available -->
<!-- we create a file in the directory where jsp is kept which is seen by javatest -->
<%! String dir; %>
<% String path=request.getPathTranslated(); %>
<% if(path!=null) {
		 int where=path.lastIndexOf("negativeFlush"); 
		 dir=path.substring(0,where); 
		 }else{
		 dir=System.getProperty("user.home");
	         }
		 java.io.File file=new java.io.File(dir+System.getProperty("file.separator")+"negativeFlush.err"); 
		 java.io.FileWriter fw=new java.io.FileWriter(file);
%>

<!-- Now lets try to flush the stream -->
<% try { out.flush(); fw.write("no IOException"); fw.flush();fw.close();
}catch(java.io.IOException ioe) { %>
<% fw.write("we got IOException"); fw.flush();fw.close(); }%>
</body>
</html>
