
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>negativeExprBean</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /**	Name: negativeExprBean
		Description: Use a bean with the useBean action in the page.
			  	Then try to retrieve one of its properties with an 
			  	expression.
		Result: Should throw ClassCastException
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[					  	
]]></jsp:text>
<jsp:useBean id="counter" scope="request" class="core_syntax.scripting.expressions.Counter" />
<jsp:text><![CDATA[

<!-- using expression to setCounter to set counter value 10 -->
]]></jsp:text>

<jsp:expression>
<![CDATA[ counter.setCounter(10) ]]>

</jsp:expression>

<jsp:text><![CDATA[
 
<!-- using getcounterValue method in bean -->
]]></jsp:text>

<jsp:expression>
<![CDATA[ counter.getCounterValue() ]]>

</jsp:expression>

<jsp:text><![CDATA[

</body>
<html>
]]></jsp:text>

</jsp:root>