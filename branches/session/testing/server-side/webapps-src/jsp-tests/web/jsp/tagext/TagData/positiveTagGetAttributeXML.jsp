
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveTagGetAttribute</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ 
	/** 	
	Name : positiveTagGetAttribute
	Description : Create a TagData object by passing a hashtable.
	              Print the contents of the hashtable using the 
	              getAttribute() method of TagData.
	Result :    The contents should be printed.
	**/  

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
	java.util.Hashtable ht = new java.util.Hashtable();
	ht.put("Color1","red");
	ht.put("Color2","green");
	ht.put("Color3","yellow");
	ht.put("Color4","orange");

	javax.servlet.jsp.tagext.TagData td = new javax.servlet.jsp.tagext.TagData(ht);

	for(int i=1;i<=4;i++) {
		out.println("Colors are  " + td.getAttribute("Color"+i));


]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<br> ]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ }

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[

</body>
</html>
]]></jsp:text>

</jsp:root>