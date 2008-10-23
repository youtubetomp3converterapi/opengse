
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveRemoveValue</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ 
	/** 	
	Name : positiveRemoveValue
	Description : Create a TagSupport object and set values
	              using the setValue() method.  Get the values 
	              set using the getValue() method.  Call the removeValue()
	              method and remove one value.  Print the rest.
	Result :   The values, except the one removed, should be printed.
	**/  

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[

	javax.servlet.jsp.tagext.TagSupport ts = new javax.servlet.jsp.tagext.TagSupport();
	
	ts.setValue("Color1","red");
	ts.setValue("Color2","green");

	for(int i=1;i<=2;i++) {
		out.println("Colors are  " + ts.getValue("Color"+i));


]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<br> ]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ }

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[

<br>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
ts.removeValue("Color1");
out.println( ts.getValue("Color1"));


]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>