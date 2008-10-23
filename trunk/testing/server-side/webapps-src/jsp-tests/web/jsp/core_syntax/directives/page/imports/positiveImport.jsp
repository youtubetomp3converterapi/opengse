<html>
<title>positiveImport</title>
<body>
<% /**	Name: positiveImport
		Description: Use jsp page directive with language="java" and an
			  import attribute of java.util.Properties.  Validate 
              that the Properties object can be created.
		Result:No error
**/ %>
<!-- language=java and we import a java package to check if import works -->
<%@ page language="java" import="java.util.Properties" %>

<%  Properties props=new Properties(); 
    props.put("name","harry");
    String name=(String)props.getProperty("name");
    out.println(name);
 %>

</body>
</html>
