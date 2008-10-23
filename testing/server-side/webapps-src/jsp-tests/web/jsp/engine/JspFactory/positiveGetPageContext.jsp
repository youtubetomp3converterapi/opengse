<html>
<title>positiveGetPageContext</title>
<body>
<%  /** Name :positiveGetPageContext
        Description :we use getDefaultFactory to get a valid factory object
                     and call getPageContext and on the obtained object call
                     a pagecontext method
        Result :we should get no compile time error and pagecontext method
                should work
 **/ %>
<%@ page import="javax.servlet.jsp.*" %>
<% JspFactory jf=JspFactory.getDefaultFactory(); %>
<% if(jf!=null) {
    PageContext pc=jf.getPageContext(this,request,response,null,true,10,false);
    if(pc!=null) {
       if(pc.getResponse()!=null)
       out.println("it works");
    }else {
       out.println("it does not work");
    }
   } else {
       out.println("it does not work");
   }
%>
</body>
</html>


     
