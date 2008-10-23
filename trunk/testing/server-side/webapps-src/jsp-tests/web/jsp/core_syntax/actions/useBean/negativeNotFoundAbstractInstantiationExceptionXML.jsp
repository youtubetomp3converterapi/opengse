
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>negativeNotFoundAbstractInstantiationException</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : negativeNotFoundAbstractInstantiationException
	Description : Declare a bean (no other object will be
                  found in scope) with no beanName or type 
                  attributes, and the class attribute references
                  an abstract object.  Since the object
                  doesn't already exist, useBean will try to
                  create an instance and an InstantiationException
                  will occur.
	Result : Catch the InstantiationException and print a message.
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ try { 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
]]></jsp:text>
<jsp:useBean id="abstractBean" class="core_syntax.actions.useBean.AbstractBean" scope="page" />
<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ } catch ( InstantiationException ie ) {
    out.println( "InstantiationException successfully caught.\nTest status: PASS" );
   }

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>