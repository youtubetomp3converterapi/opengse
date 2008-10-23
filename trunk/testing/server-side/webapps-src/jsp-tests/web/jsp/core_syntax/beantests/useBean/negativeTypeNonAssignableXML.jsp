
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>negativeTypeNonAssignable</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : negativeTypeNonAssignable
	Description : we are using 'class' and 'type' together and 'class' 
	              is not assignable to 'type'.
	Result :
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[	 
<!-- using 'class' and 'type' together and 'class' is not assignable to type -->
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ try { 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
]]></jsp:text>
<jsp:useBean id="ncounter"  class="core_syntax.beantests.useBean.NewCounter"
type="java.util.Properties" />
<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ }catch(java.lang.ClassCastException cle) { out.println("class cast exception");
} 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[

</body>
</html>
]]></jsp:text>

</jsp:root>