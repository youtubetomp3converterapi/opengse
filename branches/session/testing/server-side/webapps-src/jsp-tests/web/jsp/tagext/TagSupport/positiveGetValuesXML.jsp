
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveGetValues</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
    /*
    
    Name:  positiveGetValues
    Description: Create a TagSupport object and set values using
                  the setValue() method.  Call the getValues() method
                  which returns an enumeration, and print the contents.
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

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- Returns only keys names -->
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[

	java.util.Enumeration e = ts.getValues();
	while (e.hasMoreElements()) {
        String v = (String)e.nextElement();
       	out.println(v);
    }

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>