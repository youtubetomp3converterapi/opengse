
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positivePushBody</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
/*
       Name: positivePushBody
       Description:Call the pushBody() method which returns a
                 new BodyJspWriter object, saves the current "out" 
                 JspWriter,and updates the value of the "out" attribute 
                 in the page scope attribute namespace of the PageContext.
                 Print the contents of the BodyContent object using the "out"
                 object.
       Result:   Should print the contents of the BodyContent Object
       
*/

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[                 



]]></jsp:text>

<jsp:scriptlet>

<![CDATA[	
	javax.servlet.jsp.tagext.BodyContent bc = pageContext.pushBody();
	bc.println(".com");
	out.println(bc.getString());
	pageContext.popBody();

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>