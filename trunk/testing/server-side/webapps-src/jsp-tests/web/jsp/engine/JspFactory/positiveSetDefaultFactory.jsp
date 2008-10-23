<html>
<title>positiveSetDefaultFactory</title>
<body>
<%   /** Name : positiveSetDefaultFactory
         Description :use getDefaultFactory to get a factory object. use the 
                      same object for setting as default
         Result: page should get compiled and output we should get without error
**/ %>         
<%@ page import="javax.servlet.jsp.*" %>
<% JspFactory jf=JspFactory.getDefaultFactory(); %>
<% if(jf!=null) {
       JspFactory.setDefaultFactory(jf);
       out.println("it works"); 
   } else {
       out.println("it does not work");
   }
%>
</body>
</html>

     
