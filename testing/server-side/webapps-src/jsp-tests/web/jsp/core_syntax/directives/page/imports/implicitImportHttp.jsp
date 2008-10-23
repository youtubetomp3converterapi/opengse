<html>
<body>
<% /**	Name: implicitImportJsp
	Description: Use jsp page directive with language="java" 
		    Do not specify javax.servlet.http in the import attribute
		    as it should be available implicitly.  Validate
		    that an HttpUtils object can be created.
			    
	Result:No error
**/ %>

<!-- language=java and we check if implicit import works -->

<%@ page language="java" %>

<%

  HttpUtils hu = new HttpUtils();
    
  
%>

<%= hu instanceof javax.servlet.http.HttpUtils %>

</body>
</html>
