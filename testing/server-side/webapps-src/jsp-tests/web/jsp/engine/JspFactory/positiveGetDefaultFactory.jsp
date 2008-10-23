<html>
<title>positiveGetDefaultFactory</title>
<body>
<% /** Name :positiveGetDefaultFactory
       Description : we call the static method getDefaultFactory
       Result : should return a non null value for the default factory 
**/ %>
<%@ page import="javax.servlet.jsp.*" %>
<% JspFactory jf=JspFactory.getDefaultFactory(); %>
<% if(jf!=null) {
       out.println("it works"); 
   } else {
       out.println("it does not work");
   }
%>
</body>
</html>

     
