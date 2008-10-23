
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positivePopBody</title>
<body>

]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
/*
     Name: positvePopBody
     Description:Call the pushBody() method which returns a
                 new BodyJspWriter object, saves the current "out" JspWriter,
                 and updates the value of the "out" attribute in the
                 page scope attribute namespace of the PageContext.Then call 
                 the popBody() which returns the previous JspWriter "out" saved
                 by the matching pushBody() and updates the value of the "out" 
                 attribute in the page scope attribute namespace of the PageConxtext
                 Invoke some method on the JspWriter object.
                 
      Result:    Should return the expected return type (depends on the method invoked).            
*/

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[

]]></jsp:text>

<jsp:scriptlet>

<![CDATA[	
	javax.servlet.jsp.tagext.BodyContent bc = pageContext.pushBody();
	JspWriter jw = pageContext.popBody();
	jw.println("sun");

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>