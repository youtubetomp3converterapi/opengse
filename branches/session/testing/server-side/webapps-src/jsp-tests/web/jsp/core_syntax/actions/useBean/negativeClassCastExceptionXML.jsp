
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>negativeClassCastException</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : negativeClassCastException
	Description : Using a bean of a particular type declared with
                  session scope from another page, attempt to verify
                  that a ClassCastException is thrown if the bean
                  on this page specifies a conflicting type.
	Result : Catch the ClassCastException and print a message.
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
<jsp:useBean id="ncounter" type="core_syntax.actions.useBean.NewCounter" scope="session" />
<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ } catch ( java.lang.ClassCastException cce ) {
    out.println( "ClassCastException successfully caught.\nTest status: PASS" );
   }

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>