<html>
<title>implicitImportLang</title>
<body>
<% /**	Name: implicitImportLang
		Description: Use a jsp directive with language="java" 
			    Do not specify the import attribute.  The java.lang 
                package should be available implicitly.  Validate
                that a String and Integer object can be created.
			    
		Result:No error
**/ %>
<!-- language=java and check if implicit import works -->
<%@ page language="java" %>



<%  
    String str="sun";
     out.println(str);
    Integer i=new Integer(5);
    String x=i.toString();
    out.println(x);


 %>

</body>
</html>
