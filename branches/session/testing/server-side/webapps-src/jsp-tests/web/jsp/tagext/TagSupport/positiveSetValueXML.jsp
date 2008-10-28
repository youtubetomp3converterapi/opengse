
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveSetValue</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
    /*
    
    Name:  positiveSetValue
    Description: Create a TagSupport object and set values using
                  the setValue() method.  Call the getValue() method
                  and print the contents.
     Result:     The values which were set should be printed.             
 */
 

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
</body>
</html>
]]></jsp:text>

</jsp:root>