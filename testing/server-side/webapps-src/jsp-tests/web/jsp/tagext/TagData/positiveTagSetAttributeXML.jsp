
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveTagSetAttribute</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ 
	/** 	
	Name : positiveTagSetAttribute
	Description : Create a TagData object by passing a hashtable
	              created using the setAttribute() method of
	              TagData.  Call the getAttribute() method and print 
	              the contents.
	Result :      The contents set should be printed.
	**/  

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[

	java.util.Hashtable ht = new java.util.Hashtable();
	javax.servlet.jsp.tagext.TagData td = new javax.servlet.jsp.tagext.TagData(ht);
	
	td.setAttribute("Color1","red");
	td.setAttribute("Color2","green");
	td.setAttribute("Color3","yellow");
	td.setAttribute("Color4","orange");

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