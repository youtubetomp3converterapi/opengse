<html>
<title>positiveReleasePageContext</title>
<body>
<% /**  Name :positiveReleasePageContext
        Description : use getDefaultFactory and use it to create a pageContext 
                      object. use the method releasePageContext with the 
                      pageContext created and then call getResponse method
        Result :we should get null as result of getResponse for the 
                pageContext after release method
 
**/ %>
<%@ page import="javax.servlet.jsp.*" %>
<% JspFactory jf=JspFactory.getDefaultFactory(); %>
<% if(jf!=null) {
    PageContext pc=jf.getPageContext(this,request,response,null,true,10,false);
    jf.releasePageContext(pc);
    if(pc!=null) {
       if(pc.getResponse()==null)
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

     
