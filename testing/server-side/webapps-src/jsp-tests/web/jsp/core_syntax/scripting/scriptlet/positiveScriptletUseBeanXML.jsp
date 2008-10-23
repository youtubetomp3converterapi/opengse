
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>positiveScriptletUseBean</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /**	Name:positiveScripletUseBean
		Description:Create a valid useBean action in the JSP.
				    After that,use a scriplet to change the 				
				    value of a specific bean property. Send the new 
				    value of the property to outstream.
		Result: The page contents with the new value of the bean 
				property
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
								
<!-- we use scriptlet to access the property of a bean -->
]]></jsp:text>
<jsp:useBean id="counter" scope="request" class="core_syntax.scripting.scriptlet.Counter" />
<jsp:text><![CDATA[

<!-- setCounter method is used in Bean and send to out stream -->
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
	counter.setCounter(10);
	out.println(counter.getCounterValue());

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[

</body>
<html>
]]></jsp:text>

</jsp:root>