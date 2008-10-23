
<!-- This File is generated automatically by jsp2XML converter tool --> 
<!-- Written By Ramesh Mandava/Santosh Singh -->
<jsp:root
xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2"
>

<jsp:text><![CDATA[<html>
<title>negativeClassCastExceptionFwd</title>
<body>
]]></jsp:text>

<jsp:scriptlet>

<![CDATA[ /** 	Name : negativeClassCastFwd
        Description: Declare a bean with session scope
                     and then forward the request to another
                     page.
	    Result : None.  The interesting stuff will occur in the 
                 negativeClassCastException.jsp page.
**/ 
]]>

</jsp:scriptlet>

<jsp:text><![CDATA[
]]></jsp:text>
<jsp:useBean id="ncounter"  class="core_syntax.actions.useBean.Counter" scope="session" />
<jsp:text><![CDATA[
]]></jsp:text>
<jsp:forward page="negativeClassCastException.jsp" />
<jsp:text><![CDATA[
</body>
</html>
]]></jsp:text>

</jsp:root>