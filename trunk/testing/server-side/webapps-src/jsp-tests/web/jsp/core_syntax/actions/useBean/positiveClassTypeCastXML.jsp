
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveClassTypeCast</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : positiveClassTypeCast
	Description : we are using 'class' and 'type' together and 'class' 
		      is assignable to 'type'. 
	Result :we should get the expected page without an error
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- we are using 'class' and 'type' together and 'class' is assignable to type-->
]]></jsp:text>
<jsp:useBean id="ncounter"  class="core_syntax.actions.useBean.NewCounter"
type="core_syntax.actions.useBean.Counter" />
<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ out.println( ncounter.getCount());  
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>