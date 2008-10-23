
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /**	Name: implicitImportServlet
		Description: Use jsp page directive with language="java" 
			    Do not specify the import attribute. The javax.servlet
			    package should be available implicitly.  Validate
                that a RequestDispatch object can be created.
			    
		Result:No error
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[		

<!-- language=java and we check if implicit import works -->

]]></jsp:text>
<jsp:directive.page language="java" />
<jsp:text><![CDATA[

]]></jsp:text>

<jsp:scriptlet>

<![CDATA[

try{

 RequestDispatcher rd = getServletConfig().getServletContext().getRequestDispatcher( "/jsp/core_syntax/directives/page/imports/implicit.jsp");
 rd.forward(request, response);

  }catch(Exception o){o.printStackTrace();}


]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html>	  
	  
]]></jsp:text>

</jsp:root>