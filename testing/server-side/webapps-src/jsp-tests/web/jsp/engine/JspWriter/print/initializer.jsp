<html>
<title>initializer</title>
<body>
<!-- javatest uses this jsp to get the directory where test jsps are kept -->
<%! String where=null; %>
<% String path=request.getPathTranslated(); %>
<% if(path!=null) {
                     int last=path.lastIndexOf("initializer.jsp");   
                     where= path.substring(0,last);
     
                   } else {
                       path=System.getProperty("user.home");
                       where=path;
                     }
                     
     out.println("path="+where);   %>  
</body>
</html>