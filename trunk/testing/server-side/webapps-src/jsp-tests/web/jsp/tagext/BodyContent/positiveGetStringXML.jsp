
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveGetString</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ 
	/** 	
	Name : positiveGetString
	Description : Try to print the contents of BodyContent object 
				 using getString() method.
	Result :  Expected to print contents of BodyContent object. 
	**/  

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[	 

]]></jsp:text>

<jsp:scriptlet>

<![CDATA[	
	// using pageContext.pushBody() to create a BodyContent object
	javax.servlet.jsp.tagext.BodyContent bc = pageContext.pushBody();
	bc.println("Checking For getString() method");
	out.println(bc.getString());
	pageContext.popBody();		

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[



</body>
</html>
]]></jsp:text>

</jsp:root>