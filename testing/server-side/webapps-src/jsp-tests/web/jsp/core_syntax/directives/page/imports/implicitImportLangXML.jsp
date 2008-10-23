
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>implicitImportLang</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /**	Name: implicitImportLang
		Description: Use a jsp directive with language="java" 
			    Do not specify the import attribute.  The java.lang 
                package should be available implicitly.  Validate
                that a String and Integer object can be created.
			    
		Result:No error
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- language=java and check if implicit import works -->
]]></jsp:text>
<jsp:directive.page language="java" />
<jsp:text><![CDATA[



]]></jsp:text>

<jsp:scriptlet>

<![CDATA[  
    String str="sun";
     out.println(str);
    Integer i=new Integer(5);
    String x=i.toString();
    out.println(x);


 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[

</body>
</html>
]]></jsp:text>

</jsp:root>