
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveImport</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /**	Name: positiveImport
		Description: Use jsp page directive with language="java" and an
			  import attribute of java.util.Properties.  Validate 
              that the Properties object can be created.
		Result:No error
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- language=java and we import a java package to check if import works -->
]]></jsp:text>
<jsp:directive.page language="java" import="java.util.Properties"/>
<jsp:text><![CDATA[

]]></jsp:text>

<jsp:scriptlet>

<![CDATA[  Properties props=new Properties(); 
    props.put("name","harry");
    String name=(String)props.getProperty("name");
    out.println(name);
 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[

</body>
</html>
]]></jsp:text>

</jsp:root>