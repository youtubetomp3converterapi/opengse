<html>
<title>negativeImport</title>
<body>
<% /**	Name : negativeImport
		Description: Use a jsp directive with the language not set
			   to java.Create an import statement with any package.
		Result:Undefined? Error?
**/ %>		
<!-- Language defined to something other than java and invalid import. Test should fail -->
<%@ page  language="c" import="java.util.Properties" %>

<%  Properties props=new Properties(); 
    props.put("name","harry");
    String name=(String)props.getProperty("name");
    out.println(name);
 %>
 
</body>
</html>
