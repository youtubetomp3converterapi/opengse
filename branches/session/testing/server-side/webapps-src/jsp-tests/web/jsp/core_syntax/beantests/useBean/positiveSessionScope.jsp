<html>
<title>positiveSessionScope</title>
<body>
<% /** 	Name : positiveSessionScope
	Description : Create the JSP page with useBean scope set to session.
                      Load the CounterBean.
	Result :Counter must increment each time page is loaded.
**/ %>	 
<!-- Declaring the bean with out body -->
<%@ page import="javax.servlet.http.*" %>
<jsp:useBean id="myBean" scope="session" class="core_syntax.beantests.useBean.Counter" />
<!-- accessing the bean thru a scriptlet -->
<% Cookie[] cok=request.getCookies(); 
   for(int j=0;j<cok.length;j++) {
   out.println(cok[j].getName()+ "  "+cok[j].getValue());
   }
   %>
      
<%
out.newLine();
 out.println(myBean.getCount());
%>
</body>
</html> 
