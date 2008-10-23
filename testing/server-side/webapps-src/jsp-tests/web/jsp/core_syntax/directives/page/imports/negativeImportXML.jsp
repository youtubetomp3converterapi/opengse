
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>negativeImport</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /**	Name : negativeImport
		Description: Use a jsp directive with the language not set
			   to java.Create an import statement with any package.
		Result:Undefined? Error?
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[		
<!-- Language defined to something other than java and invalid import. Test should fail -->
]]></jsp:text>
<jsp:directive.page language="c" import="java.util.Properties"/>
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