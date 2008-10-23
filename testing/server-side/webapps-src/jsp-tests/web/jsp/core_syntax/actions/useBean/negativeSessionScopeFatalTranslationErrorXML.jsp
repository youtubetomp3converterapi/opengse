
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[]]></jsp:text>
<jsp:directive.page session="false" />
<jsp:text><![CDATA[
<html>
<title>negativeSessionScopeFatalTranslationError</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : negativeSessionScopeFatalTranslationError
	Description : Declare a bean with scope set to session,
                  and configure the page so that it doesn't
                  participate in a session.  This should 
                  cause a fatal translation error.
	Result : Fatal translation error
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
<!-- Declaring the bean with beanName as a class -->
]]></jsp:text>
<jsp:useBean id="myBean" scope="session"
    class="core_syntax.actions.useBean.NewCounter" />
<jsp:text><![CDATA[
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[
 out.println(myBean.getCount());

]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>