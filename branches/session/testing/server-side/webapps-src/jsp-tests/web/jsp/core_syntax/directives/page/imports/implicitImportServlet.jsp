<html>
<body>
<% /**	Name: implicitImportServlet
		Description: Use jsp page directive with language="java" 
			    Do not specify the import attribute. The javax.servlet
			    package should be available implicitly.  Validate
                that a RequestDispatch object can be created.
			    
		Result:No error
**/ %>		

<!-- language=java and we check if implicit import works -->

<%@ page language="java"  %>

<%

try{

 RequestDispatcher rd = getServletConfig().getServletContext().getRequestDispatcher( "/jsp/core_syntax/directives/page/imports/implicit.jsp");
 rd.forward(request, response);

  }catch(Exception o){o.printStackTrace();}

%>
</body>
</html>	  
	  
