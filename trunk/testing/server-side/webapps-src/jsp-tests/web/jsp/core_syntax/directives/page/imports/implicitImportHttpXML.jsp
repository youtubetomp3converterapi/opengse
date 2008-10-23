
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /**	Name: implicitImportJsp
	Description: Use jsp page directive with language="java" 
		    Do not specify javax.servlet.http in the import attribute
		    as it should be available implicitly.  Validate
		    that an HttpUtils object can be created.
			    
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

  HttpUtils hu = new HttpUtils();
    
  

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[

]]></jsp:text>

<jsp:expression>
<![CDATA[ hu instanceof javax.servlet.http.HttpUtils ]]>

</jsp:expression>

<jsp:text><![CDATA[

</body>
</html>
]]></jsp:text>

</jsp:root>